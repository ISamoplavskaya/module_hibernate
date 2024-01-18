package dao.impl;


import dao.OperationDao;
import entity.Account;
import entity.Category;
import entity.Operation;
import org.hibernate.Session;
import util.HibernateConnectionUtil;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class OperationDaoImpl extends GenericDaoImpl<Operation> implements OperationDao {
    public OperationDaoImpl(Class<Operation> type) {
        super(type);
    }



}
