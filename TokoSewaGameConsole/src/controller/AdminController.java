/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author ASUS
 */
import model.AdminModel;
import model.Pembayaran;
import model.Berlangganan;
import model.Console;
import model.Diskon;
import view.AdminView;

import java.util.ArrayList;
import java.sql.Date;

public class AdminController {
    private AdminModel modelAdmin;
    private AdminView viewAdmin;

    public AdminController(AdminModel modelA, AdminView viewA) {
        this.modelAdmin = modelA;
        this.viewAdmin = viewA;
        viewA.setController(this);
        loadInitialData();
    }

    private void loadInitialData() {
        loadPembayaranList();
        loadConsoleList();
        loadDiskonList();
        loadBerlanggananList();
    }

    // ===== PEMBAYARAN =====
    public void loadPembayaranList() {
        ArrayList<Pembayaran> pembayaranList = modelAdmin.getAllPembayaran();
        viewAdmin.showPembayaranList(pembayaranList); //jika perlu
    }

    public void addPembayaran(int fk_admin, int fk_console, String KTP, String nama_pelanggan,
                              int lama_peminjaman, String kodeDiskon) {

        // Step 1: Ambil data console berdasarkan ID
        Console console = modelAdmin.getConsoleById(fk_console);
        if (console == null) {
            viewAdmin.showMessage("Console tidak ditemukan.");
            return;
        }else if(console.stock == 0){
            viewAdmin.showMessage("Console Kehabisan Stock");
            return;
        }

        int hargaDasar = console.harga;
        int totalHarga = hargaDasar * lama_peminjaman;

        // Step 2: Cek diskon dari kode diskon jika ada
        if (kodeDiskon != null && !kodeDiskon.isEmpty()) {
            ArrayList<Diskon> diskons = modelAdmin.getAllDiskon();
            for (Diskon d : diskons) {
                if (d.Kode_unik.equalsIgnoreCase(kodeDiskon)) {
                    int potongan = (totalHarga * d.diskon) / 100;
                    totalHarga -= potongan;
                    break;
                }
            }
        }

        // Step 3: Cek apakah pelanggan berlangganan
        ArrayList<Berlangganan> langganans = modelAdmin.readAllBerlangganan();
        for (Berlangganan b : langganans) {
            if (b.KTP.equals(KTP) && b.status.equalsIgnoreCase("Aktif")) {
                // Isi otomatis nama
                nama_pelanggan = b.nama;
                // Diskon tambahan 20%
                totalHarga -= (totalHarga * 20) / 100;
                break;
            }
        }

        // Step 4: Tambahkan transaksi
        boolean success = modelAdmin.addTransaksi(fk_admin, fk_console, KTP, nama_pelanggan, lama_peminjaman, totalHarga, "Belum Dikembalikan");
        if (success) {
            viewAdmin.showMessage("Pembayaran berhasil ditambahkan!");
            loadPembayaranList();
        } else {
            viewAdmin.showMessage("Gagal menambahkan pembayaran.");
        }
    }

    public void updatePembayaranStatus(int id, Date tanggal) {
        if (modelAdmin.updatePembayaran(id, tanggal)) {
            viewAdmin.showMessage("Status pembayaran berhasil diperbarui!");
            loadPembayaranList();
        } else {
            viewAdmin.showMessage("Gagal memperbarui status pembayaran.");
        }
    }

    // ===== CONSOLE =====
    public void loadConsoleList() { //jika perlu untuk ambil data semua console
        ArrayList<Console> consoleList = modelAdmin.getAllConsoles();
        viewAdmin.showConsoleList(consoleList);
        
    }

    public void addConsole(String nama, String deskripsi, int stock, int harga) {
        if (modelAdmin.addConsole(nama, deskripsi, stock, harga)) {
            viewAdmin.showMessage("Console berhasil ditambahkan!");
            loadConsoleList();
        } else {
            viewAdmin.showMessage("Gagal menambahkan console.");
        }
    }

    public void updateConsole(int id, String nama, String deskripsi, int stock, int harga) {
        if (modelAdmin.updateConsole(id, stock)) {
            viewAdmin.showMessage("Console berhasil diperbarui!");
            loadConsoleList();
        } else {
            viewAdmin.showMessage("Gagal memperbarui console.");
        }
    }
    /* jika perlu
    public void deleteConsole(int id) {
        if (modelAdmin.deleteConsole(id)) {
            viewAdmin.showMessage("Console berhasil dihapus!");
            loadConsoleList();
        } else {
            viewAdmin.showMessage("Gagal menghapus console.");
        }
    }*/

    // ===== DISKON =====
    public void loadDiskonList() {
        ArrayList<Diskon> diskonList = modelAdmin.getAllDiskon(); // jika perlu
        viewAdmin.showDiskonList(diskonList);
    }

    // ===== BERLANGGANAN =====
    public void loadBerlanggananList() {
        ArrayList<Berlangganan> berlanggananList = modelAdmin.readAllBerlangganan();
        viewAdmin.showBerlanggananList(berlanggananList);
    }

    public void updateStatusBerlangganan(String KTP, String newStatus) {
        if (modelAdmin.updateStatusBerlangganan(KTP, newStatus)) {
            viewAdmin.showMessage("Status berlangganan berhasil diperbarui!");
            loadBerlanggananList();
        } else {
            viewAdmin.showMessage("Gagal memperbarui status berlangganan.");
        }
    }
}
