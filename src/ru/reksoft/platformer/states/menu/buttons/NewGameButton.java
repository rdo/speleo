package ru.reksoft.platformer.states.menu.buttons;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import ru.reksoft.platformer.GameProgress;
import ru.reksoft.platformer.Platformer;
import ru.reksoft.platformer.states.play.PlayState;

public class NewGameButton extends AbstractMenuButton {

	@Override
	public void onClick(GameContainer gc, StateBasedGame game) {

		try {
			PlayState state = new PlayState(Platformer.GAMEPLAY_STATE,
					new GameProgress());
			state.init(gc, game);
			game.addState(state);
			game.enterState(Platformer.GAMEPLAY_STATE);
		} catch (SlickException e) {

		}

	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return "new game";
	}

}
