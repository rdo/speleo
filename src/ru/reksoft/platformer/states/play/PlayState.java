package ru.reksoft.platformer.states.play;

import org.newdawn.fizzy.Body;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import ru.reksoft.platformer.GameProgress;
import ru.reksoft.platformer.Platformer;
import ru.reksoft.platformer.objects.DynamicGameObject;
import ru.reksoft.platformer.objects.LightSource;
import ru.reksoft.platformer.objects.npc.Controllable;
import ru.reksoft.platformer.objects.npc.Player;
import ru.reksoft.platformer.objects.repository.HookBullet;

public class PlayState extends BasicGameState {

	private static final int X_DISPLACEMENT = Platformer.SCREEN_WIDTH / 2;
	private static final int Y_DISPLACEMENT = Platformer.SCREEN_HEIGHT / 2;

	private PlatformerLevel world;

	private FogOfWar fog;

	private TiledBackground background;

	private Player player;

	boolean w_pressed = false;
	boolean a_pressed = false;
	boolean s_pressed = false;
	boolean d_pressed = false;

	private int stateId;

	private GameProgress progress;

	private boolean exitToMenu = false;

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		world = new PlatformerLevel(new TiledMap(progress.currentLevel),
				progress.player);
		world.setGraphics(arg0.getGraphics());
		world.addListener(new JumpCollisionListener());
		world.addListener(new DynamicObjectCollisionListener(world));

		player = world.getPlayer();

		player.setStats(progress.player);
		// player.setHP(progress.hp);
		// player.setExp(progress.exp);

		fog = new FogOfWar(world);
		background = new TiledBackground(world.mapWidth, world.mapHeigth);

		world.update(1);

		System.out.println("Play state inited");

	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		background.render(g, world.xOffset, world.yOffset);
		renderWorld(g);
		fog.render(g);
		renderUI(g);

	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		// TODO: here should be next level loading

		if (exitToMenu) {
			arg1.enterState(Platformer.PAUSE_STATE);
			exitToMenu = false;
		}

		if (player.getHp() <= 0) {
			arg1.enterState(Platformer.MAIN_MENU_STATE);
		}

		if (player.getX() < 0 || player.getX() > world.mapWidth
				|| player.getY() < 0 || player.getY() > world.mapHeigth) {
			// outside the map. game over
			arg1.enterState(Platformer.MAIN_MENU_STATE);
		}

		for (int i = 0; i < world.getBodyCount(); i++) {
			Body b = world.getBody(i);
			Object ud = b.getUserData();
			if (ud != null && ud instanceof Controllable) {
				((Controllable) ud).update();
			}
			if (ud != null && ud instanceof LightSource) {
				fog.update((LightSource) ud);
			}
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

		if (!d_pressed && !a_pressed && !player.onJoint) {
			player.stopMovement();
		}
		if (s_pressed) {
			player.stopMovement();
		}

		world.update(1);

	}

	@Override
	public int getID() {
		return stateId;
	}

	public PlayState(int stateId, GameProgress progress) {
		super();
		this.stateId = stateId;
		this.progress = progress;
	}

	private void renderWorld(Graphics g) {
		if (player.getX() > X_DISPLACEMENT
				&& player.getX() < world.mapWidth - X_DISPLACEMENT) {
			world.xOffset = (int) (player.getX() - X_DISPLACEMENT);
		}
		int physY = (int) (world.mapHeigth - player.getY());
		if (physY > Y_DISPLACEMENT && physY < world.mapHeigth - Y_DISPLACEMENT) {
			int newY = Platformer.SCREEN_HEIGHT - world.mapHeigth
					+ (physY - Y_DISPLACEMENT);
			world.yOffset = newY;
		} else if (physY < Y_DISPLACEMENT) {
			world.yOffset = Platformer.SCREEN_HEIGHT - world.mapHeigth;
		}
		// g.drawImage(background, 0, 0);

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

	private void renderUI(Graphics g) {
		g.drawString("HP: " + player.getHp() + " EXP: " + player.getExp()
				+ " weapon: "
				+ player.getDefaultBullet().getClass().getSimpleName(), 32, 4);
		g.drawString("Player position: " + player.getBody().getX() + ", "
				+ player.getBody().getY(), 32, 24);
	}

	@Override
	public void mouseClicked(int clickedBtn, int mouseX, int mouseY,
			int clickCount) {
		if (clickedBtn == 0) {
			player.shootTo(mouseX + world.xOffset, mouseY - world.yOffset);
		} else if (clickedBtn == 1) {
			if (player.onJoint) {
				player.releaseJoint();
			} else {
				HookBullet hook = new HookBullet();
				hook.setPlayer(player);
				player.shootTo(mouseX + world.xOffset, mouseY - world.yOffset,
						hook);
			}
		}
	}

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
		} else if (Character.isDigit(paramChar)) {
			int weaponCode = Character.getNumericValue(paramChar);
			player.setWeapon(weaponCode);
		} else if (paramChar == 27) {
			exitToMenu = true;
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

	public GameProgress getProgress() {
		progress.player = player.stats;
		return progress;
	}

}
