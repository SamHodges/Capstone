import java.awt.RenderingHints;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CoffinGame extends GeneralGame {
	// initializes variables
	private JPanel frame, frameBackground;
	Polygon coffin;
	ArrayList<Holes> holes;
	int circleWidth, safeDistance, score, currentSeconds, remainingSeconds, levelBonus;
	boolean join;
	long startTime, currentTime, newHoles;

	// method that runs when a CoffinGame is created
	public CoffinGame() {
		// sets score to 0
		score = 0;

		// creates a new window and sets it visible
		frame = new JPanel();
		frame.add(this);
		frame.setVisible(true);
	}

	// updates the game
	public void update() {
		// updates the various times
		currentTime = System.currentTimeMillis() - startTime;
		currentSeconds = Math.round(currentTime / 1000);
		remainingSeconds = 30 - currentSeconds;

		// checks if the game is over
		isFinished();

		// Receives mouse coordinates and adjusts y because of the top bar
		int coordinates[] = InputManager.coordinates();
		coordinates[1] = coordinates[1] - 25;

		// detects when a hole is clicked and removes it, and adds to the score
		for (int i = 0; i < holes.size(); i++) {
			if (Math.hypot((holes.get(i).getX() + circleWidth - coordinates[0]),
					(holes.get(i).getY() + circleWidth - coordinates[1])) <= circleWidth) {
				score = score + 1;
				holes.remove(i);
			}
		}

		// creates new holes at a speed determined by level
		if (currentTime - newHoles > (1000 / levelBonus)) {
			holes.add(new Holes());
			newHoles = currentTime;
		}
	}

	// checks to see if the time is up or if there are too many holes
	public void isFinished() {
		// changes to "you win" screen if time is up
		if (remainingSeconds < 0) {
			Main.getInstance().changeGame(5);
		}

		// changes to "you lose" screen if there are too many holes
		if (holes.size() >= 40) {
			Main.getInstance().changeGame(3);
		}
	}

	// paints everything
	protected void paintComponent(Graphics g) {
		// creates instance of class that can paint various objects
		Graphics2D g2d = (Graphics2D) g;

		// sets specified colors using RGB
		Color green = new Color(33, 96, 7);
		Color brown = new Color(153, 115, 0);
		Color black = new Color(0, 0, 0);
		Color white = new Color(255, 255, 255);
		Color red = new Color(244, 78, 66);
		Color darkRed = new Color(211, 10, 10);

		// sets the font and size for the text
		Font Arial = new Font("Arial", Font.PLAIN, 50);

		// draws the background- red if nearly dead
		g2d.setColor(green);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		if (holes.size() > 35 && holes.size() % 2 == 0) {
			g2d.setColor(darkRed);
			g2d.fillRect(0, 0, getWidth(), getHeight());
		}

		// draws the coffin
		g2d.setColor(brown);
		int xPoints[] = { 20, 250, 880, 880, 250, 20 };
		int yPoints[] = { 300, 215, 350, 550, 685, 600 };
		g2d.fillPolygon(xPoints, yPoints, xPoints.length);

		// draws the red boxes for time, score, and amount of holes
		g2d.setColor(red);
		g2d.fillRect(10, 20, 250, 70);
		g2d.fillRect(getWidth() - 260, 20, 250, 70);
		g2d.fillRect((getWidth() / 2) - 125, 20, 250, 70);

		// draws the text for the top of the screen
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(white);
		g2d.setFont(Arial);
		g2d.drawString("Score: " + score, 30, 70);
		g2d.drawString("Time: " + remainingSeconds, getWidth() - 240, 70);
		g2d.drawString("Holes:" + holes.size(), (getWidth() / 2) - 105, 70);

		// draws the holes
		g2d.setColor(black);
		g2d.setStroke(new BasicStroke(35));
		for (int i = 0; i < holes.size(); i++) {
			g2d.fillOval(holes.get(i).getX(), holes.get(i).getY(), holes.get(i).getDiameter(),
					holes.get(i).getDiameter());
		}
	}

	// resets so the game can be replayed
	public void reset(int level) {
		// sets beginning level, score and times
		levelBonus = level;
		score = 0;
		startTime = System.currentTimeMillis();
		currentTime = 0;
		newHoles = currentTime;

		// creates an array of 20 holes
		holes = new ArrayList<Holes>();
		for (int i = 0; i < 20; i++) {
			holes.add(i, new Holes());
		}

		// creates the coffin shape
		int xPoints[] = { 20, 250, 880, 880, 250, 20 };
		int yPoints[] = { 300, 215, 350, 550, 685, 600 };
		coffin = new Polygon(xPoints, yPoints, xPoints.length);

		// sets the radius and distance holes are from edge of coffin
		circleWidth = 25;
		safeDistance = 10;
	}
}
