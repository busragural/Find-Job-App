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
public class Certificate {
     
    private int id, userId, companyId, duration;
    private Date receiptDate;
    private String certifName, companyName ;

    public Certificate(){
        
    }
    
    public Certificate(int userId, String certifName, String companyName, int duration, Date receiptDate ){
        this.userId = userId;
        this.certifName = certifName;
        this.companyName = companyName;
        this.duration = duration;
        this.receiptDate = receiptDate;
    }
      
    public void updateCertificateDetails(Connection conn, int userId, Certificate updatedCertificate, int certId) {
        try {
            int companyId = getCompanyIdByName(conn, updatedCertificate.getCompanyName());
            
            String updateQuery = "UPDATE certificate SET company_id=?, certification_name=?, duration=?, receipt_date=? WHERE user_id=? AND id=?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(updateQuery)) {
                preparedStatement.setInt(1, companyId);
                preparedStatement.setString(2, updatedCertificate.getCertifName());
                preparedStatement.setInt(3, updatedCertificate.getDuration());
                preparedStatement.setDate(4, updatedCertificate.getReceiptDate());
                preparedStatement.setInt(5, userId);
                preparedStatement.setInt(6, certId);

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
                        certificate.setCertifName(resultSet.getString("certification_name"));
                        certificate.setDuration(resultSet.getInt("duration"));
                        certificate.setReceiptDate(resultSet.getDate("receipt_date"));
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
    public Date getReceiptDate() {
        return receiptDate;
    }

    /**
     * @param receiptDate the receiptDate to set
     */
    public void setReceiptDate(Date receiptDate) {
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

    ArrayList<Certificate> getCertificateList(Connection conn, int user_id) {
        ArrayList<Certificate> certificateList = new ArrayList<>();

        try {
            String query = "SELECT * FROM certificate WHERE user_id = ? ";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setInt(1, user_id);

                // Execute the query
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Iterate through the results and add each education entry to the list
                    while (resultSet.next()) {
                        Certificate cert = new Certificate();
                        cert.setId(resultSet.getInt("id"));
                        cert.setUserId(resultSet.getInt("user_id"));
                        cert.setCompanyId(resultSet.getInt("company_id"));
                        cert.setCertifName(resultSet.getString("certification_name"));
                        cert.setDuration(resultSet.getInt("duration"));
                        cert.setReceiptDate(resultSet.getDate("receipt_date"));
                        cert.setCompanyName(getCompanyNameById(conn, cert.getCompanyId()));

                        certificateList.add(cert);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException
        }

        return certificateList;
    }

    void addCertificate(Connection conn, int user_id, Certificate tmp) {
        String sql = "INSERT INTO certificate (user_id, company_id, certification_name, receipt_date, duration) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            int compId = getCompanyIdByName(conn, tmp.getCompanyName());
            
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, compId);
            preparedStatement.setString(3, tmp.getCertifName());
            preparedStatement.setDate(4, tmp.getReceiptDate());
            preparedStatement.setInt(5, tmp.getDuration());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // SQL hatası oluştuğunda burada işlem yapabilirsiniz.
            e.printStackTrace();
            // Hata mesajını veya kullanıcıya gösterilecek mesajı belirleyebilirsiniz.
            throw new RuntimeException("Eğitim bilgisi eklenirken bir hata oluştu.");
        }
    }
      
}
