package Model;

public class ManualShipFactory extends ShipFactory {

    @Override
    protected Coordinate getStartCoordinate(Ship ship) throws Exception {
        return new Coordinate(ConsoleHelper.getInput(String.format("Please enter start coordinate for %s", ship.getName())));
    }

    @Override
    protected Direction getStartDirection(Ship ship) throws Exception {
        return Direction.equivalentTo(Integer.parseInt(ConsoleHelper.getInput(String.format("Please enter direction for %s:\n  1. North\n  2. South\n  3. East\n  4. West\nChoose one: ", ship.getName()))));
    }
}
