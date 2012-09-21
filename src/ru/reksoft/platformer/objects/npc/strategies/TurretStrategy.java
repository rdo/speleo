package ru.reksoft.platformer.objects.npc.strategies;

import org.jbox2d.dynamics.Body;

public class TurretStrategy extends AbstractNpcStrategy {

	boolean inited = false;

	@Override
	public void update() {
		if (!inited) {
			self.getBody().getJBoxBody().m_type = Body.e_staticType;
			inited = true;
		}
		self.shootToPlayer();
		// self.getBody().setVelocity(0, 0);
	}

}
