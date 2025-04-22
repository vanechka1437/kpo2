# Zoo Management System

Веб-приложение для автоматизации бизнес-процессов зоопарка.  
Реализовано в рамках учебного задания по курсу "Конструирование ПО".

## Функциональность

Реализованы следующие Use Case:
- **Управление животными**:
    - Добавление/удаление (POST/DELETE `/api/animals`)
    - Просмотр информации (GET `/api/animals/{id}`)
- **Управление вольерами**:
    - Создание (POST `/api/enclosures`)
    - Просмотр списка (GET `/api/enclosures`)
- **Перемещение животных** (POST `/api/transfers`)
- **Расписание кормлений**:
    - Создание расписания (POST `/api/feeding`)
    - Отметка о выполнении (POST `/api/feeding/{id}/complete`)
- **Статистика зоопарка** (GET `/api/stats`)

## Технологии

- **Java 17**
- **Spring Boot 3.1.5**
- **Swagger OpenAPI 3.0** (документация API)
- **Maven** (сборка)
- **In-memory хранилище** (H2 в режиме памяти)

## Архитектура

Проект структурирован согласно **Clean Architecture**:

```src/
├── java/
│ └── domain/
│ ├── controllers/ 
│ ├── dto/ 
│ ├── infrastructure/ 
│ ├── services/ 
│ └── ZooApplication
```

### Принципы DDD:
- **Value Objects**: `FoodType`, `EnclosureType`
- **Агрегаты**: `Animal`, `Enclosure`, `FeedingSchedule`
- **Доменные события**: `AnimalMovedEvent`, `FeedingTimeEvent`
- **Инкапсуляция правил**:
    - Проверка совместимости вольера при перемещении
    - Валидация времени кормления

## Запуск проекта

1. **Сборка**:
```bash
mvn clean install
```

2. **Запуск**:

```bash
java -jar target/zoo-management-1.0.0.jar
```

3. **Документация API**: 

Откройте в браузере:
http://localhost:8080/swagger-ui.html

## Примеры запросов
1. **Создание животного**:
```bash
curl -X POST http://localhost:8080/api/animals \
  -H "Content-Type: application/json" \
  -d '{
    "species": "Predator",
    "name": "Simba",
    "birthDate": "2020-01-15",
    "gender": "Male",
    "favoriteFood": "Meat"
  }'
```
2. **Получение статистики**:
```bash
curl -X GET http://localhost:8080/api/stats
```

## Применённые концепции

| Принцип                             | Как реализовано?                                                                 |
|-------------------------------------|----------------------------------------------------------------------------------|
| **Независимость Domain слоя**       | Классы `Animal`, `Enclosure`, `FeedingSchedule` не содержат аннотаций Spring.    |
| **Зависимости через интерфейсы**    | Сервисы используют `AnimalRepository`, `EnclosureRepository` (интерфейсы).      |
| **Изоляция бизнес-логики**          | Правила перемещения животных реализованы в `Animal.moveToEnclosure()`.           |
| **Слоистая архитектура**            | Чёткое разделение на Domain/Application/Infrastructure/Presentation слои.       |
| **Value Objects (DDD)**             | `FoodType`, `EnclosureType` — иммутабельные объекты с валидацией.               |
| **Доменные события (DDD)**          | `AnimalMovedEvent` генерируется при изменении вольера.                          |
| **Инкапсуляция правил (DDD)**       | `Enclosure.addAnimal()` проверяет вместимость перед добавлением.                |
| **Dependency Inversion (SOLID)**    | Сервисы зависят от абстракций (`FeedingScheduleRepository`), а не реализаций.   |
| **Single Responsibility (SOLID)**   | `AnimalTransferService` отвечает только за перемещение животных.                |
