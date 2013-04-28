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
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Universe {

	private final Game game;
	private List<World> worlds = new ArrayList<World>();
	private World currentWorld;

	public Universe(Game game) {
		this.game = game;
		File savesFolder = new File(game.baseDir.getPath(), "saves");
		savesFolder.mkdir();
		FileFilter onlyFolders = new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isDirectory();
			}
		};
		for(File folder : savesFolder.listFiles(onlyFolders)){
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
