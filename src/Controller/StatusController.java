package Controller;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

import Model.Game;
import Model.StatusListener;

public class StatusController {
    private JTextPane statusPane;
    private Game model;
    private StatusListener statusListener; 

    public StatusController(JTextPane pane, Game model){
        this.statusPane = pane;
        this.model = model;

        // listen for game updates
        statusListener = new ControllerStatusListener();
        this.model.addListener(statusListener);
    }

    public void disconnect() {
        model.removeListener(statusListener);
        statusListener = null;
        statusPane = null;
        model = null;
    }

    private class ControllerStatusListener implements StatusListener {
        @Override
        public void statusMessage(String message) {
            try{
                statusPane.getStyledDocument().insertString(statusPane.getStyledDocument().getLength(), message, null);
            } catch (BadLocationException e){
                e.printStackTrace();
            }
        }
    }
}
