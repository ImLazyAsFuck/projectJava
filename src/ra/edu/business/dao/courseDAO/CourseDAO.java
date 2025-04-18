package ra.edu.business.dao.courseDAO;

import ra.edu.business.dao.BaseDAO;
import ra.edu.business.model.Pagination;
import ra.edu.business.model.course.Course;

import java.util.List;

public interface CourseDAO extends BaseDAO<Course>{
    Course findbyId(int id);

    Pagination<Course> searchByName(String name,  int page, int pageSize);

    List<Course> sortCourseByName(boolean typeSort);

    boolean isNameUnique(String name);
}
