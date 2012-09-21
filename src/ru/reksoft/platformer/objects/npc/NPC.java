package ru.reksoft.platformer.objects.npc;

import java.util.Random;

import org.newdawn.fizzy.Body;
import org.newdawn.slick.Graphics;

import ru.reksoft.platformer.CharacterInfo;
import ru.reksoft.platformer.objects.DynamicGameObject;
import ru.reksoft.platformer.objects.npc.strategies.NpcStrategy;
import ru.reksoft.platformer.states.play.PlatformerLevel;

public class NPC extends Person implements Controllable {
	//TODO: move to pojo-class or create new class for each enemy?
	private static int FIRE_ACCURACY = 50;
	private static int FIRE_RANGE = 400;
	private static int FIRE_PERIOD = 500;
	private static int MAX_HP = 3;
	public float prevX;
	public boolean jumped = false;
	public boolean moveRight = true;
	// int hp=MAX_HP;

	int startX;
	int startY;

	private Person player;

	private long lastShoot;

	private NpcStrategy strategy;

	Random r = new Random();

	public NPC(PlatformerLevel world, int x, int y, NpcStrategy strategy) {
		super(world, x, y, new CharacterInfo());
		prevX = x;
		lastShoot = System.currentTimeMillis();
		startX = x;
		startY = y;

		strategy.setNpc(this);
		strategy.setPlayer(player);
		strategy.setWorld(world);
		this.strategy = strategy;

		stats.hp = 3;
		stats.speed=3;
		stats.bulletSpeed = 50;
	}

	@Override
	public void collidesWith(DynamicGameObject other) {

	}

	@Override
	public void update() {
		strategy.update();

	}

	public void shootToPlayer() {
		if (player != null) {
			if (Math.abs(player.getX() - getX()) < FIRE_RANGE) {
				if (System.currentTimeMillis() - lastShoot > FIRE_PERIOD) {
					shootTo((int) player.getX() + r.nextInt() % FIRE_ACCURACY,
							(int) player.getY() + r.nextInt() % FIRE_ACCURACY);
					lastShoot = System.currentTimeMillis();
				}

			}
		}
	}

	public void move() {
		moveFromSideToSide();
	}

	private void moveFromSideToSide() {
		if (moveRight) {
			stepRight();
		} else {
			stepLeft();
		}
	}

	private void followPlayer() {
		if (getX() > player.getX()) {
			stepLeft();
		} else {
			stepRight();
		}
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		super.draw(g, x, y);
		g.drawString(
				Integer.toString(stats.hp) + "/" + Integer.toString(MAX_HP)
						+ " hp", x - 16, y - 24);
	}

	@Override
	public String toString() {
		return "SimpleBot: moveRigtht=" + moveRight + " state=" + currentState;
	}

	public void setPlayer(Person player) {
		this.player = player;
	}

	@Override
	public void changeHp(int value) {
		super.changeHp(value);
		if (getHp() <= 0) {
			onDeath();
		}
	}

	@Override
	public void onDeath() {
		strategy = null;
		getWorld().remove(getBody());
		player.setExp(player.getExp() + 1);
	}

}
