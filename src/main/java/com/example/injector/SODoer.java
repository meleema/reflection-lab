package com.example.injector;

/**
 * Реализация SomeOtherInterface, выводящая "C".
 * 
 * @author Yarovaya Maria
 * @version 1.0
 */
public class SODoer implements SomeOtherInterface {
    
    /**
     * Выводит "C" в консоль.
     */
    @Override
    public void doSomeOther() {
        System.out.println("C");
    }
}
