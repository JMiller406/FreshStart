package Controller;

import Model.GridListener;
import Model.OceanGrid;
import View.GridPanel;

public class OceanGridController {
	private GridPanel view;
	private OceanGrid model;
	private GridListener modelListener;

	public OceanGridController(GridPanel view, OceanGrid model){
		this.view = view;
		this.model = model;

		// listen for notifications from the model
		modelListener = new OceanGridListener();
		model.addListener(modelListener);
	}

	private class OceanGridListener implements GridListener {
		@Override
		public void gridChanged(Model.GridRep gridRep) {
			view.setGridRep(gridRep);
		}
	}
}
