package ra.edu.business.dao;

import ra.edu.business.model.Pagination;
import ra.edu.business.model.course.Course;

import java.util.List;

public interface BaseDAO<T>{
    List<T> findAll();
    boolean save(T t);
    boolean update(T t);
    boolean delete(int id);
    Pagination<T> findPage(int page, int size);
}
