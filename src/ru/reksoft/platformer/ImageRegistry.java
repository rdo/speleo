package ru.reksoft.platformer;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ImageRegistry {

	private static ImageRegistry instance;
	Map<Class, Image> registry = new HashMap<Class, Image>();

	public Image explosion;

	public Image player;

	private ImageRegistry() throws SlickException {
		explosion = new Image("data/explosion.png");
		player = new Image("data/player.png");
	}

	public static synchronized ImageRegistry getInstance() {
		if (instance == null) {
			try {
				instance = new ImageRegistry();
			} catch (SlickException e) {
				throw new RuntimeException();
			}
		}
		return instance;
	}

	public void draw(Class clazz, Graphics g, float x, float y) {
		Image m = registry.get(clazz);
		if (m == null) {
			try {
				m = new Image("data/objects/" + clazz.getSimpleName() + ".png");
				registry.put(clazz, m);
			} catch (Exception e) {
				return;
			}

		}
		g.drawImage(m, x, y);

	}

}
