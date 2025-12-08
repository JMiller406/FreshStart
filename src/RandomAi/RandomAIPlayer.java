package RandomAi;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// YOU'LL NEED TO UPDATE THESE IMPORTS TO MATCH YOUR PROJECT
import Model.Player;
import Model.Coordinate;
import Model.ShotResult;
import Model.OceanGrid;
import Model.Ship;
import Model.ShotDelegate;
import Model.AutomaticShipFactory;

public class RandomAIPlayer implements Player, Serializable {

	private List<Coordinate> shotsToTake = new ArrayList<>();
    private OceanGrid oceanGrid = new OceanGrid();
	private List<Ship> ships = new ArrayList<>();
	private AutomaticShipFactory factory = new AutomaticShipFactory();
    private ShotDelegate shotDelegate;

	public RandomAIPlayer(ShotDelegate delegate) {
        shotDelegate = delegate;
        // populate shots to take
        for(int x = 0; x < 10; x++){
            for(int y = 0; y < 10; y++){
                try{
                    shotsToTake.add(new Coordinate(x,y));
                } catch (Exception e){
                    // ignore - shouldn't happen
                }
            }
        }
        Collections.shuffle(shotsToTake);
	}

	@Override
    public void placeShips() {
        // persist the placed ships locally so shipsAreSunk() can report correctly
        ships = factory.getShips();
        oceanGrid.placeShips(ships);
    }

    @Override
    public void takeShot() {
        Coordinate newShot = shotsToTake.remove(0); 
        shotDelegate.handleShot(newShot, this);
    }

    @Override
    public ShotResult receiveShot(Coordinate shot) {
        return oceanGrid.receiveShot(shot);
    }

    @Override
    public String getName() {
        return "Rando Loser 1.0";
    }

    @Override
    public void receiveShotResult(ShotResult result) {
        // This AI doesn't care how things turn out - not too smart!
    }

    @Override
    public boolean shipsAreSunk() {
        // rely on oceanGrid (which knows the placed ships) to determine sunk state
        return oceanGrid.shipsAreSunk();
    }
	
}
