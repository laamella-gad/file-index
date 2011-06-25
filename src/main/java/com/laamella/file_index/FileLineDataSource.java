package com.laamella.file_index;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import com.laamella.file_index.FileAreaIndex.FileArea;

public class FileLineDataSource<K extends Comparable<K>> {
	private final FileAreaIndex<K> fileAreaIndex;
	private final File file;
	private CharsetDecoder decoder;
	private CharBuffer charBuffer;
	private ByteBuffer byteBuffer;
	private FileChannel channel;
	private FileInputStream fileInputStream;

	public FileLineDataSource(final File file, final FileAreaIndex<K> fileAreaIndex, final Charset charset)
			throws IOException {
		this.file = file;
		this.fileAreaIndex = fileAreaIndex;
		decoder = charset.newDecoder();
		byteBuffer = ByteBuffer.allocate(fileAreaIndex.getLargestAreaSize());
		charBuffer = CharBuffer.allocate(fileAreaIndex.getLargestAreaSize());
		open();
	}

	public FileLineDataSource(final File file, final FileLineIndexer<K> fileLineIndexer, final Charset charset)
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

	public String readLine(final K key) throws IOException {
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
}
