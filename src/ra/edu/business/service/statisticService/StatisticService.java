package ra.edu.business.service.statisticService;

import ra.edu.business.model.Pagination;

import java.util.List;
import java.util.Map;

public interface StatisticService{
    int findTotalCourse();
    int findTotalStudent();
    Pagination<Map<String, Integer>> findCoursesHaveMore10Student(int page, int size);
    Pagination<Map<String, Integer>> findStudentCountEachCourse(int page, int size);
    List<Map<String, Integer>> findTop5CoursesByStudentCount();
}
