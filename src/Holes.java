import java.awt.Polygon;

public class Holes {
	// creates variables that all holes have
	private int x, y, diameter = 50, circleWidth = 25, safeDistance = 30;

	// method that runs whenever a new hole is created
	Holes() {
		// sets the coordinates randomly
		int[] coordinates = randomCoordinates();
		x = coordinates[0];
		y = coordinates[1];
	}

	// a method to update the coordinates
	public void updateCoordinates(int newX, int newY) {
		x = newX;
		y = newY;
	}

	// a method that returns random coordinates
	public int[] randomCoordinates() {
		// creates a coffin shape
		int xPoints[] = { 20, 250, 880, 880, 250, 20 };
		int yPoints[] = { 300, 215, 350, 550, 685, 600 };
		Polygon coffin = new Polygon(xPoints, yPoints, xPoints.length);

		// chooses a random x that's within the coffin shape
		x = (int) ((Math.random() * 760) + circleWidth + safeDistance);

		// randomly picks y values that could be within the coffin
		int random = (int) (Math.random() * 485 + 215);

		// checks if those y values are within the coffin at that x value
		while (!(coffin.contains(x, random + circleWidth + safeDistance)
				&& coffin.contains(x, random - circleWidth - safeDistance))) {
			random = (int) (Math.random() * 485 + 215);
		}
		y = random;

		// sets and returns array with coordinates
		int[] coordinates = { x, y };
		return coordinates;
	}

	// returns the values of both coordinates
	public int[] getValues() {
		int[] coordinates = { x, y };
		return coordinates;
	}

	// returns the x coordinate for that hole
	public int getX() {
		return x;
	}

	// returns the y value for that hole
	public int getY() {
		return y;
	}

	// returns hole diameter
	public int getDiameter() {
		return diameter;
	}
}
