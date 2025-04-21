package ra.edu.business.service.enrollmentService;

import ra.edu.business.model.Pagination;
import ra.edu.business.model.course.Course;
import ra.edu.business.model.student.Student;

import java.util.List;

public interface EnrollmentService{
    Pagination<Student> studentByCourse(int s_id, int page, int size, int c_id);
    boolean addStudentToEnrollment(int courseId,int studentId);
    //    boolean isStudentExistInEnrollment(int courseId,int studentId);
    boolean removeStudentFromEnrollment(int courseId,int studentId);
    boolean isCourseExist(int courseId);
    List<Course> dislayCurrentAccCourse(int s_id);
}
