package biz.churen.self.model.rdb;

import org.springframework.stereotype.Service;

@Service("oracle_virtual")
public abstract class AbstractOracleVirtualTable<E extends IBean>
    extends AbstractTable<E> implements ITable<E> {
}
