package dao.impl;

import dao.OperationDao;
import entity.Operation;


public class OperationDaoImpl extends GenericDaoImpl<Operation> implements OperationDao {
    public OperationDaoImpl(Class<Operation> type) {
        super(type);
    }

}
