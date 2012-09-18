package ru.reksoft.platformer.objects.repository;

import org.newdawn.slick.Graphics;

import ru.reksoft.platformer.PlatformerLevel;
import ru.reksoft.platformer.objects.DynamicGameObject;
import ru.reksoft.platformer.objects.npc.Controllable;

public class LightSpawner extends AbstractObject implements Controllable {
	
	private int period=0;
	private int MAX_PERIOD=3000;
	
	private long previousCall;
	
	
	public LightSpawner(PlatformerLevel world, int x, int y) {
		super(world, x, y);
		body.setStatic(true);
		previousCall=System.currentTimeMillis();
		
		

	}

	

	@Override
	public void update() {
		
		if(period>MAX_PERIOD){
			//spawn light
			DynamicLight light = new DynamicLight(world, (int)body.getX(), (int)body.getY()+10);
			period=0;
			
		}else{
			period+=(System.currentTimeMillis()-previousCall);
			previousCall=System.currentTimeMillis();
		}

	}

	@Override
	public void draw(Graphics g, int xOffset, int yOffset) {
	}

	@Override
	public void collidesWith(DynamicGameObject other) {
	}

}
