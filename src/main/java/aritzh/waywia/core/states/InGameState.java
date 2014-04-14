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

package aritzh.waywia.core.states;

import aritzh.waywia.blocks.BackgroundBlock;
import aritzh.waywia.blocks.Block;
import aritzh.waywia.blocks.WallBlock;
import aritzh.waywia.core.Game;
import aritzh.waywia.core.Login;
import aritzh.waywia.entity.Entity;
import aritzh.waywia.entity.QuadEntity;
import aritzh.waywia.entity.player.Player;
import aritzh.waywia.gui.HudGui;
import aritzh.waywia.lib.BlockLib;
import aritzh.waywia.universe.Universe;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Graphics;

import java.io.File;
import java.io.IOException;

/**
 * @author Aritz Lopez
 */
public class InGameState extends WaywiaState {

    private Universe universe;
    private Player player;

    public InGameState(Game game) {
        super(game, "In-Game");
    }

    @Override
    public int getID() {
        return 1;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void render(Graphics g) {
        if (this.universe != null) this.universe.render(g);
    }

    @Override
    public void update(int delta) {
        this.universe.update(delta);

        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            player.setVY(-1);
        } else if (player.getVY() == -1) player.setVY(0);
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            player.setVY(1);
        } else if (player.getVY() == 1) player.setVY(0);
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            player.setVX(-1);
        } else if (player.getVX() == -1) player.setVX(0);
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            player.setVX(1);
        } else if (player.getVX() == 1) player.setVX(0);

        if (Keyboard.isKeyDown(Keyboard.KEY_W) && Keyboard.isKeyDown(Keyboard.KEY_S)) {
            player.setVY(0);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A) && Keyboard.isKeyDown(Keyboard.KEY_D)) {
            player.setVX(0);
        }
    }

    @Override
    public void init() {
        this.openGUI(new HudGui(this));

        this.initUniverse();

        InGameState.registerEntities();
        InGameState.registerBlocks();
    }

    private void initUniverse() {
        this.universe = Universe.loadUniverse(new File(this.game.savesDir, "uniBase"), this);
        if (this.universe == null) {
            try {
                this.universe = Universe.newUniverse("UniBase", this.game.savesDir, "UniWorld", this);
            } catch (IOException e) {
                Game.logger.e("Could not create universe UniBase", e);
            }
        }
        this.player = new Player(Login.getUsername(), universe);

        this.universe.setPlayer(this.player);
    }

    private static void registerEntities() {
        Entity.registerEntity(QuadEntity.class, 0);
    }

    private static void registerBlocks() {
        Block.registerBlock(new BackgroundBlock(), BlockLib.IDS.BACKGROUND);
        Block.registerBlock(new WallBlock(), BlockLib.IDS.WALL);
    }

    @Override
    public void onClosing() {
        if (this.universe != null) try {
            this.universe.save();
        } catch (IOException e) {
            Game.logger.e("Error saving universe", e);
        }
    }

    @Override
    public void keyPressed(int key, char c) {
        super.keyPressed(key, c);
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        if (!this.isGuiOpen() || this.currGui.hasTransparentBackGround()) universe.clicked(x, y);
    }
}
