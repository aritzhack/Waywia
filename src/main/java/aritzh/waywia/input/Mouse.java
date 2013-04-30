package aritzh.waywia.input;

import aritzh.waywia.core.Game;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Mouse implements MouseListener {

	private Game game;

	public Mouse(Game game) {
		this.game = game;
		this.game.getGc().getInput().addMouseListener(this);
	}

	@Override
	public void mouseWheelMoved(int change) {
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {

	}

	@Override
	public void mousePressed(int button, int x, int y) {
		if (this.game.currGui != null) this.game.currGui.clicked(x, y, button);
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
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
