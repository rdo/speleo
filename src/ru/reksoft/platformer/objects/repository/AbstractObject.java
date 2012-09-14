package ru.reksoft.platformer.objects.repository;

import org.newdawn.fizzy.Body;
import org.newdawn.fizzy.Rectangle;

import ru.reksoft.platformer.PlatformerLevel;
import ru.reksoft.platformer.objects.DynamicGameObject;

public abstract class AbstractObject implements DynamicGameObject{

	PlatformerLevel world;
	Body body;
	
	public AbstractObject(PlatformerLevel world, int x, int y){
		this.world=world;
		body=new Body(new Rectangle(3f, 3f), x, y, true);
		world.add(body);
		body.setUserData(this);
	}
	
}
