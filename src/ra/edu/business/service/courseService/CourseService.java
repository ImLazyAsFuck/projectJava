package ra.edu.business.service.courseService;

import ra.edu.business.model.Pagination;
import ra.edu.business.model.course.Course;
import ra.edu.business.service.BaseService;

import java.util.List;

public interface CourseService extends BaseService<Course>{
    Course findbyId(int id);
    Pagination<Course> searchByName(String name, int page, int pageSize);
    List<Course> sortCourseByName(boolean typeSort);
}
