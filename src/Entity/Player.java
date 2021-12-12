package Entity;

import Audio.AudioPlayer;
import TileMap.*;

import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Objects;
/*! \class Player extends MapObject
    \brief Implementează jucatorul.
 */
public class Player extends MapObject {
	
	/// Player stuff
	private int lives;
	private final int maxLives;
	private int health;
	private final int maxHealth;
	private int flash;
	private final int maxFlash;
	private int score;
	private long time;
	private boolean dead;
	private boolean flinching;
	private long flinchTimer;
	
	/// FlashBall
	private boolean flashing;
	private final int flashCost;
	private final int flashBallDamage;
	private final ArrayList<FlashBall> flashBalls;
	
	/// Flash attack
	private boolean attacking;
	private final int attackCost;
	private final int attackDamage;
	private final ArrayList<FlashAttack> flashAttacks;

	/// Animations
	private ArrayList<BufferedImage[]> sprites;

	/// Animation actions
	private static final int IDLE = 1;
	private static final int WALKING = 0;
	private static final int JUMPING = 2;
	private static final int FALLING = 2;
	private static final int ATTACKING = 4;
	private static final int FLASHBALL = 7;
	private static final int DEAD = 8;

	///SoundEffects
	private final HashMap<String , AudioPlayer> sfx;

