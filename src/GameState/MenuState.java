package GameState;

import TileMap.Background;
import java.awt.*;
import java.awt.event.KeyEvent;

/*! \class MenuState extends GameState
    \brief Implementeaza meniul jocului.
 */
public class MenuState extends GameState {
	
	private Background bg; /// Background-ul meniului
	
	private int currentChoice = 0; /// Optiunea currenta
	private final String[] options = {
		"Start Game",
		"     Help",
		"     Quit"
	}; /// Array-ul cu optiunile din meniu

	private Font font; /// Fontul pentru textul afisat

	/*! \fn public MenuState(GameStateManager gsm)
        \brief Constructorul de initializare al clasei.

        \param gsm O referinta catre un obiect de tip GameStateManager.
     */
	public MenuState(GameStateManager gsm) {
		
		this.gsm = gsm;
		try {
			///Seteaza background-ul
			bg = new Background("/Backgrounds/menu_bg.png");
			// Seteaza fontul textului
			font = new Font("Courier", Font.BOLD, 16);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/*! \fn public void init()
		\brief Initializeaza starea curenta a meniului.
	*/
	public void init() {}

	/*! \fn public void update()
		\brief Actualizeaza starea curenta a meniului.
	*/
	public void update() {
		bg.update();
	}

	/*! \fn public void draw(Graphics2D g)
		\brief Deseneaza (randeaza) pe ecran starea curenta a meniului.
		\param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
 	*/
	public void draw(Graphics2D g) {
		
		/// Deseneaza background-ul
		bg.draw(g);

		/// Deseneaza optiunile
		g.setFont(font);
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice) {
				g.setColor(Color.BLACK);
			}
			else {
				g.setColor(Color.RED);
			}
			g.drawString(options[i], 170, 160 + i * 25);
		}
	}
	/*! \fn private void select()
		\brief Defineste fiecarei optiuni din meniu cate o actiune.
	*/
	private void select() {
		if(currentChoice == 0) {
			gsm.setState(GameStateManager.Level1State);
		}
		if(currentChoice == 1) {
			gsm.setState(GameStateManager.HelpState);
		}
		if(currentChoice == 2) {
			System.exit(0);
		}
	}

	/*! \fn public void keyPressed(int k)
   		\brief Defineste ce schimbari se vor realiza in cadrul meniului in urma apasarii unei taste.
	*/
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER){
			select();
		}
		if(k == KeyEvent.VK_UP) {
			currentChoice--;
			if(currentChoice == -1) {
				currentChoice = options.length - 1;
			}
		}
		if(k == KeyEvent.VK_DOWN) {
			currentChoice++;
			if(currentChoice == options.length) {
				currentChoice = 0;
			}
		}
	}
	/*! \fn public void keyReleased(int k)
		\brief Defineste ce schimbari se vor realiza in cadrul meniului in urma eliberarii unei taste.
	*/
	public void keyReleased(int k) {}
}