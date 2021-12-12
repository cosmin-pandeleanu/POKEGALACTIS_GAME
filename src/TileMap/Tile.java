package TileMap;

import java.awt.image.BufferedImage;

/*! \class Tile
    \brief Implementează o dală.
 */
public class Tile {
	
	private final BufferedImage image; /// Imaginea dalei
	private final int type; /// Tipul dalei
	
	/// Tile types
	/// NORMAL - player-ul poate trece prin acest tip de dala
	/// BLOCKED - player-ul intra in coliziune cu acest tip de dala
	public static final int NORMAL = 0;
	public static final int BLOCKED = 1;

	/*! \fn public Tile(BufferedImage image, int type)
		\brief Constructorul de initializare al clasei.
		\param image
		\param type
	 */
	public Tile(BufferedImage image, int type) {
		this.image = image;
		this.type = type;
	}
	/*! \fn public BufferedImage getImage()
		\brief returneaza o imaginea dalei
	*/
	public BufferedImage getImage() { return image; }

	/*! \fn public int getType()
		\brief returneaza o tipul dalei.
	*/
	public int getType() { return type; }
}
