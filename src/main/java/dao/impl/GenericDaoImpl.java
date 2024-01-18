package dao.impl;

import dao.GenericDao;
import org.hibernate.Session;
import util.HibernateConnectionUtil;

import java.util.List;
import java.util.Optional;

public class GenericDaoImpl<T> implements GenericDao<T> {
    protected Class<T> typeClass;

    public GenericDaoImpl(Class<T> type) {
        this.typeClass = type;
    }

    @Override
    public Optional<T> findByID(long id) {
        try (Session session = HibernateConnectionUtil.getSession()) {
            session.beginTransaction();
                T object = session.get(typeClass, id);
                if (object == null) {
                    System.out.println("not found by id " + id);
                    session.getTransaction().commit();
                    return Optional.empty();
                }
                session.getTransaction().commit();
                return Optional.of(object);
            }
        }

        @Override
        public List<T> findAll () {
            List<T> allEntity;
            try (Session session = HibernateConnectionUtil.getSession()) {
                session.beginTransaction();
                try {
                    String queryFindAll = "FROM " + typeClass.getSimpleName();
                    allEntity = session.createQuery(queryFindAll, typeClass).getResultList();
                    session.getTransaction().commit();
                } catch (Exception e) {
                    session.getTransaction().rollback();
                    throw new RuntimeException(e);
                }
            }
            return allEntity;
        }

        @Override
        public void save (T entity){
            if (Validator.validateEntity(entity)) {
                try (Session session = HibernateConnectionUtil.getSession()) {
                    session.beginTransaction();
                    try {
                        session.persist(entity);
                        session.getTransaction().commit();
                    } catch (Exception e) {
                        session.getTransaction().rollback();
                        throw new RuntimeException(e);
                    }
                }
            } else System.out.println("Validation failed. Unable to save " + entity.getClass().getSimpleName());
        }

        @Override
        public void update (T entity){
            if (Validator.validateEntity(entity)) {
                try (Session session = HibernateConnectionUtil.getSession()) {
                    session.beginTransaction();
                    try {
                        session.update(entity);
                        session.getTransaction().commit();
                    } catch (Exception e) {
                        session.getTransaction().rollback();
                        throw new RuntimeException(e);
                    }
                }
            } else System.out.println("Validation failed. Unable to update " + entity.getClass().getSimpleName());
        }

        @Override
        public void delete (T entity){
            try (Session session = HibernateConnectionUtil.getSession()) {
                session.beginTransaction();
                try {
                    session.remove(entity);
                    session.getTransaction().commit();
                } catch (Exception e) {
                    session.getTransaction().rollback();
                    throw new RuntimeException(e);
                }
            }
        }
    }
