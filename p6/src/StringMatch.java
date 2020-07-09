/**
 * TODO #1
 * I wasn't able to start the Boyer-Moore algorithm
 */

public class StringMatch {

	/**
	 * Returns the result of running the naive algorithm to match pattern in text.
	 */
	public static Result matchNaive(String pattern, String text) {  
		int m = pattern.length();
		int n = text.length();
		int i = 0, comps = 0;
		while (i <= n - m) {
			int matchCount = 0;
			int j = 0;
			while (j < m) {
				if (pattern.charAt(j) != text.charAt(i + j))
					break;
				matchCount++;
				comps++;
				j++;
			}
			if (matchCount == m) {
				return new Result(i, comps);
			}
			comps++;
			i++;
		}
		return new Result(-1, comps);
	}

	/**
	 * Populates flink with the failure links for the KMP machine associated with the
	 * given pattern, and returns the cost in terms of the number of character comparisons.
	 */
	public static int buildKMP(String pattern, int[] flink) {
		int comps = 0, i = 2, m = pattern.length();
		flink[0] = -1;
		if (flink.length > 1) flink[1] = 0;
		
		while (i <= m) {
			int j = flink[i - 1];
			while (j != -1 && pattern.charAt(j) != pattern.charAt(i - 1)) {
				j = flink[j];
				comps++;
			}
			flink[i] = j + 1;
			i++;
		}
		return comps;
	}

	/**
	 * Returns the result of running the KMP machine specified by flink (built for the
	 * given pattern) on the text.
	 */
	public static Result runKMP(String pattern, String text, int[] flink) {
		int comps = 0;
		int j = -1, state = -1;
		while (true) {
			
			if (state == -1 || text.charAt(j) == pattern.charAt(state)) {
				state++;
				if (state == flink.length - 1) {
					//found
					return new Result(j - pattern.length() + 1, comps + 1);
				}
				j++;
				if (j == text.length()) {
					//not found
					return new Result(-1, comps);
				}
				comps++;
			}
			else {
				state = flink[state];
			}
		}  
	}

	/**
	 * Returns the result of running the KMP algorithm to match pattern in text. The number
	 * of comparisons includes the cost of building the machine from the pattern.
	 */
	public static Result matchKMP(String pattern, String text) {
		int m = pattern.length();
		int[] flink = new int[m + 1];
		int comps = buildKMP(pattern, flink);
		Result ans = runKMP(pattern, text, flink);
		return new Result(ans.pos, comps + ans.comps);
	}

	/**
	 * TODO
	 * 
	 * Populates delta1 with the shift values associated with each character in the
	 * alphabet. Assume delta1 is large enough to hold any ASCII value.
	 */
	public static void buildDelta1(String pattern, int[] delta1) {

	}

	/**
	 * TODO
	 * 
	 * Returns the result of running the simplified Boyer-Moore algorithm using the
	 * delta1 table from the pre-processing phase.
	 */
	public static Result runBoyerMoore(String pattern, String text, int[] delta1) {
		return new Result(-1, 0);
	}

	/**
	 * Returns the result of running the simplified Boyer-Moore algorithm to match 
	 * pattern in text. 
	 */
	public static Result matchBoyerMoore(String pattern, String text) {
		int[] delta1 = new int[Constants.SIGMA_SIZE];
		buildDelta1(pattern, delta1);
		return runBoyerMoore(pattern, text, delta1);
	}
	
	public static void main(String[] args) {
		Result r = matchNaive("BAAA", "AABAAA");
		System.out.println(r);
		Result re = matchNaive("yes", "no, yet yes");
		System.out.println(re);
		
		String m = "AABAACAABABA";
//		int[] a = new int[m.length() + 1];
//		buildKMP(m, a);
		
		Result res = matchKMP("CAA", m);
		System.out.println(res);
		
		Result resu = matchKMP("yes", "no, yet yes");
		System.out.println(resu);
		
		Result resul = matchKMP("BAAA", "AABAAA");
		System.out.println(r);
	}
}
