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

package aritzh.waywia.gui.components;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Button extends GUIElement {


	protected static Font defaultFont;

	protected final float x, y, textX, textY;
	protected final int width, height;
	protected String text;

	/**
	 * Brightest to darkest
	 */
	protected static final Color[] colors = new Color[3];

	static {
		Button.colors[0] = new Color(196, 196, 196);
		Button.colors[1] = new Color(119, 119, 119);
		Button.colors[2] = new Color(63, 63, 63);
		try {
			defaultFont = new Image(0, 0).getGraphics().getFont();
		} catch (SlickException ignored) {
		}
	}

	/**
	 * Creates a button, with customizable position and text,
	 * and with a width of 100. <br />
	 * To specify the with, {@link Button#Button(String, int, int, int)}
	 *
	 * @param text The text inside the button
	 * @param x    x coordinate. If negative, button will be centered around it
	 * @param y    y coordinate. If negative, button will be centered around it
	 * @see Button#Button(String, int, int, int)
	 */
	public Button(String text, int x, int y) {
		this(text, x, y, 100);
	}

	/**
	 * Creates a button, with customizable position, text and size
	 *
	 * @param text  The text inside the button
	 * @param x     x coordinate. If negative, button will be centered around it
	 * @param y     y coordinate. If negative, button will be centered around it
	 * @param width The width of the button
	 */
	public Button(String text, int x, int y, int width) {

		this.text = text;
		this.width = width;
		this.height = 35;

		x = x >= 0 ? x : -x - this.width / 2;
		y = y >= 0 ? y : -y - this.height / 2;

		this.x = x;
		this.y = y;

		this.textX = (this.x + this.width / 2);
		if (Button.defaultFont == null) {
			try {
				Button.defaultFont = new Image(0, 0).getGraphics().getFont();
			} catch (SlickException ignored) {
			}
		}
		// Magic math!
		if (Button.defaultFont != null)
			this.textY = (this.y + this.height / 2) - (Button.defaultFont.getHeight(this.text)) / 2;
		else this.textY = (this.y + this.height / 2);
	}

	public void render(Graphics g) {
		if (!this.render) return;

		Color before = g.getColor();

		g.setColor(this.pressed ? Button.colors[2] : this.hover ? Button.colors[0] : Button.colors[1]);
		g.fillRect(this.x, this.y, this.width, this.height);

		g.setColor(this.pressed ? Button.colors[0] : this.hover ? Button.colors[1] : Button.colors[2]);
		g.drawRect(this.x, this.y, this.width, this.height);

		g.setColor(Color.black);
		g.draw(new Point(this.x, this.y));
		g.draw(new Point(this.x + this.width, this.y));
		g.draw(new Point(this.x, this.y + this.height));
		g.draw(new Point(this.x + this.width, this.y + this.height));

		float x = textX - g.getFont().getWidth(this.text) / 2;
		float y = textY;
		g.setColor(Color.white);
		g.drawString(this.text, x, y);
		g.setColor(before);
	}

	public Rectangle getBBox() {
		return new Rectangle(this.x, this.y, this.width, this.height);
	}
}
