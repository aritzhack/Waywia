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

package aritzh.waywia.blocks;

import aritzh.waywia.lib.BlockLib;
import aritzh.waywia.universe.World;
import org.newdawn.slick.geom.Rectangle;

/**
 * @author Aritz Lopez
 */
public class BackgroundBlock extends Block {

    @Override
    public String getName() {
        return "Background";
    }

    @Override
    public void clicked(float x, float y, World world) {
        if (!world.anyEntityCollides(new Rectangle(x, y, Block.SIZE, Block.SIZE)))
            world.setBlock((int) x, (int) y, BlockLib.IDS.WALL);
    }

    @Override
    public void update(int x, int y, int delta, World arg) {
        super.update(x, y, delta, arg);
    }
}
