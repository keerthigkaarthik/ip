package task;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * Represents an event task with a specific start and end time.
 */
public class Event extends Task {
    private final LocalDateTime start;
    private final LocalDateTime end;

    /**
     * Constructs an Event task with a description, start time, and end time.
     *
     * @param description The description of the event.
     * @param startStr The start time of the event in string format.
     * @param endStr The end time of the event in string format.
     * @throws DateTimeParseException If the start or end time cannot be parsed.
     */
    public Event(String description, String startStr, String endStr) throws DateTimeParseException {
        super(description);
        try {
            this.start = LocalDateTime.parse(startStr, Task.toSelfFormatter);
            this.end = LocalDateTime.parse(endStr, Task.toSelfFormatter);
        } catch (DateTimeParseException exception) {
            TaskList.mainTaskList.deleteTask(TaskList.mainTaskList.getNumTasks() - 1);
            throw exception;
        }
        this.type = "[E]";
    }

    /**
     * Loads an event task from a formatted string array obtained from splicing the save file
     * during Storage's load method.
     *
     * @param properties The string array containing the task properties.
     */
    public static void load(String[] properties) {
        try {
            Event newEvent = new Event(properties[2], properties[3], properties[4]);
            if (Objects.equals(properties[1], "1")) {
                newEvent.markAsDone();
            }
        } catch (DateTimeParseException e) {
            System.out.println("Failed to parse date for loaded event: " + e.getMessage());
        }
    }

    /**
     * Returns a string representation of the event task formatted for saving to a file.
     * The format includes the task type, completion status, description, start time, and end time, separated by " | ".
     *
     * @return The formatted string for saving the event task to file.
     */
    @Override
    public String saveFileFormat() {
        String status = this.isCompleted() ? "1 | " : "0 | ";
        return "E | " + status + this.getDescription() + " | " + getStart("in") + " | " + getEnd("in");
    }

    private String getStart(String type) {
        if (type.equalsIgnoreCase("in")) {
            return this.start.format(Task.toSelfFormatter);
        } else if (type.equalsIgnoreCase("out")) {
            return this.start.format(Task.toUserFormatter);
        } else {
            return "Invalid time format specified.";
        }
    }

    private String getEnd(String type) {
        if (type.equalsIgnoreCase("in")) {
            return this.end.format(Task.toSelfFormatter);
        } else if (type.equalsIgnoreCase("out")) {
            return this.end.format(Task.toUserFormatter);
        } else {
            return "Invalid time format specified.";
        }
    }

    @Override
    public String toString() {
        return super.toString() + " (From: " + getStart("out") + " To: " + getEnd("out") + ")";
    }
}
