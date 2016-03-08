package com.laamella.file_index;

import java.util.Map;
import java.util.TreeMap;

/**
 * A fast storage for areas in a file. It is created by the user, and is
 * populated by an indexer.
 * 
 * @param <K>
 *            the key.
 */
public class FileAreaIndex<K extends Comparable<K>> implements MinimalMap<K, FileArea> {
	private final Map<K, FileArea> index;
	private int largestAreaSize;

	/**
	 * Create a new, empty index.
	 */
	public FileAreaIndex() {
		index = new TreeMap<K, FileArea>();
	}

	/**
	 * Create an index with a user specified map implementation.
	 * 
	 * @param index
	 *            an empty map.
	 */
	public FileAreaIndex(final Map<K, FileArea> index) {
		this.index = index;
	}

	public final void index(final K key, final FileArea area) {
		index.put(key, area);
		if (area.size > largestAreaSize) {
			largestAreaSize = area.size;
		}
	}

	@Override public final FileArea get(final K key) {
		return index.get(key);
	}

	@Override public final boolean contains(final K key) {
		return index.containsKey(key);
	}

	/**
	 * @return the size of the largest encountered FileArea size.
	 */
	public int getLargestAreaSize() {
		return largestAreaSize;
	}
}
