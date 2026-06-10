package forth.practice;

import java.util.Scanner;
import static forth.practice.ToDo.Priority.*;

@GenerateClass
@ToDo("оптимізувати код класу")
public class Main {

    @ToDo("знайти інший спосіб визначити сканер")
    static Scanner scanner = new Scanner(System.in);

    static ToDoAnalyzer analyzer = new ToDoAnalyzer();

    public static void main(String[] args) {
        System.setOut(new java.io.PrintStream(System.out, true, java.nio.charset.StandardCharsets.UTF_8));

        try {
            Class<?> generatedClass = Class.forName("forth.practice.GeneratedTodoClass");
            analyzer.analyze(generatedClass);
        } catch (ClassNotFoundException e) {
            System.err.println("Клас не знайдено. Перевірте роботу процесора");
        }

        try {
            Class<?> MainClass = Class.forName("forth.practice.Main");
            analyzer.analyze(MainClass);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println("DAY TRANSLATOR");
        while (true) {
            System.out.print("> Enter day (or 'exit'): ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("EXIT")) {
                System.out.println("Bye-bye!");
                break;
            }
            try {
                Day day = Day.valueOf(input);
                System.out.println(returnDay(day));
            } catch (IllegalArgumentException e) {
                System.out.println("# There's no such a day, try again");
            }
        }
    }

    private static String returnDay(Day day) {
        return switch (day) {
            case MONDAY -> "Понеділок";
            case TUESDAY -> "Вівторок";
            case WEDNESDAY -> "Середа";
            case THURSDAY -> "Четвер";
            case FRIDAY -> "П’ятниця";
            case SATURDAY -> "Субота";
            case SUNDAY -> "Неділя";
        };
    }

    @ToDo(value = "дописати клас", priority = HIGH)
    private static int returnDayNumber(Day day){
        return 0;
    }

    @StrictTaskCheck(description = "Перевіряємо, що метод не void")
    public String validMethod() {
        return "Все працює чудово";
    }
}