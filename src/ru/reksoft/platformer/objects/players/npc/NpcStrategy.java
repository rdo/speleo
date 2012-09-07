package ru.reksoft.platformer.objects.players.npc;

import ru.reksoft.platformer.PlatformerWorld;
import ru.reksoft.platformer.objects.players.Player;

public interface NpcStrategy {
public void setWorld(PlatformerWorld world);
public void setNpc(NPC npc);
public void setPlayer(Player player);
public void update();
}
