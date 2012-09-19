package ru.reksoft.platformer;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import ru.reksoft.platformer.states.menu.MenuState;
import ru.reksoft.platformer.states.play.PlayState;


public class Platformer extends StateBasedGame {
	
	
	public static final int SCREEN_WIDTH = 1024;
	public static final int SCREEN_HEIGHT = 768;
	private static final int FRAME_RATE = 20;
	
	
	 public static final int MAINMENUSTATE = 0;
	 public static final int GAMEPLAYSTATE = 1;

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
		addState(new MenuState(MAINMENUSTATE));
        addState(new PlayState(GAMEPLAYSTATE));
        
        //enterState(GAMEPLAYSTATE);
		
	}

}
