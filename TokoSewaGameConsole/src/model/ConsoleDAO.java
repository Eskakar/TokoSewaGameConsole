/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Asus
 */
public class ConsoleDAO {
    
    /**
     * Mengambil semua data console dari database
     * @return List of ConsoleModel objects
     */
    public ArrayList<ConsoleModel> getAllConsoles() {
        ArrayList<ConsoleModel> consoleList = new ArrayList<>();
        String query = "SELECT * FROM console ORDER BY id";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                ConsoleModel console = new ConsoleModel();
                console.setId(rs.getInt("id"));
                console.setPaket(rs.getString("paket"));
                console.setDeskripsi(rs.getString("deskripsi"));
                console.setStock(rs.getInt("stock"));
                console.setHarga(rs.getBigDecimal("harga"));
                
                consoleList.add(console);
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving console data: " + e.getMessage());
            e.printStackTrace();
        }
        
        return consoleList;
    }
    
    /**
     * Mengambil data console berdasarkan ID
     * @param id ID console
     * @return ConsoleModel object atau null jika tidak ditemukan
     */
    public ConsoleModel getConsoleById(int id) {
        String query = "SELECT * FROM console WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                ConsoleModel console = new ConsoleModel();
                console.setId(rs.getInt("id"));
                console.setPaket(rs.getString("paket"));
                console.setDeskripsi(rs.getString("deskripsi"));
                console.setStock(rs.getInt("stock"));
                console.setHarga(rs.getBigDecimal("harga"));
                
                return console;
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving console by ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    
    
    public boolean updateStock(int id, int newStock) {
        String query = "UPDATE console SET stock = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, newStock);
            stmt.setInt(2, id);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating stock: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Menambah stok console (untuk restocking)
     * @param id ID console
     * @param additionalStock Jumlah stok yang ditambahkan
     * @return true jika berhasil, false jika gagal
     */
    public boolean addStock(int id, int additionalStock) {
        String query = "UPDATE console SET stock = stock + ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, additionalStock);
            stmt.setInt(2, id);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error adding stock: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Mengurangi stok console (untuk peminjaman)
     * @param id ID console
     * @param reduceStock Jumlah stok yang dikurangi
     * @return true jika berhasil, false jika gagal
     */
    public boolean reduceStock(int id, int reduceStock) {
        // Cek apakah stok mencukupi
        ConsoleModel console = getConsoleById(id);
        if (console == null || console.getStock() < reduceStock) {
            System.err.println("Insufficient stock or console not found");
            return false;
        }
        
        String query = "UPDATE console SET stock = stock - ? WHERE id = ? AND stock >= ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, reduceStock);
            stmt.setInt(2, id);
            stmt.setInt(3, reduceStock);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error reducing stock: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Update seluruh data console
     * @param console ConsoleModel object dengan data baru
     * @return true jika berhasil, false jika gagal
     */
    public boolean updateConsole(ConsoleModel console) {
        String query = "UPDATE console SET paket = ?, deskripsi = ?, stock = ?, harga = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, console.getPaket());
            stmt.setString(2, console.getDeskripsi());
            stmt.setInt(3, console.getStock());
            stmt.setBigDecimal(4, console.getHarga());
            stmt.setInt(5, console.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating console: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
