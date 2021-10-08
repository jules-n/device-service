FROM gcr.io/distroless/java:11-debug
SHELL ["/busybox/sh", "-c"]
WORKDIR /app
COPY build/libs/device-service.jar /app
CMD ["device-service.jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"]
