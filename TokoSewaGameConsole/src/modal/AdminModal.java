/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modal;

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
public class AdminModal {
    // inner class untuk paket console
    public static class Console {
        public int id;
        public String nama_paket;
        public String deskripsi;
        public int stock;
        public int harga;

        public Console(int id, String nama, String desk, int stck, int harga) {
            this.id = id;
            this.nama_paket = nama;
            this.deskripsi = desk;
            this.stock = stck;
            this.harga = harga;
        }
    }
    // inner class untuk data pelanggan berlanggan
    public static class Berlangganan {
        public String KTP;
        public String nama;
        public String status;
        public Date tanggal_expired;

        public Berlangganan(String KTP, String nama, String status,Date tanggal) {
            this.KTP = KTP;
            this.nama = nama;
            this.status = status;
            this.tanggal_expired = tanggal;
        }
    }
    // inner class untuk data diskon
    public static class Diskon {
        public String Kode_unik;
        public int diskon;

        public Diskon(String kode, int diskon) {
            this.Kode_unik = kode;
            this.diskon = diskon;
        }
    }
    // inner class untuk data Pembayaran
    public static class Pembayaran {
        public int id;
        public int fk_admin;
        public int fk_console;
        public String KTP;
        public String nama_pelanggan;
        public Date tanggal_pembayaran;
        public int lama_peminjaman;
        public Date tanggal_pengembalian; //kapan pelanggan sudah mengembalikan ke toko
        public int total_harga;
        public String status_console;//sudah dikembalikan atau belum
        public Date tanggal_expired; //batas akhir pengembalian game console
        
         // Constructor dengan parameter
        public Pembayaran(int id, int fk_admin, int fk_console, String KTP, String nama_pelanggan, 
                     Date tanggal_pembayaran, int lama_peminjaman, int total_harga, 
                     Date tanggal_expired) {
            this.id = id;
            this.fk_admin = fk_admin;
            this.fk_console = fk_console;
            this.KTP = KTP;
            this.nama_pelanggan = nama_pelanggan;
            this.tanggal_pembayaran = tanggal_pembayaran;
            this.lama_peminjaman = lama_peminjaman;
            this.tanggal_pengembalian = null; // Awalnya null karena belum dikembalikan
            this.total_harga = total_harga;
            this.status_console = "Belum Dikembalikan"; // Status awal
            this.tanggal_expired = tanggal_expired;
        }
    }
    // inner class untuk data Admin
    public static class Admin {
        public int id;
        public String nama;
        public String sandi;

        public Admin(int id,String nama, String sandi) {
            this.id = id;
            this.nama = nama;
            this.sandi = sandi;
        }
    }
    //=====================================================
    //sekarang method untuk sql Querry CRUDE ke database
    
    //A. Pembayaran
    //1. tambah transaksi / Create
    public boolean addTransaksi( int fk_admin, int fk_console, String KTP, String nama_pelanggan, int lama_peminjaman, int total_harga,String status) {
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
            stmt.setInt(7, total_harga);
            stmt.setString(8, status);
            stmt.setInt(9, lama_peminjaman);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //2. read transaksi pembayaran
    public ArrayList<Pembayaran> getPembayaran() {
        ArrayList<Pembayaran> pembayaran = new ArrayList<>();
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM pembayaran"; 
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                pembayaran.add(new Pembayaran(
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
    //B. cosole
    //1.create / add console
    public boolean addConsole(String nama, String deskripsi, int stock, int harga) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO console (nama, deskripsi, stock, harga) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, nama);
            stmt.setString(2, deskripsi);
            stmt.setInt(3, stock);
            stmt.setInt(4, harga);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //2. get / read all console -- jika diperlukan
    public ArrayList<Console> getAllConsoles() {
        ArrayList<Console> consoles = new ArrayList<>();
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM console";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                consoles.add(new Console(
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getString("deskripsi"),
                    rs.getInt("stock"),
                    rs.getInt("harga")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consoles;
    }
    //3.get /read satu console (untuk halaman depan)
    public Console getConsoleById(int id) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM console WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Console(
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getString("deskripsi"),
                    rs.getInt("stock"),
                    rs.getInt("harga")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //5.update / edit console -- jika perlu 5 & 6 overloading namanya
    public boolean updateConsole(int id, int stock) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "UPDATE console SET stock = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, stock);
            stmt.setInt(2, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //5.update / edit console -- jika perlu
    public boolean updateConsole(int id, String nama, String deskripsi, int stock, int harga) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "UPDATE console SET nama = ?, deskripsi = ?, stock = ?, harga = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, nama);
            stmt.setString(2, deskripsi);
            stmt.setInt(3, stock);
            stmt.setInt(4, harga);
            stmt.setInt(5, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //6. delete console -- jika perlu
    public boolean deleteConsole(int id) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "DELETE FROM console WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //C. diskon
    //1. read diskon
    public ArrayList<Diskon> getAllDiskon() {
        ArrayList<Diskon> diskon = new ArrayList<>();
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM diskon";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                diskon.add(new Diskon(
                    rs.getString("kode_unik"),
                    rs.getInt("person")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return diskon;
    }
    //D. berlangganan -- jika perlu
    //1.read
    public ArrayList<Berlangganan> readAllBerlangganan() {
        ArrayList<Berlangganan> langgananList = new ArrayList<>();
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT KTP, Nama, status, tanggal_expired_date FROM berlangganan";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                langgananList.add(new Berlangganan(
                    rs.getString("KTP"),
                    rs.getString("Nama"),
                    rs.getString("status"),
                    rs.getDate("tanggal_expired_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return langgananList;
    }
    //2. create
    public boolean createBerlangganan(String KTP, String nama, String status, Date tanggalExpired) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO berlangganan (KTP, Nama, status, tanggal_expired_date) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, KTP);
            stmt.setString(2, nama);
            stmt.setString(3, status);
            stmt.setDate(4, tanggalExpired);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //3.update
    public boolean updateStatusBerlangganan(String KTP, String newStatus) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "UPDATE berlangganan SET status = ? WHERE KTP = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, newStatus);
            stmt.setString(2, KTP);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
