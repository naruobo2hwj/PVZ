
public class Sun {
	public int x;
	public float y, speed;
	
	public Sun (int x, int y, float speed){
		this.x = x;
		this.y = y;
		this.speed = speed;
	}
	
	public void move () {
		y += speed;
		if(y > 500) speed = 0;
	}

}
