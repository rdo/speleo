package ru.reksoft.platformer.states.menu;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ru.reksoft.platformer.states.menu.buttons.AbstractMenuButton;

public abstract class AbstractMenuState extends BasicGameState {

	private int id;

	protected List<AbstractMenuButton> menu = new ArrayList<AbstractMenuButton>();

	private AbstractMenuButton lastSelect;
	
	private AbstractMenuButton hover;

	private int buttonWidth = 100;
	private int buttonHeight = 25;

	private int menuStartX = 350;
	private int menuStartY = 300;

	public AbstractMenuState(int id) {
		super();
		this.id = id;

	}

	public void addButton(AbstractMenuButton btn) {
		int yOffset = menu.size() * buttonHeight;
		btn.setSize(buttonWidth, buttonHeight);
		btn.setPosition(menuStartX, menuStartY + yOffset);
		menu.add(btn);
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		for (AbstractMenuButton m : menu) {
			if(m.equals(hover)){
				m.renderHover(arg2);	
			}else{
				m.render(arg2);	
			}
			
		}

	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		if (lastSelect != null) {
			lastSelect.onClick(arg0, arg1);
			lastSelect = null;
		}

	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		for (AbstractMenuButton m : menu) {
			if (m.isCursorOver(x, y)) {
				lastSelect = m;
				return;
			}
		}
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		super.mouseMoved(oldx, oldy, newx, newy);
		for (AbstractMenuButton m : menu) {
			if (m.isCursorOver(newx, newy)) {
				hover=m;
				return;
			}
		}
		hover=null;
	}
	

}
