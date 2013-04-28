/*
 * Copyright (c) 2013 Aritzh (Aritz Lopez)
 *
 * This game is free software: you can redistribute it and/or modify it under the
 * terms of the GNUGeneral Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
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
			assert out == i : "Integer " + i + " didn't match " + out;
		}
	}

	@Test
	public void shortTest() throws Exception {
		for (short s = Short.MIN_VALUE; s < Short.MAX_VALUE; s++) {
			byte[] data = new BDSShort(s, Short.toString(s)).getBytes();
			short out = new BDSShort(data).getData();
			assert out == s : "Short " + s + " didn't match " + out;
		}
	}

	@Test
	public void byteTest() throws Exception {
		for (byte b = Byte.MIN_VALUE; b < Byte.MAX_VALUE; b++) {
			byte[] data = new BDSByte(b, Byte.toString(b)).getBytes();
			byte out = new BDSByte(data).getData();
			assert out == b : "Byte " + b + " didn't match " + out;
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
			assert nameAfter.equals(nameBefore) && dataAfter.equals(dataBefore)
					: "Name should be " + nameBefore + " and is " + nameAfter + "\n"
					+ "Data should be " + dataBefore + " and is " + dataAfter;
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

		BDSCompound comp = new BDSCompound("compound");
		comp.add(beforeStringBDS)
				.add(beforeByteBDS)
				.add(beforeShortBDS)
				.add(beforeIntBDS)
				.add(new BDSCompound("myComp")
						.add(beforeStringBDS)
						.add(beforeByteBDS)
						.add(beforeShortBDS)
						.add(beforeIntBDS));

		BDSCompound afterComp = new BDSCompound(comp.getBytes());

		BDSString afterStringBDS = (BDSString) afterComp.get(0);
		BDSByte afterByteBDS = (BDSByte) afterComp.get(1);
		BDSShort afterShortBDS = (BDSShort) afterComp.get(2);
		BDSInt afterIntBDS = (BDSInt) afterComp.get(3);

		BDSCompound nestedComp = (BDSCompound) afterComp.get(4);

		BDSString afterNestedStringBDS = (BDSString) afterComp.get(0);
		BDSByte afterNestedByteBDS = (BDSByte) afterComp.get(1);
		BDSShort afterNestedShortBDS = (BDSShort) afterComp.get(2);
		BDSInt afterNestedIntBDS = (BDSInt) afterComp.get(3);

		assert beforeString.equals(afterStringBDS.getData()) : "String not equal";
		assert beforeByte == afterByteBDS.getData() : "Byte not equal";
		assert beforeShort == afterShortBDS.getData() : "Short not equal";
		assert beforeInt == afterIntBDS.getData() : "Int not equal";

		assert beforeString.equals(afterNestedStringBDS.getData()) : "Nested String not equal";
		assert beforeByte == afterNestedByteBDS.getData() : "Nested Byte not equal";
		assert beforeShort == afterNestedShortBDS.getData() : "Nested Short not equal";
		assert beforeInt == afterNestedIntBDS.getData() : "Nested Int not equal";
	}
}
