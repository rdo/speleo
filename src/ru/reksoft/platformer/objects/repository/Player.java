package ru.reksoft.platformer.objects.repository;

import java.util.ArrayList;
import java.util.List;

import ru.reksoft.platformer.PlatformerLevel;
import ru.reksoft.platformer.objects.LightSource;
import ru.reksoft.platformer.objects.npc.Person;

public class Player extends Person implements LightSource{
	
	private Class<?> currentWeapon;
	
	private int lightRadius=5;
	
	private List<Class> weapons = new ArrayList<Class>(10);

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
