package ru.reksoft.platformer.objects.repository;

import ru.reksoft.platformer.objects.DynamicGameObject;
import ru.reksoft.platformer.objects.LightSource;
import ru.reksoft.platformer.objects.npc.Controllable;

public class Flashlight extends Bullet implements LightSource, Controllable {

	private int radius = 1;

	private int jumpCounter = 1;

	private int MAX_JUMP = 2;

	public Flashlight() {
		super();
		energyCost=5;
	}

	@Override
	public int getRadius() {
		return radius;
	}

	@Override
	public void collidesWith(DynamicGameObject other) {
		if (jumpCounter >= MAX_JUMP) {
			getBody().setVelocity(0, 0);
			radius = 3;
		} else {
			jumpCounter++;
		}

	}

	@Override
	public void update() {
		if (jumpCounter >= MAX_JUMP) {
			getBody().setVelocity(0, getBody().getYVelocity());
		}

	}

	@Override
	public void setRadius(int radius) {
		this.radius = radius;

	}
}
