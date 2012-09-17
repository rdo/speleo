package ru.reksoft.platformer;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.fizzy.Body;
import org.newdawn.fizzy.Rectangle;
import org.newdawn.fizzy.World;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.tiled.TiledMap;

import ru.reksoft.platformer.objects.npc.NPC;
import ru.reksoft.platformer.objects.npc.Person;
import ru.reksoft.platformer.objects.npc.Player;
import ru.reksoft.platformer.objects.npc.strategies.AbstractNpcStrategy;
import ru.reksoft.platformer.objects.repository.AbstractObject;
import ru.reksoft.platformer.objects.repository.HealingPotion;

public class PlatformerLevel extends World{ 
	public Player getPlayer() {
		return player;
	}

	public int xOffset=0;
	public int yOffset=0;
	
	public int mapHeigth;
	public int mapWidth;
	
	public TiledMap map;
	
	public Player player;
	
	private Graphics graphics;
	
	private List<PlatformerLevel.AsynchImageEvent> delayedEvents = new ArrayList<PlatformerLevel.AsynchImageEvent>();
	
	public PlatformerLevel() {
		super();
	}

	public PlatformerLevel(float x1, float y1, float x2, float y2,
			float g, float iterations) {
		super(x1, y1, x2, y2, g, iterations);
	}

	public PlatformerLevel(float worldWidth, float worldHeight) {
		super(worldWidth, worldHeight);
	}
	
	public PlatformerLevel(TiledMap map){
		
		super(0, 0, map.getWidth() * map.getTileWidth(), map.getHeight() * map.getTileHeight(), 1, 1);
		
		this.map=map;
		mapHeigth = map.getHeight() * map.getTileHeight();
		mapWidth = map.getWidth() * map.getTileWidth();
		
		ArrayList<NPC> npcs=  new ArrayList<NPC>();

		System.out.println(mapWidth + ":" + mapHeigth);
		// world.yOffset=-mapHeigth+SCREEN_HEIGHT;
		for (int i = 0; i < map.getObjectGroupCount(); i++) {
			for (int j = 0; j < map.getObjectCount(i); j++) {
				int width = map.getObjectWidth(i, j);
				int height = map.getObjectHeight(i, j);
				int x = map.getObjectX(i, j);
				int y = map.getObjectY(i, j);
				String name = map.getObjectName(i, j);
				//System.out.println(name);
				if (name == null || name.equals("")) {
					addFloor(x, y, width, height);
					continue;
				}
				
				if (name.equals("player")) {
					player = new Player(this, x, y);
				}else if (name.equals("enemy")) {
					String strategyName=map.getObjectProperty(i, j, "strategy", "TurretStrategy");
					NPC bot = new NPC(this, x, y, AbstractNpcStrategy.createStrategy(strategyName));
					npcs.add(bot);
				}else{
					try{
						String packageName = AbstractObject.class.getPackage().getName();
						String className=packageName+"."+name;
						Class clazz=Class.forName(className);
						Object o = clazz.getConstructors()[0].newInstance(this, x,y);
						System.out.println(o.toString() +" created");
						
					}catch(Exception e){
						
					}
				}
			}
		}
		
		//player could be inited after bots; avoiding npe
		for(NPC npc:npcs){
			npc.setPlayer(player);
		}
		npcs.clear();
		
		this.yOffset = 0;
		// player = new Player(world, 100, 500);
		// bot = new SimpleBot(world, 1500, 560);
		// bot.setPlayer(player);
		// new HealingPotion(world, 100, 100);
	}

	public PlatformerLevel(int iterations) {
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
		for (PlatformerLevel.AsynchImageEvent e : delayedEvents) {
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
