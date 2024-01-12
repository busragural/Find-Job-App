/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package findjob;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.ResultSetMetaData;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
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

    public Advertisement(int id, int companyId,String title,  String description,String location,LocalDate openDate, LocalDate deadlineDate, int appliedCount,boolean isActive,boolean isJob, String type ,String department,String workingModel) {
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
    
    public ArrayList<Advertisement> getFilteredAdvertisements(Connection conn, boolean isJob, String location, String type, int selectedCompany, String selectedWorkType) {
        ArrayList<Advertisement> filteredAdvertisements = new ArrayList<>();

        try {
            // Build the SQL query based on the provided filters
            String sql = "SELECT * FROM filter_advertisements(?, ?, ?, ?, ?)";
            System.out.println("isjob: " + isJob);
            System.out.println("loca" + location);
            System.out.println("type" + type);
            System.out.println("comp " + selectedCompany);
            System.out.println("work" + selectedWorkType);
            
            
            // Prepare the statement
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                // Set parameters
                stmt.setBoolean(1, isJob);
                stmt.setString(2, type);
                stmt.setInt(3, selectedCompany);
                stmt.setString(4, location);
                stmt.setString(5, selectedWorkType);

                // Execute the query
                try (ResultSet rs = stmt.executeQuery()) {
                    // Check if there is a result
                    while (rs.next()) {
                        // Inside the while loop after getting the array result as a string
                        String arrayResultString = rs.getString(1);
                        System.out.println(arrayResultString);

                        // Check if the result string is not null or empty
                        if (arrayResultString != null && !arrayResultString.isEmpty()) {
                            // Remove leading and trailing curly braces
                            arrayResultString = arrayResultString.replaceAll("[{}\"]", "");

                            // Split the string into individual elements based on parentheses
                            String[] elements = arrayResultString.split("\\),");

                            for (String element : elements) {
                                // Remove all parentheses
                                element = element.replaceAll("[()]", "");

                                // Split values
                                String[] values = element.split(",");

                                // Additional cleanup to remove leading/trailing backslashes
                                for (int i = 0; i < values.length; i++) {
                                    values[i] = values[i].replaceAll("^\\\\+|\\\\+$", "");
                                }

                                // Convert values to Advertisement object properties
                                try {
                                    Advertisement advertisement;

                                    if (values.length == 13) {
                                        // For job advertisements with 13 columns
                                        advertisement = new Advertisement(
                                                Integer.parseInt(values[0].trim()),
                                                Integer.parseInt(values[1].trim()),
                                                values[2].trim(),
                                                values[3].trim(),
                                                values[4].trim(),
                                                LocalDate.parse(values[5].trim()),
                                                LocalDate.parse(values[6].trim()),
                                                Integer.parseInt(values[7].trim()),
                                                Boolean.parseBoolean(values[8].trim()),
                                                Boolean.parseBoolean(values[9].trim()),
                                                values[10].trim(),
                                                values[11].trim(),
                                                values[12].trim()
                                        );
                                    } else if (values.length == 11) {
                                        // For course advertisements with 11 columns
                                        advertisement = new Advertisement(
                                                Integer.parseInt(values[0].trim()),
                                                Integer.parseInt(values[1].trim()),
                                                values[2].trim(),
                                                values[3].trim(),
                                                values[4].trim(),
                                                LocalDate.parse(values[5].trim()),
                                                LocalDate.parse(values[6].trim()),
                                                Integer.parseInt(values[7].trim()),
                                                Boolean.parseBoolean(values[8].trim()),
                                                Boolean.parseBoolean(values[9].trim()),
                                                values[10].trim(),
                                                null,
                                                null
                                        );
                                    } else {
                                        // Handle other cases or throw an exception
                                        throw new IllegalArgumentException("Unexpected number of columns: " + values.length);
                                    }

                                    filteredAdvertisements.add(advertisement);
                                    System.out.println(advertisement);
                                } catch (NumberFormatException | DateTimeParseException ex) {
                                    // Handle parsing errors gracefully
                                    System.err.println("Error parsing values: " + Arrays.toString(values));
                                }
                            }
                        }

                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }

        return filteredAdvertisements;
    }

  
    
    
    
    public ArrayList<Advertisement> getAllJobAdvertisements(Connection conn) {
        ArrayList<Advertisement> advList = new ArrayList<>();

        try {
            String query = "SELECT * FROM active_job_advertisements";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Advertisement adv = new Advertisement();
                        adv.setId(resultSet.getInt("id"));
                        adv.setCompanyId(resultSet.getInt("company_id"));
                        adv.setAppliedCount(resultSet.getInt("applied_count"));
                        adv.setTitle(resultSet.getString("title"));
                        adv.setDescription(resultSet.getString("description"));
                        adv.setLocation(resultSet.getString("location"));
                        adv.setDepartment(resultSet.getString("department"));
                        adv.setWorkingModel(resultSet.getString("working_model"));
                        adv.setType(resultSet.getString("type"));
                        adv.setIsActive(resultSet.getBoolean("is_active"));
                        adv.setIsJob(resultSet.getBoolean("is_job"));
                        adv.setOpenDate(resultSet.getDate("open_date").toLocalDate());
                        adv.setDeadlineDate(resultSet.getDate("deadline_date").toLocalDate());

                        advList.add(adv);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException
        }

        return advList;
    }
    
    public ArrayList<Advertisement> getAllCourseAdvertisements(Connection conn) {
        ArrayList<Advertisement> advList = new ArrayList<>();

        try {
            String query = "SELECT * FROM active_course_advertisements ";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Advertisement adv = new Advertisement();
                        adv.setId(resultSet.getInt("id"));
                        adv.setCompanyId(resultSet.getInt("company_id"));
                        adv.setAppliedCount(resultSet.getInt("applied_count"));
                        adv.setTitle(resultSet.getString("title"));
                        adv.setDescription(resultSet.getString("description"));
                        adv.setLocation(resultSet.getString("location"));
                        adv.setDepartment(resultSet.getString("department"));
                        adv.setWorkingModel(resultSet.getString("working_model"));
                        adv.setType(resultSet.getString("type"));
                        adv.setIsActive(resultSet.getBoolean("is_active"));
                        adv.setIsJob(resultSet.getBoolean("is_job"));
                        adv.setOpenDate(resultSet.getDate("open_date").toLocalDate());
                        adv.setDeadlineDate(resultSet.getDate("deadline_date").toLocalDate());

                        advList.add(adv);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException
        }

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


