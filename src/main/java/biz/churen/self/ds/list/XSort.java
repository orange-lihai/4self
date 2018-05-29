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

  static class XLink<E> {
    E elem;
    XLink<E> pre;
    XLink<E> next;

    public XLink () {}

    public XLink (E e) {
      this.elem = e;
    }

    public void addNext(E e) {
      if (null == this.elem) {
        this.elem = e;
        return;
      }
      XLink<E> _new = new XLink<>(e);
      XLink<E> end = this;
      while (null != end.next) {end = end.next;  }
      end.next = _new;
      _new.pre = end;
    }

    public void printXLink() {
      XLink<E> v = this;
      StringBuilder sb = new StringBuilder(" link: ");
      while (null != v) {
        sb.append(v.elem);
        sb.append(" --> ");
        v = v.next;
      }
      System.out.println(sb.toString());
    }
  }

  public static XLink<Integer> insertSortInLink(XLink<Integer> link) {
    if (null == link) { return link; }
    XLink<Integer> _next, _pre, _in, _start;
    _start = link;
    _in = _start.next;
    while (null != _in) {
      _next = _in.next;
      _pre = _in.pre;
      while (null != _pre && _in.elem < _pre.elem) {
        _pre = _pre.pre;
      }
      if (_pre != _in.pre) {
        if (null != _in.next) {
          _in.next.pre = _in.pre;
        }
        _in.pre.next = _in.next;
        if (null == _pre) {
          _in.next = _start;
          _start.pre = _in;
          _in.pre = null;
          _start = _in;
        } else {
          _pre.next.pre = _in;
          _in.next = _pre.next;
          _pre.next = _in;
          _in.pre = _pre;
        }
        _in = _next;
      } else {
        _in = _in.next;
      }
    }
    return _start;
  }

  public static int[] insertSort(int[] arr, boolean desc) {
    if (null == arr || arr.length <= 1) { return arr; }
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

  private static int[] insertShellSort(int[] arrShell, boolean desc) {
    if (null == arrShell || arrShell.length <= 1) { return arrShell; }
    int gap = arrShell.length / 2;
    while (gap > 0) {
      for (int i = gap; i < arrShell.length; i++) {
        for (int j = i; j - gap >= 0; j = j - gap) {
          boolean _b = desc ? arrShell[j - gap] < arrShell[j] : arrShell[j - gap] > arrShell[j];
          if (_b) {
            int temp = arrShell[j];
            arrShell[j] = arrShell[j - gap];
            arrShell[j - gap] = temp;
          } else {
            break;
          }
        }
      }
      gap = gap / 2;
    }
    return arrShell;
  }

  private static int[] selectionSort(int[] arrSel, boolean desc) {
    if (null == arrSel || arrSel.length <= 1) { return arrSel; }
    for (int i = 0; i < arrSel.length; i++) {
      int _check = -1, _t = arrSel[i];
      for (int j = i + 1; j < arrSel.length; j++) {
        if (desc ? arrSel[j] > _t : arrSel[j] < _t) {
          _check = j;
          _t = arrSel[j];
        }
      }
      if (_check >= 0) {
        int temp = arrSel[i];
        arrSel[i] = arrSel[_check];
        arrSel[_check] = temp;
      }
    }
    return arrSel;
  }

  private static int[] heapSort(int[] arrHeap, boolean desc) {
    if (null == arrHeap || arrHeap.length <= 1) { return arrHeap; }
    int _parent = ((arrHeap.length - 1) / 2);
    for (int i = _parent; i >= 0; i--) {
      heapSwap(arrHeap, i, arrHeap.length - 1, desc);
    }
    for (int j = arrHeap.length - 1; j > 0; j--) {
      XNumber.swap(arrHeap, 0, j);
      heapSwap(arrHeap, 0, j - 1, desc);
    }


    return arrHeap;
  }

  private static int[] heapSwap(int[] arrHeap, int start, int arrLength, boolean desc) {
    if (null == arrHeap || arrHeap.length <= 1) { return arrHeap; }
    int len = arrLength >= arrHeap.length - 1 ? arrHeap.length - 1 : arrLength;
    int _idx, _left, _right;
    while (true) {
      _idx = start;
      _left = 2 * start + 1;
      _right = 2 * (start + 1);
      if (_left <= len && (desc ? (arrHeap[start] > arrHeap[_left]) : (arrHeap[start] < arrHeap[_left]))) {
        start = _left;
      }
      if (_right <= len && (desc ? (arrHeap[start] > arrHeap[_right]) : (arrHeap[start] < arrHeap[_right]))) {
        start = _right;
      }
      if (_idx != start) {
        XNumber.swap(arrHeap, start, _idx);
      } else {
        break;
      }
    }
    return arrHeap;
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
    // 直接插入排序, 数组
    int[] arrIn = XNumber.randomIntArr(7, 0, 100);
    XSort.print(arrIn, "arrIn", " ");
    int[] arrOut = XSort.insertSort(arrIn, true);
    XSort.print(arrOut, "arrOut", " ");
    System.out.println("\n");

    // 直接插入排序, 双链表
    int[] arrLink = XNumber.randomIntArr(7, 0, 100);
    XSort.print(arrLink, "arrLink", " ");
    XLink<Integer> link = new XLink<>();
    for (int a : arrLink) {
      link.addNext(a);
    }
    link.printXLink();
    link = XSort.insertSortInLink(link);
    link.printXLink();
    System.out.println("\n");

    // 对直接插入排序的优化, 希尔排序, 数组
    int[] arrShell = XNumber.randomIntArr(30, 0, 100);
    XSort.print(arrShell, "arrShell", " ");
    int[] arrShellOut = XSort.insertShellSort(arrShell, true);
    XSort.print(arrShellOut, "arrShellOut", " ");
    System.out.println("\n");

    // 简单选择排序
    int[] arrSel = XNumber.randomIntArr(30, 0, 100);
    XSort.print(arrSel, "arrSel", " ");
    int[] arrSelOut = XSort.selectionSort(arrSel, true);
    XSort.print(arrSelOut, "arrSelOut", " ");
    System.out.println("\n");

    // 堆排序
    int[] arrHeap = XNumber.randomIntArr(20, 0, 100);
    XSort.print(arrHeap, "arrHeap", " ");
    int[] arrHeapOut = XSort.heapSort(arrHeap, false);
    XSort.print(arrHeapOut, "arrHeapOut", " ");
    System.out.println("\n");
  }


}
