package forth.practice;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface ToDo {

     enum Priority {
        LOW, MEDIUM, HIGH
    }

    String value();
    Priority priority() default Priority.MEDIUM;

}