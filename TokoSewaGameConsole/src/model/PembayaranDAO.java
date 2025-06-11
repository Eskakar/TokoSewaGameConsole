/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class PembayaranDAO {
    //1. tambah transaksi / Create
    public boolean addTransaksi(int fk_admin, int fk_console, String KTP, String nama_pelanggan,
                            int lama_peminjaman, BigDecimal total_harga, String status, String catatan) {
    try {
        Connection conn = DatabaseConnection.getConnection();

        String sql = "INSERT INTO pembayaran " +
                     "(fk_admin, ktp, nama_pelanggan, fk_console, tanggal_pembayaran, lama_peminjaman, " +
                     "total_harga, status_console, tanggal_expired, Catatan) " +
                     "VALUES (?, ?, ?, ?, NOW(), ?, ?, ?, DATE_ADD(NOW(), INTERVAL ? DAY), ?)";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, fk_admin);
        stmt.setString(2, KTP);
        stmt.setString(3, nama_pelanggan);
        stmt.setInt(4, fk_console);
        stmt.setInt(5, lama_peminjaman);
        stmt.setBigDecimal(6, total_harga);
        stmt.setString(7, status);
        stmt.setInt(8, lama_peminjaman); // untuk INTERVAL ? DAY
        stmt.setString(9, catatan);

        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    //2. read transaksi pembayaran
    public ArrayList<PembayaranModel> getAllPembayaran() {
        ArrayList<PembayaranModel> pembayaran = new ArrayList<>();
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM pembayaran"; 
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                pembayaran.add(new PembayaranModel(
                    rs.getInt("id"),
                    rs.getInt("fk_admin"),
                    rs.getInt("fk_console"),
                    rs.getString("KTP"),
                    rs.getString("nama_pelanggan"),
                    rs.getDate("tanggal_pembayaran"),
                    rs.getInt("lama_peminjaman"),
                    rs.getInt("total_harga"),
                    rs.getDate("tanggal_expired"),
                    rs.getString("status_console"),
                    rs.getString("catatan")
                ));
            }
        } catch (SQLException e) {
        e.printStackTrace();
        }
        return pembayaran;
    }
    // 3. delete pembayaran
    public boolean deletePembayaran(int id) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "DELETE FROM pembayaran WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //4. update / edit pembayaran
    public boolean updatePembayaran(int id,Date tanggalSudahKembali,String status) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "UPDATE pembayaran SET " +
                         "tanggal_sudah_kembali = ?," +
                         "status_console = ?"+
                         "WHERE id = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, tanggalSudahKembali);
            stmt.setString(2, "Dikembalikan");
            stmt.setInt(3, id);    
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //5. ambil data pembayaran by id
     public PembayaranModel getPembayaranById(int id) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM pembayaran WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                PembayaranModel p = new PembayaranModel();
                p.setId(rs.getInt("id"));
                p.setFk_admin(rs.getInt("fk_admin"));
                p.setFk_console(rs.getInt("fk_console"));
                p.setKTP(rs.getString("ktp"));
                p.setNama_pelanggan(rs.getString("nama_pelanggan"));
                p.setTanggal_pembayaran(rs.getDate("tanggal_pembayaran"));
                p.setLama_peminjaman(rs.getInt("lama_peminjaman"));
                p.setTotal_harga(rs.getInt("total_harga"));
                p.setTanggal_expired(rs.getDate("tanggal_expired"));
                
                String status = rs.getString("status_console");
                p.setStatus_console(status != null ? status : "Dipinjam");
                
                return p;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
