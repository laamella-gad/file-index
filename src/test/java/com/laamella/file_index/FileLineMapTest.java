package com.laamella.file_index;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import org.junit.Test;

public class FileLineMapTest {
	@Test public void test1FileMap() throws IOException {
		final URL test1Url = getClass().getResource("/test1.txt");
		final File file = new File(test1Url.getFile());
		final Charset utf8 = Charset.forName("UTF-8");
		final FileAreaIndex<Integer> index = new FileLineNumberIndexer().index(file, utf8);
		final FileLineMap<Integer> map = new FileLineMap<Integer>(file, index, utf8);
		assertEquals("1234567890" + (char) 10, map.get(1));
		assertEquals("" + (char) 10, map.get(2));
		assertEquals("ABCDEFGHIJ" + (char) 10, map.get(3));
		assertEquals("abcde" + (char) 10, map.get(4));
		assertEquals("line with \\r" + (char) 13, map.get(5));
		assertEquals("line with \\n" + (char) 10, map.get(6));
		assertEquals("line with \\r\\n" + (char) 13 + "" + (char) 10, map.get(7));
		assertEquals("ᐊᓕᒍᖅ ᓂᕆᔭᕌᖓᒃᑯ ᓱᕋᙱᑦᑐᓐᓇᖅᑐᖓ", map.get(8));
		assertNull(map.get(9));
	}
}
