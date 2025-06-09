/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author Anzio
 */
public class Pembayaran {
    private int id;
    private int fkAdmin;
    private String ktp;
    private String namaPelanggan;
    private int fkConsole;
    private LocalDate tanggalPembayaran;
    private int lamaPeminjaman;
    private LocalDate tanggalSudahKembali;
    private int totalHarga;
    private String statusConsole;
    private LocalDate tanggalExpired;
    private String catatan;
    private String metodePembayaran;
    private String kodeVoucher;
    
    // Constructor kosong
    public Pembayaran() {
        this.tanggalPembayaran = LocalDate.now();
        this.statusConsole = "Dipinjam";
    }
    
    // Constructor dengan parameter
    public Pembayaran(String ktp, String namaPelanggan, int fkConsole, 
                     int lamaPeminjaman, int totalHarga, String catatan, 
                     String metodePembayaran, String kodeVoucher) {
        this();
        this.ktp = ktp;
        this.namaPelanggan = namaPelanggan;
        this.fkConsole = fkConsole;
        this.lamaPeminjaman = lamaPeminjaman;
        this.totalHarga = totalHarga;
        this.catatan = catatan;
        this.metodePembayaran = metodePembayaran;
        this.kodeVoucher = kodeVoucher;
        this.tanggalExpired = this.tanggalPembayaran.plusDays(lamaPeminjaman);
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getFkAdmin() { return fkAdmin; }
    public void setFkAdmin(int fkAdmin) { this.fkAdmin = fkAdmin; }
    
    public String getKtp() { return ktp; }
    public void setKtp(String ktp) { this.ktp = ktp; }
    
    public String getNamaPelanggan() { return namaPelanggan; }
    public void setNamaPelanggan(String namaPelanggan) { this.namaPelanggan = namaPelanggan; }
    
    public int getFkConsole() { return fkConsole; }
    public void setFkConsole(int fkConsole) { this.fkConsole = fkConsole; }
    
    public LocalDate getTanggalPembayaran() { return tanggalPembayaran; }
    public void setTanggalPembayaran(LocalDate tanggalPembayaran) { this.tanggalPembayaran = tanggalPembayaran; }
    
    public int getLamaPeminjaman() { return lamaPeminjaman; }
    public void setLamaPeminjaman(int lamaPeminjaman) { 
        this.lamaPeminjaman = lamaPeminjaman;
        if (this.tanggalPembayaran != null) {
            this.tanggalExpired = this.tanggalPembayaran.plusDays(lamaPeminjaman);
        }
    }
    
    public LocalDate getTanggalSudahKembali() { return tanggalSudahKembali; }
    public void setTanggalSudahKembali(LocalDate tanggalSudahKembali) { this.tanggalSudahKembali = tanggalSudahKembali; }
    
    public int getTotalHarga() { return totalHarga; }
    public void setTotalHarga(int totalHarga) { this.totalHarga = totalHarga; }
    
    public String getStatusConsole() { return statusConsole; }
    public void setStatusConsole(String statusConsole) { this.statusConsole = statusConsole; }
    
    public LocalDate getTanggalExpired() { return tanggalExpired; }
    public void setTanggalExpired(LocalDate tanggalExpired) { this.tanggalExpired = tanggalExpired; }
    
    public String getCatatan() { return catatan; }
    public void setCatatan(String catatan) { this.catatan = catatan; }
    
    public String getMetodePembayaran() { return metodePembayaran; }
    public void setMetodePembayaran(String metodePembayaran) { this.metodePembayaran = metodePembayaran; }
    
    public String getKodeVoucher() { return kodeVoucher; }
    public void setKodeVoucher(String kodeVoucher) { this.kodeVoucher = kodeVoucher; }
    
    @Override
    public String toString() {
        return "Pembayaran{" +
                "id=" + id +
                ", ktp='" + ktp + '\'' +
                ", namaPelanggan='" + namaPelanggan + '\'' +
                ", fkConsole=" + fkConsole +
                ", lamaPeminjaman=" + lamaPeminjaman +
                ", totalHarga=" + totalHarga +
                ", statusConsole='" + statusConsole + '\'' +
                '}';
    }
}