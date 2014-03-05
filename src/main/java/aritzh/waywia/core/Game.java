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

import io.github.aritzhack.util.eventBus.DeadEvent;
import io.github.aritzhack.util.eventBus.EventBus;
import io.github.aritzhack.util.eventBus.Subscribe;
import io.github.aritzhack.util.extensions.ExtensibleApp;
import io.github.aritzhack.util.extensions.Extensions;
import io.github.aritzhack.util.extensions.events.ExtensionEvent;
import io.github.aritzhack.util.logging.Logger;
import io.github.aritzhack.util.logging.LoggerBuilder;
import aritzh.waywia.core.states.ErrorState;
import aritzh.waywia.core.states.InGameState;
import aritzh.waywia.core.states.LoadingState;
import aritzh.waywia.core.states.MenuState;
import aritzh.waywia.core.states.WaywiaState;
import aritzh.waywia.gui.components.GUI;
import aritzh.waywia.lib.GameLib;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.LogSystem;
import org.reflections.Reflections;

import java.io.File;
import java.io.IOException;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Game extends StateBasedGame implements ExtensibleApp {

	public final File root;
	public final File savesDir;
    public final File modsDir;
	private final EventBus BUS = new EventBus("MainBus");
	private AppGameContainer gc = null;
	public WaywiaState menuState, inGameState, loadingState;
	public ErrorState errorState;
	private boolean initMods = false;
	public final Extensions mods;
	public final Reflections reflections = new Reflections(ClassLoader.getSystemClassLoader());

	public static Logger logger = null;

	public Game(File root, String username, String password) throws IOException {
		super(GameLib.FULL_NAME);
		Game.logger = new LoggerBuilder("Game").toConsole().setFileAmount(5).setLogRoot(new File(root, "logs")).setResBundle("locales.lang").build();

		Game.shutSlick2D();

		Login.logIn(username, password);

		this.mods = new Extensions(this, Game.logger);

		if (root == null) root = new File(System.getProperty("user.dir"));

		this.root = root;
		if (!this.root.exists() && !this.root.mkdirs())
			throw new IOException("Couldn't make folder for the game");

		this.savesDir = new File(this.root, "saves");
		if (!this.savesDir.exists() && !savesDir.mkdirs()) throw new IOException("Couldn't make the saves' folder");

        this.modsDir = new File(this.root, "mods");
        if (!this.modsDir.exists() && !modsDir.mkdirs()) throw new IOException("Couldn't make the mods' folder");

		this.registerEventHandler(this);

		Config.init(root);
		Game.logger.log("test1");
	}

	private static void shutSlick2D() {
		Log.setLogSystem(new LogSystem() {
			@Override
			public void error(String message, Throwable e) {
			}

			@Override
			public void error(Throwable e) {
			}

			@Override
			public void error(String message) {
			}

			@Override
			public void warn(String message) {
			}

			@Override
			public void warn(String message, Throwable e) {
			}

			@Override
			public void info(String message) {
			}

			@Override
			public void debug(String message) {
			}
		});
	}

	@Override
	public boolean closeRequested() {
		this.getCurrentState().closing();
        try {
            Config.GAME.save();
        } catch (IOException e) {
            Game.logger.exception("Error saving config", e);
        }
        if (Config.GAME.getBoolean("Mods", "loadMods")) this.BUS.post(new ExtensionEvent.ExtensionUnloadEvent(this));
		return super.closeRequested();
	}

	public void exit() {
		this.closeRequested();
		this.getGc().exit();
		System.exit(0);
	}

	@Override
	public WaywiaState getCurrentState() {
		return (WaywiaState) super.getCurrentState();
	}

	@Override
	public void enterState(int id) {
		enterState(id, new FadeOutTransition(), new FadeInTransition());
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		this.gc = (AppGameContainer) container;

		//container.getInput().addKeyListener(new Keyboard(this));
		//container.getInput().addMouseListener(new Mouse(this));

		container.getInput().poll(container.getWidth(), container.getHeight());

		this.addState(this.loadingState = new LoadingState(this));
		this.addState(this.menuState = new MenuState(this));
		this.addState(this.inGameState = new InGameState(this));
		this.addState(this.errorState = new ErrorState(this));
	}

	@Subscribe
	public void catchDeadEvents(DeadEvent e) {
		Game.logger.debug("Caught dead event: " + e.getEvent());
	}

    @Override
	protected void preUpdateState(GameContainer container, int delta) throws SlickException {
		gc.setTitle(GameLib.FULL_NAME + " - " + gc.getFPS() + " FPS - " + this.getCurrentState().toString() + " - " + Login.getUsername() + " -  Running in " + (Login.isLoggedIn() ? "online" : "offline") + " mode");
	}

	public AppGameContainer getGc() {
		return gc;
	}

	public void registerEventHandler(Object o) {
		this.BUS.register(o);
	}

	public GUI getCurrentGui() {
		if (this.getCurrentState() != null) {
			return this.getCurrentState().getCurrentGui();
		}
		// Should not happen, but heh...
		return null;
	}

	public boolean isGuiOpen() {
		return this.getCurrentGui() != null;
	}

    @Override
    public Reflections getReflections() {
        return this.reflections;
    }

    @Override
    public EventBus getExtensionsEventBus() {
        return this.BUS;
    }
}
