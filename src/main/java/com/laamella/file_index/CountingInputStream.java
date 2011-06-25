package com.laamella.file_index;

import java.io.IOException;
import java.io.InputStream;

public class CountingInputStream extends InputStream {
	private final InputStream wrappedInputStream;
	private long position;

	public CountingInputStream(final InputStream wrappedInputStream) {
		this.wrappedInputStream = wrappedInputStream;
		this.position = 0;
	}

	public long getPosition() {
		return position;
	}

	@Override public int read() throws IOException {
		position++;
		return wrappedInputStream.read();
	}

}
