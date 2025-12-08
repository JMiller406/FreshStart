package Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Grid implements Serializable {
    protected Cell[][] cells = new Cell[10][10];
    private transient List<GridListener> listeners = new ArrayList<GridListener>();

    public Grid(){
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                cells[i][j] = new Cell();
            }
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        listeners = new ArrayList<GridListener>();
    }


    protected Cell cellAtLocation(Coordinate location){
        return cells[location.getRow()][location.getColumn()];
    }

    public void addListener(GridListener toAdd){
        listeners.add(toAdd);
        notifyListeners();
    }

    public void removeListener(GridListener toRemove){
        listeners.remove(toRemove);
    }

    protected void notifyListeners(){
        for(GridListener listener : listeners){
            GridRep gridRep = new GridRep(this);
            listener.gridChanged(gridRep);
        }
    }

    public void print(){
        printHeader();
        for(int i = 0; i < 10; i++){
            printRow(i);
        }

    }

    private void printHeader(){
        System.out.println("   . 1 . 2 . 3 . 4 . 5 . 6 . 7 . 8 . 9 . 10.");
    }

    private void printRow(int row){
        char firstLetter = (char)(row + 97);
        String rowLetter = String.format("%s", firstLetter).toUpperCase();
        System.out.print(String.format(" %s .", rowLetter));
        for (int col = 0; col < 10; col++){
            Cell cell = cells[row][col];
            CellState state = cell.getState();
            switch(state){
                case EMPTY:
                    System.out.print("   .");
                    break;
                case MISS:
                    System.out.print(" o .");
                    break;
                case HIT:
                    System.out.print(" * .");
                    break;
                case OCCUPIED:
                    System.out.printf(" # .");
                    break;
                default:
                    System.out.print("   .");
                    break;
            } 
        }
        System.out.print("\n");
    }
  
}
