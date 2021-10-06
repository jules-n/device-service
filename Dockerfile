FROM gcr.io/distroless/java:11-debug
SHELL ["/busybox/sh", "-c"]
WORKDIR /app
COPY build/libs/device-service.jar /app
CMD ["device-service.jar"]
