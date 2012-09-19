package ru.reksoft.platformer.states.menu;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

public class MenuButton{
	private Image image;
	private String text;
	private int x;
	private int y;
	private int width;
	private int height;
	
	private MenuAction clickAction;
	
	public MenuButton(Image m, int x, int y){
		this.image=m;
		this.x=x;
		this.y=y;
		this.width=m.getWidth();
		this.height=m.getHeight();
	}
	
	public MenuButton(String text, int x, int y, int width, int height){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.text=text;
	}
	
	public void onClick(StateBasedGame game){
		clickAction.perform(game);
	}
	public boolean isCursorOver(int xPos, int yPos){
		if(xPos>x && xPos<x+width){
			if(yPos>y && yPos<y+height){
				return true;
			}
		}
		return false;
	}

	public void setClickAction(MenuAction clickAction) {
		this.clickAction = clickAction;
	}
	
	public void render(Graphics g){
		if(image!=null){
			g.drawImage(image, x, y);
		}else if(text!=null){
			Color temp = g.getColor();
			g.setColor(Color.white);
			g.drawString(text, x, y);
			g.setColor(temp);
		}
	}
	
	
}