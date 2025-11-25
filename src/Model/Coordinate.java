package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Coordinate {
    private int row;
    private int column;

    public Coordinate(String humanValue) throws Exception {
        row = rowFromString(humanValue);
        column = columnFromString(humanValue);
    }

    public Coordinate(int row, int column) throws Exception {
        if(row < 0 || row > 9){
            throw new Exception("Invalid row for shot.");
        }
        if(column < 0 || column > 9){
            throw new Exception("Invalid column for shot.");
        }
        this.row = row;
        this.column = column;
    }

    public static Coordinate randomCoordinate(){
        try {
            return new Coordinate((int)(Math.random()*10.0), (int)(Math.random()*10.0));
        } catch (Exception e){
            return null; // impossible
        } 
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public String getHumanValue() {
        char firstLetter = (char)(row + 97);
        String assembled = Character.toString(firstLetter) + Integer.toString(column+1); // note 1-index column number
        return assembled.toUpperCase(); 
    }

    private int rowFromString(String value) throws Exception{
        String normalizedValue = value.toLowerCase();
        char firstChar = normalizedValue.charAt(0);
        int asciiValue = (int)firstChar;
        int result = asciiValue - 97; // ascii of 'a' = 97
        if(result < 0 || result > 9){
            throw new Exception("Invalid row for shot.");
        }
        return result;
    }

    private int columnFromString(String value) throws Exception{
        String columnPortion = value.substring(1);
        int column = Integer.parseInt(columnPortion);
        column -= 1; // switch from 1-index to 0-index
        if(column < 0 || column > 9){
            throw new Exception("Invalid column for shot.");
        }
        return column;
    }

    @Override
    public String toString(){
        return getHumanValue();
    }

    @Override
    public boolean equals(Object o){
        if(this == o){ return true; }
        if(o == null){ return false; }
        if(getClass() != o.getClass()){ return false; }
        Coordinate coord = (Coordinate)o;
        if(row == coord.getRow() && column == coord.getColumn()){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return Objects.hash(row, column);
    }

    // refactored from ShipFactory
    // note that this will throw if any resulting coordinates are off the grid
    public List<Coordinate> coordsInDirection(int length, Direction direction) throws Exception {
        List<Coordinate> retVal = new ArrayList<>();
        retVal.add(this);
        for(int i = 1; i < length; i++){
            int nextRow = retVal.get(i-1).getRow();
            int nextCol = retVal.get(i-1).getColumn();
            switch(direction){
                case NORTH -> nextRow -= 1;
                case SOUTH -> nextRow += 1;
                case EAST -> nextCol += 1;
                case WEST -> nextCol -= 1;
            }
            retVal.add(new Coordinate(nextRow, nextCol));
        }
        return retVal;
    }
}
