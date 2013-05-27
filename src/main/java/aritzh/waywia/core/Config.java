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

package aritzh.waywia.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Config {
	private static Map<String, String> data = new HashMap<>();

	public static void init() {
		// DOING CONFIG!!!!
		Properties p = new Properties();
		try {
			p.load(new FileInputStream(new File("config.cfg")));
			for (Map.Entry e : p.entrySet()) {
				data.put((String) e.getKey(), (String) e.getValue());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassCastException e) {

		}

	}
}
