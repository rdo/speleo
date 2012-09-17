package ru.reksoft.platformer;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class TiledBackground {
	private int width;
	private int height;
	private Image tile;
	private int yCount;
	private int xCount;
	
	public TiledBackground(int width, int height) throws SlickException {
		super();
		this.width = width;
		this.height = height;
		tile = new Image("data/background.png");
		yCount = width/tile.getHeight()+2;
		xCount = height/tile.getHeight()+2;
	}

	public void render(Graphics g, int xOffset, int yOffset) {
		long start = System.currentTimeMillis();
		for (int i = 0; i <= xCount; i++) {
			for (int j = 0; j <= yCount; j++) {

				int xPos = i * tile.getWidth() - xOffset;
				int yPos = j * tile.getHeight() + yOffset;
				if (xPos >= -100 && xPos <= Platformer.SCREEN_WIDTH && yPos >= -100
						&& yPos <= Platformer.SCREEN_HEIGHT) {
					g.drawImage(tile, xPos, yPos);
					
				}
			}
		}
		System.out.println("Duration: "+(System.currentTimeMillis()-start));

	}
}
