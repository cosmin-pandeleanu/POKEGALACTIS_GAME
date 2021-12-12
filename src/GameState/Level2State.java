package GameState;

import Audio.AudioPlayer;
import Database.DataBase;
import Entity.Enemies.*;
import Main.GamePanel;
import TileMap.*;
import Entity.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

/*! \class Level2State extends GameState
    \brief Implementeaza cel de-al doilea nivel al jocului.
 */
public class Level2State extends GameState {

    private Background bg;
    private Player player;
    private TileMap tileMap;

    private ArrayList<Enemy> enemies;
    private ArrayList<Enemy> finalEnemies;
    private ArrayList<Explosion> explosions;

    private Info info;
    private BufferedImage text;
    private Title titleLVL;
    private AudioPlayer bgMusic;

    /// Events
    private int eventCount = 0;
    private boolean eventStart;
    private ArrayList<Rectangle> tb;
    private boolean eventFinish;
    private boolean eventDead;
    private int finEnemies = 1;

    /*! \fn public Level2State(GameStateManager gsm)
        \brief Constructorul de initializare al clasei.
        \param gsm O referinta catre un obiect de tip GameStateManager.
     */
    public Level2State(GameStateManager gsm) {
        this.gsm = gsm;
        init();
    }

    /*! \fn public void init()
        \brief Initializeaza starea curenta state-ului.
    */
    public void init() {

        /// Tile Map
        tileMap = new TileMap(30);
        tileMap.loadTiles("/Tilesets/EarthTileSet.png");
        tileMap.loadMap("/Maps/Level_2.map");
        tileMap.setPosition(0, 0);
        tileMap.setTween(1);

        /// Background
        bg = new Background("/Backgrounds/level2_bg.png");

        /// Player
        player = new Player(tileMap);
        player.setPosition(100, 150);
        player.setLives(PlayerSave.getLives());
        player.setHealth(PlayerSave.getHealth());
        player.setScore(PlayerSave.getScore());
        player.setTime(PlayerSave.getTime());

        /// Info Status
        info = new Info(player);

        /// Background Music
        bgMusic = new AudioPlayer("/Audio/Music/bg_music.mp3");
        bgMusic.play();

        /// Enemies
        populateEnemies();

        /// Explosions
        explosions = new ArrayList<>();

        /// Title
        try {
            text = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/Titles/Titles.png")));
            titleLVL = new Title(text.getSubimage(0, 75, 240, 75));
            titleLVL.setY(60);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        /// Start event
        eventStart = true;
        tb = new ArrayList<>();
        eventStart();
    }

    /*! \fn private void populateEnemies()
        \brief Adauga inamicii pe harta de joc.
    */
    private void populateEnemies() {
        enemies = new ArrayList<>();
        Haunter h;
        Gastly g;
        Point[] points = new Point[] {
                new Point(200, 100),
                new Point(750, 150),
                new Point(100, 250),
                new Point(1250, 200),
                new Point(1750, 150),
                new Point(2250, 200),
                new Point(2700, 200),
                new Point(2900, 100),
                new Point(3050, 200),
                new Point(3650, 200)
        };
        for (Point point : points) {
            h = new Haunter(tileMap);
            h.setPosition(point.x, point.y);
            enemies.add(h);
            g = new Gastly(tileMap);
            g.setPosition(point.x + 75, point.y);
            enemies.add(g);
            g.setPosition(point.x + 150, point.y);
            enemies.add(g);
        }

        // The final enemy
        finalEnemies = new ArrayList<>();
        Point point = new Point(3700,200);
        Dusknoir d;
        d = new Dusknoir(tileMap);
        d.setPosition(point.x , point.y);
        finalEnemies.add(d);
    }

    /*! \fn public void update()
        \brief Actualizeaza starea curenta a nivelului.
    */
    public void update(){

        /// Play events
        if(eventStart) eventStart();
        if(eventDead) eventDead();
        if(eventFinish) eventFinish();

        /// Move title
        if(titleLVL != null) {
            titleLVL.update();
            if(titleLVL.shouldRemove()) { titleLVL = null; }
        }

        /// Check if player dead event should start
        if(player.getHealth() == 0 || player.gety() > tileMap.getHeight()) {
            eventDead = true;
        }
        /// Update player
        player.update();

        /// Update tilemap
        tileMap.setPosition(GamePanel.WIDTH / 2 - player.getx(), GamePanel.HEIGHT / 2 - player.gety());
        tileMap.update();
        tileMap.fixBounds();

        /// Attack enemies
        player.checkAttack(enemies);
        player.checkAttack(finalEnemies);

        /// Update all enemies

        for(int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            e.update();
            if(e.isDead()) {
                enemies.remove(i);
                i--;
                player.updateScore(100);
                explosions.add(new Explosion(e.getx(), e.gety()));
            }
        }
        for(int i = 0; i < finalEnemies.size(); i++) {
            Enemy e = finalEnemies.get(i);
            e.update();
            if(e.isDead()) {
                finalEnemies.remove(i);
                i--;
                player.updateScore(500);
                finEnemies--;
                explosions.add(new Explosion(e.getx(), e.gety()));
            }
        }
        /// Check if end of level event should start
        if( finEnemies == 0) {
            eventFinish = true;
        }

        /// Update explosions
        for(int i = 0; i < explosions.size(); i++) {
            explosions.get(i).update();
            if(explosions.get(i).shouldRemove()) {
                explosions.remove(i);
                i--;
            }
        }
    }

    /*! \fn public void draw(Graphics2D g)
        \brief Deseneaza (randeaza) pe ecran starea curenta a nivelului.
        \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
    */
    public void draw(Graphics2D g)
    {
        /// Draw background
        bg.draw(g);

        /// Draw tilemap
        tileMap.draw(g);

        /// Draw player
        player.draw(g);

        /// Draw info
        info.draw(g);

        /// Draw title
        if(titleLVL != null){ titleLVL.draw(g);}

        /// Draw enemies
        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }
        for (Enemy finalEnemy : finalEnemies) {
            finalEnemy.draw(g);
        }

        /// Draw explosions
        for (Explosion explosion : explosions) {
            explosion.setMapPosition(
                    (int) tileMap.getx(), (int) tileMap.gety());
            explosion.draw(g);
        }

        g.setColor(Color.BLACK);
        for (Rectangle rectangle : tb) {
            g.fill(rectangle);
        }
    }

