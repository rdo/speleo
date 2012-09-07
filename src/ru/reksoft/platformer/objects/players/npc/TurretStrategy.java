package ru.reksoft.platformer.objects.players.npc;

public class TurretStrategy extends AbstractNpcStrategy{

	@Override
	public void update() {
		self.shootToPlayer();
		self.getBody().setVelocity(0, 0);
	}

}
