package View;

import javax.swing.JPanel;

import Model.CellState;
import Model.GridRep;
import Util.Constants;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class GridPanel extends JPanel {
    private GridRep gridRep = null;

    public GridPanel(){
        this.setBackground(Color.CYAN);
    }

    public void setGridRep(GridRep rep){
        gridRep = rep;
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // panel dimensions
        int width = this.getWidth();
        int height = this.getHeight();
        int spacing = Constants.Dimensions.CELL_SIZE;

        // set colors
        g.setColor(Color.BLACK);

        // draw vertical lines
        for (int x = 0; x <= width; x += spacing) {
            g.drawLine(x, 0, x, height);
        }


        // draw horizontal lines
        for (int y = 0; y <= height; y += spacing) {
            g.drawLine(0, y, width, y);
        }

        if(gridRep != null){
            // the grid rep has information about what I should be drawing in the cells
            for(int row = 0; row < 10; row++){
                for(int column = 0; column < 10; column++){
                    CellState state = gridRep.getStateAt(row, column);

                    switch(state){
                        case MISS -> drawMissAt(row, column, g);
                        case HIT -> drawHitAt(row, column, g);
                        case OCCUPIED -> drawOccupiedAt(row, column, g);
                        case EMPTY -> {} // do nothing
                    }
                }
            }
        }
    }
    
    
    protected void drawOccupiedAt(int row, int column, Graphics g) {
       // draw whole cell dark grey for ship

       // location math
       int upperLeftX = column * Constants.Dimensions.CELL_SIZE;
       int upperLeftY = row * Constants.Dimensions.CELL_SIZE;
       int width = Constants.Dimensions.CELL_SIZE;
       int height = Constants.Dimensions.CELL_SIZE;

       g.setColor(Color.DARK_GRAY);
       g.fillRect(upperLeftX, upperLeftY, width, height);
    }

    protected void drawHitAt(int row, int column, Graphics g) {
       // draw red circle to represent a peg

        // location math
        int upperLeftX = column * Constants.Dimensions.CELL_SIZE + Constants.Dimensions.CELL_SIZE / 2 - Constants.Dimensions.PEG_DIAMETER / 2;
        int upperLeftY = row * Constants.Dimensions.CELL_SIZE + Constants.Dimensions.CELL_SIZE / 2 - Constants.Dimensions.PEG_DIAMETER / 2;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        Ellipse2D.Double circle = new Ellipse2D.Double(upperLeftX, upperLeftY, Constants.Dimensions.PEG_DIAMETER, Constants.Dimensions.PEG_DIAMETER);
        g2d.fill(circle);
        g2d.setColor(Color.BLACK);
        g2d.draw(circle);
    }

    private void drawMissAt(int row, int column, Graphics g) {
        // draw white circle to represent a peg

        // location math
        int upperLeftX = column * Constants.Dimensions.CELL_SIZE + Constants.Dimensions.CELL_SIZE / 2 - Constants.Dimensions.PEG_DIAMETER / 2;
        int upperLeftY = row * Constants.Dimensions.CELL_SIZE + Constants.Dimensions.CELL_SIZE / 2 - Constants.Dimensions.PEG_DIAMETER / 2;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        Ellipse2D.Double circle = new Ellipse2D.Double(upperLeftX, upperLeftY, Constants.Dimensions.PEG_DIAMETER, Constants.Dimensions.PEG_DIAMETER);
        g2d.fill(circle);
        g2d.setColor(Color.BLACK);
        g2d.draw(circle);
    
    }
    

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Constants.Dimensions.GRID_SIZE, Constants.Dimensions.GRID_SIZE);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(Constants.Dimensions.GRID_SIZE, Constants.Dimensions.GRID_SIZE);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(Constants.Dimensions.GRID_SIZE, Constants.Dimensions.GRID_SIZE);
    }

}
