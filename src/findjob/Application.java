/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package findjob;
import java.sql.*;
import java.util.Date;

/**
 *
 * @author BusraGural
 */
public class Application {
    
    private int id, userId, advId;
    private Date applicationDate;

    public Application(int id, int userId, int advId, Date applicationDate) {
        this.id = id;
        this.userId = userId;
        this.advId = advId;
        this.applicationDate = applicationDate;
    }
    
    public Application(){
        
    }
    
    public void applyAdvertisement(Connection conn, Advertisement adv, int userId) throws SQLException {
        System.out.println("adv.getId" + adv.getId());
        System.out.println("userId" + userId);
        String query = "INSERT INTO applications (user_id, adv_id, application_date) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, adv.getId()); // Assuming Advertisement has an getId method
            preparedStatement.setDate(3, new java.sql.Date(System.currentTimeMillis())); 

            preparedStatement.executeUpdate();

            System.out.println("Application successful.");
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
            throw e;
        }
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
     * @return the advId
     */
    public int getAdvId() {
        return advId;
    }

    /**
     * @param advId the advId to set
     */
    public void setAdvId(int advId) {
        this.advId = advId;
    }

    /**
     * @return the applicationDate
     */
    public Date getApplicationDate() {
        return applicationDate;
    }

    /**
     * @param applicationDate the applicationDate to set
     */
    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }
    
    
}
