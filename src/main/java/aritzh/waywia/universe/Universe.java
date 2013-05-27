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
import aritzh.waywia.util.Util;
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

	public static FileFilter onlyFolders = new FileFilter() {
		@Override
		public boolean accept(File file) {
			return file.isDirectory();
		}
	};

	private File root;

	private Set<World> worlds = new HashSet<>();
	private World currentWorld;

	private String name;
	private BDSCompound customData;

	public Universe(String name, File root) throws IOException {
		this.name = name;
		this.root = root;
		if (this.root.exists()) { // If it already exists, load it
			GameLogger.debug("Folder existed, trying to load");
			File f = new File(this.root, "universe.dat");
			if (f.exists()) {
				GameLogger.debug("File exists, trying to load");
				try {
					BDSCompound data = new BDSCompound(f);
					GameLogger.debug("Universe data loaded, parsing");
					this.parseUniverse(data);
					GameLogger.debug("Universe loaded, done!");
					return;
				} catch (IOException ignored) {
					// Ignore it, because if we got here means we haven't returned, so let it continue
				}
			}
		}
		GameLogger.debug("Universe folder not detected, or invalid, creating new universe at " + this.root.getAbsolutePath());
		if (!Util.delete(this.root))
			throw new IOException("Couldn't delete the folder " + this.root.getAbsolutePath() + " in order to make a new Universe");
		if (!this.root.mkdirs()) throw new IOException("Couldn't make root folder for universe " + this);
		this.worlds.add(new World("Base", this, new File(root, "base")));
		this.toBDS().writeToFile(new File(this.root, "universe.dat"));
	}

	public Universe(BDSCompound compound, File root) throws IOException {
		this.root = root;
		if (!(this.root.exists() || this.root.mkdirs()))
			throw new IOException("Couldn't make root folder for universe " + this);
		this.parseUniverse(compound);
	}

	private void parseUniverse(BDSCompound compound) {
		BDSString nameBDS = compound.getString("Name", 0);
		this.name = (nameBDS != null ? nameBDS.getData() : "Universe " + root.getName());
		this.customData = compound.getComp("CustomData", 0);
		this.worlds.addAll(World.getWorldList(this, root));
		String currWName = null;
		try {
			currWName = compound.getString("CurrWorld", 0).getData();
			if (currWName != null) GameLogger.debug("Current world name found: " + currWName);
		} catch (NullPointerException ignored) {
		}
		for (World w : this.worlds) {
			if (w != null && (currWName == null || w.getName().equals(currWName))) {
				this.currentWorld = w;
				break;
			}
		}
		GameLogger.debug("Universe parsed");
	}

	public void render(Graphics g) {
		if (this.currentWorld != null) this.currentWorld.render(g);
	}

	public static List<Universe> getUniverseList(File path) {
		List<Universe> ret = new ArrayList<>();

		for (File folder : path.listFiles(onlyFolders)) {

			File f = new File(folder, "universe.dat");
			if (!f.exists()) continue;
			BDSCompound data;
			try {
				data = new BDSCompound(f);
			} catch (Exception ignored) {
				continue;
			}
			try {
				ret.add(new Universe(data, folder));
			} catch (IOException ignored) {
				continue;
			}
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
}
