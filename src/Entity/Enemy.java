package Entity;

import TileMap.TileMap;

/*! \class Enemy extends MapObject
    \brief Implementează un inamic.
 */
public class Enemy extends MapObject {

    protected int health;
    protected int maxHealth;
    protected boolean dead;
    protected int damage;

    protected boolean flinching;
    protected long flinchTimer;

    /*! \fn public Enemy(TileMap tm)
        \brief Constructorul de initializare al clasei.
        \param tm
     */
    public Enemy(TileMap tm) {
        super(tm);
    }

    /*! \fn public void update()
        \brief
    */
    public void update(){}

    /*! \fn public boolean isDead()
        \brief
     */
    public boolean isDead() {
        return dead;
    }

    /*! \fn public int getDamage()
        \brief
     */
    public int getDamage() {
        return damage;
    }

    /*! \fn public void hit(int damage)
        \brief
     */
    public void hit(int damage) {
        if (dead || flinching) return;
        health -= damage;
        if (health < 0) health = 0;
        if (health == 0) dead = true;
        flinching = true;
        flinchTimer = System.nanoTime();
    }

    /*! \fn     protected void getNextPosition()
        \brief
     */
    protected void getNextPosition() {
        // Movement
        if(left) {
            dx -= moveSpeed;
            if(dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        }
        else if(right) {
            dx += moveSpeed;
            if(dx > maxSpeed) {
                dx = maxSpeed;
            }
        }
        // Falling
        if(falling) {
            dy += fallSpeed;
        }
    }
    /*! \fn   protected void checkFlinching()
        \brief
     */
    protected void checkFlinching() {
        // Check flinching
        if(flinching) {
            long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
            if(elapsed > 400) {
                flinching = false;
            }
        }
    }
    /*! \fn  protected void checkDirection()
        \brief
     */
    protected void checkDirection(){
        // If it hits a wall, go other direction
        if(right && dx == 0) {
            right = false;
            left = true;
            facingRight = false;
        }
        else if(left && dx == 0) {
            right = true;
            left = false;
            facingRight = true;
        }
    }
}