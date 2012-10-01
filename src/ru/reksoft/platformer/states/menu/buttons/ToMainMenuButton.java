package ru.reksoft.platformer.states.menu.buttons;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import ru.reksoft.platformer.Platformer;

public class ToMainMenuButton extends AbstractMenuButton {

	@Override
	public void onClick(GameContainer gc, StateBasedGame game) {
		game.enterState(Platformer.MAIN_MENU_STATE);

	}

	@Override
	public String getText() {
		return "back to main menu";
	}

}
