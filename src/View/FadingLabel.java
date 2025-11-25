package View;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.Timer;

public class FadingLabel extends JLabel {
	private static final long serialVersionUID = 1L;
	private final int FADE_OUT_DURATION = 1000; // milliseconds
    private final int FADE_DELAY = 1500; // milliseconds
    private final int FADE_FPS = 30;
	private int alpha = 255;
	private Timer showTimer;
	private Timer fadeTimer;
	private int step = (int) (255.0f / (FADE_OUT_DURATION / 1000.0f * FADE_FPS));

	public FadingLabel(String text, int alignment){
		super(text, alignment);
		showTimer = new Timer(FADE_DELAY, e -> startFade());
		showTimer.setRepeats(false);
		fadeTimer = new Timer((int) (1.0f / FADE_FPS * 1000.0f), e -> performFade());
		fadeTimer.setRepeats(false);
		showTimer.restart();
	}

	@Override
	public void setText(String value){
		super.setText(value);
		if(showTimer != null){
			alpha = 255;
			setForeground(new Color(0, 0, 0, alpha));
			showTimer.restart();
			fadeTimer.stop();
		}	
	}

	private void startFade() {
		fadeTimer.restart();
	}

	private void performFade() {
		if(alpha > step){
			alpha -= step;
			setForeground(new Color(0, 0, 0, alpha));
			fadeTimer.restart();
		} else {
			fadeTimer.stop();
		}
	}
}
