package biz.churen.self.ds.list;

import biz.churen.self.util.XNumber;

/**
 * Util Class Of Sort Functions
 * 数组 int[]
 * 链表 XLink<Integer>
 * 冒泡排序, 选择排序, 插入排序, 希尔排序, 归并排序, 快速排序, 堆排序, 计数排序, 桶排序, 基数排序
 *
 *                                        |-- 直接插入排序
 *                         |--- 插入排序 --|
 *                         |              |-- 希尔排序
 *                         |
 *                         |              |-- 简单选择排序
 *                         |--- 选择排序 --|
 *                         |              |-- 堆排序
 *          |-- 内部排序 ---|
 *          |              |
 *          |              |              |-- 冒泡排序
 *          |              |--- 交换排序 --|
 *          |              |              |-- 快速排序
 *          |              |
 *          |              |--- 归并排序
 *          |              |
 *          |              |--- 基数排序
 * 排序 -----
 *          |
 *          |
 *          |              |--- 2路归并排序
 *          |-- 外部排序 ---|
 *                         |--- 多路归并排序
 *
 *
 */
public class XSort {

  class XLink<E> {
    E elem;
    XLink<E> pre;
    XLink<E> next;
  }

  public static int[] insertSort(int[] arr, boolean desc) {
    if (null == arr || arr.length <= 0) { return arr; }
    int i, j, p;
    for (i = 1; i < arr.length; i++) {
      p = arr[i];
      for (j = i - 1; j >= 0; j--) {
        if (desc ? p < arr[j] : p >= arr[j]) { break; }
        arr[j + 1] = arr[j];
      }
      arr[j + 1] = p;
    }
    return arr;
  }

  public static XLink<Integer> insertSortInLink(XLink<Integer> link) {
    if (null == link) { return link; }
    XLink<Integer> _cur, _pre, _next;
    _cur = link;
    _next = link.next;
    while (null != _next) {
      _pre = _cur;
      while (_next.elem < _pre.elem) {
        _pre = _pre.pre;
      }
      // TODO
    }
    return link;
  }

  // print function
  private static void print(int[] arr, String desc, String split) {
    if (null == arr || arr.length <= 0) { return; }
    if (null == split) { split = " "; }
    System.out.println(desc + " : ");
    for (int a : arr) {
      System.out.print(a);
      System.out.print(split);
    }
    System.out.println();
  }

  public static void main(String[] args) {
    int[] arrIn = XNumber.randomIntArr(7, 0, 100);
    XSort.print(arrIn, "arrIn", " ");
    int[] arrOut = XSort.insertSort(arrIn, true);
    XSort.print(arrOut, "arrOut", " ");

  }
}
