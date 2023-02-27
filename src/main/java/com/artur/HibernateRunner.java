package com.artur;
import com.artur.util.HibernateUtil;

public class HibernateRunner {
    public static void main(String[] args) {

        try (var sessionFactory = HibernateUtil.buildSessionFactory();
            var session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.getTransaction().commit();
        }
    }
}
