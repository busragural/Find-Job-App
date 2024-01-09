/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package findjob;

import java.awt.List;
import java.time.LocalDate;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author BusraGural
 */

public class JobAdvertisement extends Advertisement {
    private String department, workingModel, jobType;

    // Constructor
    public JobAdvertisement(int id, int companyId, int appliedCount, String title, String description, String location,
                            LocalDate openDate, LocalDate deadlineDate, boolean isActive,
                            String department, String workingModel, String jobType) {
        // Advertisement sınıfının constructor'ını çağırarak ortak özellikleri set et
        super(id, companyId, appliedCount, title, description, location, openDate, deadlineDate, isActive);

        // JobAdvertisement sınıfının özel özelliklerini set et
        this.department = department;
        this.workingModel = workingModel;
        this.jobType = jobType;
    }
    
    public JobAdvertisement(){
        
    }
    
    public static ArrayList<JobAdvertisement> getAllJobAdvertisements(Connection conn) {
        ArrayList<JobAdvertisement> jobAdvertisements = new ArrayList<>();

        try {
            String query = "SELECT * FROM job_advertisement";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        JobAdvertisement jobAd = new JobAdvertisement(
                                resultSet.getInt("id"),
                                resultSet.getInt("company_id"),
                                resultSet.getInt("applied_count"),
                                resultSet.getString("title"),
                                resultSet.getString("description"),
                                resultSet.getString("location"),
                                resultSet.getDate("open_date").toLocalDate(),
                                resultSet.getDate("deadline_date").toLocalDate(),
                                resultSet.getBoolean("is_active"),
                                resultSet.getString("department"),
                                resultSet.getString("working_model"),
                                resultSet.getString("job_type")
                        );
                        jobAdvertisements.add(jobAd);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException
        }

        return jobAdvertisements;
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
     * @return the workingModel
     */
    public String getWorkingModel() {
        return workingModel;
    }

    /**
     * @param workingModel the workingModel to set
     */
    public void setWorkingModel(String workingModel) {
        this.workingModel = workingModel;
    }

    /**
     * @return the jobType
     */
    public String getJobType() {
        return jobType;
    }

    /**
     * @param jobType the jobType to set
     */
    public void setJobType(String jobType) {
        this.jobType = jobType;
    }
    
    


}