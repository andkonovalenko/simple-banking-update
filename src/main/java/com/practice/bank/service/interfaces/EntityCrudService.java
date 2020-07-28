package com.practice.bank.service.interfaces;

import java.util.List;

public interface EntityCrudService<T> {

    T getOneById(Long id);

    List<T> getAll();

    T create(T object);

    T update(Long id, T object);

    void delete(Long id);
}
