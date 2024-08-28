package Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class Deadline extends Task {
    public final LocalDateTime deadline;

    public Deadline(String t, String d) throws DateTimeParseException{
        super(t);
        LocalDateTime deadline;

        try {
            deadline = LocalDateTime.parse(d, Task.toSelfFormatter);
        } catch (DateTimeParseException exception) {
            TaskList.mainTaskList.deleteTask(TaskList.mainTaskList.getNumTasks() - 1);
            throw exception;
        }
        this.type = "[D]";
        this.deadline = deadline;

    }

    public static void load(String[] arr) {
        Deadline newDeadline = new Deadline(arr[2], arr[3]);
        if (Objects.equals(arr[1], "1")) {
            newDeadline.done();
        }
    }

    private String getDeadline(String type) {
        if (type.equals("in")) {
            return this.deadline.format(Task.toSelfFormatter);
        } else if (type.equals("out")) {
            return this.deadline.format(Task.toUserFormatter);
        } else {
            return "something went wrong; deadline time formatter";
        }
    }

    @Override
    public String saveFileFormat() {
        String status;
        if (this.getCompleted()) {
            status = "1 | ";
        } else {
            status = "0 | ";
        }
        return "E | " + status + this.getTask() + " | " + this.getDeadline("in");
    }
    @Override
    public String toString() {
        return super.toString() + " (By: " + this.getDeadline("out") + ")";
    }


}