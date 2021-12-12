package Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import javax.imageio.ImageIO;

/*! \class Info
    \brief Implementeaza zona in care se afiseaza informatiile legate jucator pe parcursul jocului.
 */
public class Info {

    private final Player player;
    private BufferedImage image;
    private Font font;

    /*! \fn public Info(Player p)
        \brief Constructorul de initializare al clasei.
        \param p - Player de la care se extrag informatiile pentru a fi afisate in tabel.
     */
    public Info(Player p) {
        player = p;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Info/info.png")));
            font = new Font("Arial", Font.PLAIN, 15);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /*! \fn public void draw(Graphics2D g)
        \brief Deseneaza (randeaza) pe ecran informatiile despre jucator.
        \param g Contextul grafic in care trebuie sa deseneze informatiile pe ecran.
     */
    public void draw(Graphics2D g) {
        g.drawImage(image, 0, 5, null);
        g.setFont(font);
        g.setColor(Color.YELLOW);
        g.drawString(player.getLives() + "/" + player.getMaxLives(), 30, 20);
        g.setColor(Color.RED);
        g.drawString(player.getHealth() + "/" + player.getMaxHealth(), 30, 40);
        g.setColor(Color.YELLOW);
        g.drawString(player.getFlash() / 100 + "/" + player.getMaxFlash() / 100, 30, 60);
        g.setColor(Color.BLUE);
        g.drawString(player.getScore()+"", 20, 80);
    }
}