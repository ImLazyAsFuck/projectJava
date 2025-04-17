package ra.edu.business.dao.courseDAO;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.Pagination;
import ra.edu.business.model.course.Course;
import ra.edu.utils.PrintError;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDaoImp implements CourseDAO {
    @Override
    public Course findbyName(String name) {
        Course course = null;
        Connection con = null;
        CallableStatement cs = null;
        try {
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call findbyName(?)}");
            cs.setString(1, name);
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                course = new Course();
                course.setId(rs.getInt("c_id"));
                course.setName(rs.getString("c_name"));
                course.setDuration(rs.getInt("c_duration"));
                course.setDescription(rs.getString("c_description"));
                course.setInstructor(rs.getString("c_instructor"));
                Timestamp timestamp = rs.getTimestamp("c_created_at");
                if (timestamp != null) {
                    course.setCreatedAt(timestamp.toLocalDateTime());
                }
            }
        } catch (SQLException e) {
            PrintError.println("Error while finding course by name: " + e.getMessage());
        }catch(Exception e){
            PrintError.println("Unknown error while saving course: " + e.getMessage());
        }
        ConnectionDB.closeConnection(con, cs);
        return course;
    }


    @Override
    public List<Course> sortCourseByName(boolean typeSort){
        return List.of();
    }

    @Override
    public List<Course> findAll() {
        List<Course> list = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;
        try {
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call find_all_course()}");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("c_id"));
                course.setName(rs.getString("c_name"));
                course.setDuration(rs.getInt("c_duration"));
                course.setDescription(rs.getString("c_description"));
                course.setInstructor(rs.getString("c_instructor"));

                Timestamp ts = rs.getTimestamp("c_created_at");
                if (ts != null) {
                    course.setCreatedAt(ts.toLocalDateTime());
                }

                list.add(course);
            }
        } catch (SQLException e) {
            PrintError.println("Error while fetching all courses: " + e.getMessage());
        }catch(Exception e){
            PrintError.println("Unknown error while saving course: " + e.getMessage());
        }
        ConnectionDB.closeConnection(con, cs);
        return list;
    }

    @Override
    public boolean save(Course course) {
        Connection con = null;
        CallableStatement cs = null;
        try {
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call insert_course(?, ?, ?, ?, ?, ?)}");
            cs.setString(1, course.getName());
            cs.setInt(2, course.getDuration());
            cs.setString(3, course.getDescription());
            cs.setString(4, course.getInstructor());
            cs.setTimestamp(5, Timestamp.valueOf(course.getCreatedAt()));
            cs.registerOutParameter(6, Types.INTEGER);

            cs.execute();
            int returnCode = cs.getInt(6);
            return returnCode == 0;

        } catch (SQLException e) {
            PrintError.println("Error while saving course: " + e.getMessage());
        } catch(Exception e){
            PrintError.println("Unknown error while saving course: " + e.getMessage());
        }
        ConnectionDB.closeConnection(con, cs);
        return false;
    }


    @Override
    public boolean update(Course course) {
        Connection con = null;
        CallableStatement cs = null;
        try {
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call update_course(?, ?, ?, ?, ?, ?)}");
            cs.setInt(1, course.getId());
            cs.setString(2, course.getName());
            cs.setInt(3, course.getDuration());
            cs.setString(4, course.getDescription());
            cs.setString(5, course.getInstructor());
            cs.registerOutParameter(6, Types.INTEGER);

            cs.execute();
            int returnCode = cs.getInt(6);
            if(returnCode == 1){
                System.out.println("Some course name is duplicate");
            }else if(returnCode == 2){
                System.out.println("Not found course");
            }
            return returnCode == 0;

        } catch (SQLException e) {
            PrintError.println("Error while updating course: " + e.getMessage());
        }catch(Exception e){
            PrintError.println("Unknown error while saving course: " + e.getMessage());
        }
        ConnectionDB.closeConnection(con, cs);
        return false;
    }


    @Override
    public boolean delete(Course course){
        return false;
    }

    @Override
    public List<Course> findPage(int page, int size) {
        List<Course> list = new ArrayList<>();
        try (Connection con = ConnectionDB.openConnection();
             CallableStatement cs = con.prepareCall("{call find_courses_by_page(?, ?)}")) {

            cs.setInt(1, page);
            cs.setInt(2, size);
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("c_id"));
                course.setName(rs.getString("c_name"));
                course.setDuration(rs.getInt("c_duration"));
                course.setDescription(rs.getString("c_description"));
                course.setInstructor(rs.getString("c_instructor"));

                Timestamp ts = rs.getTimestamp("c_created_at");
                if (ts != null) {
                    course.setCreatedAt(ts.toLocalDateTime());
                }

                list.add(course);
            }

        } catch (SQLException e) {
            PrintError.println("Error while fetching course page: " + e.getMessage());
        }
        return list;
    }

}