    /*! \fn public void keyPressed(int k)
       \brief Defineste ce schimbari se vor realiza in cadrul nivelului in urma apasarii unei taste.
    */
    public void keyPressed(int k)
    {
        if(k == KeyEvent.VK_LEFT) player.setLeft(true);
        if(k == KeyEvent.VK_RIGHT) player.setRight(true);
        if(k == KeyEvent.VK_UP) player.setUp(true);
        if(k == KeyEvent.VK_DOWN) player.setDown(true);
        if(k == KeyEvent.VK_UP) player.setJumping(true);
        if(k == KeyEvent.VK_F) player.setFlashing();
        if(k == KeyEvent.VK_R) player.setAttacking();
    }

    /*! \fn public void keyReleased(int k)
        \brief Defineste ce schimbari se vor realiza in cadrul nivelului in urma eliberarii unei taste.
    */
    public void keyReleased(int k)
    {
        if(k == KeyEvent.VK_LEFT) player.setLeft(false);
        if(k == KeyEvent.VK_RIGHT) player.setRight(false);
        if(k == KeyEvent.VK_UP) player.setUp(false);
        if(k == KeyEvent.VK_DOWN) player.setDown(false);
        if(k == KeyEvent.VK_UP) player.setJumping(false);
    }

    /// EVENTS

    /*! \fn private void reset()
        \brief Implementeaza resetarea nivelul atunci cand jucatorul isi pierde o viata.
    */
    private void reset() {
        player.reset();
        player.setPosition(100, 100);
        populateEnemies();
        eventCount = 0;
        tileMap.setShaking(false, 0);
        eventStart = true;
        eventStart();
        titleLVL = new Title(text.getSubimage(0, 75, 240, 75));
        titleLVL.setY(60);
    }

    /*! \fn private void eventStart()
         \brief Implementeaza inceperea nivelului.
     */
    private void eventStart() {
        eventCount++;
        if(eventCount == 1) {
            tb.clear();
            tb.add(new Rectangle(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
            tb.add(new Rectangle(0, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
            tb.add(new Rectangle(0, GamePanel.HEIGHT / 2, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
            tb.add(new Rectangle(GamePanel.WIDTH / 2, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
        }
        if(eventCount > 1 && eventCount < 60) {
            tb.get(0).height -= 4;
            tb.get(1).width -= 6;
            tb.get(2).y += 4;
            tb.get(3).x += 6;
        }
        if(eventCount == 30) titleLVL.begin();
    }

    /*! \fn private void eventDead()
      \brief Implementeaza ce se intampla atunci cand jucatorul isi pierde o viata.
  */
    private void eventDead() {
        eventCount++;
        if(eventCount == 1) {
            player.setDead();
            player.stop();
        }
        if(eventCount == 60) {
            tb.clear();
            tb.add(new Rectangle(GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
        }
        else if(eventCount > 60) {
            tb.get(0).x -= 6;
            tb.get(0).y -= 4;
            tb.get(0).width += 12;
            tb.get(0).height += 8;
        }
        if(eventCount >= 120) {
            if(player.getLives() == 1) {
                PlayerSave.setHealth(player.getHealth());
                PlayerSave.setLives(player.getLives());
                PlayerSave.setScore(player.getScore());
                PlayerSave.setTime(player.getTime());
                PlayerSave.setDead(true);
                DataBase.insertDB("GAME LOST ","LEVEL 2", PlayerSave.getScore(), PlayerSave.getLives()-1, PlayerSave.getHealth(), PlayerSave.getTimeToString());
                bgMusic.stop();
                gsm.setState(GameStateManager.StatusState);
            }
            else {
                eventDead = false;
                eventCount = 0;
                player.loseLife();
                reset();
            }
        }
    }

    /*! \fn private void eventFinish()
        \brief Implementeaza ce se intampla atunci cand jucatorul termina nivelul.
    */
    private void eventFinish() {
        eventCount++;
        if(eventCount == 1) {
            player.stop();
        }
        else if(eventCount == 120) {
            tb.clear();
            tb.add(new Rectangle(GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
        }
        else if(eventCount > 120) {
            tb.get(0).x -= 6;
            tb.get(0).y -= 4;
            tb.get(0).width += 12;
            tb.get(0).height += 8;
        }
        if(finEnemies == 0) {
            PlayerSave.setHealth(player.getHealth());
            PlayerSave.setLives(player.getLives());
            PlayerSave.setScore(player.getScore());
            PlayerSave.setTime(player.getTime());
            PlayerSave.setDead(false);
            DataBase.insertDB("GAME WON ", "LEVEL 2", PlayerSave.getScore(), PlayerSave.getLives(), PlayerSave.getHealth(), PlayerSave.getTimeToString());
            bgMusic.stop();
            gsm.setState(GameStateManager.StatusState);
        }
    }
}