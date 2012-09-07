package ru.reksoft.platformer.objects.players.npc;

import java.util.Random;

import org.newdawn.slick.Graphics;

import ru.reksoft.platformer.PlatformerWorld;
import ru.reksoft.platformer.objects.DynamicGameObject;
import ru.reksoft.platformer.objects.players.Player;

public class NPC extends Player implements Controllable{
	
	private static final int FIRE_RANGE = 400;
	private static final int FIRE_PERIOD = 500;
	private static final int MAX_HP=1;
	float prevX;
	boolean jumped=false;
	boolean moveRigth=true;
	//int hp=MAX_HP;
	
	int startX;
	int startY;
	
	private Player player;
	
	private long lastShoot;
	
	private NpcStrategy strategy;	
	
	
	Random r = new Random();

	public NPC(PlatformerWorld world, int x, int y, NpcStrategy strategy) {
		super(world, x, y);
		prevX=x;
		lastShoot=System.currentTimeMillis();
		startX=x;
		startY=y;
		hp=MAX_HP;
		
		//strategy = new SideToSideStrategy();
		strategy.setNpc(this);
		strategy.setPlayer(player);
		strategy.setWorld(world);
		this.strategy=strategy;
		
		super.BULLET_SPEED=40;
	}

	@Override
	public void collidesWith(DynamicGameObject other) {
		
	}

	@Override
	public void update() {
		strategy.update();
		
	}

	public void shootToPlayer() {
		if(player!=null){
			if(Math.abs(player.getX()-getX())<FIRE_RANGE){
				if(System.currentTimeMillis()-lastShoot>FIRE_PERIOD){
					shootTo((int)player.getX(), (int)player.getY());
					lastShoot=System.currentTimeMillis();
				}
				
			}
		}
	}
	public void move() {
			moveFromSideToSide();
	}

	private void moveFromSideToSide() {
		if(moveRigth){
			stepRight();
		} else{
			stepLeft();
		}
	}

	private void followPlayer() {
		if(getX()>player.getX()){
			stepLeft();
		}else{
			stepRight();
		}
	}
	private void restart(){
		hp=MAX_HP;
		super.getBody().setPosition(startX, startY);
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		super.draw(g, x, y);
		g.drawString(Integer.toString(hp)+"/"+Integer.toString(MAX_HP)+" hp", x-16, y-24);
	}

	@Override
	public String toString() {
		return "SimpleBot: moveRigtht="+moveRigth+" state="+currentState;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public void changeHp(int value) {
		super.changeHp(value);
		if(getHp()<=0){
			onDeath();
		}
	}

	@Override
	public void onDeath() {
		strategy=null;
		getWorld().remove(getBody());
		player.setExp(player.getExp()+1);
	}

}