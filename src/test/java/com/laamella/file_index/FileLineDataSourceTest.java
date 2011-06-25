package com.laamella.file_index;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import org.junit.Test;

public class FileLineDataSourceTest {
	@Test public void test1FileAreas() throws IOException {
		final URL test1Url = getClass().getResource("/test1.txt");
		final File file = new File(test1Url.getFile());
		final Charset utf8 = Charset.forName("UTF-8");
		final FileAreaIndex<Integer> index = new FileLineNumberIndexer().index(file, utf8);
		final FileLineDataSource<Integer> dataSource = new FileLineDataSource<Integer>(file, index, utf8);
		assertEquals("1234567890" + (char) 10, dataSource.readLine(1));
		assertEquals("" + (char) 10, dataSource.readLine(2));
		assertEquals("ABCDEFGHIJ" + (char) 10, dataSource.readLine(3));
		assertEquals("abcde" + (char) 10, dataSource.readLine(4));
		assertEquals("line with \\r" + (char) 13, dataSource.readLine(5));
		assertEquals("line with \\n" + (char) 10, dataSource.readLine(6));
		assertEquals("line with \\r\\n" + (char) 13 + "" + (char) 10, dataSource.readLine(7));
		assertNull(dataSource.readLine(8));
	}
}
