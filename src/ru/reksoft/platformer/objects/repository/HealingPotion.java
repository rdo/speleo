package ru.reksoft.platformer.objects.repository;

import org.newdawn.slick.Graphics;

import ru.reksoft.platformer.ImageRegistry;
import ru.reksoft.platformer.objects.Destructable;
import ru.reksoft.platformer.objects.DynamicGameObject;
import ru.reksoft.platformer.objects.npc.Person;
import ru.reksoft.platformer.states.play.PlatformerLevel;

public class HealingPotion extends AbstractObject {
	
	public HealingPotion(PlatformerLevel world, int x, int y){
		super(world, x, y);
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		ImageRegistry.getInstance().draw(HealingPotion.class, g, x, y);

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
