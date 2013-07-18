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

package aritzh.waywia.event;

import aritzh.util.ReflectionUtil;

/**
 * Root of all events. Listen to this in order to handle all events
 *
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public abstract class WaywiaEvent {

	protected final boolean isCancelable;
	protected Result result;

	public WaywiaEvent() {
		this.isCancelable = ReflectionUtil.classHasAnnotation(this.getClass(), Cancelable.class);
	}

	public WaywiaEvent(boolean isCancelable) {
		this.isCancelable = isCancelable;
	}

	public boolean isCancelable() {
		return isCancelable;
	}

	public Result getResult() {
		return this.result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public void cancel() {
		this.result = Result.CANCEL;
	}
}
