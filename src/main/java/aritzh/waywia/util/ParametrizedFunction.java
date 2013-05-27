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

package aritzh.waywia.util;

/**
 * A function that can be applied to an {@code input} object of type {@code I}, and
 * returns an object of type {@code R}. Accepts an array of objects as parameters,
 * so that they can be used in the implementation
 *
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public interface ParametrizedFunction<I, R> {

	/**
	 * Apply this ParametrizedFunction to {@code input} with {@code args} as parameters
	 *
	 * @param input The object to which this ParametrizedFunction will be applied
	 * @param args  The extra arguments needed to run the function
	 * @return The return value of applying {@code this} to {@code input}
	 */
	public R apply(I input, Object... args);
}
