package com.example;

import com.example.injector.Injector;
import com.example.injector.SomeBean;

/**
 * Демонстрация работы инжектора зависимостей.
 * 
 * @author Yarovaya Maria
 * @version 1.0
 */
public class Main {
    
    /**
     * Точка входа в приложение.
     */
    public static void main(String[] args) {
        System.out.println("=== Dependency Injection Demo ===");
        
        // Создаем и инициализируем bean через инжектор
        SomeBean bean = (new Injector()).inject(new SomeBean());
        
        System.out.println("Calling foo() method:");
        bean.foo();
        
        System.out.println("=== Demo Completed ===");
        
        // Демонстрация смены реализации
        System.out.println("\n=== Changing Implementation ===");
        System.out.println("To test different implementation, change config.properties:");
        System.out.println("com.example.injector.SomeInterface=com.example.injector.OtherImpl");
        System.out.println("This will output 'BC' instead of 'AC'");
    }
}