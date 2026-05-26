package org.example;

import org.example.HardcodedTranslations.Day;

import java.util.Scanner;

//comments


/*
 cool big comments
 */
public class MainForCopy {
    public static void main(String[] args) {
        System.out.println("DAY TRANSLATOR");
        Scanner scanner = new Scanner(System.in);


        /**
         * cool big comments like this
         */
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
}
