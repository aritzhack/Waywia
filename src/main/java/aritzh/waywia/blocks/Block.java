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
import aritzh.waywia.bds.BDSStorable;
import aritzh.waywia.bds.BDSString;
import aritzh.waywia.core.GameLogger;
import com.google.common.collect.Maps;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.Map;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public abstract class Block implements BDSStorable {

	private static final Map<String, Class<? extends Block>> stringToBlock = Maps.newHashMap();

	public static final int SIZE = 16;

	public static void registerBlock(Class<? extends Block> clazz) {
		if (clazz == null) throw new IllegalArgumentException("Null block class cannot be registered");
		try {
			Block.stringToBlock.put(clazz.newInstance().getName(), clazz);
		} catch (InstantiationException | IllegalAccessException | ExceptionInInitializerError ex) {
			InstantiationException e = new InstantiationException("Could not register block");
			e.initCause(ex.getCause());
			GameLogger.exception("Failed to register block class " + clazz, e);
		}
	}

	private static Block newBlockFromName(String name) {
		try {
			return Block.stringToBlock.get(name).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			GameLogger.exception("Could not instantiate block with name \"" + name + "\"", e);
		} catch (NullPointerException e) {
			throw new IllegalArgumentException("Block type \"" + name + "\" is not registered. This is a bug!");
		}
		return null;
	}

	public static Block fromBDS(BDSCompound comp) {
		String name = comp.getString("Name", 0).getData();
		return Block.newBlockFromName(name);
	}

	public void render(int x, int y, Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.blue);
		g.fillRect(x * Block.SIZE, y * Block.SIZE, Block.SIZE, Block.SIZE);
		g.setColor(Color.white);
		g.drawRect(x * Block.SIZE, y * Block.SIZE, Block.SIZE, Block.SIZE);
		g.setColor(c);
	}

	public abstract String getName();

	public BDSCompound toBDS() {
		return new BDSCompound("Block").add(new BDSString(this.getName(), "Name"));
	}

	public void clicked(int x, int y) {
	}

	public void update(int x, int y, int delta) {
	}
}
