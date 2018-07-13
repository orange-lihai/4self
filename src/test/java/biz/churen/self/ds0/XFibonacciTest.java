package biz.churen.self.ds0;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by lihai5 on 2018/6/5.
 */
class XFibonacciTest {

  @Test void testAll() {
    // 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, ...
    XFibonacci fibonacci = new XFibonacci(128);
    assertEquals(10, fibonacci.current());
    int f = fibonacci.pre();
    assertEquals(f, 55);
    f = fibonacci.next();
    assertEquals(f, 89);

    assertEquals(fibonacci.current(), 10);

    fibonacci.setCurrent(4);
    f = fibonacci.get();
    assertEquals(f, 5);
  }
}
