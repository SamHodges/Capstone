// list of imported classes (classes used within this one) for this class
import javax.swing.JPanel;


abstract public class GeneralGame extends JPanel{
	// creates general classes for CoffinGame and LogGame
	public abstract void update();
	public abstract void reset(int level);
}
