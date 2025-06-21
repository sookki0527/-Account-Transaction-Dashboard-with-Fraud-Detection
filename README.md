# 🏦 Account Transaction Dashboard with ML Fraud Detection

This project is a full-stack microservices-based banking dashboard that integrates Java-based backend services with a Python-based ML microservice to detect fraudulent transactions. It demonstrates a production-style architecture that uses real-time messaging (Kafka), REST APIs, and statistical anomaly detection using Isolation Forest.

##  Architecture Overview
![Diagram](https://github.com/user-attachments/assets/6257a635-71ac-49ee-b0a2-8e8e8332e198)


**Key Highlights:**
- Spring Boot for account & transaction services
- Python Flask ML API for fraud detection
- Kafka for real-time, asynchronous event streaming
- MySQL for relational data storage
- Angular for dynamic UI and WebSocket-based notifications
- Docker Compose for service orchestration

---

## 🛠 Tech Stack

| Component              | Tech/Tool                                 |
|------------------------|--------------------------------------------|
| **Backend API**         | Spring Boot (REST, JPA, MySQL)             |
| **ML Service**          | Python, Flask, Scikit-learn, Pandas, NumPy |
| **Event Streaming**     | Apache Kafka, Spring Kafka, kafka-python   |
| **Frontend**            | Angular, RxJS, WebSocket                   |
| **Infrastructure**      | Docker, Docker Compose, AWS EC2           |

---

##  Features

-  Account & Transaction APIs (Deposit, Withdraw, Transfer)
-  Real-time fraud detection using Kafka event pipeline
-  Isolation Forest-based unsupervised anomaly prediction
-  Statistical feature engineering based on account behavior
-  Containerized deployment via Docker Compose
-  WebSocket-based real-time frontend alerts

---

##  Project Structure

### Account Service
- Handles deposits/withdrawals
- Publishes events to Kafka `account-topic`
- Tech: Spring Boot, MySQL, Kafka Producer

###  Transaction Service
- Handles inter-account transfers
- Publishes to Kafka `transfer-topic`
- Tech: Spring Boot, MySQL, Kafka Producer

###  Fraud Detection Service (Java)
- Kafka consumer for transactions
- Calls Flask ML API for prediction
- Publishes results to Kafka
- Tech: Spring Boot, RestTemplate, Kafka, Docker

###  ML API (Python + Flask)
- Loads Isolation Forest model
- Receives transaction data via POST `/predict`
- Returns `{"result": "anomaly"}` or `{"result": "normal"}`

###  Notification Service
- Listens to fraud detection output
- Sends WebSocket alerts to Angular frontend

---

##  ML Training & Feature Engineering

The fraud detection model uses **Isolation Forest** with statistically engineered features:

For each sender and receiver account, I calculated:

- Average transaction amount (`mean`)
- Standard deviation (`std`)
- Number of transactions (`count`)

```python
from_stats = df.groupby('from_account_id')['amount'].agg(['mean', 'std', 'count']).rename(
    columns={'mean': 'from_mean', 'std': 'from_std', 'count': 'from_count'}
)

to_stats = df.groupby('to_account_id')['amount'].agg(['mean', 'std', 'count']).rename(
    columns={'mean': 'to_mean', 'std': 'to_std', 'count': 'to_count'}
)

df = df.merge(from_stats, on='from_account_id', how='left')
df = df.merge(to_stats, on='to_account_id', how='left')

🔹 Model Training (Isolation Forest)
Used IsolationForest from scikit-learn

Set contamination = 0.2 to simulate ~20% anomaly ratio

Scored predictions using decision_function

python
Copy
Edit
model = IsolationForest(n_estimators=100, contamination=0.2, max_features=1.0, random_state=42)
model.fit(X)
df['anomaly'] = model.predict(X)
df['anomaly_score'] = model.decision_function(X)


🔹 Results
Anomalies (i.e., anomaly == -1) were sorted by anomaly score and visually inspected. Below are the top suspicious transactions:

amount	type	from_account_id	anomaly_score
9971.79	TRANSFER	25883	-0.73
8326.58	WITHDRAW	12819	-0.66
6403.13	WITHDRAW	13866	-0.60

This model was then exported using joblib and deployed to the Flask ML API.

