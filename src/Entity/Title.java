package Entity;

import Main.GamePanel;
import java.awt.*;
import java.awt.image.BufferedImage;


/*! \class Title
    \brief Implementeaza titlul care se afiseaza pe ecran la inceperea unui nou nivel sau la finalul acestuia in functie de rezultatul jucatorului.
 */
public class Title {
	
	public BufferedImage image;
	
	public int count;
	private boolean done;
	private boolean remove;
	
	private double x;
	private double y;
	private double dx;
	
	private final int width;

	/*! \fn public Title(BufferedImage image)
		\brief Constructorul de initializare al clasei.
		\param image - este imaginea folosita pentru titlu.
	 */
	public Title(BufferedImage image) {
		this.image = image;
		width = image.getWidth();
		x = -width;
		done = false;
	}

	/*! \fn public void setY(double y)
		\brief Seteaza pozitia Y de unde se va afisa titlul pe ecran.
		\param y valoarea cu care actualizeaza titlul.
	 */
	public void setY(double y) { this.y = y; }

	/*! \fn public void begin()
		\brief Inceperea titlului
	 */
	public void begin() {
		dx = 10;
	}

	/*! \fn boolean shouldRemove()
		\brief Seteaza ca sa dispara tilul de pe ecran.
	 */
	public boolean shouldRemove() { return remove; }

	/*! \fn public void update()
		\brief Actualizeaza titlul
	 */
	public void update() {
		if(!done) {
			if(x >= (GamePanel.WIDTH - width) / 2) {
				x = (GamePanel.WIDTH - width) / 2;
				count++;
				if(count >= 120) done = true;
			}
			else {
				x += dx;
			}
		}
		else {
			x += dx;
			if(x > GamePanel.WIDTH) remove = true;
		}
	}

	/*! \fn public void draw(Graphics2D g)
		\brief Deseneaza (randeaza) pe ecran imaginea titlului.
		\param g Contextul grafic in care trebuie sa deseneze titlul jocului pe ecran.
	 */
	public void draw(Graphics2D g) {
		g.drawImage(image, (int)x, (int)y, null);
	}
}
