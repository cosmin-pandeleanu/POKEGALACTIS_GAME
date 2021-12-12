package Entity;

import java.awt.image.BufferedImage;

/*! \class Animation
    \brief Implementează o animatie.
 */
public class Animation {

	private BufferedImage[] frames;
	private int currentFrame;

	private long startTime;
	private long delay;

	private boolean playedOnce;

	/*! \fn public Animation()
		\brief Constructorul de initializare al clasei.
	 */
	public Animation() {
		playedOnce = false;
	}

	/*! \fn public void setFrames(BufferedImage[] frames)
		\brief Seteaza frame-urile unei animatiii.
		\param frames - un array de imagini
	 */
	public void setFrames(BufferedImage[] frames) {
		this.frames = frames;
		currentFrame = 0;
		startTime = System.nanoTime();
		playedOnce = false;
	}

	/*! \fn public void setDelay(long d)
		\brief Seteaza timpul de intarziere.
		\param d - timpul.
	 */
	public void setDelay(long d) {
		delay = d;
	}

	/*! \fn public void setFrame(int i)
		\brief Seteaza frame-ul curent.
		\param i - frame-ul curent
	 */
	public void setFrame(int i) {
		currentFrame = i;
	}

	/*! \fn public void update()
		\brief Actualizeaza animatia.
	 */
	public int getFrame() { return currentFrame; }
	public BufferedImage getImage() { return frames[currentFrame]; }
	public boolean hasPlayedOnce() { return playedOnce; }
	/*! \fn public void update()
		\brief Actualizeaza animatia.
	 */
	public void update() {
		if (delay == -1) return;
		long elapsed = (System.nanoTime() - startTime) / 1000000;
		if (elapsed > delay) {
			currentFrame++;
			startTime = System.nanoTime();
		}
		if (currentFrame == frames.length) {
			currentFrame = 0;
			playedOnce = true;
		}
	}
}