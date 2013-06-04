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

package aritzh.waywia.util;

import aritzh.waywia.core.GameLogger;
import com.google.common.collect.Sets;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Set;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ReflectionUtil {

	public static void addFolderToClasspath(File folder) throws IOException {
		URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		Class<? extends URLClassLoader> sysclass = URLClassLoader.class;

		FileFilter jarAndZips = new FileFilter() {
			@Override
			public boolean accept(File f) {
				return f.isFile() && (f.getName().endsWith(".jar") || f.getName().endsWith(".zip"));
			}
		};

		try {
			Method method = sysclass.getDeclaredMethod("addURL", URL.class);
			method.setAccessible(true);
			for (File f : folder.listFiles(jarAndZips)) {
				GameLogger.debug("Found mod file: " + f.getAbsolutePath());
				method.invoke(sysloader, f.toURI().toURL());
			}

		} catch (Throwable t) {
			t.printStackTrace();
			throw new IOException("Error, could not add URL to system classloader");
		}
	}

	public static boolean classHasAnnotation(Class clazz, Class<? extends Annotation> annotation) {
		try {
			Set<Class> hierarchy = ReflectionUtil.flattenHierarchy(clazz);
			for (Class c : hierarchy) {
				if (c.isAnnotationPresent(annotation)) return true;
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return false;
	}

	private static Set<Class> flattenHierarchy(Class clazz) {
		Set<Class> ret = Sets.newHashSet();

		ret.add(clazz);

		Class up;
		while ((up = clazz.getSuperclass()) != null) {
			ret.add(up);
			ret.addAll(Arrays.asList(up.getInterfaces()));
			clazz = up;
		}

		return ret;
	}
}
