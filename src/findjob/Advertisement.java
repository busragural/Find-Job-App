/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package findjob;

import java.time.LocalDate;

/**
 *
 * @author BusraGural
 */
public class Advertisement {
    private int id, companyId, appliedCount;
    private String title, description, location;
    private LocalDate  openDate, deadlineDate; 
    private boolean isActive; 
    
    public Advertisement(int id, int companyId, int appliedCount, String title, String description, String location, LocalDate openDate, LocalDate deadlineDate, boolean isActive) {
        this.id = id;
        this.companyId = companyId;
        this.appliedCount = appliedCount;
        this.title = title;
        this.description = description;
        this.location = location;
        this.openDate = openDate;
        this.deadlineDate = deadlineDate;
        this.isActive = isActive;
    }
    public Advertisement() {
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
    
}


