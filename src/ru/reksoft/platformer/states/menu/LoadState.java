package ru.reksoft.platformer.states.menu;

import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import ru.reksoft.platformer.GameProgress;
import ru.reksoft.platformer.SaveManager;
import ru.reksoft.platformer.states.menu.buttons.LoadGameButton;
import ru.reksoft.platformer.states.menu.buttons.Spacer;
import ru.reksoft.platformer.states.menu.buttons.ToMainMenuButton;

public class LoadState extends AbstractMenuState {

	private SaveManager manager = SaveManager.getInstance();

	public LoadState(int id) {
		super(id);

	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		List<GameProgress> saves = manager.getProgressList();
		for (GameProgress gameProgress : saves) {
			addButton(new LoadGameButton(gameProgress));
		}
		addButton(new Spacer());
		addButton(new ToMainMenuButton());
	}

}
