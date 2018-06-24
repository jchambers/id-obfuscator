package com.eatthepath.idobfuscator.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class AlphabetBuilderTest {

    @Test
    public void testIncludeDigits() {
        final char[] alphabet = new AlphabetBuilder().includeDigits().build();

        assertEquals(10, alphabet.length);
        assertAlphabetContainsAllCharactersInString(alphabet, "0123456789");
    }

    @Test
    public void testIncludeUppercaseLatinLetters() {
        final char[] alphabet = new AlphabetBuilder().includeUppercaseLatinLetters().build();

        assertEquals(26, alphabet.length);
        assertAlphabetContainsAllCharactersInString(alphabet, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

    @Test
    public void testIncludeLowercaseLatinLetters() {
        final char[] alphabet = new AlphabetBuilder().includeLowercaseLatinLetters().build();

        assertEquals(26, alphabet.length);
        assertAlphabetContainsAllCharactersInString(alphabet, "abcdefghijklmnopqrstuvwxyz");
    }

    @Test
    public void testIncludeUrlSafePunctuation() {
        final char[] alphabet = new AlphabetBuilder().includeUrlSafePunctuation().build();

        assertEquals(10, alphabet.length);
        assertAlphabetContainsAllCharactersInString(alphabet, "$-_.!*'(),");
    }

    @Test
    public void testExcludeVowels() {
        final char[] alphabet = new AlphabetBuilder()
                .includeUppercaseLatinLetters()
                .includeLowercaseLatinLetters()
                .excludeVowels()
                .build();

        assertEquals(42, alphabet.length);
        assertAlphabetContainsAllCharactersInString(alphabet, "BCDFGHJKLMNPQRSTVWXYZbcdfghjklmnpqrstvwxyz");
    }

    @Test
    public void testExcludeVisuallySimilarCharacters() {
        final char[] alphabet = new AlphabetBuilder()
                .includeUppercaseLatinLetters()
                .includeLowercaseLatinLetters()
                .includeDigits()
                .excludeVisuallySimilarCharacters()
                .build();

        assertEquals(52, alphabet.length);
        assertAlphabetContainsAllCharactersInString(alphabet, "234789ABCDEFHJKLMNPQRTUVWXYZabcdefghijkmnpqrstuvwxyz");
    }

    @Test
    public void testIncludeAdditionalCharactersCollectionOfCharacter() {
        final char[] alphabet = new AlphabetBuilder()
                .includeAdditionalCharacters(Arrays.asList('a', 'b', 'c'))
                .build();

        assertEquals(3, alphabet.length);
        assertAlphabetContainsAllCharactersInString(alphabet, "abc");
    }

    @Test
    public void testIncludeAdditionalCharactersCharArray() {
        final char[] alphabet = new AlphabetBuilder()
                .includeAdditionalCharacters('a', 'b', 'c')
                .build();

        assertEquals(3, alphabet.length);
        assertAlphabetContainsAllCharactersInString(alphabet, "abc");
    }

    @Test
    public void testExcludeAdditionalCharactersCollectionOfCharacter() {
        final char[] alphabet = new AlphabetBuilder()
                .includeLowercaseLatinLetters()
                .excludeAdditionalCharacters(Arrays.asList('a', 'b', 'c'))
                .build();

        assertEquals(23, alphabet.length);
        assertAlphabetContainsAllCharactersInString(alphabet, "defghijklmnopqrstuvwxyz");
    }

    @Test
    public void testExcludeAdditionalCharactersCharArray() {
        final char[] alphabet = new AlphabetBuilder()
                .includeLowercaseLatinLetters()
                .excludeAdditionalCharacters('a', 'b', 'c')
                .build();

        assertEquals(23, alphabet.length);
        assertAlphabetContainsAllCharactersInString(alphabet, "defghijklmnopqrstuvwxyz");
    }

    @Test
    public void testShuffleWithRandomSeed() {
        final char[] unshuffledAlphabet = new AlphabetBuilder()
                .includeLowercaseLatinLetters()
                .build();

        final char[] shuffledAlphabet = new AlphabetBuilder()
                .includeLowercaseLatinLetters()
                .shuffleWithRandomSeed(85733487)
                .build();

        assertEquals(unshuffledAlphabet.length, shuffledAlphabet.length);
        assertFalse(Arrays.equals(unshuffledAlphabet, shuffledAlphabet));

        final Set<Character> unshuffledSet = new HashSet<>();

        for (final char c : unshuffledAlphabet) {
            unshuffledSet.add(c);
        }

        final Set<Character> shuffledSet = new HashSet<>();

        for (final char c : shuffledAlphabet) {
            shuffledSet.add(c);
        }

        assertEquals(unshuffledSet, shuffledSet);
    }

    private static void assertAlphabetContainsAllCharactersInString(final char[] alphabet, final String string) {
        for (final char c : alphabet) {
            assertTrue(string.contains(new String(new char[] { c })));
        }
    }
}
