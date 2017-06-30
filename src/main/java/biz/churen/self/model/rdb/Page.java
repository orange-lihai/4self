package biz.churen.self.model.rdb;

import java.util.ArrayList;
import java.util.List;

public class Page<R> {
  public int pageNo;
  public int pageSize;
  public List<R> data;

  public int totalNum;
  public int totalPage;
  public int offset;

  @SuppressWarnings("unused")
  public Page() {
    this(1, 10);
  }
  public Page(int pageNo, int pageSize) {
    this.pageNo = pageNo;
    this.pageSize = pageSize;
    this.data = new ArrayList<>();
  }

  public int getOffset() {
    this.offset = (this.pageNo - 1) * this.pageSize;
    return this.offset;
  }
  @SuppressWarnings("unused")
  public void setData(List<R> d) { this.data = d; }
  public void setData(List<R> d, int totalNum) {
    this.data = d;
    this.totalNum = totalNum;
    this.totalPage = totalNum % pageSize > 0 ? totalNum / pageSize + 1 : totalNum / pageSize;
  }
}
