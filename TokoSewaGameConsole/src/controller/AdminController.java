/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author ASUS
 */
import model.AdminModal;
import model.AdminModal.Pembayaran;
import model.AdminModal.Berlangganan;
import model.AdminModal.Console;
import model.AdminModal.Diskon;
import view.AdminView;

import java.util.ArrayList;
import java.sql.Date;
import view.Login;

public class AdminController {
    private AdminModal model;
    private AdminView view;

    public AdminController(AdminModal model, AdminView view) {
        this.model = model;
        this.view = view;
        view.setController(this);
        loadInitialData();
    }

    public AdminController(AdminModal model, Login login) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void loadInitialData() {
        loadPembayaranList();
        loadConsoleList();
        loadDiskonList();
        loadBerlanggananList();
    }

    // ===== PEMBAYARAN =====
    public void loadPembayaranList() {
        ArrayList<Pembayaran> pembayaranList = model.getAllPembayaran();
        //view.showPembayaranList(pembayaranList); //jika perlu
    }

    public void addPembayaran(int fk_admin, int fk_console, String KTP, String nama_pelanggan,
                              int lama_peminjaman, String kodeDiskon) {

        // Step 1: Ambil data console berdasarkan ID
        Console console = model.getConsoleById(fk_console);
        if (console == null) {
            view.showMessage("Console tidak ditemukan.");
            return;
        }else if(console.stock == 0){
            view.showMessage("Console Kehabisan Stock");
            return;
        }

        int hargaDasar = console.harga;
        int totalHarga = hargaDasar * lama_peminjaman;

        // Step 2: Cek diskon dari kode diskon jika ada
        if (kodeDiskon != null && !kodeDiskon.isEmpty()) {
            ArrayList<Diskon> diskons = model.getAllDiskon();
            for (Diskon d : diskons) {
                if (d.Kode_unik.equalsIgnoreCase(kodeDiskon)) {
                    int potongan = (totalHarga * d.diskon) / 100;
                    totalHarga -= potongan;
                    break;
                }
            }
        }

        // Step 3: Cek apakah pelanggan berlangganan
        ArrayList<Berlangganan> langganans = model.readAllBerlangganan();
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
        boolean success = model.addTransaksi(fk_admin, fk_console, KTP, nama_pelanggan, lama_peminjaman, totalHarga, "Belum Dikembalikan");
        if (success) {
            view.showMessage("Pembayaran berhasil ditambahkan!");
            loadPembayaranList();
        } else {
            view.showMessage("Gagal menambahkan pembayaran.");
        }
    }

    public void updatePembayaranStatus(int id, Date tanggal) {
        if (model.updatePembayaran(id, tanggal)) {
            view.showMessage("Status pembayaran berhasil diperbarui!");
            loadPembayaranList();
        } else {
            view.showMessage("Gagal memperbarui status pembayaran.");
        }
    }

    // ===== CONSOLE =====
    public void loadConsoleList() { //jika perlu untuk ambil data semua console
        ArrayList<Console> consoleList = model.getAllConsoles();
    }

    public void addConsole(String nama, String deskripsi, int stock, int harga) {
        if (model.addConsole(nama, deskripsi, stock, harga)) {
            view.showMessage("Console berhasil ditambahkan!");
            loadConsoleList();
        } else {
            view.showMessage("Gagal menambahkan console.");
        }
    }

    public void updateConsole(int id, String nama, String deskripsi, int stock, int harga) {
        if (model.updateConsole(id, stock)) {
            view.showMessage("Console berhasil diperbarui!");
            loadConsoleList();
        } else {
            view.showMessage("Gagal memperbarui console.");
        }
    }
    /* jika perlu
    public void deleteConsole(int id) {
        if (model.deleteConsole(id)) {
            view.showMessage("Console berhasil dihapus!");
            loadConsoleList();
        } else {
            view.showMessage("Gagal menghapus console.");
        }
    }*/

    // ===== DISKON =====
    public void loadDiskonList() {
        ArrayList<Diskon> diskonList = model.getAllDiskon(); // jika perlu
        //view.showDiskonList(diskonList);
    }

    // ===== BERLANGGANAN =====
    public void loadBerlanggananList() {
        ArrayList<Berlangganan> berlanggananList = model.readAllBerlangganan();
        view.showBerlanggananList(berlanggananList);
    }

    public void updateStatusBerlangganan(String KTP, String newStatus) {
        if (model.updateStatusBerlangganan(KTP, newStatus)) {
            view.showMessage("Status berlangganan berhasil diperbarui!");
            loadBerlanggananList();
        } else {
            view.showMessage("Gagal memperbarui status berlangganan.");
        }
    }
}
