package census;

import java.util.*;

public class WordCensusImpl_DelPapa implements WordCensus {

    private final Map<String, Integer> wordMap;
    private final List<String> rankedWords;

    public WordCensusImpl_DelPapa(List<String> wordList) {
        assert wordList != null : "Input wordList is null!";

        wordMap = new HashMap<>();
        for (String word : wordList) {
            wordMap.put(word, wordMap.getOrDefault(word, 0) + 1);
        }

        // Sort words by frequency and alphabetically for tie-breaking
        rankedWords = new ArrayList<>(wordMap.keySet());
        rankedWords.sort((w1, w2) -> {
            int freqCompare = Integer.compare(wordMap.get(w2), wordMap.get(w1));
            return (freqCompare != 0) ? freqCompare : w1.compareTo(w2);
        });
    }

    @Override
    public int getCount(String word) {
        return wordMap.getOrDefault(word, 0);
    }

    @Override
    public Set<String> getDistinctWords() {
        return wordMap.keySet();
    }

    @Override
    public String getWordWithRank(int rank) {
        assert rank > 0 : "Rank must be greater than 0!";
        return (rank <= rankedWords.size()) ? rankedWords.get(rank - 1) : null;
    }
}
