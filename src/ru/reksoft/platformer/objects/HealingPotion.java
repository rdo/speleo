package ru.reksoft.platformer.objects;

import org.newdawn.fizzy.Body;
import org.newdawn.fizzy.Circle;
import org.newdawn.slick.Graphics;

import ru.reksoft.platformer.PlatformerWorld;
import ru.reksoft.platformer.objects.players.Player;

public class HealingPotion implements DynamicGameObject {
	
	PlatformerWorld world;
	Body body;
	
	public HealingPotion(PlatformerWorld world, int x, int y){
		this.world=world;
		body=new Body(new Circle(3f), x, y, true);
		world.add(body);
		body.setUserData(this);
	}

	@Override
	public void draw(Graphics g, int xOffset, int yOffset) {
		//g.drawOval(xOffset, yOffset, 16, 16);
		g.drawString("+", xOffset, yOffset);

	}

	@Override
	public void collidesWith(DynamicGameObject other) {
		if(other instanceof Player){
			((Destructable)other).changeHp(5);
			System.out.println(other.toString()+" healed");
			world.remove(body);
		}

	}

}
