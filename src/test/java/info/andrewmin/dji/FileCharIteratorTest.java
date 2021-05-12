package info.andrewmin.dji;

import info.andrewmin.dji.lexer.FileChar;
import info.andrewmin.dji.lexer.FileCharIterator;
import info.andrewmin.dji.lexer.FileLoc;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class FileCharIteratorTest {

    @Test
    public void hasNext_BlankString_False() {
        Iterator<FileChar> f = new FileCharIterator("");
        assertFalse(f.hasNext());
    }

    @Test
    public void hasNext_NotBlankString_True() {
        String s = "abc";
        Iterator<FileChar> f = new FileCharIterator(s);
        for (int i = 0; i < s.length(); i++) {
            assertTrue(f.hasNext());
            f.next();
        }
        assertFalse(f.hasNext());
    }

    @Test
    public void next_FileLoc_Correct() {
        String s = "ab\nc";
        Iterator<FileChar> f = new FileCharIterator(s);
        assertEquals(new FileLoc(1, 1), f.next().getLoc());
        assertEquals(new FileLoc(1, 2), f.next().getLoc());
        assertEquals(new FileLoc(1, 3), f.next().getLoc());
        assertEquals(new FileLoc(2, 1), f.next().getLoc());
    }

}
