# app.py
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
scaler, model = joblib.load(model_path)
app = Flask(__name__)

engine = create_engine(f"mysql+pymysql://{user}:{password}@{host}:{port}/{database}")
query = "SELECT * FROM transaction"
df = pd.read_sql(query, engine)
df['date'] = pd.to_datetime(df['date'])
df['hour'] = df['date'].dt.hour
X = df.drop(columns=['user_id', 'id', 'date'])
X = pd.get_dummies(X, drop_first=False)
print(X.head())

@app.route('/predict', methods=['POST'])
def predict():

    data = request.get_json()
    df_new = pd.DataFrame([data])

    user_id = data["userId"]
    print("user_id : " + user_id)
    df_new['date'] = pd.to_datetime(df_new['date'])

    df_new['hour'] = df_new['date'].dt.hour
    df_new = df_new.drop(columns=['date'])
    print(df_new.head())
    new_transaction = pd.get_dummies(df_new)
    new_transaction = new_transaction.reindex(columns=X.columns, fill_value=0)


    new_scaled = scaler.transform(new_transaction)


    result = model.predict(new_scaled)[0]
    print("Prediction Result:", result)
    print("🚨 Anomalous transaction !" if result == -1 else "✅ Normal transaction")

    return jsonify({'prediction': int(result)})

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
