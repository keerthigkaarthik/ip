package bob;

import storage.Storage;
import ui.UI;
import ui.Parser;
import ui.BotException;

/**
 * The Bob class is the main entry point for the Bob application.
 * It initializes the user interface and storage, and processes user input in a loop.
 * Hello!!
 */
public class Bob {
    public UI ui = new UI();
    public Storage storage = new Storage("src/main/Data/save.txt");

//    /**
//     * The main method that runs the Bob application.
//     *
//     * @param args Command-line arguments.
//     */
//    public static void main(String[] args) {
//        ui.showWelcomeMessage();
//
//        Storage storage = new Storage("src/main/Data/save.txt");
//        storage.loadData();
//
//        boolean continueRunning = true;
//
//        while (continueRunning) {
//            try {
//                String userInput = ui.getUserInput();
//                ui.showSeparator();
//                continueRunning = Parser.parse(userInput, storage);
//            } catch (BotException e) {
//                ui.showErrorMessage(e.getMessage());
//            } finally {
//                ui.showNewLineSeparator();
//            }
//        }
//    }

    public String getResponse(String userInput) {

        return Parser.parse(userInput, this.storage);
    }
}
