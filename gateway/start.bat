@echo off
cd /d "C:\Users\Admin\Desktop\study_folder\microservices_java\repo\gateway"

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
call docker build -t gateway-service .
if errorlevel 1 (
    echo ERROR: Ошибка сборки образа!
    pause
    exit /b 1
)

echo Запуск контейнера...
docker stop gateway-service >nul 2>&1
docker rm gateway-service >nul 2>&1
call docker run -d -p 3001:3001 --name gateway-service -e SPRING_PROFILES_ACTIVE=cloud gateway-service

echo Gateway service started!
echo Доступно по: http://localhost:3001
pause