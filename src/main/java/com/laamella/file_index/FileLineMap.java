package com.laamella.file_index;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * A map of a key to a line in a file.
 * 
 * @param <K>
 *            the type of the keys.
 */
public class FileLineMap<K extends Comparable<K>> implements MinimalMap<K, String> {
	private final FileAreaIndex<K> fileAreaIndex;
	private final File file;
	private final CharsetDecoder decoder;
	private final CharBuffer charBuffer;
	private final ByteBuffer byteBuffer;
	private FileChannel channel;
	private FileInputStream fileInputStream;

	public FileLineMap(final File file, final FileAreaIndex<K> fileAreaIndex, final Charset charset) throws IOException {
		this.file = file;
		this.fileAreaIndex = fileAreaIndex;
		decoder = charset.newDecoder();
		byteBuffer = ByteBuffer.allocate(fileAreaIndex.getLargestAreaSize());
		charBuffer = CharBuffer.allocate(fileAreaIndex.getLargestAreaSize());
		open();
	}

	public FileLineMap(final File file, final FileLineIndexer<K> fileLineIndexer, final Charset charset)
			throws IOException {
		this(file, fileLineIndexer.index(file, charset), charset);
	}

	private void open() throws IOException {
		fileInputStream = new FileInputStream(file);
		channel = fileInputStream.getChannel();
	}

	public void close() throws IOException {
		channel.close();
		fileInputStream.close();
	}

	@Override public String get(final K key) throws IOException {
		final FileArea fileArea = fileAreaIndex.get(key);
		if (fileArea == null) {
			return null;
		}
		channel.position(fileArea.startPosition);
		byteBuffer.clear();
		byteBuffer.limit(fileArea.size);
		channel.read(byteBuffer);
		byteBuffer.flip();
		charBuffer.clear();
		decoder.reset();
		decoder.decode(byteBuffer, charBuffer, false);
		charBuffer.flip();
		return charBuffer.toString();
	}

	@Override public boolean contains(final K key) {
		// TODO Auto-generated method stub
		return false;
	}
}
