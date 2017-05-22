package Objects;

import PVZG.AnimatedImage;
import PVZG.PVZDisplay;
import PVZG.PVZGame;
import processing.core.PImage;

public class Sunflower extends Plant {
	public int sunTimer = 0;
	
	private int frameRate = 3;
	
	PImage sunflower;
	AnimatedImage restingSunflower;
	
	public Sunflower (PVZGame p, int row, int col) {
		super(p, row, col);
		lifeTime = 600;
		
		restingSunflower = new AnimatedImage(p, "../resources/restingSunflower/", "frame_", "_delay-0.05s", 60, dx, dy);
		
		sunflower = p.loadImage("../resources/sunflowerHD.png");
		sunflower.resize(dx, dy);
	}
	
	public void render(){
//		p.image(sunflower, this.x, this.y);
		restingSunflower.display(this.x, this.y);
	}
	
	public void tick(){
		this.sunTimer++;
		if(PVZDisplay.timeInNanoseconds() % frameRate == 0){
			restingSunflower.tick();
		}
	}
}