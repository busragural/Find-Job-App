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
    private int id;
    public User() {
        
    }
    public User(String name, String surname, String mail, String username, String password){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.username = username;
        this.password = password;
    }
    
    public User(int id, String name, String surname, String mail, String username, String password){
        this.id = id;
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
    
   
    
    public int register(Connection conn, User user) {
       int userId = -1; // Varsayılan olarak -1, işlem başarısız olursa bu değer kullanılacaktır

       try {
           // SQL query to insert data into the 'Users' table
           String insertQuery = "INSERT INTO Users (name, surname, mail, username, password) VALUES (?,?,?,?,?)";

           try (PreparedStatement preparedStatement = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
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

                   // Retrieve the generated keys (user ID)
                   try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                       if (generatedKeys.next()) {
                           userId = generatedKeys.getInt(1); // Get the generated user ID
                       }
                   }
               } else {
                   System.out.println("Failed to register user.");
               }
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }

       return userId;
   }

    
    public boolean loginCheck(Connection conn, String username, String password) {
        try {
            String query = "SELECT * FROM Users WHERE username = ? AND password = ?";
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
            String query = "SELECT id, name, surname FROM users WHERE username = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Set the name and surname in the User object
                        user.setId(resultSet.getInt("id"));
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
     
    public void checkEducationTable(Connection conn, int userId) {
        try {
//            // Check if the education table exists
//            DatabaseMetaData metaData = conn.getMetaData();
//            ResultSet resultSet = metaData.getTables(null, null, "education", null);
//
//            if (!resultSet.next()) {
//                // If the table doesn't exist, create it
//                String createTableQuery = "CREATE TABLE education ("
//                        + "id SERIAL PRIMARY KEY NOT NULL,"
//                        + "user_id INT REFERENCES Users(id),"
//                        + "school_name VARCHAR(255) NOT NULL,"
//                        + "department VARCHAR(255) NOT NULL,"
//                        + "start_date DATE NOT NULL,"
//                        + "finish_date DATE NOT NULL,"
//                        + "grade NUMERIC(3,2))";
//
//                try (Statement stmt = conn.createStatement()) {
//                    stmt.executeUpdate(createTableQuery);
//
//                    System.out.println("Education table created successfully!");
//                }
//            } else {
//                System.out.println("Education table already exists.");
//            }

            // Insert user_id into the Education table
            String insertUserIdQuery = "INSERT INTO Education (user_id, school_name, department, start_date, finish_date, grade) VALUES (?, 'Default School','Default Department' , '2022-01-01', '2023-01-01', 0.0)";
            try (PreparedStatement preparedStatement = conn.prepareStatement(insertUserIdQuery)) {
                preparedStatement.setInt(1, userId);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("User ID added to Education table successfully!");
                } else {
                    System.out.println("Failed to add User ID to Education table.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void checkExperienceTable(Connection conn, int userId) {
        try {
//            DatabaseMetaData metaData = conn.getMetaData();
//            ResultSet resultSet = metaData.getTables(null, null, "experience", null);
//
//            if (!resultSet.next()) {
//                // If the table doesn't exist, create it
//                String createTableQuery = "CREATE TABLE Experience("
//                        + "id SERIAL PRIMARY KEY NOT NULL,"
//                        + "user_id INT REFERENCES Users(id),"
//                        + "company_id INT REFERENCES company(id) ,"
//                        + "job_name VARCHAR(50) NOT NULL,"
//                        + "start_date DATE NOT NULL,"
//                        + "finish_date DATE NOT NULL,"
//                        + "department VARCHAR(50) NOT NULL)";
//                try (Statement stmt = conn.createStatement()) {
//                    stmt.executeUpdate(createTableQuery);
//                    System.out.println("Experience table created successfully!");
//                }
//            } else {
//                System.out.println("Experience table already exists.");
//            }

            // Insert user_id into the Experience table
            String insertUserIdQuery = "INSERT INTO Experience (user_id, company_id, job_title, start_date, finish_date, department) VALUES (?, 0, 'default' , '2022-01-01', '2023-01-01', 'Default Department')";
            try (PreparedStatement preparedStatement = conn.prepareStatement(insertUserIdQuery)) {
                preparedStatement.setInt(1, userId);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("User ID added to Experience table successfully!");
                } else {
                    System.out.println("Failed to add User ID to Experience table.");
                }
            }
        } catch (SQLException e) {
            // Handle SQLException
            if (e.getSQLState().equals("42P07")) {
                // This error code indicates that the table already exists
                System.out.println("Experience table already exists.");
            } else {
                e.printStackTrace();
            }
        }
    }

    
public void checkCertificateTable(Connection conn, int userId) {
    try {
//        DatabaseMetaData metaData = conn.getMetaData();
//        ResultSet resultSet = metaData.getTables(null, null, "certificate", null);
//
//        if (!resultSet.next()) {
//            // If the table doesn't exist, create it
//            String createTableQuery = "CREATE TABLE Certificate("
//                    + "id SERIAL PRIMARY KEY NOT NULL,"
//                    + "user_id INT REFERENCES Users(id),"
//                    + "certif_name VARCHAR(50),"
//                    + "receipt_date DATE NOT NULL,"
//                    + "company_id INT REFERENCES company(id) ,"
//                    + "duration INT NOT NULL)";
//            try (Statement stmt = conn.createStatement()) {
//                stmt.executeUpdate(createTableQuery);
//                System.out.println("Certificate table created successfully!");
//            }
//        } else {
//            System.out.println("Certificate table already exists.");
//        }

        // Insert user_id into the Certificate table
        String insertUserIdQuery = "INSERT INTO Certificate (user_id, certification_name, receipt_date, company_id, duration) VALUES (?, 'Default Certificate', '2022-01-01', 0, 0)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(insertUserIdQuery)) {
            preparedStatement.setInt(1, userId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User ID added to Certificate table successfully!");
            } else {
                System.out.println("Failed to add User ID to Certificate table.");
            }
        }
    } catch (SQLException e) {
        // Handle SQLException
        if (e.getSQLState().equals("42P07")) {
            // This error code indicates that the table already exists
            System.out.println("Certificate table already exists.");
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
    
    
}
