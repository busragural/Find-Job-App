/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package findjob;
import java.awt.List;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
/**
 *
 * @author BusraGural
 */
public class Education {
    private int id, userId;
    private String schoolName, department;
    private double grade;
    private Date startDate, finishDate;

    public Education(){
        
    }
   
    public Education(int userId,String schoolName, String department, double grade, Date startDate, Date finishDate){
        this.userId = userId;
        this.schoolName = schoolName;
        this.department = department;
        this.grade = grade;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }
    
    
    public void updateEducationDetails(Connection conn, int userId, Education updatedEducation, int eduId) {
        try {
            // Önce eğitim bilgisini güncelle
            String updateQuery = "UPDATE education SET school_name=?, department=?, grade=?, start_date=?, finish_date=? WHERE user_id=? AND id = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, updatedEducation.getSchoolName());
                preparedStatement.setString(2, updatedEducation.getDepartment());
                preparedStatement.setDouble(3, updatedEducation.getGrade());
                preparedStatement.setDate(4, updatedEducation.getStartDate());
                preparedStatement.setDate(5, updatedEducation.getFinishDate());
                preparedStatement.setInt(6, userId);
                preparedStatement.setInt(7, eduId);
                preparedStatement.executeUpdate();
                System.out.println("Education details updated successfully!");
                System.out.println("School Name: " + updatedEducation.getSchoolName());
                System.out.println("Department: " + updatedEducation.getDepartment());
                System.out.println("Grade: " + updatedEducation.getGrade());
                System.out.println("Start Date: " + updatedEducation.getStartDate());
                System.out.println("Finish Date: " + updatedEducation.getFinishDate());
                System.out.println("User ID: " + userId);
                System.out.println("Education ID bu: " + eduId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // SQLException'ı handle et
        }
    }
    
    public ArrayList<Education> getEducationList(Connection conn, int user_id) {
        ArrayList<Education> educationList = new ArrayList<>();

        try {
            String query = "SELECT * FROM education WHERE user_id = ? ";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setInt(1, user_id);

                // Execute the query
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Iterate through the results and add each education entry to the list
                    while (resultSet.next()) {
                        Education edu = new Education();
                        edu.setId(resultSet.getInt("id"));
                        edu.setUserId(resultSet.getInt("user_id"));
                        edu.setSchoolName(resultSet.getString("school_name"));
                        edu.setDepartment(resultSet.getString("department"));
                        edu.setGrade(resultSet.getDouble("grade"));
                        edu.setStartDate(resultSet.getDate("start_date"));
                        edu.setFinishDate(resultSet.getDate("finish_date"));

                        educationList.add(edu);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException
        }

        return educationList;
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
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the finishDate
     */
    public Date getFinishDate() {
        return finishDate;
    }

    /**
     * @param finishDate the finishDate to set
     */
    public void setFinishDate(Date finishDate) {
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

    void addEducation(Connection conn, int userId, Education updatedEducation){
        String sql = "INSERT INTO education (user_id, school_name, department, grade, start_date, finish_date) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {    
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, updatedEducation.getSchoolName());
            preparedStatement.setString(3, updatedEducation.getDepartment());
            preparedStatement.setDouble(4, updatedEducation.getGrade());
            preparedStatement.setDate(5, updatedEducation.getStartDate());
            preparedStatement.setDate(6, updatedEducation.getFinishDate());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // SQL hatası oluştuğunda burada işlem yapabilirsiniz.
            e.printStackTrace();
            // Hata mesajını veya kullanıcıya gösterilecek mesajı belirleyebilirsiniz.
            throw new RuntimeException("Eğitim bilgisi eklenirken bir hata oluştu.");
        }
    }

}
