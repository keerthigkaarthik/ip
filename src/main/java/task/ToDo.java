package task;

import java.util.Objects;

/**
 * Represents a to-do task without any specific time constraints.
 */
public class ToDo extends Task {

    /**
     * Constructs a new ToDo task with the specified description.
     *
     * @param description The description of the to-do task.
     */
    public ToDo(String description) {
        super(description);
        this.type = "[T]";
    }

    /**
     * Loads a ToDo task from a formatted string array obtained from splicing the save file
     * during Storage's load method.
     *
     * @param properties The string array containing the task properties.
     */
    public static void load(String[] properties) {
        ToDo newTodo = new ToDo(properties[2]);
        if (Objects.equals(properties[1], "1")) {
            newTodo.markAsDone();
        }
    }

    /**
     * Returns a string representation of the to-do task formatted for saving to a file.
     *
     * @return The formatted string for file saving.
     */
    @Override
    public String saveFileFormat() {
        String status = this.isCompleted() ? "1 | " : "0 | ";
        return "T | " + status + this.getDescription();
    }
}
