package Objects;

import PVZG.AnimatedImage;
import PVZG.PVZDisplay;
import PVZG.PVZGame;
import processing.core.PImage;

public class Peashooter extends Plant {
	public int peaTimer = 0;
	
	PImage peashooter;
	AnimatedImage spittingPeashooter, restingPeashooter;
	private int frameRate = 2;
	
	public Peashooter(PVZGame p, int row, int col) {
		super(p, row, col);
		lifeTime = 600;
		
		spittingPeashooter = new AnimatedImage(p, "../resources/peashooterSpit/", "frame_", "_delay-0.02s", 60, dx, dy);
		restingPeashooter = new AnimatedImage(p, "../resources/restingPeashooter/", "frame_", "_delay-0.02s", 60, dx, dy);
		
		peashooter = p.loadImage("../resources/Peashooter_HD.png");
		peashooter.resize(dx+5, dy);
	}

	public void render(){
//		p.image(peashooter, this.x, this.y);
//		spittingPeashooter.display(this.x, this.y);
		restingPeashooter.display(this.x, this.y);
	}
	
	public void tick(){
		this.peaTimer++;
		if(PVZDisplay.timeInNanoseconds() % frameRate == 0){
			spittingPeashooter.tick();
			restingPeashooter.tick();
		}
	}
	
}