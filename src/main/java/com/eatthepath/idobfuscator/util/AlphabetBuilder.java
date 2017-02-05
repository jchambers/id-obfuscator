package com.eatthepath.idobfuscator.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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

    public AlphabetBuilder includeDigits() {
        this.includeDigits = true;
        return this;
    }

    public AlphabetBuilder includeUppercaseLatinLetters() {
        this.includeUppercaseLatinLetters = true;
        return this;
    }

    public AlphabetBuilder includeLowercaseLatinLetters() {
        this.includeLowercaseLatinLetters = true;
        return this;
    }

    public AlphabetBuilder includeUrlSafePunctuation() {
        this.includeUrlSafePunctuation = true;
        return this;
    }

    public AlphabetBuilder excludeVowels() {
        this.excludeVowels = true;
        return this;
    }

    public AlphabetBuilder excludeVisuallySimilarCharacters() {
        this.excludeVisuallySimilarCharacters = true;
        return this;
    }

    public AlphabetBuilder includeAdditionalCharacters(final Collection<Character> additionalCharacters) {
        this.additionalIncludedCharacters.addAll(additionalCharacters);
        return this;
    }

    public AlphabetBuilder includeAdditionalCharacters(final char... additionalCharacters) {
        for (final char c : additionalCharacters) {
            this.additionalIncludedCharacters.add(c);
        }

        return this;
    }

    public AlphabetBuilder excludeAdditionalCharacters(final Collection<Character> additionalCharacters) {
        this.additionalExcludedCharacters.addAll(additionalCharacters);
        return this;
    }

    public AlphabetBuilder excludeAdditionalCharacters(final char... additionalCharacters) {
        for (final char c : additionalCharacters) {
            this.additionalExcludedCharacters.add(c);
        }

        return this;
    }

    public AlphabetBuilder shuffleWithRandomSeed(final long randomSeed) {
        this.shuffleSeed = randomSeed;
        return this;
    }

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
