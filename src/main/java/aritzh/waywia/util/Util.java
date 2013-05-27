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

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

import java.io.File;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Util {

	public static boolean delete(File f) {
		if (f == null || !f.exists()) return true;
		if (f.isDirectory()) {
			File[] subFiles = f.listFiles();
			if (subFiles != null) {
				for (File sf : subFiles) {
					Util.delete(sf);
				}
			}
		}
		return f.delete();
	}

	public static String repeatString(String s, int n) {
		return new String(new char[n]).replace("\0", s);
	}

	public static Shape move(Shape source, float x, float y) {
		return source.transform(Transform.createTranslateTransform(x, y));
	}

	public static File getAppDir(String name) {
		String s1 = System.getProperty("user.home", ".");
		File file1;

		switch (getOs()) {
			case UNIX:
				file1 = new File(s1, name + '/');
				break;
			case WINDOWS:
				String s2 = System.getenv("APPDATA");

				if (s2 != null) {
					file1 = new File(s2, name + '/');
				} else {
					file1 = new File(s1, name + '/');
				}

				break;
			case MACOS:
				file1 = new File(s1, "Library/Application Support/" + name);
				break;
			default:
				file1 = new File(s1, name + '/');
		}

		if (!file1.exists() && !file1.mkdirs()) {
			throw new RuntimeException("The working directory could not be created: " + file1);
		} else {
			return file1;
		}
	}

	private static EnumOS getOs() {
		String s = System.getProperty("os.name").toLowerCase();
		return s.contains("win") ? EnumOS.WINDOWS :
				(s.contains("mac") ? EnumOS.MACOS :
						(s.contains("solaris") || s.contains("sunos") || s.contains("linux") || s.contains("unix") ? EnumOS.UNIX :
								EnumOS.UNKNOWN));
	}

	private static enum EnumOS {

		WINDOWS(0),
		UNIX(1),
		MACOS(2),
		UNKNOWN(3);

		private int id;

		EnumOS(int id) {

			this.id = id;
		}

		public static EnumOS getByID(int id) {
			for (EnumOS os : EnumOS.values()) {
				if (os.id == id) return os;
			}
			return UNKNOWN;
		}

		public int getId() {
			return id;
		}
	}
}
