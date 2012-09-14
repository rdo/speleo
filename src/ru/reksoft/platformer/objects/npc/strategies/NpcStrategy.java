package ru.reksoft.platformer.objects.npc.strategies;

import ru.reksoft.platformer.PlatformerLevel;
import ru.reksoft.platformer.objects.npc.Person;
import ru.reksoft.platformer.objects.repository.NPC;

public interface NpcStrategy {
public void setWorld(PlatformerLevel world);
public void setNpc(NPC npc);
public void setPlayer(Person player);
public void update();
}
