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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class MatrixTest {

	@Test
	public void getElementTest() {
		Matrix<Integer> mat = MatrixTest.newMultiplicationTable(10, 10);
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				assertEquals("For coords (" + x + ", " + y + ")", x * y, mat.get(x, y).intValue());
			}
		}
	}

	@Test
	public void paramFuncTest() {
		Matrix<Integer> mat = MatrixTest.newMultiplicationTable(10, 10);

		ParametrizedFunction<Integer, Object> pFunc = new ParametrizedFunction<Integer, Object>() {
			@Override
			public Object apply(Integer input, Object... args) {
				int x = (int) args[0];
				int y = (int) args[1];
				assertEquals("For coords (" + x + ", " + y + ")", x * y, input.intValue());
				return null;
			}
		};

		mat.runForEach(pFunc);
	}

	public static Matrix<Integer> newMultiplicationTable(int x, int y) {
		Matrix<Integer> ret = new Matrix<>(x, y);
		for (int ix = 0; ix < x; ix++) {
			for (int iy = 0; iy < y; iy++) {
				ret.set(ix * iy, ix, iy);
			}
		}
		return ret;
	}
}
