package ru.reksoft.platformer.states.menu.buttons;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

public abstract class AbstractMenuButton {
	private Image image;
	private String text;
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
		} else if (getText() != null) {
			//Color temp = g.getColor();
			//g.setColor(Color.white);
			g.drawString(getText(), x, y);
			//g.setColor(temp);
		}else{
			//do nothing
		}
	}
	public void renderHover(Graphics g){
		Color temp = g.getColor();
		g.setColor(Color.red);
		render(g);
		g.setColor(temp);
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public abstract String getText();

}