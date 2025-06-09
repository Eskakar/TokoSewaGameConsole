/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author ASUS
 */

public class PembayaranModel {
    private int id;
    private int fk_admin;
    private int fk_console;
    private String KTP;
    private String nama_pelanggan;
    private Date tanggal_pembayaran;
    private int lama_peminjaman;
    private Date tanggal_sudah_kembali;
    private int total_harga;
    private String status_console;
    private Date tanggal_expired;

    // Default constructor
    public PembayaranModel() {
    }

    // Constructor with parameters
    public PembayaranModel(int id, int fk_admin, int fk_console, String KTP, String nama_pelanggan, 
                          Date tanggal_pembayaran, int lama_peminjaman, int total_harga, Date tanggal_expired) {
        this.id = id;
        this.fk_admin = fk_admin;
        this.fk_console = fk_console;
        this.KTP = KTP;
        this.nama_pelanggan = nama_pelanggan;
        this.tanggal_pembayaran = tanggal_pembayaran;
        this.lama_peminjaman = lama_peminjaman;
        this.total_harga = total_harga;
        this.tanggal_expired = tanggal_expired;
        this.status_console = "Dipinjam"; // Default status
    }

    // Full constructor
    public PembayaranModel(int id, int fk_admin, int fk_console, String KTP, String nama_pelanggan, 
                          Date tanggal_pembayaran, int lama_peminjaman, Date tanggal_sudah_kembali, 
                          int total_harga, String status_console, Date tanggal_expired) {
        this.id = id;
        this.fk_admin = fk_admin;
        this.fk_console = fk_console;
        this.KTP = KTP;
        this.nama_pelanggan = nama_pelanggan;
        this.tanggal_pembayaran = tanggal_pembayaran;
        this.lama_peminjaman = lama_peminjaman;
        this.tanggal_sudah_kembali = tanggal_sudah_kembali;
        this.total_harga = total_harga;
        this.status_console = status_console;
        this.tanggal_expired = tanggal_expired;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFk_admin() {
        return fk_admin;
    }

    public void setFk_admin(int fk_admin) {
        this.fk_admin = fk_admin;
    }

    public int getFk_console() {
        return fk_console;
    }

    public void setFk_console(int fk_console) {
        this.fk_console = fk_console;
    }

    public String getKTP() {
        return KTP;
    }

    public void setKTP(String KTP) {
        this.KTP = KTP;
    }

    public String getNama_pelanggan() {
        return nama_pelanggan;
    }

    public void setNama_pelanggan(String nama_pelanggan) {
        this.nama_pelanggan = nama_pelanggan;
    }

    public Date getTanggal_pembayaran() {
        return tanggal_pembayaran;
    }

    public void setTanggal_pembayaran(Date tanggal_pembayaran) {
        this.tanggal_pembayaran = tanggal_pembayaran;
    }

    public int getLama_peminjaman() {
        return lama_peminjaman;
    }

    public void setLama_peminjaman(int lama_peminjaman) {
        this.lama_peminjaman = lama_peminjaman;
    }

    public Date getTanggal_sudah_kembali() {
        return tanggal_sudah_kembali;
    }

    public void setTanggal_sudah_kembali(Date tanggal_sudah_kembali) {
        this.tanggal_sudah_kembali = tanggal_sudah_kembali;
    }

    public int getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(int total_harga) {
        this.total_harga = total_harga;
    }

    public String getStatus_console() {
        return status_console;
    }

    public void setStatus_console(String status_console) {
        this.status_console = status_console;
    }

    public Date getTanggal_expired() {
        return tanggal_expired;
    }

    public void setTanggal_expired(Date tanggal_expired) {
        this.tanggal_expired = tanggal_expired;
    }

    @Override
    public String toString() {
        return "PembayaranModel{" +
                "id=" + id +
                ", fk_admin=" + fk_admin +
                ", fk_console=" + fk_console +
                ", KTP='" + KTP + '\'' +
                ", nama_pelanggan='" + nama_pelanggan + '\'' +
                ", tanggal_pembayaran=" + tanggal_pembayaran +
                ", lama_peminjaman=" + lama_peminjaman +
                ", tanggal_sudah_kembali=" + tanggal_sudah_kembali +
                ", total_harga=" + total_harga +
                ", status_console='" + status_console + '\'' +
                ", tanggal_expired=" + tanggal_expired +
                '}';
    }
}