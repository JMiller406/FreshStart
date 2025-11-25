
import Controller.OceanGridController;
import Controller.StatusController;
import Controller.TargetGridController;
import Model.Game;
import View.GameWindow;

public class App {
    public static void main(String[] args) throws Exception {
        // 1. build out the view layer...
        GameWindow gameWindow = new GameWindow("Battleship");

        // 2. build out the Model layer...
        Game game = new Game();

        // 3. Connect Models and Views via Controllers...
        TargetGridController tgc = new TargetGridController(gameWindow.getTargetPanel(), game.getHumanTargetGrid());
        StatusController sc = new StatusController(gameWindow.getStatusPane(), game);
        OceanGridController ogc = new OceanGridController(gameWindow.getOceanPanel(), game.getHumanOceanGrid());

        gameWindow.setVisible(true);
        gameWindow.pack();

        // 4. Start the game!
        game.start();
    }
}
