package Entity;


/*! \class PlayerSave
    \brief Salveaza informatii despre jucator.
 */
public class PlayerSave {
    private static int lives = 3;
    private static int health = 5;
    private static int score = 0;
    private static long time = 0;
    private static boolean dead = false;

    /*! \fn public static void init()
        \brief Seteaza valorile implicite.
     */
    public static void init() {
        lives = 3;
        health = 5;
        score = 0;
        time = 0;
        dead = false;
    }

    public static int getLives() { return lives; }
    public static void setLives(int i) { lives = i; }

    public static int getHealth() { return health; }
    public static void setHealth(int i) { health = i; }

    public static int getScore() {return score; }
    public static void setScore(int i) {score = i; }

    public static long getTime() { return time; }
    public static void setTime(long t) { time = t; }

    public static boolean getDead() {return dead;}
    public static void setDead(boolean b) {dead = b;}

    public static String getTimeToString() {
        int minutes = (int) (time / 3600);
        int seconds = (int) ((time % 3600) / 60);
        return seconds < 10 ? minutes + ":0" + seconds : minutes + ":" + seconds;
    }

}
