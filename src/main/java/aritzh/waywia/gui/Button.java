package aritzh.waywia.gui;

import aritzh.waywia.util.RenderUtil;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Button {

	private static Image texture;
	private float x, y;
	private String text;
	private boolean render;
	private GUI handler;

	static {
		texture = RenderUtil.getTexture("button");
	}

	private int id;

	public Button(int x, int y, String text, int id) {
		this.x = x;
		this.y = y;
		this.text = text;
		this.id = id;
	}

	public void render() {
		if (render) texture.draw(this.x, this.y);
	}


	public void setRender(boolean render) {
		this.render = render;
	}

	public boolean getRender() {
		return render;
	}

	public Rectangle getBBox() {
		return new Rectangle(this.x, this.y, 10, 10);
	}

	public int getID() {
		return this.id;
	}
}
