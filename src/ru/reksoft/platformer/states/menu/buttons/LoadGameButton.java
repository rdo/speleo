package ru.reksoft.platformer.states.menu.buttons;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import ru.reksoft.platformer.GameProgress;
import ru.reksoft.platformer.Platformer;
import ru.reksoft.platformer.states.play.PlayState;

public class LoadGameButton extends AbstractMenuButton {

	private GameProgress progress;

	public LoadGameButton(GameProgress pro) {
		super();
		this.progress = pro;
	}

	@Override
	public void onClick(GameContainer gc, StateBasedGame game) {
		try {
			PlayState state = new PlayState(Platformer.GAMEPLAY_STATE, progress);
			state.init(gc, game);
			game.addState(state);
			game.enterState(Platformer.GAMEPLAY_STATE);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return progress.toString();
	}
}
