import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

/*
 * @lc app=leetcode id=2 lang=java
 *
 * [2] Add Two Numbers
 */

// @lc code=start
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class AddTwoNumbersSolution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        return addTwoNumbersSolution0(l1, l2);
    }

    private static final Logger LOGGER = Logger.getLogger(AddTwoNumbersSolution.class.getName());

    public static void main(String[] args) {
        try {
            AddTwoNumbersSolution s = new AddTwoNumbersSolution();

            int[] result = ListNode.toArray(s.addTwoNumbers(ListNode.fromArray(new int[] { 2, 4, 3 }),
                    ListNode.fromArray(new int[] { 5, 6, 4 })));
            if (LOGGER.isLoggable(Level.INFO)) {
                LOGGER.info(Arrays.toString(result));
            }
            if (!Arrays.equals(result, new int[] { 7, 0, 8 })) {
                throw new AssertionError();
            }

            result = ListNode
                    .toArray(s.addTwoNumbers(ListNode.fromArray(new int[] { 0 }), ListNode.fromArray(new int[] { 0 })));
            if (LOGGER.isLoggable(Level.INFO)) {
                LOGGER.info(Arrays.toString(result));
            }
            if (!Arrays.equals(result, new int[] { 0 })) {
                throw new AssertionError();
            }

            result = ListNode.toArray(s.addTwoNumbers(ListNode.fromArray(new int[] { 9, 9, 9, 9, 9, 9, 9 }),
                    ListNode.fromArray(new int[] { 9, 9, 9, 9 })));
            if (LOGGER.isLoggable(Level.INFO)) {
                LOGGER.info(Arrays.toString(result));
            }
            if (!Arrays.equals(result, new int[] { 8, 9, 9, 9, 0, 0, 0, 1 })) {
                throw new AssertionError();
            }

            LOGGER.info("Tests passed!");
        } catch (AssertionError e) {
            LOGGER.severe("Tests failed!");
        }
    }

    ListNode addTwoNumbersSolution0(ListNode l1, ListNode l2) {

        if (l1 == null || l2 == null) {
            throw new IllegalArgumentException();
        }

        ListNode l1Current = l1;
        ListNode l2Current = l2;
        ListNode result = null;
        ListNode previousNode = null;
        int carry = 0;
        while (l1Current != null || l2Current != null || carry != 0) {
            ListNode newNode = new ListNode();

            if (previousNode == null) {
                result = newNode;
            } else {
                previousNode.next = newNode;
            }

            int val1 = 0;
            int val2 = 0;

            if (l1Current != null) {
                val1 = l1Current.val;
                l1Current = l1Current.next;
            }

            if (l2Current != null) {
                val2 = l2Current.val;
                l2Current = l2Current.next;
            }

            int sum = val1 + val2 + carry;
            int currentVal = sum;
            if (sum >= 10) {
                carry = 1;
                currentVal = sum % 10;
            } else {
                carry = 0;
            }

            newNode.val = currentVal;
            previousNode = newNode;

        }
        return result;
    }
}

class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public static ListNode fromArray(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException();
        }

        ListNode listNode = new ListNode(array[0]);

        ListNode currentNode = listNode;
        for (int i = 1; i < array.length; i++) {
            currentNode.next = new ListNode(array[i]);
            currentNode = currentNode.next;
        }

        return listNode;
    }

    public static int[] toArray(ListNode listNode) {
        if (listNode == null) {
            throw new IllegalArgumentException();
        }

        ArrayList<Integer> list = new ArrayList<>(16);

        ListNode currentNode = listNode;
        while (currentNode != null) {
            list.add(currentNode.val);
            currentNode = currentNode.next;
        }

        // or return list.stream().mapToInt(Integer::intValue).toArray()
        return list.stream().mapToInt(i -> i).toArray();
    }
}
// @lc code=end
