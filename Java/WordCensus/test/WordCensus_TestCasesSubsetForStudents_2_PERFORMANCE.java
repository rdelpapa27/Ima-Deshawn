package test;

import static org.junit.Assert.assertEquals; 
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import census.WordCensus;
import census.WordCensusImpl_DelPapa;

//Using a fixed method order here because there is a non-trivial 
//performance penalty for the first test case method that is run.
//Therefore, we want warAndPeaceTest_A_60sec() to run before
//warAndPeaceTest_A_HalfSec() so that warAndPeaceTest_A_HalfSec()
//cannot possibly run first.

//IMPORTANT STUDENT NOTE: The safest thing to do is to run your code on a
//computer in the ACL. Realize that your computer might be significantly
//slower than the computer that I will ultimately run your computer on.
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WordCensus_TestCasesSubsetForStudents_2_PERFORMANCE extends WordCensus_TestCasesSubsetForStudents_1_ENVIRONMENT
{
	@Points(5)
	@Test(timeout=60000)
	public void warAndPeaceTest_A_60sec()
	{
		TEST_GOAL_MESSAGE = "Check whether the War and Peace test can run in under 60 seconds";
		warAndPeaceTest();
	}
	
	@Points(5)
	@Test(timeout=15000)
	public void warAndPeaceTest_B_15sec()
	{
		TEST_GOAL_MESSAGE = "Check whether the War and Peace test can run in under 15 seconds";
		warAndPeaceTest();
	}
	
	@Points(10)
	@Test(timeout=2000)
	public void warAndPeaceTest_C_2sec()
	{
		TEST_GOAL_MESSAGE = "Check whether the War and Peace test can run in under 2 seconds";
		warAndPeaceTest();
	}
	
	@Points(1)
	@Test(timeout=1000)
	public void warAndPeaceTest_D_1sec()
	{
		TEST_GOAL_MESSAGE = "Check whether the War and Peace test can run in under 1 seconds";
		warAndPeaceTest();
	}
	
	@Points(1)
	@Test(timeout=500)
	public void warAndPeaceTest_E_Halfsec()
	{
		TEST_GOAL_MESSAGE = "Check whether the War and Peace test can run in under 0.5 seconds";
		warAndPeaceTest();
	}
	
	public void warAndPeaceTest() {
		Scanner warAndPeaceScanner = null;
		try {
			warAndPeaceScanner = getWarAndPeaceScanner();
		} catch (IOException e) {
			fail("War and Peace not read!");
		}
		List<String> wordListLowercase = getWordListLowercase(warAndPeaceScanner);
		WordCensus warAndPeaceWordCensus = getWordCensus(wordListLowercase);
		assertEquals(288, warAndPeaceWordCensus.getCount("war"));
		assertEquals(109, warAndPeaceWordCensus.getCount("peace"));
		assertEquals(22063, warAndPeaceWordCensus.getCount("and"));
		assertEquals(10454, warAndPeaceWordCensus.getCount("a"));
		assertEquals(34341, warAndPeaceWordCensus.getCount("the"));
		assertEquals(0, warAndPeaceWordCensus.getCount("tomato"));
		assertEquals(1612, warAndPeaceWordCensus.getCount("an"));

		final int DISTINCT_WORDS_SIZE = warAndPeaceWordCensus.getDistinctWords().size();
		
		assertEquals(21642, DISTINCT_WORDS_SIZE);
		
		//THIS IS PART OF THE PERFORMANCE TESTING:
		for(int rank = 1; rank <= DISTINCT_WORDS_SIZE; rank++)
		{
			String word_rank = warAndPeaceWordCensus.getWordWithRank(rank);
			int count_rank = warAndPeaceWordCensus.getCount(word_rank);
		}
	}
	
	//********************** SUPPORT *******************************************************************************************
	//********************** SUPPORT *******************************************************************************************	
	protected static List<String> getWordListLowercase(Scanner wordScanner)
	{
		List<String> wordList = new ArrayList<String>();
		while(wordScanner.hasNext())
		{
			wordList.add(wordScanner.next().toLowerCase());
		}
		return wordList;
	}
	
	protected WordCensus getWordCensus(List<String> wordList)
	{
		return new WordCensusImpl_DelPapa(wordList);
	}
	
	protected Scanner getWarAndPeaceScanner() throws IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream("resources/WarAndPeace.txt");
		assert input != null : "input is null! : Check that the resources folder is on the classpath, the file name is correct, and the file is in the resources folder";
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
		Scanner warAndPeaceScanner = new Scanner(bufferedReader);
		warAndPeaceScanner.useDelimiter("(" + warAndPeaceScanner.delimiter().pattern() + "|[.!,?\"\'])+");
		return warAndPeaceScanner;
	}
}
