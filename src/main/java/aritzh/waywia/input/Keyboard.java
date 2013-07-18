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

package aritzh.waywia.input;

import aritzh.waywia.core.Game;
import org.newdawn.slick.*;
import org.newdawn.slick.imageout.ImageOut;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Keyboard implements KeyListener {

	private final Game game;

	public Keyboard(Game game) {
		this.game = game;
	}

	@Override
	public void keyPressed(int key, char c) {
		GameContainer gc = this.game.getGc();
		switch (key) {
			case Input.KEY_S:
				try {
					Image target = new Image(gc.getWidth(), gc.getHeight());
					gc.getGraphics().copyArea(target, 0, 0);
					ImageOut.write(target, "screenshot.png", false);
					target.destroy();
				} catch (SlickException e) {
					Game.logger.exception("Could not save screenshot", e);
				}
				break;
			default:
				break;
		}
		this.game.getCurrentState().keyPressed(key, c);
	}

	@Override
	public void keyReleased(int key, char c) {
		this.game.getCurrentState().keyReleased(key, c);
	}

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
}
