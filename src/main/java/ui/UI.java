package ui;

import task.TaskList;

import java.util.Scanner;

/**
 * Handles user interactions such as displaying messages and receiving input.
 * Hello!
 */
public class UI {

    /**
     * Constructs a UI object for handling user input.
     */
    public UI() {
    }

    /**
     * Displays a welcome message to the user.
     */
    public String showWelcomeMessage() {
        return "Hello! I'm Bob\nWhat can I do for you?\n";
    }

    /**
     * Displays a message indicating a task has been added and prints the list of tasks.
     */
    static String taskAddedMsg() {
        String msg = "";
        msg += "Sure! I've added that in for you.\n";
        msg += TaskList.mainTaskList.printList();
        return msg;
    }

}
