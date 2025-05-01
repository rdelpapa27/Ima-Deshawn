package census;

import java.util.Set;

public interface WordCensus {
	//pre: nothing
	//part of post: rv >= 0
	public int getCount(String word);
	//part of pre: rank >= 1
	//part of pre: rank <= getDistinctWords().size()
	//part of post: rank < getDistinctWords().size() ==> getCount(getWordWithRank(rank)) >= getCount(getWordWithRank(rank + 1))
	//part of post: [((1 <= i < j <= getDistinctWords().size()) && (word_i.equals(getWordWithRank(i))) && (word_j.equals(getWordWithRank(j)))
	         // && (getCount(word_i) == getCount(word_j))] ==> (getWordWithRank(i) < getWordWithRank(j))
	public String getWordWithRank(int rank);
	//part of post: rv != null
	//part of post: !rv.contains(null)
	public Set<String> getDistinctWords();
}
