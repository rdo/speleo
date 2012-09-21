package ru.reksoft.platformer.objects;

import org.newdawn.slick.Graphics;

public interface DynamicGameObject {
	public void draw(Graphics g, int xOffset, int yOffset);

	public void collidesWith(DynamicGameObject other);

}
