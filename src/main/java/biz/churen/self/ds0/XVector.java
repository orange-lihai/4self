package biz.churen.self.ds0;

import biz.churen.self.util.XNumber;

import java.lang.reflect.Array;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class XVector<E extends Comparable> {
  private static final int DEFAULT_CAPACITY = 3;
  private int _size;
  private int _capacity;
  private E[] _elem;

  // constructors
  public XVector(Class<?> clazz) {
    this(clazz, DEFAULT_CAPACITY);
  }
  public XVector(Class<?> clazz, int capacity) {
    _size = 0;
    _capacity = capacity;
    _elem = newArr(clazz, _capacity);
  }
  public XVector(E[] src, int low, int high) {
    copyFrom(src, low, high);
  }
  public XVector(E[] src, int n) {
    copyFrom(src, 0, n);
  }
  public XVector(XVector<E> src, int low, int high) {
    copyFrom(src._elem, low, high);
  }
  public XVector(XVector<E> src, int n) {
    copyFrom(src._elem, 0, n);
  }

  // private functions
  private void expand() {
    if (_size < _capacity) { return; }
    if (_capacity < DEFAULT_CAPACITY) { _capacity = DEFAULT_CAPACITY; }
    _capacity = _capacity * 2;
    E[] _new = newArr(_elem[0].getClass(), _capacity);
    System.arraycopy(_elem, 0, _new, 0, _size);
    _elem = _new;
  }
  
  private void shrink() {
    if (_capacity < (DEFAULT_CAPACITY << 1)) { return; }
    if ((_size << 2) > _capacity) { return; } // 25%
    _capacity = _capacity << 1;
    E[] _new = newArr(_elem[0].getClass(), _capacity);
    System.arraycopy(_elem, 0, _new, 0, _size);
    _elem = _new;
  }

  private XVector<E> copyFrom(E[] src, int low, int high) {
    assert null != src && 0 <= low && low < high && high <= src.length;
    _size = 0;
    _capacity = (high - low) * 2;
    _elem = newArr(src[0].getClass(), _capacity);
    System.arraycopy(src, low, _elem, 0, high - low);
    _size = high - low;
    return this;
  }
  
  @SuppressWarnings({"unchecked"})
  private E[] newArr(Class<?> componentType, int length) {
    return (E[]) (Array.newInstance(componentType, length));
  }

  // sort
  public boolean bubble(int low, int high) { return false; }
  public void bubbleSort(int low, int high) {}
  public void merge(int low, int mid, int high) {}
  public void mergeSort(int low, int high) {}
  public int partition(int low, int high) { return 0; }
  public void quickSort(int low, int high) {}
  public void heapSort(int low, int high) {}
  
  // read only
  public int size() { return _size; }
  public boolean empty() { return _size <= 0; }

  // 判断向量是否有序, 返回逆序相邻元素对的总数
  @SuppressWarnings({"unchecked"})
  public int disOrdered() {
    int n = 0;
    for (int i = 1; i < _size; i++) {
      if (_elem[i].compareTo(_elem[i - 1]) < 0) { n++; }
    }
    return n;
  }

  // 无序向量查找方法 find()
  public int find(E e) { return find(e, 0, _size); }

  // 无序向量查找方法 find()
  @SuppressWarnings({"unchecked"})
  public int find(E e, int low, int high) {
    for (int i = low; i < high; i++) {
      if (0 == _elem[i].compareTo(e)) { return i; }
    }
    return -1;
  }

  public int search(E e) { return _size <= 0 ? -1 : search(e, 0, _size); }
  public int search(E e, int low, int high) { return 0; }
  public E get(int rank) { return _elem[rank]; }
  public void put(int rank, E e) { _elem[rank] = e; }


  // writable
  public E remove(int rank) {
    E e = get(rank);
    remove(rank, rank + 1);
    return e;
  }
  // 删除秩在区间 [low, high) 之内的元素
  public int remove(int low, int high) {
    if (low == high) { return 0; }
    while (high < _size) {
      _elem[low++] = _elem[high++];
    }
    _size = low;
    shrink();
    return (high - low);
  }

  // insert()  0 <= rank <= _size
  public int insert(int rank, E e) {
    expand();
    for (int i = _size; i > rank; i--) {
      _elem[i] = _elem[i - 1];
    }
    _elem[rank] = e;
    _size++;
    return 0;
  }
  public int insert(E e) { return insert(_size, e); }

  // 对 [low, high) 排序
  public void sort(int low, int high, BiFunction<E, E, Integer> compare) {}
  // 整体排序
  public void sort(BiFunction<E, E, Integer> compare) { sort(0, _size, compare);}
  // 对 [low, high) 置乱
  public void unSort(int low, int high) {
    for (int i = high - 1; i > low; i--) {
      int _rand = (int) (Math.random() * (XNumber.next10(high)));
      int _r = (_rand % (i - low)) + low;
      E temp = _elem[i];
      _elem[i] = _elem[_r];
      _elem[_r] = temp;
    }
  }
  // 整体置乱
  public void unSort() { unSort(0, _size); }

  // 无序去重
  public int deDuplicate() {
    int oldSize = _size;
    for (int i = 1; i < _size;) {
      int _r = find(_elem[i], 0, i);
      if (_r >= 0) { remove(_r); }
      else { i++; }
    }
    return oldSize - _size;
  }
  // 有序去重
  @SuppressWarnings({"unchecked"})
  public int uniquify() {
    int i = 0, j = 0;
    while (++j < _size) {
      if (_elem[j].compareTo(_elem[i]) != 0) {
        _elem[++i] = _elem[j];
      }
    }
    _size = ++i;
    shrink();
    return 0;
  }

  // traverse
  // 遍历
  public void traverse(Consumer<E> consumer) {
    assert null != consumer;
    for (int i = 0; i < _size; i++) {
      consumer.accept(_elem[i]);
    }
  }






}
