# Команда SEVEN
![](https://i.ibb.co/0BrqtpN/logo2.png)

## Backend часть для сайта объявлений
Для готовой фронтенд части необходимо реализовать функционал backend на Java:
* Авторизация и аутентификация пользователей. 
* Распределение ролей между пользователями: пользователь и администратор. 
* CRUD для объявлений на сайте: администратор может удалять или редактировать все объявления, а пользователи — только свои. 
* Под каждым объявлением пользователи могут оставлять отзывы. 
* В заголовке сайта можно осуществлять поиск объявлений по названию. Показывать и сохранять картинки объявлений.
* 
В качестве шаблона был предоставлен файл [Openapi](https://drive.google.com/file/d/1fGT-DBhNW9IyRdrqTBi8moIx8iL1txeF/view).

## Техническое задание проекта:
[Техническое задание](https://skyengpublic.notion.site/64113e0a2641475c9ad9bea93144afff)

## Комада разработчиков "SEVEN":
[Кучеренко Виталий](https://github.com/KucherenkoVV/)

[Иванов Антон](https://github.com/Lycanthropewolf)

## Стек технологий:

### Backend:
* Среда разработки: IntelliJ IDEA 2023.2 (Ultimate Edition)
* Java 11

Подключенные библиотеки:
* Maven
* Spring Boot
* Spring Web
* Spring Data
* Spring JPA
* Spring Security
* Swagger
* Lombok
* Stream API

### SQL:
* PostgreSQL
* Liquibase

### Frontend:
* Docker [образ](http://ghcr.io/bizinmitya/front-react-avito:v1.19)

## Запуск:
Для запуска необходимо:
1. Клонировать проект в среду разработки
   
![](https://i.ibb.co/0sV13Zw/git-clone.png)
![](https://i.ibb.co/2MYJbDq/VSC.png)

2. Прописать properties в файле application.properties
3. Запустить Docker
4. Запустить Docker образ
5. Запустить метод main в файле HomeworkApplication.java
После выполнения всех действий сайт будет доступен по ссылке http://localhost:3000 и Swagger по ссылке.
