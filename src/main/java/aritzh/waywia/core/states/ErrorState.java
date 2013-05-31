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

package aritzh.waywia.core.states;

import aritzh.waywia.core.Game;
import org.newdawn.slick.Graphics;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ErrorState extends WaywiaState {

	private String when;
	private Throwable throwable;

	public ErrorState(Game game) {
		super(game, "ErrorState");
	}

	@Override
	public int getID() {
		return 3;
	}

	/**
	 * Sets the throwable and moment, so that the error can be printed to the user
	 *
	 * @param when A string identifying when the error occurred
	 * @param t    The throwable that describes the error
	 * @return The StateID of ErrorState, so that it can be used like Game.enterState(ErrorState.setError(...))
	 */
	public int setError(String when, Throwable t) {
		if (t == null || when == null) throw new IllegalArgumentException("When and throwable must not be null!");
		this.when = when;
		throwable = t;
		return this.getID();
	}

	@Override
	public void init() {
	}

	@Override
	public void render(Graphics g) {
		if (this.when == null || this.throwable == null)
			throw new IllegalStateException("Error state was not correctly initialized");
	}

	@Override
	public void update(int delta) {
		if (this.when == null || this.throwable == null)
			throw new IllegalStateException("Error state was not correctly initialized using ErrorState.setError()");
	}
}
