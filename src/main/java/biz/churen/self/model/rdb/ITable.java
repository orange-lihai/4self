package biz.churen.self.model.rdb;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ITable<E extends IBean> {
  String columnsMetaSql();
  String uniqueKeysMetaSql();
  String primaryKeysMetaSql();

  List<RColumn> columns(Class<E> clazz);
  Set<String> columnsSet(Class<E> clazz);
  List<RColumn> pks(Class<E> clazz);
  Map<String, List<RColumn>> ukMap(Class<E> clazz);

  Integer queryCount(IQuery<E> query);
  Integer queryCount(String sql);
  Integer queryCount(String sql, Object[] args);

  public Page<E> queryPage(IQuery<E> query, Page<E> page);
  public List<E> queryList(IQuery<E> query, Integer maxNum);
  public List<E> queryList(IQuery<E> query);
  public List<E> queryList(String sql, Class<E> clazz);
  public List<E> queryList(String sql, Object[] args, Class<E> clazz);
  public <T> List<T> queryListT(String sql, Object[] args, Class<T> clazz);

  public E queryOneByPK(E clazz, Object... params);
  public List<E> queryOne(E e);
  public List<E> queryOne(IQuery<E> query);
  public List<E> queryOne(String sql, Class<E> clazz);
  public List<E> queryOne(String sql, Object[] args, Class<E> clazz);

  public int insert(List<E> list);
  public int insert(E e);

  public int delete(E e);
  public int delete(List<E> list);

  public int update(E e, boolean allReplace);
  public int update(E e);
  public int update(List<E> list);
  public int update(List<E> list, boolean allReplace);

  public void execute(List<String> sql);
  public void execute(String sql);
}
