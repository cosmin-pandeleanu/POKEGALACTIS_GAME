package TileMap;

import java.awt.*;
import java.awt.image.*;
import java.util.Objects;
import javax.imageio.ImageIO;

/*! \class Background
    \brief Implementeaza background-ul.
 */
public class Background {
	
	private BufferedImage image;

	/*! \fn public Background(String s)
		\brief Constructorul de initializare al clasei.
		\param s path-ul catre imaginea pentru background.
	 */
	public Background(String s) {
		try {
			image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(s)));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/*! \fn public void update()
        \brief Actualizeaza backgorund-ul
     */
	public void update() {}

	/*! \fn public void draw(Graphics2D g)
        \brief Deseneaza (randeaza) pe ecran imaginea de background.
        \param g Contextul grafic in care trebuie sa deseneze backround-ul jocului pe ecran.
     */
	public void draw(Graphics2D g) {
		g.drawImage(image, 0,0, null);
	}
}