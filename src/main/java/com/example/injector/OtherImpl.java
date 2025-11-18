package com.example.injector;

/**
 * Реализация SomeInterface, выводящая "B".
 * 
 * @author Yarovaya Maria
 * @version 1.0
 */
public class OtherImpl implements SomeInterface {
    
    /**
     * Выводит "B" в консоль.
     */
    @Override
    public void doSomething() {
        System.out.println("B");
    }
}