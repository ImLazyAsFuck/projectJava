package ra.edu.business.service.courseService;

import ra.edu.business.dao.courseDAO.CourseDAO;
import ra.edu.business.dao.courseDAO.CourseDaoImp;
import ra.edu.business.model.course.Course;

import java.util.List;

public class CourseServiceImp implements CourseService {
    private final static CourseDAO COURSE_DAO = new CourseDaoImp();
    @Override
    public Course findbyName(String name){
        return COURSE_DAO.findbyName(name);
    }

    @Override
    public List<Course> sortCourseByName(boolean typeSort){
        return COURSE_DAO.sortCourseByName(typeSort);
    }

    @Override
    public List<Course> findAll(){
        return COURSE_DAO.findAll();
    }

    @Override
    public boolean save(Course course){
        return COURSE_DAO.save(course);
    }

    @Override
    public boolean update(Course course){
        return COURSE_DAO.update(course);
    }

    @Override
    public boolean delete(Course course){
        return COURSE_DAO.delete(course);
    }

    @Override
    public List<Course> findPage(int page, int size){
        return COURSE_DAO.findPage(page, size);
    }
}
