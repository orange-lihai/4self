package biz.churen.self.util;

/**
 * Util Class Of Numbers
 */
public class XNumber {

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
}
