package ru.reksoft.platformer.objects.npc.strategies;

import org.jbox2d.dynamics.Body;

import ru.reksoft.platformer.objects.npc.NPC;
import ru.reksoft.platformer.objects.npc.Person;
import ru.reksoft.platformer.states.play.PlatformerLevel;

public class TurretStrategy extends AbstractNpcStrategy {

	boolean inited = false;

	@Override
	public void update(PlatformerLevel world, NPC self, Person player) {
		if (!inited) {
			self.getBody().getJBoxBody().m_type = Body.e_staticType;
			inited = true;
		}
		self.shootToPlayer();
		// self.getBody().setVelocity(0, 0);
	}

}
