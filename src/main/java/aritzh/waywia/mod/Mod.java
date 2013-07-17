package aritzh.waywia.mod;

/**
 * This class should not be the extended by mods, it's just used to store the mods' data on runtime
 *
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Mod {
	public final String name;
	public final String version;
	public final String waywiaVersion;
	public final Object modInstance;

	protected Mod(String name, String version, String waywiaVersion, Object modInstance) {
		this.name = name;
		this.version = version;
		this.waywiaVersion = waywiaVersion;
		this.modInstance = modInstance;
	}

}
