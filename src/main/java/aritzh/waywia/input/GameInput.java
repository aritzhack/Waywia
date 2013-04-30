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
import aritzh.waywia.core.GameLogger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.imageout.ImageOut;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class GameInput {

	private final Game game;

	public GameInput(Game game) {
		this.game = game;
	}

	public void update(int i) throws SlickException {
		GameContainer gc = game.getGc();
		if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) gc.exit();
		if (gc.getInput().isKeyPressed(Input.KEY_S)) {
			Image target = new Image(gc.getWidth(), gc.getHeight());
			gc.getGraphics().copyArea(target, 0, 0);
			ImageOut.write(target, "screenshot.png", false);
			target.destroy();
		}
		if (gc.getInput().isKeyPressed(Input.KEY_R)) {
			GameLogger.log("Reload!");
			this.game.reload();
		}
	}
}
