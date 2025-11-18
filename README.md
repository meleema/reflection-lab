# Reflection Lab - Фреймворк внедрения зависимостей

Лабораторная работа по рефлексии и внедрению зависимостей в Java. Реализует пользовательскую аннотацию `@AutoInjectable` и инжектор зависимостей с использованием Java Reflection API.

## Описание проекта

Этот проект демонстрирует реализацию простого фреймворка внедрения зависимостей с использованием Java Reflection API. Он включает пользовательскую аннотацию `@AutoInjectable` и класс `Injector`, который автоматически инициализирует помеченные поля настроенными реализациями.

## Структура проекта

```
reflection-lab/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           ├── injector/
│   │   │           │   ├── AutoInjectable.java
│   │   │           │   ├── Injector.java
│   │   │           │   ├── SomeInterface.java
│   │   │           │   ├── SomeOtherInterface.java
│   │   │           │   ├── SomeImpl.java
│   │   │           │   ├── OtherImpl.java
│   │   │           │   ├── SODoer.java
│   │   │           │   └── SomeBean.java
│   │   │           └── Main.java
│   │   └── resources/
│   │       └── config.properties
│   └── test/
│       └── java/
│           └── com/
│               └── example/
│                   └── injector/
│                       └── InjectorTest.java
├── pom.xml
└── README.md
```

## Возможности

- **Пользовательская аннотация**: `@AutoInjectable` для пометки внедряемых полей
- **Внедрение на основе рефлексии**: Автоматическое внедрение зависимостей с использованием Java Reflection
- **Конфигурация**: Внешняя конфигурация через properties-файл
- **Unit-тесты**: Полное покрытие тестами с JUnit 5
- **Maven сборка**: Стандартная структура Maven проекта

## Установка и использование

### Предварительные требования
- Java 11 или выше
- Maven 3.6+

### Сборка и запуск

```bash
# Клонирование репозитория
git clone https://github.com/meleema/reflection-lab.git
cd reflection-lab

# Компиляция проекта
mvn clean compile

# Запуск тестов
mvn test

# Запуск приложения
mvn exec:java
```

### Создание JAR
```bash
mvn clean package
java -jar target/reflection-lab-1.0.0.jar
```

## Пример использования

### 1. Определение интерфейсов и реализаций
```java
public interface SomeInterface {
    void doSomething();
}

public class SomeImpl implements SomeInterface {
    public void doSomething() { System.out.println("A"); }
}
```

### 2. Создание класса с зависимостями
```java
public class SomeBean {
    @AutoInjectable
    private SomeInterface field1;
    
    @AutoInjectable
    private SomeOtherInterface field2;
    
    public void foo() {
        field1.doSomething();
        field2.doSomeOther();
    }
}
```

### 3. Настройка реализаций
Отредактируйте `src/main/resources/config.properties`:
```properties
com.example.injector.SomeInterface=com.example.injector.SomeImpl
com.example.injector.SomeOtherInterface=com.example.injector.SODoer
```

### 4. Использование инжектора
```java
SomeBean bean = (new Injector()).inject(new SomeBean());
bean.foo(); // Вывод: A C
```

## Конфигурация

Файл `config.properties` связывает интерфейсы с их реализациями:
```properties
# Формат: полное-имя-интерфейса=полное-имя-реализации
com.example.injector.SomeInterface=com.example.injector.SomeImpl
com.example.injector.SomeOtherInterface=com.example.injector.SODoer
```

Измените `SomeImpl` на `OtherImpl` чтобы увидеть другой вывод:
```properties
com.example.injector.SomeInterface=com.example.injector.OtherImpl
```
Вывод изменится на: `B C`

## Тестирование

Запуск тестов:
```bash
mvn test
```

Тесты покрывают:
- Функциональность внедрения зависимостей
- Обработку ошибок (null объекты, отсутствующая конфигурация)
- Инициализацию полей с аннотациями и без
- Пользовательские файлы конфигурации

## Документация API

Генерация JavaDoc:
```bash
mvn javadoc:javadoc
# Открыть target/site/apidocs/index.html
```

