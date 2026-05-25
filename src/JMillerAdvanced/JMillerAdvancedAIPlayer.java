package JMillerAdvanced;

import java.io.Serializable;
import java.util.*;

import Model.Player;
import Model.Coordinate;
import Model.ShotResult;
import Model.OceanGrid;
import Model.Ship;
import Model.AutomaticShipFactory;
import Model.ShotDelegate;

public class JMillerAdvancedAIPlayer implements Player, Serializable {

    private OceanGrid oceanGrid = new OceanGrid();
    private List<Ship> ships = new ArrayList<>();
    private AutomaticShipFactory factory = new AutomaticShipFactory();
    
    private ProbabilityModel probabilityModel;
    private PursuitStrategy pursuitStrategy;
    
    private Set<Coordinate> shotsTaken = new HashSet<>();
    private ShotDelegate shotDelegate;
    
    public JMillerAdvancedAIPlayer(ShotDelegate delegate) {
        this.shotDelegate = delegate;
        this.probabilityModel = new ProbabilityModel();
        this.pursuitStrategy = new PursuitStrategy(shotsTaken);
    }

    @Override
    public void placeShips() {
        this.ships = factory.getShips();
        oceanGrid.placeShips(this.ships);
    }

    @Override
    public void takeShot() {
        Coordinate shot = null;
        
        switch (pursuitStrategy.getCurrentState()) {
            case HUNT:
                shot = probabilityModel.getBestShot(shotsTaken);
                break;
            case BRACKET:
                if (!pursuitStrategy.getBracketShots().isEmpty()) {
                    shot = pursuitStrategy.getBracketShots().remove(0);
                } else {
                    pursuitStrategy.reset();
                    shot = probabilityModel.getBestShot(shotsTaken);
                }
                break;
            case PURSUE:
                if (!pursuitStrategy.getPursueShots().isEmpty()) {
                    shot = pursuitStrategy.getPursueShots().remove(0);
                } else {
                    pursuitStrategy.handleMiss(null);
                    shot = getNextShot();
                }
                break;
            case REVERSE:
                if (!pursuitStrategy.getReverseShots().isEmpty()) {
                    shot = pursuitStrategy.getReverseShots().remove(0);
                } else {
                    pursuitStrategy.reset();
                    shot = probabilityModel.getBestShot(shotsTaken);
                }
                break;
        }
        
        if (shot != null) {
            shotsTaken.add(shot);
            shotDelegate.handleShot(shot, this);
        }
    }
    
    private Coordinate getNextShot() {
        switch (pursuitStrategy.getCurrentState()) {
            case HUNT:
                return probabilityModel.getBestShot(shotsTaken);
            case BRACKET:
                if (!pursuitStrategy.getBracketShots().isEmpty()) {
                    return pursuitStrategy.getBracketShots().remove(0);
                } else {
                    pursuitStrategy.reset();
                    return probabilityModel.getBestShot(shotsTaken);
                }
            case PURSUE:
                if (!pursuitStrategy.getPursueShots().isEmpty()) {
                    return pursuitStrategy.getPursueShots().remove(0);
                }
                break;
            case REVERSE:
                if (!pursuitStrategy.getReverseShots().isEmpty()) {
                    return pursuitStrategy.getReverseShots().remove(0);
                } else {
                    pursuitStrategy.reset();
                    return probabilityModel.getBestShot(shotsTaken);
                }
        }
        return probabilityModel.getBestShot(shotsTaken);
    }

    @Override
    public void receiveShotResult(ShotResult result) {
        probabilityModel.update(result);
        
        if (result == ShotResult.HIT || result == ShotResult.SUNK) {
            pursuitStrategy.handleHit(result);
        } else if (result == ShotResult.MISS) {
            pursuitStrategy.handleMiss(result);
        }
        
        if (result == ShotResult.SUNK) {
            pursuitStrategy.handleSunk(result);
        }
    }

    @Override
    public ShotResult receiveShot(Coordinate shot) {
        return oceanGrid.receiveShot(shot);
    }

    @Override
    public String getName() {
        return "JMiller Advanced AI 1.0";
    }

    @Override
    public boolean shipsAreSunk() {
        return oceanGrid.shipsAreSunk();
    }
}
