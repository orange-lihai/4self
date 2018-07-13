package biz.churen.self.ds0;

import java.util.function.BiFunction;
import java.util.function.Consumer;

public class XList<E> {
  private E _elem;
  private XListNode<E> _header;
  private XListNode<E> _trailer;
  private int _size;

  // constructors
  public XList() { init(); }

  public XList(XList<E> src) {
    copyNodes(src.first(), src._size);
  }

  public XList(XList<E> src, int start, int n) {
    copyNodes(this.get(start), n);
  }

  public XList(XListNode<E> p, int n) {
    copyNodes(p, n);
  }

  // private functions
  private void init() {
    _header = new XListNode<>();
    _trailer = new XListNode<>();
    _header.successor = _trailer;
    _header.predecessor = null;
    _trailer.successor = null;
    _trailer.predecessor = _header;
    _size = 0;
  }

  private int clear() {
    int oldSize = _size;
    while (_size > 0) {
      remove(_header.successor);
    }
    return oldSize;
  }

  private void copyNodes(XListNode<E> from, int n) {
    init();
    XListNode<E> _link = from;
    while (null != from && null != _link && n > 0) {
      this.insertAsLast(_link.elem);
      _link = _link.successor;
      n++;
    }
  }

  private void mergeSort(XListNode<E> one, int n, XListNode<E> two, int m) {

  }

  private void mergeSort(XListNode<E> from, int length) {
    
  }

  private void selectionSort(XListNode<E> from, int length) {

  }

  private void insertSort(XListNode<E> from, int length) {

  }


  // readable
  public int size() { return _size; }

  public boolean empty() { return _size <= 0; }

  public XListNode<E> get(int i) {
    XListNode<E> _link = first();
    while (null != _link && i > 0) {
      _link = _link.successor;
      i--;
    }
    return _link;
  }

  public XListNode<E> first() {
    return _header.successor;
  }

  public XListNode<E> last() {
    return _trailer.predecessor;
  }

  // 判断节点 node 是否对外合法
  public boolean valid(XListNode<E> node) {
    return (null != node) && (node != _header) && (node != _trailer);
  }

  // 判断节点 p 是否为 节点 q 的前驱, 并且距离不超过 n
  // public boolean valid(XListNode<E> p, int n, XListNode<E> q) {}

  // 判断节点 p 是否为 节点 q 的后继, 并且距离不超过 n
  // public boolean valid(XListNode<E> p, int n, XListNode<E> q) {}

  // 判断列表是否已经排序
  public int disOrdered(BiFunction<E, E, Integer> compare) {
    int s = 0;
    XListNode<E> _link = first();
    while (_link != _trailer) {
      if (0 == compare.apply(_link.elem, _link.successor.elem)) { s++; }
      _link = _link.successor;
    }
    return s;
  }

  // 无序列表查找(从左向右)
  public XListNode<E> find(E e, BiFunction<E, E, Integer> compare) {
    return find(e, first(), _size, compare);
  }

  // 无序区间查找(从左向右)
  public XListNode<E> find(E e, XListNode<E> start, int n, BiFunction<E, E, Integer> compare) {
    XListNode<E> _link = start;
    while (null != e && null != _link && n > 0) {
      if (0 == compare.apply(_link.elem, e)) { return _link; }
      _link = _link.successor;
      n--;
    }
    return null;
  }

  public XListNode<E> find(E e, XListNode<E> start, BiFunction<E, E, Integer> compare) {
    XListNode<E> _link = start;
    while (null != e && _trailer != _link) {
      if (0 == compare.apply(_link.elem, e)) { return _link; }
      _link = _link.successor;
    }
    return null;
  }

  // 有序列表查找
  public XListNode<E> search(E e, BiFunction<E, E, Integer> compare) {
    return search(e, first(), _size, compare);
  }

  // 有序区间查找
  public XListNode<E> search(E e, XListNode<E> start, int n, BiFunction<E, E, Integer> compare) {
    return find(e, start, n, compare);
  }

  // 在 p 及其 n - 1 个后继中选出最大者
  // public XListNode<E> selectMax(XListNode<E> p, int n) {}

  // 整体最大者
  // public XListNode<E> selectMax() {}

  
  // writable
  // 将 e 当作首节点插入
  public XListNode<E> insertAsFirst(E e) {
    _size++;
    return _header.insertAsSucc(e);
  }

  // 将 e 当作尾节点插入
  public XListNode<E> insertAsLast(E e) {
    _size++;
    return _trailer.insertAsPred(e);
  }

  // 将 e 当作p节点的前驱插入
  public XListNode<E> insertBefore(XListNode<E> p, E e) {
    _size++;
    return p.insertAsPred(e);
  }

  // 将 e 当作p节点的后继插入
  public XListNode<E> insertAfter(XListNode<E> p, E e) {
    _size++;
    return p.insertAsSucc(e);
  }

  // 删除
  public E remove(XListNode<E> p) {
    p.successor.predecessor = p.predecessor;
    p.predecessor.successor = p.successor;
    _size--;
    return p.elem;
  }

  // 有序列表区间归并
  public XList<E> merge(XListNode<E> p, int m, XListNode<E> q, int n) {
    return null;
  }
  
  // 全列表合并
  public XList<E> merge(XList<E> p) {
    return merge(this.first(), this._size, p.first(), p._size);
  }

  // 列表整体排序
  public void sort() {
    sort(first(), _size);
  }

  // 列表区间排序
  public void sort(XListNode<E> p, int n) {}
  
  // 无序去重
  public int deDuplicate(BiFunction<E, E, Integer> compare) {
    if (_size <= 1) { return 0; }
    int oldSize = _size;
    XListNode<E> _link = first();
    while (_link != _trailer) {
      XListNode<E> p = find(_link.elem, _link.successor, compare);
      if (null != p) {
        remove(p);
      } else {
        _link = _link.successor;
      }
    }
    return oldSize - _size;
  }

  // 有序去重
  public int uniquify(BiFunction<E, E, Integer> compare) {
    if (_size <= 1) { return 0; }
    int oldSize = _size;
    XListNode<E> _link = first();
    while (_link != _trailer) {
      int c = compare.apply(_link.elem, _link.successor.elem);
      if (0 == c) {
        remove(_link.successor);
      } else {
        _link = _link.successor;
      }
    }
    return oldSize - _size;
  }

  // traverse
  public void traverse(Consumer<E> consumer) {
    XListNode<E> _link = _header.successor;
    for (int i = 0; i < _size; i++) {
      consumer.accept(_link.elem);
      _link = _link.successor;
    }
  }
}
