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

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class InputBox extends Button {

    private boolean hasFocus = false;
    private int cursorBlinkCooldown = 0;
    private boolean cursorVisible = true;
    private int cursorPos = 0;
    private boolean insert = true;

    /**
     * Creates a button, with customizable position, text and size
     *
     * @param x x coordinate. If negative, button will be centered around it
     * @param y y coordinate. If negative, button will be centered around it
     */
    public InputBox(int x, int y, int w) {
        super("Placeholder", x, y, w);
        this.text = "";
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        g.setColor(Color.white);
        this.cursorBlinkCooldown++;
        if (cursorBlinkCooldown == 650) {
            cursorVisible = !cursorVisible;
            cursorBlinkCooldown = 0;
        }
        if (!cursorVisible || !this.hasFocus()) return;
        float cursorX = this.textX - (Button.defaultFont.getWidth(this.text) / 2 - Button.defaultFont.getWidth(this.text.substring(0, cursorPos)));
        float beforeW = g.getLineWidth();

        if (Button.defaultFont.getWidth(this.text + "   ") > this.width) {
            g.setLineWidth(4);
            cursorX += 2;
        } else if (!this.insert) {
            int lineWidth = 10;
            if (this.cursorPos + 1 < this.text.length()) {
                lineWidth = Button.defaultFont.getWidth(String.valueOf(this.text.charAt(this.cursorPos + 1)));
            }
            g.setLineWidth(lineWidth);
            cursorX += lineWidth / 2;
        }

        g.drawLine(cursorX, this.y + this.height / 2 - 10, cursorX, this.y + this.height / 2 + 10);
        g.setLineWidth(beforeW);
    }

    public boolean hasFocus() {
        return hasFocus;
    }

    public void keyPressed(int key, char c) {
        if (!this.hasFocus()) return;

        switch (key) {
            case 203: // <-
                this.cursorPos--;
                break;
            case 205: // ->
                this.cursorPos++;
                break;
            case 199: // START
                this.cursorPos = 0;
                break;
            case 207: // END
                this.cursorPos = this.text.length();
                break;
            case 14: // Backspace
                if (this.cursorPos == 0) break; // Nothing to remove
                this.text = this.text.substring(0, cursorPos - 1) + this.text.substring(cursorPos);
                cursorPos--;
                break;
            case 211: // Delete
                if (this.cursorPos == this.text.length()) break; // Nothing to delete
                this.text = this.text.substring(0, cursorPos) + this.text.substring(cursorPos + 1);
                break;
            case 210:  // Insert
                this.insert = !this.insert;
                break;
            default:
                if (Button.defaultFont.getWidth(this.text + "   ") > this.width) break;
                if (insert || this.cursorPos == this.text.length()) {
                    this.text = new StringBuilder(this.text).insert(this.cursorPos, c).toString();
                } else {
                    this.text = new StringBuilder(this.text).deleteCharAt(this.cursorPos).insert(this.cursorPos, c).toString();
                }
                cursorPos++;
                break;
        }
        if (this.cursorPos < 0) cursorPos = 0;
        if (this.cursorPos > this.text.length()) cursorPos = this.text.length();

    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        super.mouseClicked(button, x, y, clickCount);
        this.setHasFocus(this.getBBox().contains(x, y));
    }

    private void setHasFocus(boolean hasFocus) {
        this.hasFocus = hasFocus;
    }

    @Override
    public void setPressed(boolean pressed) {
    }

    @Override
    public void setHover(boolean hover) {
    }

    public String getText() {
        return this.text;
    }
}
