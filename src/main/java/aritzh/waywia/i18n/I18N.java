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

package aritzh.waywia.i18n;

import aritzh.waywia.core.GameLogger;
import com.google.common.base.Preconditions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class I18N {

	private static final Map<Locale, Properties> locales = new HashMap<>();
	private static Locale currentLocale = Locale.US;

	public static void addLocale(Locale locale, Properties properties) {
		if (I18N.locales.containsKey(locale)) {
			GameLogger.warning("Tried to add locale " + locale.toString() + " when it was already added!");
			return;
		}
		I18N.locales.put(locale, properties);
	}

	public static void mergeLocales(Locale locale, Properties properties) {
		Properties addTo = I18N.locales.get(locale);
		if (addTo == null) addTo = new Properties();
		for (Map.Entry prop : properties.entrySet()) {
			addTo.setProperty((String) prop.getKey(), (String) prop.getValue());
		}
		I18N.locales.put(locale, addTo);
	}

	/**
	 * Adds a localization file. The file must be readable using:
	 * <ul>
	 * <li>{@link Properties#load(java.io.InputStream)} if {@code xml} is {@code false}</li>
	 * <li>{@link Properties#loadFromXML(java.io.InputStream)} if {@code xml} is {@code true}</li>
	 * </ul>
	 * <p>If the {@code locale} argument is null, the locale will be taken from the file, as a propety with key {@code locale}.</p>
	 * The property must be readable using {@link Locale#forLanguageTag(String)}
	 *
	 * @param file   The file to load the localization from
	 * @param xml    Whether the file's format is xml or not
	 * @param locale Explicitly tell the locale of the file. Can be implicitly told by adding
	 *               a {@code locale} property to the file, and leaving this argument as null.
	 */
	public static void readLocale(File file, boolean xml, Locale locale) {
		Preconditions.checkNotNull(file, "Language file must not be null");
		Preconditions.checkNotNull(locale, "Locale must not be null");
		try {
			FileInputStream is = new FileInputStream(file);

			Properties props = new Properties();
			if (xml) props.loadFromXML(is);
			else props.load(is);

			props.remove("locale");
			if (locales.get(locale) != null) {
				I18N.mergeLocales(locale, props);
			} else {
				locales.put(locale, props);
			}
		} catch (IOException e) {
			GameLogger.logAndThrowAsRuntime("Could not read locale " + locale + " from file " + file.getAbsolutePath(), e);
		}
	}

	/**
	 * Adds a localization file. The file must be readable using:
	 * <ul>
	 * <li>{@link Properties#load(java.io.InputStream)} if {@code xml} is {@code false}</li>
	 * <li>{@link Properties#loadFromXML(java.io.InputStream)} if {@code xml} is {@code true}</li>
	 * </ul>
	 * <p>If the {@code locale} argument is null, the locale will be taken from the file, as a propety with key {@code locale}.</p>
	 * The property must be readable using {@link Locale#forLanguageTag(String)}
	 *
	 * @param file   The file to load the localization from
	 * @param xml    Whether the file's format is xml or not
	 * @param locale Explicitly tell the locale of the file. Can be implicitly told by adding
	 *               a {@code locale} property to the file, and leaving this argument as null.
	 */
	public static void readLocale(String file, boolean xml, Locale locale) {
		I18N.readLocale(new File(file), xml, locale);
	}


	public static void init(File basedir) throws IOException {
		if (!basedir.exists() && !basedir.mkdirs())
			throw new IOException("Couldn't create folder for localization files");
		if (!basedir.isDirectory()) {
			throw new IllegalArgumentException("Base directory for I18N must be a directory!");
		}

		for (File f : basedir.listFiles(I18N.propFiles)) {
			String fileName = f.getName().replace(".lang.xml", "").replace(".lang", "").replace("_", "-");
			Locale locale = Locale.forLanguageTag(fileName);
			if (locale == null) {
				GameLogger.warning("File " + f.getAbsolutePath() + " doesn't have a correct language-file name syntax");
				continue;
			}
			I18N.readLocale(f, false, locale);
		}
		for (File f : basedir.listFiles(I18N.xmlFiles)) {
			String fileName = f.getName().replaceFirst("[.][^.]+$", "").replace("_", "-");
			Locale locale = Locale.forLanguageTag(fileName);
			if (locale == null) {
				GameLogger.warning("File " + f.getAbsolutePath() + " doesn't have a correct language-file name syntax");
				continue;
			}
			I18N.readLocale(f, true, locale);
		}
	}

	public static String translate(String key) {
		if (I18N.locales.containsKey(I18N.currentLocale) && I18N.locales.get(I18N.currentLocale).containsKey(key))
			return I18N.locales.get(I18N.currentLocale).getProperty(key);
		else return key;
	}

	private static final FilenameFilter propFiles = new FilenameFilter() {
		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(".lang");
		}
	};
	private static final FilenameFilter xmlFiles = new FilenameFilter() {
		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(".lang.xml");
		}
	};
}
