FROM eclipse-temurin:17-jdk-alpine
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
WORKDIR /app
# Copia y cambia el dueño
COPY --chown=appuser:appgroup target/menu-digital-1.0.0.jar app.jar
EXPOSE 8084
USER appuser
ENTRYPOINT ["java", "-jar", "app.jar"]