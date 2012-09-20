package ru.reksoft.platformer.states.menu.buttons;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import ru.reksoft.platformer.Platformer;
import ru.reksoft.platformer.states.menu.LoadState;

public class ToLoadMenuButton extends AbstractMenuButton {

	public ToLoadMenuButton() {
		super();
		text="load game";
	}

	@Override
	public void onClick(GameContainer gc, StateBasedGame game) {
		
		try {
			LoadState state = new LoadState(Platformer.LOAD_GAME);
			state.init(gc, game);
			game.addState(state);
			game.enterState(Platformer.LOAD_GAME);
		} catch (SlickException e) {
		}
	}

}
