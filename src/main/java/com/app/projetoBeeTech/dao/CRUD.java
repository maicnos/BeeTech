package com.app.projetoBeeTech.dao;

import java.util.List;

public interface CRUD <T>{

    public T create(T obj);

    public List<T> read();

    public void update (T obj);

    public void delete(int id);

}
