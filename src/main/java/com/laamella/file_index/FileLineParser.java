package com.laamella.file_index;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;

/**
 * Utility class that reads all lines from a file.
 * Not thread safe.
 */
class FileLineParser {
	private long position = 0;
	private long startPosition = 0;
	private boolean gotCarriageReturn = false;
	private boolean gotNewLine = false;
	private StringBuffer line = new StringBuffer();
	private LineConsumer lineConsumer;

	public void parse(final File file, final Charset charset, final LineConsumer lineConsumer) throws IOException {
		this.lineConsumer = lineConsumer;
		final FileInputStream fileInputStream = new FileInputStream(file);
		try {
			final FileChannel channel = fileInputStream.getChannel();
			try {
				final MappedByteBuffer map = channel.map(MapMode.READ_ONLY, 0, channel.size());
				final CharsetDecoder decoder = charset.newDecoder();
				final CharBuffer out = CharBuffer.allocate(4096);
				CoderResult result;
				do {
					result = decoder.decode(map, out, false);
					out.flip();
					while (out.hasRemaining()) {
						final char c = out.get();
						if (gotNewLine || (c != '\n' && gotCarriageReturn)) {
							gotNewLine = false;
							gotCarriageReturn = false;
							lineDone();
						}
						if (c == '\r') {
							gotCarriageReturn = true;
						}
						if (c == '\n') {
							gotNewLine = true;
						}
						line.append(c);
						position += byteSizeOfCharacter(c, charset);
					}
					out.clear();
				} while (result.isOverflow());
				lineDone();
			} finally {
				channel.close();
			}
		} finally {
			fileInputStream.close();
		}
	}

	private int byteSizeOfCharacter(final char c, final Charset charset) {
		// TODO inefficient
		return ("" + c).getBytes(charset).length;
	}

	private void lineDone() {
		lineConsumer.consume(startPosition, (int) (position - startPosition), line);
		line = new StringBuffer();
		startPosition = position;
	}

	public interface LineConsumer {
		void consume(long startPosition, int size, StringBuffer line);
	}
}
