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

import aritzh.waywia.i18n.I18N;
import aritzh.waywia.input.GameInput;
import aritzh.waywia.universe.Universe;
import com.google.common.eventbus.EventBus;
import org.newdawn.slick.*;

import java.io.File;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Game extends BasicGame {

	public final File baseDir;
	private final GameInput input;
	private final EventBus BUS;
	private GameContainer gc = null;
	private Universe universe;

	public Game() {
		super("Hello World");
		this.baseDir = new File(System.getProperty("user.dir"));
		this.BUS = new EventBus("MainBus");

		GameLogger.initLogger();
		this.input = new GameInput(this);
		I18N.init(baseDir);
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
		this.universe = new Universe(this);
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		((AppGameContainer) gc).setTitle(String.valueOf(gc.getFPS()));
		this.input.update(i);
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
		this.universe = new Universe(this);
	}
}
