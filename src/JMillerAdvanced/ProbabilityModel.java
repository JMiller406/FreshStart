package JMillerAdvanced;

import java.util.*;

import Model.Coordinate;
import Model.ShotResult;

public class ProbabilityModel {
    
    private static final Map<String, Integer> SHIP_LENGTHS = Map.of(
        "Carrier", 5,
        "Battleship", 4,
        "Cruiser", 3,
        "Submarine", 3,
        "Destroyer", 2
    );
    
    private List<PossibleShip> possibleShips = new ArrayList<>();
    
    private static class PossibleShip {
        String name;
        List<Coordinate> coordinates;
        int hitCount = 0;
        
        PossibleShip(String name, List<Coordinate> coordinates) {
            this.name = name;
            this.coordinates = new ArrayList<>(coordinates);
        }
        
        boolean overlaps(Coordinate coord) {
            return coordinates.contains(coord);
        }
        
        void registerHit() {
            hitCount++;
        }
        
        int getWeight() {
            return hitCount > 0 ? hitCount * 10 : 1;
        }
    }
    
    public ProbabilityModel() {
        initializePossibleShips();
    }
    
    private void initializePossibleShips() {
        for (Map.Entry<String, Integer> entry : SHIP_LENGTHS.entrySet()) {
            String shipName = entry.getKey();
            int length = entry.getValue();
            
            for (int y = 0; y < 10; y++) {
                for (int x = 0; x <= 10 - length; x++) {
                    List<Coordinate> coords = new ArrayList<>();
                    for (int i = 0; i < length; i++) {
                        try {
                            coords.add(new Coordinate(x + i, y));
                        } catch (Exception e) {
                        }
                    }
                    possibleShips.add(new PossibleShip(shipName, coords));
                }
            }
            
            for (int x = 0; x < 10; x++) {
                for (int y = 0; y <= 10 - length; y++) {
                    List<Coordinate> coords = new ArrayList<>();
                    for (int i = 0; i < length; i++) {
                        try {
                            coords.add(new Coordinate(x, y + i));
                        } catch (Exception e) {
                        }
                    }
                    possibleShips.add(new PossibleShip(shipName, coords));
                }
            }
        }
    }
    
    public void update(ShotResult result) {
        Coordinate coord = result.getLocation();
        
        if (result == ShotResult.HIT || result == ShotResult.SUNK) {
            possibleShips.stream()
                .filter(ship -> ship.overlaps(coord))
                .forEach(PossibleShip::registerHit);
            
            if (result == ShotResult.SUNK) {
                handleSunk(result.getShipName(), coord);
            }
        } else if (result == ShotResult.MISS) {
            possibleShips.removeIf(ship -> ship.overlaps(coord));
        }
    }
    
    private void handleSunk(String shipName, Coordinate lastHit) {
        possibleShips.removeIf(ship -> ship.name.equals(shipName));
    }
    
    public Coordinate getBestShot(Set<Coordinate> shotsTaken) {
        Map<Coordinate, Integer> probabilityMap = new HashMap<>();
        
        for (PossibleShip ship : possibleShips) {
            int weight = ship.getWeight();
            for (Coordinate coord : ship.coordinates) {
                if (!shotsTaken.contains(coord)) {
                    probabilityMap.merge(coord, weight, Integer::sum);
                }
            }
        }
        
        return probabilityMap.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElseGet(() -> {
                for (int x = 0; x < 10; x++) {
                    for (int y = 0; y < 10; y++) {
                        try {
                            Coordinate coord = new Coordinate(x, y);
                            if (!shotsTaken.contains(coord)) {
                                return coord;
                            }
                        } catch (Exception e) {
                        }
                    }
                }
                return null;
            });
    }
    
    public int getRemainingPossibilities() {
        return possibleShips.size();
    }
}
