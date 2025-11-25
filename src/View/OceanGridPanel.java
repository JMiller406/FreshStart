package View;

import java.awt.Graphics;

public class OceanGridPanel extends GridPanel {
        
    @Override 
      protected void drawHitAt(int row, int column, Graphics g){
        super.drawOccupiedAt(row, column, g);
        super.drawHitAt(row, column, g);
      }

    }