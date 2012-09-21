package ru.reksoft.platformer.states.menu.buttons;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import ru.reksoft.platformer.Platformer;
import ru.reksoft.platformer.SaveManager;
import ru.reksoft.platformer.states.play.PlayState;

public class SaveButton extends AbstractMenuButton {
	SaveManager manager;

	@Override
	public void onClick(GameContainer gc, StateBasedGame game) {
		try {
			PlayState state = (PlayState) game
					.getState(Platformer.GAMEPLAY_STATE);
			manager.saveProgress(state.getProgress());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		game.enterState(Platformer.GAMEPLAY_STATE);
	}

	public SaveButton() {
		super();
		text = "save game";
		manager = SaveManager.getInstance();

	}

}
