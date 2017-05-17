import processing.core.PApplet;
import processing.core.PImage;

public class AnimatedImage{
  PImage[] images;
  int imageCount;
  int frame = 0;
  public boolean finishedFirstLoop = false;
  PApplet p;
  
  public AnimatedImage(PApplet p, String filepath, String imagePrefix, String imageSuffix, int count, int x, int y) {
    imageCount = count;
    images = new PImage[imageCount];
    this.p = p;

    for (int i = 0; i < imageCount; i++) {
      String filename = filepath + imagePrefix + i + imageSuffix + ".gif";
      images[i] = p.loadImage(filename);
      images[i].resize(x, y);
    }
  }

  public void display(float xpos, float ypos) {
	if(frame == imageCount-1) finishedFirstLoop = true;
    p.image(images[frame], xpos, ypos);
  }
  
  public void tick(){
	  frame = (frame+1) % imageCount;
  }
  
  public int getWidth() {
    return images[0].width;
  }
}