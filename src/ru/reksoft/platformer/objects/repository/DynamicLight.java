package ru.reksoft.platformer.objects.repository;

import ru.reksoft.platformer.states.play.PlatformerLevel;

public class DynamicLight extends StaticLight {

	public DynamicLight(PlatformerLevel world, int x, int y) {
		super(world, x, y);
		body.setStatic(false);
	}

}
