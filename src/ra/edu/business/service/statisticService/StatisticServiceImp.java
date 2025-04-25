package ra.edu.business.service.statisticService;

import ra.edu.business.dao.statisticDAO.StatisticDAO;
import ra.edu.business.dao.statisticDAO.StatisticDAOImp;
import ra.edu.business.model.Pagination;

import java.util.List;
import java.util.Map;

public class StatisticServiceImp implements StatisticService{
    private final static StatisticDAO STATISTIC_DAO = new StatisticDAOImp();
    @Override
    public int findTotalCourse(){
        return STATISTIC_DAO.findTotalCourse();
    }

    @Override
    public int findTotalStudent(){
        return STATISTIC_DAO.findTotalStudent();
    }

    @Override
    public Pagination<Map<String, Integer>> findCoursesHaveMore10Student(int page, int size){
        return STATISTIC_DAO.findCoursesHaveMore10Student(page, size);
    }

    @Override
    public Pagination<Map<String, Integer>> findStudentCountEachCourse(int page, int size){
        return STATISTIC_DAO.findStudentCountEachCourse(page, size);
    }

    @Override
    public List<Map<String, Integer>> findTop5CoursesByStudentCount(){
        return STATISTIC_DAO.findTop5CoursesByStudentCount();
    }
}
