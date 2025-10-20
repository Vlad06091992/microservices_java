@echo off
cd /d "C:\Users\Admin\Desktop\study_folder\microservices_java\repo\eureka"

echo Проверка Docker...
docker version
if errorlevel 1 (
    echo ERROR: Docker не запущен!
    pause
    exit /b 1
)

echo Сборка JAR...
call mvn clean package -DskipTests
if errorlevel 1 (
    echo ERROR: Ошибка сборки!
    pause
    exit /b 1
)

echo Сборка Docker образа...
call docker build -t eureka-service .
if errorlevel 1 (
    echo ERROR: Ошибка сборки образа!
    pause
    exit /b 1
)

echo Запуск контейнера...
docker stop eureka-service >nul 2>&1
docker rm eureka-service >nul 2>&1
call docker run -d -p 8761:8761 --name eureka-service eureka-service

echo eureka-service started!
echo Доступно по: http://localhost:8761
pause