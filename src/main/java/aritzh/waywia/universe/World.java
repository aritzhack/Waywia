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

package aritzh.waywia.universe;

import aritzh.waywia.bds.BDSCompound;
import aritzh.waywia.bds.BDSString;
import aritzh.waywia.blocks.Block;
import aritzh.waywia.entity.Entity;
import aritzh.waywia.entity.player.Player;
import org.newdawn.slick.Graphics;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class World {

	private final Universe universe;

	private HashMap<Integer, Entity> entities = new HashMap<Integer, Entity>();
	private HashMap<String, Player> players = new HashMap<String, Player>();
	private ArrayList<ArrayList<Block>> blocks = new ArrayList<ArrayList<Block>>();
	
	private BDSCompound customData = new BDSCompound("CustomData");

	private String worldName;

	public World(Universe universe, File worldFolder) {
		this.universe = universe;
	}

	private void load() {
	}

	// TODO How to check the coords of the block?
	public void update(int delta) {
		for (ArrayList<Block> blocklist : blocks) {
			for (Block b : blocklist) {
				b.update(delta);
			}
		}

		for (Entity e : entities.values()) {
			e.update(delta);
		}
	}

	// TODO Really save to BDS
	public BDSCompound toBDS() {
		BDSCompound ret = new BDSCompound("World");
		BDSCompound entities = new BDSCompound("Entities");
		for (Entity e : this.entities.values()) {
			if (e instanceof Player) continue;
			entities.add(e.toBDS());
		}
		ret.add(entities);
		BDSCompound players = new BDSCompound("Players");
		for (Player p : this.players.values()) {
			players.add(p.toBDS());
		}
		ret.add(players);
		BDSCompound blocks = new BDSCompound("Blocks");
		for (ArrayList<Block> blockList : this.blocks) {
			for (Block b : blockList) {
				blocks.add(b.toBDS());
			}
		}
		ret.add(new BDSString(this.worldName, "Name"));
		ret.add(this.customData);
		return ret;
	}

	public void render(Graphics g) {
		for (ArrayList<Block> blockList : this.blocks) {
			for (Block b : blockList) {
				b.render(g);
			}
		}

		for (Entity e : entities.values()) {
			e.render(g);
		}
	}
}
