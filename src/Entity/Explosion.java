package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Objects;
import javax.imageio.ImageIO;

/*! \class Explosion
    \brief Implementează explozia care apare atunci cand un inamic este invins.
 */
public class Explosion {

    private final int x;
    private final int y;
    private int xmap;
    private int ymap;

    private final int width;
    private final int height;

    private final Animation animation;
    private BufferedImage[] sprites;

    private boolean remove;

    /*! \fn public Explosion(int x, int y)
       \brief Constructorul de initializare al clasei.
       \param x - coordonata X
       \param y - coordanata Y
    */
    public Explosion(int x, int y) {

        this.x = x;
        this.y = y;

        width = 30;
        height = 30;

        try {
            BufferedImage spritesheet = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/Enemies/explosion.png")));
            sprites = new BufferedImage[6];
            for(int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(70);
    }
    /*! \fn public void update()
        \brief Actualizeaza explozia.
     */
    public void update() {
        animation.update();
        if(animation.hasPlayedOnce()) {
            remove = true;
        }
    }

    /*! \fn public boolean shouldRemove()
        \brief
     */
    public boolean shouldRemove() { return remove; }


    /*! \fn public void setMapPosition(int x, int y)
        \brief seteaza coordonatele unde trebuie sa apara explozia
     */
    public void setMapPosition(int x, int y) {
        xmap = x;
        ymap = y;
    }

    /*! \fn public void draw(Graphics2D g)
        \brief Deseneaza (randeaza) pe ecran imaginea exploziei.
        \param g Contextul grafic in care trebuie sa deseneze.
     */
    public void draw(Graphics2D g) {
        g.drawImage(animation.getImage(), x + xmap - width / 2, y + ymap - height / 2, null);
    }
}