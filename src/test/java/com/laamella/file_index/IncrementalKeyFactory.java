package com.laamella.file_index;

class IncrementalKeyFactory implements FileLineIndexer.KeyFactory<Integer> {
	private int key = 0;

	@Override public Integer createKey(final FileArea fileArea, final StringBuffer line) {
		key++;
		System.out.println(fileArea + " " + line.toString().trim());
		for (int i = 0; i < line.length(); i++) {
			System.out.print(" " + (int) line.charAt(i));
		}
		System.out.println();
		return key;
	}
}