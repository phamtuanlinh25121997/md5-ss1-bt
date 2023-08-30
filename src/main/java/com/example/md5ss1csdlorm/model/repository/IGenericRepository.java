package com.example.md5ss1csdlorm.model.repository;

import java.util.List;

public interface IGenericRepository<T,E> {
    List<T> findAll();
    T findById(E id);
    void save(T m);
    void delete(E id);
}
