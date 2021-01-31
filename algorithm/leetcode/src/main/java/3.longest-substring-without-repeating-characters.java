import java.util.logging.Logger;
import java.util.HashMap;

/*
 * @lc app=leetcode id=3 lang=java
 *
 * [3] Longest Substring Without Repeating Characters
 */

// @lc code=start
class LengthOfLongestSubstringSolution {
    public int lengthOfLongestSubstring(String s) {
        return lengthOfLongestSubstringSolution2(s);
    }

    private static final Logger LOGGER = Logger.getLogger(LengthOfLongestSubstringSolution.class.getName());

    public static void main(String[] args) {
        try {
            LengthOfLongestSubstringSolution s = new LengthOfLongestSubstringSolution();

            if (3 != s.lengthOfLongestSubstring("abcabcbb")) {
                throw new AssertionError();
            }

            if (1 != s.lengthOfLongestSubstring("bbbbb")) {
                throw new AssertionError();
            }

            if (3 != s.lengthOfLongestSubstring("pwwkew")) {
                throw new AssertionError();
            }

            if (0 != s.lengthOfLongestSubstring("")) {
                throw new AssertionError();
            }

            if (1 != s.lengthOfLongestSubstring(" ")) {
                throw new AssertionError();
            }

            if (7 != s.lengthOfLongestSubstring("abcdefcbgh")) {
                throw new AssertionError();
            }

            LOGGER.info("Tests passed!");
        } catch (AssertionError e) {
            LOGGER.severe("Tests failed!");
        }
    }

    int lengthOfLongestSubstringSolution0(final String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }

        final char[] chars = s.toCharArray();
        final int length = s.length();

        int finalMax = 0;
        int currentMax = 0;
        HashMap<Character, Integer> map = new HashMap<>(length);
        int start = 0;
        int end = 0;
        while (start < length && end < length) {
            // ignore rest string
            if (length - start <= finalMax) {
                return finalMax;
            }

            char c = chars[end];

            if (map.containsKey(c)) {

                int newStart = map.get(c);
                start = newStart + 1;

                // rebuid map
                map.clear();
                for (int i = start; i < end; i++) {
                    map.put(chars[i], i);
                }
            }
            map.put(c, end);
            end++;

            currentMax = end - start;
            if (currentMax > finalMax) {
                finalMax = currentMax;
            }
        }

        return finalMax;
    }

    /**
     * Solution from <a href="https://leetcode.com/problems/longest-substring-without-repeating-characters/discuss/1729/11-line-simple-Java-solution-O(n)-with-explanation">
     * 11-line simple Java solution, O(n) with explanation</a>
     */
    int lengthOfLongestSubstringSolution1(final String s) {
        if (s.length() == 0)
            return 0;
        HashMap<Character, Integer> map = new HashMap<>();
        int max = 0;
        for (int i = 0, j = 0; i < s.length(); ++i) {
            if (map.containsKey(s.charAt(i))) {
                // no need to clean up map, because exclude any index less than j
                j = Math.max(j, map.get(s.charAt(i)) + 1);
            }
            map.put(s.charAt(i), i);
            max = Math.max(max, i - j + 1);
        }
        return max;
    }

    /**
     * inspired by lengthOfLongestSubstringSolution1
     * no need to clean up map
     */
    int lengthOfLongestSubstringSolution2(final String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }

        final char[] chars = s.toCharArray();
        final int length = s.length();

        int finalMax = 0;
        HashMap<Character, Integer> map = new HashMap<>(length);
        int start = 0;
        int end = 0;
        while (start < length && end < length) {
            // ignore rest string
            if (length - start <= finalMax) {
                return finalMax;
            }

            char c = chars[end];

            if (map.containsKey(c)) {
                start = Math.max(start, map.get(c) + 1);
            }
            map.put(c, end);
            end++;

            finalMax = Math.max(finalMax, end - start);
        }

        return finalMax;
    }
}
// @lc code=end
