# Cursova
Опис:
Цей проєкт призначений для автоматизованого тестування веб-аплікації з використанням Selenium, Selenide та TestNG. Проєкт підтримує багатобраузерне тестування (Chrome, Chrome Headless, Firefox) і дозволяє запускати тести для локально піднятої аплікації або аплікації, що доступна з інтернету.

Вимоги:
Перед початком роботи необхідно мати:

Java 11+ (JDK),
Maven,
Встановлені браузери: Chrome, Firefox,
Драйвери для браузерів:
ChromeDriver
GeckoDriver

Установка:

Клонувати репозиторій:
git clone https://github.com/AnastasiiaDegtyaryova/cursova.git

Перейти в директорію проєкту:
cd your-project

В директорії з docker-compose.yml в командному рядку ввести docker compose up

Встановити залежності за допомогою Maven:
mvn clean install

Налаштування:
У проєкті використовується файл config.properties, в якому можна налаштувати URL аплікації для тестування (локально або з інтернету).

Приклад конфігураційного файлу:
baseUrl = http://127.0.0.1

# або для інтернет-доступу
# baseUrl=http://127.0.0.1/jsonrpc.php

Запуск тестів:

Запуск на Chrome:
mvn clean test -Dbrowser=chrome

Запуск на Chrome Headless:
mvn clean test -Dbrowser=chrome_headless

Запуск на Firefox:
mvn clean test -Dbrowser=firefox

Структура проєкту:
src/test/java - Тестові класи
src/main/resources - Ресурси та конфігурації
drivers/ - Драйвери для браузерів

Основні класи:

BaseApiTest - Базовий клас для роботи з API, який використовує JSON-RPC.

TaskActionsTest - Клас, який тестує дії над тасками, включаючи створення, редагування та переміщення задач між колонками.

TestSetup - Клас для ініціалізації браузера, підтримує кілька режимів запуску (Chrome, Chrome Headless, Firefox).

Звітування:
Для створення звітів тестування використовується Allure. Для генерації звіту виконайте:
mvn allure:serve
