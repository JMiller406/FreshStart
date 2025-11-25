package Model;

public class AutomaticShipFactory extends ShipFactory {

    @Override
    protected Coordinate getStartCoordinate(Ship ship) throws Exception {
        return Coordinate.randomCoordinate();
    }

    @Override
    protected Direction getStartDirection(Ship ship) throws Exception {
        return Direction.randomDirection();
    }   
}
