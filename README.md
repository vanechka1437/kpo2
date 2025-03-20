# Финансовый учет (ВШЭ-банк)

## Описание проекта
Данный проект реализует модуль **"Учет финансов"** для ВШЭ-банка.  
Система позволяет пользователям управлять своими **счетами, категориями доходов и расходов**, а также анализировать **финансовые операции**.

**Основной функционал:**  
✅ Создание, редактирование и удаление **счетов, категорий и операций**  
✅ Подсчет **разницы доходов и расходов** за период  
✅ Группировка операций **по категориям**  
✅ **Импорт и экспорт** данных в CSV, JSON, YAML  
✅ **Аналитика и статистика по финансам**  
✅ **Кеширование данных** для повышения производительности

---

## Используемые технологии
- **Java 17**
- **Spring Boot** (DI, JPA, REST)
- **H2/PostgreSQL** (БД)
- **Lombok** (для упрощения кода)
- **Maven** (сборка проекта)

---

## Архитектурные принципы

### **Принципы SOLID**
Проект разрабатывался с учетом **SOLID**:

| Принцип | Как реализовано? |
|---------|-----------------|
| **S** - Single Responsibility (Принцип единственной ответственности) | `BankAccountService`, `OperationService` и другие сервисы отвечают только за свою логику. |
| **O** - Open/Closed (Принцип открытости/закрытости) | Для импорта/экспорта данных используется **шаблонный метод**, позволяющий легко добавлять новые форматы. |
| **L** - Liskov Substitution (Принцип подстановки Барбары Лисков) | Все подклассы (`CsvDataImporter`, `JsonDataImporter`) корректно заменяют родительский класс `DataImporter`. |
| **I** - Interface Segregation (Принцип разделения интерфейсов) | Разные интерфейсы для репозиториев, сервисов, фасадов. |
| **D** - Dependency Inversion (Принцип инверсии зависимостей) | Используется Spring DI (`@Service`, `@Repository`, `@Autowired`). |

---

### **Принципы GRASP**
Проект учитывает **GRASP (General Responsibility Assignment Software Patterns)**:

| Принцип | Как реализовано? |
|---------|-----------------|
| **High Cohesion (Высокая связанность)** | `FinanceFacade` объединяет работу с доменной моделью. |
| **Low Coupling (Слабая связанность)** | `OperationService`, `CategoryService` не зависят друг от друга напрямую. |
| **Controller (Контроллер)** | REST-контроллеры (`AccountController`, `OperationController`) управляют взаимодействием. |
| **Polymorphism (Полиморфизм)** | Шаблонный метод для импорта данных (`DataImporter`). |
| **Pure Fabrication (Изолированное проектирование)** | `BankAccountProxy` кеширует данные и разгружает базу. |

---

## Используемые паттерны GoF

| Паттерн         | Где используется? |
|----------------|----------------|
| **Фасад (Facade)** | Управление `BankAccount`, `Category`, `Operation` через `FinanceFacade`. |
| **Команда (Command) + Декоратор (Decorator)** | Реализация пользовательских сценариев (`AddOperationCommand`) и измерение времени выполнения (`TimedCommandDecorator`). |
| **Шаблонный метод (Template Method)** | Импорт данных (`DataImporter`, `CsvDataImporter`, `JsonDataImporter`). |
| **Посетитель (Visitor)** | Экспорт данных (`ExportVisitor`). |
| **Фабрика (Factory)** | Создание объектов (`DomainFactory`). |
| **Прокси (Proxy)** | Кеширование данных о счетах (`BankAccountProxy`). |

---

## Установка и запуск
### 1. **Клонирование проекта**
```sh
git clone https://github.com/your-repo/finance-tracker.git
cd finance-tracker
