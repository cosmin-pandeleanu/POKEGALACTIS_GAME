package Entity;

import TileMap.TileMap;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import javax.imageio.ImageIO;
/*! \class FlashBall extends MapObject
    \brief Implementează atacul cu explozia de fulgere.
 */
public class FlashBall extends MapObject {

    private boolean hit;
    private boolean remove;
    private BufferedImage[] sprites;
    private BufferedImage[] hitSprites;

    /*! \fn public FlashBall(TileMap tm, boolean right)
       \brief Constructorul de initializare al clasei.
       \param tm
       \param right
    */
    public FlashBall(TileMap tm, boolean right)
    {
        super(tm);

        facingRight = right;

        moveSpeed = 3.8;
        if(right) dx = moveSpeed;
        else dx = -moveSpeed;

        width = 50;
        height = 50;
        cwidth = 40;
        cheight = 40;

        // Load sprites
        try {
            BufferedImage spritesheet = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/Pikachu/FlashBall.png")));
            sprites = new BufferedImage[4];
            hitSprites = new BufferedImage[4];
            for(int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
                hitSprites[i] = sprites[i];
            }
            animation = new Animation();
            animation.setFrames(sprites);
            animation.setDelay(20);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }


    public void setHit() {
        if(hit) return;
        hit = true;
        animation.setFrames(hitSprites);
        animation.setDelay(20);
        dx = 0;
    }

    public boolean shouldRemove() { return remove; }

    /*! \fn public void update()
        \brief Actualizeaza atacul.
     */
    public void update() {

        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        if(dx == 0 && !hit) {
            setHit();
        }

        animation.update();
        if(hit && animation.hasPlayedOnce()) {
            remove = true;
        }
    }

    /*! \fn public void draw(Graphics2D g)
        \brief Deseneaza (randeaza) pe ecran imaginea atacului.
        \param g Contextul grafic in care trebuie sa deseneze.
     */
    public void draw(Graphics2D g) {
        setMapPosition();
        super.draw(g);
    }
}
