package com.example.goldenticketnew.service;

import java.util.List;
import java.util.Optional;

public interface IGeneralService<T> {
    List<T> findAll();

    Optional<T> findById(Integer id);

    T save(T t);

    void remove(Integer id);
}
