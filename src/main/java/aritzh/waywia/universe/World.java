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
import aritzh.waywia.core.GameLogger;
import aritzh.waywia.entity.Entity;
import aritzh.waywia.entity.player.Player;
import aritzh.waywia.util.Matrix;
import aritzh.waywia.util.ParametrizedFunction;
import com.google.common.base.CaseFormat;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import org.newdawn.slick.Graphics;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class World implements BDSStorable {

	private final File root;

	private Multimap<String, Entity> entities;
	private HashMap<String, Player> players;
	private Matrix<Block> blocks;

	private BDSCompound customData = new BDSCompound("CustomData");

	private final String worldName;

	private World(String name, File root, BDSCompound customData, Multimap<String, Entity> entities, HashMap<String, Player> players, Matrix<Block> blocks) {
		this.worldName = name;
		this.root = root;
		this.customData = customData;
		this.entities = entities;
		this.players = players;
		this.blocks = blocks;
	}

	public static World newWorld(String name, File universeFolder) throws IOException {
		String folderName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, name);
		File root = new File(universeFolder, folderName);

		if (!root.exists() && !root.mkdirs())
			throw new IOException("Could not create folder for new world at: " + root.getAbsolutePath());

		BDSCompound customData = new BDSCompound("CustomData");

		Multimap<String, Entity> entities = ArrayListMultimap.create();

		HashMap<String, Player> player = Maps.newHashMap();

		Matrix<Block> blocks = new Matrix<Block>(50, 38, new BackgroundBlock());

		return new World(name, root, customData, entities, player, blocks);
	}

	public static World loadWorld(File root) {
		BDSCompound worldComp = World.getCompoundFromFolder(root);
		if (worldComp == null) return null;

		try {
			String name = worldComp.getString("Name", 0).getData();
			BDSCompound data = worldComp.getComp("CustomData", 0);

			Matrix<Block> blocks = World.readBlocks(worldComp.getComp("Blocks", 0));

			Multimap<String, Entity> entities = World.readEntities(worldComp.getComp("Entities", 0));

			HashMap<String, Player> players = World.readPlayers(worldComp.getComp("Players", 0));

			return new World(name, root, data, entities, players, blocks);
		} catch (NullPointerException ignored) {
		}

		return null;
	}

	private static Multimap<String, Entity> readEntities(BDSCompound entitiesComp) {
		Multimap<String, Entity> entities = ArrayListMultimap.create();
		for (BDSCompound comp : entitiesComp.getAllCompounds()) {
			if (!comp.getName().equals("Entity")) continue;
			try {
				Entity e = Entity.fromBDS(comp);

				if (e != null) entities.put(e.getName(), e);
			} catch (Exception ignored) {
			}
		}
		return entities;
	}

	private static HashMap<String, Player> readPlayers(BDSCompound playersComp) {
		HashMap<String, Player> players = Maps.newHashMap();
		for (BDSCompound comp : playersComp.getAllCompounds()) {
			if (!comp.getName().equals("Player")) continue;
			try {
				Player p = Player.fromBDS(comp);

				if (p != null) players.put(p.getUsername(), p);
			} catch (Exception ignored) {
			}
		}
		return players;
	}

	private static Matrix<Block> readBlocks(BDSCompound blockComp) {
		Matrix<Block> blocks = new Matrix<Block>(100, 100, new BackgroundBlock());
		for (BDSCompound comp : blockComp.getAllCompounds()) {
			if (!comp.getName().equals("Block")) continue;

			int x = comp.getInt("x", 0).getData();
			int y = comp.getInt("y", 0).getData();

			Block b = Block.fromBDS(comp);
			if (b != null) blocks.set(b, x, y);
		}
		return blocks;
	}

	private static BDSCompound getCompoundFromFolder(File folder) {
		File f;
		if (folder.exists() && (f = new File(folder, "world.dat")).exists()) {
			try {
				return new BDSCompound(f);
			} catch (IOException ignored) {
			}
		}
		return null;
	}

	public void spanwEntity(Entity e) {
		this.entities.put(e.getName(), e);
	}

	public void removeEntity(Entity e) {
		this.entities.remove(e.getName(), e);
	}

	public void update(int delta) {
		this.blocks.runForEach(World.blockUpdate, delta);

		for (Entity e : this.entities.values()) {
			e.update(delta);
		}

		for (Player p : this.players.values()) {
			p.update(delta);
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
		blocks.addAll(this.blocks.runForEach(blockSave).toFlatArrayList());
		ret.add(blocks);
		return ret;
	}

	public void render(Graphics g) {
		this.blocks.runForEach(World.blockRender, g);

		for (Entity e : entities.values()) {
			e.render(g);
		}

		for (Player p : this.players.values()) {
			p.render(g);
		}
	}

	public static Set<World> listWorldsInFolder(File root) {
		Set<World> ret = new HashSet<>();
		for (File folder : root.listFiles(Universe.onlyFolders)) {
			World w = World.loadWorld(folder);
			if (w == null) {
				try {
					GameLogger.warning("Folder " + folder.getCanonicalPath() + " is not a valid world folder");
				} catch (IOException e) {
					GameLogger.warning("Folder " + folder.getAbsolutePath() + " is not a valid world folder");
				}
				continue;
			}
			ret.add(w);
		}
		return ret;
	}

	public String getName() {
		return worldName;
	}

	private static final ParametrizedFunction<Block, Object> blockUpdate = new ParametrizedFunction<Block, Object>() {
		@Override
		public Object apply(Block input, Object... args) {
			input.update((int) args[0], (int) args[1], (int) args[2]);
			return null;
		}
	};

	private static final ParametrizedFunction<Block, BDSCompound> blockSave = new ParametrizedFunction<Block, BDSCompound>() {
		@Override
		public BDSCompound apply(Block input, Object... args) {
			BDSCompound ret = input.toBDS();
			ret.add(new BDSInt((int) args[0], "x"));
			ret.add(new BDSInt((int) args[1], "y"));
			return ret;
		}
	};

	private static final ParametrizedFunction<Block, Object> blockRender = new ParametrizedFunction<Block, Object>() {
		@Override
		public Object apply(Block input, Object... args) {
			input.render((int) args[0], (int) args[1], (Graphics) args[2]);
			return null;
		}
	};

	public void save() {
		this.toBDS().writeToFile(new File(this.root, "world.dat"));
	}

	public void clicked(int x, int y) {
		x /= 16;
		y /= 16;
		this.blocks.get(x, y).clicked(x, y);
	}
}
