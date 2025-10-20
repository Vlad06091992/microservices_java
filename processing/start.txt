@echo off
cd /d "C:\Users\Admin\Desktop\study_folder\microservices_java\repo\currency"

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
call docker build -t currency-service .
if errorlevel 1 (
    echo ERROR: Ошибка сборки образа!
    pause
    exit /b 1
)

echo Запуск контейнера...
docker stop currency-service >nul 2>&1
docker rm currency-service >nul 2>&1
call docker run -d -p 8080:8080 --name currency-service -e SPRING_PROFILES_ACTIVE=cloud currency-service

echo Currency service started!
echo Доступно по: http://localhost:8080
pause