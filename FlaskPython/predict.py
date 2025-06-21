#predict.py
from flask import Flask, request, jsonify
import joblib
from sqlalchemy import create_engine
from dotenv import load_dotenv
import pandas as pd
import os

load_dotenv()

user = os.getenv("DB_USER")
password = os.getenv("DB_PASSWORD")
host = os.getenv("DB_HOST")
port = os.getenv("DB_PORT")
database = os.getenv("DB_NAME")
model_path = os.getenv("MODEL_PATH")

model = joblib.load(model_path)
from_stats = joblib.load('./from_stats.pkl')
to_stats = joblib.load('./to_stats.pkl')

app = Flask(__name__)

engine = create_engine(f"mysql+pymysql://{user}:{password}@{host}:{port}/{database}")
query = "SELECT * FROM transaction"
df = pd.read_sql(query, engine)
df['date'] = pd.to_datetime(df['date'])
df['hour'] = df['date'].dt.hour
X = df.drop(columns=['user_id', 'id', 'date'])
X = pd.get_dummies(X, drop_first=False)
print(X.head())
print("✅ from_stats.columns:", from_stats.columns.tolist())

@app.route('/predict-advanced', methods=['POST'])
def predict():

    data = request.get_json()

    new_txn = pd.DataFrame([data])
    print("✅ new_txn:\n", new_txn)
    column_mapping = {
            "fromAccountId": "from_account_id",
            "toAccountId": "to_account_id",
            "userId": "user_id",
            "date": "date",
            "type": "type",
            "amount": "amount"
        }

    new_txn.rename(columns=column_mapping, inplace=True)
    user_id = data["userId"]



    new_txn = new_txn.merge(from_stats, on='from_account_id', how='left')
    new_txn = new_txn.merge(to_stats, on='to_account_id', how='left')

    new_txn['date'] = pd.to_datetime(new_txn['date'])
    new_txn['hour'] = new_txn['date'].dt.hour
    new_txn['date_weekend'] = new_txn['date'].dt.weekday >= 5
    new_txn['date_day_of_week'] = new_txn['date'].dt.weekday
    new_txn['date_day_of_month'] = new_txn['date'].dt.day
    new_txn['date_month'] = new_txn['date'].dt.month
    new_txn['date_quarter'] = new_txn['date'].dt.quarter
    new_txn["amount_z"] = (new_txn["amount"] - df["from_mean"]) / (df["from_std"] + 1e-5)
    new_txn["is_new_account"] = (new_txn["from_count"] == 1).astype(int)

    new_txn = new_txn[[
        'amount', 'from_mean', 'from_std', 'from_count',
        'to_mean', 'to_std', 'to_count',
        'hour', 'date_weekend', 'date_day_of_week',
        'date_day_of_month', 'date_month', 'date_quarter', 'amount_z','is_new_account'
    ]]

    pred = model.predict(new_txn)[0]  # 1: 정상, -1: 이상치
    score = model.decision_function(new_txn)[0]
    print("Prediction Score:", score )
    print("🚨 Anomalous transaction !" if pred == -1 else "✅ Normal transaction")

    return jsonify({'prediction': int(pred)})

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
