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

package aritzh.waywia.gui;

import aritzh.waywia.core.states.InGameState;
import aritzh.waywia.entity.player.Player;
import aritzh.waywia.gui.components.GUI;
import aritzh.waywia.util.RenderUtil;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class HudGui extends GUI {

	private static Image heartEmpty = RenderUtil.getImage("heartEmpty");
	private static Image heartFull = RenderUtil.getImage("heartFull");

	public HudGui(InGameState state) {
		super(state);
	}

	@Override
	public void render(Graphics g) {
		super.render(g);
		Player p = ((InGameState) this.state).getPlayer();
		for (int i = 0; i < p.getMaxHealth(); i++) {
			if (p.getHealth() > i) g.drawImage(HudGui.heartFull, HudGui.heartFull.getWidth() * i, 0);
			else g.drawImage(HudGui.heartEmpty, HudGui.heartFull.getWidth() * i, 0);
		}
	}


	@Override
	public void keyPressed(int key, char c) {
		super.keyPressed(key, c);
		if (key == Input.KEY_ESCAPE) this.state.openGUI(new GamePauseGUI((InGameState) this.state, this));
		else if (key == Input.KEY_ENTER) ((InGameState) this.state).getPlayer().hurt(1);
	}
}
