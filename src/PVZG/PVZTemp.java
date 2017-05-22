package PVZG;
import java.util.LinkedHashMap;
import java.util.Map;
import processing.core.*;

public class PVZTemp {
	// Colors used for empty locations.
	private static final int EMPTY_COLOR = 0xFFFFFFFF;

	// Color used for objects that have no defined color.
	private static final int UNKNOWN_COLOR = 0x66666666;

	private PApplet p; // the applet we want to display on

	public int gridX, gridY, w, h; // (x, y) of upper left corner of display
	// the width and height of the display

	public float dx = 80, dy = 98;

	private int rows = 5, cols = 9;

	// A map for storing colors for participants in the simulation
	private Map<Object, Integer> colors;
	private Map<Object, PImage> images;

	
	public PVZTemp(PApplet p, int x, int y, int w, int h) {
		this.gridX = x;
		this.gridY = y;
		this.w = w;
		this.h = h;
		this.p = p;

		colors = new LinkedHashMap<Object, Integer>();
		images = new LinkedHashMap<Object, PImage>();
	}

	/*public void drawGrid(int[][] f, PImage sunflower, PImage peashooter, PImage zombie) {
		int piece;
		int numcols = f[0].length;
		int numrows = f.length;

		for (int i = 0; i < numrows; i++) {
			for (int j = 0; j < numcols; j++) {
				if(f[i][j] == 1) 
			}
		}
	}*/

	/**
	 * Define a color to be used for a given value in the grid.
	 * 
	 * @param pieceType
	 *            The type of piece in the grid.
	 * @param color
	 *            The color to be used for the given type of piece.
	 */
	public void setColor(Object pieceType, Integer color) {
		colors.put(pieceType, color);
	}
	
	/**
	 * Define an Image to be used for a given value in the grid.
	 * 
	 * @param pieceType
	 *            The type of piece in the grid.
	 * @param img
	 *            The image to be used for the given type of piece.
	 */
	public void setImage(Object pieceType, PImage img) {
		images.put(pieceType, img);
	}
	
	/**
	 * Define a color to be used for a given value in the grid.
	 * 
	 * @param pieceType
	 *            The type of piece in the grid.
	 * @param filename
	 *            The file path to the image to be used for the given type of piece.
	 */
	public void setImage(Object pieceType, String filename) {
		PImage img = p.loadImage(filename);
		setImage(pieceType, img);
	}

	/**
	 * @return The color to be used for a given class of animal.
	 */
	private Integer getColor(Object pieceType) {
		Integer col = colors.get(pieceType);
		if (col == null) { // no color defined for this class
			return UNKNOWN_COLOR;
		} else {
			return col;
		}
	}
	
	private PImage getImage(Object pieceType) {
		PImage img = images.get(pieceType);
		return img;
	}

	// Draw a box at the location
	public void highlightLocation(Location l, PVZDisplay g) {
		if (g.isInGrid(l)) {
			p.fill(p.color(50, 200, 50, 150));
			p.rect(xCoordOf(l), yCoordOf(l), dx, dy);
		}
	}

	// return the y pixel value of the upper-left corner of location l
	private float yCoordOf(Location l) {
		return gridY + l.getRow() * dy;
	}

	// return the x pixel value of the upper-left corner of location l
	private float xCoordOf(Location l) {
		return gridX + l.getCol() * dx;
	}

	// Return location at coordinates x, y on the screen
	public Location gridLocationAt(float mousex, float mousey) {
		Location l = new Location((int) Math.floor((mousey - gridY) / dy),
				(int) Math.floor((mousex - gridX) / dx));
		return l;
	}

	// Return whether (x, y) is over the board or not
	public boolean overBoard(float mx, float my) {
		return (mx >= gridX && mx <= gridX + w && my > gridY && my < gridY + h);
	}
}