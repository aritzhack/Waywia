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

import aritzh.waywia.bds.BDSCompound;
import aritzh.waywia.bds.BDSString;
import aritzh.waywia.core.GameLogger;
import org.newdawn.slick.Graphics;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public abstract class Block {

	int hardness;

	public abstract void render(Graphics g);

	public void update(int delta, int x, int y) {
		GameLogger.debug("Updated: (" + x + ", " + y + "), after " + delta);
	}

	public abstract String getName();

	public BDSCompound toBDS() {
		return new BDSCompound("Block").add(new BDSString(this.getName(), "Name"));
	}

	public static Block fromBDS(BDSCompound blockBDS) {
		// TODO Parse block
		return null;  //To change body of created methods use File | Settings | File Templates.
	}
}
