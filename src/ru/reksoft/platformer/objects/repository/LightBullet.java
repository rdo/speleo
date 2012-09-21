package ru.reksoft.platformer.objects.repository;

import ru.reksoft.platformer.objects.LightSource;

public class LightBullet extends Bullet implements LightSource {

	@Override
	public int getRadius() {
		return 2;
	}

	@Override
	public void setRadius(int radius) {
	}

}
