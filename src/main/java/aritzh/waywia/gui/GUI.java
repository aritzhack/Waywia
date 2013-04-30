package aritzh.waywia.gui;

import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public abstract class GUI {
	private List<Button> buttons = new ArrayList<>();
	private List<Label> labels = new ArrayList<>();

	public void render(Graphics g) {
		for (Button b : this.buttons) {
			b.render();
		}
		for (Label l : this.labels) {
			l.render(g);
		}
	}

	protected void addButton(Button b) {
		this.buttons.add(b);
	}

	protected void addLabel(Label l) {
		this.labels.add(l);
	}

	protected void removeButton(Button b) {
		this.buttons.remove(b);
	}

	protected void removeLabel(Label l) {
		this.labels.remove(l);
	}

	public void clicked(int x, int y, int button) {
		for (Button b : this.buttons) {
			if (b.getBBox().contains(x, y)) b.getID();
		}
	}

	public void clicked(int id) {
	}

	public void onKeyPress(int keycode) {
	}
}
