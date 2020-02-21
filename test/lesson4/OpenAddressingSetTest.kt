package lesson4

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag

class OpenAddressingSetTest {

    @Test
    @Tag("Example")
    fun add() {
        val set = OpenAddressingSet<String>(16)
        assertTrue(set.isEmpty())
        set.add("Alpha")
        set.add("Beta")
        set.add("Omega")
        assertSame(3, set.size)
        assertTrue("Beta" in set)
        assertFalse("Gamma" in set)
        assertTrue("Omega" in set)
    }

    @Test
    fun contains() {
        val set = OpenAddressingSet<String>(16)
        assertTrue(set.isEmpty())
        set.add("Alpha")
        set.add("Beta")
        set.add("Omega")
        assertSame(3, set.size)
        assertTrue("Beta" in set)
        assertFalse("Gamma" in set)
        assertTrue("Omega" in set)

    }

    @Test
    fun remove() {
        val set = OpenAddressingSet<String>(16)
        set.add("Alpha")
        set.add("Beta")
        set.add("Omega")
        set.remove("Omega")
        set.remove("Alpha")
        assertSame(1, set.size)
        assertTrue("Beta" in set)
        assertFalse("Alpha" in set)
        assertFalse("Omega" in set)
        set.remove("Beta")
        assertTrue(set.isEmpty())
    }

    @Test
    fun iterator() {
        val set = OpenAddressingSet<String>(16)
        set.add("A")
        set.add("B")
        set.add("C")
        set.add("D")
        var it = set.iterator()
        var count = 0
        val expected = arrayOf("A", "B", "C", "D")
        val actual = arrayOfNulls<String>(4)
        while (it.hasNext()) {
            actual[count] = it.next()
            count++
        }
        assertEquals(4, count)
        assertArrayEquals(expected, actual)
        it = set.iterator()
        while (it.hasNext()) {
            it.remove()
        }
        assertTrue(set.isEmpty())
    }

    @Test
    fun toStringTest() {
        val set = OpenAddressingSet<String>(16)
        set.add("A")
        set.add("B")
        set.add("C")
        set.add("D")
        set.remove("D")
        val actual = "[ A, B, C ]"
        assertEquals(set.toString(), actual)
    }

}