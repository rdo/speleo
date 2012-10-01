package ru.reksoft.platformer;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import ru.reksoft.platformer.states.menu.GamePauseState;
import ru.reksoft.platformer.states.menu.MainMenuState;

public class Platformer extends StateBasedGame {

	public static final int SCREEN_WIDTH = 1024;
	public static final int SCREEN_HEIGHT = 768;
	private static final int FRAME_RATE = 25;

	public static final int MAIN_MENU_STATE = 0;
	public static final int GAMEPLAY_STATE = 1;
	public static final int PAUSE_STATE = 2;
	public static final int LOAD_GAME = 3;

	public Platformer(String title) {
		super(title);
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

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		addState(new MainMenuState(MAIN_MENU_STATE));
		// no need to add this state now, it will be added when "new game"
		// button will be pressed
		// addState(new PlayState(GAMEPLAY_STATE, new GameProgress()));
		addState(new GamePauseState(PAUSE_STATE));

	}

}
