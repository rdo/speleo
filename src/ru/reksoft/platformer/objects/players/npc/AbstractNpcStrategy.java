package ru.reksoft.platformer.objects.players.npc;

import java.util.Random;

import ru.reksoft.platformer.PlatformerWorld;
import ru.reksoft.platformer.objects.players.Player;

public abstract class AbstractNpcStrategy implements NpcStrategy {
	
	protected PlatformerWorld world;
	protected NPC self;
	protected Player player;
	
	protected Random r = new Random();

	@Override
	public void setWorld(PlatformerWorld world) {
		this.world=world;
	}

	@Override
	public void setNpc(NPC npc) {
		self=npc;
	}

	@Override
	public void setPlayer(Player player) {
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


