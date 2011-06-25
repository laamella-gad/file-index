package com.laamella.file_index;

class FileLineNumberIndexer extends FileLineIndexer<Integer> {
	private int key = 0;

	@Override protected Integer createKey(final FileArea fileArea, final StringBuffer line) {
		key++;
		System.out.println(fileArea + " " + line.toString().trim());
		for (int i = 0; i < line.length(); i++) {
			System.out.print(" " + (int) line.charAt(i));
		}
		System.out.println();
		return key;
	}
}