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

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class UtilTest {

	@Test
	public void deleteTest() {
		try {
			File folder1 = new File("folder1");
			boolean created = true;
			created &= folder1.mkdirs();

			File folder11 = new File(folder1, "folder11");
			created &= folder11.mkdirs();

			File file1 = new File(folder1, "file1.txt");
			created &= file1.createNewFile();

			File file11 = new File(folder11, "file11.txt");
			created &= file11.createNewFile();

			assert folder1.exists() : "Folder1 does not exist";
			assert folder11.exists() : "Folder11 does not exist";
			assert file1.exists() : "File1 does not exist";
			assert file11.exists() : "File11 does not exist";

			Util.delete(folder1);

			assert !folder1.exists() : "Folder1 exists";
			assert !folder11.exists() : "Folder11 exists";
			assert !file1.exists() : "File1 exists";
			assert !file11.exists() : "File11 exists";

			// Leave this the last, since it doesn't describe which one hasn't been described
			assert created : "One of the files or folders could not be created";

		} catch (IOException e) {
			e.printStackTrace();
			assert false : "IOException thrown";
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

		assert got.equals(expected) : "Strings " + expected + " and " + got + " are not equal!";
	}

	@Test
	public void getOsTest() {

		File f = new File(".");

		try {
			switch (Util.getOs()) {
				case WINDOWS:
					assert !f.getCanonicalPath().startsWith("/") : "Windows canonnical paths cannot start with /";
					return;
				case UNIX:
				case MACOS:
					assert f.getCanonicalPath().startsWith("/") : "Non-windows canonical paths must start with /";
				case UNKNOWN:
					assert false : "Unknown OS";
			}
		} catch (IOException e) {
			e.printStackTrace();
			assert false : "IOException was thrown";
		}
	}
}
