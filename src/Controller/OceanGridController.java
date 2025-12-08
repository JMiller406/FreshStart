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
		this.model.addListener(modelListener);
	}

	public void disconnect() {
		model.removeListener(modelListener);
		modelListener = null;
		view = null;
		model = null;
	}

	private class OceanGridListener implements GridListener {
		@Override
		public void gridChanged(Model.GridRep gridRep) {
			view.setGridRep(gridRep);
		}
	}
}
