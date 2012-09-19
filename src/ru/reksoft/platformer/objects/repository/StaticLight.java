package ru.reksoft.platformer.objects.repository;

import org.newdawn.fizzy.Body;
import org.newdawn.slick.Graphics;

import ru.reksoft.platformer.objects.DynamicGameObject;
import ru.reksoft.platformer.objects.LightSource;
import ru.reksoft.platformer.states.play.PlatformerLevel;

public class StaticLight extends AbstractObject implements LightSource{
	
	private int radius=2;

	public StaticLight(PlatformerLevel world, int x, int y) {
		super(world, x, y);
		super.body.setStatic(true);
	}

	@Override
	public int getRadius() {
		return radius;
	}

	@Override
	public Body getBody() {
		return super.body;
	}

	@Override
	public void draw(Graphics g, int xOffset, int yOffset) {
		
	}

	@Override
	public void collidesWith(DynamicGameObject other) {
		world.remove(body);
		
	}

	@Override
	public void setRadius(int radius) {
		this.radius=radius;
		
	}

}
