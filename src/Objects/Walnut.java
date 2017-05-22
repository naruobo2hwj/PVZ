package Objects;

import PVZG.AnimatedImage;
import PVZG.PVZGame;
import processing.core.PImage;

public class Walnut extends Plant {
	
	PImage walnut;
	
	public Walnut(PVZGame p, int row, int col) {
		super(p, row, col);
		lifeTime = 1500;
		
		walnut = p.loadImage("../resources/walnut.png");
		walnut.resize(dx, dy);
	}
	
	
}