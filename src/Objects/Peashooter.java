package Objects;

public class Peashooter extends Plant {
	public int peaTimer = 0;
	
	public Peashooter(int row, int col) {
		super(row, col);
		
		lifeTime = 600;
	}
	
	public void tick(){
		this.peaTimer++;
	}
	
}