package ru.reksoft.platformer.states.menu.buttons;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

public abstract class AbstractMenuButton {
	protected Image image;
	protected String text;
	protected int x;
	protected int y;
	protected int width;
	protected int height;

	public abstract void onClick(GameContainer gc, StateBasedGame game);

	public boolean isCursorOver(int xPos, int yPos) {
		if (image != null) {
			if (xPos > x && xPos < x + image.getWidth()) {
				if (yPos > y && yPos < y + image.getHeight()) {
					return true;
				}
			}
		} else {
			if (xPos > x && xPos < x + width) {
				if (yPos > y && yPos < y + height) {
					return true;
				}
			}
		}

		return false;
	}

	public void render(Graphics g) {
		if (image != null) {
			g.drawImage(image, x, y);
		} else if (text != null) {
			Color temp = g.getColor();
			g.setColor(Color.white);
			g.drawString(text, x, y);
			g.setColor(temp);
		}
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

}