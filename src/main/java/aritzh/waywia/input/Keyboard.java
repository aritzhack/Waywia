package aritzh.waywia.input;

import aritzh.waywia.core.Game;
import aritzh.waywia.core.GameLogger;
import org.newdawn.slick.*;
import org.newdawn.slick.imageout.ImageOut;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Keyboard implements KeyListener {

	private Game game;

	public Keyboard(Game game) {
		this.game = game;
		game.getGc().getInput().addKeyListener(this);
	}

	@Override
	public void keyPressed(int key, char c) {
		GameContainer gc = this.game.getGc();
		switch (key) {
			case Input.KEY_ESCAPE:
				gc.exit();
				break;
			case Input.KEY_S:
				Image target = null;
				try {
					target = new Image(gc.getWidth(), gc.getHeight());
					gc.getGraphics().copyArea(target, 0, 0);
					ImageOut.write(target, "screenshot.png", false);
					target.destroy();
				} catch (SlickException e) {
					throw new RuntimeException("Could not save screenshot!");
				}
				break;
			case Input.KEY_R:
				GameLogger.log("Reload!");
				this.game.reload();
				break;
			default:
				break;
		}
		if (this.game.currGui != null) this.game.currGui.onKeyPress(key);
	}

	@Override
	public void keyReleased(int key, char c) {

	}

	@Override
	public void setInput(Input input) {
	}

	@Override
	public boolean isAcceptingInput() {
		return true;
	}

	@Override
	public void inputEnded() {
	}

	@Override
	public void inputStarted() {
	}
}
