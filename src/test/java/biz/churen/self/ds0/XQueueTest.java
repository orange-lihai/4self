package biz.churen.self.ds0;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class XQueueTest {

  XQueue<String> queue = new XQueue<>(String.class);

  @Test void push() {
    queue.push("A");
    queue.push("B");
    queue.push("C");
    queue.push("D");
    queue.push("E");
    queue.push("F");

    Assertions.assertEquals(queue.size(), 6);
  }

  @Test void pop() {
    Optional<String> e = queue.pop();
    Assertions.assertEquals(e.orElse(""), "A");
  }

  @Test void peek() {
    Optional<String> e = queue.peek();
    Assertions.assertEquals(e.orElse(""), "B");
  }

  @Test void testAll() {
    push();
    pop();
    peek();
    Assertions.assertEquals(queue.size(), 5);
    queue.push("A");
    queue.push("A");
    queue.push("A");
    queue.push("A");
    queue.pop();
    queue.pop();
    queue.pop();
    queue.pop();
    queue.pop();
    queue.pop();
    Assertions.assertEquals(queue.size(), 3);
  }
}
