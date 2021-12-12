package GameState;

import TileMap.Background;
import java.awt.*;
import java.awt.event.KeyEvent;


/*! \class HelpState extends GameState
    \brief Implementeaza un state al jocului in care jucatorul poate afla informatii despre regulile jocului.
 */
public class HelpState extends GameState{

    private Background bg;  /// Background-ul state-ului
    private Color color; /// Culoarea textului meniului
    private Font font; /// Fontul textului
    private final String[] HelpText = {
            "Mişcarea caracterului se realizează prin intermediul tastelor:",
            "    Sus – pentru salt",
            "    Dreapta - pentru mers înainte ",
            "    Stânga – pentru mers înapoi",
            "Iar atacurile se realizează prin intermediul tastelor:",
            "    F – pentru FlashBall",
            "    R – pentru FlashAttack"
    }; ///Textul care se va afisa in fereastra

    /*! \fn public HelpState(GameStateManager gsm)
        \brief Constructorul de initializare al clasei.
        \param gsm O referinta catre un obiect de tip GameStateManager.
     */
    public HelpState(GameStateManager gsm) {
        this.gsm = gsm;
        try{
            ///Seteaza background-ul
            bg = new Background("/Backgrounds/help_bg.png");
            // Seteaza culoarea si fontul textului
            color = new Color(0xFF041B6A, true);
            font = new Font("Courier", Font.PLAIN, 14);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    /*! \fn public void init()
        \brief Initializeaza starea curenta a state-ului.
     */
    public void init() {}

    /*! \fn public void update()
        \brief Actualizeaza starea curenta a state-ului.
     */
    public void update() {
        bg.update();
    }

    /*! \fn public void draw(Graphics2D g)
        \brief Deseneaza (randeaza) pe ecran starea curenta a meniului.
        \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
     */
    public void draw(Graphics2D g) {

        /// Deseneaza background-ul
        bg.draw(g);

        /// Deseneaza textul
        g.setColor(color);
        g.setFont(font);
        for(int i = 0; i < HelpText.length; i++) {
            g.drawString(HelpText[i], 30, 70 + i*20);
        }
        g.drawString("ESC => back to GameMenu", 10,290);
    }
    /*! \fn public void keyPressed(int k)
        \brief Defineste ce schimbari se vor realiza in urma apasarii unei taste.
    */
    public void keyPressed(int k) {
        if(k == KeyEvent.VK_ESCAPE){
            gsm.setState(GameStateManager.MenuState);
        }
    }

    /*! \fn public void keyReleased(int k)
        \brief Defineste ce schimbari se vor realiza in urma eliberarii unei taste.
    */
    public void keyReleased(int k) {}
}
