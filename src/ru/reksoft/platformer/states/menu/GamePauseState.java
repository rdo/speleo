package ru.reksoft.platformer.states.menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import ru.reksoft.platformer.states.menu.buttons.ContinueButton;
import ru.reksoft.platformer.states.menu.buttons.SaveButton;
import ru.reksoft.platformer.states.menu.buttons.Spacer;
import ru.reksoft.platformer.states.menu.buttons.ToMainMenuButton;

public class GamePauseState extends AbstractMenuState {

	public GamePauseState(int id) {
		super(id);
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		addButton(new ContinueButton());
		addButton(new SaveButton());
		addButton(new Spacer());
		addButton(new ToMainMenuButton());
	}

}
