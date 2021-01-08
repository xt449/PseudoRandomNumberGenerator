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

	private int nextInternal() {
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

		return (int) (value >>> 32);

//		switch((int) Math.floorMod(seed.get(), 4)) {
//			case 0: {
//				return (int) seed.incrementAndGet();
//			}
//			case 1: {
//				return (int) seed.addAndGet(System.nanoTime());
//			}
//			case 2: {
//				final long temp = ;
//
//				return (int) (temp >>> 32);
//			}
//			default: {
//				final long temp = ;
//
//				return (int) (temp >>> 32);
//			}
//		}
	}

	// Unbounded generation

	public boolean nextBoolean() {
		return nextInternal() % 2 == 0;
	}

	public byte nextByte() {
		return (byte) (nextInternal() >>> 24);
	}

	public char nextChar() {
		return (char) ((nextInternal() >>> 16) - Short.MIN_VALUE);
	}

	public byte nextShort() {
		return (byte) (nextInternal() >>> 16);
	}

	public int nextInt() {
		return nextInternal();
	}

	public long nextLong() {
		return (((long) nextInternal()) << 32) + nextInternal();
	}

	public float nextFloat() {
		return 1F / nextInt();
	}

	public double nextDouble() {
		return 1D / nextLong();
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
