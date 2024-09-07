package storage;

import task.Deadline;
import task.Event;
import task.TaskList;
import task.ToDo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * The Storage class handles loading and saving data to a specified file path.
 * It supports operations to clear, save, and load data.
 * Hello!!
 */
public class Storage {
    private String filePath;

    /**
     * Constructs a Storage object with the specified file path.
     *
     * @param filePath The path to the file used for storing data.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Sets the file path to a new value.
     *
     * @param filePath The new file path.
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Gets the current file path.
     *
     * @return The current file path.
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Clears the contents of the save file.
     */
    public void clearSave() {
        try (FileWriter saveFile = new FileWriter(filePath)) {
            saveFile.write("");
        } catch (IOException e) {
            System.out.println("There was an error clearing your saved data. Details can be found below.");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Saves the current tasks to the save file.
     */
    public void saveData() {
        try (FileWriter saveFile = new FileWriter(filePath)) {
            int numTasks = TaskList.mainTaskList.getNumTasks();
            for (int i = 0; i < numTasks; i++) {
                saveFile.write(TaskList.mainTaskList.getTaskFileFormat(i));
                if (i != numTasks - 1) {
                    saveFile.write("\n");
                }
            }
        } catch (IOException e) {
            System.out.println("There was an error saving your data. Details can be found below.");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Loads tasks from the save file.
     */
    public void loadData() {
        try (Scanner reader = new Scanner(new File(filePath))) {
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                String taskType = data.substring(0, 1);
                String[] taskDetails = data.split(" \\| ");
                switch (taskType) {
                case "E" -> Event.load(taskDetails);
                case "D" -> Deadline.load(taskDetails);
                case "T" -> ToDo.load(taskDetails);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while loading the data.");
            System.out.println(e.getMessage());
        }
    }
}
