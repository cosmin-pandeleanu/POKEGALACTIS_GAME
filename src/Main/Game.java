package Main;

import Database.DataBase;
import javax.swing.JFrame;

/*! \class Game
    \brief Implementeaza main-ul proiectului.
 */
public class Game {

	///Game main
	public static void main(String[] args) {

		JFrame game = new JFrame("POKEGALACTIS");
		game.setContentPane(new GamePanel());
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setResizable(false);
		game.pack();
		game.setVisible(true);
		DataBase.createDB();
	}
}
