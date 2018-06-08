package biz.churen.self.util;

import java.util.*;

/**
 * algorithms about stack
 */
public class XStackUtil {

  // final Set<Character> parentheses = new HashSet<>(Arrays.asList('(', ')', '[', ']', '{', '}'));
  static final Set<Character> leftParentheses = new HashSet<>(Arrays.asList('(', '[', '{'));
  static final Set<Character> rightParentheses = new HashSet<>(Arrays.asList(')', ']', '}'));
  // + - * / ^ ! ( ) EOF
  public enum operator {ADD, SUB, MUL, DIV, POW, FAC, L_P, R_P, EOF};
  static final char[][] operatorPrecedence = {
    {'>', '>', '<', '<', '<', '<', '<', '>', '>'}, // +
    {'>', '>', '<', '<', '<', '<', '<', '>', '>'}, // -
    {'>', '>', '>', '>', '<', '<', '<', '>', '>'}, // *
    {'>', '>', '>', '>', '<', '<', '<', '>', '>'}, // /
    {'>', '>', '>', '>', '>', '<', '<', '>', '>'}, // ^
    {'>', '>', '>', '>', '>', '>', ' ', '>', '>'}, // !
    {'<', '<', '<', '<', '<', '<', '<', '=', ' '}, // (
    {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, // )
    {'<', '<', '<', '<', '<', '<', '<', ' ', '='}  // \0
   // +    -    *    /    ^    !    (    )    \0
  };
  static Map<Character, Enum> operatorToEnum = new HashMap<>();
  static {
    operatorToEnum.put('+', operator.ADD);
    operatorToEnum.put('-', operator.ADD);
    operatorToEnum.put('*', operator.ADD);
    operatorToEnum.put('/', operator.ADD);
    operatorToEnum.put('^', operator.ADD);
    operatorToEnum.put('!', operator.ADD);
    operatorToEnum.put('(', operator.ADD);
    operatorToEnum.put(')', operator.ADD);
    operatorToEnum.put('\0', operator.ADD);
  }
  public static final Set<Character> blankChar = new HashSet<>(Arrays.asList(' ', '\t', '\n'));

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

  /**
   * reverse Polish notation
   */
  public Stack<Character> RPN(String exp) {
    Stack<Character> stackRPN = new Stack<>();
    try {
      expressionEval(exp, stackRPN);
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    }
    return stackRPN;
  }

  public Double expressionEval(String exp) {
    Stack<Character> stackRPN = new Stack<>();
    Double d = null;
    try {
      d = expressionEval(exp, stackRPN);
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    }
    return d;
  }

  public Double expressionEval(String exp, Stack<Character> stackRPN) throws Throwable {
    if (null == exp || exp.trim().isEmpty()) { return null; }
    Stack<Double> stackNum = new Stack<>();
    Stack<Character> stackOp = new Stack<>();
    stackOp.push('\0');
    int i = 0;
    Character c;
    while (!stackOp.isEmpty() && i <= exp.length()) {
      if (i < exp.length()) {
        c = exp.charAt(i++);
      } else {
        c = '\0';
      }
      if (blankChar.contains(c)) { continue; }
      if ('0' <= c && c <= '9') {
        Double d = Double.valueOf(c);
        stackNum.push(d);
        stackRPN.push(c);
      } else {
        char pri = operatorPrecedence[stackOp.peek()][c];
        switch (pri) {
          case '<':
            stackOp.push(c);
            break;
          case '=':
            stackOp.pop();
            break;
          case '>':
            char op = stackOp.pop();
            stackRPN.push(op);
            if ('!' == op) {
              Double _d = stackNum.pop();
              stackNum.push(XOperator.calc(op, _d));
            } else {
              Double _dOne = stackNum.pop();
              Double _dTwo = stackNum.pop();
              stackNum.push(XOperator.calc(op, _dOne, _dTwo));
            }
          default: break;
        }
      }
    }
    return stackNum.size() == 1 ? stackNum.peek() : null;
  }

}
