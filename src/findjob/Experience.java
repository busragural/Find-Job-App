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
public class Experience {
    private int id, userId, companyId;
    private String department, jobName, companyName; 
    private LocalDate startDate, finishDate;

    public Experience(){
        
    }
    
    public Experience(int userId, String companyName, String department, String jobName, LocalDate startDate, LocalDate finishDate){
        this.userId = userId;
        this.companyName = companyName;
        this.department = department;
        this.jobName = jobName;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }
    
    public void updateExperienceDetails(Connection conn, int userId, Experience updatedExperience){
        try {
            int companyId = getCompanyIdByName(conn, updatedExperience.getCompanyName());
            String updateQuery = "UPDATE experience SET company_id=?, department=?, job_name=? , start_date=?, finish_date=? WHERE user_id=?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(updateQuery)) {
                 preparedStatement.setInt(1, companyId);
                preparedStatement.setString(2, updatedExperience.getDepartment());
                preparedStatement.setString(3, updatedExperience.getJobName());
                preparedStatement.setDate(4, Date.valueOf(updatedExperience.getStartDate()));
                preparedStatement.setDate(5, Date.valueOf(updatedExperience.getFinishDate()));
                preparedStatement.setInt(6, userId);

                preparedStatement.executeUpdate();
                System.out.println("Experience details updated successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // SQLException'ı handle et
        }
    }
    
    public int getCompanyIdByName(Connection conn, String companyName) throws SQLException {
        int companyId = -1; // Varsayılan olarak -1, eğer bir hata olursa kontrol etmek için.

        String query = "SELECT id FROM company WHERE name=?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, companyName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    companyId = resultSet.getInt("id");
                }
            }
        }
        return companyId;
    }
    
   
    public Experience getExperienceDetails(Connection conn, int user_id, Experience exp) {
        try {           
            String query = "SELECT * FROM experience WHERE user_id = ? ";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setInt(1, user_id);

                // Execute the query
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Check if there is any result
                    if (resultSet.next()) {
                        // Assuming that the columns in the 'experience' table are named appropriately
                        exp.setId(resultSet.getInt("id"));
                        exp.setUserId(resultSet.getInt("user_id"));
                        exp.setCompanyId(resultSet.getInt("company_id"));
                        exp.setDepartment(resultSet.getString("department"));
                        exp.setJobName(resultSet.getString("job_name"));
                        exp.setStartDate(resultSet.getDate("start_date").toLocalDate());
                        exp.setFinishDate(resultSet.getDate("finish_date").toLocalDate());
                        exp.setCompanyName(getCompanyNameById(conn, exp.getCompanyId()));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException
        }

        return exp;
    }
    
    public String getCompanyNameById(Connection conn, int companyId) throws SQLException {
        
        String companyName = "";
        String query = "SELECT name FROM company WHERE id=?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, companyId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    companyName = resultSet.getString("name");
                }
            }
        }
        return companyName;
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
     * @return the companyId
     */
    public int getCompanyId() {
        return companyId;
    }

    /**
     * @param companyId the companyId to set
     */
    public void setCompanyId(int companyId) {
        this.companyId = companyId;
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
     * @return the jobName
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * @param jobName the jobName to set
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    
    
}
