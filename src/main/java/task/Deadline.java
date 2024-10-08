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
        super.setType(TaskType.DEADLINE);
        try {
            this.deadline = LocalDateTime.parse(deadlineStr, Task.TO_SELF_FORMATTER);
        } catch (DateTimeParseException exception) {
            TaskList.mainTaskList.deleteTask(TaskList.mainTaskList.getNumTasks() - 1);
            System.out.println(exception.getMessage());
            throw exception;
        }
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

    /**
     * Gets the deadline of the task.
     *
     * @return The LocalDateTime of the deadline.
     */
    public LocalDateTime getDeadline() {
        return deadline;
    }

    /**
     * Formats the deadline based on the target reader.
     *
     * @param target The intended reader (USER or BOB).
     * @return A formatted string representation of the deadline.
     */
    private String getDeadlineFormatted(ReadBy target) {
        if (target == ReadBy.BOB) {
            return this.deadline.format(Task.TO_SELF_FORMATTER);
        } else {
            return this.deadline.format(Task.TO_USER_FORMATTER);
        }
    }

    /**
     * Returns a string representation of the deadline task formatted for saving to a file.
     * The format includes the task type, completion status, task description, and deadline, separated by " | ".
     *
     * @return The formatted string for saving the deadline task to file.
     */
    @Override
    public String saveFileFormat() {
        String status = this.isMarkedAsCompleted() ? "1 | " : "0 | ";
        return "D | " + status + this.getDescription() + " | " + getDeadlineFormatted(ReadBy.BOB);
    }

    /**
     * Returns a string representation of the deadline task.
     *
     * @return A string representation of the task with its deadline.
     */
    @Override
    public String toString() {
        return super.toString() + " (By: " + getDeadlineFormatted(ReadBy.USER) + ")";
    }
}
