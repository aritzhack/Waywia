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
import aritzh.waywia.gui.MainMenuGUI;
import org.newdawn.slick.Graphics;

/**
 * @author Aritz Lopez
 */
public class MenuState extends WaywiaState {

    public MenuState(Game game) {
        super(game, "Menu");
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void render(Graphics g) {
    }

    @Override
    public void update(int delta) {
    }

    @Override
    public void init() {
        this.currGui = new MainMenuGUI(this);
    }
}
