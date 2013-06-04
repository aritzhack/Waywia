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

import aritzh.waywia.core.states.*;
import aritzh.waywia.gui.components.GUI;
import aritzh.waywia.i18n.I18N;
import aritzh.waywia.input.Keyboard;
import aritzh.waywia.input.Mouse;
import aritzh.waywia.lib.GameLib;
import aritzh.waywia.mod.Mod;
import aritzh.waywia.util.ReflectionUtil;
import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.reflections.Reflections;

import java.io.File;
import java.io.IOException;

import static aritzh.waywia.mod.events.ModEvent.ModUnloadEvent;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Game extends StateBasedGame {

	public final File baseDir;
	public final File savesDir;
	private final EventBus BUS = new EventBus("MainBus");
	private GameContainer gc = null;
	public WaywiaState menuState, inGameState, loadingState;
	public ErrorState errorState;
	private final boolean loggedIn;
	private boolean initMods = false;

	public Game(File root, boolean loggedIn) throws IOException {
		super(GameLib.FULL_NAME);
		this.loggedIn = loggedIn;
		GameLogger.init(new File(root, "logs"));

		if (!this.loggedIn) GameLogger.warning("Game running in not-logged-in mode!");
		else GameLogger.log("Successfully logged in");

		if (root == null) root = new File(System.getProperty("user.dir"));

		this.baseDir = root;
		if (!this.baseDir.exists() && !this.baseDir.mkdirs())
			throw new IOException("Couldn't make folder for the game");

		this.savesDir = new File(this.baseDir, "saves");
		if (!this.savesDir.exists() && !savesDir.mkdirs()) throw new IOException("Couldn't make the saves' folder");

		this.registerEventHandler(this);

		Config.init();
		I18N.init(this.baseDir);
	}

	@Override
	public boolean closeRequested() {
		((WaywiaState) this.getCurrentState()).closing();
		Config.GAME.save();
		this.BUS.post(new ModUnloadEvent(this));
		Display.destroy();
		return super.closeRequested();
	}

	public void exit() {
		this.closeRequested();
		this.getGc().exit();
		System.exit(0);
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		this.gc = container;

		container.getInput().addKeyListener(new Keyboard(this));
		container.getInput().addMouseListener(new Mouse(this));

		container.getInput().poll(container.getWidth(), container.getHeight());

		this.addState(this.loadingState = new LoadingState(this));
		this.addState(this.menuState = new MenuState(this));
		this.addState(this.inGameState = new InGameState(this));
		this.addState(this.errorState = new ErrorState(this));
	}

	@Subscribe
	public void catchDeadEvents(DeadEvent e) {
		GameLogger.debug("Caught dead event: " + e.getEvent());
	}

	public void loadMods() {
		if (initMods) return;
		initMods = true;
		try {
			File modsFolder = new File(baseDir, "mods");
			ReflectionUtil.addFolderToClasspath(modsFolder);
			Reflections reflections = new Reflections(ClassLoader.getSystemClassLoader());

			for (Class c : reflections.getTypesAnnotatedWith(Mod.class)) {
				Object o = c.newInstance();
				GameLogger.debug("Found mod class: " + c.getName() + "; Instance: " + o);
				this.registerEventHandler(o);
			}
		} catch (InstantiationException | IllegalAccessException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void preUpdateState(GameContainer container, int delta) throws SlickException {
		((AppGameContainer) gc).setTitle(GameLib.FULL_NAME + " - " + gc.getFPS() + " FPS - " + this.getCurrentState().toString());
	}

	public GameContainer getGc() {
		return gc;
	}

	public void registerEventHandler(Object o) {
		this.BUS.register(o);
	}

	public GUI getCurrentGui() {
		if (this.getCurrentState() != null && this.getCurrentState() instanceof WaywiaState) {
			return ((WaywiaState) this.getCurrentState()).getCurrentGui();
		}
		// Should not happen, but heh...
		return null;
	}

	public boolean isGuiOpen() {
		return this.getCurrentGui() != null;
	}
}
