package forth.practice;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ToDoAnalyzer {
    public void analyze(Class<?> clazz) {
        System.out.println("\n=== RUNTIME АНАЛІЗ КЛАСУ: " + clazz.getSimpleName() + " ===");

        if (clazz.isAnnotationPresent(ToDo.class)) {
            ToDo todo = clazz.getAnnotation(ToDo.class);
            System.out.println("[КЛАС] Завдання: " + todo.value() + " | Пріоритет: " + todo.priority());
        }

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(ToDo.class)) {
                ToDo todo = field.getAnnotation(ToDo.class);
                System.out.println("[ПОЛЕ '" + field.getName() + "'] Завдання: " + todo.value() + " | Пріоритет: " + todo.priority());
            }
        }

        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (constructor.isAnnotationPresent(ToDo.class)) {
                ToDo todo = constructor.getAnnotation(ToDo.class);
                System.out.println("[КОНСТРУКТОР] Завдання: " + todo.value() + " | Пріоритет: " + todo.priority());
            }
        }

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(ToDo.class)) {
                ToDo todo = method.getAnnotation(ToDo.class);
                System.out.println("[МЕТОД '" + method.getName() + "'] Завдання: " + todo.value() + " | Пріоритет: " + todo.priority());
            }
        }
    }
}