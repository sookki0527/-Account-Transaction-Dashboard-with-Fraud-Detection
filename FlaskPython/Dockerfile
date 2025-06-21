FROM python:3.10-slim

WORKDIR /app

COPY requirements.txt requirements.txt
COPY predict.py predict.py
COPY isolation_model.pkl .
COPY from_stats.pkl .
COPY to_stats.pkl .
RUN pip install --upgrade pip && \
    pip install -r requirements.txt


CMD ["python", "predict.py"]
