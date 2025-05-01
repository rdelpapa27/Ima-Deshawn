package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import census.WordCensus;

public class WordCensus_TestCasesWrittenByStudent_3_DelPapa extends WordCensus_TestCasesSubsetForStudents_2_PERFORMANCE {

    private static List<String> makeList(String... words) {
        return Arrays.asList(words);
    }

    @Test
    public void testEmptyList() {
        WordCensus wc = getWordCensus(Collections.emptyList());
        assertEquals(0, wc.getCount("hello"));
        assertTrue(wc.getDistinctWords().isEmpty());
    }

    @Test
    public void testSingleWord() {
        WordCensus wc = getWordCensus(makeList("apple"));
        assertEquals(1, wc.getCount("apple"));
        assertEquals(Set.of("apple"), wc.getDistinctWords());
        assertEquals("apple", wc.getWordWithRank(1));
    }

    @Test
    public void testMultipleSameCountAlphabeticalOrder() {
        WordCensus wc = getWordCensus(makeList("dog", "cat", "bat"));
        // All words appear once, should be ranked alphabetically
        assertEquals("bat", wc.getWordWithRank(1));
        assertEquals("cat", wc.getWordWithRank(2));
        assertEquals("dog", wc.getWordWithRank(3));
    }

    @Test
    public void testFrequencyOrder() {
        WordCensus wc = getWordCensus(makeList("a", "a", "b", "c", "c", "c"));
        assertEquals("c", wc.getWordWithRank(1)); // 3 times
        assertEquals("a", wc.getWordWithRank(2)); // 2 times
        assertEquals("b", wc.getWordWithRank(3)); // 1 time
    }

    @Test
    public void testRankOutOfBounds() {
        WordCensus wc = getWordCensus(makeList("hello", "world"));
        assertEquals(null, wc.getWordWithRank(3)); // Only 2 distinct words
    }
}
