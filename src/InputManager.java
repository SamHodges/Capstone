// list of imported classes (classes used within this one) for this class
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InputManager{
	// create an instance of each class to store the input values
	private static Keys keys = new Keys();
	private static Mouse mouse = new Mouse();
	
	private InputManager(){
	}
	
	// returns the instance of keys, so the frame can access it
	public static Keys getInstanceKeys(){
		return keys;
	}
	
	// returns the instance of mouse, so the frame can access it
	public static Mouse getInstanceMouse(){
		return mouse;
	}
	
	// returns the current keys that are being pressed
	public static int[] isActive(){
		return keys.active;
	}
	
	// returns the coordinates of the last mouse click
	public static int[] coordinates(){
		return mouse.coordinates;
	}
	
	// the class that listens for mouse inputs
	public static class Mouse implements MouseListener{
		// creating empty array for coordinates
		int[] coordinates = {0,0};
		
		// updating array with detected coordinates
		public void update(int x, int y){
			coordinates[0] = x;
			coordinates[1] = y;
			System.out.println(x + y);
		}
		
		// returns coordinates of last mouse click
		public int[] coordinates(){
			return coordinates;
		}

		/* NOTE: the following methods are from the mouse detection class,
		 * so I did not write the code for the detection of the mouse,
		 * just what to use that detection for. 
		 * The reason some methods are empty is because I did not need them;
		 * however, they were required methods for this class.
		 */
		
		// method for detecting mouse clicks (press and release)
		@Override
		public void mouseClicked(MouseEvent e) {			
		}
		
		// method for detecting a mouse press and updating the coordinates
		@Override
		public void mousePressed(MouseEvent e) {
			update(e.getX(), e.getY());
		
		}

		// method for detecting mouse release
		@Override
		public void mouseReleased(MouseEvent e) {
		}

		// method for detecting when a mouse is hovering over something
		@Override
		public void mouseEntered(MouseEvent e) {
		}

		// method for detecting when a mouse stops hovering over something
		@Override
		public void mouseExited(MouseEvent e) {			
		}
	}
	
	// the class that listens for key inputs
	public static class Keys implements KeyListener{
		// sets all keys to the not active position
		int up = 0;
		int down = 0;
		int left = 0;
		int right = 0;
		
		int[] active = {up, down, left, right};
		
		// updates which keys are active when pressed
		public void update(int c, int val){
			if(c == KeyEvent.VK_W){
				 active[0] = val;
			}
			if(c == KeyEvent.VK_S){
				 active[1] = val;
			}
			if(c == KeyEvent.VK_A){
				 active[2] = val;
			}
			if(c == KeyEvent.VK_D){
				 active[3] = val;
			}

		}
		
		/*NOTE: the following methods are from the key detection class,
		 * so I did not write the code for the detection of the mouse,
		 * just what to use that detection for. 
		 */
		
		// method for detecting which key is typed
		@Override
		public void keyTyped(KeyEvent e) {
			char c = e.getKeyChar();
		}
		
		// method for detecting which key is pressed
		@Override
		public void keyPressed(KeyEvent e) {
			int val = 1;
			int c = e.getKeyCode();
			update(c, val);
		}

		// method for detecting which key is released
		@Override
		public void keyReleased(KeyEvent e) {
			int val = 0;
			int c = e.getKeyCode();
			update(c, val);
		}
		
		// returns which keys are active (currently being pressed)
		public int[] getKeys(){
			return active;
			
		}
		
	}

}
