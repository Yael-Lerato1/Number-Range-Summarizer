package numberrangesummarizer;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NumberRangeSummarizerTest {

    private NumberRangeSummarizer summarizer;

    @BeforeEach
    void setUp() {
        summarizer = new NumberRangeSummarizerImpl();
    }

    // collect() tests

    @Test
    void collect_nullInput_returnsEmptyCollection() {
        assertTrue(summarizer.collect(null).isEmpty());
    }

    @Test
    void collect_blankInput_returnsEmptyCollection() {
        assertTrue(summarizer.collect("   ").isEmpty());
    }

    @Test
    void collect_standardInput_returnsCorrectNumbers() {
        Collection<Integer> result = summarizer.collect("1,3,6,7,8");
        assertEquals(List.of(1, 3, 6, 7, 8), result);
    }

    @Test
    void collect_inputWithSpaces_trimsAndParses() {
        Collection<Integer> result = summarizer.collect("1, 2, 3");
        assertEquals(List.of(1, 2, 3), result);
    }

    @Test
    void collect_unsortedInput_returnsSortedCollection() {
        Collection<Integer> result = summarizer.collect("5,3,1,4,2");
        assertEquals(List.of(1, 2, 3, 4, 5), result);
    }

    // summarizeCollection() tests

    @Test
    void summarize_nullInput_returnsEmptyString() {
        assertEquals("", summarizer.summarizeCollection(null));
    }

    @Test
    void summarize_emptyCollection_returnsEmptyString() {
        assertEquals("", summarizer.summarizeCollection(Collections.emptyList()));
    }

    @Test
    void summarize_singleElement_returnsJustThatNumber() {
        assertEquals("5", summarizer.summarizeCollection(List.of(5)));
    }

    @Test
    void summarize_noConsecutiveNumbers_returnsAllSingles() {
        assertEquals("1, 3, 5, 7", summarizer.summarizeCollection(List.of(1, 3, 5, 7)));
    }

    @Test
    void summarize_allConsecutive_returnsOneRange() {
        assertEquals("1-5", summarizer.summarizeCollection(List.of(1, 2, 3, 4, 5)));
    }

    @Test
    void summarize_twoConsecutiveNumbers_returnsRange() {
        assertEquals("1-2", summarizer.summarizeCollection(List.of(1, 2)));
    }

    @Test
    void summarize_mixedInput_returnsRangesAndSingles() {
        assertEquals("1, 3, 6-8, 12-15, 21-24, 31",
                summarizer.summarizeCollection(List.of(1, 3, 6, 7, 8, 12, 13, 14, 15, 21, 22, 23, 24, 31)));
    }

    @Test
    void summarize_negativeNumbers_handledCorrectly() {
        assertEquals("-3--1, 2-4",
                summarizer.summarizeCollection(Arrays.asList(-3, -2, -1, 2, 3, 4)));
    }

    // end-to-end tests (whole program tests)

    @Test
    void endToEnd_sampleFromInterface_matchesExpectedOutput() {
        Collection<Integer> collected = summarizer.collect("1,3,6,7,8,12,13,14,15,21,22,23,24,31");
        assertEquals("1, 3, 6-8, 12-15, 21-24, 31", summarizer.summarizeCollection(collected));
    }

    @Test
    void endToEnd_inputWithSpaces_summarizedCorrectly() {
        Collection<Integer> collected = summarizer.collect("1, 2, 3, 5, 6");
        assertEquals("1-3, 5-6", summarizer.summarizeCollection(collected));
    }
}