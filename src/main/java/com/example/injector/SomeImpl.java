package com.example.injector;

/**
 * Реализация SomeInterface, выводящая "A".
 * 
 * @author Yarovaya Maria
 * @version 1.0
 */
public class SomeImpl implements SomeInterface {
    
    /**
     * Выводит "A" в консоль.
     */
    @Override
    public void doSomething() {
        System.out.println("A");
    }
}