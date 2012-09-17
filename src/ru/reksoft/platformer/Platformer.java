package ru.reksoft.platformer;

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
import ru.reksoft.platformer.objects.LightSource;
import ru.reksoft.platformer.objects.npc.Controllable;
import ru.reksoft.platformer.objects.repository.Player;


public class Platformer extends BasicGame {
	
	
	public static final int SCREEN_WIDTH = 1024;
	public static final int SCREEN_HEIGHT = 768;
	private static final int FRAME_RATE = 20;
	private static final int X_DISPLACEMENT = SCREEN_WIDTH / 2;
	private static final int Y_DISPLACEMENT = SCREEN_HEIGHT / 2;
	
	private PlatformerLevel world;
	
	private FogOfWar fog;
	
	private TiledBackground background;
	
	//private Image background;
	
	private Player player;

	private int mouseX;
	private int mouseY;
	private boolean mousePressed = false;

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
		
		world=new PlatformerLevel(new TiledMap("data/level2.tmx"));
		world.setGraphics(arg0.getGraphics());
		world.addListener(new JumpCollisionListener());
		world.addListener(new DynamicObjectCollisionListener(world));

		//background=new Image("data/cave.jpg");
		
		player=world.getPlayer();
		
		fog=new FogOfWar(world);
		background = new TiledBackground(world.mapWidth, world.mapHeigth);

		
		arg0.getInput().addKeyListener(new KeyboardListener());
		arg0.getInput().addMouseListener(new ListenerMouse());
		
		System.out.println("inited");

	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		
		if(player.getHp()<=0){
			//currentState=GameState.END;
		}

		for (int i = 0; i < world.getBodyCount(); i++) {
			Body b = world.getBody(i);
			Object ud = b.getUserData();
			if (ud != null && ud instanceof Controllable) {
				((Controllable) ud).update();
			}
			if(ud!=null && ud instanceof LightSource){
				fog.update((LightSource)ud);
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
			background.render(g, world.xOffset, world.yOffset);
			renderWorld(g);
			fog.render(g);
			renderUI(g);
			break;
		case END:
			g.drawString("GAME OVER", SCREEN_WIDTH/2, SCREEN_HEIGHT/2);
			break;
		}
	}

	private void renderWorld(Graphics g) {
		if (player.getX() > X_DISPLACEMENT
				&& player.getX() < world.mapWidth - X_DISPLACEMENT) {
			world.xOffset = (int) (player.getX() - X_DISPLACEMENT);
		}
		int physY = (int) (world.mapHeigth - player.getY());
		if (physY > Y_DISPLACEMENT && physY < world.mapHeigth - Y_DISPLACEMENT) {
			int newY = SCREEN_HEIGHT - world.mapHeigth + (physY - Y_DISPLACEMENT);
			world.yOffset = newY;
		} else if (physY < Y_DISPLACEMENT) {
			world.yOffset = SCREEN_HEIGHT - world.mapHeigth;
		}
		//g.drawImage(background, 0, 0);
		

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
		world.map.render(-world.xOffset, world.yOffset);
	}
	
	private void renderUI(Graphics g){
		g.drawString("HP: "+player.getHp()+" EXP: "+player.getExp()+" weapon: "+player.getDefaultBullet().getClass().getSimpleName(), 32, 4);
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
		public void setInput(Input paramInput) {}

		@Override
		public boolean isAcceptingInput() {
			return true;
		}

		@Override
		public void inputEnded() {}

		@Override
		public void inputStarted() {}

		@Override
		public void keyPressed(int paramInt, char paramChar) {
			if (paramChar == 'w') {
				w_pressed = true;
			} else if (paramChar == 'a') {
				a_pressed = true;
			} else if (paramChar == 's') {
				s_pressed = true;
			} else if (paramChar == 'd') {
				d_pressed = true;
			}else if(Character.isDigit(paramChar)){
				int weaponCode=Character.getNumericValue(paramChar);
				player.setWeapon(weaponCode);
			}
			
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
