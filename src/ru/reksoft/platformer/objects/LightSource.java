package ru.reksoft.platformer.objects;

import org.newdawn.fizzy.Body;

public interface LightSource {

	public void setRadius(int radius);

	public int getRadius();

	public Body getBody();
}
