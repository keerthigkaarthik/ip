package task;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * Represents a task with a specific deadline.
 */
public class Deadline extends Task {
    public final LocalDateTime deadline;

    /**
     * Constructs a Deadline task with a description and a deadline.
     *
     * @param description The description of the deadline task.
     * @param deadlineStr The deadline date and time in a string format.
     * @throws DateTimeParseException If the deadline string cannot be parsed.
     */
    public Deadline(String description, String deadlineStr) throws DateTimeParseException {
        super(description);
        try {
            this.deadline = LocalDateTime.parse(deadlineStr, Task.toSelfFormatter);
        } catch (DateTimeParseException exception) {
            TaskList.mainTaskList.deleteTask(TaskList.mainTaskList.getNumTasks() - 1);
            System.out.println(exception.getMessage());
            throw exception;
        }
        this.type = "[D]";
    }

    /**
     * Loads a Deadline task from a formatted string array obtained from splicing the save file
     * during Storage's load method.
     *
     * @param properties The string array containing the task properties.
     */
    public static void load(String[] properties) {
        try {
            Deadline newDeadline = new Deadline(properties[2], properties[3]);
            if (Objects.equals(properties[1], "1")) {
                newDeadline.markAsDone();
            }
        } catch (DateTimeParseException e) {
            System.out.println("Failed to parse date for loaded deadline: " + e.getMessage());
        }
    }

    private String getDeadline(String type) {
        if (type.equals("in")) {
            return this.deadline.format(Task.toSelfFormatter);
        } else if (type.equals("out")) {
            return this.deadline.format(Task.toUserFormatter);
        } else {
            return "Incorrect deadline format type specified.";
        }
    }

    /**
     * Returns a string representation of the deadline task formatted for saving to a file.
     * The format includes the task type, completion status, task description, and deadline, separated by " | ".
     * This string is used for persisting the task data in a structured text file, allowing for easy reloading of tasks.
     *
     * @return The formatted string for saving the deadline task to file.
     */
    @Override
    public String saveFileFormat() {
        String status = this.isCompleted() ? "1 | " : "0 | ";
        return "D | " + status + this.getDescription() + " | " + getDeadline("in");
    }

    @Override
    public String toString() {
        return super.toString() + " (By: " + getDeadline("out") + ")";
    }
}
