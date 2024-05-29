package org.example.repository.hibernate;

import org.example.model.Product;
import org.example.repository.ProductsRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;

public class ProductsDbOrmRepository implements ProductsRepository {

    private static SessionFactory sessionFactory;

    public ProductsDbOrmRepository() {
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
    public Product findById(Integer integer) {
        Product product = new Product();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                product = session.createQuery("from Product where id = ?1", Product.class)
                        .setParameter(1, integer)
                        .setMaxResults(1)
                        .uniqueResult();
                tx.commit();
                return product;
            } catch (RuntimeException exception) {
                System.err.println("Error while finding product " + exception);
                if (tx != null) {
                    tx.rollback();
                }
            }
        }
        return null;
    }

    @Override
    public List<Product> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Product> products =
                        session.createQuery("from Product as p order by p.id asc", Product.class)
                                .list();
                tx.commit();
                return products;
            } catch (RuntimeException exception) {
                System.err.println("Error while finding all products " + exception);
                if (tx != null) {
                    tx.rollback();
                }
            }
        }
        return new ArrayList<>();
    }

    @Override
    public boolean save(Product entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(entity);
                tx.commit();
                return true;
            } catch (RuntimeException exception) {
                System.err.println("Error while saving product " + exception);
                if (tx != null) {
                    tx.rollback();
                }
            }
        }
        return false;
    }

    @Override
    public Product delete(Integer integer) {
        Product product = findById(integer);
        if (product != null) {
            try (Session session = sessionFactory.openSession()) {
                Transaction tx = null;
                try {
                    tx = session.beginTransaction();
                    session.delete(product);
                    tx.commit();
                    return product;
                } catch (RuntimeException exception) {
                    System.err.println("Error while deleting product " + exception);
                    if (tx != null) {
                        tx.rollback();
                    }
                }
            }
        }
        return null;
    }

    @Override
    public boolean update(Product entity, Integer integer) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                entity.setId(integer);
                session.update(entity);
                tx.commit();
                return true;
            } catch (RuntimeException exception) {
                System.err.println("Error while updating product " + exception);
                if (tx != null) {
                    tx.rollback();
                }
            }
        }
        return false;
    }
}
