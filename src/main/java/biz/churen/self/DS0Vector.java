package biz.churen.self;

import biz.churen.self.ds0.XVector;
import biz.churen.self.util.XNumber;

public class DS0Vector {

  public static void main(String[] args) {
    XVector<String> v = new XVector<>(String.class);
    XVector<String> v4 = new XVector<>(String.class, 4);

    String[] arr = {"a", "bb", "ccc", "dddd", "eeeee"};
    XVector<String> a = new XVector<>(arr, 2, 3);
    XVector<String> b = new XVector<>(arr, arr.length);

    XVector<String> c = new XVector<>(b, 2, 3);
    XVector<String> d = new XVector<>(b, b.size());

    d.unSort(1, 3);
    d.unSort();

    Integer[] ea = (XNumber.randomIntegerArr(20, 2, 50));
    XVector<Integer> e = new XVector<>(ea, ea.length);
    e.unSort();

    v.insert("a");
    v.insert("b");
    v.insert("c");
    v.insert("d");
    v.insert("e");
    v.insert("f");
    v.insert("g");
    v.insert("h");
    String x = v.get(2);

    v.remove(0);
    v.remove(2, 4);

    System.out.println(v);
  }
}
