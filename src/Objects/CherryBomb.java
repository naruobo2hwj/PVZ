package Objects;

import PVZG.AnimatedImage;
import PVZG.PVZGame;
import processing.core.PImage;

public class CherryBomb extends Plant {
	
	PImage cherryBomb;
	
	public CherryBomb(PVZGame p, int row, int col) {
		super(p, row, col);
		lifeTime = 100;
		
		cherryBomb = p.loadImage("../resources/cherryBomb_HD.png");
		cherryBomb.resize(dx, dy);
	}
	
	
}