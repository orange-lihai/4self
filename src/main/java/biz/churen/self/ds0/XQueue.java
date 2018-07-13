package biz.churen.self.ds0;

import java.lang.reflect.Array;
import java.util.Optional;

public class XQueue<E> {
  private static final int DEFAULT_CAPACITY = 3;
  private int _capacity;
  private E[] _elem;
  private int _header;
  private int _trailer;

  @SuppressWarnings({"unchecked"})
  public XQueue(Class<?> clazz) {
    _capacity = DEFAULT_CAPACITY;
    _header = _trailer = 0;
    _elem = (E[])(Array.newInstance(clazz, _capacity));
  }

  @SuppressWarnings({"unchecked"})
  public XQueue() {
    _capacity = DEFAULT_CAPACITY;
    _header = _trailer = 0;
    _elem = (E[]) (Array.newInstance(Object.class, _capacity));
  }

    @SuppressWarnings({"unchecked"})
  private void dilation() {
    int _size = size();
    int _trailerSize = _capacity - _trailer;
    if (_size == 0) { return; }

    if (_trailerSize <= _capacity * 0.25 || _header >= _capacity * 0.25) {
      E[] _new = (E[])(Array.newInstance(_elem[0].getClass(), _size * 2));
      /*int j = 0;
      for (int i = _header; i < _trailer; i++) {
        _new[j++] = _elem[i];
      }*/
      System.arraycopy(_elem, _header, _new, 0, _size);
      _elem = _new;
      _header = 0;
      _trailer = _size;
      _capacity = _size * 2;
    }
  }

  public void push(E e) {
    dilation();
    _elem[_trailer] = e;
    _trailer += 1;
  }

  public Optional<E> pop() {
    dilation();
    E e = null;
    if (size() > 0) {
      e = _elem[_header];
      _header += 1;
    }
    return Optional.ofNullable(e);
  }

  public E pop2() {
    dilation();
    E e = null;
    if (size() > 0) {
      e = _elem[_header];
      _header += 1;
    }
    return e;
  }

  public Optional<E> peek() {
    E e = (size() > 0) ? _elem[_header] : null;
    return Optional.ofNullable(e);
  }

  public int size() {
    return (_trailer - _header);
  }
}
