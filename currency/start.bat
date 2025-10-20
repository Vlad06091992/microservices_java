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

echo Остановка старых контейнеров...
docker stop currency-service-1 currency-service-2 >nul 2>&1
docker rm currency-service-1 currency-service-2 >nul 2>&1

echo Создание сети (если нет)...
docker network create app-network >nul 2>&1

echo Запуск инстансов...


docker run -d -p 8080:8080 ^
  -e SPRING_PROFILES_ACTIVE=cloud ^
  -e EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://host.docker.internal:8761/eureka/ ^
  -e SPRING_APPLICATION_NAME=currency-service ^
  -e SERVER_PORT=8080 ^
  currency-service

docker run -d -p 8081:8081 ^
  -e SPRING_PROFILES_ACTIVE=cloud ^
  -e EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://host.docker.internal:8761/eureka/ ^
  -e SPRING_APPLICATION_NAME=currency-service ^
  -e SERVER_PORT=8081 ^
  currency-service

echo Оба инстанса currency-service запущены!
echo Проверьте Eureka: http://localhost:8761
pause