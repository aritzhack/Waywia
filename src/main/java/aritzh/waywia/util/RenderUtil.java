package aritzh.waywia.util;

import aritzh.waywia.core.Game;
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
		try {
			return new Image(TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(filename)));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			return null;
		} catch (Exception e) {
			GameLogger.log(Level.SEVERE, "Error loading resource " + filename);
			Game.instance.getGc().exit();
		}
		return null;
	}
}
