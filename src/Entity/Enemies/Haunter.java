package Entity.Enemies;

import Entity.*;
import TileMap.TileMap;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.util.Objects;
import javax.imageio.ImageIO;

/*! \class Haunter extends Enemy
    \brief Implementeaza inamicul Haunter.
 */
public class Haunter extends Enemy {

    private BufferedImage[] sprites;

    /*! \fn public Haunter(TileMap tm)
        \brief Constructorul de initializare al clasei.
        \param tm - harta nivelului
     */
    public Haunter(TileMap tm) {
        super(tm);
        moveSpeed = 0.9;
        maxSpeed = 0.9;
        fallSpeed = 0.289;
        maxFallSpeed = 1.0;

        width = 50;
        height = 50;
        cwidth = 40;
        cheight = 40;

        health = maxHealth = 5;
        damage = 1;

        // Load sprites
        try {
            BufferedImage spritesheet = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/Enemies/haunter.png")));
            sprites = new BufferedImage[5];
            for(int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(400);

        right = true;
        facingRight = true;

    }

    /*! \fn public void update()
         \brief Actualizeaza inamicul.
      */
    public void update() {
        /// Update position
        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);
        checkFlinching();
        checkDirection();

        /// Update animation
        animation.update();
    }

    /*! \fn public void draw(Graphics2D g)
        \brief Deseneaza (randeaza) pe ecran imaginea inamicului.
        \param g Contextul grafic in care trebuie sa deseneze inamicul pe ecran.
     */
    public void draw(Graphics2D g) {
        setMapPosition();
        super.draw(g);
    }
}
