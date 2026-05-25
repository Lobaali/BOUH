FROM nvcr.io/nvidia/tritonserver:24.01-py3

COPY model_repository /models

CMD ["tritonserver","--model-repository=/models","--grpc-port=8080","--http-port=8000","--metrics-port=8002"]