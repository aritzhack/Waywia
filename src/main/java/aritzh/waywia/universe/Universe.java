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
import aritzh.waywia.bds.BDSStorable;
import aritzh.waywia.bds.BDSString;
import aritzh.waywia.core.GameLogger;
import com.google.common.base.CaseFormat;
import org.newdawn.slick.Graphics;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Universe implements BDSStorable {

	private final File root;
	private final Set<World> worlds;
	private final String name;
	private final BDSCompound customData;

	private World currentWorld;

	private Universe(String name, File root, Set<World> worlds, World currentWorld, BDSCompound customData) {
		this.name = name;
		this.root = root;
		this.worlds = worlds;
		this.customData = customData;
		this.currentWorld = currentWorld;
		if (!this.worlds.contains(this.currentWorld)) this.worlds.add(this.currentWorld);
	}

	public static Universe newUniverse(String name, File saveFolder, String defaultWorldName) throws IOException {
		String folderName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, name);
		File root = new File(saveFolder, folderName);
		if (!root.exists() && !root.mkdirs())
			throw new IOException("Could not create folder for new universe at: " + root.getAbsolutePath());

		Set<World> worlds = new HashSet<>();
		String worldName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, defaultWorldName);
		World defaultWorld = World.newWorld(worldName, root);

		BDSCompound comp = new BDSCompound("CustomData");

		Universe u = new Universe(name, root, worlds, defaultWorld, comp);
		u.toBDS().writeToFile(new File(root, "universe.dat"));
		return u;
	}

	public static Universe loadUniverse(File root) {
		BDSCompound uniComp = Universe.getCompoundFromFolder(root);
		if (uniComp == null) return null;
		try {
			String name = uniComp.getString("Name", 0).getData();
			BDSCompound data = uniComp.getComp("CustomData", 0);
			Set<World> worlds = World.listWorldsInFolder(root);
			if (worlds.isEmpty()) return null;
			String currWorldName = uniComp.getString("CurrWorldName", 0).getData();
			World currW = null;
			for (World w : worlds) {
				if (w.getName().equals(currWorldName)) currW = w;
			}
			if (currW == null) currW = worlds.iterator().next();
			Universe u = new Universe(name, root, worlds, currW, data);
			u.save();
			return u;
		} catch (NullPointerException ignored) {
		}
		return null;
	}

	private static BDSCompound getCompoundFromFolder(File folder) {
		File f;
		if (folder.exists() && (f = new File(folder, "universe.dat")).exists()) {
			try {
				return new BDSCompound(f);
			} catch (IOException ignored) {
			}
		}
		return null;
	}

	public void render(Graphics g) {
		if (this.currentWorld != null) this.currentWorld.render(g);
	}

	public static List<Universe> getUniverseList(File savesFolder) {
		List<Universe> ret = new ArrayList<>();

		for (File folder : savesFolder.listFiles(onlyFolders)) {
			Universe u = Universe.loadUniverse(folder);
			if (u == null) {
				GameLogger.warning("Folder " + folder.getAbsolutePath() + " is not a valid universe folder");
				continue;
			}
			ret.add(u);
		}
		return ret;
	}

	@Override
	public BDSCompound toBDS() {
		BDSCompound ret = new BDSCompound("Universe");
		ret.add(new BDSString(this.name, "Name"));
		if (this.currentWorld != null) ret.add(new BDSString(this.currentWorld.getName(), "CurrWorld"));
		if (customData != null) ret.add(this.customData);
		return ret;
	}

	public void update(int delta) {
		if (this.currentWorld != null) this.currentWorld.update(delta);
	}

	public void save() {
		this.toBDS().writeToFile(new File(root, "universe.dat"));
		for (World w : this.worlds) {
			w.save();
		}
	}

	public void clicked(int x, int y) {
		if (this.currentWorld != null) currentWorld.clicked(x, y);
	}

	public static final FileFilter onlyFolders = new FileFilter() {
		@Override
		public boolean accept(File file) {
			return file.isDirectory();
		}
	};
}
