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

package aritzh.waywia.core;

import aritzh.waywia.i18n.I18N;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.LogSystem;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
@SuppressWarnings("UnusedDeclaration")
public class GameLogger {
	private static final Logger logger = Logger.getLogger("GameLogger");
	private static final Level DEFAULT_LOG_LEVEL = Level.INFO;

	private static boolean init;

	public static void init(File root) throws IOException {
		if (!root.exists() && !root.mkdirs()) throw new IOException("Couldn't make folder for logs!");
		GameLogger.logger.setLevel(Level.ALL);
		GameLogger.logger.setUseParentHandlers(false);

		Log.setLogSystem(new NullLogSystem());

		ConsoleHandler consoleHandler = new ConsoleHandler();
		Formatter formatter = new SimpleFormat();

		consoleHandler.setFormatter(formatter);
		consoleHandler.setLevel(Level.ALL);

		GameLogger.logger.addHandler(consoleHandler);

		try {
			FileHandler fileHandler = new FileHandler(root.getAbsolutePath() + File.separator + "log%g.log", 1, 4, false);
			fileHandler.setFormatter(formatter);
			GameLogger.logger.addHandler(fileHandler);
		} catch (IOException e) {
			e.printStackTrace();
		}
		init = true;
	}

	public static boolean isInit() {
		return init;
	}

	public static void log(String s) {
		GameLogger.logger.log(DEFAULT_LOG_LEVEL, s);
	}

	public static void log(Level level, String message) {
		GameLogger.logger.log(level, message);
	}

	public static void log(String format, Object... params) {
		GameLogger.log(String.format(format, params));
	}

	public static void log(Level level, String format, Object... params) {
		GameLogger.log(level, String.format(format, params));
	}

	public static void warning(String message) {
		GameLogger.logger.warning(message);
	}

	public static void warning(String format, Object... args) {
		GameLogger.warning(String.format(format, args));
	}

	public static void debug(String message) {
		GameLogger.logger.fine(message);
	}

	public static void debug(String format, Object... args) {
		GameLogger.debug(String.format(format, args));
	}

	public static void debug(Object o) {
		GameLogger.logger.fine(String.valueOf(o));
	}

	public static void severe(String message) {
		GameLogger.logger.severe(message);
	}

	public static void severe(String format, Object... args) {
		GameLogger.severe(String.format(format, args));
	}

	public static void logTranslated(Level level, String message) {
		GameLogger.log(level, I18N.translate(message));
	}

	public static void logTranslated(Level level, String format, Object... args) {
		GameLogger.logTranslated(level, String.format(format, args));
	}

	public static void logTranslated(String message) {
		GameLogger.logTranslated(GameLogger.DEFAULT_LOG_LEVEL, message);
	}

	public static void logTranslated(String format, Object... args) {
		GameLogger.logTranslated(GameLogger.DEFAULT_LOG_LEVEL, format, args);
	}

	public static void warnTranslated(String message) {
		GameLogger.warning(I18N.translate(message));
	}

	public static void warnTranslated(String format, Object... args) {
		GameLogger.warnTranslated(String.format(format, args));
	}

	public static void exception(String message, Throwable throwable) {
		GameLogger.logger.severe(message);
		throwable.printStackTrace();
	}

	public static void exception(Throwable throwable) {
		GameLogger.exception(throwable.getLocalizedMessage(), throwable);
	}

	public static void logAndThrowAsRuntime(String message, Throwable throwable) {
		GameLogger.exception(message, throwable);
		if (throwable instanceof RuntimeException) {
			throw (RuntimeException) throwable;
		} else throw new RuntimeException(message, throwable);
	}

	public static void logAndThrowAsRuntime(String s) {
		GameLogger.severe(s);
		throw new RuntimeException(s);
	}

	private static class SimpleFormat extends Formatter {

		final DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");

		@Override
		public String format(LogRecord record) {
			StringBuilder builder = new StringBuilder(1000);
			builder.append(df.format(new Date(record.getMillis()))).append(" - ");
			builder.append("[").append((record.getLevel() == Level.FINE ? "DEBUG" : record.getLevel())).append("] ");
			builder.append(formatMessage(record));
			builder.append("\n");
			return builder.toString();
		}
	}

	private static class NullLogSystem implements LogSystem {

		@Override
		public void error(String message, Throwable e) {
		}

		@Override
		public void error(Throwable e) {
		}

		@Override
		public void error(String message) {
		}

		@Override
		public void warn(String message) {
		}

		@Override
		public void warn(String message, Throwable e) {
		}

		@Override
		public void info(String message) {
		}

		@Override
		public void debug(String message) {
		}
	}
}
