package biz.churen.self.util;

import java.util.*;

/**
 * algorithms about stack
 */
public class XStackUtil {

  // final Set<Character> parentheses = new HashSet<>(Arrays.asList('(', ')', '[', ']', '{', '}'));
  static final Set<Character> leftParentheses = new HashSet<>(Arrays.asList('(', '[', '{'));
  static final Set<Character> rightParentheses = new HashSet<>(Arrays.asList(')', ']', '}'));

  /**
   * @param left left part of parentheses
   * @param right right part of parentheses
   * @return is valid pair
   */
  public static boolean parenthesesPair(Character left, Character right) {
    return !(null == left || null == right)
           && (
               ('(' == left && ')' == right)
            || ('[' == left && ']' == right)
            || ('{' == left && '}' == right)
           );
  }

  /**
   *
   * @param expression
   * examples: 1 * (2 + 3) return 1;
   *           {2, 3, 4} ssd [4 5 6] return 2;
   *           ssd return 0;
   *           [[{4, 6, 7]  return -1;
   * @return the num of pairs in @expression, return -1 if Paren Match failed."
   */
  public static int parenthesesMatch(String expression) {
    int pairs = 0;
    Stack<Character> stack = new Stack<>();
    for (int i = 0; null != expression && i < expression.length(); i++) {
      Character c = expression.charAt(i);
      if (!leftParentheses.contains(c) && !rightParentheses.contains(c)) { continue; }
      if (leftParentheses.contains(c)) {
        stack.push(c);
      } else {
        Character pre = stack.empty() ? null : stack.peek();
        if (parenthesesPair(pre, c)) { stack.pop(); pairs++; }
        else { stack.push(c); }
      }
    }
    return stack.size() > 0 ? -1 : pairs;
  }
}
