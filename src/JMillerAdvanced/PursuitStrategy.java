package JMillerAdvanced;

import java.util.*;
import Model.Coordinate;
import Model.ShotResult;

public class PursuitStrategy {
    
    public enum State { HUNT, BRACKET, PURSUE, REVERSE }
    private State currentState = State.HUNT;
    
    private List<Coordinate> bracketShots = new ArrayList<>();
    private List<Coordinate> pursueShots = new ArrayList<>();
    private List<Coordinate> reverseShots = new ArrayList<>();
    private Coordinate firstHit;
    private Coordinate secondHit;
    
    private Set<Coordinate> shotsTaken;
    
    public PursuitStrategy(Set<Coordinate> shotsTaken) {
        this.shotsTaken = shotsTaken;
    }
    
    public State getCurrentState() {
        return currentState;
    }
    
    public List<Coordinate> getBracketShots() {
        return bracketShots;
    }
    
    public List<Coordinate> getPursueShots() {
        return pursueShots;
    }
    
    public List<Coordinate> getReverseShots() {
        return reverseShots;
    }
    
    public void handleHit(ShotResult result) {
        Coordinate hitCoord = result.getLocation();
        
        switch (currentState) {
            case HUNT:
                firstHit = hitCoord;
                currentState = State.BRACKET;
                populateBracketShots(hitCoord);
                break;
            case BRACKET:
                secondHit = hitCoord;
                currentState = State.PURSUE;
                populatePursueAndReverseShots(firstHit, secondHit);
                break;
            case PURSUE:
                break;
            case REVERSE:
                break;
        }
    }
    
    public void handleMiss(ShotResult result) {
        switch (currentState) {
            case BRACKET:
                break;
            case PURSUE:
                currentState = State.REVERSE;
                break;
            case REVERSE:
                reset();
                break;
            default:
                break;
        }
    }
    
    public void handleSunk(ShotResult result) {
        reset();
    }
    
    public void reset() {
        currentState = State.HUNT;
        firstHit = null;
        secondHit = null;
        bracketShots.clear();
        pursueShots.clear();
        reverseShots.clear();
    }
    
    private void populateBracketShots(Coordinate hit) {
        bracketShots.clear();
        
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for (int[] dir : directions) {
            try {
                Coordinate bracket = new Coordinate(hit.getRow() + dir[0], hit.getColumn() + dir[1]);
                if (!shotsTaken.contains(bracket)) {
                    bracketShots.add(bracket);
                }
            } catch (Exception e) {
            }
        }
    }
    
    private void populatePursueAndReverseShots(Coordinate first, Coordinate second) {
        pursueShots.clear();
        reverseShots.clear();
        
        int dRow = second.getRow() - first.getRow();
        int dCol = second.getColumn() - first.getColumn();
        
        Coordinate next = second;
        for (int i = 0; i < 4; i++) {
            try {
                next = new Coordinate(next.getRow() + dRow, next.getColumn() + dCol);
                if (!shotsTaken.contains(next)) {
                    pursueShots.add(next);
                }
            } catch (Exception e) {
                break;
            }
        }
        
        next = first;
        for (int i = 0; i < 4; i++) {
            try {
                next = new Coordinate(next.getRow() - dRow, next.getColumn() - dCol);
                if (!shotsTaken.contains(next)) {
                    reverseShots.add(next);
                }
            } catch (Exception e) {
                break;
            }
        }
    }
}
