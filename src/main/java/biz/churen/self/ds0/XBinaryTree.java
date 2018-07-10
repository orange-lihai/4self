package biz.churen.self.ds0;

import java.util.Optional;
import java.util.function.Consumer;

public class XBinaryTree<T> {
  private int _size;
  private XBinaryNode<T> _root;
  
  // rectify
  private int updateHight(XBinaryNode<T> x) {
    x.hight = 1 + Math.max(XBinaryNode.stature(x.leftChild), XBinaryNode.stature(x.rightChild));
    return x.hight;
  }

  private void updateHightAbove(XBinaryNode<T> x) {
    while (null != x) {
      updateHight(x);
      x = x.parent;
    }
  }

  // constructors
  public XBinaryTree() {
    this._size = 0;
    this._root = null;
  }

  // functions
  public int size() { return _size; }

  public XBinaryNode<T> root() { return _root; }

  public boolean empty() { return null == _root; }

  public XBinaryNode<T> insertAsRoot(T e) {
    _size = 1;
    return (_root = new XBinaryNode<>(e));
  }

  public XBinaryNode<T> insertAsLeftChild(XBinaryNode<T> x, T e) {
    _size++;
    x.insertAsLeftChild(e);
    updateHightAbove(x);
    return x.leftChild;
  }

  public XBinaryNode<T> insertAsRightChild(XBinaryNode<T> x, T e) {
    _size++;
    x.insertAsRightChild(e);
    updateHightAbove(x);
    return x.rightChild;
  }

  public XBinaryNode<T> attachAsRightChild(XBinaryNode<T> x, XBinaryTree<T> t) {
    if (null == t) { return x; }
    x.leftChild = t._root;
    t._root.parent = x;
    _size += t._size;
    updateHightAbove(x);
    return x;
  }

  public XBinaryNode<T> attachAsLeftChild(XBinaryNode<T> x, XBinaryTree<T> t) {
    if (null == t) { return x; }
    x.rightChild = t._root;
    t._root.parent = x;
    _size += t._size;
    updateHightAbove(x);
    return x;
  }

  public int remove(XBinaryNode<T> x) {
    XBinaryNode<T> p = x.parent;
    if (p.leftChild == x) { p.leftChild = null; }
    if (p.rightChild == x) { p.rightChild = null; }
    updateHightAbove(p);
    int n = x.size();
    _size -= n;
    return n;
  }

  public XBinaryTree<T> secede(XBinaryNode<T> x) {
    XBinaryNode<T> p = x.parent;
    if (p.leftChild == x) { p.leftChild = null; }
    if (p.rightChild == x) { p.rightChild = null; }
    updateHightAbove(p);
    int n = x.size();
    _size -= n;

    XBinaryTree<T> newTree = new XBinaryTree<>();
    newTree._root = x; newTree._size = n;
    return newTree;
  }

  public void travelByLevel(Consumer<T> consumer) {
    if (null == _root || null == _root.data) { return; }
    XQueue<XBinaryNode<T>> queue = new XQueue<>(_root.getClass());
    queue.push(_root);
    while (queue.size() > 0) {
      Optional<XBinaryNode<T>> rootOpt = queue.pop();
      rootOpt.ifPresent(root -> {
        consumer.accept(root.data);
        if (null != root.leftChild) { queue.push(root.leftChild); }
        if (null != root.rightChild) { queue.push(root.rightChild); }
      });
    }
  }

  // 先序遍历, 递归版本, 不推荐.
  @Deprecated
  @SuppressWarnings({"unused"})
  private void travelByPreorder(Consumer<T> consumer, XBinaryNode<T> node) {
    if (null == node || null == node.data) { return; }
    consumer.accept(node.data);
    travelByPreorder(consumer, node.leftChild);
    travelByPreorder(consumer, node.rightChild);
  }

  public void travelByPreorder(Consumer<T> consumer) {
    if (null == _root) { return; }
    // 递归调用 // travelByPreorder(consumer, _root);
    // 非递归调用
    XStack<XBinaryNode<T>> stack = new XStack<>();
    stack.push(_root);
    while (!stack.empty()) {
      XBinaryNode<T> node = stack.pop();
      consumer.accept(node.data);
      if (null != node.rightChild) { stack.push(node.rightChild); }
      if (null != node.leftChild) { stack.push(node.leftChild); }
    }
  }

  public void travelByPreorder2(Consumer<T> consumer) {
    if (null == _root) { return; }
    XStack<XBinaryNode<T>> stack = new XStack<>();
    XBinaryNode<T> r = root();
    while (true) {
      travelByLeftBranch(r, stack, consumer);
      if (stack.empty()) { break; }
      r = stack.pop();
    }
  }

  private void travelByLeftBranch(XBinaryNode<T> node, XStack<XBinaryNode<T>> stack, Consumer<T> consumer) {
    while (null != node) {
      consumer.accept(node.data);
      if (null != node.rightChild) { stack.push(node.rightChild); }
      node = node.leftChild;
    }
  }
  
  public void travelByInorder(Consumer<T> consumer) {
    if (null == _root) { return; }
    XBinaryNode<T> r = _root;
    XStack<XBinaryNode<T>> stack = new XStack<>();
    while (true) {
      goByLeftBranch(r, stack);
      if (stack.empty()) { break; }
      r = stack.pop();
      consumer.accept(r.data);
      r = r.rightChild;
    }
  }

  private void goByLeftBranch(XBinaryNode<T> node, XStack<XBinaryNode<T>> stack) {
    while (null != node) {
      stack.push(node);
      node = node.leftChild;
    }
  }

  public void travelByPostorder(Consumer<T> consumer) {
    if (null == _root) { return; }
    XStack<XBinaryNode<T>> stack = new XStack<>();
    XBinaryNode<T> r = _root;
    while (true) {
      goByLeftRight(r, stack);
      if (stack.empty()) { break; }
      r = stack.pop();
      consumer.accept(r.data);
      if (null != r.parent && r.parent.rightChild != r) {
        r = r.parent.rightChild;
      } else {
        r = null;
      }
    }
  }

  // highest leaf visible from left;
  private void goByLeftRight(XBinaryNode<T> node, XStack<XBinaryNode<T>> stack) {
    while (null != node) {
      stack.push(node);
      if (null != node.leftChild) {
        node = node.leftChild;
      } else {
        node = node.rightChild;
      }
    }
  }
}
