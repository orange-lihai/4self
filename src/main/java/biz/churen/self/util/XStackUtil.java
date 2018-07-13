package biz.churen.self.util;

import java.util.*;

/**
 * algorithms about stack
 */
public class XStackUtil {

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
      if (!XOperator.leftParentheses.contains(c) && !XOperator.rightParentheses.contains(c)) { continue; }
      if (XOperator.leftParentheses.contains(c)) {
        stack.push(c);
      } else {
        Character pre = stack.empty() ? null : stack.peek();
        if (XOperator.parenthesesPair(pre, c)) { stack.pop(); pairs++; }
        else { stack.push(c); }
      }
    }
    return stack.size() > 0 ? -1 : pairs;
  }

  /**
   * reverse Polish notation
   */
  public static Stack<String> RPN(String exp) {
    Stack<String> stackRPN = new Stack<>();
    try {
      expressionEval(exp, stackRPN);
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    }
    return stackRPN;
  }

  /**
   * 计算表达式
   */
  public static Double expressionEval(String exp) {
    Stack<String> stackRPN = new Stack<>();
    Double d = null;
    try {
      d = expressionEval(exp, stackRPN);
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    }
    return d;
  }

  /**
   * 计算表达式, 并记录 "逆波兰表达式" 的栈
   * 如何支持负数呢? 比如:  -1 + 2 * 3
   */
  public static Double expressionEval(String exp, Stack<String> stackRPN) throws Throwable {
    if (null == exp || exp.trim().isEmpty()) { return null; }
    List<String> expList = XOperator.split(exp);
    Stack<Double> stackNum = new Stack<>();
    Stack<String> stackOp = new Stack<>();
    stackOp.push("\0");
    int i = 0;
    String c;
    while (!stackOp.isEmpty() && i <= expList.size()) {
      if (i < expList.size()) {
        c = expList.get(i);
      } else {
        c = "\0";
      }
      if (XOperator.blankChar.contains(c)) { i++; continue; }
      if (XNumber.isNum(c)) {
        Double d = Double.valueOf(c);
        stackNum.push(d);
        stackRPN.push(c);
        i++;
      } else {
        char pri = XOperator.operatorPrecedence[
              XOperator.operatorToEnum.get(stackOp.peek()).ordinal()
            ][
              XOperator.operatorToEnum.get(c).ordinal()
            ];
        switch (pri) {
          case '<':
            stackOp.push(c); i++;
            break;
          case '=':
            stackOp.pop(); i++;
            break;
          case '>':
            String op = stackOp.pop();
            stackRPN.push(op);
            int n = XOperator.consumerNum(op);
            if (n <= 0) { throw new IllegalArgumentException("operator: " + op + " un-supported yet!"); }
            Double[] params = new Double[n];
            for (int j = 0; j < n; j++) {
              params[n - j - 1] = stackNum.pop();
            }
            stackNum.push(XOperator.calcMore(op, params));
            break;
          default: break;
        }
      }
    }
    return stackNum.size() == 1 ? stackNum.peek() : null;
  }


  /**
   * 皇后问题里面用到的 "皇后类"
   */
  class Queen {
    int x;
    int y;

    Queen(int x, int y) { this.x = x; this.y = y; }

    boolean conflict(Queen q) {
      return (this.x == q.x) // 行冲突
          || (this.y == q.y) // 列冲突
          || (this.x - this.y == q.x - q.y) // y =  1 * x + b
          || (this.x + this.y == q.x + q.y) // y = -1 * x + b
      ;
    }

    boolean conflict(Collection<Queen> queens) {
      if (null == queens || queens.isEmpty()) { return false; }
      for (Queen q : queens) {
        if (this.conflict(q)) { return true; }
      }
      return false;
    }
  }

  public List<Stack<Queen>> placeQueens(int n) {
    List<Stack<Queen>> rs = new ArrayList<>();
    Stack<Queen> stack = new Stack<>();
    int i = 0, j = 0;
    while (true) {
      if (i == 0 && j == n) { break; }
      for (; j < n; j++) {
        Queen t = new Queen(i, j);
        if (!t.conflict(stack)) { break; }
      }
      if (j >= n || i >= n) {
        Queen t = stack.pop();
        i = t.x;
        j = t.y + 1;
      } else {
        stack.push(new Queen(i, j));
        if (stack.size() == n) { cloneMulti(rs, stack); } // 记录合法的解
        i++;
        j = 0;
      }
    }
    return rs;
  }

  public void cloneMulti(List<Stack<Queen>> tar,  Stack<Queen> queens) {
    Stack<Queen> r = new Stack<>();
    for (int i = 0; i < queens.size(); i++) {
      Queen newQueen = new Queen(queens.get(i).x, queens.get(i).y);
      r.add(newQueen);
    }
    tar.add(r);
  }

  
}
