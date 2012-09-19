package ru.reksoft.platformer.objects.npc.strategies;

import ru.reksoft.platformer.objects.npc.NPC;
import ru.reksoft.platformer.objects.npc.Person;
import ru.reksoft.platformer.states.play.PlatformerLevel;

public interface NpcStrategy {
public void setWorld(PlatformerLevel world);
public void setNpc(NPC npc);
public void setPlayer(Person player);
public void update();
}
