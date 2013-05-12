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
	 * Creates a new empty matrix
	 */
	public Matrix() {
		this.columns = new ArrayList<>();
	}

	/**
	 * Creates a new empty matrix, with the specified starting size
	 *
	 * @param startWidth  The amount of columns
	 * @param startHeight The amount of rows
	 */
	public Matrix(int startWidth, int startHeight) {
		for (int i = 0; i < startWidth; i++) {
			this.columns.set(i, new ArrayList<E>(startHeight));
		}
	}

	public Matrix<E> setDefaultElement(E defaultElement) {
		this.defaultElement = defaultElement;
		return this;
	}

	public E get(int column, int row) {
		if (columns.size() < column) {
			columns.set(column, new ArrayList<E>());
		}
		ArrayList<E> columnList = columns.get(column);
		if (columnList.size() < row) {
			columnList.set(row, this.defaultElement);
		}
		return columnList.get(row);
	}

	public void set(E element, int x, int y) {
		if (this.columns.size() < x) {
			columns.set(x, new ArrayList<E>());
		}
		this.columns.get(x).set(y, element);
	}

	public <R> List<R> runForEach(ParametrizedFunction<E, R> function, Object... args) {
		List<R> ret = new ArrayList<>();
		for (int x = 0; x < this.columns.size(); x++) {
			ArrayList<E> col = this.columns.get(x);
			for (int y = 0; y < col.size(); y++) {
				R obj = function.apply(col.get(y), args, x, y);
				if (obj != null) ret.add(obj);
			}
		}
		return ret;
	}

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
