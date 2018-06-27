package com.sunsea.parkinghere.framework.data;

import java.util.LinkedHashMap;

public class ListMap<K, V> extends LinkedHashMap<K, V> {
	private static final long serialVersionUID = 2342216660568623659L;

	public ListMap() {
		super(16, 0.75f, false);
	}

	public ListMap(int initialCapacity) {
		super(initialCapacity, 0.75f, false);
	}

	public ListMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor, false);
	}

	public int indexOf(K key) {
		int i = -1;
		for (K k : keySet()) {
			i++;
			if (key.equals(k))
				return i;
		}
		return -1;
	}

	public K getLastKey() {
		K retval = null;
		for (K k : keySet()) {
			retval = k;
		}
		return retval;
	}
}
