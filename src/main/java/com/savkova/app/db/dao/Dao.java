package com.savkova.app.db.dao;

import java.util.List;

public interface Dao<T> {
    void add(T t);
    T get(int id);
    List<T> getAll();
    void update(T t);
    void delete(int id);
}
