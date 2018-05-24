package biz.churen.self.ds.tree;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Stack;

/**
 * Created by lihai5 on 2018/5/24.
 */
public class NumberParseTree {
  public Double value;
  public NumberParseTree leftTree;
  public NumberParseTree rightTree;

  public NumberParseTree(Double value) {
    this.value = value;
  }

  private static Set<String> operators = new LinkedHashSet<>(Arrays.asList(
      "(", ")",
      "+", "-", "*", "/",
      "sin", "cos", "tan", "cot", "sec", "csc",
      "log", "ln",
      "^",
      "pi",
      "e"
  ));

  /**
   * calculate the value of expression, using binary tree
   * @param expression eg. (1 * (2 + 3))
   *                       2 * 5 + sin 5
   *                       pi * e
   *                       (ln 4 + (log 100) + 2 log 10)
   *                       5 + 3^2 + e^6
   * @return E
   */
  public Stack<Double> toStack(String expression) {
    if (null == expression || expression.trim().isEmpty()) { return null; }
    for (String o : operators) {
      expression = expression.replaceAll(o, " " + o + " ");
    }
    String[] tokens = expression.split(" ");
    Stack<Double> stack = new Stack<>();
    for (String t : tokens) {

    }
    return stack;
  }
}
