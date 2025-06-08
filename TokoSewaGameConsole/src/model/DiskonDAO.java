/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class DiskonDAO {
    //1. read diskon
    public ArrayList<DiskonModel> getAllDiskon() {
        ArrayList<DiskonModel> diskon = new ArrayList<>();
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM diskon";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                diskon.add(new DiskonModel(
                    rs.getString("kode_unik"),
                    rs.getInt("persen")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return diskon;
    }
}
