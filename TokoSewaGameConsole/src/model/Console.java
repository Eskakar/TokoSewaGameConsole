/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ASUS
 */
public  class Console {
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
