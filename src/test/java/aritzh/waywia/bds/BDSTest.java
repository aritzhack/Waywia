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

import java.io.File;

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
			assert out == i : "Integer " + out + " didn't match " + i;
		}
	}

	@Test
	public void shortTest() throws Exception {
		for (short s = Short.MIN_VALUE; s < Short.MAX_VALUE; s++) {
			byte[] data = new BDSShort(s, Short.toString(s)).getBytes();
			short out = new BDSShort(data).getData();
			assert out == s : "Short " + out + " didn't match " + s;
		}
	}

	@Test
	public void byteTest() throws Exception {
		for (byte b = Byte.MIN_VALUE; b < Byte.MAX_VALUE; b++) {
			byte[] data = new BDSByte(b, Byte.toString(b)).getBytes();
			byte out = new BDSByte(data).getData();
			assert out == b : "Byte " + out + " didn't match " + b;
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
		BDSCompound beforeNestedBDS = new BDSCompound("myComp").add(beforeStringBDS)
				.add(beforeByteBDS)
				.add(beforeShortBDS)
				.add(beforeIntBDS);

		BDSCompound beforeComp = new BDSCompound("compound");
		beforeComp.add(beforeStringBDS)
				.add(beforeNestedBDS)
				.add(beforeByteBDS)
				.add(beforeShortBDS)
				.add(beforeIntBDS);

		beforeComp.writeToFile(new File("a.dat"));


		BDSCompound afterComp = new BDSCompound(beforeComp.getBytes());

		BDSString afterStringBDS = afterComp.getString(0);
		BDSByte afterByteBDS = afterComp.getByte(0);
		BDSShort afterShortBDS = afterComp.getShort(0);
		BDSInt afterIntBDS = afterComp.getInt(0);


		BDSCompound nestedComp = afterComp.getComp(0);

		BDSString afterNestedStringBDS = nestedComp.getString(0);
		BDSByte afterNestedByteBDS = nestedComp.getByte(0);
		BDSShort afterNestedShortBDS = nestedComp.getShort(0);
		BDSInt afterNestedIntBDS = nestedComp.getInt(0);

		assert beforeComp.equals(afterComp) : "Objects are not equal!";

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
