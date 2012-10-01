package ru.reksoft.platformer.states.menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import ru.reksoft.platformer.states.menu.buttons.ExitButton;
import ru.reksoft.platformer.states.menu.buttons.NewGameButton;
import ru.reksoft.platformer.states.menu.buttons.ToLoadMenuButton;

public class MainMenuState extends AbstractMenuState {

	public MainMenuState(int id) {
		super(id);
		

	}
	@Override
	public void init(final GameContainer arg0, final StateBasedGame arg1)
			throws SlickException {

		addButton(new NewGameButton());
		addButton(new ToLoadMenuButton());
		addButton(new ExitButton());

	}



}
