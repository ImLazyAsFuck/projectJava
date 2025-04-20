package ra.edu.business.service.studentService;

import ra.edu.business.dao.BaseDAO;
import ra.edu.business.model.Account.Account;
import ra.edu.business.model.Pagination;
import ra.edu.business.model.student.Student;

public interface StudentService extends BaseDAO<Student> {
    Student findById(int id);
    Pagination<Student> findByName(String name, int page, int size);
    Pagination<Student> findByEmail(String email, int page, int size);
    boolean isEmailExist(String email);
    boolean save(Account account, Student student);
}
