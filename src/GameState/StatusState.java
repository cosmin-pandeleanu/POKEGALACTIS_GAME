package GameState;

import Entity.PlayerSave;
import Entity.Title;
import TileMap.Background;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/*! \class StatusState extends GameState
    \brief Implementeaza un state al jocului in care jucatorul afla informatii despre punctajele obtinute pe parcursul jocului.
 */
public class StatusState extends GameState{

    private Background bg;
    private BufferedImage text;
    private Title title;
    private Color color;
    private Font font;

    public StatusState(GameStateManager gsm) {
        this.gsm = gsm;
        init();
    }

    /*! \fn public void init()
        \brief
    */
    public void init() {
        bg = new Background("/Backgrounds/help_bg.png");
        try{
            color = new Color(0xFF041B6A, true);
            font = new Font("Courier", Font.PLAIN, 14);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        try {
            text = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/Titles/Titles.png")));
            if(PlayerSave.getDead()) {
                title = new Title(text.getSubimage(0, 150, 320, 75));}
            else {
                title = new Title(text.getSubimage(0,225,280,75));
            }
            title.setY(60);
            title.begin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*! \fn public void update()
        \brief
    */
    public void update() {
        bg.update();
        if(title != null) {
            title.update();
            if(title.shouldRemove()) { title = null; }
        }
    }

    /*! \fn public void draw(Graphics2D g)
        \brief Deseneaza (randeaza) pe ecran starea curenta a state-ului.
        \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
     */
    public void draw(Graphics2D g) {

        /// draw bg
        bg.draw(g);

        /// draw title
        if(title != null){ title.draw(g);}

        g.setColor(color);
        g.setFont(font);
        g.drawString("Score: " + PlayerSave.getScore(), 10, 150);
        if(PlayerSave.getDead())
        {g.drawString("Lives: " + (PlayerSave.getLives() - 1), 10, 170);}
        else
        { g.drawString("Lives: " + PlayerSave.getLives(), 10, 170);}
        g.drawString("Health: " + PlayerSave.getHealth(), 10, 190);

        g.drawString("ESC => back to GameMenu", 10,290);
    }

    /*! \fn public void keyPressed(int k)
       \brief Defineste ce schimbari se vor realiza in cadrul nivelului in urma apasarii unei taste.
    */
    public void keyPressed(int k) {
        if(k == KeyEvent.VK_ESCAPE){
            gsm.setState(GameStateManager.MenuState);
        }
    }
    /*! \fn public void keyReleased(int k)
        \brief Defineste ce schimbari se vor realiza in cadrul nivelului in urma eliberarii unei taste.
    */
    public void keyReleased(int k) {}
}