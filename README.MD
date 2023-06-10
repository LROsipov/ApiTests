#  autotests API

### About

Дипломная работа. Курс  "Автоматизатор тестирвоание на Java".       
Задание 2: API   
19_qa-java

### Structure

Проект состоит из одного модуля и содержит код и API тесты для сайта https://stellarburgers.nomoreparties.site

Стандартная структура модуля:

в src:

* main/java/site/stellaburgers
    * utils - базовые значения для тестов
    * enums - различные enum, которые могут понадобиться в рамках разработки автотестов
    * dto - DTO классы для работы с сериализации и десериализации данных
    * specs - спецификации апи
    * factory - классы для генерации объектов с тестовыми данными
    * steps - классы, содержащие специфичные для данного сервиса методы-степы


* test/java/site/stellaburgers/test
    * BaseTest - базовый класс для общей логики
    * пакеты\классы с тестовыми сценариями
### Stack
В проекте используется Java 11, RestAssured  5.3, Lombok 1.18.26, JUnit 4.13.2,Maven 4.0.0

### Installation
В проекте есть возможность получить  Allure отчет  о прохождении тестов. Для получения отчета о тестировании в терминале необходимо выполнить команду из папки проекта
mvn clean test
mvn allure:serve

### Contributors
email:osipov.l.r@yandex.ru
tg:@leon1d0s