/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Anzio
 */
public class SubsDAO {
    private Connection connection;
    

 
    
    
    /**
     * Mengambil semua data subscription dari database
     * @return List berisi semua data subscription
     */
    public List<SubsModel> getAllSubscriptions() {
        List<SubsModel> subscriptions = new ArrayList<>();
        String sql = "SELECT * FROM berlangganan ORDER BY nama";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                SubsModel subscription = new SubsModel();
                subscription.setKtp(rs.getString("KTP"));
                subscription.setNama(rs.getString("Nama"));
                subscription.setStatus(rs.getString("status"));
                subscription.setTanggalExpired(rs.getDate("tanggal_expired"));
                
                subscriptions.add(subscription);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error mengambil data: " + e.getMessage());
        }
        
        return subscriptions;
    }
    
    /**
     * Menambah subscription baru ke database
     * @param subscription data subscription yang akan ditambah
     * @return true jika berhasil, false jika gagal
     */
    public boolean addSubscription(SubsModel subscription) {
        String sql = "INSERT INTO berlangganan (KTP, Nama, status, tanggal_expired) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, subscription.getKtp());
            pstmt.setString(2, subscription.getNama());
            pstmt.setString(3, subscription.getStatus());
            pstmt.setDate(4, new java.sql.Date(subscription.getTanggalExpired().getTime()));
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error menambah data: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Mengupdate data subscription berdasarkan KTP
     * @param subscription data subscription yang akan diupdate
     * @return true jika berhasil, false jika gagal
     */
    public boolean updateSubscription(SubsModel subscription) {
        String sql = "UPDATE berlangganan SET Nama = ?, status = ?, tanggal_expired = ? WHERE KTP = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, subscription.getNama());
            pstmt.setString(2, subscription.getStatus());
            pstmt.setDate(3, new java.sql.Date(subscription.getTanggalExpired().getTime()));
            pstmt.setString(4, subscription.getKtp());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error mengupdate data: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Menghapus subscription berdasarkan KTP
     * @param ktp nomor KTP subscription yang akan dihapus
     * @return true jika berhasil, false jika gagal
     */
    public boolean deleteSubscription(String ktp) {
        String sql = "DELETE FROM berlangganan WHERE KTP = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, ktp);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error menghapus data: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Mencari subscription berdasarkan KTP
     * @param ktp nomor KTP yang dicari
     * @return object Subscription jika ditemukan, null jika tidak ditemukan
     */
    public SubsModel getSubscriptionByKTP(String ktp) {
        String sql = "SELECT * FROM berlangganan WHERE KTP = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, ktp);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    SubsModel subscription = new SubsModel();
                    subscription.setKtp(rs.getString("KTP"));
                    subscription.setNama(rs.getString("Nama"));
                    subscription.setStatus(rs.getString("status"));
                    subscription.setTanggalExpired(rs.getDate("tanggal_expired"));
                    
                    return subscription;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error mencari data: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Menutup koneksi database
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error menutup koneksi: " + e.getMessage());
        }
    }
}
