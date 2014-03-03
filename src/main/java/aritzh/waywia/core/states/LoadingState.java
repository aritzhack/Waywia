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

import aritzh.waywia.core.Config;
import aritzh.waywia.core.Game;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.transition.FadeInTransition;

import java.io.IOException;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class LoadingState extends WaywiaState {

	private int step = 0;

	public LoadingState(Game game) {
		super(game, "Loading");
	}

	@Override
	public void init() {
	}

	@Override
	public void render(Graphics g) {
		switch (step) {
			case 0:
				g.setColor(Color.pink);
				g.fillRect(0, 0, this.game.getGc().getWidth(), this.game.getGc().getHeight());
				if (!Config.GAME.getBoolean("Mods", "loadMods")) {
					step = 2;
					Game.logger.log("Skipping mod loading");
					return;
				}
				break;
			case 1:
				Game.logger.log("Starting to load mods...");

                long before = System.currentTimeMillis();
                try {
                    this.game.mods.loadAllExtensions(this.game.modsDir);
                } catch (ReflectiveOperationException | IOException ignored) { }
                Game.logger.debug("Mod-loading lasted " + (System.currentTimeMillis() - before) / 1000.0 + " seconds");

                Game.logger.log("Mods loaded");
				break;
			case 2:
				this.game.enterState(this.game.menuState.getID(), null, new FadeInTransition());
				return;
		}
		this.step++;
	}

	@Override
	public void update(int delta) {
	}

	@Override
	public int getID() {
		return 4;
	}
}
