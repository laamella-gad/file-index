package com.laamella.file_index;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Read a file and registers every line as a FileArea. Keys are created by the
 * user.
 */
// TODO save/load indexes
public class FileLineIndexer<K extends Comparable<K>> {
	private final KeyFactory<K> keyFactory;

	public FileLineIndexer(KeyFactory<K> keyFactory) {
		this.keyFactory = keyFactory;
	}

	public FileAreaIndex<K> index(final File file, final Charset charset) throws IOException {
		final FileAreaIndex<K> fileAreaIndex = new FileAreaIndex<>();
		index(file, fileAreaIndex, charset);
		return fileAreaIndex;
	}

	public void index(final File file, final FileAreaIndex<K> fileAreaIndex, final Charset charset) throws IOException {
		new FileLineParser().parse(file, charset, (startPosition, size, line) -> {
					final FileArea area = new FileArea(startPosition, size);
					final K key = keyFactory.createKey(area, line);
					fileAreaIndex.index(key, area);
				}
		);
	}

	public interface KeyFactory<K> {
		K createKey(FileArea fileArea, StringBuffer line);
	}
}
