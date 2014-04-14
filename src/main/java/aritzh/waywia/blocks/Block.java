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

import aritzh.waywia.universe.World;
import aritzh.waywia.util.RenderUtil;
import com.google.common.collect.Lists;
import io.github.aritzhack.util.bds.BDSCompound;
import io.github.aritzhack.util.bds.BDSInt;
import io.github.aritzhack.util.bds.BDSStorable;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.util.List;

/**
 * @author Aritz Lopez
 */
public abstract class Block implements BDSStorable {

    public static final int SIZE = 32;
    private static final List<Block> blocks = Lists.newArrayList();
    Image texture = RenderUtil.getImage("defaultBlock");
    private boolean solid = false;

    public abstract String getName();

    public static void registerBlock(Block block, int id) {
        if (Block.blocks.contains(block))
            throw new IllegalArgumentException("Block " + block + " was already registered");
        if (Block.blocks.size() > id && Block.blocks.get(id) != null)
            throw new IllegalArgumentException("Block id " + id + "is occupied by " + Block.blocks.get(id) + " when adding " + block);
        Block.blocks.add(id, block);
    }

    public static Block fromBDS(BDSCompound comp) {
        try {
            int id = comp.getInt("ID", 0).getData();
            return Block.getBlock(id);
        } catch (NullPointerException ignored) {
            return null;
        }
    }

    public static Block getBlock(int id) {
        return Block.blocks.size() > id ? Block.blocks.get(id) : null;
    }

    public void render(int x, int y, Graphics g, World world) {
        g.drawImage(texture, x * SIZE, y * SIZE);
    }

    protected void setSolid() {
        this.solid = true;
    }

    public boolean isSolid() {
        return this.solid;
    }

    public BDSCompound toBDS() {
        return new BDSCompound("Block").add(new BDSInt(Block.blocks.indexOf(this), "ID"));
    }

    public void clicked(float x, float y, World world) {
    }

    public void update(int x, int y, int delta, World arg) {
    }
}
