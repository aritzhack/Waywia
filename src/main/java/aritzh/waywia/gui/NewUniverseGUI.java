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

import aritzh.waywia.core.GameLogger;
import aritzh.waywia.core.states.MenuState;
import aritzh.waywia.gui.components.Button;
import aritzh.waywia.gui.components.GUI;
import aritzh.waywia.gui.components.InputBox;
import aritzh.waywia.gui.components.Label;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class NewUniverseGUI extends GUI {

	private final Button newButton, backButton;
	private final InputBox worldName;
	private final Label title;

	public NewUniverseGUI(MenuState menuState) {
		super(menuState);

		this.addElement(newButton = new Button("Create", -this.width / 2 + 75, this.height - 50));
		this.addElement(backButton = new Button("Back", -this.width / 2 - 75, this.height - 50));

		this.addElement(worldName = new InputBox(-this.width / 2, -this.height / 2, 200));

		this.addElement(title = new Label("Hola!", -this.width / 2, -this.height / 2 + 100, 200));
	}

	@Override
	public void clicked(int id) {
		if (id == this.newButton.getID() && !this.worldName.getText().trim().equals("")) {
			GameLogger.debug("Creating world: " + this.worldName.getText().trim());
		} else if (id == this.backButton.getID()) {
			this.state.openGUI(new MainMenuGUI((MenuState) this.state));
		}
	}
}
