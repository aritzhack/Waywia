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

import aritzh.waywia.core.GameLogger;
import org.newdawn.slick.Image;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.util.logging.Level;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class RenderUtil {

	public static Image getTexture(String filename) {
		if (!filename.endsWith(".png")) filename += ".png";
		filename = "/res/" + filename;
		try {
			return new Image(TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(filename)));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			return null;
		} catch (Exception e) {
			GameLogger.log(Level.SEVERE, "Error loading resource " + filename);
		}
		return null;
	}
}
