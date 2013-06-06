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

package aritzh.waywia.entity.player;

import aritzh.waywia.bds.BDSCompound;
import aritzh.waywia.entity.Entity;
import aritzh.waywia.universe.Universe;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Player extends Entity {

	String username;
	private Universe universe;

	public Player(String username, Universe universe) {
		this.username = username;
		this.universe = universe;
		this.universe.setPlayer(this);
	}

	public Player() {
	}

	@Override
	public Shape getBoundingShape() {
		return null;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(this.posX, this.posY, 32, 32);
	}

	@Override
	public String getName() {
		return "Player";
	}

	public String getUsername() {
		return this.username;
	}

	@Override
	public BDSCompound toBDS() {
		// Maybe friends list?
		return super.toBDS();
	}

	@Override
	public int getMaxHealth() {
		return 20;
	}

	public static Player fromBDS(BDSCompound comp) {
		Player p = (Player) Entity.fromBDS(comp);
		p.username = comp.getString("Username", 0).getData();
		return p;
	}

	public void move(Vector2f vector2f, int delta) {
		this.posX += (vector2f.x * delta * velX) / 5f;
		this.posY += (vector2f.y * delta * velY) / 5f;
	}

	public void setUniverse(Universe universe) {
		this.universe = universe;
	}
}
