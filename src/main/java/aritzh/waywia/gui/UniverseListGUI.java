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

package aritzh.waywia.gui;

import aritzh.waywia.core.states.MenuState;
import aritzh.waywia.gui.components.GUI;
import org.newdawn.slick.Input;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class UniverseListGUI extends GUI {

	public UniverseListGUI(MenuState state) {
		super(state);
		//List<Universe> universes = Universe.getUniverseList(new File(this.state.getGame().baseDir, "saves"));
		//this.addElement(new ScrollList<>(universes, 0, 0, this.width, this.height));
		this.state.getGame().enterState(this.state.getGame().inGameState.getID());
	}

	@Override
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_ESCAPE) {
			((MenuState) this.state).openGUI(new MainMenuGUI((MenuState) this.state));
		} else {
			super.keyPressed(key, c);
		}
	}
}
