package util;

import entity.Account;
import entity.Operation;
import entity.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public final class HibernateConnectionUtil {
    private final static SessionFactory sessionFactory = buildSessionFactory();

    public static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure();
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Account.class);
        configuration.addAnnotatedClass(Operation.class);
        return configuration.buildSessionFactory();
    }
    public static Session openSession(){
        return sessionFactory.openSession();
}

}
