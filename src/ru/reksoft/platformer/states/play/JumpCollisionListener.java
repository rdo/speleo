package ru.reksoft.platformer.states.play;

import org.newdawn.fizzy.Body;
import org.newdawn.fizzy.CollisionEvent;
import org.newdawn.fizzy.Rectangle;
import org.newdawn.fizzy.Shape;
import org.newdawn.fizzy.WorldListener;

import ru.reksoft.platformer.objects.npc.Person;

public class JumpCollisionListener implements WorldListener {

	public JumpCollisionListener() {
		super();
	}

	@Override
	public void collided(CollisionEvent event) {

		Object ud1 = event.getBodyA().getUserData();
		Object ud2 = event.getBodyB().getUserData();
		Body player = null;
		Body other = null;

		if (ud1 instanceof Person) {
			player = event.getBodyA();
			other = event.getBodyB();
		} else if (ud2 instanceof Person) {
			player = event.getBodyB();
			other = event.getBodyA();
		} else {
			return;
		}

		float halfHeight = 0;
		Shape shape = other.getShape();
		if (shape instanceof Rectangle) {
			halfHeight = ((Rectangle) shape).getHeight() / 2;
			// System.out.println(halfHeight);
		}
		float otherFloorLevel = -halfHeight + other.getY();
		// System.out.println("Other floor is on "+otherFloorLevel+", player is on "+player.getY());
		if (otherFloorLevel > player.getY()) {
			// System.out.println("Collision with floor");
			((Person) player.getUserData()).endJump();
			((Person) player.getUserData()).setLastCollision(other);
			
		}
	}

	@Override
	public void separated(CollisionEvent event) {
		// TODO Auto-generated method stub

	}
}
