/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package findjob;
import java.sql.*;
import java.time.LocalDate;
/**
 *
 * @author BusraGural
 */
public class Education {
    private int id, userId;
    private String schoolName, department;
    private double grade;
    private LocalDate startDate, finishDate;

    public Education(){
        
    }
   
    public Education(int userId,String schoolName, String department, double grade, LocalDate startDate, LocalDate finishDate){
        this.userId = userId;
        this.schoolName = schoolName;
        this.department = department;
        this.grade = grade;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }
    
    
    public void updateEducationDetails(Connection conn, int userId, Education updatedEducation) {
        try {
            // Önce eğitim bilgisini güncelle
            String updateQuery = "UPDATE education SET school_name=?, department=?, grade=?, start_date=?, finish_date=? WHERE user_id=?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, updatedEducation.getSchoolName());
                preparedStatement.setString(2, updatedEducation.getDepartment());
                preparedStatement.setDouble(3, updatedEducation.getGrade());
                preparedStatement.setDate(4, Date.valueOf(updatedEducation.getStartDate()));
                preparedStatement.setDate(5, Date.valueOf(updatedEducation.getFinishDate()));
                preparedStatement.setInt(6, userId);

                preparedStatement.executeUpdate();
                System.out.println("Education details updated successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // SQLException'ı handle et
        }
    }
    
    public Education getEducationDetails(Connection conn, int user_id, Education edu) {
        try {
            String query = "SELECT * FROM education WHERE user_id = ? ";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setInt(1, user_id);

                // Execute the query
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Check if there is any result
                    if (resultSet.next()) {
                        // Assuming that the columns in the 'education' table are named appropriately
                        edu.setId(resultSet.getInt("id"));
                        edu.setUserId(resultSet.getInt("user_id"));
                        edu.setSchoolName(resultSet.getString("school_name"));
                        edu.setDepartment(resultSet.getString("department"));
                        edu.setGrade(resultSet.getDouble("grade"));
                        edu.setStartDate(resultSet.getDate("start_date").toLocalDate());
                        edu.setFinishDate(resultSet.getDate("finish_date").toLocalDate());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException
        }
        
        return edu;
    }

    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the schoolName
     */
    public String getSchoolName() {
        return schoolName;
    }

    /**
     * @param schoolName the schoolName to set
     */
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    /**
     * @return the grade
     */
    public double getGrade() {
        return grade;
    }

    /**
     * @param grade the grade to set
     */
    public void setGrade(double grade) {
        this.grade = grade;
    }

    /**
     * @return the startDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the finishDate
     */
    public LocalDate getFinishDate() {
        return finishDate;
    }

    /**
     * @param finishDate the finishDate to set
     */
    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    /**
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }

}
