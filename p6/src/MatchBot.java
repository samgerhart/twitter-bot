import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;
import twitter4j.TwitterException;

/**
 * TODO #2
 */

public class MatchBot extends TwitterBot {
	List<String> ansKMP = new ArrayList<>();
	/**
	 * Constructs a MatchBot to operate on the last numTweets of the given user.
	 */
	public MatchBot(String user, int numTweets) {
		super(user, numTweets);
	}

	/**
	 * Employs the KMP string matching algorithm to add all tweets containing 
	 * the given pattern to the provided list. Returns the total number of 
	 * character comparisons performed.
	 */
	public int searchTweetsKMP(String pattern, List<String> ans) {
		int comps = 0;
		for (String s : tweets) {
			Result r = StringMatch.matchKMP(pattern, s);
			if (r.pos > -1) {
				ans.add(s);
			}
			comps += r.comps;
		}
		
		return comps;
	}

	/**
	 * Employs the naive string matching algorithm to find all tweets containing 
	 * the given pattern to the provided list. Returns the total number of 
	 * character comparisons performed.
	 */
	public int searchTweetsNaive(String pattern, List<String> ans) {
		int comps = 0;
		for (String s : tweets) {
			Result r = StringMatch.matchNaive(pattern, s);
			if (r.pos != -1) {
				ans.add(s);
			}
			comps += r.comps;
		}
		return comps;
	}
	
	/**
	 * searches the tweets using Boyer-Moore algorithm 
	 * that sadly isn't implemented 
	 */
	public int searchTweetsBoyerMoore(String pattern, List<String> ans) {
		int comps = 0;
		for (String s : tweets) {
			Result r = StringMatch.matchBoyerMoore(pattern, s);
			if (r.pos != -1) {
				ans.add(s);
			}
			comps += r.comps;
		}
		return comps;
	}

	public static void main(String... args) {
		
		String handle = "Ecosia", pattern = "trees";
		MatchBot bot = new MatchBot(handle, 2000);

		// Search all tweets for the pattern.
		List<String> ansNaive = new ArrayList<>();
		int compsNaive = bot.searchTweetsNaive(pattern, ansNaive); 
		List<String> ansKMP = new ArrayList<>();
		int compsKMP = bot.searchTweetsKMP(pattern, ansKMP);  
		List<String> ansBoyerMoore = new ArrayList<>();
		int compsBoyerMoore = bot.searchTweetsBoyerMoore(pattern, ansBoyerMoore);

		System.out.println("naive comps = " + compsNaive + 
				", KMP comps = " + compsKMP +
				", Boyer-Moore comps = " + compsBoyerMoore);

		for (int i = 0; i < ansKMP.size(); i++) {
			String tweet = ansKMP.get(i);
			assert tweet.equals(ansNaive.get(i));
			System.out.println(i++ + ". " + tweet);
			System.out.println(pattern + " appears at index " + 
					tweet.toLowerCase().indexOf(pattern.toLowerCase()));
		}

		// Do something similar for the Boyer-Moore matching algorithm.

	}
}
