package Bob;

import Storage.Storage;
import UI.UI;
import UI.Parser;
import UI.BotException;

/**
 * The Bob class is the main entry point for the Bob application.
 * It initializes the user interface and storage, and processes user input in a loop.
 */
public class Bob {

    /**
     * The main method that runs the Bob application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        UI ui = new UI();
        ui.showWelcomeMessage();

        Storage storage = new Storage("src/main/Data/save.txt");
        storage.loadData();

        boolean continueRunning = true;

        while (continueRunning) {
            try {
                String userInput = ui.getUserInput();
                ui.showSeparator();
                continueRunning = Parser.parse(userInput, storage);
            } catch (BotException e) {
                ui.showErrorMessage(e.getMessage());
            } finally {
                ui.showNewLineSeparator();
            }
        }
    }
}
