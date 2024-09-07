package ui;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.TaskList;
import task.ToDo;

/**
 * Test class for Parser's handleDelete method to ensure it functions correctly
 * with valid inputs, and throws the correct exceptions with invalid inputs.
 */
public class ParserTest {

    /**
     * Sets up the environment before each test, initializing the TaskList with predefined tasks.
     */
    @BeforeEach
    public void setUp() {
        TaskList.mainTaskList.clearTasks(); // Clear the TaskList before each test
        new ToDo("Task 1");
        new ToDo("Task 2");
        new ToDo("Task 3");
    }

    /**
     * Tests the deletion of a valid task ensuring the task count is decreased correctly.
     */
    @Test
    public void testHandleDeleteValidTask() {
        try {
            Parser.handleDelete("2"); // Should delete the second task
            assertEquals(2, TaskList.mainTaskList.getNumTasks(), "After deletion, task count should decrease.");
        } catch (BotException e) {
            fail("Exception should not have been thrown for a valid task deletion.");
        }
    }

    /**
     * Tests the deletion command with an empty argument, expecting an exception.
     */
    @Test
    public void testHandleDeleteEmptyArgument() {
        Exception exception = assertThrows(BotException.class, () -> Parser.handleDelete(""));
        assertEquals("Please provide a task number.", exception.getMessage());
    }

    /**
     * Tests the deletion command with an invalid task number, expecting an exception.
     */
    @Test
    public void testHandleDeleteInvalidTaskNumber() {
        Exception exception = assertThrows(BotException.class, () -> Parser.handleDelete("10"));
        assertEquals("That task does not exist!", exception.getMessage());
    }
}
