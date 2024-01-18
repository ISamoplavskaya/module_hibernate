package dao.impl;

import dao.AccountDao;
import entity.Account;
import entity.Category;
import entity.Operation;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateConnectionUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                    throw new RuntimeException(e);
                }
            }
        } else System.out.println("Account validation failed. Unable to save or update operation.");
    }

    @Override
    public List<Operation> getOperationsByAccount(long accountID) {
        try (Session session = HibernateConnectionUtil.getSession()) {
            session.beginTransaction();
            Account account = session.get(Account.class, accountID);
            Query<Operation> query = session.createQuery("FROM Operation WHERE account = :account", Operation.class);
            query.setParameter("account", account);
            return query.list();
        }
    }

    @Override
    public List<Operation> getOperationInPeriodByAccount(long accountID, LocalDateTime startDate,LocalDateTime endDate) {
        try (Session session = HibernateConnectionUtil.getSession()) {
            session.beginTransaction();
            Account account = session.get(Account.class, accountID);
            Query<Operation> query = session.createQuery("FROM Operation WHERE account = :account AND operationDate BETWEEN :startDate AND :endDate", Operation.class);
            query.setParameter("account", account);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);

            return query.list();
        }
    }

}
