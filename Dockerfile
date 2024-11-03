FROM openjdk:17-jdk-slim
WORKDIR /app
#COPY target/order.jar orders.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "orders.jar"]
