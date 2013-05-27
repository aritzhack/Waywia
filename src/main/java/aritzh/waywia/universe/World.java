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
import aritzh.waywia.bds.BDSInt;
import aritzh.waywia.bds.BDSStorable;
import aritzh.waywia.bds.BDSString;
import aritzh.waywia.blocks.BackgroundBlock;
import aritzh.waywia.blocks.Block;
import aritzh.waywia.entity.Entity;
import aritzh.waywia.entity.QuadEntity;
import aritzh.waywia.entity.player.Player;
import aritzh.waywia.util.Matrix;
import aritzh.waywia.util.ParametrizedFunction;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.newdawn.slick.Graphics;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class World implements BDSStorable {

	private final Universe universe;
	private final File root;

	private Multimap<String, Entity> entities = ArrayListMultimap.create();
	private HashMap<String, Player> players = new HashMap<>();
	private Matrix<Block> blocks = new Matrix<Block>(100, 100, new BackgroundBlock());

	private BDSCompound customData = new BDSCompound("CustomData");

	private String worldName;

	public World(String name, Universe universe, File root) throws IOException {
		this.worldName = name;
		this.root = root;
		if (!(this.root.exists() || this.root.mkdirs()))
			throw new IOException("Couldn't make root folder for world " + this);
		this.universe = universe;
		this.toBDS().writeToFile(new File(root, "world.dat"));
	}

	public World(Universe universe, BDSCompound data, File root) {
		this.universe = universe;
		this.root = root;
		this.parseWorldData(data);
		this.spanwEntity(new QuadEntity(200, 200));
	}

	private void parseWorldData(BDSCompound compound) {
		BDSString nameBDS = compound.getString("Name", 0);
		this.worldName = (nameBDS != null ? nameBDS.getData() : "World " + root.getName());
		this.customData = compound.getComp("CustomData", 0);
		BDSCompound blocksBDS = compound.getComp("Blocks", 0);
		if (blocksBDS != null) {
			int i = 0;
			BDSCompound blockBDS;
			while ((blockBDS = blocksBDS.getComp("Block", i)) != null) {
				int x = blockBDS.getInt("x", 0).getData();
				int y = blockBDS.getInt("y", 0).getData();
				this.blocks.set(Block.fromBDS(blockBDS), x, y);
				i++;
			}
		}
		// TODO Parse players and entities
	}

	public void spanwEntity(Entity e) {
		this.entities.put(e.getName(), e);
	}

	public void update(final int delta) {
		this.blocks.runForEach(World.blockUpdate, delta);

		for (Entity e : entities.values()) {
			e.update(delta);
		}
	}

	@Override
	public BDSCompound toBDS() {
		BDSCompound ret = new BDSCompound("World");

		ret.add(new BDSString(this.worldName, "Name"));
		if (this.customData != null) ret.add(this.customData);

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
		blocks.addAll(this.blocks.runForEach(blockSave));
		ret.add(blocks);
		return ret;
	}

	public void render(Graphics g) {
		this.blocks.runForEach(World.blockRender, g);

		for (Entity e : entities.values()) {
			e.render(g);
		}
	}

	public void removeEntity(Entity e) {
		this.entities.remove(e.getName(), e);
	}

	public static List<World> getWorldList(Universe univ, File root) {
		List<World> ret = new ArrayList<>();
		for (File folder : root.listFiles(Universe.onlyFolders)) {
			File f = new File(folder, "world.dat");
			if (!f.exists()) continue;
			BDSCompound data;
			try {
				data = new BDSCompound(f);
			} catch (Exception ignored) {
				continue; // Couldn't parse file -> not valid -> Skip
			}
			ret.add(new World(univ, data, folder));
		}
		return ret;
	}

	public String getName() {
		return worldName;
	}

	private static ParametrizedFunction<Block, Object> blockUpdate = new ParametrizedFunction<Block, Object>() {
		@Override
		public Object apply(Block input, Object... args) {
			input.update((int) args[0], (int) args[1], (int) args[2]);
			return null;
		}
	};

	private static ParametrizedFunction<Block, BDSCompound> blockSave = new ParametrizedFunction<Block, BDSCompound>() {
		@Override
		public BDSCompound apply(Block input, Object... args) {
			BDSCompound ret = input.toBDS();
			ret.add(new BDSInt((int) args[0], "x"));
			ret.add(new BDSInt((int) args[1], "y"));
			return ret;
		}
	};

	private static ParametrizedFunction<Block, Object> blockRender = new ParametrizedFunction<Block, Object>() {
		@Override
		public Object apply(Block input, Object... args) {
			input.render((int) args[0], (int) args[1], (Graphics) args[2]);
			return null;
		}
	};
}
