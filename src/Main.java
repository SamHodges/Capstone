// list of imported classes (classes used within this one) for this class
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Main extends JPanel implements Runnable {
	// declaring variables
	private static Main mainClass; 
	JFrame keyFrame;
	GeneralGame game;
	GeneralGame oldGame, coffinGame, coffinGameNJ, logGame;
	JPanel cards;
	int level;

	private Main() {

	}
	
	// return an instance of main so other classes can use methods inside of it
	public static Main getInstance(){
		return mainClass;
	}

	// set up for Swing, which is how I made the graphics
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Main());
	}

	// sets up main
	public void run() {
		
		mainClass = this;
		
		// says there is no current game
		game = null;
		
		// creates the pop up window and makes it so the program ends when it closes
		keyFrame = new JFrame("Game");
		keyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// adds the key and mouse listeners
		keyFrame.addKeyListener(InputManager.getInstanceKeys());
		keyFrame.addMouseListener(InputManager.getInstanceMouse());

		// runs the menu() method
		menu();

		// adds all the elements to the frame and displays it to the user
		keyFrame.pack();
		keyFrame.setVisible(true);

		// creates a new timer
		Timer t = new Timer();

		// updates the game (if there is one) every 50 milliseconds
		t.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if (game != null) {
					game.update();
				}
			}
		}, 0, 50);

		// repaints the game display (if there is a game) every 20 milliseconds
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				if (game != null) {
					game.repaint();
				}
			}

		}, 0, 20);
	}

	// creates the various menu displays
	public void menu() {
		// MAIN PAGE
		// creates the header
		JLabel header = new JLabel("Help the Bundrens", JLabel.CENTER);
		header.setBackground(new Color(255, 255, 255));
		header.setPreferredSize(new Dimension(900, 100));
		header.setFont(new Font("American Typewriter", Font.BOLD, 50));

		// creates the main background area
		JPanel background = new JPanel();
		background.setLayout(new BoxLayout(background, BoxLayout.PAGE_AXIS));
		background.setOpaque(true);
		background.setBackground(new Color(114, 160, 234));
		background.setPreferredSize(new Dimension(900, 900));
		
		// creates a button linking to LogGame
		JButton game1 = new JButton("Cross the River");
		game1.setBounds(50, 150, 100, 30);
		game1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeGame(6);
				oldGame = logGame;
			}
		});
		game1.setAlignmentX(CENTER_ALIGNMENT);
		background.add(game1);
		background.add(Box.createHorizontalGlue());

		// creates a button linking to CoffinGame
		JButton game2 = new JButton("Fix the Coffin");
		game2.setBounds(50, 300, 100, 30);
		game2.setAlignmentX(CENTER_ALIGNMENT);
		game2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeGame(6);
				oldGame = coffinGame;
			}
		});
		background.add(game2);
		background.add(Box.createHorizontalGlue());

		// add the header and background into one screen in the right layout
		JPanel main = new JPanel();
		main.setLayout(new BorderLayout());
		main.add(header, BorderLayout.NORTH);
		main.add(background, BorderLayout.CENTER);

		
		// GAME OVER
		// create the header
		JPanel end = new JPanel();
		JLabel endHeader = new JLabel("GAME OVER: YOU LOST", JLabel.CENTER);
		endHeader.setBackground(new Color(244, 182, 66));
		endHeader.setPreferredSize(new Dimension(900, 100));
		endHeader.setFont(new Font("American Typewriter", Font.BOLD, 50));
		
		// create the background
		JPanel endBackground = new JPanel();
		endBackground.setLayout(new BoxLayout(endBackground, BoxLayout.PAGE_AXIS));
		endBackground.setOpaque(true);
		endBackground.setBackground(new Color(244, 113, 65));
		endBackground.setPreferredSize(new Dimension(900, 900));
		
		// create the home button
		JButton home = new JButton("Main Menu");
		home.setBounds(50, 150, 100, 30);
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeGame(4);
			}
		});
		home.setAlignmentX(CENTER_ALIGNMENT);
		endBackground.add(home);
		endBackground.add(Box.createHorizontalGlue());
		
		// create the replay button
		JButton replay = new JButton("Play Again");
		replay.setBounds(50, 300, 100, 30);
		replay.setAlignmentX(CENTER_ALIGNMENT);
		replay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeGame(6);
			}
		});

		// add them all to one screen in the correct layout
		endBackground.add(replay);
		end.setLayout(new BorderLayout());
		end.add(endHeader, BorderLayout.NORTH);
		end.add(endBackground, BorderLayout.CENTER);
		
		
		// YOU WIN
		// create header
		JPanel win = new JPanel();
		JLabel winHeader = new JLabel("CONGRATS: YOU WON!", JLabel.CENTER);
		winHeader.setBackground(new Color(244, 182, 66));
		winHeader.setPreferredSize(new Dimension(900, 100));
		winHeader.setFont(new Font("American Typewriter", Font.BOLD, 50));
		
		// create background
		JPanel winBackground = new JPanel();
		winBackground.setLayout(new BoxLayout(winBackground, BoxLayout.PAGE_AXIS));
		winBackground.setOpaque(true);
		winBackground.setBackground(new Color(154, 255, 145));
		winBackground.setPreferredSize(new Dimension(900, 900));
		
		// create home button
		JButton winHome = new JButton("Main Menu");
		winHome.setBounds(50, 150, 100, 30);
		winHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeGame(4);
			}
		});
		winHome.setAlignmentX(CENTER_ALIGNMENT);
		winBackground.add(winHome);
		winBackground.add(Box.createHorizontalGlue());
		
		// create replay button
		JButton winReplay = new JButton("Play Again");
		winReplay.setBounds(50, 300, 100, 30);
		winReplay.setAlignmentX(CENTER_ALIGNMENT);
		winReplay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeGame(6);
			}
		});

		// add to one screen with correct layout
		winBackground.add(winReplay);
		win.setLayout(new BorderLayout());
		win.add(winHeader, BorderLayout.NORTH);
		win.add(winBackground, BorderLayout.CENTER);
		
	
		// LEVELS
		// create header
		JLabel levelHeader = new JLabel("Choose your level", JLabel.CENTER);
		levelHeader.setBackground(new Color(255, 255, 255));
		levelHeader.setPreferredSize(new Dimension(900, 100));
		levelHeader.setFont(new Font("American Typewriter", Font.BOLD, 50));

		// create background
		JPanel levelBackground = new JPanel();
		levelBackground.setLayout(new BoxLayout(levelBackground, BoxLayout.PAGE_AXIS));
		levelBackground.setOpaque(true);
		levelBackground.setBackground(new Color(114, 160, 234));
		levelBackground.setPreferredSize(new Dimension(900, 900));
		
		// create level 1 button
		JButton level1 = new JButton("Level 1");
		level1.setBounds(50, 150, 100, 30);
		level1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				level = 1;
				if(oldGame.equals(logGame)){
					changeGame(1);
				}
				else if(oldGame.equals(coffinGame)){
					changeGame(2);
				}
			}
		});
		level1.setAlignmentX(CENTER_ALIGNMENT);
		levelBackground.add(level1);
		levelBackground.add(Box.createHorizontalGlue());
		
		// create level 2 button
		JButton level2 = new JButton("Level 2");
		level2.setBounds(50, 300, 100, 30);
		level2.setAlignmentX(CENTER_ALIGNMENT);
		level2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				level = 2;
				if(oldGame.equals(logGame)){
					changeGame(1);
				}
				else if(oldGame.equals(coffinGame)){
					changeGame(2);
				}
			}
		});
		levelBackground.add(level2);
		levelBackground.add(Box.createHorizontalGlue());
		
		// create level 3 button
		JButton level3 = new JButton("Level 3");
		level3.setBounds(50, 300, 100, 30);
		level3.setAlignmentX(CENTER_ALIGNMENT);
		level3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				level = 3;
				if(oldGame.equals(logGame)){
					changeGame(1);
				}
				else if(oldGame.equals(coffinGame)){
					changeGame(2);
				}
			}
		});
		levelBackground.add(level3);

		// add everything to the screen with the correct layout
		JPanel levels = new JPanel();
		levels.setLayout(new BorderLayout());
		levels.add(levelHeader, BorderLayout.NORTH);
		levels.add(levelBackground, BorderLayout.CENTER);
		
		
		// create an instance of each game
		coffinGame = new CoffinGame();
		logGame = new LogGame();
		
		// create and add everything to CardLayout (like a stack of cards)
		cards = new JPanel(new CardLayout());
		cards.add(main, "main");
		cards.add(end, "end");
		cards.add(logGame, "logGame");
		cards.add(win, "win");
		cards.add(coffinGame, "coffinGame");
		cards.add(levels, "levels");
		
		// add all of this to the main screen
		keyFrame.getContentPane().add(cards);
		keyFrame.pack();
	}

	// a method that changes which screen is visible, and which is the current game (variable) 
	public void changeGame(int button) {
		// LogGame is visible
		if (button == 1) {
			keyFrame.requestFocus();
			CardLayout cl = (CardLayout) (cards.getLayout());
			cl.show(cards, "logGame");
			game = logGame;
			oldGame = logGame;
			logGame.reset(level);
			
		}
		
		// CoffinGame is visible
		if(button == 2){
			keyFrame.requestFocus();
			CardLayout cl = (CardLayout)(cards.getLayout());
			cl.show(cards, "coffinGame");
			game = coffinGame;
			oldGame = coffinGame;
			coffinGame.reset(level);
		}
		
		// end screen is visible
		if (button == 3){
			CardLayout cl = (CardLayout) (cards.getLayout());
			cl.show(cards, "end");
			game = null;
		}
		
		// main screen is visible
		if(button == 4){
			CardLayout cl = (CardLayout) (cards.getLayout());
			cl.show(cards, "main");
			game = null;
		}
		
		// win screen is visible
		if(button == 5){
			CardLayout cl = (CardLayout) (cards.getLayout());
			cl.show(cards, "win");
			game = null;
		}
		
		// level screen is visible
		if(button == 6){
			CardLayout cl = (CardLayout) (cards.getLayout());
			cl.show(cards, "levels");
		}
	}
}
