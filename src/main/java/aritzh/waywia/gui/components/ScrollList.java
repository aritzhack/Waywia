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

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ScrollList<T> extends GUIElement {

    private static Image scrollQuad;
    private static Image scrollBar;
    private final java.util.List<T> items = new ArrayList<>();
    private final Rectangle bbox;
    private T selected;
    private float x, y, width, height;
    private int scrollPercent = 0;
    private Image backGround;

    public ScrollList(T[] items, float x, float y, float w, float h, int id) {
        this(Arrays.asList(items), x, y, w, h);
    }

    public ScrollList(List<T> items, float x, float y, float w, float h) {
        this.items.addAll(items);
        this.bbox = new Rectangle(this.x, this.y, this.width, this.height);
        this.initTextures();
    }

    private void initTextures() {
        // TODO load the textures
    }

    public ScrollList(T item, float x, float y, float w, float h) {
        this.items.add(item);
        this.bbox = new Rectangle(x, y, width, height);
    }


    @Override
    public void render(Graphics g) {
        // TODO Render!
    }

    @Override
    public Rectangle getBBox() {
        return bbox;
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        super.mouseClicked(button, x, y, clickCount);
        // TODO Handle clicking to select
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
        super.mouseDragged(oldx, oldy, newx, newy);
    }
}
