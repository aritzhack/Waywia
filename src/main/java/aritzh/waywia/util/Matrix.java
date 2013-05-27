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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Matrix<E> implements Iterable<ArrayList<E>> {


	E defaultElement = null;
	List<ArrayList<E>> columns;
	ParametrizedFunction function;

	/**
	 * Creates a new matrix, with the default size of 5x5
	 */
	public Matrix() {
		this(5, 5);
	}

	/**
	 * Creates a new empty matrix, with the specified starting size
	 *
	 * @param startWidth  The amount of columns
	 * @param startHeight The amount of rows
	 */
	public <D extends E> Matrix(int startWidth, int startHeight, D defaultElement) {
		this.defaultElement = defaultElement;
		this.columns = new ArrayList<>();
		for (int x = 0; x < startWidth; x++) {
			this.columns.add(new ArrayList<E>(startHeight));
			for (int y = 0; y < startHeight; y++) {
				this.columns.get(x).add(this.defaultElement);
			}
		}
	}

	public Matrix(int startWidth, int startHeight) {
		this(startWidth, startHeight, null);
	}

	/**
	 * Sets the default element to be added to the list.
	 * If this is never set, null will be added
	 *
	 * @param defaultElement The element to be used by default
	 * @return {@code this}
	 */
	public Matrix<E> setDefaultElement(E defaultElement) {
		this.defaultElement = defaultElement;
		return this;
	}

	/**
	 * Returns the element in column {@code x} and y {@code y}
	 *
	 * @param x The column of the element
	 * @param y The row of the element
	 * @return the element at (x, y)
	 */
	public E get(int x, int y) {
		if (columns.size() < x) {
			columns.set(x, new ArrayList<E>());
		}
		ArrayList<E> columnList = columns.get(x);
		if (columnList.size() < y) {
			columnList.set(y, this.defaultElement);
		}
		return columnList.get(y);
	}

	/**
	 * Sets the element {@code element} to the {@code (x, y)} position of the matrix
	 *
	 * @param element The element to be set
	 * @param x       The row
	 * @param y       The column
	 * @return {@code this}
	 */
	public Matrix<E> set(E element, int x, int y) {
		if (this.columns.size() < x) {
			columns.set(x, new ArrayList<E>());
		}
		this.columns.get(x).set(y, element);
		return this;
	}

	/**
	 * Runs a {@link ParametrizedFunction} for each element of the matrix, using these arguments:
	 * <ol>
	 * <li>X Coordinate in the matrix</li>
	 * <li>Y Coordinate in the matrix</li>
	 * <li>{@code args}, at the end</li>
	 * </ol>
	 *
	 * @param function The ParametrizedFunction to run for each element
	 * @param args     The arguments to add at the end of the function's params
	 * @param <R>      The return value for the function. Must be the same as the return value of {@code function}
	 * @return A list containing all the return values
	 */
	public <R> List<R> runForEach(ParametrizedFunction<E, R> function, Object... args) {
		List<R> ret = new ArrayList<>();
		for (int x = 0; x < this.columns.size(); x++) {
			ArrayList<E> col = this.columns.get(x);
			for (int y = 0; y < col.size(); y++) {
				if (col.get(y) == null) continue;
				R obj = function.apply(col.get(y), x, y, args);
				if (obj != null) ret.add(obj);
			}
		}
		return ret;
	}


	/**
	 * Returns an iterator for this matrix.
	 * It returns a iterator of {@code ArrayList<E>},
	 * each ArrayList being a column
	 *
	 * @return an Iterator of {@code ArrayList<E>}s
	 */
	@Override
	public Iterator<ArrayList<E>> iterator() {
		return new MatrixIterator(this);
	}

	private class MatrixIterator implements Iterator<ArrayList<E>> {

		int current = 0;
		private Matrix<E> matrix;

		public MatrixIterator(Matrix<E> matrix) {
			this.matrix = matrix;
		}

		@Override
		public boolean hasNext() {
			return current >= this.matrix.columns.size();
		}

		@Override
		public ArrayList<E> next() {
			if (current == this.matrix.columns.size())
				throw new NoSuchElementException();
			current++;
			return this.matrix.columns.get(current - 1);
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
