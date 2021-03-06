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

import aritzh.waywia.blocks.Block;
import aritzh.waywia.core.Game;
import aritzh.waywia.universe.World;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import io.github.aritzhack.util.bds.BDSCompound;
import io.github.aritzhack.util.bds.BDSInt;
import io.github.aritzhack.util.bds.BDSStorable;
import io.github.aritzhack.util.bds.BDSString;
import io.github.aritzhack.util.collections.ParametrizedFunction;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import java.util.List;

/**
 * @author Aritz Lopez
 */
public abstract class Entity implements BDSStorable {

    private static final BiMap<Integer, Class<? extends Entity>> entities = HashBiMap.create();
    private static final ParametrizedFunction<Block, Boolean> checkCollision = (input, args) -> {
        int x = (int) args[0] * Block.SIZE;
        int y = (int) args[1] * Block.SIZE;
        Shape bbox = (Shape) args[2];
        return input.isSolid() && bbox.intersects(new Rectangle(x, y, Block.SIZE, Block.SIZE));
    };
    protected final float width = Block.SIZE, height = Block.SIZE;
    protected final BDSCompound customData = new BDSCompound("CustomData");
    protected float posX = 0, posY = 0;
    protected int velX = 1, velY = 1;
    protected int health;

    public Entity() {
        this.health = this.getMaxHealth();
    }

    public abstract int getMaxHealth();

    public Entity(int health) {
        this.health = health;
    }

    public abstract void render(Graphics g);

    public abstract String getName();

    public static void registerEntity(Class<? extends Entity> clazz, int id) {
        if (Entity.entities.containsValue(clazz))
            throw new IllegalArgumentException("Entity class " + clazz + "was already registered!");
        if (Entity.entities.containsKey(id))
            throw new IllegalArgumentException("Entity ID " + id + "was already in use by " + Entity.entities.get(id) + " when adding " + clazz);

        Entity.entities.put(id, clazz);
    }

    public static Entity fromBDS(BDSCompound comp) {
        int id = comp.getInt("ID", 0).getData();

        float posX = Float.valueOf(comp.getString("posX", 0).getData());
        float posY = Float.valueOf(comp.getString("posY", 0).getData());

        int velX = comp.getInt("velX", 0).getData();
        int velY = comp.getInt("velY", 0).getData();

        Entity e = Entity.getNewEntity(id);

        e.setPosition(posX, posY);
        e.setVelocity(velX, velY);

        return e;
    }

    public static Entity getNewEntity(int id) {
        try {
            return Entity.entities.get(id).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            Game.logger.e("Could not instantiate entity with id \"" + id + "\"", e);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Entity type \"" + id + "\" is not registered. This is a bug!");
        }
        return null;
    }

    public void setPosition(float x, float y) {
        this.posX = x;
        this.posY = y;
    }

    public void setVelocity(int vx, int vy) {
        this.velX = vx;
        this.velY = vy;
    }

    public void update(int delta, World world) {
        float targetPosX = this.posX + (delta * velX) / 5f;
        float targetPosY = this.posY + (delta * velY) / 5f;


        if (!this.collides(world, new Rectangle(targetPosX, this.posY, Block.SIZE, Block.SIZE))) this.posX = targetPosX;
        if (!this.collides(world, new Rectangle(this.posX, targetPosY, Block.SIZE, Block.SIZE))) this.posY = targetPosY;
    }

    private boolean collides(World world, Rectangle bbox) {
        List<Boolean> booleanList = world.runForEachBlock(checkCollision, bbox).toFlatArrayList();
        for (boolean bool : booleanList) {
            if (bool) return true;
        }
        return false;
    }

    public Shape getBoundingShape() {
        return new Rectangle(this.posX, this.posY, this.width, this.height);
    }

    public BDSCompound getCustomData() {
        return this.customData;
    }

    public Shape getBoundingBox() {
        return new Rectangle(this.posX, this.posY, Block.SIZE, Block.SIZE);
    }

    public BDSCompound toBDS() {
        return new BDSCompound("Entity")
                .add(new BDSInt(Entity.entities.inverse().get(this.getClass()), "ID"))
                .add(new BDSString(Float.toString(posX), "posX"))
                .add(new BDSString(Float.toString(posY), "posY"))
                .add(new BDSInt(velX, "velX"))
                .add(new BDSInt(velY, "velY"))
                .add(this.customData);
    }

    public Vector2f getPosition() {
        return new Vector2f(posX, posY);
    }

    public void setPosition(Vector2f pos) {
        this.posX = pos.getX();
        this.posY = pos.getY();
    }

    public Vector2f getVelocity() {
        return new Vector2f(velX, velY);
    }

    public int getHealth() {
        return this.health;
    }

    public void hurt(int amount) {
        this.health -= amount;
        if (this.health < 0) this.health = 0;
    }

    public int getVX() {
        return this.velX;
    }

    public void setVX(int vx) {
        this.velX = vx;
    }

    public int getVY() {
        return this.velY;
    }

    public void setVY(int vy) {
        this.velY = vy;
    }
}
