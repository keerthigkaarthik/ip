package UI;

import Task.TaskList;

import java.util.Scanner;

/**
 * Handles user interactions such as displaying messages and receiving input.
 */
public class UI {
    private final Scanner input;

    /**
     * Constructs a UI object for handling user input.
     */
    public UI() {
        input = new Scanner(System.in);
    }

    /**
     * Displays a welcome message to the user.
     */
    public void showWelcomeMessage() {
        System.out.println("Hello! I'm Bob\nWhat can I do for you?");
        System.out.println();
    }

    /**
     * Displays a message indicating a task has been added and prints the list of tasks.
     */
    static void taskAddedMsg() {
        System.out.println("Sure! I've added that in for you.");
        TaskList.mainTaskList.printList();
    }

    /**
     * Displays a separator line to the console.
     */
    public void showSeparator() {
        System.out.println("--------------------------------------------------");
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to display.
     */
    public void showErrorMessage(String message) {
        System.out.println(message);
    }

    /**
     * Displays a separator line followed by a newline to the console.
     */
    public void showNewLineSeparator() {
        System.out.println("--------------------------------------------------\n");
    }

    /**
     * Gets the user's input from the console.
     *
     * @return The user's input as a string.
     */
    public String getUserInput() {
        return input.nextLine();
    }
}
