package com.wmg.springbootrest.service;

import java.util.List;

public interface BaseService<T> {

    List<T> listAll();

    T getById(Long id);

    T save(T domainObject);

    T update(Long id, T domainObject);

    T patch(Long id, T domainObject);

    void delete(Long id);
}
