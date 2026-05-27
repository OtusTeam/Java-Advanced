# reactor — OTUS Java Advanced: Reactive Demo

## Стек

- **Java 17**, **Spring Boot 3.3.1**, **WebFlux** (Project Reactor)
- **R2DBC + PostgreSQL** (реактивный драйвер, без JDBC)
- Maven, Lombok

## Запуск

```bash
# 1. Поднять БД
docker-compose up -d          # postgres:15 на localhost:5432, db=test, user=postgres, pass=123

# 2. Запустить приложение
./mvnw spring-boot:run        # порт 8080
```

`schema.sql` накатывается автоматически при старте (`spring.sql.init.mode: always`).

## Структура

```
src/main/java/com/example/demo/
  controller/
    UserController.java   — CRUD + /users-emails + /sync/users-emails
    EmailController.java  — /emails, /emails/ids, /sync/emails, /sync/emails/ids
  service/
    UserService.java      — бизнес-логика; emailProviders — захардкожен (mail.ru, yandex.ru, gmail.com)
  repo/
    UserRepository.java   — R2DBC репозиторий
  dto/
    User.java, CreateUserRq.java, EmailDto.java, EmailProviderDto.java
  debug/
    DebugReactiveApp.java — учебный класс: реактивная vs. блокирующая реализация одного алгоритма
```

## API (базовый URL: `http://localhost:8080`)

| Метод  | Путь                    | Описание                                              |
|--------|-------------------------|-------------------------------------------------------|
| POST   | `/user`                 | Создать пользователя `{name, email}`                  |
| GET    | `/user/{id}`            | Получить по ID                                        |
| PUT    | `/user/{id}`            | Обновить `{name, email}`                              |
| DELETE | `/user/{id}`            | Удалить                                               |
| GET    | `/users`                | Все пользователи (`Flux<User>`)                       |
| GET    | `/users-emails`         | Все email строками (`Flux<String>`)                   |
| GET    | `/sync/users-emails`    | То же самое, но блокирующий вызов (`List<String>`)    |
| GET    | `/emails`               | Все email как `Flux<EmailDto>`                        |
| GET    | `/sync/emails`          | Блокирующий вариант `/emails`                         |
| GET    | `/emails/ids`           | ID email-провайдеров по доменам (`Flux<Integer>`)     |
| GET    | `/sync/emails/ids`      | То же, блокирующий                                    |

Postman-коллекция: `Reactive User API.postman_collection.json` (переменные: host=localhost, port=8080, userId=1, userName=Alex, userEmail=alex@mail.ru).

## Ветка `lessons/reactor-profiling` — что изменилось

Ветка посвящена **профилированию и отладке реактивных цепочек**:

- **`DebugReactiveApp`** — учебный класс, демонстрирующий два подхода к одной задаче (получить ID провайдеров из email пользователей):
  - реактивный (`Flux<Integer> getUsersEmailProviders(Flux<UserDto>)`)
  - блокирующий (`List<Integer> getUsersEmailProviders(List<UserDto>)`)
- **`UserService.getUsersEmailProviderIds()`** — расширена отладочными операторами: `doOnSubscribe`, `doOnNext`, `doOnError`, `doOnComplete`, `.checkpoint(...)`, `.log(...)` для наблюдения за потоком данных.
- **`UserController`** — рефакторинг (закомментированы альтернативные `produces`: `APPLICATION_NDJSON_VALUE`, `TEXT_EVENT_STREAM_VALUE` — можно раскомментировать для стриминга).

## Важные детали

- `getUsersEmailsBlocking()` намеренно делает `Thread.sleep(100)` — демонстрация блокировки в реактивном стеке.
- `emailProviders` — статический массив, не БД; провайдеры: id=1 mail.ru, id=2 yandex.ru, id=3 gmail.com.
- Таблица в БД называется `app_user` (не `user`, чтобы избежать конфликта с зарезервированным словом SQL).
- Уникальный индекс на `email` — дублирующий email вернёт ошибку.
