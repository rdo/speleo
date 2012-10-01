package ru.reksoft.platformer.objects.npc.strategies;

import ru.reksoft.platformer.objects.npc.NPC;
import ru.reksoft.platformer.objects.npc.Person;
import ru.reksoft.platformer.states.play.PlatformerLevel;

public interface NpcStrategy {

	public abstract void update(PlatformerLevel world, NPC self, Person player);
}
