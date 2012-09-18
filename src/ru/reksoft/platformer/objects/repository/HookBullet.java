package ru.reksoft.platformer.objects.repository;

import ru.reksoft.platformer.objects.DynamicGameObject;
import ru.reksoft.platformer.objects.npc.Player;

public class HookBullet extends Bullet{
	
	public boolean hooked;
	public float x;
	public float y;
	
	private Player player;
	public void setPlayer(Player player) {
		this.player = player;
	}
	@Override
	public void collidesWith(DynamicGameObject other) {
		x = body.getX();
		y = body.getY();
		world.remove(body);
		player.hookBullet=this;
		
	}
	
}
