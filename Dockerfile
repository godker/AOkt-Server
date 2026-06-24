FROM eclipse-temurin:25-jre

WORKDIR /app

EXPOSE 7666

ENTRYPOINT ["java", "-jar", "/app/AOkt-Server-all.jar"]