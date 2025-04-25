package ra.edu.business.dao.statisticDAO;

import ra.edu.business.model.Pagination;
import ra.edu.business.model.course.Course;

import java.util.List;
import java.util.Map;

public interface StatisticDAO{
    int findTotalCourse();
    int findTotalStudent();
    Pagination<Map<String, Integer>> findCoursesHaveMore10Student(int page, int size);
    Pagination<Map<String, Integer>> findStudentCountEachCourse(int page, int size);
    List<Map<String, Integer>> findTop5CoursesByStudentCount();
}
