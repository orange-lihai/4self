package biz.churen.self.ds0.graph;

import biz.churen.self.ds0.XStack;

public abstract class XAbstractGraph<V, E> {
  public enum VStatus {UNDISCOVERED, DISCOVERED, VISITED};
  public enum EStatus {UNDETERMINED, TREE, CROSS, FORWARD, BACKWARD};

  // vertex
  public int n;
  public abstract int insert(V v);  // 插入顶点, 返回编号
  public abstract V remove(int a);
  public abstract V vertex(int a);
  public abstract int inDegree(int a);
  public abstract int outDegree(int a);
  public abstract int firstNeighbor(int a);
  public abstract int nextNeighbor(int a, int b);
  public abstract VStatus status(int a);
  public abstract int dTime(int a);
  public abstract int fTime(int a);
  public abstract int parent(int a);
  public abstract int priority(int a);


  // edge
  public int e;
  public abstract boolean exists(int a, int b); // if exists edge (a, b)
  public abstract void insert(E e, int a, int b, int w); // insert a edge in (a, b), weight is w
  public abstract E remove(int a, int b); // remove the edge of (a, b)
  public abstract EStatus status(int a, int b); // the status of edge (a, b)
  public abstract E edge(int a, int b); // the edge (a, b)
  public abstract int weight(int a, int b); // the weight of edge (a, b)

  // algorithms
  public abstract void bfs(int a);
  public abstract void dfs(int a);
  public abstract void bcc(int a);
  public abstract XStack<V> topologicalSort(int a);

  public abstract void prim(int a);
  public abstract void dijkstra(int a);

  // template <typename PU> // 优先级更新器(函数对象)
  // void pfs(int a, PU); // 优先级搜索算法
}
