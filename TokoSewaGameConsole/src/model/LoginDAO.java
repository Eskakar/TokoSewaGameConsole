/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Anzio
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginDAO {

    
    public String authenticateUser(String username, String password) {
        String role = null;
        
        try {
            // First, check in customer table
            role = checkAdminCredentials(username, password);
            
        } catch (SQLException e) {
            System.err.println("Error during authentication: " + e.getMessage());
        }
        
        return role;
    }
    
    /**
     * Check credentials in customer table
     * @param username Username
     * @param password Password
     * @return "customer" if found, null if not found
     * @throws SQLException
     */
   
    
    /**
     * Check credentials in admin table
     * @param username Username
     * @param password Password
     * @return "admin" if found, null if not found
     * @throws SQLException
     */
    private String checkAdminCredentials(String username, String password) throws SQLException {
        String query = "SELECT * FROM admin WHERE nama = ? AND sandi = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return "admin";
                }
            }
        }
        
        return null;
    }
    
    /**
     * Get customer details by username (optional, untuk keperluan lain)
     * @param username Username customer
     * @return ResultSet dengan data customer
     */
   
    
    /**
     * Get admin details by username (optional, untuk keperluan lain)
     * @param username Username admin
     * @return ResultSet dengan data admin
     */
    /*
    public ResultSet getAdminByUsername(String username) {
        try {
            String query = "SELECT * FROM admin WHERE nama = ?";
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, username);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error getting admin data: " + e.getMessage());
            return null;
        }
    }*/
    
    /**
     * Close database connection
     */
    public void closeConnection() {
        try {Connection connection = DatabaseConnection.getConnection();
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
    
    /**
     * Check if database connection is still valid
     * @return true if connection is valid, false otherwise
     */
    public boolean isConnectionValid() {
        try {Connection connection = DatabaseConnection.getConnection();
            return connection != null && !connection.isClosed() && connection.isValid(3);
        } catch (SQLException e) {
            return false;
        }
    }
    public AdminModel getAdminByUsername(String nama) {
        String query = "SELECT * FROM admin WHERE nama = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, nama);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                AdminModel adminModel = new AdminModel(0,null,null);
                adminModel.setId(rs.getInt("id"));
                adminModel.setNama(rs.getString("nama"));
                adminModel.setSandi(rs.getString("sandi"));               
                return adminModel;
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving admin by nama: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
}

