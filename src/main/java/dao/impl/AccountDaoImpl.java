package dao.impl;

import dao.AccountDao;
import entity.Account;
import entity.Category;
import entity.Operation;
import exeptions.HibernateSessionException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateConnectionUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class AccountDaoImpl extends GenericDaoImpl<Account> implements AccountDao {
    public AccountDaoImpl(Class<Account> type) {
        super(type);
    }

    @Override
    public void createOperationForAccount(long accountID, Operation operation) {
        if (Validator.validateEntity(operation)) {
            try (Session session = HibernateConnectionUtil.getSession()) {
                session.beginTransaction();
                try {
                    Account account = session.get(Account.class, accountID);
                    if (account != null) {
                        BigDecimal balance = account.getBalance();
                        BigDecimal amount = operation.getAmount();
                        if (operation.getCategory() == Category.EXPENSE &&
                                balance.compareTo(amount) < 0) {
                            System.out.println("Insufficient funds! The operation cannot be added.");
                            return;
                        }
                        operation.setAccount(account);
                        account.getOperations().add(operation);

                        if (operation.getCategory() == Category.EXPENSE) {
                            account.setBalance(balance.subtract(amount));
                        } else if (operation.getCategory() == Category.INCOME) {
                            account.setBalance(balance.add(amount));
                        }
                        session.update(account);
                    } else {
                        System.out.println("Account not found!");
                    }
                    session.getTransaction().commit();
                } catch (Exception e) {
                    session.getTransaction().rollback();
                    throw new HibernateSessionException("(Error creating entity during a Hibernate session", e);
                }
            }
        } else System.out.println("Account validation failed. Unable to save or update operation.");
    }

    @Override
    public void deleteOperationByID(long accountID, long operationID) {
        try (Session session = HibernateConnectionUtil.getSession()) {
            session.beginTransaction();
            try {
                Account account = session.get(Account.class, accountID);
                Operation operation = session.get(Operation.class, operationID);
                if (account != null && operation != null) {
                    account.getOperations().remove(operation);
                    BigDecimal amount = operation.getAmount();
                    Category category = operation.getCategory();
                    if (category == Category.EXPENSE) {
                        account.setBalance(account.getBalance().add(amount));
                    } else if (category == Category.INCOME) {
                        if (account.getBalance().compareTo(amount) < 0) {
                            System.out.println("Insufficient funds! The operation cannot be deleted.");
                            return;
                        } else {
                            account.setBalance(account.getBalance().subtract(amount));
                        }
                    } else {
                        System.out.println("The category of the operation is not supported.");
                    }
                    session.remove(operation);
                    session.update(account);
                } else {
                    System.out.println("Operation not found.");
                }
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new HibernateSessionException("(Error deleting entity during a Hibernate session", e);
            }
        }
    }

    private static final String SELECT_OPERATION_BY_ACCOUNT = "FROM Operation WHERE account = :account";

    @Override
    public List<Operation> getOperationsByAccount(long accountID) {
        try (Session session = HibernateConnectionUtil.getSession()) {
            session.beginTransaction();
            Account account = session.get(Account.class, accountID);
            Query<Operation> query = session.createQuery(SELECT_OPERATION_BY_ACCOUNT, Operation.class);
            query.setParameter("account", account);
            return query.list();
        }
    }

    private static final String SELECT_OPERATION_IN_PERIOD = "FROM Operation WHERE account = :account AND operationDate BETWEEN :startDate AND :endDate";

    @Override
    public List<Operation> getOperationInPeriodByAccount(long accountID, LocalDateTime startDate, LocalDateTime endDate) {
        try (Session session = HibernateConnectionUtil.getSession()) {
            session.beginTransaction();
            Account account = session.get(Account.class, accountID);
            Query<Operation> query = session.createQuery(SELECT_OPERATION_IN_PERIOD, Operation.class);
            query.setParameter("account", account);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            return query.list();
        }
    }
}