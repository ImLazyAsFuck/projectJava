package ra.edu.business.dao.statisticDAO;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.Pagination;
import ra.edu.utils.Print.PrintError;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticDAOImp implements StatisticDAO{

    @Override
    public int findTotalCourse(){
        Connection con = null;
        CallableStatement cs = null;
        int totalCourse = 0;
        try{
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call find_total_course()}");
            ResultSet rs = cs.executeQuery();
            if(rs.next()){
                totalCourse = rs.getInt(1);
            }
            return totalCourse;
        }catch(SQLException e){
            PrintError.println("Error while getting total course");
        }catch(Exception e){
            PrintError.println("Unknown error while getting total course");
        }finally{
            ConnectionDB.closeConnection(con, cs);
        }
        return 0;
    }

    @Override
    public int findTotalStudent(){
        Connection con = null;
        CallableStatement cs = null;
        int totalCourse = 0;
        try{
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call find_total_student()}");
            ResultSet rs = cs.executeQuery();
            if(rs.next()){
                totalCourse = rs.getInt(1);
            }
            return totalCourse;
        }catch(SQLException e){
            PrintError.println("Error while getting total course");
        }catch(Exception e){
            PrintError.println("Unknown error while getting total course");
        }finally{
            ConnectionDB.closeConnection(con, cs);
        }
        return 0;
    }

    @Override
    public Pagination<Map<String, Integer>> findCoursesHaveMore10Student(int page, int size){
        Connection con = null;
        CallableStatement cs = null;
        Pagination<Map<String, Integer>> pagination = new Pagination<>();
        List<Map<String, Integer>> list = new ArrayList<>();
        try{
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call find_courses_have_more_10_student(?,?,?)}");
            cs.setInt(1, page);
            cs.setInt(2, size);
            cs.registerOutParameter(3, Types.INTEGER);
            ResultSet rs = cs.executeQuery();


            while(rs.next()){
                Map<String, Integer> newMap = new HashMap<>();
                String courseName = rs.getString("c_name");
                int studentCount = rs.getInt("student_count");
                if(courseName != null){
                    newMap.put(courseName, studentCount);
                    list.add(newMap);
                }
            }

            int totalItems = cs.getInt(3);
            int totalPages = (int)Math.ceil((double)totalItems / size);
            if(page > totalPages && totalPages > 0){
                page = totalPages;
            }
            if(totalPages == 0){
                page = 1;
            }

            pagination.setTotalItems(totalItems);
            pagination.setTotalPages(totalPages);
            pagination.setPageSize(size);
            pagination.setItems(list);
            return pagination;

        }catch(SQLException e){
            PrintError.println("Error while getting courses have more 10 student");
        }catch(Exception e){
            PrintError.println("Unknown error while getting courses have more 10 student");
        }finally{
            ConnectionDB.closeConnection(con, cs);
        }
        return pagination;
    }

    @Override
    public Pagination<Map<String, Integer>> findStudentCountEachCourse(int page, int size){
        Connection con = null;
        CallableStatement cs = null;
        Pagination<Map<String, Integer>> pagination = new Pagination<>();
        List<Map<String, Integer>> list = new ArrayList<>();
        try{
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call find_student_count_each_course(?,?,?)}");
            cs.setInt(1, page);
            cs.setInt(2, size);
            cs.registerOutParameter(3, Types.INTEGER);
            ResultSet rs = cs.executeQuery();
            while(rs.next()){
                Map<String, Integer> newMap = new HashMap<>();
                String courseName = rs.getString("c_name");
                int studentCount = rs.getInt("student_count");
                if(courseName != null){
                    newMap.put(courseName, studentCount);
                    list.add(newMap);
                }
            }
            int totalItems = cs.getInt(3);
            int totalPages = (int) Math.ceil((double) totalItems / size);
            if(page > totalPages && totalPages > 0){
                page = totalPages;
            }
            if(totalPages == 0){
                page = 1;
            }
            pagination.setTotalItems(totalItems);
            pagination.setTotalPages(totalPages);
            pagination.setPageSize(size);
            pagination.setItems(list);
            return pagination;
        }catch(SQLException e){
            PrintError.println("Error while getting courses have more 10 student");
        }
        catch(Exception e){
            PrintError.println("Unknown error while getting courses have more 10 student");
        }finally{
            ConnectionDB.closeConnection(con, cs);
        }
        return pagination;
    }

    @Override
    public List<Map<String, Integer>> findTop5CoursesByStudentCount(){
        Connection con = null;
        CallableStatement cs = null;
        List<Map<String, Integer>> list = new ArrayList<>();
        try{
            con = ConnectionDB.openConnection();
            cs = con.prepareCall("{call find_top5_courses_by_student_count()}");
            ResultException(cs, list);
            return list;
        }catch(SQLException e){
            PrintError.println("Error while getting courses have more 10 student");
        }catch(Exception e){
            PrintError.println("Unknown error while getting courses have more 10 student");
        }finally{
            ConnectionDB.closeConnection(con, cs);
        }
        return list;
    }

    private void ResultException(CallableStatement cs, List<Map<String, Integer>> list) throws SQLException{
        ResultSet rs = cs.executeQuery();
        while(rs.next()){
            Map<String, Integer> newMap = new HashMap<>();
            rs.getString(1);
            rs.getString(2);
            newMap.put(rs.getString(1), rs.getInt(2));
            list.add(newMap);
        }
    }
}
