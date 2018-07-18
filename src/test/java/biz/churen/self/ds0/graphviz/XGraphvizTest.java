package biz.churen.self.ds0.graphviz;

import biz.churen.self.ds0.XVector;
import org.junit.jupiter.api.Test;

import java.util.Collections;

class XGraphvizTest {
  @Test void doDrawVector() throws Exception {
    XVector<String> v = new XVector<>();
    v.insert("S"); v.insert("K"); v.insert("I");
    v.insert("A"); v.insert("B"); v.insert("C");
    v.insert("X"); v.insert("Y"); v.insert("Z");
    XGraphviz.doDrawVector(Collections.singletonList(v), "testVector.dot");
  }

  @Test void doDrawStack() {
  }

  @Test void doDrawQueue() {
  }

  @Test void doDrawBinaryTree() {
  }

  @Test void doDrawGraph() {
  }

}
