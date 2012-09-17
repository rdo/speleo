package ru.reksoft.platformer.objects.npc;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import ru.reksoft.platformer.ImageRegistry;
import ru.reksoft.platformer.PlatformerLevel;
import ru.reksoft.platformer.objects.LightSource;
import ru.reksoft.platformer.objects.repository.Bullet;
import ru.reksoft.platformer.objects.repository.Flashlight;
import ru.reksoft.platformer.objects.repository.LightBullet;

public class Player extends Person implements LightSource{
	
	private Class<?> currentWeapon;
	
	private int lightRadius=5;
	
	private List<Class> weapons = new ArrayList<Class>(10);
	
	private Image stopLeft;
	private Image stopRight;
	private Image jumpRight;
	private Image jumpLeft;
	
	private Animation moveLeft;
	
	private Animation moveRight;

	@Override
	public void endJump() {
		float yVelocity = getBody().getYVelocity();
		if(yVelocity>21){
			changeHp(-1);
		}
		super.endJump();
	}

	public Player(PlatformerLevel world, int x, int y) {
		super(world, x, y);
		for(int i=0;i<10;i++){
			weapons.add(null);
		}
		weapons.add(0, Bullet.class);
		weapons.add(1, LightBullet.class);
		weapons.add(2, Flashlight.class);
		currentWeapon=weapons.get(1);
		Image playerTileset = ImageRegistry.getInstance().player;
		stopLeft = playerTileset.getSubImage(0, 0, 52, 56);
		stopRight = playerTileset.getSubImage(52, 0, 52, 56);
		
		jumpLeft = playerTileset.getSubImage(0, 56, 52, 56);
		jumpRight = playerTileset.getSubImage(52, 56, 52, 56);
		
		moveLeft=new Animation(true);
		for(int i=0;i<14;i++){
			moveLeft.addFrame(playerTileset.getSubImage(0, 112+56*i, 52, 56), 100);
		}
		
		moveRight=new Animation(true);
		for(int i=0;i<14;i++){
			moveRight.addFrame(playerTileset.getSubImage(52, 112+56*i, 52, 56), 100);
		}
		
		
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		if(looksRight){
			if(currentState==State.STOP){
				g.drawImage(stopRight, x-25, y-39);
			}else if(currentState==State.JUMP){
				g.drawImage(jumpRight, x-25, y-39);
			}else{
				g.drawAnimation(moveRight, x-25, y-39);
			}
		}else{
			if(currentState==State.STOP){
				g.drawImage(stopLeft, x-25, y-39);
			}else if(currentState==State.JUMP){
				g.drawImage(jumpLeft, x-25, y-39);
			}else{
				g.drawAnimation(moveLeft, x-25, y-39);
			}
		}
		
	}

	@Override
	public int getRadius() {
		return lightRadius;
	}

	@Override
	public Bullet getDefaultBullet() {
		try {
			return (Bullet)currentWeapon.newInstance();
		} catch (Exception e) {
			return new Bullet();
		}
	}

	@Override
	public void onDeath() {
				
	}
	
	public void setWeapon(int code){
		Class newWeapon=weapons.get(code);
		if(newWeapon!=null){
			currentWeapon=newWeapon;
		}
	}

	@Override
	public void setRadius(int radius) {
		lightRadius=radius;
		
	}

}