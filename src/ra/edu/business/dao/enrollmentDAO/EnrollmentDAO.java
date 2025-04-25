package ra.edu.business.dao.enrollmentDAO;

import ra.edu.business.model.Pagination;
import ra.edu.business.model.course.Course;
import ra.edu.business.model.enrollment.Enrollment;
import ra.edu.business.model.student.Student;

import java.util.List;

public interface EnrollmentDAO{
    Pagination<Student> studentByCourse(int c_id, int page, int size);
    boolean addStudentToEnrollment(int courseId,int studentId);
//    boolean isStudentExistInEnrollment(int courseId,int studentId);
    boolean removeStudentFromEnrollment(int courseId,int studentId);
    boolean isCourseExist(int courseId);
    List<Course> dislayCurrentAccCourse(int s_id);
    List<Enrollment> findAll();
    Pagination<Course> findCourseByStudentId(int s_id, int page, int size);
    boolean approveStudent(int courseId,int studentId);
    boolean studentCancelfromEnrollment(int courseId,int studentId);
}
