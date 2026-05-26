package org.example;

import org.example.HardcodedTranslations.Day;

import java.util.Scanner;


// COMENTS


public class MainCopy {
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

            try {
                Day day = Day.valueOf(input);
                System.out.println(returnDay(day));
            } catch (IllegalArgumentException e) {
                System.out.println("# There's no such a day, try again");
            }
        }
    }

    private static String returnDay(Day day){
        return "day";
    }

    
}
