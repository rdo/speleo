package ru.reksoft.platformer.objects;

import org.jbox2d.common.Vec2;
import org.newdawn.fizzy.Body;
import org.newdawn.fizzy.Circle;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import ru.reksoft.platformer.PlatformerWorld;
import ru.reksoft.platformer.objects.players.Player;

public class Bullet implements DynamicGameObject{
	
	private Image explosion;
	
	private PlatformerWorld world;
	
	private float prevX;
	private float prevY;
	
	private Body bullet;
	
	public Bullet(PlatformerWorld world, float x, float y, float xVelocity, float yVelocity){
		this.world=world;
		bullet = new Body(new Circle(1f), x, y);
		world.add(bullet);
		bullet.getJBoxBody().setLinearVelocity(new Vec2(xVelocity, yVelocity));
		bullet.setUserData(this);
		try{
			explosion=new Image("data/explosion.png");
		}catch(SlickException e){
			System.out.println("Failed to load explosion image: "+e.getMessage());
		}
	}

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
		float x = bullet.getX();
		float y=bullet.getY();
		world.remove(bullet);
		world.addAsynchImageEvent(explosion, x, y-20);
		
	}
}
