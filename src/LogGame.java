import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class LogGame extends GeneralGame {
	// initializes variables
	private JPanel frame;
	Rectangle hit1, hit2, hit3;
	Rectangle rectangleHits[];
	private int rectanglePosition[], rectangleDirection[];
	private int playerPositionY, playerPositionX, levelBonus;
	private BufferedImage shortLog, longLog, player;
	BufferedImage img;

	// method that runs when log game is created
	public LogGame() {
		// creates screen and sets it visible
		frame = new JPanel();
		frame.setPreferredSize(new Dimension(900, 900));

		frame.add(this);
		frame.setVisible(true);

		// creates image variables, sometimes throws an exception that doesn't
		// stop program running
		try {
			shortLog = ImageIO.read(getClass().getResourceAsStream("/shortLog.png"));
			longLog = ImageIO.read(getClass().getResourceAsStream("/longLog.png"));
			player = ImageIO.read(getClass().getResourceAsStream("/player.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// updates the game positions
	public void update() {
		// calls other methods to check if the game is over
		collisionDetect();
		isFinished();

		// changes log positions and speed (if it went back to the top of the
		// screen)
		for (int i = 0; i < rectanglePosition.length; i++) {
			rectanglePosition[i] += rectangleDirection[i];
			if (rectanglePosition[i] > getHeight() && rectangleDirection[i] > 0) {
				rectanglePosition[i] = -250;
				rectangleDirection[i] = (int) (10 * Math.random() * levelBonus) + 5;
			}
		}

		// creates array of which keys are active out of WASD
		int keys[] = InputManager.isActive();

		// changes player position based on those keys
		if (keys[0] == 1 && playerPositionY > 0) {
			playerPositionY -= 10;
		}
		if (keys[1] == 1 && playerPositionY < (getHeight() - 100)) {
			playerPositionY += 10;
		}
		if (keys[2] == 1 && playerPositionX > 0) {
			playerPositionX -= 10;
		}
		if (keys[3] == 1 && playerPositionX < (getWidth() - 200)) {
			playerPositionX += 10;
		}

		// changes the position of the invisible rectangles that make collision
		// detection more accurate for the player
		hit1.setLocation(playerPositionX, playerPositionY + 33);
		hit2.setLocation(playerPositionX + 115, playerPositionY + 43);
		hit3.setLocation(playerPositionX + 115 + 40, playerPositionY + 5);

		// changes the size and position of the invisible rectangles that make
		// collision detection more accurate fro the logs
		for (int i = 0; i < rectanglePosition.length; i++) {
			rectangleHits[i].setLocation(100 * i + 250, rectanglePosition[i]);
			if (rectangleDirection[i] > 8) {
				rectangleHits[i].setSize(50, 75);
			} else {
				rectangleHits[i].setSize(50, 200);
			}
		}
	}

	// checks to see if the player reached the end
	public void isFinished() {
		if (playerPositionX > (getWidth() - 225)) {
			System.out.println("Done!");
			Main.getInstance().changeGame(5);

		}
	}

	// checks to see if the player hit a log
	public void collisionDetect() {
		for (int i = 0; i < rectanglePosition.length; i++) {
			if (hit1.intersects(rectangleHits[i]) || hit2.intersects(rectangleHits[i])
					|| hit3.intersects(rectangleHits[i])) {
				System.out.println(
						"HIT: " + playerPositionY + ", " + rectanglePosition[i] + ", " + rectangleDirection[i]);
				Main.getInstance().changeGame(3);
			}
		}
	}

	// makes everything visible
	@Override
	protected void paintComponent(Graphics g) {
		// creates an instance of Graphics2D, which can draw various shapes
		Graphics2D g2d = (Graphics2D) g;

		// specifies colors using RGB
		Color blue = new Color(62, 146, 224);

		// draws the backgorund
		g2d.setColor(blue);
		g2d.fillRect(0, 0, getWidth(), getHeight());

		// draws the log pictures in the new log positions
		for (int i = 0; i < rectanglePosition.length; i++) {
			if (rectangleDirection[i] > 8) {
				g2d.drawImage(shortLog, 100 * i + 250, rectanglePosition[i], 50, 75, null);
			} else {
				g2d.drawImage(longLog, 100 * i + 250, rectanglePosition[i], 50, 200, null);
			}
		}

		// draws the player in the new position
		g2d.drawImage(player, playerPositionX, playerPositionY, 200, 100, null);
	}

	// returns everything to original position, so the game can be played more than once
	public void reset(int level) {
		// sets the level
		levelBonus = level;

		// creates the rectangles with size and position, as well as their invisible rectangles
		rectanglePosition = new int[10];
		rectangleDirection = new int[10];
		rectangleHits = new Rectangle[10];
		for (int i = 0; i < 10; i++) {
			rectangleDirection[i] = (int) (10 * Math.random() * levelBonus) + 5;
			rectanglePosition[i] = (int) (900 * Math.random());
		}
		
		// specifies size of the rectangle- faster rectangles are shorter
		for (int i = 0; i < rectanglePosition.length; i++) {
			if (rectangleDirection[i] > 8) {
				rectangleHits[i] = new Rectangle(100 * i + 250, rectanglePosition[i], 50, 75);
			} else {
				rectangleHits[i] = new Rectangle(100 * i + 250, rectanglePosition[i], 50, 200);
			}
		}

		// specifies player start position
		playerPositionX = 0;
		playerPositionY = 450;

		// creates invisible rectangles for the player
		hit1 = new Rectangle(playerPositionX, playerPositionY + 33, 115, 70);
		hit2 = new Rectangle(playerPositionX + 115, playerPositionY + 43, 40, 57);
		hit3 = new Rectangle(playerPositionX + 115 + 40, playerPositionY + 5, 45, 93);
	}
}


