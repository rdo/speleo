package ru.reksoft.platformer.states.menu.buttons;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class ExitButton extends AbstractMenuButton {

	@Override
	public void onClick(GameContainer gc, StateBasedGame game) {
		System.exit(0);

	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return "exit";
	}

}
