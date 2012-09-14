package ru.reksoft.platformer.objects.repository;

import org.newdawn.slick.Graphics;

import ru.reksoft.platformer.ImageRegistry;
import ru.reksoft.platformer.Images;
import ru.reksoft.platformer.PlatformerLevel;
import ru.reksoft.platformer.objects.Destructable;
import ru.reksoft.platformer.objects.DynamicGameObject;
import ru.reksoft.platformer.objects.npc.Person;

public class HealingPotion extends AbstractObject {
	
	public HealingPotion(PlatformerLevel world, int x, int y){
		super(world, x, y);
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		g.drawImage(ImageRegistry.getInstance().getImage(Images.healingPotion), x, y);
		//g.drawOval(xOffset, yOffset, 16, 16);
		//g.drawString("+", xOffset, yOffset);

	}

	@Override
	public void collidesWith(DynamicGameObject other) {
		if(other instanceof Person){
			((Destructable)other).changeHp(5);
			System.out.println(other.toString()+" healed");
			world.remove(body);
		}

	}

}
