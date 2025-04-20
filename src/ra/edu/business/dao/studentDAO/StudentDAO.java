package ra.edu.business.dao.studentDAO;

import ra.edu.business.dao.BaseDAO;
import ra.edu.business.model.Pagination;
import ra.edu.business.model.student.Student;

public interface StudentDAO extends BaseDAO<Student>{
    Student findById(int id);
    Pagination<Student> findByName(String name, int page, int size);
    Pagination<Student> findByEmail(String email, int page, int size);
    boolean isEmailExist(String email);
}
