
import java.util.ArrayList;

import javax.swing.JOptionPane;

import processing.core.*;

public class PVZGame extends PApplet {
	private int plantChosen, round = 1, roundTimer = 0;
	public int dx = 80, dy = 98;
	
	public int green = color(150, 250, 0);
	public int black = color(0, 0, 0);

	public int peaInterval = 120, sunInterval = 2000, spawnInterval, sunSkyInterval = 1500;
	public int spawnTimer = 0, sunTimer = 0;
	public int sunCount = 50;
	private boolean paused = false, gameOver = false;

	ArrayList<Ball> peas;
	ArrayList<Sunflower> sunflowers;
	ArrayList<Sun> suns;
	ArrayList<Peashooter> peashooters;
	ArrayList<CherryBomb> cherryBombs;
	ArrayList<Walnut> walnuts;
	ArrayList<Zombie> zombies;

	PVZDisplay display;

	private Location source;

	PImage field, sun, sunflower, peashooter, cherryBomb, walnut, zombie;
	PImage outline, outlineSun, PSeedPacket, SSeedPacket, CBSeedPacket, WSeedPacket;
	PFont font;

	public void setup() {
		size(1021, 600); // set the size of the screen.

		display = new PVZDisplay(this, 250, 80, 985, 490);

		peas = new ArrayList<Ball>();
		sunflowers = new ArrayList<Sunflower>();
		suns = new ArrayList<Sun>();
		peashooters = new ArrayList<Peashooter>();
		cherryBombs = new ArrayList<CherryBomb>();
		walnuts = new ArrayList<Walnut>();
		zombies = new ArrayList<Zombie>();

		field = loadImage("../resources/field2.jpg");
		field.resize(1021, 600);
		
		
		font = createFont("Georgia", 64);
		textFont(font);
	}

	@Override
	public void draw() {
		background(field);
		
		roundTimer++;
		if(roundTimer > 8000){
			round++;
			System.out.println(round);
			roundTimer = 0;
		}
		
		display.drawPeashooters(peashooters);
		display.drawSunflowers(sunflowers);
		display.drawSuns(suns);
		display.drawPeas(peas, green);
		display.drawCherryBombs(cherryBombs);
		display.drawWalnuts(walnuts);
		display.drawZombies(zombies);
		display.displayConstants(sunCount, black);
		
		//image(walnut, 300, 300);
		
		if (paused == false && gameOver == false) {
			increaseSunTime();
			increasePeaTime();

			spawnZombies();
			moveZombies();

			shootPeas();
			movePeas();
			
			spawnSuns();
			
			detectCollision();
		}
		
		if(gameOver) {
			JOptionPane.showMessageDialog(null, "Game Over!");
		}
	}

	public void mouseClicked() {
		Location loc = display.gridLocationAt(mouseX, mouseY);

		if(paused == false) {
			for (int i = 0; i < suns.size(); i++) {
				if(plantChosen == 0) {
					Sun r = suns.get(i);
					if (mouseX >= r.x && mouseX <= r.x + 50 && mouseY >= r.y && mouseY <= r.y + 50){
						suns.remove(i);
						sunCount+=25;
					}
				}
			}
			
			if (display.isInSCard(mouseX, mouseY) && sunCount >= 50) plantChosen = 1;
			else if (display.isInPCard(mouseX, mouseY) && sunCount >= 100) plantChosen = 2;
			else if (display.isInCBCard(mouseX, mouseY) && sunCount >= 150) plantChosen = 3;
			else if (display.isInWCard(mouseX, mouseY) && sunCount >= 50) plantChosen = 4;
			
			else if (display.hitPaused(mouseX, mouseY)) paused = !paused;
			
			else if (display.move(loc.getRow(), loc.getCol(), plantChosen)) {
				if (plantChosen == 1) {
					Sunflower s = new Sunflower(loc.getRow(), loc.getCol());
					sunflowers.add(s);
					sunCount -= 50;
				} else if (plantChosen == 2) {
					Peashooter p = new Peashooter(loc.getRow(), loc.getCol());
					peashooters.add(p);
					sunCount -= 100;
				} else if (plantChosen == 3) {
					CherryBomb c = new CherryBomb(loc.getRow(), loc.getCol());
					cherryBombs.add(c);
					sunCount -= 1500;
				} else if (plantChosen == 4) {
					Walnut w = new Walnut(loc.getRow(), loc.getCol());
					walnuts.add(w);
					sunCount -= 50;
				}
				plantChosen = 0;
			}
		}
	}
	
