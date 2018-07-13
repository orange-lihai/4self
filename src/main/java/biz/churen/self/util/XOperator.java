package biz.churen.self.util;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class XOperator {
  // + - * / ^ ! ( ) EOF
  public enum operator {ADD, SUB, MUL, DIV, POW, FAC, L_P, R_P, EOF};
  public static final char[][] operatorPrecedence = {
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
  public static Map<String, Enum> operatorToEnum = new HashMap<>();
  static {
    operatorToEnum.put("+", operator.ADD);
    operatorToEnum.put("-", operator.SUB);
    operatorToEnum.put("*", operator.MUL);
    operatorToEnum.put("/", operator.DIV);
    operatorToEnum.put("^", operator.POW);
    operatorToEnum.put("!", operator.FAC);
    operatorToEnum.put("(", operator.L_P);
    operatorToEnum.put(")", operator.R_P);
    operatorToEnum.put("\0", operator.EOF);
  }
  public static final Set<String> blankChar = new HashSet<>(Arrays.asList("", " ", "\t", "\n"));

  public static final Set<String> leftParentheses = new TreeSet<>(Arrays.asList("(", "[", "{"));
  public static final Set<String> rightParentheses = new TreeSet<>(Arrays.asList(")", "]", "}"));

  public static final Set<String> unaryOperators = new TreeSet<>(Arrays.asList(
      "!",
      "sin", "cos", "tan", "ctn",
      "lg", "ln"
  ));

  public static final Set<String> binaryOperators = new TreeSet<>(Arrays.asList(
      "+", "-", "*", "/",
      "^",
      "log",
      "mod"
  ));

  public static final Set<String> constants = new TreeSet<>(Arrays.asList(
      "pi", "e"
  ));

  public static int consumerNum(String op) {
    if (unaryOperators.contains(op)) {
      return 1;
    } else if (binaryOperators.contains(op)) {
      return 2;
    } else {
      return 0;
    }
  }

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

  public static Double calc(String op, Double d) throws Exception {
    return calcMore(op, new Double[]{d});
  }

  public static Double calc(String op, Double d1, Double d2) throws Exception {
    return calcMore(op, new Double[]{d1, d2});
  }

  public static Double calcMore(String op, Double[] args) throws Exception {
    Double r;
    switch (op) {
      case "!":
        r = Double.valueOf(factorial(args[0].intValue()));
        break;
      case "^":
        r = Math.pow(args[0], args[1]);
        break;
      case "+":
        r = args[0] + args[1];
        break;
      case "-":
        r = args[0] - args[1];
        break;
      case "*":
        r = args[0] * args[1];
        break;
      case "/":
        r = args[0] / args[1];
        break;
      default: throw new Exception("operator [" + op + "] not supported yet!");
    }
    return r;
  }

  public static long factorial(int n) {
    assert n >= 0;
    if (n == 0) { return 1; }
    long f = 1;
    while (n > 0) {
      f = f * n;
      n--;
    }
    return f;
  }

  public static List<String> split(String exp) {
    if (null == exp || exp.trim().length() <= 0) { return new LinkedList<>(); }
    Set<String> allOperators = new TreeSet<>((a, b) -> {
      int n = b.length() - a.length();
      return n == 0 ? (a.compareTo(b)) : n;
    });
    allOperators.addAll(leftParentheses);
    allOperators.addAll(rightParentheses);
    allOperators.addAll(unaryOperators);
    allOperators.addAll(binaryOperators);
    allOperators.addAll(constants);

    for (String k : allOperators) {
      String _pat = "(?! )" + Pattern.quote(k);
      exp = exp.replaceAll(_pat, " " + k);
      _pat = Pattern.quote(k) + "(?! )";
      exp = exp.replaceAll(_pat, k + " ");
    }

    exp = exp.replaceAll("[ ]{2,}", " ");

    // exp = exp.replaceAll("(?![\\d\\|\\!\\|\\)])[\\-]", " 0 -");

    return Arrays.stream(exp.split(" ")).filter(Objects::nonNull)
                 .map(String::trim)
                 .filter(f -> f.length() > 0)
                 .collect(Collectors.toList());
  }
}
