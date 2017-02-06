package com.eatthepath.idobfuscator.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Constructs alphabets for use with a {@link com.eatthepath.idobfuscator.AlphabetCodec}. Alphabet builders are not
 * thread-safe, and should generally be considered "single-shot" tools for constructing
 * {@link com.eatthepath.idobfuscator.AlphabetCodec} instances.
 *
 * @author <a href="https://github.com/jchambers">Jon Chambers</a>
 */
public class AlphabetBuilder {
    private static final List<Character> DIGITS = getCharacterListFromString("0123456789");
    private static final List<Character> UPPERCASE_LATIN_LETTERS = getCharacterListFromString("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    private static final List<Character> LOWERCASE_LATIN_LETTERS = getCharacterListFromString("abcdefghijklmnopqrstuvwxyz");
    private static final List<Character> URL_SAFE_PUNCTUATION = getCharacterListFromString("$-_.!*'(),");

    private static final List<Character> VOWELS = getCharacterListFromString("AEIOUaeiou");
    private static final List<Character> VISUALLY_SIMILAR_CHARACTERS = getCharacterListFromString("0Oo1Il5S6G");

    private boolean includeUppercaseLatinLetters;
    private boolean includeLowercaseLatinLetters;
    private boolean includeDigits;
    private boolean includeUrlSafePunctuation;

    private boolean excludeVowels;
    private boolean excludeVisuallySimilarCharacters;

    private List<Character> additionalIncludedCharacters = new ArrayList<>();
    private List<Character> additionalExcludedCharacters = new ArrayList<>();

    private Long shuffleSeed;

    /**
     * Includes decimal digits (0-9) in the alphabet under construction.
     *
     * @return a reference to this alphabet builder
     */
    public AlphabetBuilder includeDigits() {
        this.includeDigits = true;
        return this;
    }

    /**
     * Includes uppercase Latin letters (A-Z) in the alphabet under construction.
     *
     * @return a reference to this alphabet builder
     */
    public AlphabetBuilder includeUppercaseLatinLetters() {
        this.includeUppercaseLatinLetters = true;
        return this;
    }

    /**
     * Includes lowercase Latin letters (A-Z) in the alphabet under construction.
     *
     * @return a reference to this alphabet builder
     */
    public AlphabetBuilder includeLowercaseLatinLetters() {
        this.includeLowercaseLatinLetters = true;
        return this;
    }

    /**
     * Includes URL-safe punctuation characters ({@code $-_.!*'(),}) in the alphabet under construction.
     *
     * @return a reference to this alphabet builder
     */
    public AlphabetBuilder includeUrlSafePunctuation() {
        this.includeUrlSafePunctuation = true;
        return this;
    }

    /**
     * Excludes uppercase and lowercase vowels (a, e, i, o, and u) from the the alphabet under construction. This can
     * be helpful for preventing dictionary words (and especially profanity) from appearing in obfuscated identifiers.
     *
     * @return a reference to this alphabet builder
     */
    public AlphabetBuilder excludeVowels() {
        this.excludeVowels = true;
        return this;
    }

    /**
     * Excludes characters that are visually similar to other Latin letters or decimal digits (for example, O and 0 or
     * l, I and 1) from the alphabet under construction. This can be especially helpful in circumstances when obfuscated
     * identifiers may be transcribed by hand or communicated verbally.
     *
     * @return a reference to this alphabet builder
     */
    public AlphabetBuilder excludeVisuallySimilarCharacters() {
        this.excludeVisuallySimilarCharacters = true;
        return this;
    }

    /**
     * Includes the given collection of additional characters in the alphabet under construction. Care should be taken
     * not to include characters that may have already been added by other means (e.g. uppercase Latin letters).
     *
     * @param additionalCharacters a collection of characters to include in the alphabet under construction
     *
     * @return a reference to this alphabet builder
     */
    public AlphabetBuilder includeAdditionalCharacters(final Collection<Character> additionalCharacters) {
        this.additionalIncludedCharacters.addAll(additionalCharacters);
        return this;
    }

    /**
     * Includes the given additional characters in the alphabet under construction. Care should be taken not to include
     * characters that may have already been added by other means (e.g. uppercase Latin letters).
     *
     * @param additionalCharacters the characters to include in the alphabet under construction
     *
     * @return a reference to this alphabet builder
     */
    public AlphabetBuilder includeAdditionalCharacters(final char... additionalCharacters) {
        for (final char c : additionalCharacters) {
            this.additionalIncludedCharacters.add(c);
        }

        return this;
    }

    /**
     * Excludes the given collection of characters from the alphabet under construction.
     *
     * @param additionalCharacters a collection of characters to exclude from the alphabet under construction
     *
     * @return a reference to this alphabet builder
     */
    public AlphabetBuilder excludeAdditionalCharacters(final Collection<Character> additionalCharacters) {
        this.additionalExcludedCharacters.addAll(additionalCharacters);
        return this;
    }

    /**
     * Excludes the given characters from the alphabet under construction.
     *
     * @param additionalCharacters a collection of characters to exclude from the alphabet under construction
     *
     * @return a reference to this alphabet builder
     */
    public AlphabetBuilder excludeAdditionalCharacters(final char... additionalCharacters) {
        for (final char c : additionalCharacters) {
            this.additionalExcludedCharacters.add(c);
        }

        return this;
    }

    /**
     * Sets the random seed that will be used to randomize the order of characters in the alphabet under construction.
     * If not specified, the alphabet under construction will not be shuffled.
     *
     * @param randomSeed the random seed to use for shuffling the characters in the alphabet under construction
     *
     * @return a reference to this alphabet builder
     */
    public AlphabetBuilder shuffleWithRandomSeed(final long randomSeed) {
        this.shuffleSeed = randomSeed;
        return this;
    }

    /**
     * Constructs an alphabet with the characters that have been included, less the characters that have been excluded,
     * by this builder. If a random seed was provided, the order of the characters in the returned alphabet will be
     * randomized.
     *
     * @return an array of characters in the alphabet constructed by this builder
     */
    public char[] build() {
        final List<Character> alphabetList = new ArrayList<>();

        if (this.includeDigits) {
            alphabetList.addAll(DIGITS);
        }

        if (this.includeUppercaseLatinLetters) {
            alphabetList.addAll(UPPERCASE_LATIN_LETTERS);
        }

        if (this.includeLowercaseLatinLetters) {
            alphabetList.addAll(LOWERCASE_LATIN_LETTERS);
        }

        if (this.includeUrlSafePunctuation) {
            alphabetList.addAll(URL_SAFE_PUNCTUATION);
        }

        alphabetList.addAll(this.additionalIncludedCharacters);

        if (this.excludeVowels) {
            alphabetList.removeAll(VOWELS);
        }

        if (this.excludeVisuallySimilarCharacters) {
            alphabetList.removeAll(VISUALLY_SIMILAR_CHARACTERS);
        }

        alphabetList.removeAll(this.additionalExcludedCharacters);

        if (this.shuffleSeed != null) {
            final Random random = new Random(this.shuffleSeed);
            Collections.shuffle(alphabetList, random);
        }

        final char[] alphabetArray = new char[alphabetList.size()];

        for (int i = 0; i < alphabetList.size(); i++) {
            alphabetArray[i] = alphabetList.get(i);
        }

        return alphabetArray;
    }

    private static List<Character> getCharacterListFromString(final String string) {
        final char[] characterArray = string.toCharArray();
        final List<Character> characterList = new ArrayList<>();

        for (final char c : characterArray) {
            characterList.add(c);
        }

        return characterList;
    }
}
