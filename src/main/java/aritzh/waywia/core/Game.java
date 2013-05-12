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

import aritzh.waywia.core.states.InGameState;
import aritzh.waywia.core.states.MenuState;
import aritzh.waywia.core.states.WaywiaState;
import aritzh.waywia.gui.components.GUI;
import aritzh.waywia.i18n.I18N;
import aritzh.waywia.input.Keyboard;
import aritzh.waywia.input.Mouse;
import aritzh.waywia.lib.GameLib;
import com.google.common.eventbus.EventBus;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.io.File;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Game extends StateBasedGame {

	public final File baseDir;
	public final File savesDir;
	private final EventBus BUS;
	private GameContainer gc = null;

	public Game() {
		super(GameLib.FULL_NAME);
		GameLogger.initLogger();

		this.baseDir = new File(System.getProperty("user.dir"));
		if (!baseDir.exists() && !baseDir.mkdirs()) throw new RuntimeException("Couldn't make folder for the game");
		this.savesDir = new File(baseDir, "saves");
		if (!this.savesDir.exists() && !savesDir.mkdirs())
			throw new RuntimeException("Couldn't make the saves' folder");

		this.BUS = new EventBus("MainBus");
		I18N.init(baseDir);
	}

	@Override
	public boolean closeRequested() {
		return super.closeRequested();
	}

	@Override
	public void enterState(int id) {
		super.enterState(id);
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		this.gc = container;
		container.getInput().addKeyListener(new Keyboard(this));
		container.getInput().addMouseListener(new Mouse(this));
		container.getInput().poll(container.getWidth(), container.getHeight());
		this.addState(new MenuState(this));
		this.addState(new InGameState(this));
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
