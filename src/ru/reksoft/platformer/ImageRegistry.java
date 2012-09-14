package ru.reksoft.platformer;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ImageRegistry {
	
	private static ImageRegistry instance;
	Map<Images, Image> registry = new HashMap<Images, Image>();

	private ImageRegistry() throws SlickException{
		registry.put(Images.bullet, new Image("data/bullet.png"));
		registry.put(Images.healingPotion, new Image("data/healingPotion.png"));
	}
	public static synchronized ImageRegistry getInstance(){
		if(instance==null){
			try {
				instance=new ImageRegistry();
			} catch (SlickException e) {
				throw new RuntimeException();
			}
		}
		return instance;
	}
	public Image getImage(Images image){
		return registry.get(image);
	}
}
