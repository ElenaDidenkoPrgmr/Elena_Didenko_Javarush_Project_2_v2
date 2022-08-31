package com.javarush.island;

import com.diogonunes.jcolor.Attribute;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static com.diogonunes.jcolor.Ansi.colorize;

public class Dialog {
    private final String WELCOME = "Welcome to simulation \"ISLAND LIVE\"";
    private final String QUESTION = "Do you want to change default size of island? (20 x 100) \n" +
            "[Y/n] (default is \"N\")  : ";
    private final String REQUEST_WIDTH = "Enter width of island: ";
    private final String REQUEST_HEIGHT = "Enter height of island: ";
    private final String REQUEST_SIMULATION_DAYS = "Enter number of simulation days: ";
    private final String WRONG_NUMBERS = "width, height and number of simulation days must be positive numbers";
    private final String GAME_START = "Let's go watch on our simulation!";
    private final int MIN_VALUE = 1;
    private final char YES_LOWER = 'y';
    private final char YES_UPPER = 'Y';


    private PrintStream output;
    private InputStream input;

    public Dialog(PrintStream output, InputStream input) {
        this.output = output;
        this.input = input;
    }

    public void startDialog() {
        output.println(colorize(WELCOME, Attribute.BLUE_TEXT()));
        output.print(colorize(QUESTION, Attribute.BLUE_TEXT()));

        char answer = getAnswer(input);
        Island island = createIsland(answer);
        if (island != null) {
            output.println(colorize(GAME_START,Attribute.BLUE_TEXT()));
            island.lifeCycleIslandWithPhaser(output);
            //island.lifeCycleIslandSimple(output);
        }
    }

    private char getAnswer(InputStream input) {
        Scanner scanner = new Scanner(input);
        return  scanner.next().charAt(0);
    }

    private Island createIsland(char answer) {
        Island island;
        if (answer == YES_LOWER || answer == YES_UPPER) {
            try {
                island = createCustomIsland();
            } catch (RuntimeException e) {
                System.out.println(WRONG_NUMBERS);
                island = null;
            }
        } else {
            island = new Island();
        }
        return island;
    }

    private Island createCustomIsland() {
        int newWidth = inputSetting(REQUEST_WIDTH);
        int newHeight = inputSetting(REQUEST_HEIGHT);
        int newSimulationDays = inputSetting(REQUEST_SIMULATION_DAYS);

        Island island = new Island(newWidth, newHeight, newSimulationDays);
        return island;
    }

    private int inputSetting(String inputMessage) {
        output.print(colorize(inputMessage, Attribute.BLUE_TEXT()));
        return checkInput();
    }
    private int checkInput() {
        Scanner scanner = new Scanner(input);
        int result;
        if (scanner.hasNextInt()) {
            result = scanner.nextInt();
            if (result < MIN_VALUE) {
                throw new RuntimeException();
            }
        } else {
            throw new RuntimeException();
        }
        return result;
    }
}
