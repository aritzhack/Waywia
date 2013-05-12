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

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.InputListener;
import org.newdawn.slick.geom.Rectangle;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public abstract class GUIElement implements InputListener {

	protected boolean render = true;

	protected boolean hover = false;
	protected boolean pressed;

	private int id = -1;

	public abstract void render(Graphics g);


	public void setRender(boolean render) {
		this.render = render;
	}

	public boolean shouldRender() {
		return render;
	}

	public abstract Rectangle getBBox();

	public int getID() {
		return this.id;
	}

	public void setHover(boolean hover) {
		this.hover = hover;
	}

	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}

	public void setID(int id) {
		this.id = id;
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
	public void controllerButtonPressed(int controller, int button) {
	}

	@Override
	public void controllerButtonReleased(int controller, int button) {
	}

	@Override
	public void keyPressed(int key, char c) {
	}

	@Override
	public void keyReleased(int key, char c) {
	}

	@Override
	public void mouseWheelMoved(int change) {
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		if (this.getBBox().contains(x, y)) {
			this.setPressed(true);
		}
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		if (this.getBBox().contains(x, y)) {
			this.setPressed(false);
		}
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		this.setHover(this.getBBox().contains(newx, newy));
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
	}

	@Override
	public void setInput(Input input) {
	}

	@Override
	public boolean isAcceptingInput() {
		return false;
	}

	@Override
	public void inputEnded() {
	}

	@Override
	public void inputStarted() {
	}
}
