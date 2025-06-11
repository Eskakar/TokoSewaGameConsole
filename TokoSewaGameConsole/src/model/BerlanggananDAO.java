/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class BerlanggananDAO {

    public ArrayList<BerlanggananModel> getAllSubscriptions() {
        ArrayList<BerlanggananModel> subscriptions = new ArrayList<>();
        String sql = "SELECT * FROM berlangganan ORDER BY nama";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                BerlanggananModel subscription = new BerlanggananModel();
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

    public boolean addSubscription(String ktp,String nama,int berapaLama) {
        String sql = "INSERT INTO berlangganan (KTP, Nama, status, tanggal_expired) VALUES (?, ?, ?, DATE_ADD(NOW(), INTERVAL ? DAY))";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ktp);
            pstmt.setString(2, nama);
            pstmt.setString(3, "Aktif");
            pstmt.setInt(4, berapaLama);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error menambah data: " + e.getMessage());
            return false;
        }
    }

    public boolean updateSubscription(BerlanggananModel subscription) {
        String sql = "UPDATE berlangganan SET Nama = ?, status = ?, tanggal_expired = ? WHERE KTP = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

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

    public boolean updateStatusByKTP(String ktp, String newStatus) {
        String sql = "UPDATE berlangganan SET status = ? WHERE KTP = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newStatus);
            pstmt.setString(2, ktp);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error mengupdate status: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteSubscription(String ktp) {
        String sql = "DELETE FROM berlangganan WHERE KTP = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ktp);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error menghapus data: " + e.getMessage());
            return false;
        }
    }

    public BerlanggananModel getSubscriptionByKTP(String ktp) {
        String sql = "SELECT * FROM berlangganan WHERE KTP = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ktp);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    BerlanggananModel subscription = new BerlanggananModel();
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
    public void expireSubscriptionsToday() {
        String sql = "UPDATE berlangganan SET status = 'Expired' WHERE DATE(tanggal_expired) < CURDATE() AND status != 'Expired'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate(); 
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error mengupdate status expired: " + e.getMessage());
        }
    }
}

