package com.artur.database.repository;

import com.artur.database.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface Repository<K extends Serializable, E extends BaseEntity<K>> {

    void delete(E entity);

    E save(E entity);

    void update(E entity);

    Optional<E> findById(K id);

    List<E> findAll();
}
