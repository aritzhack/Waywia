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

import aritzh.waywia.entity.ai.AI;
import aritzh.waywia.universe.World;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aritz Lopez
 */
public abstract class EntityNPC extends Entity {

    protected final List<AI> ais = new ArrayList<>();

    public EntityNPC() {
        super();
    }

    public EntityNPC(int posX, int posY) {
        this.setPosition(posX, posY);
    }

    @Override
    public void update(int delta, World world) {
        for (AI ai : this.ais) {
            ai.perform(this, delta);
        }

        super.update(delta, world);
    }
}
