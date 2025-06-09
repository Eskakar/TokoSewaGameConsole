/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


import model.Pembayaran;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
/**
 *
 * @author Anzio
 */
public class PembayaranDAO {
        public boolean insertPembayaran(Pembayaran pembayaran) {
        String sql = "INSERT INTO pembayaran (fk_admin, ktp, nama_pelanggan, fk_console, " +
                    "tanggal_pembayaran, lama_peminjaman, total_harga, status_console, tanggal_expired) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, 1); // Default admin ID = 1
            pstmt.setString(2, pembayaran.getKtp());
            pstmt.setString(3, pembayaran.getNamaPelanggan());
            pstmt.setInt(4, pembayaran.getFkConsole());
            pstmt.setDate(5, Date.valueOf(pembayaran.getTanggalPembayaran()));
            pstmt.setInt(6, pembayaran.getLamaPeminjaman());
            pstmt.setInt(7, pembayaran.getTotalHarga());
            pstmt.setString(8, pembayaran.getStatusConsole());
            pstmt.setDate(9, Date.valueOf(pembayaran.getTanggalExpired()));
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                // Mendapatkan generated ID
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        pembayaran.setId(generatedKeys.getInt(1));
                    }
                }
                
                // Update stock console (kurangi 1)
                updateConsoleStock(pembayaran.getFkConsole(), -1);
                
                System.out.println("Data pembayaran berhasil disimpan dengan ID: " + pembayaran.getId());
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error saat menyimpan pembayaran: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Mengambil semua data console yang tersedia
     * @return List of Console objects
     */
    public List<ConsoleModel> getAllConsoles() {
        List<ConsoleModel> consoles = new ArrayList<>();
        String sql = "SELECT * FROM console WHERE stock > 0 ORDER BY harga";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                ConsoleModel console = new ConsoleModel();
                console.setId(rs.getInt("id"));
                console.setPaket(rs.getString("paket"));
                console.setDeskripsi(rs.getString("deskripsi"));
                console.setStock(rs.getInt("stock"));
                console.setHarga(rs.getBigDecimal("harga"));
                consoles.add(console);
            }
            
        } catch (SQLException e) {
            System.err.println("Error saat mengambil data console: " + e.getMessage());
        }
        
        return consoles;
    }
    
    /**
     * Mengambil data console berdasarkan ID
     * @param consoleId ID console
     * @return Console object atau null jika tidak ditemukan
     */
    public ConsoleModel getConsoleById(int consoleId) {
        String sql = "SELECT * FROM console WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, consoleId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
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
            
        } catch (SQLException e) {
            System.err.println("Error saat mengambil console by ID: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Update stock console
     * @param consoleId ID console
     * @param stockChange perubahan stock (+ untuk tambah, - untuk kurang)
     * @return true jika berhasil
     */
    private boolean updateConsoleStock(int consoleId, int stockChange) {
        String sql = "UPDATE console SET stock = stock + ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, stockChange);
            pstmt.setInt(2, consoleId);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error saat update stock console: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Cek apakah kode voucher valid dan ambil nilai diskonnya
     * @param kodeVoucher kode voucher
     * @return persentase diskon (0 jika tidak valid)
     */
    public int getDiskonByKode(String kodeVoucher) {
        if (kodeVoucher == null || kodeVoucher.trim().isEmpty()) {
            return 0;
        }
        
        String sql = "SELECT persen FROM diskon WHERE kode_unik = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, kodeVoucher.trim());
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("persen");
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error saat cek voucher: " + e.getMessage());
        }
        
        return 0;
    }
    
    /**
     * Menghitung total harga dengan diskon
     * @param hargaAsli harga asli
     * @param lamaPeminjaman lama peminjaman dalam hari
     * @param persenDiskon persentase diskon (0-100)
     * @return total harga setelah diskon
     */
    public int hitungTotalHarga(BigDecimal hargaAsli, int lamaPeminjaman, int persenDiskon) {
        BigDecimal totalSebelumDiskon = hargaAsli.multiply(BigDecimal.valueOf(lamaPeminjaman));
        
        if (persenDiskon > 0) {
            BigDecimal diskon = totalSebelumDiskon.multiply(BigDecimal.valueOf(persenDiskon))
                                                 .divide(BigDecimal.valueOf(100));
            totalSebelumDiskon = totalSebelumDiskon.subtract(diskon);
        }
        
        return totalSebelumDiskon.intValue();
    }
    
    /**
     * Mengambil semua data pembayaran
     * @return List of Pembayaran objects
     */
    public List<Pembayaran> getAllPembayaran() {
        List<Pembayaran> pembayaranList = new ArrayList<>();
        String sql = "SELECT p.*, c.paket FROM pembayaran p " +
                    "JOIN console c ON p.fk_console = c.id " +
                    "ORDER BY p.tanggal_pembayaran DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Pembayaran pembayaran = new Pembayaran();
                pembayaran.setId(rs.getInt("id"));
                pembayaran.setKtp(rs.getString("ktp"));
                pembayaran.setNamaPelanggan(rs.getString("nama_pelanggan"));
                pembayaran.setFkConsole(rs.getInt("fk_console"));
                pembayaran.setTanggalPembayaran(rs.getDate("tanggal_pembayaran").toLocalDate());
                pembayaran.setLamaPeminjaman(rs.getInt("lama_peminjaman"));
                pembayaran.setTotalHarga(rs.getInt("total_harga"));
                pembayaran.setStatusConsole(rs.getString("status_console"));
                pembayaran.setTanggalExpired(rs.getDate("tanggal_expired").toLocalDate());
                
                if (rs.getDate("tanggal_sudah_kembali") != null) {
                    pembayaran.setTanggalSudahKembali(rs.getDate("tanggal_sudah_kembali").toLocalDate());
                }
                
                pembayaranList.add(pembayaran);
            }
            
        } catch (SQLException e) {
            System.err.println("Error saat mengambil data pembayaran: " + e.getMessage());
        }
        
        return pembayaranList;
    }
}