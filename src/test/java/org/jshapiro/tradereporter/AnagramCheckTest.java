package org.jshapiro.tradereporter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnagramCheckTest {

    @Test
    void isAnagramShouldReturnTrue() {
        assertTrue(new AnagramCheck().isAnagram("ABc", "Cab"));
        assertTrue(new AnagramCheck().isAnagram("A Bc", "C a b"));
    }

    @Test
    void isAnagramShouldReturnFalse() {
        assertFalse(new AnagramCheck().isAnagram("ABd", "Cab"));
        assertFalse(new AnagramCheck().isAnagram("ABCD", "Cab"));
    }
}