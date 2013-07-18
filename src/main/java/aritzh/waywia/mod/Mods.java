package aritzh.waywia.mod;

import aritzh.util.ReflectionUtil;
import aritzh.waywia.core.Game;
import com.google.common.base.Preconditions;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Mods {

	private final List<Mod> mods = new ArrayList<>();

	private final Game game;

	public Mods(Game game) {
		this.game = game;
	}

	public void register(Class mod) {
		Preconditions.checkNotNull(mod);
		Preconditions.checkArgument(ReflectionUtil.classHasAnnotation(mod, ModData.class), "Tried to register non-mod class " + mod + "!");

		Set<Field> instances = game.reflections.getFieldsAnnotatedWith(ModInstance.class);

		Object instance = null;

		if (instances != null && instances.size() == 1) {
			instance = instances.iterator().next();
		}
		if (instance == null) {
			try {
				instance = mod.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				Game.logger.exception("Exception when instantiating mod class", new IllegalArgumentException(e));
			}
		}
		if (instance == null)
			Game.logger.warning("Class " + mod + " could not be loaded as a mod class");
		ModData data = (ModData) mod.getAnnotation(ModData.class);

		Mod m = new Mod(data.modName(), data.version(), data.waywiaVersion(), instance);
		this.mods.add(m);
		Game.logger.log("Mod \"" + m.name + "\" successfully loaded");
	}
}
