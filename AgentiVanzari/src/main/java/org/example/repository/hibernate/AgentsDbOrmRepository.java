package org.example.repository.hibernate;

import org.example.model.Agent;
import org.example.repository.AgentsRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;

public class AgentsDbOrmRepository implements AgentsRepository {

    private static SessionFactory sessionFactory;

    public AgentsDbOrmRepository() {
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
    public Agent findById(String s) {
        Agent agent = new Agent();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                agent = session.createQuery("from Agent where id = ?1", Agent.class)
                        .setParameter(1, s)
                        .setMaxResults(1)
                        .uniqueResult();
                tx.commit();
                return agent;
            } catch (RuntimeException exception) {
                if (tx != null) {
                    tx.rollback();
                }
            }
        }
        return null;
    }

    @Override
    public List<Agent> findAll() {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Agent> agents =
                        session.createQuery("from Agent as m order by m.id asc", Agent.class)
                                .list();
                tx.commit();
                return agents;
            } catch (RuntimeException exception) {
                System.err.println("Error at select all agents " + exception);
                if (tx != null) {
                    tx.rollback();
                }
            }
        }
        return new ArrayList<>();
    }

    @Override
    public boolean save(Agent entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(entity);
                tx.commit();
                return true;
            } catch (RuntimeException exception) {
                System.err.println("Error saving agent: " + exception);
                if (tx != null) {
                    tx.rollback();
                }
                return false;
            }
        }
    }

    @Override
    public Agent delete(String s) {
        Agent agentToDelete = findById(s);
        if (agentToDelete != null) {
            try (Session session = sessionFactory.openSession()) {
                Transaction tx = null;
                try {
                    tx = session.beginTransaction();
                    session.delete(agentToDelete);
                    tx.commit();
                } catch (RuntimeException exception) {
                    System.err.println("Error deleting agent: " + exception);
                    if (tx != null) {
                        tx.rollback();
                    }
                }
            }
        }
        return agentToDelete;
    }

    @Override
    public boolean update(Agent entity, String s) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Agent existingAgent = session.get(Agent.class, s);
                if (existingAgent != null) {
                    existingAgent.setName(entity.getName());
                    existingAgent.setEmail(entity.getEmail());
                    existingAgent.setPassword(entity.getPassword());
                    session.update(existingAgent);
                    tx.commit();
                    return true;
                }
            } catch (RuntimeException exception) {
                System.err.println("Error updating agent: " + exception);
                if (tx != null) {
                    tx.rollback();
                }
            }
        }
        return false;
    }
}
