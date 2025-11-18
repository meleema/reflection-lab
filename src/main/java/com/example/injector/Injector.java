package com.example.injector;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * Инжектор зависимостей для автоматической инициализации полей с аннотацией @AutoInjectable.
 * 
 * @author Yarovaya Maria
 * @version 1.0
 */
public class Injector {
    
    private static final String PROPERTIES_FILE = "config.properties";
    private final Properties properties;
    
    /**
     * Конструктор по умолчанию.
     */
    public Injector() {
        this.properties = loadProperties();
    }
    
    /**
     * Конструктор с пользовательским файлом настроек.
     */
    public Injector(String propertiesFile) {
        this.properties = loadProperties(propertiesFile);
    }
    
    /**
     * Внедряет зависимости в объект.
     */
    public <T> T inject(T target) {
        if (target == null) {
            throw new IllegalArgumentException("Target object cannot be null");
        }
        
        Class<?> targetClass = target.getClass();
        Field[] fields = targetClass.getDeclaredFields();
        
        for (Field field : fields) {
            if (field.isAnnotationPresent(AutoInjectable.class)) {
                injectField(target, field);
            }
        }
        
        return target;
    }
    
    /**
     * Внедряет зависимость в конкретное поле.
     */
    private void injectField(Object target, Field field) {
        Class<?> fieldType = field.getType();
        
        if (!fieldType.isInterface()) {
            throw new IllegalStateException(
                "Field " + field.getName() + " must be an interface type"
            );
        }
        
        String implementationClassName = properties.getProperty(fieldType.getName());
        if (implementationClassName == null || implementationClassName.trim().isEmpty()) {
            throw new IllegalStateException(
                "No implementation specified for interface: " + fieldType.getName()
            );
        }
        
        try {
            Class<?> implementationClass = Class.forName(implementationClassName.trim());
            Object implementationInstance = implementationClass.getDeclaredConstructor().newInstance();
            
            if (!fieldType.isAssignableFrom(implementationClass)) {
                throw new IllegalStateException(
                    "Class " + implementationClassName + " does not implement " + fieldType.getName()
                );
            }
            
            field.setAccessible(true);
            field.set(target, implementationInstance);
            
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                "Implementation class not found: " + implementationClassName, e
            );
        } catch (InstantiationException | IllegalAccessException | 
                 java.lang.reflect.InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(
                "Failed to create instance of: " + implementationClassName, e
            );
        }
    }
    
    /**
     * Загружает настройки из properties файла.
     */
    private Properties loadProperties() {
        return loadProperties(PROPERTIES_FILE);
    }
    
    /**
     * Загружает настройки из указанного файла.
     */
    private Properties loadProperties(String propertiesFile) {
        Properties props = new Properties();
        
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(propertiesFile)) {
            if (input == null) {
                throw new RuntimeException("Properties file not found: " + propertiesFile);
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file: " + propertiesFile, e);
        }
        
        return props;
    }
}