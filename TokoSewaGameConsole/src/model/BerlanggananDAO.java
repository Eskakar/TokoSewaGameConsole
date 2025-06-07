/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Anzio
 */
public class BerlanggananDAO {
    
    private Connection connection;
    
    public BerlanggananDAO() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }
    
    /**
     * Menambahkan data berlangganan baru
     * @param berlangganan object Berlangganan
     * @return true jika berhasil, false jika gagal
     */
    public boolean tambahBerlangganan(Berlangganan berlangganan) {
        String sql = "INSERT INTO berlangganan (KTP, Nama, status, tanggal_expired) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, berlangganan.getKtp());
            stmt.setString(2, berlangganan.getNama());
            stmt.setString(3, berlangganan.getStatus());
            stmt.setDate(4, Date.valueOf(berlangganan.getTanggalExpired()));
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error saat menambah berlangganan: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Mengecek apakah KTP sudah terdaftar
     * @param ktp nomor KTP
     * @return true jika sudah ada, false jika belum
     */
    public boolean isKTPExists(String ktp) {
        String sql = "SELECT COUNT(*) FROM berlangganan WHERE KTP = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ktp);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error saat mengecek KTP: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Mengupdate status berlangganan berdasarkan tanggal expired
     * @param ktp nomor KTP
     * @return true jika berhasil, false jika gagal
     */
    public boolean updateStatusBerlangganan(String ktp) {
        String sql = "UPDATE berlangganan SET status = CASE " +
                    "WHEN tanggal_expired >= CURDATE() THEN 'Aktif' " +
                    "ELSE 'Tidak Aktif' END " +
                    "WHERE KTP = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ktp);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error saat update status: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Mendapatkan semua data berlangganan
     * @return List berlangganan
     */
    public List<Berlangganan> getAllBerlangganan() {
        List<Berlangganan> listBerlangganan = new ArrayList<>();
        String sql = "SELECT * FROM berlangganan ORDER BY tanggal_expired DESC";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Berlangganan berlangganan = new Berlangganan();
                berlangganan.setKtp(rs.getString("KTP"));
                berlangganan.setNama(rs.getString("Nama"));
                berlangganan.setStatus(rs.getString("status"));
                berlangganan.setTanggalExpired(rs.getDate("tanggal_expired").toLocalDate());
                
                listBerlangganan.add(berlangganan);
            }
            
        } catch (SQLException e) {
            System.err.println("Error saat mengambil data berlangganan: " + e.getMessage());
        }
        
        return listBerlangganan;
    }
    
    /**
     * Mendapatkan data berlangganan berdasarkan KTP
     * @param ktp nomor KTP
     * @return object Berlangganan atau null jika tidak ditemukan
     */
    public Berlangganan getBerlanggananByKTP(String ktp) {
        String sql = "SELECT * FROM berlangganan WHERE KTP = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ktp);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Berlangganan berlangganan = new Berlangganan();
                berlangganan.setKtp(rs.getString("KTP"));
                berlangganan.setNama(rs.getString("Nama"));
                berlangganan.setStatus(rs.getString("status"));
                berlangganan.setTanggalExpired(rs.getDate("tanggal_expired").toLocalDate());
                
                return berlangganan;
            }
            
        } catch (SQLException e) {
            System.err.println("Error saat mengambil data berlangganan: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Menghapus data berlangganan
     * @param ktp nomor KTP
     * @return true jika berhasil, false jika gagal
     */
    public boolean hapusBerlangganan(String ktp) {
        String sql = "DELETE FROM berlangganan WHERE KTP = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ktp);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error saat menghapus berlangganan: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Mengupdate semua status berlangganan berdasarkan tanggal expired
     */
    public void updateAllStatusBerlangganan() {
        String sql = "UPDATE berlangganan SET status = CASE " +
                    "WHEN tanggal_expired >= CURDATE() THEN 'Aktif' " +
                    "ELSE 'Tidak Aktif' END";
        
        try (Statement stmt = connection.createStatement()) {
            int rowsAffected = stmt.executeUpdate(sql);
            System.out.println("Status " + rowsAffected + " berlangganan telah diupdate.");
            
        } catch (SQLException e) {
            System.err.println("Error saat update semua status: " + e.getMessage());
        }
    }
}