package com.laamella.file_index;

import java.io.IOException;

/**
 * Minimal map interface.
 * 
 * @param <K>
 *            key type.
 * @param <V>
 *            value type.
 */
public interface MinimalMap<K, V> {
	V get(K key) throws IOException;

	boolean contains(K key) throws IOException;
}