	/*! \fn public Player(TileMap tm)
		\brief Constructorul de initializare al clasei.
		\param tm
 */
	public Player(TileMap tm) {
		
		super(tm);
		width = 50;
		height = 50;
		cwidth = 40;
		cheight = 40;
		
		moveSpeed = 3;
		maxSpeed = 3;
		stopSpeed = 0.4;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -6;
		stopJumpSpeed = 0.1;
		
		facingRight = true;

		lives = maxLives = 3;
		health = maxHealth = 5;
		flash = maxFlash = 2500;
		score = 0;

		/// Flash ball
		flashCost = 200;
		flashBallDamage = 10;
		flashBalls = new ArrayList<FlashBall>();

		/// Flash attack
		attackCost = 150;
		attackDamage = 10;
		flashAttacks = new ArrayList<FlashAttack>();

		/// Load sprites
		try {
			BufferedImage spritesheet = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/Pikachu/PikachuSprite.png")));
			sprites = new ArrayList<BufferedImage[]>();
			int[] numFrames = {4, 4, 2, 4, 2, 3, 4, 4, 3};
			for(int i = 0; i < 6; i++)
			{
				BufferedImage[] bi = new BufferedImage[numFrames[i]];
				for(int j = 0; j < numFrames[i]; j++) {
					bi[j] = spritesheet.getSubimage(j * width, i * height, width, height);
				}
				sprites.add(bi);
			}
			spritesheet = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/Pikachu/PikachuSprite2.png")));
			for(int i = 0; i < 3; i++)
			{
				BufferedImage[] bi = new BufferedImage[numFrames[i+6]];
				for(int j = 0; j < numFrames[i+6]; j++) {
					bi[j] = spritesheet.getSubimage(j * (width+10), i * (height+10), width+10, height+10);
				}
				sprites.add(bi);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		/// Animatia initiala
		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(500);


		/// SoundEffects
		sfx = new HashMap<>();
		sfx.put("jump", new AudioPlayer("/Audio/SFX/jump.mp3"));
		sfx.put("pikachu", new AudioPlayer("/Audio/SFX/pikachu.mp3"));
		sfx.put("attack", new AudioPlayer("/Audio/SFX/attack.mp3"));
	}

	public int getLives() { return lives; }
	public int getMaxLives() { return maxLives; }
	public void setLives(int i) { lives = i; }
	public void loseLife() { lives--; }
	public int getHealth() { return health; }
	public void setHealth(int i) { health = i; }
	public int getMaxHealth() { return maxHealth; }
	public int getFlash() { return flash; }
	public int getMaxFlash() { return maxFlash; }
	public int getScore(){return score;}
	public void setScore(int i) {score = i;}
	public void updateScore(int i){ score+= i;}
	public long getTime() { return time; }
	public void setTime(long t) { time = t; }
	public void setFlashing() {
		flashing = true;
	}
	public void setAttacking() {
		attacking = true;
	}
	public void setDead() {
		health = 0;
		dead = true;
		stop();
	}



	/*! \fn public void checkAttack(ArrayList<Enemy> enemies)
		\brief verifica atacurile player-ului contra inamicilor si loviturile inamicilor contra player-ului.
		\param enemies Array-ul de inamici aflati in nivelul curent
	 */
	public void checkAttack(ArrayList<Enemy> enemies) {
		/// Loop through enemies
		for (Enemy e : enemies) {
			/// FlashBalls
			for (FlashBall flashBall : flashBalls) {
				if (flashBall.intersects(e)) {
					e.hit(flashBallDamage);
					flashBall.setHit();
					break;
				}
			}
			/// FlashAttacks
			for (FlashAttack flashAttack : flashAttacks) {
				if (flashAttack.intersects(e)) {
					e.hit(attackDamage);
					flashAttack.setHit();
					break;
				}
			}
			/// Check enemy collision
			if (intersects(e)) {
				hit(e.getDamage());
			}
		}
	}

	/*! \fn public void hit(int damage)
		\brief Scade nivelul de viata atunci cand player-ul este lovit de un inamic.
		\param damage - valoare cu care scade nivelul de viata.
	 */
	public void hit(int damage) {
		if(flinching) return;
		health -= damage;
		if(health < 0) health = 0;
		if(health == 0) dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
	}

	/*! \fn public void reset()
		\brief Reseteaza valorile atunci cand jucatorul isi pierde o viata.
	 */
	public void reset() {
		health = maxHealth;
		facingRight = true;
		currentAction = -1;
		stop();
	}

	/*! \fn public void stop()
		\brief Dezactiveaza toate actiunile pe care le poate face player-ul.
	 */
	public void stop() {
		left = right = up = down = flinching = jumping = attacking  = flashing =  false;
	}

	/*! \fn public void getNextPosition()
		\brief Ofera urmatoare pozitie pe harta
	 */
	private void getNextPosition() {
		
		// Movement
		if(left) {
			dx -= moveSpeed;
			if(dx < -maxSpeed) { dx = -maxSpeed; }
		}
		else if(right) {
			dx += moveSpeed;
			if(dx > maxSpeed) { dx = maxSpeed; }
		}
		else {
			if(dx > 0) {
				dx -= stopSpeed;
				if(dx < 0) { dx = 0; }
			}
			else if(dx < 0) {
				dx += stopSpeed;
				if(dx > 0) { dx = 0; }
			}
		}
		
		/// Cannot move while attacking, except in air
		if((currentAction == ATTACKING || currentAction == FLASHBALL) && !(jumping || falling)) {
			dx = 0;
		}
		
		/// Jumping
		if(jumping && !falling) {
			dy = jumpStart;
			falling = true;	
		}
		/// Falling
		if(falling) {
			if(dy > 0) dy += fallSpeed * 0.9;
			else dy += fallSpeed;
			if(dy > 0) jumping = false;
			if(dy < 0 && !jumping) dy += stopJumpSpeed;
			if(dy > maxFallSpeed) dy = maxFallSpeed;
		}
	}

	/*! \fn public void update()
		\brief Actualizeaza player-ul.
	 */
	public void update() {
		/// Update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);

		/// Check attack has stopped
		if(currentAction == ATTACKING) {
			if(animation.hasPlayedOnce()) attacking = false;
		}
		if(currentAction == FLASHBALL) {
			if(animation.hasPlayedOnce()) flashing = false;
		}

		///SoundEffects
		if(attacking){
			flashing = false;
			sfx.get("pikachu").play();
		}
		if(flashing){
			attacking = false;
			sfx.get("pikachu").play();
		}
		if(jumping){
			sfx.get("jump").play();
		}


		/// Check done flinching
		if(flinching) {
			long elapsed =
					(System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed > 1000) {
				flinching = false;
			}
		}

		/// FlashBall
		flash += 1;
		if(flash > maxFlash) flash = maxFlash;
		if(flashing && currentAction != FLASHBALL) {
			if(flash > flashCost) {
				flash -= flashCost;
				FlashBall fb = new FlashBall(tileMap, facingRight);
				fb.setPosition(x, y);
				flashBalls.add(fb);
			}
		}
		/// Update Flashballs
		for(int i = 0; i < flashBalls.size(); i++) {
			flashBalls.get(i).update();
			if(flashBalls.get(i).shouldRemove()) {
				flashBalls.remove(i);
				i--;
			}
		}

		/// FlashAttack
		flash+= 1;
		if(flash > maxFlash) flash = maxFlash;
		if(attacking && currentAction != ATTACKING) {
			if(flash > attackCost) {
				flash -= attackCost;
				FlashAttack fb = new FlashAttack(tileMap, facingRight);
				fb.setPosition(x, y);
				flashAttacks.add(fb);
			}
		}
		/// Update FlashAttacks
		for(int i = 0; i < flashAttacks.size(); i++) {
			flashAttacks.get(i).update();
			if(flashAttacks.get(i).shouldRemove()) {
				flashAttacks.remove(i);
				i--;
			}
		}
		/// Set animation
		if(attacking) {
			if(currentAction != ATTACKING) {
				currentAction = ATTACKING;
				animation.setFrames(sprites.get(ATTACKING));
				animation.setDelay(150);
				width = 50;
			}
		}
		else if(flashing) {
			if(currentAction != FLASHBALL) {
				currentAction = FLASHBALL;
				animation.setFrames(sprites.get(FLASHBALL));
				animation.setDelay(150);
				width = 60;
			}
		}
		else if(dy > 0) {
			if(currentAction != FALLING) {
				currentAction = FALLING;
				animation.setFrames(sprites.get(FALLING));
				animation.setDelay(100);
				width = 50;
			}
		}
		else if(dy < 0) {
			if(currentAction != JUMPING) {
				currentAction = JUMPING;
				animation.setFrames(sprites.get(JUMPING));
				animation.setDelay(-1);
				width = 50;
			}
		}
		else if(left || right) {
			if(currentAction != WALKING) {
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(40);
				width = 50;
			}
		}
		else {
			if(currentAction != IDLE) {
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(400);
				width = 50;
			}
		}
		if(health == 0) {
			if(currentAction != DEAD) {
				currentAction = DEAD;
				animation.setFrames(sprites.get(DEAD));
				animation.setDelay(1000);
				width = 50;
			}
			stop();
		}
		
		animation.update();
		
		/// Set direction
		if(currentAction != ATTACKING && currentAction != FLASHBALL) {
			if(right) facingRight = true;
			if(left) facingRight = false;
		}
		time++;
	}

	/*! \fn public void draw(Graphics2D g)
		\brief Deseneaza (randeaza) pe ecran player-ul si atacurile.
		\param g Contextul grafic in care trebuie sa deseneze.
	 */
	public void draw(Graphics2D g) {

		setMapPosition();
		/// Draw fireBalls
		for (FlashBall flashBall : flashBalls) {
			flashBall.draw(g);
		}

		/// Draw flashAttacks
		for (FlashAttack flashAttack : flashAttacks) {
			flashAttack.draw(g);
		}

		/// Draw player
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed / 100 % 2 == 0) {
				return;
			}
		}
		super.draw(g);
	}
}