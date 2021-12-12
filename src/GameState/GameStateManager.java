package GameState;

import java.awt.*;

/*! \class GameStateManager
    \brief Implementeaza managerul pentru state-uri.
 */
public class GameStateManager {
	
	private final GameState[] gameStates;
	private int currentState;

	public static final int NumGamesStates = 5;
	public static final int MenuState = 0;
	public static final int HelpState = 1;
	public static final int Level1State = 2;
	public static final int Level2State = 3;
	public static final int StatusState = 4;


	/*! \fn public GameStateManager()
	\brief Constructorul de initializare al clasei.
	 */
	public GameStateManager() {
		gameStates = new GameState[NumGamesStates];
		currentState = MenuState;
		loadState(currentState);
	}

	/*! \fn private void loadState(int state)
		\brief Aloca memorie pentru state necesar
		\param state
	*/
	private void loadState(int state) {
		if(state == MenuState)
			gameStates[state] = new MenuState(this);
		if(state == HelpState)
			gameStates[state] = new HelpState(this);
		if(state == Level1State)
			gameStates[state] = new Level1State(this);
		if(state == Level2State)
			gameStates[state] = new Level2State(this);
		if(state == StatusState)
			gameStates[state] = new StatusState(this);
	}

	/*! \fn private void unloadState(int state)
		\brief Elibereaza memoria alocata pentru state
		\param state
	*/
	private void unloadState(int state) {
		gameStates[state] = null;
	}

	/*! \fn public void update()
		\brief Seteaza starea curenta.
		\state Starea care se seteaza
	 */
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}

	/*! \fn public void update()
		\brief Actualizeaza starea curenta.
	 */
	public void update() {
		try{
			gameStates[currentState].update();
		} catch (Exception ignored) {
		}
	}

	/*! \fn public void draw(Graphics2D g)
		\brief Deseneaza (randeaza) pe ecran starea curenta.
		\param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
	 */
	public void draw(Graphics2D g) {
		try{
			gameStates[currentState].draw(g);
		} catch (Exception ignored) {
		}
	}

	/*! \fn public void keyPressed(int k)
		\brief Defineste ce schimbari se vor realiza in urma apasarii unei taste.
	*/
	public void keyPressed(int k) {
		try{
			gameStates[currentState].keyPressed(k);
		} catch (Exception ignored) {
		}
	}

	/*! \fn public void keyReleased(int k)
		\brief Defineste ce schimbari se vor realiza in urma eliberarii unei taste.
	*/
	public void keyReleased(int k) {
		try{
			gameStates[currentState].keyReleased(k);
		} catch (Exception ignored) {
		}
	}
}