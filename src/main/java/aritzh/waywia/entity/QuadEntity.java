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

package aritzh.waywia.entity;

import aritzh.waywia.util.RenderUtil;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class QuadEntity extends EntityNPC {

    private static final Image texture = RenderUtil.getImage("quadEntity");

    public QuadEntity() {
        super();
    }

    public QuadEntity(int posX, int posY) {
        super(posX, posY);
    }

    @Override
    public int getMaxHealth() {
        return 100;
    }

    @Override
    public Shape getBoundingShape() {
        return new Rectangle(0, 0, texture.getWidth(), texture.getHeight());
    }

    @Override
    public void render(Graphics g) {
        texture.draw(this.posX, this.posY);
    }

    @Override
    public String getName() {
        return "QuadEntity";
    }
}
