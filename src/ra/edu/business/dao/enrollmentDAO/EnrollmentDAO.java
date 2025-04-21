package ra.edu.business.dao.enrollmentDAO;

import ra.edu.business.model.Pagination;
import ra.edu.business.model.student.Student;

import java.util.List;

public interface EnrollmentDAO{
    Pagination<Student> studentByCourse(String name);
    boolean addStudentToEnrollment(int courseId,int studentId);
//    boolean isStudentExistInEnrollment(int courseId,int studentId);
    boolean removeStudentFromEnrollment(int courseId,int studentId);
    boolean isCourseExist(int courseId);
}
