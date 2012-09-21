package ru.reksoft.platformer.states.play;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import ru.reksoft.platformer.Platformer;
import ru.reksoft.platformer.objects.LightSource;

public class FogOfWar {

	private PlatformerLevel level;
	private byte[][] fogMap;
	int width;
	int height;

	Image sheet;
	Image halfTransparent;
	Image quarterTransparent;

	public static final int SIZE = 8;

	public static final int RADIUS = 64 / SIZE;

	public FogOfWar(PlatformerLevel level) throws SlickException {
		this.level = level;
		width = level.map.getWidth() * level.map.getTileWidth() / SIZE;
		height = level.map.getHeight() * level.map.getTileHeight() / SIZE;
		;
		fogMap = new byte[height][width];
		sheet = new Image("data/shadows.png", false, Image.FILTER_NEAREST);
		halfTransparent = sheet.getSubImage(0, 0, SIZE, SIZE);
		quarterTransparent = sheet.getSubImage(32, 0, SIZE, SIZE);
	}

	public void render(Graphics g) {
		long start = System.currentTimeMillis();
		Color temp = g.getColor();
		g.setColor(Color.black);

		// int c1=0, c2 = 0, c3=0;

		// TODO: optimize cycle conditions

		// int wStart=level.xOffset/SIZE;
		// int wEnd=wStart+Platformer.SCREEN_WIDTH/SIZE;

		// int hStart=level.yOffset/SIZE;
		// int hEnd=hStart+Platformer.SCREEN_HEIGHT/SIZE;
		int wStart = 0;
		int hStart = 0;
		int hEnd = height;
		int wEnd = width;

		for (int h = hStart - 1; h <= hEnd; h++) {
			for (int w = wStart - 1; w <= wEnd; w++) {
				if (h >= -10 && h < height && w >= -10 && w < width) {
					int x = SIZE * w - level.xOffset;
					int y = SIZE * h + level.yOffset;
					if (x >= 0 && x <= Platformer.SCREEN_WIDTH && y >= 0
							&& y <= Platformer.SCREEN_HEIGHT) {
						byte value = fogMap[h][w];
						if (value == 0) {
							g.fillRect(x, y, SIZE, SIZE);
						} else if (value == 1) {
							// c1++;
							g.drawImage(quarterTransparent, x, y);
						} else if (value == 2) {
							// c2++;
							g.drawImage(halfTransparent, x, y);
						} else if (value == 3) {
							// c3++;
						}
					}
				}

			}
		}

		g.setColor(temp);
		fogMap = new byte[height][width];
		long duration = System.currentTimeMillis() - start;
		// System.out.println("Render duration "+duration+" c1="+c1+" c2="+c2+" c3="+c3);
		// System.out.println("Render duration "+duration+", drawn "+counter);
	}

	public void update(LightSource src) {
		if (src.getRadius() <= 0) {
			return;
		}

		long start = System.currentTimeMillis();
		// int c1=0, c2 = 0, c3=0;
		int x = (int) (src.getBody().getX() / SIZE);
		int y = (int) (src.getBody().getY() / SIZE);

		int lightRadius = src.getRadius() * FogOfWar.RADIUS;

		for (int h = y - lightRadius; h < y + lightRadius; h++) {
			for (int w = x - lightRadius; w < x + lightRadius; w++) {
				// avoid ArrayOutOfBoundException
				if (h >= 0 && h < height && w >= 0 && w < width) {
					int xDiff = Math.abs(x - w);
					int yDiff = Math.abs(y - h);
					// light only squared in radius
					float distance = xDiff * xDiff + yDiff * yDiff;
					float rSquare = lightRadius * lightRadius;
					if (distance <= rSquare && distance >= rSquare * 0.75) {
						if (fogMap[h][w] < 1)
							fogMap[h][w] = 1;
						// c1++;
					} else if (distance >= rSquare * 0.5
							&& distance < rSquare * 0.75) {
						if (fogMap[h][w] < 2)
							fogMap[h][w] = 2;
						// c2++;
					} else if (distance <= rSquare / 2) {
						if (fogMap[h][w] < 3)
							fogMap[h][w] = 3;
						// c3++;

					}

				}
			}
		}
		long duration = System.currentTimeMillis() - start;
		// System.out.println("Update duration "+duration+" c1="+c1+" c2="+c2+" c3="+c3);
	}
}
