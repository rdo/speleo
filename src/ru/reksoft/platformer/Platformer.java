package ru.reksoft.platformer;

import java.util.ArrayList;

import org.newdawn.fizzy.Body;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import ru.reksoft.platformer.objects.DynamicGameObject;
import ru.reksoft.platformer.objects.HealingPotion;
import ru.reksoft.platformer.objects.players.Player;
import ru.reksoft.platformer.objects.players.npc.AbstractNpcStrategy;
import ru.reksoft.platformer.objects.players.npc.Controllable;
import ru.reksoft.platformer.objects.players.npc.NPC;

public class Platformer extends BasicGame {
	
	private static final int SCREEN_HEIGHT = 768;
	private static final int SCREEN_WIDTH = 1024;
	private static final int FRAME_RATE = 20;
	private static final int X_DISPLACEMENT = SCREEN_WIDTH / 2;
	private static final int Y_DISPLACEMENT = SCREEN_HEIGHT / 2;
	
	private Image background;
	private PlatformerWorld world;

	private int mouseX;
	private int mouseY;
	private boolean mousePressed = false;

	private TiledMap map;
	int mapHeigth;
	int mapWidth;

	private Player player;

	boolean w_pressed = false;
	boolean a_pressed = false;
	boolean s_pressed = false;
	boolean d_pressed = false;

	GameState currentState = GameState.PLAY;

	public Platformer(String title) {
		super(title);
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		
		ArrayList<NPC> npcs=  new ArrayList<NPC>();
		map = new TiledMap("data/level1.tmx");

		mapHeigth = map.getHeight() * map.getTileHeight();
		mapWidth = map.getWidth() * map.getTileWidth();
		System.out.println(mapWidth + ":" + mapHeigth);
		background = new Image("data/skybox.jpg");
		world = new PlatformerWorld(0, 0, mapWidth, mapHeigth, 1, 1);
		world.setGraphics(arg0.getGraphics());
		// world.yOffset=-mapHeigth+SCREEN_HEIGHT;
		for (int i = 0; i < map.getObjectGroupCount(); i++) {
			for (int j = 0; j < map.getObjectCount(i); j++) {
				int width = map.getObjectWidth(i, j);
				int height = map.getObjectHeight(i, j);
				int x = map.getObjectX(i, j);
				int y = map.getObjectY(i, j);
				String name = map.getObjectName(i, j);
				System.out.println(name);
				if (name == null || name.equals("")) {
					world.addFloor(x, y, width, height);
					continue;
				}
				if (name.equals("player")) {
					player = new Player(world, x, y);
				} else if (name.equals("heal")) {
					new HealingPotion(world, x, y);
				} else if (name.equals("enemy")) {
					String strategyName=map.getObjectProperty(i, j, "strategy", "SideToSideStrategy");
					NPC bot = new NPC(world, x, y, AbstractNpcStrategy.createStrategy(strategyName));
					npcs.add(bot);
				}
			}
		}
		
		//player could be inited after bots; avoiding npe
		for(NPC npc:npcs){
			npc.setPlayer(player);
		}
		npcs.clear();
		
		world.yOffset = 0;
		// player = new Player(world, 100, 500);
		// bot = new SimpleBot(world, 1500, 560);
		// bot.setPlayer(player);
		// new HealingPotion(world, 100, 100);

		world.addListener(new JumpCollisionListener());
		world.addListener(new DynamicObjectCollisionListener(world));
		arg0.getInput().addKeyListener(new KeyboardListener());
		arg0.getInput().addMouseListener(new ListenerMouse());
		
		System.out.println("inited");

	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		if(player.getHp()<=0){
			currentState=GameState.END;
		}

		for (int i = 0; i < world.getBodyCount(); i++) {
			Body b = world.getBody(i);
			Object ud = b.getUserData();
			if (ud != null && ud instanceof Controllable) {
				((Controllable) ud).update();
			}
		}

		if (mousePressed) {
			player.shootTo(mouseX + world.xOffset, mouseY - world.yOffset);
			mousePressed = false;
		}
		if (!w_pressed) {
			player.allowNextJump();
		}

		if (w_pressed) {
			player.jump();
		}

		if (a_pressed) {
			player.stepLeft();
		}

		if (d_pressed) {
			player.stepRight();
		}

		if (!d_pressed && !a_pressed) {
			player.stopMovement();
		}
		if (s_pressed) {
			player.stopMovement();
		}

		world.update(1);
	}

