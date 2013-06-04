/*
 * Copyright (c) 2013 Aritzh (Aritz Lopez)
 *
 * This game is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This game is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this
 * game. If not, see http://www.gnu.org/licenses/.
 */

package aritzh.waywia.gui.components;

import aritzh.waywia.core.states.WaywiaState;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.InputListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public abstract class GUI implements InputListener {
	private final List<GUIElement> elements = new ArrayList<>();
	protected final WaywiaState state;
	protected final int width, height;
	private int lastElementID = 0;

	public GUI(WaywiaState state) {
		this.state = state;
		this.width = this.state.getGame().getGc().getWidth();
		this.height = this.state.getGame().getGc().getHeight();
	}

	public void render(Graphics g) {
		for (GUIElement e : this.elements) {
			e.render(g);
		}
	}

	protected final void addElement(GUIElement e) {
		this.elements.add(e);
		e.setID(lastElementID++);
	}

	public void mouseClicked(int button, int x, int y, int clickCount) {
		for (GUIElement e : this.elements) {
			if (e.getBBox().contains(x, y)) {
				this.clicked(e.getID());
			}
			e.mouseClicked(button, x, y, clickCount);
		}
	}

	public void mousePressed(int button, int x, int y) {
		for (GUIElement e : this.elements) {
			e.mousePressed(button, x, y);
		}
	}

	public void mouseReleased(int button, int x, int y) {
		for (GUIElement e : this.elements) {
			e.mouseReleased(button, x, y);
		}
	}

	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		for (GUIElement e : this.elements) {
			e.mouseMoved(oldx, oldy, newx, newy);
		}
	}

	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		for (GUIElement e : this.elements) {
			e.setHover(e.getBBox().contains(newx, newy));
			e.setPressed(e.getBBox().contains(newx, newy));
			e.mouseDragged(oldx, oldy, newx, newy);
		}
	}

	@Override
	public void mouseWheelMoved(int change) {
		for (GUIElement e : this.elements) {
			e.mouseWheelMoved(change);
		}
	}

	@Override
	public void keyPressed(int key, char c) {
		for (GUIElement e : this.elements) {
			e.keyPressed(key, c);
		}
	}

	@Override
	public void keyReleased(int key, char c) {
		for (GUIElement e : this.elements) {
			e.keyReleased(key, c);
		}
	}

	/**
	 * For use in sub-classes. This is called with the ID of the element clicked
	 *
	 * @param id The id of the element that was clicked
	 */
	public void clicked(int id) {
	}

	// InputListener methods not used at this level

	@Override
	public void setInput(Input input) {
	}

	@Override
	public boolean isAcceptingInput() {
		return true;
	}

	@Override
	public void inputEnded() {
	}

	@Override
	public void inputStarted() {
	}

	@Override
	public void controllerButtonPressed(int controller, int button) {
	}

	@Override
	public void controllerLeftPressed(int controller) {
	}

	@Override
	public void controllerLeftReleased(int controller) {
	}

	@Override
	public void controllerRightPressed(int controller) {
	}

	@Override
	public void controllerRightReleased(int controller) {
	}

	@Override
	public void controllerUpPressed(int controller) {
	}

	@Override
	public void controllerUpReleased(int controller) {
	}

	@Override
	public void controllerDownPressed(int controller) {
	}

	@Override
	public void controllerDownReleased(int controller) {
	}

	@Override
	public void controllerButtonReleased(int controller, int button) {
	}
}
