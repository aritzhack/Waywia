package aritzh.waywia.gui;

import aritzh.waywia.core.Game;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class MainMenuGUI extends GUI {

	private Button newGameButton, exitButton;
	private Game game;

	public MainMenuGUI(Game game) {
		this.game = game;
		this.addButton(newGameButton = new Button(0, 0, "New Game", 0));
		this.addButton(exitButton = new Button(50, 50, "Exit", 1));
	}

	@Override
	public void clicked(int id) {
		super.clicked(id);

		if (id == exitButton.getID()) {
			this.game.getGc().exit();
		}
	}
}
