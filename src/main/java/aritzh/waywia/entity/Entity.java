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

package aritzh.waywia.entity;

import aritzh.waywia.bds.BDSCompound;
import aritzh.waywia.bds.BDSInt;
import aritzh.waywia.bds.BDSStorable;
import aritzh.waywia.bds.BDSString;
import aritzh.waywia.core.GameLogger;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public abstract class Entity implements BDSStorable {

	protected float posX = 0;
	protected float posY = 0;
	protected float velX = 1;
	protected float velY = 1;
	protected int health;
	protected final BDSCompound customData = new BDSCompound("CustomData");
	private static final BiMap<Integer, Class<? extends Entity>> entities = HashBiMap.create();

	public Entity() {
		this.health = this.getMaxHealth();
	}

	public abstract int getMaxHealth();

	public void update(int delta) {
		//float targetPosX = this.posX + this.velX * delta;
		//float targetPosY = this.posY + this.velY * delta;

		// TODO Collision

		//this.setPosition(targetPosX, targetPosY);
	}

	public abstract Shape getBoundingShape();

	public BDSCompound getCustomData() {
		return this.customData;
	}

	public abstract void render(Graphics g);

	public abstract String getName();

	public BDSCompound toBDS() {
		return new BDSCompound("Entity")
				.add(new BDSInt(Entity.entities.inverse().get(this.getClass()), "ID"))
				.add(new BDSString(Float.toString(posX), "posX"))
				.add(new BDSString(Float.toString(posY), "posY"))
				.add(new BDSString(Float.toString(velX), "velX"))
				.add(new BDSString(Float.toString(velY), "velY"))
				.add(this.customData);
	}

	public static void registerEntity(Class<? extends Entity> clazz, int id) {
		if (Entity.entities.containsValue(clazz))
			throw new IllegalArgumentException("Entity class " + clazz + "was already registered!");
		if (Entity.entities.containsKey(id))
			throw new IllegalArgumentException("Entity ID " + id + "was already in use by " + Entity.entities.get(id) + " when adding " + clazz);

		Entity.entities.put(id, clazz);
	}

	public static Entity getNewEntity(int id) {
		try {
			return Entity.entities.get(id).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			GameLogger.exception("Could not instantiate entity with id \"" + id + "\"", e);
		} catch (NullPointerException e) {
			throw new IllegalArgumentException("Entity type \"" + id + "\" is not registered. This is a bug!");
		}
		return null;
	}

	public static Entity fromBDS(BDSCompound comp) {
		int id = comp.getInt("ID", 0).getData();

		float posX = Float.valueOf(comp.getString("posX", 0).getData());
		float posY = Float.valueOf(comp.getString("posY", 0).getData());

		float velX = Float.valueOf(comp.getString("velX", 0).getData());
		float velY = Float.valueOf(comp.getString("velY", 0).getData());

		Entity e = Entity.getNewEntity(id);

		e.setPosition(posX, posY);
		e.setVelocity(velX, velY);

		return e;
	}

	public Vector2f getPosition() {
		return new Vector2f(posX, posY);
	}

	public Vector2f getVelocity() {
		return new Vector2f(velX, velY);
	}

	public void setPosition(float x, float y) {
		this.posX = x;
		this.posY = y;
	}

	public void setVelocity(float vx, float vy) {
		this.velX = vx;
		this.velY = vy;
	}

	public void setPosition(Vector2f pos) {
		this.posX = pos.getX();
		this.posY = pos.getY();
	}

	public void setVelocity(Vector2f vel) {
		this.velX = vel.getX();
		this.velY = vel.getY();
	}

	public int getHealth() {
		return this.health;
	}

	public void hurt(int amount) {
		this.health -= amount;
		if (this.health < 0) this.health = 0;
	}
}
