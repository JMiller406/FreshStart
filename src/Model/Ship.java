package Model;

import java.util.ArrayList;
import java.util.List;

public class Ship {
    private String name;
    private int length;
    private List<Coordinate> coordinates = new ArrayList<Coordinate>();
    private List<Coordinate> hits = new ArrayList<>();

    public Ship(String name, int length){
        this.name = name;
        this.length = length;
    }

    public String getName() {
        return name;
    }
    public int getHitCount() {
        return hits.size();
    }
    public int getLength() {
        return length;
    }
    public List<Coordinate> getCoordinates() {
        return coordinates;
    }
    public void setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }
    public void registerHit(Coordinate hit){
        hits.add(hit);
    }
    public Coordinate getLastHit(){
        return hits.getLast();
    }
    public boolean isSunk(){
        return hits.size() == length;
    }
}
