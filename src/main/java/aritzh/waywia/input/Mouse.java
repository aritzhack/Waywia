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
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Mouse implements MouseListener {

    private final Game game;

    public Mouse(Game game) {
        this.game = game;
    }

    @Override
    public void mouseWheelMoved(int change) {
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        this.game.getCurrentState().mouseClicked(button, x, y, clickCount);
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        this.game.getCurrentState().mousePressed(button, x, y);
    }

    @Override
    public void mouseReleased(int button, int x, int y) {
        this.game.getCurrentState().mouseReleased(button, x, y);
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        this.game.getCurrentState().mouseMoved(oldx, oldy, newx, newy);

    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
        this.game.getCurrentState().mouseDragged(oldx, oldy, newx, newy);
    }

    @Override
    public void setInput(Input input) {
    }

    @Override
    public boolean isAcceptingInput() {
        return true;
    }

    @Override
    public void inputEnded() {
    }

    @Override
    public void inputStarted() {
    }
}
