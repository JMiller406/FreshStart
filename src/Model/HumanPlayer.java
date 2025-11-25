package Model;

public class HumanPlayer implements Player, ShotDelegate {
    private String name;
    private OceanGrid oceanGrid = new OceanGrid();
    private TargetGrid targetGrid = new TargetGrid(this);
    private ShipFactory shipFactory;
    private ShotDelegate shotDelegate;

    

    public HumanPlayer(String name, ShipFactory shipFactory, ShotDelegate shotDelegate){
        this.name = name;
        this.shipFactory = shipFactory;
        this.shotDelegate = shotDelegate;
    }

    public void placeShips(){
        oceanGrid.placeShips(shipFactory.getShips());
    }

    public TargetGrid getTargetGrid() {
                return targetGrid;
    }

    public OceanGrid getOceanGrid() {
        return oceanGrid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void takeShot() {
        // wait for player to click the target grid 
    }

    @Override
    public ShotResult receiveShot(Coordinate shot) {
        return oceanGrid.receiveShot(shot);
    }

    @Override
    public void receiveShotResult(ShotResult shotResult) {
        targetGrid.receiveShotResult(shotResult);
    }

    @Override
    public boolean shipsAreSunk() {
        return oceanGrid.shipsAreSunk();
    }

    @Override
    public void handleShot(Coordinate shot, Object sender) {
        shotDelegate.handleShot(shot, this);
    }
}
