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

package aritzh.waywia.bds;

import aritzh.waywia.util.Util;

/**
 * Binary Data Storage
 * Used to store different data types in byte arrays, and the data from them
 *
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public abstract class BDS { // Binary Data Storage

	/**
	 * Returns the data representing this BDS
	 *
	 * @return An array of bytes that represent this BDS
	 */
	public abstract byte[] getBytes();

	/**
	 * Returns the name of this BDS
	 *
	 * @return the name of this BDS
	 */
	public abstract String getName();

	/**
	 * Returns the type of this BDS, useful to check if it's one type or another
	 *
	 * @return The type of this BDS
	 * @see BDSType
	 */
	public abstract BDSType getType();

	/**
	 * Returns the data corresponding to this BDS.
	 */
	public abstract Object getData();

	@Override
	public final String toString() {
		return this.toString(0);
	}

	public String toString(int level) {
		return Util.repeatString("    ", level) + this.getType() + ":" + this.getName() + ":" + this.getData();
	}
}
