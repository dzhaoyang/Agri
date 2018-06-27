package com.sunsea.parkinghere.framework.data;

import java.util.concurrent.atomic.AtomicInteger;

public final class IdentityUtils {
	private static AtomicInteger counter = new AtomicInteger(0);
	private static String prefix;

	static {
		prefix = System.getProperty("parking.booking.no.prefix", "A1");
	}

	public static String getIdentity() {
		return String.format("%s%d" , prefix, getAtomicCounter());
	}

	/**
	 * 长生消息id
	 */
	protected static long getAtomicCounter() {
		if (counter.get() > 999999) {
			counter.set(1);
		}
		long time = System.currentTimeMillis();
		long returnValue = time * 100 + counter.incrementAndGet();
		return returnValue;
	}
}
