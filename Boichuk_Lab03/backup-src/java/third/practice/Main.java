package third.practice;

import java.util.Scanner;

/*
    YEAH I HATE COMMENTS IT'S GREAT TO HAVE A PLUGIN THAT DELETES THEM
 */

public class Main {
    public static void main(String[] args) {
        System.out.println("DAY TRANSLATOR");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("> Enter day (or 'exit'): ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("EXIT")) {
                System.out.println("Bye-bye!");
                break;
            }

            // WHAT ARE YOU TRYING TO DO OVER HERE????????
            try {
                Day day = Day.valueOf(input);
                System.out.println(returnDay(day));
            } catch (IllegalArgumentException e) {
                System.out.println("# There's no such a day, try again");
            }
        }
    }

    /*
        wow genius method that returns string...
     */

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
}
