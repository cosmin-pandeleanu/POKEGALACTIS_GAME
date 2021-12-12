package Database;

import java.io.File;
import java.sql.*;

/*! \class DataBase
    \brief Implementeaza baza de date.
 */
public class DataBase {

    /*! \fn public static void createDB()
        \brief Creeaza baza de date,daca nu exista deja in proiect.
     */
    public static void createDB() {
        Connection c = null;
        Statement stmt = null;
        File db = new File("POKEGALACTIS_DB.db");
        boolean exits = db.exists();
        if(!exits){
            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:POKEGALACTIS_DB.db");
                c.setAutoCommit(false);
                stmt = c.createStatement();
                String sql = "CREATE TABLE POKEGALACTIS " +
                        "(STATUS TEXT NOT NULL," +
                        "LEVEL INT NOT NULL," +
                        "SCORE INT NOT NULL," +
                        "LIVES INT NOT NULL," +
                        "HEALTH INT NOT NULL," +
                        "TIME TEXT NOT NULL)";

                stmt.executeUpdate(sql);
                stmt.close();
                c.commit();
                c.close();

            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
            System.out.println("DataBase created!");
        }
        else
            System.out.println("DataBase already exists!");
    }

    /*! \fn public static void insertDB(String status,String level, int score, int lives, int health, String time) {
        \brief Insereaza in baza de date informatii noi.
    */
    public static void insertDB(String status,String level, int score, int lives, int health, String time) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:POKEGALACTIS_DB.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "INSERT INTO POKEGALACTIS (STATUS, LEVEL, SCORE, LIVES, HEALTH, TIME) " +
                        "VALUES ('"+ status +"', '" + level + "', "+ score + ", " + lives + ", "+ health + ", '" + time + "')";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        }
        catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

}


