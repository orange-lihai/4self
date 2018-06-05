package biz.churen.self.ds0;

public class XFibonacci {
  private int _size;
  private int[] _elem;
  private int _current;

  public XFibonacci(int maxNum) {
    assert maxNum >= 1;
    _elem = new int[64];
    _elem[0] = 1;
    _elem[1] = 1;

    int f0 = _elem[0], f1 = _elem[1], f = f0 + f1;
    int i = 2;
    while (f <= maxNum) {
      _elem[i++] = f;
      f0 = f1;
      f1 = f;
      f = f0 + f1;
    }
    _size = i;
    _current = _size - 1;
  }

  public int next() {
    int _next = _current + 1;
    assert 0 <= _next && _next < _size;
    _current = _next;
    return _elem[_next];
  }

  public int pre() {
    int _pre = _current - 1;
    assert 0 <= _pre && _pre < _size;
    _current = _pre;
    return _elem[_pre];
  }

  public int get() {
    return _elem[_current];
  }

  public int setCurrent(int current) {
    assert 0 <= current && current < _size;
    _current = current;
    return _current;
  }

  public int size() {
    return _size;
  }

  public int current() {
    return _current;
  }
}
