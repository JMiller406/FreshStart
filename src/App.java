import Controller.WindowController;
import Model.Game;
import View.GameWindow;

public class App {
    public static void main(String[] args) throws Exception {
        // 1. build out the view layer...
        GameWindow gameWindow = new GameWindow("Battleship");

        // 2. build out the Model layer...
        Game game = new Game();

        // 3. Connect Models and Views via Controllers...
        WindowController wc = new WindowController(gameWindow, game);


        gameWindow.setVisible(true);
        gameWindow.pack();

        // 4. Start the game!
        game.start();
    }
}
