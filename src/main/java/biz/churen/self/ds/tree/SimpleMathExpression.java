package biz.churen.self.ds.tree;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by lihai5 on 2018/5/24.
 */
public class SimpleMathExpression {
  private static Set<String> parenthesis = new LinkedHashSet<>(Arrays.asList(
      "(", ")"
  ));
  private static Set<String> unaryOperators = new LinkedHashSet<>(Arrays.asList(
      "sin", "cos", "tan", "cot", "sec", "csc",
      "ln"
  ));
  private static Set<String> binaryOperators = new LinkedHashSet<>(Arrays.asList(
      "+", "-", "*", "/",
      "log",
      "^"
  ));
  private static Set<String> constant = new LinkedHashSet<>(Arrays.asList(
      "pi", "e"
  ));
  private static Set<String> operators = new LinkedHashSet<>();
  static {
    operators.addAll(parenthesis);
    operators.addAll(unaryOperators);
    operators.addAll(binaryOperators);
  }

  public static boolean isValidDouble(String d) {
    boolean t = true;
    try {
      Double.valueOf(d);
    } catch (Exception ex) {
      t = false;
    }
    return  t;
  }


  /**
   * calculate the value of expression, using binary tree
   * @param expression eg. (1 * (2 + 3))
   *                       2 * 5 + sin 5
   *                       pi * e
   *                       (ln 4 + (log 100) + 2 log 10)
   *                       5 + 3^2 + e^6
   * @return E
   */
  public static Stack<Double> toStack(String expression) throws Exception {
    if (null == expression || expression.trim().isEmpty()) { return null; }
    for (String o : operators) {
      if (parenthesis.contains(o) || Arrays.asList("+", "*", "?", ".").contains(o)) {
        expression = expression.replaceAll(Pattern.quote(o), " " + o + " ");
      } else {
        expression = expression.replaceAll(o, " " + o + " ");
      }

    }
    String[] tokens = expression.split(" ");
    List<String> tokenList = Arrays.stream(tokens).filter(f -> null != f && f.trim().length() > 0)
                                   .collect(Collectors.toList());
    Stack<Double> stack = new Stack<>();
    for (String t : tokenList) {
      if (parenthesis.contains(t)) {
      } else if (unaryOperators.contains(t)) {
      } else if (binaryOperators.contains(t)) {
      } else if (constant.contains(t)) {
      } else if (SimpleMathExpression.isValidDouble(t)) {
        stack.push(Double.valueOf(t));
      } else {
        throw new Exception("parse expression error, found [ " + t + " ] in " + expression);
      }
    }
    return stack;
  }

  public static Double stackCalc(Stack<Double> stack) {
    return null;
  }

  public static Double calc(String expression) throws Exception {
    Stack<Double> stack = toStack(expression);
    System.out.println("expression to stack: ");
    for (Double d : stack) {
      System.out.println(d);
    }
    System.out.println();
    Double rs = stackCalc(stack);
    System.out.println("result of expression: " + rs);
    return rs;
  }

  public static void main(String[] args) throws Exception {
    String exp = "(3+(4*5))";
    calc(exp);
  }
}
