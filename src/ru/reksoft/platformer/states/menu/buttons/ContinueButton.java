package ru.reksoft.platformer.states.menu.buttons;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import ru.reksoft.platformer.Platformer;

public class ContinueButton extends AbstractMenuButton{

	@Override
	public void onClick(GameContainer gc, StateBasedGame game) {
		game.enterState(Platformer.GAMEPLAY_STATE);
		
	}

	public ContinueButton() {
		text="continue";
	}

}
