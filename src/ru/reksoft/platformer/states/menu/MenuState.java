package ru.reksoft.platformer.states.menu;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ru.reksoft.platformer.Platformer;
import ru.reksoft.platformer.states.play.PlayState;

public class MenuState extends BasicGameState{
	
	private int id;
	
	private List<MenuButton> menu = new ArrayList<MenuButton>();
	
	private MenuButton lastSelect; 
	
	public MenuState(int id) {
		super();
		this.id = id;
		
	}
	
	@Override
	public void init(final GameContainer arg0, final StateBasedGame arg1)
			throws SlickException {
		MenuButton newGameBtn = new MenuButton("new game", 500, 300, 100, 25);
		newGameBtn.setClickAction(new MenuAction() {
			
			@Override
			public void perform(StateBasedGame game) {
				
				try {
					PlayState playState = new PlayState(Platformer.GAMEPLAYSTATE);
					playState.init(arg0, arg1);
					game.addState(playState);
					game.enterState(Platformer.GAMEPLAYSTATE);
				} catch (SlickException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		MenuButton continueBtn = new MenuButton("continue", 500, 350, 100, 25);
		continueBtn.setClickAction(new MenuAction() {
			
			@Override
			public void perform(StateBasedGame game) {
				game.enterState(Platformer.GAMEPLAYSTATE);
				
			}
		});
		MenuButton exitBtn = new MenuButton("exit",500,400,100,25);
		exitBtn.setClickAction(new MenuAction() {
			
			@Override
			public void perform(StateBasedGame game) {
				System.exit(0);
				
			}
		});
		menu.add(newGameBtn);
		menu.add(continueBtn);
		menu.add(exitBtn);
		
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		for(MenuButton m:menu){
			m.render(arg2);
		}
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		if(lastSelect!=null){
			lastSelect.onClick(arg1);
			lastSelect=null;
		}
		
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		for(MenuButton m:menu){
			if(m.isCursorOver(x, y)){
				lastSelect=m;
			}
		}
	}

	

}
