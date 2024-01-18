package dao.impl;

import dao.UserDao;
import entity.Account;
import entity.Operation;
import entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateConnectionUtil;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {
    public UserDaoImpl(Class<User> type) {
        super(type);
    }

    @Override
    public void createAccountForUser(long userID, Account account) {
        if (Validator.validateEntity(account)) {
            try (Session session = HibernateConnectionUtil.getSession()) {
                session.beginTransaction();
                try {
                    User user = session.get(User.class, userID);
                    if (user != null) {
                        account = (Account) session.merge(account);
                        account.setUser(user);
                        user.getAccounts().add(account);
                        session.saveOrUpdate(user);
                    } else {
                        System.out.println("User not found!");
                    }
                    session.getTransaction().commit();
                } catch (Exception e) {
                    session.getTransaction().rollback();
                    throw new RuntimeException(e);
                }
            }
        } else System.out.println("Validation failed. Unable to save account");
    }

    @Override
    public List<Account> getAccountsByUser(long userID) {
        try (Session session = HibernateConnectionUtil.getSession()) {
            session.beginTransaction();
            User user = session.get(User.class, userID);
            Query<Account> query = session.createQuery("FROM Account WHERE user = :user", Account.class);
            query.setParameter("user", user);
            session.getTransaction().commit();
            return query.list();
        }
    }
    @Override
    public List<Operation> getOperationsByUser(long userID) {
        try (Session session = HibernateConnectionUtil.getSession()) {
            session.beginTransaction();
            User user=session.get(User.class,userID);
            Query<Operation> query = session.createQuery("SELECT o FROM User u JOIN u.accounts a JOIN a.operations o WHERE u = :user", Operation.class);
            query.setParameter("user", user);
            return query.list();
        }
    }


}
