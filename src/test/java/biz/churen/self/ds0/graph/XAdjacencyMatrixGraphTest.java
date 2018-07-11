package biz.churen.self.ds0.graph;

import org.junit.jupiter.api.Test;


class XAdjacencyMatrixGraphTest {

  private XAdjacencyMatrixGraph<String, String> beforeTest() {
    XAdjacencyMatrixGraph<String, String> graph = new XAdjacencyMatrixGraph<>();
    int a = graph.insert("A"); int b = graph.insert("B"); int c = graph.insert("C");
    int d = graph.insert("D"); int e = graph.insert("E"); int f = graph.insert("F");
    int g = graph.insert("G"); int s = graph.insert("S");

    graph.insert("s -> a", s, a, (int)(Math.random() * 100));
    graph.insert("s -> c", s, c, (int)(Math.random() * 100));
    graph.insert("s -> d", s, d, (int)(Math.random() * 100));

    graph.insert("a -> e", a, e, (int)(Math.random() * 100));
    graph.insert("a -> c", a, c, (int)(Math.random() * 100));

    graph.insert("c -> b", c, b, (int)(Math.random() * 100));

    graph.insert("d -> b", d, b, (int)(Math.random() * 100));

    graph.insert("e -> f", e, f, (int)(Math.random() * 100));
    graph.insert("e -> g", e, g, (int)(Math.random() * 100));

    graph.insert("f -> g", f, g, (int)(Math.random() * 100));
    graph.insert("g -> b", g, b, (int)(Math.random() * 100));
    return graph;
  }

  @Test void bfs() {
    XAdjacencyMatrixGraph<String, String> graph = beforeTest();
    graph.bfs(graph.n - 1);
  }

  @Test void dfs() {
  }

  @Test void bcc() {
  }

  @Test void topologicalSort() {
  }

  @Test void prim() {
  }

  @Test void dijkstra() {
  }

}
