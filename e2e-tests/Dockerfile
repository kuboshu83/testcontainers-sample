FROM mcr.microsoft.com/playwright/python:v1.58.0-noble

WORKDIR /tests

COPY requirements.txt ./
RUN pip install -r requirements.txt

CMD ["pytest", "-s"]