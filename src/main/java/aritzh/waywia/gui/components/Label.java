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

package aritzh.waywia.gui.components;

/**
 * @author Aritz Lopez
 */
public class Label extends InputBox {

    public Label(String text, int x, int y, int width) {
        super(x, y, width);
        this.text = text;
    }

    @Override
    public void keyPressed(int key, char c) {
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
    }
}
