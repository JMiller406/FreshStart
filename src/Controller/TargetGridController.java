package Controller;

import Model.Coordinate;
import Model.GridListener;
import Model.TargetGrid;
import Util.Constants;
import View.GridPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TargetGridController {
    private GridPanel view;
    private TargetGrid model;
    private GridPanelListener viewListener;
    private GridListener modelListener;

    public TargetGridController(GridPanel view, TargetGrid model){
        this.view = view;
        this.model = model;

        // listen for clicks on the target panel
        viewListener = new GridPanelListener();
        view.addMouseListener(viewListener);

        // listen for notifications from the model
        modelListener = new TargetGridListener();
        model.addListener(modelListener);
    }

    private class GridPanelListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e){
            // view coordinates (pixels)
            int x = e.getX();
            int y = e.getY();

            // model coordinates (row and column)
            int column = x / Constants.Dimensions.CELL_SIZE;
            int row = y / Constants.Dimensions.CELL_SIZE;


            // create a shot 
            Coordinate shot = null;
            try {
                shot = new Coordinate(row, column);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            if (model.isValidShot(shot)){
                model.handleShot(shot, this);
            } else {
                System.out.printf("You have already shot at %s%n", shot.toString());
            }


        }
    }

    public void disconnect() {
        model.removeListener(modelListener);
        view.removeMouseListener(viewListener);
        modelListener = null;
        viewListener = null;
        view = null;
        model = null;
    }

    private class TargetGridListener implements GridListener {

        @Override
        public void gridChanged(Model.GridRep gridRep) {
            view.setGridRep(gridRep);
        }

    }
}
