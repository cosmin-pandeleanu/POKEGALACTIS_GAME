package Entity.Enemies;

import Entity.*;
import TileMap.TileMap;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.util.Objects;
import javax.imageio.ImageIO;

/*! \class Abomasnow extends Enemy
    \brief Implementeaza inamicul Abomasnow.
 */
public class Abomasnow extends Enemy {

    private BufferedImage[] sprites;

    /*! \fn public Abomasnow(TileMap tm)
        \brief Constructorul de initializare al clasei.
        \param tm - harta nivelului
     */
    public Abomasnow(TileMap tm) {
        super(tm);
        moveSpeed = 0.9;
        maxSpeed = 0.9;
        fallSpeed = 0.56;
        maxFallSpeed = 1.0;

        width = 65;
        height = 65;
        cwidth = 55;
        cheight = 55;

        health = maxHealth = 80;
        damage = 2;

        // Load sprites
        try {
            BufferedImage spritesheet = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/Enemies/abomasnow.png")));
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
        ymap = tileMap.gety() + 13;
        super.draw(g);
    }
}
