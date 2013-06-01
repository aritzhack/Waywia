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

import java.io.File;
import java.io.IOException;
import java.util.Random;

import static junit.framework.Assert.*;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class UtilTest {

	@Test
	public void deleteTest() {
		try {
			File folder1 = new File("folder1");

			boolean created = folder1.mkdirs();

			File folder11 = new File(folder1, "folder11");
			created &= folder11.mkdirs();

			File file1 = new File(folder1, "file1.txt");
			created &= file1.createNewFile();

			File file11 = new File(folder11, "file11.txt");
			created &= file11.createNewFile();

			assertTrue(folder1.exists());
			assertTrue(folder11.exists());
			assertTrue(file1.exists());
			assertTrue(file11.exists());

			boolean deleted = Util.delete(folder1);

			assertTrue(deleted);

			assertFalse(folder1.exists());
			assertFalse(folder11.exists());
			assertFalse(file1.exists());
			assertFalse(file11.exists());

		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void repeatStringTest() {
		Random r = new Random();
		String start = String.valueOf(r.nextInt());
		int times = r.nextInt(10);

		StringBuilder b = new StringBuilder(start.length() * times);

		for (int i = 0; i < times; i++) {
			b.append(start);
		}

		String got = Util.repeatString(start, times);
		String expected = b.toString();

		assertEquals(expected, got);
	}

	@Test
	public void getOsTest() {

		File f = new File(".");
		switch (Util.getOs()) {
			case WINDOWS:
				try {
					assertFalse(f.getCanonicalPath().startsWith("/"));
				} catch (IOException e) {
					assertFalse(f.getAbsolutePath().startsWith("/"));
				}
				return;
			case UNIX:
			case MACOS:
				try {
					assertTrue(f.getCanonicalPath().startsWith("/"));
				} catch (IOException e) {
					assertTrue(f.getAbsolutePath().startsWith("/"));
				}
				return;
			case UNKNOWN:
				fail("Unknown OS: " + System.getProperty("os.name").toLowerCase());
		}
	}
}
