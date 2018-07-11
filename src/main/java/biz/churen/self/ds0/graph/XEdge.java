package biz.churen.self.ds0.graph;

public class XEdge<E> {
  public E data;
  public int weight;
  public XAbstractGraph.EStatus status;

  // adjacency list
  public XVertex from;
  public XVertex to;

  public XEdge(E e, int weight) {
    this.data = e;
    this.weight = weight;
    this.status = XAbstractGraph.EStatus.UNDETERMINED;
  }
}
