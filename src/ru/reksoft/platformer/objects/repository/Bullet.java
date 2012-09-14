package ru.reksoft.platformer.objects.repository;

import org.jbox2d.common.Vec2;
import org.newdawn.fizzy.Body;
import org.newdawn.fizzy.Circle;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import ru.reksoft.platformer.PlatformerLevel;
import ru.reksoft.platformer.objects.Destructable;
import ru.reksoft.platformer.objects.DynamicGameObject;

public class Bullet implements DynamicGameObject{
	
	private Image explosion;
	
	private PlatformerLevel world;
	
	private float prevX;
	private float prevY;
	
	private Body body;
	
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
		try{
			explosion=new Image("data/explosion.png");
		}catch(SlickException e){
			System.out.println("Failed to load explosion image: "+e.getMessage());
		}
	}
	
	
	public Body getBody() {
		return body;
	}

	/*public Bullet(PlatformerLevel world, float x, float y, float xVelocity, float yVelocity){
		this.world=world;
		body = new Body(new Circle(1f), x, y);
		world.add(body);
		body.getJBoxBody().setLinearVelocity(new Vec2(xVelocity, yVelocity));
		body.setUserData(this);
		try{
			explosion=new Image("data/explosion.png");
		}catch(SlickException e){
			System.out.println("Failed to load explosion image: "+e.getMessage());
		}
	}
	public Bullet(PlatformerLevel world){
		this.world=world;	
	}*/

	@Override
	public void draw(Graphics g, int x, int y) {
		g.drawOval(x, y, 2, 2);
		if(prevX==0 && prevY==0){
			prevX=x;
			prevY=y;
		}			
		g.drawLine(prevX, prevY, x, y);
		
		prevX=x;
		prevY=y;
		
	}

	@Override
	public void collidesWith(DynamicGameObject other) {
		if(other !=null && other instanceof Destructable){
			((Destructable)other).changeHp(-1);
		}
		float x = body.getX();
		float y=body.getY();
		world.remove(body);
		world.addAsynchImageEvent(explosion, x, y-20);
		
	}

	
}
