package ru.reksoft.platformer.objects.repository;

import org.newdawn.slick.Graphics;

import ru.reksoft.platformer.objects.DynamicGameObject;
import ru.reksoft.platformer.objects.npc.Player;

public class HookBullet extends Bullet {

	public static final int MAX_HOOK_LENGHT = 400;

	public boolean hooked;

	private boolean invalid = false;

	public float xCollision;
	public float yCollision;

	public float prevX;
	public float prevY;

	public float startX;
	private float startY;
	boolean inited = false;

	private Player player;

	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public void collidesWith(DynamicGameObject other) {

		if (!invalid) {
			xCollision = body.getX();
			yCollision = body.getY();
			player.hookBullet = this;
		}
		world.remove(body);

	}

	@Override
	public void draw(Graphics g, int x, int y) {

		super.draw(g, x - 4, y - 2);
		if (prevX != 0 && prevY != 0) {
			g.drawLine(x, y, prevX, prevY);
		}
		prevX = x;
		prevY = y;

		if (!inited) {
			inited = true;
			startX = x;
			startY = y;
		}
		double xDiff = startX - x;
		double yDiff = startY - y;
		double hookLenght = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
		if (hookLenght > MAX_HOOK_LENGHT) {
			invalid = true;
		}

	}

}
