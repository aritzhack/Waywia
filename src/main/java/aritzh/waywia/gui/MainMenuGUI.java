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
import aritzh.waywia.gui.components.Button;
import aritzh.waywia.gui.components.GUI;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class MainMenuGUI extends GUI {

	private Button newGameButton, loadButton, exitButton;

	public MainMenuGUI(MenuState state) {
		super(state);
		this.addElement(newGameButton = new Button("New Game", -this.width / 2, -this.height / 2 + 100));
		this.addElement(loadButton = new Button("Load", -this.width / 2, -this.height / 2 + 50));
		this.addElement(exitButton = new Button("Exit", -this.width / 2, -this.height / 2));
	}

	@Override
	public void clicked(int id) {
		super.clicked(id);
		if (id == exitButton.getID()) {
			this.state.getGame().exit();
		} else if (id == newGameButton.getID()) {
			((MenuState) this.state).openGUI(new NewUniverseGUI((MenuState) this.state));
		} else if (id == loadButton.getID()) {
			((MenuState) this.state).openGUI(new UniverseListGUI((MenuState) this.state));
		}
	}
}
