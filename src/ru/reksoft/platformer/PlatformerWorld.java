package ru.reksoft.platformer;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.fizzy.Body;
import org.newdawn.fizzy.Rectangle;
import org.newdawn.fizzy.World;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class PlatformerWorld extends World{
	//графическое смещение. нужно, чтобы отрисовывать на экране уровни большого размера. 
	public int xOffset=0;
	public int yOffset=0;
	
	private Graphics graphics;
	
	private List<PlatformerWorld.AsynchImageEvent> delayedEvents = new ArrayList<PlatformerWorld.AsynchImageEvent>();
	
	public PlatformerWorld() {
		super();
	}

	public PlatformerWorld(float x1, float y1, float x2, float y2,
			float g, float iterations) {
		super(x1, y1, x2, y2, g, iterations);
	}

	public PlatformerWorld(float worldWidth, float worldHeight) {
		super(worldWidth, worldHeight);
	}

	public PlatformerWorld(int iterations) {
		super(iterations);
	}
	public void addFloor(int x, int y, int width, int height){
		int xCenter=x+width/2;
		int yCenter=y+height/2;
		Body body=new Body(new Rectangle(width, height), xCenter, yCenter, true);
		add(body);
		body.setUserData("rectangle");
	}
	public void addAsynchImageEvent(Image image, float physX, float physY){
		AsynchImageEvent event = new AsynchImageEvent();
		event.image=image;
		event.x=(int)(physX-xOffset);
		event.y=(int)(physY+yOffset);
		delayedEvents.add(event);
	}
	
	public void drawAsynchEvents(){
		for (PlatformerWorld.AsynchImageEvent e : delayedEvents) {
			graphics.drawImage(e.image, e.x, e.y);
		}
		delayedEvents.clear();
	}

	public void setGraphics(Graphics graphics) {
		this.graphics = graphics;
	}
	
	class AsynchImageEvent{
		Image image;
		int x;
		int y;
	}

}
