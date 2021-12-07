FROM openjdk:17.0.1
WORKDIR /app
COPY build/libs/device-service.jar /app
CMD ["java", "device-service.jar"]