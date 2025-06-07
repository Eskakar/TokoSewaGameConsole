/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Anzio
 */
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object untuk Console
 * @author Anzio
 */
public class ConsoleDAO {
    
    /**
     * Mengambil semua data console dari database
     * @return List of Console objects
     * @throws SQLException
     */
    public List<ConsoleModel> getAllConsoles() throws SQLException {
        List<ConsoleModel> consoles = new ArrayList<>();
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
                
                consoles.add(console);
            }
        }
        
        return consoles;
    }
    
    /**
     * Mengambil data console berdasarkan ID
     * @param id ID console
     * @return Console object atau null jika tidak ditemukan
     * @throws SQLException
     */
    public ConsoleModel getConsoleById(int id) throws SQLException {
        String query = "SELECT * FROM console WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ConsoleModel console = new ConsoleModel();
                    console.setId(rs.getInt("id"));
                    console.setPaket(rs.getString("paket"));
                    console.setDeskripsi(rs.getString("deskripsi"));
                    console.setStock(rs.getInt("stock"));
                    console.setHarga(rs.getBigDecimal("harga"));
                    
                    return console;
                }
            }
        }
        
        return null;
    }
    
    /**
     * Mengupdate stock console
     * @param id ID console
     * @param newStock Stock baru
     * @return true jika berhasil, false jika gagal
     * @throws SQLException
     */
    public boolean updateStock(int id, int newStock) throws SQLException {
        String query = "UPDATE console SET stock = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, newStock);
            stmt.setInt(2, id);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    /**
     * Mengurangi stock console (untuk peminjaman)
     * @param id ID console
     * @return true jika berhasil, false jika gagal atau stock tidak cukup
     * @throws SQLException
     */
    public boolean decreaseStock(int id) throws SQLException {
        ConsoleModel console = getConsoleById(id);
        if (console != null && console.getStock() > 0) {
            return updateStock(id, console.getStock() - 1);
        }
        return false;
    }
    
    /**
     * Menambah stock console (untuk pengembalian)
     * @param id ID console
     * @return true jika berhasil, false jika gagal
     * @throws SQLException
     */
    public boolean increaseStock(int id) throws SQLException {
        ConsoleModel console = getConsoleById(id);
        if (console != null) {
            return updateStock(id, console.getStock() + 1);
        }
        return false;
    }
    
    /**
     * Mengecek apakah console tersedia (stock > 0)
     * @param id ID console
     * @return true jika tersedia, false jika tidak
     * @throws SQLException
     */
    public boolean isConsoleAvailable(int id) throws SQLException {
        ConsoleModel console = getConsoleById(id);
        return console != null && console.getStock() > 0;
    }
}
