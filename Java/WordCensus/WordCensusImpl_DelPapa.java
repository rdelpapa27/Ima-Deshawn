package census;

import java.util.*;

public class WordCensusImpl_DelPapa implements WordCensus{

	private final Map<String, Integer> wordMap; // Map to store word counts

	public WordCensusImpl_DelPapa(List<String> wordList) {
		
		assert wordList != null : "Input wordList is null!";
		
		wordMap = new HashMap<>();
		
		// Populate wordMap with word counts
		
		for (String word : wordList) {
			
			wordMap.put(word, wordMap.getOrDefault(word, 0) + 1);
		}
	}

	// Get count of a specific word
	
	public int getCount(String word) {
		
		return wordMap.getOrDefault(word, 0);
	}

	// Get distinct words
	
	public Set<String> getDistinctWords() {
		
		return wordMap.keySet();
	}

	// Get word with specified rank based on frequency
	
	public String getWordWithRank(int rank) {
		
		assert rank > 0 : "Rank must be greater than 0!";
		
		List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(wordMap.entrySet());
		
		// Sort entries by count in descending order
		
		sortedEntries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
		
		List<String> rankedWords = new ArrayList<>();
		
		int lastCount = -1;
		
		int repeats = 0;
		
		// Iterate through sorted entries to find word with specified rank
		
		for (Map.Entry<String, Integer> entry : sortedEntries) {
			
			if (entry.getValue() != lastCount) {
				
				rankedWords.add(entry.getKey());
			
			} else if (repeats == 0) {
				
				// Handle words with same count
				
				List<String> sameCountWords = getSameCountWords(sortedEntries, entry.getValue(), rankedWords.size());
				
				Collections.sort(sameCountWords);
				
				rankedWords.addAll(sameCountWords);
				
				repeats = sameCountWords.size();
			
			} else {
				
				repeats--;
			}
			
			lastCount = entry.getValue();
		}
		
		// Return word at specified rank
		
		return (rank <= rankedWords.size()) ? rankedWords.get(rank - 1) : null;
	}

	// Get words with the same count as specified word
	
	private List<String> getSameCountWords(List<Map.Entry<String, Integer>> sortedEntries, int count, int startIndex) {
		
		List<String> sameCountWords = new ArrayList<>();
		
		// Iterate through sorted entries to find words with the same count
		
		for (int i = startIndex; i < sortedEntries.size(); i++) {
			
			if (sortedEntries.get(i).getValue() == count) {
				
				sameCountWords.add(sortedEntries.get(i).getKey());
			}
		}
		
		return sameCountWords;
	}
}