	public void detectCollision() {
		for (int b = 0; b < peas.size(); b++) {
			Ball p = peas.get(b);
			
			for (int a = 0; a < zombies.size(); a++) {
				Zombie z = zombies.get(a);
				
				if(p.row == z.row){
					if(p.x >= z.x) {
						peas.remove(b);
						z.lifetime--;
					}
				}
				
				if(z.isDead){
					zombies.remove(a);
				}
			}
		}
		
	}

	public void detectCollision2(){
		for(Sunflower r : sunflowers) {
			for(Zombie z : zombies) {
				
			}
		}
	}
	
	public void increaseSunTime() {
		for (int i = 0; i < sunflowers.size(); i++) {
			Sunflower s = sunflowers.get(i);
			s.sunTimer++;
		}
	}

	public void increasePeaTime() {
		for (int i = 0; i < peashooters.size(); i++) {
			Peashooter p = peashooters.get(i);
			p.peaTimer++;
		}
	}

	public void shootPeas() {
		for (int i = 0; i < zombies.size(); i++) {
			Zombie z = zombies.get(i);

			for (int n = 0; n < peashooters.size(); n++) {
				Peashooter p = peashooters.get(n);

				if (p.row == z.row && p.x < z.x) {
					if (p.peaTimer > peaInterval) {
						Ball b = new Ball(p.row, p.x + dx, (float) 1.5);
						peas.add(b);
						p.peaTimer = 0;
					}
				}
			}
		}
	}

	public void spawnZombies() {
		spawnTimer++;
		if(round == 1) spawnInterval = 2000;
		else if (round == 2) spawnInterval = 1000;
		else if (round == 3) spawnInterval = 500;
		else if (round == 4) spawnInterval = 200;

		if (spawnTimer > spawnInterval) {
			int row = (int) (Math.random() * 5);
			Zombie z = new Zombie(row, (float) 0.15);
			zombies.add(z);
			spawnTimer = 0;
			spawnInterval = (int) (Math.random()*200 + spawnInterval);
		}
	}

	public void moveZombies() {
		for (int i = 0; i < zombies.size(); i++) {
			Zombie z = zombies.get(i);
			z.move();
			if(z.x < 200) gameOver = true;
		}
	}

	public void movePeas() {
		for (int i = 0; i < peas.size(); i++) {
			Ball b = peas.get(i);
			b.move();

			if (b.isDead)
				peas.remove(i);
		}
	}

	public void spawnSuns() {
		spawnSunflowerSuns();
		spawnSkySuns();
	}
	
	public void spawnSunflowerSuns() {
		for (int i = 0; i < sunflowers.size(); i++) {
			Sunflower s = sunflowers.get(i);
			s.sunTimer++;
			if (s.sunTimer > sunInterval){
				Sun r = new Sun (s.x+50, s.y-20, 0);
				suns.add(r);
				s.sunTimer = 0;
			}
		}
	}
	
	public void spawnSkySuns() {
		sunTimer++;
		if(sunTimer > sunSkyInterval) {
			Sun r = new Sun ((int)(Math.random()*800 + 200), -50, (float)0.5);
			suns.add(r);
			sunTimer = 0;
		}
	}
	
	public static void main(String[] args) {
		PApplet.main(new String[] { "PVZGame" });
	}
}