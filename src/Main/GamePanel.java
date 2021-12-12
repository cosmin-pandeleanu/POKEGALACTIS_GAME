package Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import javax.swing.JPanel;
import GameState.GameStateManager;
import static java.lang.Thread.sleep;

/*! \class GamePanel extends JPanel implements Runnable, KeyListener
    \brief Clasa principala a intregului proiect. Implementeaza Game - Loop (Update -> Draw).
 */
public class GamePanel extends JPanel implements Runnable, KeyListener {

	/// Dimensions
	public static final int WIDTH = 420;
	public static final int HEIGHT = 300;
	public static final int SCALE = 2;
	
	/// Game thread
	private Thread thread;
	private boolean running;

	/// Image
	private BufferedImage image;
	private Graphics2D g;
	
	/// Game state manager
	private GameStateManager gsm;

	/*! \fn public GamePanel()
		\brief Constructorul de initializare al clasei.
	 */
	public GamePanel() {
		super();
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();
	}

	/*! \fn public void addNotify()
		\brief Face acest container afișabil conectându-l la o resursă de ecran nativă.
	 */
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}

	/*! \fn public void init()
		\brief Initializeaza .
	 */
	private void init() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		running = true;
		gsm = new GameStateManager();
	}

	/*! \fn public void run()
   		\brief run() metoda a clasei Thread
 	*/
	public void run() {
		init();
		long start;
		long elapsed;
		long wait;
		
		/// Game loop
		while(running) {
			
			start = System.nanoTime();
			update();
			draw();
			drawToScreen();
			elapsed = System.nanoTime() - start;
			int FPS = 60;
			long targetTime = 1000 / FPS;
			wait = targetTime - elapsed / 1000000;
			if(wait < 0) wait = 5;
			try {
				sleep(wait);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*! \fn public void update()
		\brief Apeleaza functia update() din gsm.
	 */
	private void update() {
		gsm.update();
	}

	/*! \fn public void draw()
		\brief Apeleaza functia draw() din gsm.
	 */
	private void draw() {
		gsm.draw(g);
	}

	/*! \fn private void drawToScreen()
		\brief Genereaza fereastra in care se deseneaza.
	 */
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g2.dispose();
	}

	/*! \fn public void keyTyped(int k)
	*/
	public void keyTyped(KeyEvent key) {}

	/*! \fn public void keyPressed(int k)
		\brief Apeleaza functia keyPressed() din gsm.
	*/
	public void keyPressed(KeyEvent key) {
		gsm.keyPressed(key.getKeyCode());
	}

	/*! \fn public void keyReleased(int k)
		\brief Apeleaza functia keyReleased() din State ul curent.
	*/
	public void keyReleased(KeyEvent key) {
		gsm.keyReleased(key.getKeyCode());
	}
}