/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package findjob;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author BusraGural
 */
public class Advertisement {
    private int id, companyId, appliedCount;
    private String title, description, location, department, workingModel, type;
    private boolean isActive;
    private boolean isJob;
    private LocalDate openDate, deadlineDate;

    public Advertisement(int id, int companyId, int appliedCount, String title, String description, String location, String department, String workingModel, String type, boolean isActive, boolean isJob, LocalDate openDate, LocalDate deadlineDate) {
        this.id = id;
        this.companyId = companyId;
        this.appliedCount = appliedCount;
        this.title = title;
        this.description = description;
        this.location = location;
        this.department = department;
        this.workingModel = workingModel;
        this.type = type;
        this.isActive = isActive;
        this.isJob = isJob;
        this.openDate = openDate;
        this.deadlineDate = deadlineDate;
    }
    
    
    public Advertisement(){
        
    }
    
    public ArrayList<Advertisement> getAllJobAdvertisements(Connection conn){
         ArrayList<Advertisement> advList = new ArrayList<>();
        return advList;
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
     * @return the appliedCount
     */
    public int getAppliedCount() {
        return appliedCount;
    }

    /**
     * @param appliedCount the appliedCount to set
     */
    public void setAppliedCount(int appliedCount) {
        this.appliedCount = appliedCount;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the openDate
     */
    public LocalDate getOpenDate() {
        return openDate;
    }

    /**
     * @param openDate the openDate to set
     */
    public void setOpenDate(LocalDate openDate) {
        this.openDate = openDate;
    }

    /**
     * @return the deadlineDate
     */
    public LocalDate getDeadlineDate() {
        return deadlineDate;
    }

    /**
     * @param deadlineDate the deadlineDate to set
     */
    public void setDeadlineDate(LocalDate deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    /**
     * @return the isActive
     */
    public boolean isIsActive() {
        return isActive;
    }

    /**
     * @param isActive the isActive to set
     */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
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
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the isJob
     */
    public boolean isIsJob() {
        return isJob;
    }

    /**
     * @param isJob the isJob to set
     */
    public void setIsJob(boolean isJob) {
        this.isJob = isJob;
    }
    
}


