package PVZG;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import Objects.Ball;
import Objects.CherryBomb;
import Objects.Peashooter;
import Objects.Plant;
import Objects.Sun;
import Objects.Sunflower;
import Objects.Walnut;
import Objects.Zombie;
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

	ArrayList<Plant> plants;
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
	
	String musicFileName;
	MP3 backgroundMusic;
	int songTimer;

	public void setup() {
		size(1021, 600); // set the size of the screen.

		display = new PVZDisplay(this, 250, 80, 985, 490);

		musicFileName = "C:\\SCCZ\\PVZ\\resources\\Despacito.mp3";
		backgroundMusic = new MP3(musicFileName);
		backgroundMusic.play();

		plants = new ArrayList<Plant>();
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

		display.renderSuns(suns);
		display.renderPeas(peas, green);
		display.renderPlants(plants);
		display.renderZombies(zombies);
		display.displayConstants(sunCount, black);
		if(paused) display.displayGamePaused();

		if (paused == false && gameOver == false) {
			render();
			tick();
		}
		
		if(gameOver) {
			JOptionPane.showMessageDialog(null, "Game Over!");
			backgroundMusic.close();
			System.exit(0);
		}
	}
	
	public void render(){
//		display.render();
	}
	
	public void tick() {
		for (Sun s : suns) s.tick();
		for (Sunflower s : sunflowers) s.tick();
		for (Peashooter p : peashooters) p.tick();
		for (CherryBomb c : cherryBombs) c.tick();
		for (Zombie z : zombies) z.tick();
		for (Ball b : peas) b.tick();
		
		
		spawnZombies();
		spawnSuns();
		
		shootPeas();
		
		detectCollision();
		detectCollision2();
		detectCollision3();
		
		checkIfDead();
		
		display.tick();
	}

	public void mouseClicked() {
		Location loc = display.gridLocationAt(mouseX, mouseY);
		if(paused)
			if(display.hitResume(mouseX, mouseY))
				paused = false;
		
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
			
			else if (display.hitPaused(mouseX, mouseY)) paused = true;
			
			else if (display.move(loc.getRow(), loc.getCol(), plantChosen)) {
				if (plantChosen == 1) {
					Sunflower s = new Sunflower(this, loc.getRow(), loc.getCol());
					sunflowers.add(s);
					plants.add(s);
					sunCount -= 50;
				} else if (plantChosen == 2) {
					Peashooter p = new Peashooter(this, loc.getRow(), loc.getCol());
					peashooters.add(p);
					plants.add(p);
					sunCount -= 100;
				} else if (plantChosen == 3) {
					CherryBomb c = new CherryBomb(this, loc.getRow(), loc.getCol());
					cherryBombs.add(c);
					plants.add(c);
					sunCount -= 150;
				} else if (plantChosen == 4) {
					Walnut w = new Walnut(this, loc.getRow(), loc.getCol());
					walnuts.add(w);
					plants.add(w);
					sunCount -= 50;
				}
				plantChosen = 0;
			}
		}
	}
	
	public void detectCollision() {
		for (int i = 0; i < peas.size(); i++) {
			Ball b = peas.get(i);
			
			for (int a = 0; a < zombies.size(); a++) {
				Zombie z = zombies.get(a);
				
				if(b.row == z.row){
					if(b.x >= z.x) {
						peas.remove(i);
						z.lifetime--;
					}
				}
			}
		}
		
	}

	public void detectCollision2(){
		for(Plant p : plants) {
			for(Zombie z : zombies) {
				if(p.zombieReached(z)) {
					z.startEating();
				}
			}
		}
	}
	
	public void detectCollision3(){
		for(CherryBomb c : cherryBombs) {
			for(Zombie z : zombies) {
				if(c.isPowie())
					if(Math.abs(z.row - c.row) <= 1 && Math.abs(z.x - c.x) <= 100)
						z.isBurnt = true;
			}
		}
	}

	public void shootPeas() {
		for (int i = 0; i < zombies.size(); i++) {
			Zombie z = zombies.get(i);

			for (int n = 0; n < peashooters.size(); n++) {
				Peashooter p = peashooters.get(n);

				if (p.row == z.row && p.x < z.x) {
					if (p.peaTimer > peaInterval) {
						Ball b = new Ball(this, p.row, p.x + dx, (float) 1.5);
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
			Zombie z = new Zombie(this, row, (float) 0.15);
			zombies.add(z);
			spawnTimer = 0;
			spawnInterval = (int) (Math.random()*200 + spawnInterval);
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
				Sun r = new Sun (this, s.x+50, s.y-20, 0);
				suns.add(r);
				s.sunTimer = 0;
			}
		}
	}
	
	public void spawnSkySuns() {
		sunTimer++;
		if(sunTimer > sunSkyInterval) {
			Sun r = new Sun (this, (int)(Math.random()*800 + 200), -50, (float)0.5);
			suns.add(r);
			sunTimer = 0;
		}
	}
	
	public void checkIfDead(){
		for (Iterator<Ball> iterator = peas.iterator(); iterator.hasNext(); ) {
		    Ball b = iterator.next();
		    if (b.isDead) iterator.remove();
		}
		
		for (Iterator<Sun> iterator = suns.iterator(); iterator.hasNext(); ) {
		    Sun s = iterator.next();
		    if (s.isDead()) iterator.remove();
		}
		
		for (Iterator<Plant> iterator = plants.iterator(); iterator.hasNext(); ) {
		    Plant p = iterator.next();
		    if (p.isDead()) {
		        iterator.remove();
		        removePlant(p);
		    }
		}
		
		for (Iterator<Zombie> iterator = zombies.iterator(); iterator.hasNext(); ) {
		    Zombie z = iterator.next();
		    if(z.x < 200) gameOver = true;
		    if (z.isDead) iterator.remove();
		}
	}
	
	public void removePlant(Plant p){
		if (p instanceof Sunflower) sunflowers.remove(p);
		else if (p instanceof Peashooter) peashooters.remove(p);
		else if (p instanceof CherryBomb) cherryBombs.remove(p);
		else if (p instanceof Walnut) walnuts.remove(p);
	}
	
	public void removeZombie (Zombie z){
		zombies.remove(z);
	}
	
	public static void main(String[] args) {
		PApplet.main(new String[] { "PVZG.PVZGame" });
	}
}