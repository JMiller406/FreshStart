package Model;

import java.util.ArrayList;
import java.util.List;

public class OceanGrid extends Grid {
    private List<Ship> ships = new ArrayList<Ship>();

    public OceanGrid(){
        super();
    }

    public ShotResult receiveShot(Coordinate location){
        Cell cell = cellAtLocation(location);

        // shot lands on empty cell
        if(cell.getState() == CellState.EMPTY){
            cell.setState(CellState.MISS);
            ShotResult result = ShotResult.MISS;
            result.setLocation(location);
            notifyListeners();
            return result;
        }

        // shot lands on ship
        if(cell.getState() == CellState.OCCUPIED){
            cell.setState(CellState.HIT);
            cell.getShip().registerHit(location);
            if(cell.getShip().isSunk()){
                ShotResult result = ShotResult.SUNK;
                result.setLength(cell.getShip().getLength());
                result.setLocation(location);
                result.setShipName(cell.getShip().getName());
                notifyListeners();
                return result;
            } else {
                ShotResult result = ShotResult.HIT;
                result.setLocation(location);
                notifyListeners();
                return result;
            }
        }
        return ShotResult.INVALID;
    }

    public void placeShips(List<Ship> ships){
        for(Ship ship : ships){
            this.ships.add(ship);
            for(Coordinate location: ship.getCoordinates()){
                cellAtLocation(location).setState(CellState.OCCUPIED);
                cellAtLocation(location).setShip(ship);
            }
        } 
        // notify any listeners (views/controllers) so they can render the ships
        notifyListeners();
    }

    public boolean shipsAreSunk(){
        return ships.stream().allMatch(ship -> ship.isSunk());
        // old way
        /* 
        for(Ship ship : ships){
            if(!ship.isSunk()){
                return false;
            }
        }
        return true;
        */
    }
}
