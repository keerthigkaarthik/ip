package UI;

import Storage.Storage;
import Task.*;

import java.time.format.DateTimeParseException;

public class Parser {

    public static Storage storage;
    public static boolean parse(String input, Storage storage) throws BotException {
        Parser.storage = storage;
        int spaceIndex = input.indexOf(" ");
        String command = (spaceIndex != -1) ? input.substring(0, spaceIndex).toLowerCase() : input.toLowerCase();
        String arguments = (spaceIndex != -1) ? input.substring(spaceIndex + 1).trim() : "";

        switch (command) {
            case "mark":
            case "unmark":
                handleMarkUnmark(command, arguments);
                break;
            case "todo":
                handleToDo(arguments);
                break;
            case "deadline":
                handleDeadline(arguments);
                break;
            case "event":
                handleEvent(arguments);
                break;
            case "list":
                handleList();
                break;
            case "delete":
                handleDelete(arguments);
                break;
            case "bye":
                handleBye();
                return false;
            case "find":
                handleFind(arguments);
                break;
            default:
                throw new BotException("I'm sorry, I don't recognize that command.");
        }

        return true;
    }

    private static void handleFind(String arguments) throws BotException{
        if (arguments.isEmpty()) throw new BotException("Please provide a search term");
        TaskList searchResultList = TaskList.mainTaskList.tasksContainingTerm(arguments);
        searchResultList.printList();
        if (searchResultList.getNumTasks() == 1) {
            System.out.printf("There is 1 task containing the word \"%s\".\n", arguments);
        }
        System.out.printf("There are %s tasks containing the word \"%s\".\n", searchResultList.getNumTasks(), arguments);
    }

    private static void handleMarkUnmark(String command, String arguments) throws BotException {
        if (arguments.isEmpty()) throw new BotException("Please provide a task number.");
        int taskIndex = Integer.parseInt(arguments) - 1;
        if (taskIndex < 0 || taskIndex >= TaskList.mainTaskList.getNumTasks()) throw new BotException("That task does not exist!");

        if (command.equals("mark")) {
            TaskList.mainTaskList.markTask(taskIndex);
            System.out.println("Nicely done! Keep it up!\n");
            Parser.handleList();
        } else {
            TaskList.mainTaskList.unmarkTask(taskIndex);
            System.out.println("Sure, I'll uncheck that task!\n");
            Parser.handleList();
        }
    }

    private static void taskAddedMsg() {
        System.out.println("Sure! I'll add that in for you.");
        Parser.handleList();
    }
    private static void handleToDo(String arguments) throws BotException {
        if (arguments.isEmpty()) throw new BotException("Please provide a task description.");
        new ToDo(arguments);
        Parser.taskAddedMsg();
    }

    private static void handleDeadline(String arguments) throws BotException {
        int byIndex = arguments.indexOf(" /by ");
        if (byIndex == -1) throw new BotException("Please format your instructions correctly. E.g deadline [task] /by [MMddyyyy HHmm]");
        String taskDescription = arguments.substring(0, byIndex);
        String deadline = arguments.substring(byIndex + 5);
        try {
            new Deadline(taskDescription, deadline);
            Parser.taskAddedMsg();
        } catch (DateTimeParseException e) {
            System.out.println("I'm sorry I have some trouble understanding the deadline. " +
                    "Please ensure you have formatted it correctly. [MMddyyyy HHmm]");
            System.out.println(e.getMessage());
        }
    }

    private static void handleEvent(String arguments) throws BotException {
        int fromIndex = arguments.indexOf(" /from ");
        int toIndex = arguments.indexOf(" /to ");
        if (fromIndex == -1 || toIndex == -1) throw new BotException("Please format your instructions correctly. " +
                "E.g event [task] /from [MMddyyyy HHmm] /to [MMddyyyy HHmm]");
        String taskDescription = arguments.substring(0, fromIndex);
        String eventStart = arguments.substring(fromIndex + 7, toIndex);
        String eventEnd = arguments.substring(toIndex + 5);
        try {
            new Event(taskDescription, eventStart, eventEnd);
            Parser.taskAddedMsg();
        } catch (DateTimeParseException e){
            System.out.println("I had some trouble understanding your starting and or ending time. " +
                    "Please ensure you have formatted it correctly. [MMddyyyy HHmm]");
            System.out.println(e.getMessage());
        }


    }

    private static void handleList() {
        TaskList.mainTaskList.printList();
        System.out.printf("You have %s tasks in your list.\n", TaskList.mainTaskList.getNumTasks());
    }

    private static void handleDelete(String arguments) throws BotException {
        if (arguments.isEmpty()) throw new BotException("Please provide a task number.");
        int taskIndex = Integer.parseInt(arguments) - 1;
        if (taskIndex < 0 || taskIndex >= TaskList.mainTaskList.getNumTasks()) throw new BotException("That task does not exist!");
        TaskList.mainTaskList.deleteTask(taskIndex);
        System.out.println("Got it! I've removed that task for you.");
        System.out.printf("You now have %s tasks in your list.\n", TaskList.mainTaskList.getNumTasks());
    }

    private static void handleBye() {
        System.out.println("Bye. Hope to see you again soon!");
        storage.saveData();
    }
}

