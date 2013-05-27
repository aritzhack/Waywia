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

package aritzh.waywia.blocks;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class BackgroundBlock extends Block {

	@Override
	public void render(int x, int y, Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.blue);
		g.fillRect(x * 16, y * 16, (x + 1) * 16, (y + 1) * 16);
		g.setColor(c);
	}

	@Override
	public String getName() {
		return "Background";
	}
}
