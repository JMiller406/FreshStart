package View;

import java.util.prefs.Preferences;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;

public class BetterWindow extends JFrame {
    private Preferences prefs = Preferences.userNodeForPackage(this.getClass());
	private WindowListener windowListener = new WindowListener();
    private final String WINDOW_WIDTH_KEY = "BetterWindowWidthKey";
    private final String WINDOW_HEIGHT_KEY = "BetterWindowHeightKey";
    private final String WINDOW_X_KEY = "BetterWindowXKey";
    private final String WINDOW_Y_KEY = "BetterWindowYKey";

    public BetterWindow(String title){
        super(title);

        // setup window position tracking
        this.addComponentListener(windowListener);

        // get position and size from prefs
        int width = prefs.getInt(WINDOW_WIDTH_KEY, 0);
        int height = prefs.getInt(WINDOW_HEIGHT_KEY, 0);
        int x = prefs.getInt(WINDOW_X_KEY, 0);
        int y = prefs.getInt(WINDOW_Y_KEY, 0);

        // get screen dimensions; check if window off screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;

        // set size
        this.setSize(width, height);
        this.setPreferredSize(new Dimension(width, height)); // some layout managers ignore the .setSize method

        // set position
        if (x == 0 && y == 0) {
            // window pref never set, so center
            this.setLocationRelativeTo(null);
        } else if(x + width > screenWidth || y + height > screenHeight || x < 0 || y < 0){
            // window would be offscreen, so center
            this.setLocationRelativeTo(null);
        } else {
            this.setLocation(x, y);
        }
    }

    private class WindowListener extends ComponentAdapter {
		@Override
		public void componentMoved(ComponentEvent e) {
			// record the current state to prefs
            prefs.putInt(WINDOW_X_KEY, BetterWindow.this.getX());
            prefs.putInt(WINDOW_Y_KEY, BetterWindow.this.getY());
		}

        @Override
		public void componentResized(ComponentEvent e) {
			// record the current state to prefs
            prefs.putInt(WINDOW_WIDTH_KEY, BetterWindow.this.getWidth());
            prefs.putInt(WINDOW_HEIGHT_KEY, BetterWindow.this.getHeight());
		}
	}
    
}
