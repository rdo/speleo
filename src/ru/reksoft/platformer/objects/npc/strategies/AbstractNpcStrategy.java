package ru.reksoft.platformer.objects.npc.strategies;

import java.util.Random;

import ru.reksoft.platformer.PlatformerLevel;
import ru.reksoft.platformer.objects.npc.Person;
import ru.reksoft.platformer.objects.repository.NPC;

public abstract class AbstractNpcStrategy implements NpcStrategy {
	
	protected PlatformerLevel world;
	protected NPC self;
	protected Person player;
	
	protected Random r = new Random();

	@Override
	public void setWorld(PlatformerLevel world) {
		this.world=world;
	}

	@Override
	public void setNpc(NPC npc) {
		self=npc;
	}

	@Override
	public void setPlayer(Person player) {
		this.player=player;
	}

	@Override
	public abstract void update();
	
	public static NpcStrategy createStrategy(String strategyName){
		NpcStrategy strategy;
		try {
			String packageName=AbstractNpcStrategy.class.getPackage().getName();
			String fullClassName=packageName+"."+strategyName;
			Class clazz=Class.forName(fullClassName);
			strategy=(NpcStrategy)clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return strategy;
	}

}


