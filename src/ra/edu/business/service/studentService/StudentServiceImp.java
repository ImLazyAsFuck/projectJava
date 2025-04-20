package ra.edu.business.service.studentService;

import ra.edu.business.dao.studentDAO.StudentDAO;
import ra.edu.business.dao.studentDAO.StudentDAOImp;
import ra.edu.business.model.Account.Account;
import ra.edu.business.model.Pagination;
import ra.edu.business.model.student.Student;

import java.util.List;

public class StudentServiceImp implements StudentService{
    private final static StudentDAO studentDAO = new StudentDAOImp();
    @Override
    public Student findById(int id){
        return studentDAO.findById(id);
    }

    @Override
    public Pagination<Student> findByName(String name, int page, int size){
        return studentDAO.findByName(name,page,size);
    }

    @Override
    public Pagination<Student> findByEmail(String email, int page, int size){
        return studentDAO.findByEmail(email, page, size);
    }

    @Override
    public boolean isEmailExist(String email){
        return studentDAO.isEmailExist(email);
    }

    @Override
    public boolean save(Account account, Student student){
        return studentDAO.save(account,student);
    }

    @Override
    public List<Student> findAll(){
        return studentDAO.findAll();
    }

    @Override
    public boolean save(Student student){
        return true;
    }

    @Override
    public boolean update(Student student){
        return studentDAO.update(student);
    }

    @Override
    public boolean delete(int id){
        return studentDAO.delete(id);
    }

    @Override
    public Pagination<Student> findPage(int page, int size){
        return studentDAO.findPage(page, size);
    }
}
