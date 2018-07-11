package biz.churen.self.ds0.graph;

public class XVertex<V> {
  public V data;
  public int inDegree;
  public int outDegree;
  public XAbstractGraph.VStatus status;
  public int dTime;
  public int fTime;
  public int parent;
  public int priority;

  public XVertex(V v) {
    data = v;
    inDegree = 0; outDegree = 0; status = XAbstractGraph.VStatus.UNDISCOVERED;
    dTime = -1; fTime = -1; parent = -1; priority = Integer.MAX_VALUE;
  }
}
