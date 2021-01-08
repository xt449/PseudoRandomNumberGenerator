/*
 * This file is a part of PseudoRandomNumberGenerator.
 *
 * Copyright 2021 Jonathan Talcott
 *
 * PseudoRandomNumberGenerator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or any later version.
 *
 * PseudoRandomNumberGenerator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with PseudoRandomNumberGenerator.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.github.xt449.pseudorandom;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Jonathan Talcott (xt449 / BinaryBanana)
 */
public class Random {

	private static final long STATIC_SEED = System.currentTimeMillis();

	public final AtomicLong seed;

	public Random(long seed) {
		this.seed = new AtomicLong(seed);
	}

	public Random() {
		this(Random.STATIC_SEED ^ System.nanoTime());
	}

	private long nextInternal() {
		final long value = seed.get();

		if(value < 0) {
			if(value % 2 == 0) {
				seed.incrementAndGet();
			} else {
				seed.addAndGet(System.nanoTime());
			}
		} else {
			if(value % 2 == 0) {
				seed.set(-value);
			} else {
				seed.set(~(value ^ System.nanoTime()));
			}
		}

		return value;
	}

	// Unbounded generation

	public boolean nextBoolean() {
		return nextInternal() % 2 == 0;
	}

	public byte nextByte() {
		return (byte) (nextInternal() >>> 56);
	}

	public char nextChar() {
		return (char) ((nextInternal() >>> 48) - Short.MIN_VALUE);
	}

	public byte nextShort() {
		return (byte) (nextInternal() >>> 48);
	}

	public int nextInt() {
		return (int) (nextInternal() >> 32);
	}

	public long nextLong() {
		return nextInternal();
	}

	public float nextFloat() {
		return 1F / nextInternal();
	}

	public double nextDouble() {
		return 1D / nextInternal();
	}

	// Bounded generation

	/**
	 * @param bound exclusive
	 */
	public byte nextByte(byte bound) {
		return (byte) Math.floorMod(nextByte(), bound);
	}

	/**
	 * @param bound exclusive
	 */
	public char nextChar(char bound) {
		return (char) Math.floorMod(nextChar(), bound);
	}

	/**
	 * @param bound exclusive
	 */
	public short nextShort(short bound) {
		return (short) Math.floorMod(nextShort(), bound);
	}

	/**
	 * @param bound exclusive
	 */
	public int nextInt(int bound) {
		return (int) Math.floorMod(nextInt(), bound);
	}

	/**
	 * @param bound exclusive
	 */
	public long nextLong(long bound) {
		return (long) Math.floorMod(nextLong(), bound);
	}
}
