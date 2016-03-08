package com.laamella.file_index;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import org.junit.Test;

public class FileLineIndexerTest {
	@Test public void test1FileAreas() throws IOException {
		final URL test1Url = getClass().getResource("/test1.txt");
		final File file = new File(test1Url.getFile());
		final FileAreaIndex<Integer> index = new FileLineIndexer<>(new IncrementalKeyFactory()).index(file, Charset.forName("UTF-8"));
		assertEquals(0, index.get(1).startPosition);
		assertEquals(11, index.get(2).startPosition);
		assertEquals(12, index.get(3).startPosition);
		assertEquals(23, index.get(4).startPosition);
		assertEquals(29, index.get(5).startPosition);
		assertEquals(42, index.get(6).startPosition);
		assertEquals(55, index.get(7).startPosition);
		assertEquals(71, index.get(8).startPosition);
		assertEquals(11, index.get(1).size);
		assertEquals(1, index.get(2).size);
		assertEquals(11, index.get(3).size);
		assertEquals(6, index.get(4).size);
		assertEquals(13, index.get(5).size);
		assertEquals(13, index.get(6).size);
		assertEquals(16, index.get(7).size);
		assertEquals(65, index.get(8).size);
		assertFalse(index.contains(9));
	}
}
