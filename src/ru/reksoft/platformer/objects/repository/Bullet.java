package ru.reksoft.platformer.objects.repository;

import org.jbox2d.common.Vec2;
import org.newdawn.fizzy.Body;
import org.newdawn.fizzy.Circle;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import ru.reksoft.platformer.ImageRegistry;
import ru.reksoft.platformer.objects.Destructable;
import ru.reksoft.platformer.objects.DynamicGameObject;
import ru.reksoft.platformer.states.play.PlatformerLevel;

public class Bullet implements DynamicGameObject{
	
	protected Image explosion;
	
	protected PlatformerLevel world;
	
	protected Body body;
	
	private float x;
	private float y;
	private float xVelocity;
	private float yVelocity;
	
	public void setStartPosition(float x, float y){
		//this.prevX=x;
		//this.prevY=y;
		this.x=x;
		this.y=y;
	}
	
	public void setVelocity(float xVelo, float yVelo){
		xVelocity=xVelo;
		yVelocity=yVelo;
	}
	
	public void setWorld(PlatformerLevel world){
		this.world=world;
	}
	
	public void launch(){
		body = new Body(new Circle(1f), x, y);
		world.add(body);
		body.getJBoxBody().setLinearVelocity(new Vec2(xVelocity, yVelocity));
		body.setUserData(this);
		explosion=ImageRegistry.getInstance().explosion;
	}
	
	
	public Body getBody() {
		return body;
	}

	@Override
	public void draw(Graphics g, int x, int y) { 
		ImageRegistry.getInstance().draw(Bullet.class, g, x, y);
		
	}

	@Override
	public void collidesWith(DynamicGameObject other) {
		if(other !=null && other instanceof Destructable){
			((Destructable)other).changeHp(-1);
		}
		float x = body.getX();
		float y=body.getY();
		world.remove(body);
		world.addAsynchImageEvent(explosion, x-explosion.getWidth()/2, y-explosion.getHeight()/2);
		
	}

	
}
