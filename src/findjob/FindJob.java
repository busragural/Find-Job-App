/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package findjob;
import java.sql.*;
/**
 *
 * @author BusraGural
 */
public class FindJob {
    public static void main(String args[]) throws SQLException{
        String url, user, pass;
        url = "jdbc:postgresql://localhost:5432/find_job_db";
        user = "postgres";
        pass = "1q2w3e";
       Connection conn = DriverManager.getConnection(url, user, pass);  
        
        LoginUI loginPage = new LoginUI(conn);
        loginPage.setVisible(true);
        
    }
  
}
