package ru.reksoft.platformer.objects.npc.strategies;

import java.util.Random;

import ru.reksoft.platformer.objects.npc.NPC;
import ru.reksoft.platformer.objects.npc.Person;
import ru.reksoft.platformer.states.play.PlatformerLevel;

public abstract class AbstractNpcStrategy implements NpcStrategy {

	protected Random r = new Random();

	@Override
	public abstract void update(PlatformerLevel world, NPC self, Person player);

	public static NpcStrategy createStrategy(String strategyName) {
		NpcStrategy strategy;
		try {
			String packageName = AbstractNpcStrategy.class.getPackage()
					.getName();
			String fullClassName = packageName + "." + strategyName;
			Class clazz = Class.forName(fullClassName);
			strategy = (NpcStrategy) clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return strategy;
	}

}
