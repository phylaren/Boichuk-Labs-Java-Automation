package forth.practice;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeKind;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.Writer;
import java.util.Set;

@SupportedAnnotationTypes({
        "forth.practice.Generateclass",
        "forth.practice.StrictTaskCheck"
})
@SupportedSourceVersion(SourceVersion.RELEASE_24)
public class ToDoProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Messager messager = processingEnv.getMessager();

        for (Element element : roundEnv.getElementsAnnotatedWith(StrictTaskCheck.class)) {
            if (element.getKind() != ElementKind.METHOD) {
                messager.printMessage(Diagnostic.Kind.ERROR, "@StrictTaskCheck можна використовувати лише над методами!", element);
                continue;
            }

            ExecutableElement method = (ExecutableElement) element;

            if (method.getReturnType().getKind() == TypeKind.VOID) {
                messager.printMessage(Diagnostic.Kind.ERROR, "Помилка компіляції: Метод '" + method.getSimpleName() + "' з анотацією @StrictTaskCheck не може повертати void!", method);
            } else {
                messager.printMessage(Diagnostic.Kind.NOTE, "Перевірка @StrictTaskCheck успішно пройдена для: " + method.getSimpleName());
            }
        }

        for (Element element : roundEnv.getElementsAnnotatedWith(GenerateClass.class)) {
            if (element.getKind() != ElementKind.CLASS) {
                messager.printMessage(Diagnostic.Kind.ERROR, "Анотацію @GenerateClass можна застосовувати ЛИШЕ до класів!", element);
                continue;
            }

            try {
                Filer filer = processingEnv.getFiler();
                JavaFileObject file = filer.createSourceFile("forth.practice.GeneratedTodoClass");

                try (Writer writer = file.openWriter()) {
                    writer.write("package forth.practice;\n\n");

                    writer.write("@ToDo(value = \"Головний клас реєстру задач\", priority = ToDo.Priority.HIGH)\n");
                    writer.write("public class GeneratedTodoClass {\n\n");


                    writer.write("    @ToDo(value = \"Зберегти конфігурацію\", priority = ToDo.Priority.HIGH)\n");
                    writer.write("    public String criticalTask;\n\n");

                    writer.write("    @ToDo(value = \"Конструктор реєстру\", priority = ToDo.Priority.MEDIUM)\n");
                    writer.write("    public GeneratedTodoClass() {\n");
                    writer.write("        this.criticalTask = \"CRITICAL_INIT\";\n");
                    writer.write("    }\n\n");

                    writer.write("    @ToDo(value = \"Обробити чергу повідомлень\", priority = ToDo.Priority.LOW)\n");
                    writer.write("    public String processQueue() {\n");
                    writer.write("        return \"Черга оброблена\";\n");
                    writer.write("    }\n");

                    writer.write("}\n");
                }
            } catch (Exception e) {
                messager.printMessage(Diagnostic.Kind.ERROR, "Критична помилка запису файлу GeneratedTodoClass: " + e.getMessage());
            }
        }
        return true;
    }
}