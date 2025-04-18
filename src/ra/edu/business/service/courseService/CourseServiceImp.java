package ra.edu.business.service.courseService;

import ra.edu.business.dao.courseDAO.CourseDAO;
import ra.edu.business.dao.courseDAO.CourseDaoImp;
import ra.edu.business.model.Pagination;
import ra.edu.business.model.course.Course;

import java.util.List;

public class CourseServiceImp implements CourseService {
    private final static CourseDAO COURSE_DAO = new CourseDaoImp();
    @Override
    public Course findbyId(int id){
        return COURSE_DAO.findbyId(id);
    }

    @Override
    public Pagination<Course> searchByName(String name, int page, int pageSize){
        return COURSE_DAO.searchByName(name,page,pageSize);
    }

    @Override
    public List<Course> sortCourseByName(boolean typeSort){
        return COURSE_DAO.sortCourseByName(typeSort);
    }

    @Override
    public boolean isNameUnique(String name){
        return COURSE_DAO.isNameUnique(name);
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
    public boolean delete(int id){
        return COURSE_DAO.delete(id);
    }

    @Override
    public Pagination<Course> findPage(int page, int size){
        return COURSE_DAO.findPage(page, size);
    }
}
