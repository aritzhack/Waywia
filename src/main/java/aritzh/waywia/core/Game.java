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

package aritzh.waywia.core;

import aritzh.waywia.gui.GUI;
import aritzh.waywia.gui.MainMenuGUI;
import aritzh.waywia.i18n.I18N;
import aritzh.waywia.input.Keyboard;
import aritzh.waywia.input.Mouse;
import aritzh.waywia.universe.Universe;
import com.google.common.eventbus.EventBus;
import org.newdawn.slick.*;
import org.newdawn.slick.util.FileSystemLocation;
import org.newdawn.slick.util.ResourceLoader;

import java.io.File;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Game extends BasicGame {

	public static Game instance = null;

	public final File baseDir;
	private final EventBus BUS;
	public GUI currGui;
	private GameContainer gc = null;
	private Universe universe;

	public Game() {
		super("Hello World");
		this.baseDir = new File(System.getProperty("user.dir"));
		this.BUS = new EventBus("MainBus");
		instance = this;
		GameLogger.initLogger();
	}

	public void setGC(GameContainer gc) {
		this.gc = gc;
	}

	@Override
	public boolean closeRequested() {
		return super.closeRequested();
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		this.universe = new Universe(this, new File(this.baseDir.getPath(), "saves"));
		new Keyboard(this);
		new Mouse(this);
		I18N.init(baseDir);
		ResourceLoader.addResourceLocation(new FileSystemLocation(new File("./res")));
		this.currGui = new MainMenuGUI(this);
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		((AppGameContainer) gc).setTitle(String.valueOf(gc.getFPS()));
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.drawString("Hello World!", 100, 100);
		this.universe.render(g);
	}

	public GameContainer getGc() {
		return gc;
	}

	public void registerEventHandler(Object o) {
		this.BUS.register(o);
	}

	public void reload() {
		this.universe = new Universe(this, new File(this.baseDir.getPath(), "saves"));
	}
}
