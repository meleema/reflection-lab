package com.example.injector;

/**
 * Класс с зависимостями для автоматического внедрения.
 * Демонстрирует работу инжектора зависимостей.
 * 
 * @author Yarovaya Maria
 * @version 1.0
 */
public class SomeBean {
    
    @AutoInjectable
    private SomeInterface field1;
    
    @AutoInjectable
    private SomeOtherInterface field2;

    /**
     * Выполняет методы внедренных зависимостей.
     */
    public void foo() {
        field1.doSomething();
        field2.doSomeOther();
    }

    /**
     * Возвращает первую зависимость.
     */
    public SomeInterface getField1() {
        return field1;
    }

    /**
     * Возвращает вторую зависимость.
     */
    public SomeOtherInterface getField2() {
        return field2;
    }
}