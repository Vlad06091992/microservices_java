# Используем образ с Java 24 (так как у вас в pom.xml указана 24 версия)
FROM openjdk:24-ea-17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем JAR файл приложения
COPY target/eureka-0.0.1-SNAPSHOT.jar app.jar

# Открываем порт (стандартный для Spring Boot)
EXPOSE 3000

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]
