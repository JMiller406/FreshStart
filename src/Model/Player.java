package Model;

public interface Player {
    public String getName();
    public void takeShot();
    public ShotResult receiveShot(Coordinate shot);
    public void receiveShotResult(ShotResult shotResult);
    public boolean shipsAreSunk();
    public void placeShips();
}
