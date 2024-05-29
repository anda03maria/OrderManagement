package org.example.repository.hibernate;

import org.example.model.Order;
import org.example.repository.OrdersRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class OrdersDbOrmRepository implements OrdersRepository {

    private static SessionFactory sessionFactory;

    public OrdersDbOrmRepository() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e)
        {
            System.out.println("Exceptie: " + e.getMessage());
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    @Override
    public Order findById(Integer integer) {
        return null;
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public boolean save(Order entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(entity);
                tx.commit();
                return true;
            } catch (RuntimeException exception) {
                System.err.println("Error while saving order " + exception);
                if (tx != null) {
                    tx.rollback();
                }
            }
        }
        return false;
    }

    @Override
    public Order delete(Integer integer) {
        return null;
    }

    @Override
    public boolean update(Order entity, Integer integer) {
        return false;
    }
}
