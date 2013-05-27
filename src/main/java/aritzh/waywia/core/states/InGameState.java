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

package aritzh.waywia.core.states;

import aritzh.waywia.core.Game;
import aritzh.waywia.core.GameLogger;
import aritzh.waywia.universe.Universe;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.io.File;
import java.io.IOException;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class InGameState extends WaywiaState {

	private Universe universe;

	public InGameState(Game game) {
		super(game, "In-Game");
	}

	@Override
	public int getID() {
		return 1;
	}

	@Override
	public void init(GameContainer container, StateBasedGame sbGame) throws SlickException {
		this.game = (Game) sbGame;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		if (this.universe != null) this.universe.render(g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if (this.universe == null) {
			try {
				this.universe = new Universe("uniBase", new File(this.getGame().savesDir, "uniBase"));
			} catch (IOException e) {
				GameLogger.logAndThrowAsRuntime("Error opening universe \"unibase\"", e);
				return;
			}
		}
		this.universe.update(delta);
	}
}
