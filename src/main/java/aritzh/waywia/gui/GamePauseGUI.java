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

import aritzh.waywia.core.states.InGameState;
import aritzh.waywia.core.states.MenuState;
import aritzh.waywia.gui.components.Button;
import aritzh.waywia.gui.components.GUI;
import org.newdawn.slick.Input;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class GamePauseGUI extends GUI {

    private final Button resumeButton, exitButton;
    private final GUI gui;

    public GamePauseGUI(InGameState state, GUI gui) {
        super(state);
        this.gui = gui;

        this.addElement(resumeButton = new Button("Resume", -this.width / 2, -this.height / 2 + 50));
        this.addElement(exitButton = new Button("Exit", -this.width / 2, -this.height / 2));
    }

    @Override
    public boolean stopsGame() {
        return true;
    }

    @Override
    public void clicked(int id) {
        super.clicked(id);
        if (id == this.resumeButton.getID()) this.state.openGUI(gui);
        else if (id == this.exitButton.getID()) {
            this.state.openGUI(gui);
            this.state.getGame().menuState.openGUI(new MainMenuGUI((MenuState) this.state.getGame().menuState));
            this.state.getGame().enterState(this.state.getGame().menuState.getID());
        }
    }

    @Override
    public void keyPressed(int key, char c) {
        super.keyPressed(key, c);
        if (key == Input.KEY_E) {
            this.state.openGUI(this.gui);
        }
    }
}
