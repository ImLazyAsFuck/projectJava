package ra.edu.business.service.courseService;

import ra.edu.business.model.course.Course;
import ra.edu.business.service.BaseService;

import java.util.List;

public interface CourseService extends BaseService<Course>{
    Course findbyName(String name);
    List<Course> sortCourseByName(boolean typeSort);
}
