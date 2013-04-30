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

import aritzh.waywia.core.Game;
import org.newdawn.slick.Graphics;

import java.io.File;
import java.io.FileFilter;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Universe {

	private static FileFilter onlyFolders = new FileFilter() {
		@Override
		public boolean accept(File file) {
			return file.isDirectory();
		}
	};

	private final Game game;
	private Set<World> worlds = new HashSet<>();
	private World currentWorld;

	public Universe(Game game, File savesFolder) {
		this.game = game;
		savesFolder.mkdir();
		this.currentWorld = new World(this, null);
		this.worlds.add(this.currentWorld);
		for (File folder : savesFolder.listFiles(onlyFolders)) {
			this.worlds.add(new World(this, folder));
		}
	}

	public Game getGame() {
		return game;
	}

	public void render(Graphics g) {
		if (this.currentWorld != null) this.currentWorld.render(g);
	}
}
