FROM python:3.10-slim

WORKDIR /app

COPY requirements.txt requirements.txt
COPY app.py app.py
COPY scaler_and_model.pkl .

RUN pip install --upgrade pip && \
    pip install -r requirements.txt


CMD ["python", "app.py"]
