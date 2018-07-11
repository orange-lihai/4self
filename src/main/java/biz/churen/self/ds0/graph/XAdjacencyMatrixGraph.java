package biz.churen.self.ds0.graph;

import biz.churen.self.ds0.XQueue;
import biz.churen.self.ds0.XStack;
import biz.churen.self.ds0.XVector;

import java.util.Optional;

public class XAdjacencyMatrixGraph<V, E> extends XAbstractGraph<V, E> {
  private XVector<XVertex<V>> vertexs;
  private XVector<XVector<XEdge<E>>> edges;

  public XAdjacencyMatrixGraph() {
    n = e = 0;
    vertexs = new XVector<>();
    edges = new XVector<>();
  }

  @Override public int insert(V v) {
    for (int i = 0; i < n; i++) { edges.get(i).insert(null); } // 原来的每个顶点对应的边向量都新加一条边
    n++; // 节点总数加 1
    edges.insert(new XVector<>()); // 新增, 新节点对应的边向量
    for (int i = 0; i < n; i++) {
      edges.get(n-1).insert(null);
    } // 新增的边, 默认都为 null
    return vertexs.insert(new XVertex<>(v)); // 插入行
  }

  @Override public V remove(int a) {
    for (int i = 0; i < n; i++) {
      if (exists(i, a)) { e--; vertexs.get(i).outDegree--; }
      if (exists(a, i)) { e--; vertexs.get(i).inDegree--; }
    }
    if (exists(a, a)) { e++; }
    edges.remove(a);
    n--;
    for (int i = 0; i < n; i++) { edges.get(i).remove(a); }
    XVertex<V> _delV = vertexs.remove(a);
    return _delV.data;
  }

  @Override public V vertex(int a) {
    return vertexs.get(a).data;
  }

  @Override public int inDegree(int a) {
    return vertexs.get(a).inDegree;
  }

  @Override public int outDegree(int a) {
    return vertexs.get(a).outDegree;
  }

  @Override public int firstNeighbor(int a) {
    return nextNeighbor(a, n);
  }

  @Override public int nextNeighbor(int a, int b) { // 顶点 a 的(相对于顶点 b)下一邻接节点
    while ((-1 < b) && (!exists(a, --b)));
    return b;
  }

  @Override public VStatus status(int a) {
    return vertexs.get(a).status;
  }

  @Override public int dTime(int a) {
    return vertexs.get(a).dTime;
  }

  @Override public int fTime(int a) {
    return vertexs.get(a).fTime;
  }

  @Override public int parent(int a) {
    return vertexs.get(a).parent;
  }

  @Override public int priority(int a) {
    return vertexs.get(a).priority;
  }

  @Override public boolean exists(int a, int b) {
    return (0 <= a) && (a <= n) && (0 <= b) && (b <= n)
           && null != edges.get(a).get(b);
  }

  @Override public void insert(E e, int a, int b, int w) {
    if (!exists(a, b)) {
      edges.get(a).put(b, new XEdge<>(e, w));
      vertexs.get(a).outDegree++;
      vertexs.get(b).inDegree++;
      this.e++;
    }
  }

  @Override public E remove(int a, int b) {
    if (exists(a, b)) {
      E _e = edges.get(a).get(b).data;
      edges.get(a).put(b, null);
      vertexs.get(a).outDegree--;
      vertexs.get(b).inDegree--;
      this.e--;
      return _e;
    } else {
      return null;
    }
  }

  @Override public EStatus status(int a, int b) {
    return edges.get(a).get(b).status;
  }

  @Override public E edge(int a, int b) {
    return edges.get(a).get(b).data;
  }

  @Override public int weight(int a, int b) {
    return edges.get(a).get(b).weight;
  }


  // algorithms

  private void reset() {
    for (int i = 0; i < n; i++) {
      XVertex<V> curV = vertexs.get(i);
      curV.dTime = -1; curV.fTime = -1;
      curV.priority = Integer.MAX_VALUE; curV.parent = -1;
      curV.status = VStatus.UNDISCOVERED;
      for (int j = 0; j < n; j++) {
        Optional.ofNullable(edges.get(i).get(j))
                .ifPresent(p -> p.status = EStatus.UNDETERMINED);
      }
    }
  }

  // breadth first search
  @Override public void bfs(int a) {
    reset();
    int v = a;
    int[] clock = new int[]{0};
    do {
      if (VStatus.UNDISCOVERED == status(v)) {
        BFS(v, clock);
      }
    } while (a != (v = (++v % this.n)));
    
  }

  private void BFS(Integer v, int[] clock) {
    XQueue<Integer> queue = new XQueue<>();
    XVertex<V> curV = vertexs.get(v); curV.status = VStatus.DISCOVERED; // 发现初始节点
    queue.push(v);
    while (queue.size() > 0) {
      v = queue.pop2(); vertexs.get(v).dTime = ++clock[0];
      for (int u = firstNeighbor(v); u >= 0; u = nextNeighbor(v, u)) {
        if (VStatus.UNDISCOVERED == vertexs.get(u).status) {
          vertexs.get(u).parent = v;
          queue.push(u);
          vertexs.get(u).status = VStatus.DISCOVERED;
          edges.get(v).get(u).status = EStatus.TREE;
        } else {
          edges.get(v).get(u).status = EStatus.CROSS;
        }
      }
      vertexs.get(v).status = VStatus.VISITED;
      System.out.print(vertexs.get(v).data); System.out.print(" ");
    }
  }

  // depth first search
  @Override public void dfs(int a) {

  }

  // bi-connected component
  @Override public void bcc(int a) {

  }

  @Override public XStack<V> topologicalSort(int a) {
    return null;
  }

  @Override public void prim(int a) {

  }

  @Override public void dijkstra(int a) {

  }
}
