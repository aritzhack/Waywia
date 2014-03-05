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
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import java.io.PrintWriter;
import java.io.StringWriter;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ErrorState extends WaywiaState {

	private String when;
	private Throwable throwable;
	private boolean logged = false;
	String stackTrace = "";

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
		checkArgument(t != null, "Throwable must not be null!");
		checkArgument(when != null, "When must not be null!");
		this.when = when;
		this.throwable = t;
		this.logged = false;
		StringWriter wr = new StringWriter();
		PrintWriter pr = new PrintWriter(wr);
		t.printStackTrace(pr);
		wr.flush();
		stackTrace = wr.toString();
		return this.getID();
	}

	@Override
	public void init() {
	}

	@Override
	public void render(Graphics g) {
		if (this.when == null || this.throwable == null)
			throw new IllegalStateException("Error state was not correctly initialized using ErrorState.setError()");
		g.setBackground(Color.black);
		g.clear();
		g.setColor(Color.white);
		g.drawString(stackTrace, 0, 0);
	}

	@Override
	public void update(int delta) {
		if (this.when == null || this.throwable == null)
			throw new IllegalStateException("Error state was not correctly initialized using ErrorState.setError()");

		if (!logged) {
			Game.logger.e(throwable.getLocalizedMessage(), throwable);
			logged = true;
		}
	}

	@Override
	public void keyPressed(int key, char c) {
		super.keyPressed(key, c);
		if (key == Input.KEY_ESCAPE) this.game.exit();
	}
}
