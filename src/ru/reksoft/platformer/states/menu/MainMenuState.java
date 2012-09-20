package ru.reksoft.platformer.states.menu;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ru.reksoft.platformer.states.menu.buttons.ExitButton;
import ru.reksoft.platformer.states.menu.buttons.AbstractMenuButton;
import ru.reksoft.platformer.states.menu.buttons.NewGameButton;

public class MainMenuState extends BasicGameState{
	
	private int id;
	
	protected List<AbstractMenuButton> menu = new ArrayList<AbstractMenuButton>();
	
	private AbstractMenuButton lastSelect; 
	
	private int buttonWidth=100;
	private int buttonHeight=25;
	
	private int menuStartX=350;
	private int menuStartY=300;
	
	
	
	public MainMenuState(int id) {
		super();
		this.id = id;
		
	}
	
	
	
	public void addButton(AbstractMenuButton btn){
		int yOffset=menu.size()*buttonHeight;
		btn.setSize(buttonWidth, buttonHeight);
		btn.setPosition(menuStartX, menuStartY+yOffset);
		menu.add(btn);
	}
	
	@Override
	public void init(final GameContainer arg0, final StateBasedGame arg1)
			throws SlickException {
		
		addButton(new NewGameButton());
		addButton(new ExitButton());
		
		//NewGameButton b1 = new NewGameButton();
		//b1.setPosition(350, 300);
		//b1.setSize(100, 25);
		//menu.add(b1);
		
		//ExitButton b2 = new ExitButton();
		//b2.setSize(100, 25);
		//b2.setPosition(350, 350);
		//menu.add(b2);
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		for(AbstractMenuButton m:menu){
			m.render(arg2);
		}
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		if(lastSelect!=null){
			lastSelect.onClick(arg0, arg1);
			lastSelect=null;
		}
		
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		for(AbstractMenuButton m:menu){
			if(m.isCursorOver(x, y)){
				lastSelect=m;
			}
		}
	}

	

}
