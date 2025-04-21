package ra.edu.business.service.studentService;

import ra.edu.business.dao.studentDAO.StudentDAO;
import ra.edu.business.dao.studentDAO.StudentDAOImp;
import ra.edu.business.model.Account.Account;
import ra.edu.business.model.Pagination;
import ra.edu.business.model.student.Student;

import java.util.List;

public class StudentServiceImp implements StudentService{
    private final static StudentDAO STUDENT_DAO = new StudentDAOImp();
    @Override
    public Student findById(int id){
        return STUDENT_DAO.findById(id);
    }

    @Override
    public Pagination<Student> findByName(String name, int page, int size){
        return STUDENT_DAO.findByName(name,page,size);
    }

    @Override
    public Pagination<Student> findByEmail(String email, int page, int size){
        return STUDENT_DAO.findByEmail(email, page, size);
    }

    @Override
    public boolean isEmailExist(String email){
        return STUDENT_DAO.isEmailExist(email);
    }

    @Override
    public boolean save(Account account, Student student){
        return STUDENT_DAO.save(account,student);
    }

    @Override
    public boolean unlockStudent(int id){
        return STUDENT_DAO.unlockStudent(id);
    }

    @Override
    public boolean isStudentLocked(int id){
        return STUDENT_DAO.isStudentLocked(id);
    }

    @Override
    public List<Student> sortStudentByName(boolean typeSort){
        return STUDENT_DAO.sortStudentByName(typeSort);
    }

    @Override
    public List<Student> sortStudentByEmail(boolean typeSort){
        return STUDENT_DAO.sortStudentByEmail(typeSort);
    }

    @Override
    public int findStudentById(int id){
        return STUDENT_DAO.findStudentById(id);
    }

    @Override
    public List<Student> findAll(){
        return STUDENT_DAO.findAll();
    }

    @Override
    public boolean save(Student student){
        return true;
    }

    @Override
    public boolean update(Student student){
        return STUDENT_DAO.update(student);
    }

    @Override
    public boolean delete(int id){
        return STUDENT_DAO.delete(id);
    }

    @Override
    public Pagination<Student> findPage(int page, int size){
        return STUDENT_DAO.findPage(page, size);
    }
}
