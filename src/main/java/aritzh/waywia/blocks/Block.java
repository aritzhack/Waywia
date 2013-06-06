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
import aritzh.waywia.bds.BDSInt;
import aritzh.waywia.bds.BDSStorable;
import aritzh.waywia.universe.World;
import aritzh.waywia.util.RenderUtil;
import com.google.common.collect.Lists;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.util.List;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public abstract class Block implements BDSStorable {

	private static final List<Block> blocks = Lists.newArrayList();

	Image defaulTexture = RenderUtil.getImage("defaultBlock");

	public static final int SIZE = 32;

	public static void registerBlock(Block block) {
		if (Block.blocks.contains(block))
			throw new IllegalArgumentException("Block " + block + " was already registered");
		if (Block.blocks.size() > block.getID() && Block.blocks.get(block.getID()) != null)
			throw new IllegalArgumentException("Block id " + block.getID() + "is occupied by " + Block.blocks.get(block.getID()) + " when adding " + block);
		Block.blocks.add(block.getID(), block);
	}

	public static Block getBlock(int id) {
		return Block.blocks.get(id);
	}

	public static Block fromBDS(BDSCompound comp) {
		try {
			int id = comp.getInt("ID", 0).getData();
			return Block.getBlock(id);
		} catch (NullPointerException ignored) {
			return null;
		}
	}

	public void render(int x, int y, Graphics g, World world) {
		g.drawImage(defaulTexture, x * SIZE, y * SIZE);
	}

	public BDSCompound toBDS() {
		return new BDSCompound("Block").add(new BDSInt(this.getID(), "ID"));
	}

	public void clicked(int x, int y) {
	}

	public void update(int x, int y, int delta, World arg) {
	}

	public abstract int getID();

	public abstract String getName();
}
