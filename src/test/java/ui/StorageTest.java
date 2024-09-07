package ui;

import storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import task.TaskList;
import task.ToDo;
import task.Deadline;
import task.Event;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Storage class to ensure that saving and loading functionality works as expected.
 */
public class StorageTest {

    private Storage storage;
    private String tempFilePath;

    /**
     * Sets up the environment for each test. Creates a temporary file to use for storage operations.
     */
    @BeforeEach
    public void setUp() throws IOException {
        File tempFile = File.createTempFile("tempTaskFile", ".txt");
        tempFilePath = tempFile.getAbsolutePath();
        storage = new Storage(tempFilePath);

        // Reset the TaskList
        TaskList.mainTaskList.clearTasks();
    }

    /**
     * Cleans up after each test. Deletes the temporary file.
     */
    @AfterEach
    public void tearDown() {
        new File(tempFilePath).delete();
    }

    /**
     * Tests saving and loading tasks to ensure data integrity.
     */
    @Test
    public void testSaveAndLoadDataWithTasks() {
        new ToDo("Task 1");
        new Deadline("Task 2", "01-01-2024 1200");
        new Event("Task 3", "01-01-2024 1200", "01-01-2024 1300");

        storage.saveData();
        TaskList.mainTaskList.clearTasks();
        storage.loadData();

        assertEquals(3, TaskList.mainTaskList.getNumTasks());
        assertEquals("Task 1", TaskList.mainTaskList.getTask(0).getDescription());
        assertEquals("Task 2", TaskList.mainTaskList.getTask(1).getDescription());
        assertEquals("Task 3", TaskList.mainTaskList.getTask(2).getDescription());
    }

    /**
     * Tests the behavior of the save and load operations when no tasks are present.
     */
    @Test
    public void testSaveAndLoadDataWithNoTasks() {
        storage.saveData();
        TaskList.mainTaskList.clearTasks();
        storage.loadData();

        assertEquals(0, TaskList.mainTaskList.getNumTasks());
    }

    /**
     * Tests loading data from a non-existent file to check error handling.
     */
    @Test
    public void testLoadDataFromNonExistentFile() {
        storage.setFilePath("nonExistentFile.txt");
        storage.loadData();
        assertEquals(0, TaskList.mainTaskList.getNumTasks());
    }
}

