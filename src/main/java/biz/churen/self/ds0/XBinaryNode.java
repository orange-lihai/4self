package biz.churen.self.ds0;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Consumer;

public class XBinaryNode<T> implements Comparable<T> {
  @Override public int compareTo(T o) {
    return 0;
  }

  public enum RBColor { RED, BLACK };
  
  public T data;
  public XBinaryNode<T> parent;
  public XBinaryNode<T> leftChild;
  public XBinaryNode<T> rightChild;
  public int hight;
  public int npl; // null path length
  public RBColor color; // RED, BLACK

  // constructors
  public XBinaryNode() {
    data = null;
    parent = null; leftChild = null; rightChild = null;
    hight = 0; npl = 0; color = null;
  }

  public XBinaryNode(T e) {
    this.data = e;
  }

  public XBinaryNode(T e, XBinaryNode<T> parent) {
    this.data = e;
    this.parent = parent;
    this.hight = 1; this.npl = 1; this.color = null;
  }

  public XBinaryNode(T e,
    XBinaryNode<T> parent, XBinaryNode<T> leftChild, XBinaryNode<T> rightChild,
    int hight, int npl, RBColor color
  ) {
    this.data = e;
    this.parent = parent; this.leftChild = leftChild; this.rightChild = rightChild;
    this.hight = hight; this.npl = npl; this.color = color;
  }
  
  // operators
  public int size() {
    int size = 1;
    if (null != leftChild) {
      size += leftChild.size();
    }
    if (null != rightChild) {
      size += rightChild.size();
    }
    return size;
  }

  public XBinaryNode<T> insertAsLeftChild(T e) {
    return (this.leftChild = new XBinaryNode<>(e, this));
  }

  public XBinaryNode<T> insertAsRightChild(T e) {
    return (this.rightChild = new XBinaryNode<>(e, this));
  }

  public XBinaryNode<T> successor() {
    return null != leftChild ? leftChild : rightChild;
  }

  public void travelByLevel(Consumer<T> consumer) {}

  public void travelByPreorder(Consumer<T> consumer) {}

  public void travelByInorder(Consumer<T> consumer) {}

  public void travelByPostorder(Consumer<T> consumer) {}

  // comparators
  public int compare(XBinaryNode<T> e, Comparator<? super T> c) {
    return Objects.compare(this.data, e.data, c);
  }

  // statics
  public static <T> int stature(XBinaryNode<T> t) {
    return null == t ? -1 : t.hight;
  }
}
