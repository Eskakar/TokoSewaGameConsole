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
// Inner class untuk data Admin
public  class AdminModel {
    private int id;
    private String nama;
    private String sandi;

    public AdminModel(int id, String nama, String sandi) {
        this.id = id;
        this.nama = nama;
        this.sandi = sandi;
    }

        // Getter
        public int getId() {
            return id;
        }

        public String getNama() {
            return nama;
        }

        public String getSandi() {
            return sandi;
        }

        // Setter
        public void setId(int id) {
            this.id = id;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public void setSandi(String sandi) {
            this.sandi = sandi;
        }

        // Override toString()
        @Override
        public String toString() {
            return "Admin{" +
                   "id=" + id +
                   ", nama='" + nama + '\'' +
                   ", sandi='" + sandi + '\'' +
                   '}';
        }
}


