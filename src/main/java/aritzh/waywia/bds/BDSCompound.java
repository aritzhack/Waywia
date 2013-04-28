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

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Special BDS that can store different BDSs inside of it
 *
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class BDSCompound implements IBDS {

	private List<IBDS> items = new ArrayList<IBDS>();
	private String name;

	/**
	 * Constructs an empty BDSCompound
	 *
	 * @param name The name of this BDS
	 */
	public BDSCompound(String name) {
		this.name = name;
	}

	/**
	 * Constructs a BDSCompound with the BDSs inside of the list
	 *
	 * @param items The list of BDSs to construct this with
	 * @param name  The name of this BDS
	 */
	public BDSCompound(List<IBDS> items, String name) {
		this.items.addAll(items);
		this.name = name;
	}

	/**
	 * Constructs a BDSCompound with the BDSs inside of the array
	 *
	 * @param items The array of BDSs to construct this with
	 */
	public BDSCompound(IBDS[] items, String name) {
		this(Arrays.asList(items), name);
	}

	/**
	 * Constructs a BDS with the single specified BDS inside
	 *
	 * @param item The BDS this BDSCompound will contain
	 * @param name The name of this BDS
	 */
	public BDSCompound(IBDS item, String name) {
		this(new IBDS[]{item}, name);
	}

	/**
	 * Parses a BDSCompound from a byte array
	 *
	 * @param data The byte array to parse this BDSCompound from
	 */
	public BDSCompound(byte[] data) {
		this(ByteStreams.newDataInput(data), true);
	}

	private BDSCompound(ByteArrayDataInput input, boolean withType) {
		if (input == null) {
			this.name = "";
			return;
		}
		try {
			if (withType) input.readByte();
			this.name = input.readUTF();

			BDSType type;
			while ((type = BDSType.values()[input.readByte()]) != null) {
				switch (type) {
					case BDS_COMPEND:
						return;
					case BDS_BYTE:
						this.items.add(new BDSByte(input));
						break;
					case BDS_COMPOUND:
						this.items.add(new BDSCompound(input, false));
						break;
					case BDS_INT:
						this.items.add(new BDSInt(input));
						break;
					case BDS_SHORT:
						this.items.add(new BDSShort(input));
						break;
					case BDS_STRING:
						this.items.add(new BDSString(input));
						break;
					default:
						throw new IllegalArgumentException("Could not parse BDSCompound");
				}
			}

		} catch (IllegalArgumentException e) {
			throw e;
		} catch (Exception e) {
			throw new IllegalArgumentException("Could not parse BDSCompound\n" + e.getStackTrace());
		}
	}

	/**
	 * Add an element to this BDS
	 *
	 * @param bds The element to be added
	 * @return {@code this}. Eases builder pattern
	 */
	public BDSCompound add(IBDS bds) {
		if (bds == this) throw new IllegalArgumentException("Cannot add itself as a sub-element!");
		this.items.add(bds);
		return this;
	}

	/**
	 * Add an element to the list, in the specified position
	 *
	 * @param idx The position to add the element to
	 * @param bds The element to be added
	 * @return {@code this}. Eases builder pattern
	 */
	public BDSCompound add(int idx, IBDS bds) {
		this.items.add(idx, bds);
		return this;
	}

	/**
	 * Gets the element in the specified position in the list
	 *
	 * @param idx The index of the element
	 * @return The element in the {@code idx} position
	 */
	public IBDS get(int idx) {
		return this.items.get(idx);
	}

	/**
	 * Returns the {@code offset}'th BDSString from the list
	 *
	 * @param offset The number of BDSStrings to skip
	 * @return The {@code offset}'th BDSString from the list
	 */
	public BDSString getString(int offset) {
		for (IBDS bds : this.items) {
			if (bds instanceof BDSString) {
				if (offset == 0) return (BDSString) bds;
				else offset--;
			}
		}
		return null;
	}

	/**
	 * Returns the {@code offset}'th BDSByte from the list
	 *
	 * @param offset The number of BDSBytes to skip
	 * @return The {@code offset}'th BDSByte from the list
	 */
	public BDSByte getByte(int offset) {
		for (IBDS bds : this.items) {
			if (bds instanceof BDSByte) {
				if (offset == 0) return (BDSByte) bds;
				else offset--;
			}
		}
		return null;
	}

	/**
	 * Returns the {@code offset}'th BDSShort from the list
	 *
	 * @param offset The number of BDSShorts to skip
	 * @return The {@code offset}'th BDSShort from the list
	 */
	public BDSShort getShort(int offset) {
		for (IBDS bds : this.items) {
			if (bds instanceof BDSShort) {
				if (offset == 0) return (BDSShort) bds;
				else offset--;
			}
		}
		return null;
	}

	/**
	 * Returns the {@code offset}'th BDSInt from the list
	 *
	 * @param offset The number of BDSInts to skip
	 * @return The {@code offset}'th BDSInt from the list
	 */
	public BDSInt getInt(int offset) {
		for (IBDS bds : this.items) {
			if (bds instanceof BDSInt) {
				if (offset == 0) return (BDSInt) bds;
				else offset--;
			}
		}
		return null;
	}

	/**
	 * Returns the {@code offset}th BDSCompound from the list
	 *
	 * @param offset The number of BDSCompounds to skip
	 * @return The {@code offset}th BDSCompound from the list
	 */
	public BDSCompound getComp(int offset) {
		for (IBDS bds : this.items) {
			if (bds instanceof BDSCompound) {
				if (offset == 0) return (BDSCompound) bds;
				else offset--;
			}
		}
		return null;
	}

	/**
	 * Remove the element from the list
	 *
	 * @param bds The element to be removed
	 * @return {@code true} if this BDSCompound contained the element
	 */
	public boolean remove(IBDS bds) {
		return this.items.remove(bds);
	}

	/**
	 * Stores the data from this BDSCompound into a byte array, so that
	 * it can be easy and efficiently saved.
	 *
	 * @return The byte array identifying this BDS
	 */
	@Override
	public byte[] getBytes() {

		ByteArrayDataOutput output = ByteStreams.newDataOutput();
		output.writeByte(this.getType().toByte());
		output.writeUTF(this.name);
		for (IBDS bds : this.items) {
			output.write(bds.getBytes());
		}
		output.write(new BDSCompEnd().getBytes());
		return output.toByteArray();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public BDSType getType() {
		return BDSType.BDS_COMPOUND;
	}

	private class BDSCompEnd implements IBDS {

		public BDSCompEnd() {

		}

		@Override
		public byte[] getBytes() {
			return new byte[]{this.getType().toByte()};
		}

		@Override
		public String getName() {
			return "";
		}

		@Override
		public BDSType getType() {
			return BDSType.BDS_COMPEND;
		}
	}
}
