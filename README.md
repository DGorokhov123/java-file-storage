# File Storage Service (FSS)

Тестовое задание для ПКО Воксис

### Описание
Сервис с веб-интерфейсом, который позволяет пользователям загружать, просматривать, скачивать и удалять файлы. 
Метаданные файлов (имя, размер, дата загрузки, MIME-тип и т.д.) хранятся в базе данных PostgreSQL. 
Самые файлы сохраняются на диск сервера.

### Стек
•	Backend: Java 21, Spring Boot
•	PostgreSQL + JPA (Hibernate)
•	Frontend: Thymeleaf + JavaScript
•	Сборка: Maven
•	Контейнеризация: Docker, Docker Compose.

### Запуск приложения

1. Клонируйте репозиторий:
```bash
git clone https://github.com/DGorokhov123/java-file-storage.git
cd java-file-storage
```
2. Запустите приложение
```bash
docker compose up -d
```
3. Пользуйтесь
[http://127.0.0.1:8080/](http://127.0.0.1:8080/)
