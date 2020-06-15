package com.laamella.file_index;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FileIndexTest {
    @Test
    public void indexOneItem() {
        final FileAreaIndex<String> index = new FileAreaIndex<>();
        index.index("aap", new FileArea(20, 10));
        final FileArea area = index.get("aap");
        assertEquals(20, area.startPosition);
        assertEquals(10, area.size);
    }

    @Test
    public void indexTwoItems() {
        final FileAreaIndex<String> index = new FileAreaIndex<>();
        index.index("aap", new FileArea(20, 10));
        index.index("noot", new FileArea(40, 30));
        final FileArea area = index.get("aap");
        assertEquals(20, area.startPosition);
        assertEquals(10, area.size);
        final FileArea area2 = index.get("noot");
        assertEquals(40, area2.startPosition);
        assertEquals(30, area2.size);
    }

    @Test
    public void contains() {
        final FileAreaIndex<String> index = new FileAreaIndex<>();
        index.index("aap", new FileArea(20, 10));
        assertTrue(index.contains("aap"));
        assertFalse(index.contains("rwigjrwigj"));
    }

}
