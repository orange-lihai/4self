package biz.churen.self.ds0;

public class XStack<E extends Comparable> extends XVector<E> {
  
  public XStack(Class<?> clazz) {
    super(clazz);
  }

  public void push(E e) {
    insert(size(), e);
  }

  public E pop() {
    return remove(size() - 1);
  }

  public E top() {
    return get(size() - 1);
  }
}
