FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
WORKDIR /app
COPY --chown=appuser:appgroup --from=build /app/target/*.jar app.jar
EXPOSE 8084
USER appuser
ENTRYPOINT ["java", "-jar", "app.jar"]
