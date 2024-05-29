package org.example.repository;


import org.example.model.Entity;

import java.util.List;

public interface Repository<ID, E extends Entity<ID>> {

    E findById(ID id);

    List<E> findAll();

    boolean save(E entity);

    E delete(ID id);

    boolean update(E entity, ID id);

}
