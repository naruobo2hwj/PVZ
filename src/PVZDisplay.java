import java.util.ArrayList;

import java.util.LinkedHashMap;

import processing.core.PApplet;
import processing.core.PImage;

public class PVZDisplay {
	private PApplet p; // the applet we want to display on
	
	public int gridX, gridY, w, h; // (x, y) of upper left corner of display | the width and height of the display
	public int dx = 80, dy = 98;
	private int rows = 5, cols = 9;
	
	private int[][] grid;

	private float pauseButtonRadius = 25, pauseButtonX = 980,pauseButtonY = 40;
	
	PImage field, sun, sunflower, peashooter, cherryBomb, walnut, zombie;
	PImage outline, outlineSun, PSeedPacket, SSeedPacket, CBSeedPacket, WSeedPacket;

	public PVZDisplay(PApplet p, int x, int y, int w, int h) {
		this.gridX = x;
		this.gridY = y;
		this.w = w;
		this.h = h;
		this.p = p;

		grid = new int[rows][cols];
		
		field = p.loadImage("../resources/field2.jpg");
		field.resize(1021, 600);
		sun = p.loadImage("../resources/SunFA.png");
		sun.resize(50, 50);
		sunflower = p.loadImage("../resources/sunflowerHD.png");
		sunflower.resize(dx, dy);
		peashooter = p.loadImage("../resources/Peashooter_HD.png");
		peashooter.resize(dx+5, dy);
		cherryBomb = p.loadImage("../resources/cherryBomb_HD.png");
		cherryBomb.resize(dx, dy);
		walnut = p.loadImage("../resources/walnut.png");
		walnut.resize(dx, dy);
		zombie = p.loadImage("../resources/ZombieHD.png");
		zombie.resize(dx, dy+40);
		
		outline = p.loadImage("../resources/sunCountOutline.png");
		outline.resize(250, 100);
		outlineSun = p.loadImage("../resources/SunFA.png");
		outlineSun.resize(120, 120);
		PSeedPacket = p.loadImage("../resources/PSeed_Packet.jpg");
		PSeedPacket.resize(125, 90);
		SSeedPacket = p.loadImage("../resources/SSeed_Packet.png");
		SSeedPacket.resize(125, 90);
		CBSeedPacket = p.loadImage("../resources/CBSeed_Packet.png");
		CBSeedPacket.resize(125, 90);
		WSeedPacket = p.loadImage("../resources/WSeed_Packet.jpg");
		WSeedPacket.resize(125, 90);
	}
	
	public void displayConstants(int sunCount, int black) {
		p.fill(black);
		p.image(outline, 10, 10);
		p.image(outlineSun, 0, 0);
		
		p.image(PSeedPacket, 10, 125);
		p.image(SSeedPacket, 10, 225);
		p.image(CBSeedPacket, 10, 325);
		p.image(WSeedPacket, 10, 425);
		
		p.text(sunCount, 120, 80);
	}
	
	public void drawSuns(ArrayList<Sun> suns) {
		for (Sun r : suns) {
			r.move();
			p.image(sun, r.x, r.y);
		}
	}
		
	public void drawPeashooters(ArrayList<Peashooter> peashooters) {
		for (int i = 0; i < peashooters.size(); i++) {
			Peashooter a = peashooters.get(i);
			p.image(peashooter, a.x, a.y);
		}
	}
	
	public void drawPeas(ArrayList<Ball> peas, int green) {
		p.fill(green);
		for (int i = 0; i < peas.size(); i++) {
			Ball b = peas.get(i);
			p.ellipse(b.x, b.y, b.size, b.size);
		}
	}
	
	public void drawSunflowers(ArrayList<Sunflower> sunflowers) {
		for (int i = 0; i < sunflowers.size(); i++) {
			Sunflower s = sunflowers.get(i);
			p.image(sunflower, s.x, s.y);
		}
	}

	public void drawCherryBombs(ArrayList<CherryBomb> cherryBombs) {
		for (int i = 0; i < cherryBombs.size(); i++) {
			CherryBomb c = cherryBombs.get(i);
			p.image(cherryBomb, c.x, c.y);
		}
	}
	
	public void drawWalnuts(ArrayList<Walnut> walnuts) {
		for (int i = 0; i < walnuts.size(); i++) {
			Walnut w = walnuts.get(i);
			p.image(walnut, w.x, w.y);
		}
	}
	
	public void drawZombies(ArrayList<Zombie> zombies) {
		for (int i = 0; i < zombies.size(); i++) {
			Zombie z = zombies.get(i);
			p.image(zombie, z.x, z.y);
		}
	}
	
	public boolean isValidMove(int r, int c) {
		if (!isInGrid(r, c)) return false;			// if outside grid, not valid
		if (grid[r][c] != 0) return false;			// if not empty, not valid 
		return true;
	}
	
	public boolean move (int r, int c, int plant) {
		if (!isValidMove(r, c)) return false;	// if not valid, exit
		
		grid[r][c] = plant;
		
		return true;
	}
	
	public boolean isInGrid(int r, int c) {
		if(r < rows && r >= 0 && c < cols && c >= 0) return true;
		return false;
	}

	public boolean isInGrid(Location l) {
		return isInGrid(l.getRow(), l.getCol());
	}
	
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
	
	public boolean isInPCard(int x, int y) {
		if (x < 10) return false;
		else if (x > 135) return false;
		else if (y < 125) return false; 
		else if (y > 215) return false;
		return true;
	}
	
	public boolean isInSCard(int x, int y) {
		if (x < 10) return false;
		else if (x > 135) return false;
		else if (y < 225) return false; 
		else if (y > 315) return false;
		return true;
	}
	
	public boolean isInCBCard(int x, int y) {
		if (x < 10) return false;
		else if (x > 135) return false;
		else if (y < 325) return false; 
		else if (y > 415) return false;
		return true;
	}
	
	public boolean isInWCard(int x, int y) {
		if (x < 10) return false;
		else if (x > 135) return false;
		else if (y < 425) return false; 
		else if (y > 515) return false;
		return true;
	}
	
	public boolean hitPaused(int x, int y) {
		int distance = (int)(Math.sqrt((x - pauseButtonX) * (x - pauseButtonX) + (y - pauseButtonY) * (y - pauseButtonY)));
		if (distance <= pauseButtonRadius) return true;
		return false;
	}
	
	public Location gridLocationAt(float mousex, float mousey) {
		Location l = new Location((int) Math.floor((mousey - gridY) / dy),
				(int) Math.floor((mousex - gridX) / dx));
		return l;
	}

	public int[][] getGrid() {
		return grid;
	}
}