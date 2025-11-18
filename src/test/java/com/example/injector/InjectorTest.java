package com.example.injector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit тесты для класса Injector.
 * 
 * @author Yarovaya Maria
 * @version 1.0
 */
class InjectorTest {
    
    private Injector injector;
    
    @BeforeEach
    void setUp() {
        injector = new Injector();
    }
    
    @Test
    @DisplayName("Тест внедрения зависимостей")
    void testInjectDependencies() {
        SomeBean bean = new SomeBean();
        
        assertNull(bean.getField1(), "Field1 should be null before injection");
        assertNull(bean.getField2(), "Field2 should be null before injection");
        
        SomeBean injectedBean = injector.inject(bean);
        
        assertNotNull(injectedBean.getField1(), "Field1 should not be null after injection");
        assertNotNull(injectedBean.getField2(), "Field2 should not be null after injection");
        
        assertTrue(injectedBean.getField1() instanceof SomeImpl);
        assertTrue(injectedBean.getField2() instanceof SODoer);
    }
    
    @Test
    @DisplayName("Тест работы методов после внедрения")
    void testInjectedBeanFunctionality() {
        SomeBean bean = injector.inject(new SomeBean());
        assertDoesNotThrow(bean::foo);
    }
    
    @Test
    @DisplayName("Тест внедрения в null объект")
    void testInjectNullObject() {
        assertThrows(IllegalArgumentException.class, () -> injector.inject(null));
    }
    
    @Test
    @DisplayName("Тест с пользовательским файлом конфигурации")
    void testCustomPropertiesFile() {
        Injector customInjector = new Injector("config.properties");
        SomeBean bean = customInjector.inject(new SomeBean());
        
        assertNotNull(bean.getField1());
        assertNotNull(bean.getField2());
    }
    
    @Test
    @DisplayName("Тест отсутствующего файла конфигурации")
    void testMissingPropertiesFile() {
        assertThrows(RuntimeException.class, () -> new Injector("missing.properties"));
    }
    
    @Test
    @DisplayName("Тест инициализации поля с аннотацией")
    void testAutoInjectableFieldInitialization() {
        SomeBean bean = injector.inject(new SomeBean());
        
        assertNotNull(bean.getField1());
        assertNotNull(bean.getField2());
    }
    
    @Test
    @DisplayName("Тест отсутствия инициализации поля без аннотации")
    void testNonAutoInjectableFieldNotInitialized() {
        class TestClass {
            @SuppressWarnings("unused")
            private SomeInterface nonInjectedField;
            @AutoInjectable
            private SomeInterface injectedField;
        }
        
        TestClass testObj = injector.inject(new TestClass());
        assertNotNull(testObj.injectedField);
    }
}