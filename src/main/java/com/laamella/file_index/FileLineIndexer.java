package com.laamella.file_index;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Read a file and registers every line as a FileArea. Keys area created by the
 * user.
 */
public abstract class FileLineIndexer<K extends Comparable<K>> {
	public FileAreaIndex<K> index(final File file, final Charset charset) throws IOException {
		final FileAreaIndex<K> fileAreaIndex = new FileAreaIndex<K>();
		index(file, fileAreaIndex, charset);
		return fileAreaIndex;
	}

	public void index(final File file, final FileAreaIndex<K> fileAreaIndex, final Charset charset) throws IOException {
		new FileLineParser() {
			@Override protected void lineRead(final long startPosition, final int size, final StringBuffer line) {
				final FileArea area = new FileArea(startPosition, size);
				final K key = createKey(area, line);
				fileAreaIndex.index(key, area);
			}
		}.parse(file, charset);
	}

	protected abstract K createKey(FileArea fileArea, StringBuffer line);
}
