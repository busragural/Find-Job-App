/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package findjob;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author BusraGural
 */
public class Experience {
    private int id, userId, companyId;
    private String department, jobName, companyName; 
    private Date startDate, finishDate;

    public Experience(){
        
    }
    
    public Experience(int userId, String companyName, String department, String jobName, Date startDate, Date finishDate){
        this.userId = userId;
        this.companyName = companyName;
        this.department = department;
        this.jobName = jobName;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }
    
    public void updateExperienceDetails(Connection conn, int userId, Experience updatedExperience, int expId){
        try {
            int companyId = getCompanyIdByName(conn, updatedExperience.getCompanyName());
            String updateQuery = "UPDATE experience SET company_id=?, department=?, job_title=? , start_date=?, finish_date=? WHERE user_id=? AND id=?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(updateQuery)) {
                preparedStatement.setInt(1, companyId);
                preparedStatement.setString(2, updatedExperience.getDepartment());
                preparedStatement.setString(3, updatedExperience.getJobName());
                preparedStatement.setDate(4, updatedExperience.getStartDate());
                preparedStatement.setDate(5, updatedExperience.getFinishDate());
                preparedStatement.setInt(6, userId);
                preparedStatement.setInt(7, expId);

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
                        exp.setJobName(resultSet.getString("job_title"));
                        exp.setStartDate(resultSet.getDate("start_date"));
                        exp.setFinishDate(resultSet.getDate("finish_date"));
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
    
    
    
    public ArrayList<Experience> getExperienceList(Connection conn, int user_id) {
        ArrayList<Experience> expList = new ArrayList<>();
        
        try {           
            String query = "SELECT * FROM experience WHERE user_id = ? ";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setInt(1, user_id);

                // Execute the query
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Check if there is any result
                    while (resultSet.next()) {
                        // Assuming that the columns in the 'experience' table are named appropriately
                        Experience exp = new Experience();
                        exp.setId(resultSet.getInt("id"));
                        exp.setUserId(resultSet.getInt("user_id"));
                        exp.setCompanyId(resultSet.getInt("company_id"));
                        exp.setDepartment(resultSet.getString("department"));
                        exp.setJobName(resultSet.getString("job_title"));
                        exp.setStartDate(resultSet.getDate("start_date"));
                        exp.setFinishDate(resultSet.getDate("finish_date"));
                        exp.setCompanyName(getCompanyNameById(conn, exp.getCompanyId()));
                        
                        expList.add(exp);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException
        }

        return expList;
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

    void addExperience(Connection conn, int user_id, Experience exp) {
        String sql = "INSERT INTO experience (user_id, company_id, job_title, department, start_date, finish_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {   
            int compId = getCompanyIdByName(conn, exp.getCompanyName());
            
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, compId);
            preparedStatement.setString(3, exp.getJobName());
            preparedStatement.setString(4, exp.getDepartment());
            preparedStatement.setDate(5, exp.getStartDate());
            preparedStatement.setDate(6, exp.getFinishDate());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // SQL hatası oluştuğunda burada işlem yapabilirsiniz.
            e.printStackTrace();
            // Hata mesajını veya kullanıcıya gösterilecek mesajı belirleyebilirsiniz.
            throw new RuntimeException("Eğitim bilgisi eklenirken bir hata oluştu.");
        }
    }
    
    
    
}
