import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Logger;

/*
 * @lc app=leetcode id=1 lang=java
 *
 * [1] Two Sum
 *
 * https://leetcode.com/problems/two-sum/description/
 *
 * algorithms
 * Easy (44.33%)
 * Total Accepted:    1.9M
 * Total Submissions: 4.4M
 * Testcase Example:  '[2,7,11,15]\n9'
 *
 * Given an array of integers, return indices of the two numbers such that they
 * add up to a specific target.
 *
 * You may assume that each input would have exactly one solution, and you may
 * not use the same element twice.
 *
 * Example:
 *
 *
 * Given nums = [2, 7, 11, 15], target = 9,
 *
 * Because nums[0] + nums[1] = 2 + 7 = 9,
 * return [0, 1].
 *
 *
 */
class Solution {

    public int[] twoSum(int[] nums, int target) {
        return twoSumSolution2(nums, target);
    }

    private static final Logger LOGGER = Logger.getLogger(Solution.class.getName());

    public static void main(String[] args) {
        try {
            Solution s = new Solution();

            if (!Arrays.equals(s.twoSum(new int[] { 2, 7, 11, 15 }, 9), new int[] { 0, 1 })) {
                throw new AssertionError();
            }

            if (!Arrays.equals(s.twoSum(new int[] { 1, 2, 7, 11, 15 }, 9), new int[] { 1, 2 })) {
                throw new AssertionError();
            }

            if (!Arrays.equals(s.twoSum(new int[] { 3, 2, 4 }, 6), new int[] { 1, 2 })) {
                throw new AssertionError();
            }

            if (!Arrays.equals(s.twoSum(new int[] { 1, 3, 3 }, 6), new int[] { 1, 2 })) {
                throw new AssertionError();
            }

            if (!Arrays.equals(s.twoSum(new int[] { 3, 3 }, 6), new int[] { 0, 1 })) {
                throw new AssertionError();
            }

            LOGGER.info("Tests passed!");
        } catch (AssertionError e) {
            LOGGER.severe("Tests failed!");
        }
    }

    private static final String MSG_NUMS_IS_NULL = "nums is null";
    private static final String MSG_NUMS_IS_LESS_THAN_TWO = "nums less than 2 element";
    private static final String MSG_NOT_FOUND = "Not Found";

    /**
     * 暴力搜索 Brute Force
     *
     * ERROR 1: 因为没注意startIndex和endIndex的最大边界值
     *
     * Time complexity:O(n²) Space complexity:O(1)
     */
    int[] twoSumSolution0(int[] nums, int target) {
        if (nums == null) {
            throw new IllegalArgumentException(MSG_NUMS_IS_NULL);
        }

        int length = nums.length;

        if (length < 2) {
            throw new IllegalArgumentException(MSG_NUMS_IS_LESS_THAN_TWO);
        }

        for (int startIndex = 0; startIndex <= length - 2; startIndex++) {
            for (int endIndex = startIndex + 1; endIndex <= length - 1; endIndex++) {
                if (nums[startIndex] + nums[endIndex] == target) {
                    return new int[] { startIndex, endIndex };
                }
            }
        }

        throw new IllegalArgumentException(MSG_NOT_FOUND);
    }

    int[] twoSumSolution1(int[] nums, int target) {
        if (nums == null) {
            throw new IllegalArgumentException(MSG_NUMS_IS_NULL);
        }

        int length = nums.length;

        if (length < 2) {
            throw new IllegalArgumentException(MSG_NUMS_IS_LESS_THAN_TWO);
        }

        /**
         * Two-pass Hash Table
         *
         * 利用hash table的查找复杂度是O(1)的特性,将搜索时间简化
         *
         * Time complexity:O(n) Space complexity:O(n)
         */
        HashMap<Integer, Integer> map = new HashMap<>(length);
        for (int i = 0; i < length; i++) {
            map.put(nums[i], i);
        }

        for (int j = 0; j < length; j++) {
            Integer complementIndex = map.get(target - nums[j]);
            if (complementIndex != null && complementIndex.intValue() != j) {
                return new int[] { j, complementIndex.intValue() };
            }
        }

        throw new IllegalArgumentException(MSG_NOT_FOUND);
    }

    /**
     * One-pass Hash Table
     *
     * 一边插入,一边搜索,循环降低为一次
     *
     * ERROR 1: 未考虑两个数字相同的情况,题目假设了答案只会有一个,所以搜索时用上即可
     *
     * ERROR 2: 返回的顺序有误，经过测试才发现，所以测试非常必要
     *
     * Time complexity:O(n) Space complexity:O(n)
     */
    int[] twoSumSolution2(int[] nums, int target) {
        if (nums == null) {
            throw new IllegalArgumentException(MSG_NUMS_IS_NULL);
        }

        int length = nums.length;

        if (length < 2) {
            throw new IllegalArgumentException(MSG_NUMS_IS_LESS_THAN_TWO);
        }

        HashMap<Integer, Integer> map = new HashMap<>(length);
        for (int i = 0; i < length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement) && map.get(complement) != i) {
                return new int[] { map.get(complement), i };
            }
            map.put(nums[i], i);
        }

        throw new IllegalArgumentException(MSG_NOT_FOUND);
    }
}
