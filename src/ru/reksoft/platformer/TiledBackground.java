package ru.reksoft.platformer;

import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class TiledBackground {
	
	private static final int SIZE=96;
	private int width;
	private int height;
	private Image[] tiles;
	private int yCount;
	private int xCount;
	private Random r = new Random();
	
	private int[][] tilesMap;
	
	public TiledBackground(int width, int height) throws SlickException {
		this.width = width;
		this.height = height;
		Image tileSet = new Image("data/background.png");
		tiles = new Image[9];
		int counter=0;
		for(int i=0; i<3;i++){
			for(int j=0;j<3; j++){
				tiles[counter]=tileSet.getSubImage(i*(SIZE+1), j*(SIZE+1), SIZE, SIZE);
				counter++;
			}
		}
		xCount = width/SIZE+2;
		yCount = height/SIZE+2;
		tilesMap=new int[xCount][yCount];
		for(int i=0;i<xCount;i++){
			for(int j=0;j<yCount;j++){
				tilesMap[i][j]=Math.abs(r.nextInt()%9);
			}
		}
	}

	public void render(Graphics g, int xOffset, int yOffset) {
		for (int i = 0; i < xCount; i++) {
			for (int j = 0; j < yCount; j++) {

				int xPos = i * SIZE - xOffset;
				int yPos = j * SIZE + yOffset;
				if (xPos >= -100 && xPos <= Platformer.SCREEN_WIDTH && yPos >= -100
						&& yPos <= Platformer.SCREEN_HEIGHT) {
					g.drawImage(tiles[tilesMap[i][j]], xPos, yPos);
					
				}
			}
		}

	}
}
