package ru.reksoft.platformer.states.menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import ru.reksoft.platformer.states.menu.buttons.ContinueButton;
import ru.reksoft.platformer.states.menu.buttons.ToMainMenuButton;

public class ContinueGameState extends MainMenuState{

	public ContinueGameState(int id) {
		super(id);
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		addButton(new ContinueButton());
		addButton(new ToMainMenuButton());
		//ContinueButton b1 = new ContinueButton();
		//b1.setPosition(350, 300);
		//b1.setSize(100, 25);
		//menu.add(b1);
		
		//ToMainMenuButton b2 = new ToMainMenuButton();
		//b2.setPosition(350, 350);
		//b1.setSize(100, 25);
		//menu.add(b2);
	}
	
	

}
