package ra.edu.business.service.enrollmentService;

import ra.edu.business.dao.enrollmentDAO.EnrollmentDAO;
import ra.edu.business.dao.enrollmentDAO.EnrollmentDAOimp;
import ra.edu.business.model.Pagination;
import ra.edu.business.model.course.Course;
import ra.edu.business.model.student.Student;

import java.util.List;

public class EnrollmentServiceImp implements EnrollmentService {
    private final static EnrollmentDAO ENROLLMENT_DAO = new EnrollmentDAOimp();

    @Override
    public Pagination<Student> studentByCourse(int s_id, int page, int size, int c_id){
        return ENROLLMENT_DAO.studentByCourse(s_id,page,size,c_id);
    }

    @Override
    public boolean addStudentToEnrollment(int courseId, int studentId){
        return ENROLLMENT_DAO.addStudentToEnrollment(courseId,studentId);
    }

    @Override
    public boolean removeStudentFromEnrollment(int courseId, int studentId){
        return ENROLLMENT_DAO.removeStudentFromEnrollment(courseId,studentId);
    }

    @Override
    public boolean isCourseExist(int courseId){
        return ENROLLMENT_DAO.isCourseExist(courseId);
    }

    @Override
    public List<Course> dislayCurrentAccCourse(int s_id){
        return ENROLLMENT_DAO.dislayCurrentAccCourse(s_id);
    }
}
