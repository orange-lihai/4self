package biz.churen.self.util;

import java.util.regex.Pattern;

/**
 * Util Class Of Numbers
 */
public class XNumber {

  public static boolean isNum(String num) {
    if (null == num || num.trim().length() <= 0) { return false; }
    String regExp = "[-]?[0-9]+";
    return Pattern.matches(regExp, num);
  }

  public static int next10(int a) {
    int x = Math.abs(a);
    int i = 0;
    while (x > 0) {
      x = x / 10;
      i++;
    }
    return i > 0 ? (int) Math.pow(10, i) : 0;
  }

  public static int[] randomIntArr(int len, int min, int max) {
    if (len <= 0) { return null; }
    int[] r = new int[len];
    for (int i = 0; i < r.length; i++) {
      Double d = (Math.random() * XNumber.next10(max));
      r[i] = d.intValue() % (max - min) + min;
    }
    return r;
  }

  public static Integer[] randomIntegerArr(int len, int min, int max) {
    if (len <= 0) { return null; }
    Integer[] r = new Integer[len];
    for (int i = 0; i < r.length; i++) {
      Double d = (Math.random() * XNumber.next10(max));
      r[i] = d.intValue() % (max - min) + min;
    }
    return r;
  }

  public static int[] swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
    return arr;
  }
}
