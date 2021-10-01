FROM gcr.io/distroless/java:11-debug
SHELL ["/busybox/sh", "-c"]
WORKDIR /app
COPY build/libs/device-service-0.0.1-SNAPSHOT.jar /app
CMD ["device-service-0.0.1-SNAPSHOT.jar"]