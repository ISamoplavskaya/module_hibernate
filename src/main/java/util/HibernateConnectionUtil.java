package util;

import entity.Account;
import entity.Operation;
import entity.User;
import exeptions.HibernateSessionException;
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

            return sessionFactory.openSession();
        } catch (HibernateException e) {
            throw new HibernateSessionException("Error Hibernate session",e);
        }
    }

}