	@Override
	public void render(GameContainer arg0, Graphics g) throws SlickException {
		switch (currentState) {
		case PLAY:
			renderWorld(g);
			break;
		case END:
			g.drawString("GAME OVER", SCREEN_WIDTH/2, SCREEN_HEIGHT/2);
			break;
		}
	}

	private void renderWorld(Graphics g) {
		if (player.getX() > X_DISPLACEMENT
				&& player.getX() < mapWidth - X_DISPLACEMENT) {
			world.xOffset = (int) (player.getX() - X_DISPLACEMENT);
		}
		int physY = (int) (mapHeigth - player.getY());
		if (physY > Y_DISPLACEMENT && physY < mapHeigth - Y_DISPLACEMENT) {
			int newY = SCREEN_HEIGHT - mapHeigth + (physY - Y_DISPLACEMENT);
			world.yOffset = newY;
		} else if (physY < Y_DISPLACEMENT) {
			world.yOffset = SCREEN_HEIGHT - mapHeigth;
		}

		map.render(-world.xOffset, world.yOffset);

		int total = world.getBodyCount();
		for (int i = 0; i < total; i++) {
			Body b = world.getBody(i);
			Object userdata = b.getUserData();
			if (userdata != null && userdata instanceof DynamicGameObject) {
				((DynamicGameObject) userdata).draw(g, (int) b.getX()
						- world.xOffset, (int) b.getY() + world.yOffset);
			}
		}
		world.drawAsynchEvents();
		g.drawString("HP: "+player.getHp()+" EXP: "+player.getExp(), 32, 4);
	}

	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(
					new Platformer("Speleo"));
			app.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
			app.setTargetFrameRate(FRAME_RATE);
			app.setShowFPS(false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	class KeyboardListener implements KeyListener {

		@Override
		public void setInput(Input paramInput) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean isAcceptingInput() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public void inputEnded() {
			// TODO Auto-generated method stub

		}

		@Override
		public void inputStarted() {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyPressed(int paramInt, char paramChar) {
			if (paramChar == 'w')
				w_pressed = true;
			if (paramChar == 'a')
				a_pressed = true;
			if (paramChar == 's')
				s_pressed = true;
			if (paramChar == 'd')
				d_pressed = true;
			if (paramChar == ' ')
				player.shoot();
			

		}

		@Override
		public void keyReleased(int paramInt, char paramChar) {
			if (paramChar == 'w')
				w_pressed = false;
			if (paramChar == 'a')
				a_pressed = false;
			if (paramChar == 's')
				s_pressed = false;
			if (paramChar == 'd')
				d_pressed = false;

		}

	}

	class ListenerMouse implements MouseListener {

		@Override
		public void setInput(Input paramInput) {
		}

		@Override
		public boolean isAcceptingInput() {
			return true;
		}

		@Override
		public void inputEnded() {
		}

		@Override
		public void inputStarted() {
		}

		@Override
		public void mouseWheelMoved(int paramInt) {
		}

		@Override
		public void mouseClicked(int paramInt1, int paramInt2, int paramInt3,
				int paramInt4) {
			mouseX = paramInt2;
			mouseY = paramInt3;
			mousePressed = true;
		}

		@Override
		public void mousePressed(int paramInt1, int paramInt2, int paramInt3) {
		}

		@Override
		public void mouseReleased(int paramInt1, int paramInt2, int paramInt3) {
		}

		@Override
		public void mouseMoved(int paramInt1, int paramInt2, int paramInt3,
				int paramInt4) {
		}

		@Override
		public void mouseDragged(int paramInt1, int paramInt2, int paramInt3,
				int paramInt4) {
		}

	}

	enum GameState {
		PLAY, PAUSE, END;
	}

}
