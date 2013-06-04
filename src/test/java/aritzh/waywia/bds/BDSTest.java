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

package aritzh.waywia.bds;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class BDSTest {
	@Test
	public void intTest() throws Exception {
		for (int i = Integer.MIN_VALUE / 2; i <= Integer.MAX_VALUE / 2; i += 10000) {
			byte[] data = new BDSInt(i, Integer.toString(i)).getBytes();
			int out = new BDSInt(data).getData();
			assertEquals(i, out);
		}
	}

	@Test
	public void shortTest() throws Exception {
		for (short s = Short.MIN_VALUE; s < Short.MAX_VALUE; s++) {
			byte[] data = new BDSShort(s, Short.toString(s)).getBytes();
			short out = new BDSShort(data).getData();
			assertEquals(s, out);
		}
	}

	@Test
	public void byteTest() throws Exception {
		for (byte b = Byte.MIN_VALUE; b < Byte.MAX_VALUE; b++) {
			byte[] data = new BDSByte(b, Byte.toString(b)).getBytes();
			byte out = new BDSByte(data).getData();
			assertEquals(b, out);
		}
	}

	@Test
	public void stringTest() throws Exception {
		for (int i = Integer.MIN_VALUE / 2; i <= Integer.MAX_VALUE / 2; i += 10000) {
			String nameBefore = Integer.toString(i) + "N";
			String dataBefore = Integer.toString(i);
			BDSString string = new BDSString(new BDSString(dataBefore, nameBefore).getBytes());
			String nameAfter = string.getName();
			String dataAfter = string.getData();

			assertEquals(nameBefore, nameAfter);
			assertEquals(dataBefore, dataAfter);
		}
	}

	@Test
	public void compoundTest() throws Exception {

		String beforeString = "text";
		byte beforeByte = 5;
		short beforeShort = 45;
		int beforeInt = 45687;


		BDSString beforeStringBDS = new BDSString(beforeString, "string");
		BDSByte beforeByteBDS = new BDSByte(beforeByte, "byte");
		BDSInt beforeIntBDS = new BDSInt(beforeInt, "int");
		BDSShort beforeShortBDS = new BDSShort(beforeShort, "short");
		BDSCompound beforeNestedBDS = new BDSCompound("nestedComp").add(beforeStringBDS)
				.add(beforeByteBDS)
				.add(beforeShortBDS)
				.add(beforeIntBDS);

		BDSCompound beforeComp = new BDSCompound("compound");
		beforeComp.add(beforeStringBDS)
				.add(beforeNestedBDS)
				.add(beforeByteBDS)
				.add(beforeShortBDS)
				.add(beforeIntBDS);


		BDSCompound afterComp = new BDSCompound(beforeComp.getBytes());

		BDSString afterStringBDS = afterComp.getString("string", 0);
		BDSByte afterByteBDS = afterComp.getByte("byte", 0);
		BDSShort afterShortBDS = afterComp.getShort("short", 0);
		BDSInt afterIntBDS = afterComp.getInt("int", 0);


		BDSCompound nestedComp = afterComp.getComp("nestedComp", 0);

		BDSString afterNestedStringBDS = nestedComp.getString("string", 0);
		BDSByte afterNestedByteBDS = nestedComp.getByte("byte", 0);
		BDSShort afterNestedShortBDS = nestedComp.getShort("short", 0);
		BDSInt afterNestedIntBDS = nestedComp.getInt("int", 0);

		assertEquals(beforeComp, afterComp);

		assertEquals(beforeString, afterStringBDS.getData());
		assertEquals(beforeByte, afterByteBDS.getData().byteValue());
		assertEquals(beforeShort, afterShortBDS.getData().shortValue());
		assertEquals(beforeInt, afterIntBDS.getData().intValue());

		assertEquals(beforeString, afterNestedStringBDS.getData());
		assertEquals(beforeByte, afterNestedByteBDS.getData().byteValue());
		assertEquals(beforeShort, afterNestedShortBDS.getData().shortValue());
		assertEquals(beforeInt, afterNestedIntBDS.getData().intValue());

	}
}
