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
public class Certificate {
     
    private int id, userId, companyId, duration;
    private LocalDate receiptDate;
    private String certifName, companyName ;

    public Certificate(){
        
    }
    
    public Certificate(int userId, String certifName, String companyName, int duration, LocalDate receiptDate ){
        this.userId = userId;
        this.certifName = certifName;
        this.companyName = companyName;
        this.duration = duration;
        this.receiptDate = receiptDate;
    }
      
    public void updateCertificateDetails(Connection conn, int userId, Certificate updatedCertificate) {
        try {
            int companyId = getCompanyIdByName(conn, updatedCertificate.getCompanyName());
            String updateQuery = "UPDATE certificate SET company_id=?, certif_name=?, duration=?, receipt_date=? WHERE user_id=?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(updateQuery)) {
                preparedStatement.setInt(1, companyId);
                preparedStatement.setString(2, updatedCertificate.getCertifName());
                preparedStatement.setInt(3, updatedCertificate.getDuration());
                preparedStatement.setDate(4, Date.valueOf(updatedCertificate.getReceiptDate()));
                preparedStatement.setInt(5, userId);

                preparedStatement.executeUpdate();
                System.out.println("Certificate details updated successfully!");
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
    
    public Certificate getCertificateDetails(Connection conn, int userId, Certificate certificate) {
        try {
            String query = "SELECT * FROM certificate WHERE user_id = ? ";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);

                // Execute the query
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Check if there is any result
                    if (resultSet.next()) {
                        certificate.setId(resultSet.getInt("id"));
                        certificate.setUserId(resultSet.getInt("user_id"));
                        certificate.setCompanyId(resultSet.getInt("company_id"));
                        certificate.setCertifName(resultSet.getString("certif_name"));
                        certificate.setDuration(resultSet.getInt("duration"));
                        certificate.setReceiptDate(resultSet.getDate("receipt_date").toLocalDate());
                        certificate.setCompanyName(getCompanyNameById(conn, certificate.getCompanyId()));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException
        }

        return certificate;
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
     * @return the duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * @return the receiptDate
     */
    public LocalDate getReceiptDate() {
        return receiptDate;
    }

    /**
     * @param receiptDate the receiptDate to set
     */
    public void setReceiptDate(LocalDate receiptDate) {
        this.receiptDate = receiptDate;
    }

    /**
     * @return the certifName
     */
    public String getCertifName() {
        return certifName;
    }

    /**
     * @param certifName the certifName to set
     */
    public void setCertifName(String certifName) {
        this.certifName = certifName;
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
