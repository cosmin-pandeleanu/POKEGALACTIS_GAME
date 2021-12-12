package TileMap;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.Objects;
import javax.imageio.ImageIO;
import Main.GamePanel;

/*! \class TileMap
    \brief Implementeaza harta jocului.
 */
public class TileMap {
	
	/// Position
	private double x;
	private double y;
	
	/// Bounds
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;
	
	private double tween;
	
	/// Map
	private int[][] map;
	private final int tileSize;
	private int numRows;
	private int numCols;
	private int width;
	private int height;

	private int numTilesAcross;
	private Tile[][] tiles;
	
	/// Drawing
	private int rowOffset;
	private int colOffset;
	private final int numRowsToDraw;
	private final int numColsToDraw;

	/// Effects
	private boolean shaking;
	private int intensity;

	/*! \fn public TileMap(int tileSize)
		\brief Constructorul de initializare al clasei.
		\param tileSize - dimensiunea unei dale.
	 */
	public TileMap(int tileSize) {
		this.tileSize = tileSize;
		numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
		numColsToDraw = GamePanel.WIDTH / tileSize + 2;
		tween = 0.07;
	}

	/*! \fn public void loadTiles(String s)
    	\brief Incarca in memorie dalele folosite pentru harta
    	\param s path-ul catre setul de dale
 	*/
	public void loadTiles(String s) {
		try {
			/// Tileset
			BufferedImage tileset = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(s)));
			numTilesAcross = tileset.getWidth() / tileSize;
			tiles = new Tile[2][numTilesAcross];
			
			BufferedImage subimage;
			subimage = tileset.getSubimage(0, 0, tileSize, tileSize );
			tiles[0][0] = new Tile(subimage, Tile.NORMAL);
			for(int col = 1; col < numTilesAcross; col++) {
				subimage = tileset.getSubimage(col * tileSize, 0, tileSize, tileSize );
				tiles[0][col] = new Tile(subimage, Tile.BLOCKED);
				subimage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
				tiles[1][col] = new Tile(subimage, Tile.BLOCKED);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/*! \fn public void loadMap(String s)
		\brief Incarca in memorie matricea dupa care se va genera harta
		\param s path-ul catre fisierul ce contine matricea hartii
	 */
	public void loadMap(String s) {
		try {
			InputStream in = getClass().getResourceAsStream(s);
			assert in != null;
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());
			map = new int[numRows][numCols];
			width = numCols * tileSize;
			height = numRows * tileSize;
			
			xmin = GamePanel.WIDTH - width;
			xmax = 0;
			ymin = GamePanel.HEIGHT - height;
			ymax = 0;
			
			String delims = "\\s+";
			for(int row = 0; row < numRows; row++) {
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for(int col = 0; col < numCols; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/*! \fn public int getTileSize()
 	    \brief Returneaza tileSize.
	*/
	public int getTileSize() { return tileSize; }

	/*! \fn public int getx()
		\brief Returneaza x.
	*/
	public double getx() { return x; }

	/*! \fn public int gety
		\brief Returneaza y.
	*/
	public double gety() { return y; }

	/*! \fn public int getWidth()
		\brief Returneaza latimea.
	*/
	public int getWidth() { return width; }

	/*! \fn public int getHeight()
		\brief Returneaza inaltimea.
	*/
	public int getHeight() { return height; }

	/*! \fn public int getType(int row, int col)
		\brief Returneaza tipul dalei.
	*/
	public int getType(int row, int col) {
		int rc = map[row][col];
		int r = rc / numTilesAcross;
		int c = rc % numTilesAcross;
		return tiles[r][c].getType();
	}

	public void setTween(double d) { tween = d; }
	public void setShaking(boolean b, int i) { shaking = b; intensity = i; }
	public void setPosition(double x, double y) {

		this.x += (x - this.x) * tween;
		this.y += (y - this.y) * tween;
		fixBounds();
		colOffset = (int)-this.x / tileSize;
		rowOffset = (int)-this.y / tileSize;
	}
	
	public void fixBounds() {
		if(x < xmin) x = xmin;
		if(y < ymin) y = ymin;
		if(x > xmax) x = xmax;
		if(y > ymax) y = ymax;
	}

	/*! \fn public void update()
		\brief Actualizeaza hartii
	 */
	public void update() {
		if(shaking) {
			this.x += Math.random() * intensity - intensity / 2;
			this.y += Math.random() * intensity - intensity / 2;
		}
	}
	/*! \fn public void draw(Graphics2D g)
 	    \brief Deseneaza (randeaza) pe ecran harta jocului.
    	\param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
 	*/
	public void draw(Graphics2D g) {
		for(int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {
			if(row >= numRows) break;
			for(int col = colOffset; col < colOffset + numColsToDraw; col++) {
				if(col >= numCols) break;
				if(map[row][col] == 0) continue;
				int rc = map[row][col];
				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;
				g.drawImage(tiles[r][c].getImage(), (int)x + col * tileSize, (int)y + row * tileSize, null);
			}
		}
	}
}