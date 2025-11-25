package Model;

import java.util.ArrayList;
import java.util.List;

import Util.Constants;

import java.util.Collections;

public abstract class ShipFactory {

    protected List<Ship> ships = new ArrayList<>();

    public ShipFactory(){
        Constants.shipSpecifications.forEach((k,v) -> ships.add(new Ship(k, v)));
    }

    protected abstract Coordinate getStartCoordinate(Ship ship) throws Exception;

    protected abstract Direction getStartDirection(Ship ship) throws Exception;

    public List<Ship> getShips(){
        List<Ship> placedShips = new ArrayList<>();
        for(Ship ship : ships){
            List<Coordinate> coords;
            while(true){
                try {
                    Coordinate start = getStartCoordinate(ship);
                    Direction dir = getStartDirection(ship);
                    coords = start.coordsInDirection(ship.getLength(), dir);
                    ship.setCoordinates(coords);
                    if(shipOverlapsExistingShip(ship, placedShips)){
                        // overlap - try again
                        continue;
                    }
                    break;
                } catch (Exception e) {
                    // bad coordinates - try again
                    continue;
                }
            }
            placedShips.add(ship);
        }
        return ships;
    }

    protected boolean shipOverlapsExistingShip(Ship ship, List<Ship> ships){
        for(Ship existingShip : ships){
            List<Coordinate> existingShipCoords = existingShip.getCoordinates();
            for(Coordinate c: ship.getCoordinates()){
                if(existingShipCoords.contains(c)){
                    return true;
                }
            }
        }
        return false;
    }

    public List<Ship> getShipsSortedByName(Order order){
        List<Ship> ships = getShips();
        ships.sort((one, two) -> one.getName().compareTo(two.getName()));
        if(order == Order.DESC){
            Collections.reverse(ships);
        }
        return ships;
    }

    public List<Ship> getShipsSortedByLength(Order order){
        List<Ship> ships = getShips();
        ships.sort((one, two) -> Integer.compare(one.getLength(), two.getLength()));
        if(order == Order.DESC){
            Collections.reverse(ships);
        }
        return ships;
    }
}
