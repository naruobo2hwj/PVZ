package Objects;

public class Sun {
	public int x;
	public float y, speed;
	private int age, lifeSpan;
	
	public Sun (int x, int y, float speed){
		this.x = x;
		this.y = y;
		this.speed = speed;
		lifeSpan = 800;
		this.age = 0;
	}
	
	public Sun (int x, int y, float speed, int lifeSpan){
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.lifeSpan = lifeSpan;
		this.age = 0;
	}
	
	public void move () {
		y += speed;
		if(y > 500) speed = 0;
	}
	
	public void increaseAge(){
		this.age++;
	}
	
	public boolean isDead(){
		if(age >= lifeSpan) return true;
		return false;
	}

}
