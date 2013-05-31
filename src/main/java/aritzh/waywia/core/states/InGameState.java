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
import aritzh.waywia.entity.Entity;
import aritzh.waywia.entity.QuadEntity;
import aritzh.waywia.universe.Universe;
import org.newdawn.slick.Graphics;

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
	public void init() {
		Entity.registerEntity(QuadEntity.class);
	}

	@Override
	public void render(Graphics g) {
	}

	@Override
	public void update(int delta) {

		if (this.universe != null) this.universe.update(delta);
		else {
			this.universe = Universe.loadUniverseFromFolder(new File(this.game.savesDir, "uniBase"));
			if (this.universe == null) {
				try {
					this.universe = Universe.createNewUniverse("UniBase", this.game.savesDir, "UniWorld");
				} catch (IOException e) {
					GameLogger.logAndThrowAsRuntime("Could not create universe UniBase", e);
				}
			}
		}

	}
}
