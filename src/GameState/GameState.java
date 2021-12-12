package GameState;

/*! \class GameState
    \brief Implementeaza notiunea abstracta de stare a jocului/programului.
		Un joc odata ce este lansat in executie nu trebuie "sa arunce jucatorul direct in lupta", este nevoie de
		un meniu care sa contine optiuni: Start Game, Help, Quit, etc. Toate aceste optiuni nu sunt altceva
		decat stari ale programului (jocului) ce trebuiesc incarcate si afisate functie de starea curenta.
 */
public abstract class GameState {
	
	protected GameStateManager gsm;

		///Metoda abstracta destinata initializarii starii curente
	public abstract void init();
		///Metoda abstracta destinata actualizarii starii curente
	public abstract void update();
		///Metoda abstracta destinata desenarii starii curente
	public abstract void draw(java.awt.Graphics2D g);
		///Metoda abstracta care defineste ce schimbari se vor realiza in program urma apasarii unei taste.
	public abstract void keyPressed(int k);
		///Metoda abstracta care defineste ce schimbari se vor realiza in program urma eliberarii unei taste.
	public abstract void keyReleased(int k);
	
}
