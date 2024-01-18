package util;

import entity.Account;
import entity.Operation;
import entity.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public final class HibernateConnectionUtil {
    public static Session getSession() {
        try {
            Configuration configuration = new Configuration().configure();
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Account.class);
            configuration.addAnnotatedClass(Operation.class);
            SessionFactory sessionFactory = configuration.buildSessionFactory();

            Session session = sessionFactory.openSession();
            return session;
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

}
