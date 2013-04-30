package aritzh.waywia.gui;

import org.newdawn.slick.Graphics;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Label {

	private String text;
	private float x;
	private float y;
	boolean render = true;

	public Label(float x, float y, String text) {
		this.text = text;
		this.x = x;
		this.y = y;
	}

	public void render(Graphics g) {
		if (render) g.drawString(this.text, this.x, this.y);
	}

	public void setRender(boolean render) {
		this.render = render;
	}

	public boolean getRender() {
		return render;
	}
}
