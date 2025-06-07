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
public class Pembayaran {
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
