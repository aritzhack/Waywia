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

import org.junit.Test;

import java.io.File;

import static junit.framework.Assert.*;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ConfigTest {

	@Test
	public void regexTest() {
		assertTrue(Configuration.PROP_REGEX.matcher("key=val").matches());
		assertTrue(Configuration.PROP_REGEX.matcher(" key=val").matches());
		assertTrue(Configuration.PROP_REGEX.matcher("key =val").matches());
		assertTrue(Configuration.PROP_REGEX.matcher("key= val").matches());
		assertTrue(Configuration.PROP_REGEX.matcher("key=val ").matches());
		assertTrue(Configuration.PROP_REGEX.matcher(" key =val").matches());
		assertTrue(Configuration.PROP_REGEX.matcher(" key= val").matches());
		assertTrue(Configuration.PROP_REGEX.matcher(" key=val ").matches());
		assertTrue(Configuration.PROP_REGEX.matcher("key = val").matches());
		assertTrue(Configuration.PROP_REGEX.matcher("key =val ").matches());
		assertTrue(Configuration.PROP_REGEX.matcher("key= val ").matches());
		assertTrue(Configuration.PROP_REGEX.matcher(" key = val").matches());
		assertTrue(Configuration.PROP_REGEX.matcher(" key =val ").matches());
		assertTrue(Configuration.PROP_REGEX.matcher(" key = val ").matches());
		assertTrue(Configuration.PROP_REGEX.matcher("     key     =   val        ").matches());

		assertTrue(Configuration.PROP_REGEX.matcher("key=v al").matches());
		assertTrue(Configuration.PROP_REGEX.matcher(" key=v al").matches());
		assertTrue(Configuration.PROP_REGEX.matcher("key =v al").matches());
		assertTrue(Configuration.PROP_REGEX.matcher("key= v al").matches());
		assertTrue(Configuration.PROP_REGEX.matcher("key=v al ").matches());
		assertTrue(Configuration.PROP_REGEX.matcher(" key =v al").matches());
		assertTrue(Configuration.PROP_REGEX.matcher(" key= v al").matches());
		assertTrue(Configuration.PROP_REGEX.matcher(" key=v al ").matches());
		assertTrue(Configuration.PROP_REGEX.matcher("key = v al").matches());
		assertTrue(Configuration.PROP_REGEX.matcher("key =v al ").matches());
		assertTrue(Configuration.PROP_REGEX.matcher("key= v al ").matches());
		assertTrue(Configuration.PROP_REGEX.matcher(" key = v al").matches());
		assertTrue(Configuration.PROP_REGEX.matcher(" key =v al ").matches());
		assertTrue(Configuration.PROP_REGEX.matcher(" key = v al ").matches());
		assertTrue(Configuration.PROP_REGEX.matcher("     key     =   v al        ").matches());

		assertFalse(Configuration.PROP_REGEX.matcher("k ey = val").matches());
		assertFalse(Configuration.PROP_REGEX.matcher("key = ").matches());
		assertFalse(Configuration.PROP_REGEX.matcher("key =").matches());
		assertFalse(Configuration.PROP_REGEX.matcher("key =         ").matches());
		assertFalse(Configuration.PROP_REGEX.matcher("     =         ").matches());
		assertFalse(Configuration.PROP_REGEX.matcher(" = ").matches());
		assertFalse(Configuration.PROP_REGEX.matcher("=").matches());

		assertTrue(Configuration.CATEGORY_REGEX.matcher(" [cat]").matches());
		assertTrue(Configuration.CATEGORY_REGEX.matcher("[cat] ").matches());
		assertTrue(Configuration.CATEGORY_REGEX.matcher(" [ cat]").matches());
		assertTrue(Configuration.CATEGORY_REGEX.matcher(" [cat ]").matches());
		assertTrue(Configuration.CATEGORY_REGEX.matcher("[ cat] ").matches());
		assertTrue(Configuration.CATEGORY_REGEX.matcher("[cat ] ").matches());
		assertTrue(Configuration.CATEGORY_REGEX.matcher(" [cat] ").matches());
		assertTrue(Configuration.CATEGORY_REGEX.matcher(" [ cat] ").matches());
		assertTrue(Configuration.CATEGORY_REGEX.matcher(" [cat ] ").matches());
		assertTrue(Configuration.CATEGORY_REGEX.matcher(" [ cat ] ").matches());
		assertTrue(Configuration.CATEGORY_REGEX.matcher("   [  cat  ]   ").matches());
		assertTrue(Configuration.CATEGORY_REGEX.matcher("[ca t]").matches());
		assertTrue(Configuration.CATEGORY_REGEX.matcher("   [ca   t]  ").matches());
		assertTrue(Configuration.CATEGORY_REGEX.matcher("   [   ca t   ]   ").matches());
		assertTrue(Configuration.CATEGORY_REGEX.matcher("   [   ca   t   ]   ").matches());

		assertFalse(Configuration.CATEGORY_REGEX.matcher("[]").matches());
		assertFalse(Configuration.CATEGORY_REGEX.matcher("[ ]").matches());
		assertFalse(Configuration.CATEGORY_REGEX.matcher("[    ]").matches());
		assertFalse(Configuration.CATEGORY_REGEX.matcher("    []    ").matches());
		assertFalse(Configuration.CATEGORY_REGEX.matcher("    [   ]   ").matches());
	}

	@Test
	public void keyPropertyTest() {
		File configFile = new File("testConfig.cfg");
		Configuration c = Configuration.newConfig(configFile);

		String cat1 = "Category1";

		String key11 = "Key11", value11 = "Val11",
				key12 = "Key 12", value12 = "Val 12",
				key13 = "   Key13   ", value13 = "  Val13  ";

		String cat2 = "Category 2";

		String key21 = "Key21", value21 = "Val21",
				key22 = "Key 22", value22 = "Val 22",
				key23 = "   Key23   ", value23 = "  Val23  ";

		c.setProperty(cat1, key11, value11);
		c.setProperty(cat1, key12, value12);
		c.setProperty(cat1, key13, value13);

		c.setProperty(cat2, key21, value21);
		c.setProperty(cat2, key22, value22);
		c.setProperty(cat2, key23, value23);

		c.save();

		c = Configuration.loadConfig(configFile);

		String afterValue11 = c.getProperty(cat1, key11),
				afterValue12 = c.getProperty(cat1, key12),
				afterValue13 = c.getProperty(cat1, key13),
				afterValue21 = c.getProperty(cat2, key21),
				afterValue22 = c.getProperty(cat2, key22),
				afterValue23 = c.getProperty(cat2, key23);


		assertEquals(value11, afterValue11);
		assertEquals(value12, afterValue12);
		assertEquals(value13.trim(), afterValue13);
		assertEquals(value21, afterValue21);
		assertEquals(value22, afterValue22);
		assertEquals(value23.trim(), afterValue23);

		Util.delete(configFile);
	}
}
