package task;

import ui.BotException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * Manages a list of tasks, providing operations to manipulate and query tasks.
 */
public class TaskList {

    public static final TaskList mainTaskList = new TaskList();
    private final ArrayList<Task> tasks;

    /**
     * Initializes a new empty list of tasks.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the task list.
     *
     * @param task The task to add.
     */
    public void addTask(Task task) {
        this.tasks.add(task);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The number of tasks.
     */
    public int getNumTasks() {
        return this.tasks.size();
    }

    /**
     * Prints all tasks in the list with their indices.
     *
     * @return A string containing all tasks.
     */
    public String printList() {
        StringBuilder msg = new StringBuilder();
        int index = 1;
        for (Task task : this.tasks) {
            msg.append(String.format("     %d. %s\n", index, task));
            index++;
        }
        msg.append("\n");
        return msg.toString();
    }

    /**
     * Finds and returns a new TaskList containing all tasks that include the specified term.
     *
     * @param term The term to search for within task descriptions.
     * @return A TaskList of all tasks containing the term.
     */
    public TaskList tasksContainingTerm(String term) {
        TaskList result = new TaskList();
        for (Task task : this.tasks) {
            if (task.hasTerm(term)) {
                result.addTask(task);
            }
        }
        return result;
    }

    /**
     * Marks a task as done.
     *
     * @param index The index of the task to mark as done.
     */
    public void markTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            this.tasks.get(index).markAsDone();
        }
    }

    /**
     * Marks a task as not done.
     *
     * @param index The index of the task to mark as not done.
     */
    public void unmarkTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            this.tasks.get(index).markAsUndone();
        }
    }

    /**
     * Deletes a task from the list.
     *
     * @param index The index of the task to be deleted.
     */
    public void deleteTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            this.tasks.remove(index);
        }
    }

    /**
     * Returns the formatted save string for a task.
     *
     * @param index The index of the task.
     * @return The formatted save string.
     */
    public String getTaskFileFormat(int index) {
        if (index >= 0 && index < tasks.size()) {
            return tasks.get(index).saveFileFormat();
        }
        return null;
    }

    /**
     * Retrieves a task based on its index.
     *
     * @param index The index of the task.
     * @return The task at the specified index.
     * @throws BotException If the task does not exist.
     */
    public Task getTask(int index) throws BotException {
        if (index >= 0 && index < tasks.size()) {
            return this.tasks.get(index);
        }
        throw new BotException("Task does not exist!");
    }

    /**
     * Returns a TaskList containing tasks with upcoming deadlines or events within the next two days.
     *
     * @return A TaskList of upcoming tasks.
     */
    public static TaskList getUpcomingTasks() {
        LocalDateTime now = LocalDateTime.now();
        TaskList results = new TaskList();
        for (Task t : TaskList.mainTaskList.tasks) {
            if (t.getType() == Task.TaskType.TODO) {
                continue;
            }
            LocalDateTime start;
            if (t.getType() == Task.TaskType.EVENT) {
                Event tsk = (Event) t;
                start = tsk.getStartTime();
            } else {
                Deadline tsk = (Deadline) t;
                start = tsk.getDeadline();
            }

            if (now.plus(2, ChronoUnit.DAYS).isAfter(start)) {
                results.addTask(t);
            }
        }

        return results;
    }

    /**
     * Clears all tasks from the list.
     */
    public void clearTasks() {
        tasks.clear();
    }
}

