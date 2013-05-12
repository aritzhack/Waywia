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

import org.newdawn.slick.Graphics;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Inputbox extends Button {

	private boolean hasFocus = false;
	private int cursorBlinkCooldown = 0;
	private boolean cursorVisible = true;

	/**
	 * Creates a button, with customizable position, text and size
	 *
	 * @param x x coordinate. If negative, button will be centered around it
	 * @param y y coordinate. If negative, button will be centered around it
	 */
	public Inputbox(int x, int y, int w) {
		super("Placeholder", x, y, w);
		this.text = "";
	}

	@Override
	// To avoid the image change
	public void setHover(boolean hover) {
	}

	@Override
	// To avoid the image change
	public void setPressed(boolean pressed) {
	}

	@Override
	public void render(Graphics g) {
		super.render(g);
		this.cursorBlinkCooldown++;
		if (cursorBlinkCooldown == 650) {
			cursorVisible = !cursorVisible;
			cursorBlinkCooldown = 0;
		}
		if (!cursorVisible || !this.hasFocus()) return;
		float cursorX = this.x + this.width / 2 + Button.defaultFont.getWidth(this.text) / 2;
		float beforeW = g.getLineWidth();
		if (Button.defaultFont.getWidth(this.text + "   ") > this.width) {
			g.setLineWidth(4);
			cursorX += 2;
		}
		g.drawLine(cursorX, this.y + this.height / 2 - 10, cursorX, this.y + this.height / 2 + 10);
		g.setLineWidth(beforeW);
	}

	public void keyPressed(int key, char c) {
		if (!this.hasFocus()) return;
		if (c == 8 && !this.text.equals("")) {
			this.text = this.text.substring(0, this.text.length() - 1);
			return;
		} else if (c == ' ') {
			c = '_';
		}
		if (Button.defaultFont.getWidth(this.text + "   ") > this.width) return;
		this.text += c;
	}

	private void setHasFocus(boolean hasFocus) {
		this.hasFocus = hasFocus;
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		super.mouseClicked(button, x, y, clickCount);
		this.setHasFocus(this.getBBox().contains(x, y));
	}

	public boolean hasFocus() {
		return hasFocus;
	}

	public String getText() {
		return this.text;
	}
}
