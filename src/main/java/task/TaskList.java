package task;

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
     * @param task The task to add.
     */
    public void addTask(Task task) {
        this.tasks.add(task);
    }

    /**
     * Returns the number of tasks in the list.
     * @return The number of tasks.
     */
    public int getNumTasks() {
        return this.tasks.size();
    }

    /**
     * Prints all tasks in the list with their indices.
     */
    public String printList() {
        String msg = "";
        int index = 1;
        System.out.println();
        for (Task task : this.tasks) {
            msg += String.format("     %d. %s\n", index, task);
            index++;
        }
        msg += "\n";
        return msg;
    }

    /**
     * Finds and returns a new TaskList containing all tasks that include the specified term.
     * @param term The term to search for within task descriptions.
     * @return A TaskList of all tasks containing the term.
     */
    public TaskList tasksContainingTerm(String term) {
        TaskList result = new TaskList();
        for (Task task : this.tasks) {
            if (task.containsTerm(term)) {
                result.addTask(task);
            }
        }
        return result;
    }

    /**
     * Marks a task as done.
     * @param index The index of the task to mark as done.
     */
    public void markTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            this.tasks.get(index).markAsDone();
        }
    }

    /**
     * Marks a task as not done.
     * @param index The index of the task to mark as not done.
     */
    public void unmarkTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            this.tasks.get(index).markAsUndone();
        }
    }

    /**
     * Deletes a task from the list.
     * @param index The index of the task to be deleted.
     */
    public void deleteTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            this.tasks.remove(index);
        }
    }

    /**
     * Returns the formatted save string for a task.
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
     * @param index The index of the task.
     * @return The task at the specified index.
     */
    public Task getTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            return this.tasks.get(index);
        }
        return null;
    }

    /**
     * Clears all tasks from the list.
     */
    public void clearTasks() {
        tasks.clear();
    }
}
