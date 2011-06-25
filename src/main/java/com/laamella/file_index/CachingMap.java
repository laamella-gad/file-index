package com.laamella.file_index;

import java.io.IOException;

/**
 * A caching map wrapper.
 * 
 * @param <K>
 *            key type.
 * @param <V>
 *            value type.
 */
// TODO unit test
public class CachingMap<K, V> implements MinimalMap<K, V> {
	private final MinimalMap<K, V> mapToCache;
	private final LeastRecentlyUsedCache<K, V> cache;

	public CachingMap(final MinimalMap<K, V> mapToCache, final int cacheSize) {
		this.mapToCache = mapToCache;
		cache = new LeastRecentlyUsedCache<K, V>(cacheSize);
	}

	@Override public V get(final K key) throws IOException {
		final V cachedValue = cache.get(key);
		if (cachedValue != null) {
			return cachedValue;
		}
		final V freshValue = mapToCache.get(key);
		if (freshValue != null) {
			cache.put(key, freshValue);
		}
		return freshValue;
	}

	@Override public boolean contains(final K key) throws IOException {
		return get(key) != null;
	}

}
