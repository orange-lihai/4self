package biz.churen.self.ds0;

import java.util.function.Consumer;

/**
 * Abstract Data Type Of Vector, Extends Of Array
 */
public interface XIVector<E> {
  public Integer size();
  public Integer get(Integer rank);
  public void put(Integer rank, E e);
  public void insert(Integer rank, E e);
  public E remove(Integer rank);
  public Boolean disOrdered();
  public void sort();
  public E find(E e);
  public E search(E e);
  public void deDuplicate();
  public void uniquify();
  public void traverse(Consumer<E> consumer);
}
