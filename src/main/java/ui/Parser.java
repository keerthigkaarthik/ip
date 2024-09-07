package ui;

import storage.Storage;
import task.*;

import java.time.format.DateTimeParseException;

/**
 * Parses user inputs and handles commands accordingly in a command-line interface.
 */
public class Parser {

    public static Storage storage;

    /**
     * Parses the input command and delegates to the appropriate handler.
     *
     * @param input The full command input by the user.
     * @param storage The storage instance to interact with file operations.
     * @return response if valid user input.
     * @throws BotException If an unrecognized command is input.
     */
    public static String parse(String input, Storage storage) {
        Parser.storage = storage;
        int spaceIndex = input.indexOf(" ");
        String command = (spaceIndex != -1) ? input.substring(0, spaceIndex).toLowerCase() : input.toLowerCase();
        String arguments = (spaceIndex != -1) ? input.substring(spaceIndex + 1).trim() : "";

        String response;

        try {
            switch (command) {
            case "mark":
            case "unmark":
                response = handleMarkUnmark(command, arguments);
                break;
            case "todo":
                response = handleToDo(arguments);
                break;
            case "deadline":
                response = handleDeadline(arguments);
                break;
            case "event":
                response = handleEvent(arguments);
                break;
            case "list":
                response = handleList();
                break;
            case "delete":
                response = handleDelete(arguments);
                break;
            case "bye":
                response = handleBye();
                break;
            case "find":
                response = handleFind(arguments);
                break;
            default:
                throw new BotException("I'm sorry, I don't recognize that command.");
            }
        } catch (BotException e) {
            response = e.getMessage();
        }
        return response;
    }

    private static String handleFind(String arguments) throws BotException{
        String msg = "";
        if (arguments.isEmpty()) throw new BotException("Please provide a search term");
        TaskList searchResultList = TaskList.mainTaskList.tasksContainingTerm(arguments);
        msg += searchResultList.printList();
        if (searchResultList.getNumTasks() == 1) {
            msg += String.format("There is 1 task containing the word \"%s\".\n", arguments);
        } else {
            msg += String.format("There are %s tasks containing the word \"%s\".\n", searchResultList.getNumTasks(), arguments);
        }

        return msg;
    }

    private static String handleMarkUnmark(String command, String arguments) throws BotException {
        String msg = "";
        if (arguments.isEmpty()) throw new BotException("Please provide a task number.");
        int taskIndex = Integer.parseInt(arguments) - 1;
        if (taskIndex < 0 || taskIndex >= TaskList.mainTaskList.getNumTasks()) throw new BotException("That task does not exist!");

        if (command.equals("mark")) {
            TaskList.mainTaskList.markTask(taskIndex);
            msg += "Nicely done! Keep it up!\n";
        } else {
            TaskList.mainTaskList.unmarkTask(taskIndex);
            msg += "Sure, I'll uncheck that task!\n";
        }
        msg += Parser.handleList();

        return msg;
    }

    private static String handleToDo(String arguments) throws BotException {
        String msg = "";
        if (arguments.isEmpty()) throw new BotException("Please provide a task description.");
        new ToDo(arguments);
        msg += UI.taskAddedMsg();
        return msg;
    }

    private static String handleDeadline(String arguments) throws BotException {
        String msg = "";
        int byIndex = arguments.indexOf(" /by ");
        if (byIndex == -1) throw new BotException("Please format your instructions correctly. E.g., deadline [task] /by [MMddyyyy HHmm]");
        String taskDescription = arguments.substring(0, byIndex);
        String deadline = arguments.substring(byIndex + 5);
        try {
            new Deadline(taskDescription, deadline);
            msg += UI.taskAddedMsg();
        } catch (DateTimeParseException e) {
            throw new BotException("I'm sorry, I have some trouble understanding the deadline. " +
                    "Please ensure you have formatted it correctly. [MMddyyyy HHmm]");
        }

        return msg;
    }

    private static String handleEvent(String arguments) throws BotException {
        String msg = "";
        int fromIndex = arguments.indexOf(" /from ");
        int toIndex = arguments.indexOf(" /to ");
        if (fromIndex == -1 || toIndex == -1) throw new BotException("Please format your instructions correctly. E.g., event [task] /from [MMddyyyy HHmm] /to [MMddyyyy HHmm]");
        String taskDescription = arguments.substring(0, fromIndex);
        String eventStart = arguments.substring(fromIndex + 7, toIndex);
        String eventEnd = arguments.substring(toIndex + 5);
        try {
            new Event(taskDescription, eventStart, eventEnd);
            msg += UI.taskAddedMsg();
        } catch (DateTimeParseException e){
            throw new BotException("I had some trouble understanding your starting and/or ending time. " +
                    "Please ensure you have formatted it correctly. [MMddyyyy HHmm]");
        }

        return msg;
    }

    static String handleList() {
        String msg = "";
        msg += TaskList.mainTaskList.printList();
        msg += String.format("You have %s tasks in your list.\n", TaskList.mainTaskList.getNumTasks());

        return msg;
    }

    static String handleDelete(String arguments) throws BotException {
        String msg = "";
        if (arguments.isEmpty()) throw new BotException("Please provide a task number.");
        int taskIndex = Integer.parseInt(arguments) - 1;
        if (taskIndex < 0 || taskIndex >= TaskList.mainTaskList.getNumTasks()) throw new BotException("That task does not exist!");
        TaskList.mainTaskList.deleteTask(taskIndex);
        msg += "Got it! I've removed that task for you.\n";
        msg += Parser.handleList();

        return msg;
    }

    public static String handleBye() {
        String msg = "";
        msg += ("Bye. Hope to see you again soon!\n");
        storage.saveData();
        return msg;
    }

}
