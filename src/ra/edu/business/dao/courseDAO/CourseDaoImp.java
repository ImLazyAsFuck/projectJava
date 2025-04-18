package ra.edu.business.dao.courseDAO;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.Pagination;
import ra.edu.business.model.course.Course;
import ra.edu.utils.Print.PrintError;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDaoImp implements CourseDAO {
    @Override
    public Course findbyId(int id) {
        Course course = null;
        Connection con = null;
        CallableStatement cs = null;
        try {
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call find_course_by_id(?)}");
            cs.setInt(1, id);
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
    public Pagination<Course> searchByName(String name, int page, int pageSize) {
        Pagination<Course> pagination = new Pagination<>();
        List<Course> list = new ArrayList<>();

        if (page < 1) {
            page = 1;
        }

        try (Connection con = ConnectionDB.openConnection();
             CallableStatement cs = con.prepareCall("{call find_courses_by_name(?, ?, ?, ?)}")) {

            cs.setString(1, name);
            cs.setInt(2, page);
            cs.setInt(3, pageSize);
            cs.registerOutParameter(4, Types.INTEGER);

            boolean hasResultSet = cs.execute();
            if (hasResultSet) {
                try (ResultSet rs = cs.getResultSet()) {
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
                }
            }

            int totalItems = cs.getInt(4);
            int totalPages = (int) Math.ceil((double) totalItems / pageSize);

            if (page > totalPages && totalPages > 0) {
                page = totalPages;
            }
            if (totalPages == 0) {
                page = 1;
            }

            pagination.setTotalItems(totalItems);
            pagination.setTotalPages(totalPages);
            pagination.setCurrentPage(page);
            pagination.setPageSize(pageSize);
            pagination.setItems(list);

        } catch (SQLException e) {
            PrintError.println("Error while fetching course page: " + e.getMessage());
        } catch (Exception e) {
            PrintError.println("Unknown error while fetching course page: " + e.getMessage());
        }

        return pagination;
    }


    @Override
    public List<Course> sortCourseByName(boolean typeSort){
        List<Course> list = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;
        try {
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call sort_course_by_name(?)}");
            cs.setString(1, typeSort ? "asc" : "desc");
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
    public boolean isNameUnique(String name){
        Connection con = null;
        CallableStatement cs = null;
        try{
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call is_name_unique(?)}");
            cs.setString(1, name);
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                return true;
            }
            ConnectionDB.closeConnection(con, cs);
        }catch(SQLException e){
            PrintError.println("Error while fetching course name: " + e.getMessage());
        }catch(Exception e){
            PrintError.println("Unknown error while fetching course name: " + e.getMessage());
        }
        return false;
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
            cs = con.prepareCall("{call insert_course(?, ?, ?, ?)}");
            cs.setString(1, course.getName());
            cs.setInt(2, course.getDuration());
            cs.setString(3, course.getDescription());
            cs.setString(4, course.getInstructor());

            cs.executeUpdate();
            ConnectionDB.closeConnection(con, cs);
            return true;
        } catch (SQLException e) {
            PrintError.println("Error while saving course: " + e.getMessage());
            return false;
        } catch(Exception e){
            PrintError.println("Unknown error while saving course: " + e.getMessage());
            return false;
        }
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
    public boolean delete(int id) {
        Connection con = null;
        CallableStatement cs = null;
        try {
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{ call delete_course(?, ?) }");
            cs.setInt(1, id);
            cs.registerOutParameter(2, Types.INTEGER);
            cs.execute();

            int returnCode = cs.getInt(2);

            if (returnCode == 0) {
                return true;
            } else if (returnCode == 1) {
                PrintError.println("Not found course wth ID: " + id);
            } else if (returnCode == 2) {
                PrintError.println("Cannot delete course because it has registered students.");
            } else {
                throw new Exception();
            }
            ConnectionDB.closeConnection(con, cs);
            return false;
        } catch (SQLException e) {
            PrintError.println("Error while delete course " + e.getMessage());
            return false;
        } catch (Exception e) {
            PrintError.println("Unknown error while delete course " + e.getMessage());
            return false;
        }
    }


    @Override
    public Pagination<Course> findPage(int page, int size){
    Pagination<Course> pagination = new Pagination<>();
        List<Course> list = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;
        try {
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call find_courses_by_page(?, ?, ?)}");
            cs.setInt(1, page);
            cs.setInt(2, size);
            cs.registerOutParameter(3, Types.INTEGER);
            boolean hasResultSet = cs.execute();
            if(hasResultSet){
                ResultSet rs = cs.getResultSet();
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
            }
            int totalItems = cs.getInt(3);
            int totalPages = (int) Math.ceil((double) totalItems / size);

            if (page > totalPages && totalPages > 0) {
                page = totalPages;
            }
            if(totalPages == 0){
                page = 1;
            }

            pagination.setTotalItems(totalItems);
            pagination.setTotalPages(totalPages);
            pagination.setCurrentPage(page);
            pagination.setPageSize(size);
            pagination.setItems(list);

        } catch (SQLException e) {
            PrintError.println("Error while fetching course page: " + e.getMessage());
        }catch(Exception e){
            PrintError.println("Unknown error while fetching course page: " + e.getMessage());
        }
        ConnectionDB.closeConnection(con, cs);
        return pagination;
    }

//    public
}
