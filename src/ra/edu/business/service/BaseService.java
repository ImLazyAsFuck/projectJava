package ra.edu.business.service;

import ra.edu.business.model.Pagination;

import java.util.List;

public interface BaseService<T>{
    List<T> findAll();
    boolean save(T t);
    boolean update(T t);
    boolean delete(int id);
    Pagination<T> findPage(int page, int size);
}
