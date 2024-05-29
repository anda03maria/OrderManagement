package org.example.repository.hibernate;

import org.example.model.Admin;
import org.example.repository.AdminsRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class AdminsDbOrmRepository implements AdminsRepository {

    private static SessionFactory sessionFactory;

    public AdminsDbOrmRepository() {
        try {
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e)
        {
            System.out.println("Exceptie: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize session factory.");
            //StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    @Override
    public Admin findById(String s) {
        Admin admin = new Admin();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                admin = session.createQuery("from Admin where id = ?1", Admin.class)
                        .setParameter(1, s)
                        .setMaxResults(1)
                        .uniqueResult();
                System.out.println(admin);
                tx.commit();
                return admin;
            } catch (RuntimeException ex) {
                if (tx != null) {
                    tx.rollback();
                }
            }
        }
        return null;
    }

    @Override
    public List<Admin> findAll() {
        return null;
    }

    @Override
    public boolean save(Admin entity) {
        return false;
    }

    @Override
    public Admin delete(String s) {
        return null;
    }

    @Override
    public boolean update(Admin entity, String s) {
        return false;
    }
}
