package biz.churen.self.ds0;

public class XListNode<E> {
  public E elem;
  public XListNode<E> predecessor;
  public XListNode<E> successor;

  // constructors
  public XListNode() {  }

  public XListNode(E e, XListNode<E> predecessor, XListNode<E> successor) {
    elem = e;
    this.predecessor = predecessor;
    this.successor = successor;
  }

  // public functions

  public XListNode<E> insertAsPred(E e) {
    XListNode<E> newNode = new XListNode<>(e, null, null);
    newNode.successor = this;
    newNode.predecessor = predecessor;
    if (null != this.predecessor) {
      this.predecessor.successor = newNode;
    }
    this.predecessor = newNode;
    return newNode;
  }

  public XListNode<E> insertAsSucc(E e) {
    XListNode<E> newNode = new XListNode<>(e, null, null);
    newNode.successor = this.successor;
    newNode.predecessor = this;
    if (null != this.successor) {
      this.successor.predecessor = newNode;
    }
    this.successor = newNode;
    return newNode;
  }
}
