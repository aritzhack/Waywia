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

import aritzh.waywia.core.Game;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.newdawn.slick.Image;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class RenderUtil {

	public static Image getImage(String filename) {
		Preconditions.checkArgument(!Strings.isNullOrEmpty(filename), "Image file name must not be null or empty!");
		if (!filename.endsWith(".png")) filename += ".png";
		filename = "img/" + filename;
		try {
			return new Image(TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(filename)));
		} catch (Exception e) {
			Game.logger.logAndThrowAsRuntime("Could not load image " + filename, e);
		}
		return null;
	}
}