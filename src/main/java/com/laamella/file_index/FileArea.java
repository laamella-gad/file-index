package com.laamella.file_index;

public class FileArea {
	public final long startPosition;
	public final int size;

	public FileArea(final long startPosition, final int size) {
		this.startPosition = startPosition;
		this.size = size;
	}

	@Override public String toString() {
		return "FileArea[" + startPosition + "," + size + "]";
	}
}
