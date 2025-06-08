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
    public boolean addTransaksi( int fk_admin, int fk_console, String KTP, String nama_pelanggan, int lama_peminjaman, BigDecimal total_harga,String status) {
        try {
            //id auto , tanggal sesuai tanggal sekarang, tanggal sudah kembali belum dimasukin
            //yang dari DB gak ditulis cuman ID dan tanggal_sudah_dikembalikan
            Connection conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO pembayaran (fk_admin, ktp, nama_pelanggan,fk_console,tanggal_pembayaran,lama_peminjaman,total_harga,status_console,tanggal_expired) VALUES (?, ?, ?, ?,NOW(),?,?,?,DATE_ADD(NOW(), INTERVAL ? DAY))";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, fk_admin);
            stmt.setString(2, KTP);
            stmt.setString(3, nama_pelanggan);
            stmt.setInt(4, fk_console);
            stmt.setInt(6, lama_peminjaman);
            stmt.setBigDecimal(7, total_harga);
            stmt.setString(8, status);
            stmt.setInt(9, lama_peminjaman);

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
                    rs.getDate("tanggal_expired")
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
    public boolean updatePembayaran(int id,Date tanggalSudahKembali) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "UPDATE pembayaran SET " +
                         "tanggal_sudah_kembali = ? " +
                         "WHERE id = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, tanggalSudahKembali);
            stmt.setInt(2, id);    
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
