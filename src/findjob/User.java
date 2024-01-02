package findjob;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author BusraGural
 */
public class User {
    
    private String name, surname, mail, username, password;
    public User() {
        
    }
    public User(String name, String surname, String mail, String username, String password){
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.username = username;
        this.password = password;
    }
    public User(String username, String password){
        this.username = username;
        this.password = password;
    }
    
    
    
    
    public void register(Connection conn, User user){
        try {
        // SQL query to create the 'Users' table if it doesn't exist
            String createTableQuery = "CREATE TABLE IF NOT EXISTS Users (id SERIAL PRIMARY KEY NOT NULL, name VARCHAR(20) NOT NULL, surname VARCHAR(20) NOT NULL, mail VARCHAR(30) NOT NULL, username VARCHAR(20) NOT NULL, password VARCHAR(20) NOT NULL )";

            try (Statement statement = conn.createStatement()) {
                // Execute the query to create the table
                statement.executeUpdate(createTableQuery);

                // SQL query to insert data into the 'Users' table
                String insertQuery = "INSERT INTO Users (name, surname, mail, username, password) VALUES (?,?,?,?,?)";

                try (PreparedStatement preparedStatement = conn.prepareStatement(insertQuery)) {
                    // Set values for the parameters
                    preparedStatement.setString(1, user.getName());
                    preparedStatement.setString(2, user.getSurname());
                    preparedStatement.setString(3, user.getMail());
                    preparedStatement.setString(4, user.getUsername());
                    preparedStatement.setString(5, user.getPassword());

                    // Execute the query
                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("User registered successfully!");
                    } else {
                        System.out.println("Failed to register user.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    public boolean loginCheck(Connection conn, String username, String password) {
        try {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                
                try (ResultSet rs = pstmt.executeQuery()) {
                    return rs.next(); // If a row is returned, credentials are valid
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
     public User fetchUserDetails(Connection conn, String username) {
        User user = new User(); // Assuming you have a User class with appropriate getters and setters

        try {
            // Assuming you have columns named 'name' and 'surname' in your 'users' table
            String query = "SELECT name, surname FROM users WHERE username = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Set the name and surname in the User object
                        user.setName(resultSet.getString("name"));
                        user.setSurname(resultSet.getString("surname"));
                    }
                }
            }
        } catch (SQLException ex) {
            // Handle any potential SQLException
            ex.printStackTrace();
        }

        return user;
    }
    public void checkEducationTable(Connection conn){
        try {
            // Check if the education table exists
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "Education", null);

            if (!resultSet.next()) {
                // If the table doesn't exist, create it
                String createTableQuery = "CREATE TABLE Education ("
                        + "id SERIAL PRIMARY KEY NOT NULL,"
                        + "user_id INT REFERENCES Users(id),"
                        + "school_name VARCHAR(255),"
                        + "start_date DATE,"
                        + "finish_Date DATE,"
                        + "grade NUMERIC(3,2))";

                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate(createTableQuery);
                }
        }
        } catch (SQLException e) {
            // Handle SQLException
                // Handle SQLException
            if (e.getSQLState().equals("42P07")) {
                // This error code indicates that the table already exists
                System.out.println("Education table already exists.");
            } else {
                e.printStackTrace();
            }
        }
    }
    
    public void checkExperienceTable(Connection conn){
        try{
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "Experience", null);
            
            if(!resultSet.next()){
                String createTableQuery = "CREATE TABLE Experince("
                        + "id SERIAL PRIMARY KEY NOT NULL,"
                        + "user_id INT REFERENCES Users(id),"
                        + "company_id INT,"
                        + "duration INT,"
                        + "department VARCHAR(50))";
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate(createTableQuery);
                }
            }
        }
        catch(SQLException e){
            // Handle SQLException
            if (e.getSQLState().equals("42P07")) {
                // This error code indicates that the table already exists
                System.out.println("Education table already exists.");
            } else {
                e.printStackTrace();
            }
        }
    }
    
    public void checkCertificateTable(Connection conn){
        try{
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "Certificate", null);
            
            if(!resultSet.next()){
                String createTableQuery = "CREATE TABLE Certificate("
                        + "id SERIAL PRIMARY KEY NOT NULL,"
                        + "user_id INT REFERENCES Users(id),"
                        + "certif_name VARCHAR(50) ,"
                        + "receipt_date DATE,"
                        + "company_id INT,"
                        + "duration INT)";
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate(createTableQuery);
                }
            }
        }
        catch(SQLException e){
            // Handle SQLException
            if (e.getSQLState().equals("42P07")) {
                // This error code indicates that the table already exists
                System.out.println("Education table already exists.");
            } else {
                e.printStackTrace();
            }
        }
    }
    
    
    
    
  
     
     
     
     
    

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(String mail) {
        this.mail = mail;
    }
    
    
}
