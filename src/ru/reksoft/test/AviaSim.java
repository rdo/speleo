package ru.reksoft.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class AviaSim extends BasicGame {
	Image backgroundPic = null;
	Image playerPoc = null;
	Image enemyPic=null;
	Image explosionPic =null;
	float x=400;
	float y=300;
	float ay=0;
	float ax=0;
	int horizon=600;
	
	boolean isFiring=false;
	int killCounter=0;
	
	private List<Enemy> enemies = new ArrayList<Enemy>();
	public AviaSim() {
		super("PAK FA vs F-22");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		backgroundPic=new Image("data/background.jpg");
		playerPoc= new Image("data/player.png");
		enemyPic = new Image("data/enemy.png");
		explosionPic = new Image("data/explosion.png");
		enemies.add(new Enemy());
		enemies.add(new Enemy());
		enemies.add(new Enemy());
		enemies.add(new Enemy());
		
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		horizon-=5; 
		if(horizon==0) horizon=600;
		Input in = container.getInput();
		
		for (int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			if(e.destroyed==true){
				enemies.remove(e);
				enemies.add(new Enemy());
			}
			if (e.isVisible()) {
				e.move();
			} else {
				e.destroyed=true;
			}
			if(isFiring && (Math.abs(x-e.x)<100)){
				e.destroyed=true;
				killCounter++;
			}
		}
		
		checkInput(in);
		checkBorders();
		
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		Image upperPart = backgroundPic.getSubImage(0, horizon, 800, 600-horizon);
		Image downPart=backgroundPic.getSubImage(0, 0, 800, horizon);
		g.drawImage(upperPart, 0, 0);
		g.drawImage(downPart, 0, 600-horizon);
		g.drawString("Enemies killed: "+killCounter, 0, 0);
		
		for (Enemy e : enemies) {
			if(e.destroyed==true){
				g.drawImage(explosionPic, e.x, e.y);
				
			}else{
				g.drawImage(enemyPic, e.x, e.y);
			}
		}
		
		g.drawImage(playerPoc, x, y);
	}

	public static class Enemy{
		static Random r = new Random();
		int y;
		int x;
		boolean destroyed=false;
		private Enemy() {
			x=Math.abs(r.nextInt()%800);
			y=-100+r.nextInt()%100;
		}
		
		public void move(){
			y=y+15;
		}
		public boolean isVisible(){
			return y<600;
		}
		
	}
	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new AviaSim());
			app.setDisplayMode(800, 600, false);
			app.setTargetFrameRate(20);
			app.setShowFPS(false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	private void checkBorders() {
		if (y < 0) {
			y = 0;
			ay=0;
		}
		if(y>500) {
			y=500;
			ay=0;
		}
		if(x<0){
			x=0;
			ax=0;
		}
		if(x>700){
			x=700;
			ax=0;
		}
	}

	private void checkInput(Input in) {
		if(in.isKeyDown(Input.KEY_W)){
			ay-=1;
		}
		if(in.isKeyDown(Input.KEY_S)){
			ay+=1;
		}
		if(in.isKeyDown(Input.KEY_A)){
			ax-=1;
		}
		if(in.isKeyDown(Input.KEY_D)){
			ax+=1;
		}
		if(in.isKeyDown(Input.KEY_ENTER)){
			isFiring=true;
		}else{
			isFiring=false;
		}
		y=y+ay;
		x=x+ax;
	}
}