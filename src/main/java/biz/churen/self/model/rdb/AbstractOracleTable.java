package biz.churen.self.model.rdb;

import org.springframework.stereotype.Service;

@Service("oracle")
public abstract class AbstractOracleTable<E extends IBean>
    extends AbstractTable<E> implements ITable<E> {
}
