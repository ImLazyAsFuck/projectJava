package ra.edu.business.dao.courseDAO;

import ra.edu.business.dao.BaseDAO;
import ra.edu.business.model.course.Course;

import java.util.List;

public interface CourseDAO extends BaseDAO<Course>{
    Course findbyName(String name);
    List<Course> sortCourseByName(boolean typeSort);
}
