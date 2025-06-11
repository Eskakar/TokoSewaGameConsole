/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import model.AdminModel;
import model.BerlanggananDAO;
import model.BerlanggananModel;
import view.AddSubscriptionForm;
import view.EditSubscriptionForm;

/**
 *
 * @author ASUS
 */
public class BerlanggananController {
    private BerlanggananDAO subsDAO;
    private BerlanggananModel subsModel;
    private AdminModel currentAdmin;
    private AddSubscriptionForm viewAddSubs;
    private EditSubscriptionForm editSubsView;
    public BerlanggananController(AdminModel admModel){
        this.currentAdmin = admModel;
        subsDAO = new BerlanggananDAO();
    }
    
    public ArrayList loadBerlanggananList() {
       return subsDAO.getAllSubscriptions();
    }
    public void addSubs(BerlanggananModel berModel){
        boolean menambahkan = subsDAO.updateSubscription(berModel);
        if(menambahkan == false){
            viewAddSubs.showMessage("Gagal Menambahkan Berlangganan");
        }else{
            viewAddSubs.showMessage("Berhasil Menambahkan Berlangganan");
        }
    }
    public void updateSubscription(String KTP, String newStatus) {
        if(currentAdmin == null){
            editSubsView.showMessage("login dulu");
        }else if (subsDAO.updateStatusByKTP(KTP, newStatus)) {
            editSubsView.showMessage("Status berlangganan berhasil diperbarui!");
            loadBerlanggananList();
        } else {
            editSubsView.showMessage("Gagal memperbarui status berlangganan.");
        }
    }
    public void updateSubscription(BerlanggananModel berlangganan) {       
        if(currentAdmin == null){
            editSubsView.showMessage("login dulu");
        }else if (subsDAO.updateSubscription(berlangganan)) {
            editSubsView.showMessage("Status berlangganan berhasil diperbarui!");
            loadBerlanggananList();
        } else {
            editSubsView.showMessage("Gagal memperbarui status berlangganan.");
        }
    }
    public BerlanggananModel getSubsByKTP(String ktp){
        if(currentAdmin == null){
            editSubsView.showMessage("login dulu");
            return null;
        }else if (ktp != null) {
            return subsDAO.getSubscriptionByKTP(ktp);
        } else {
            editSubsView.showMessage("tidak terdapat data");
            return null;
        }
    }
    public void setAdminModel(AdminModel model){
        this.currentAdmin = model;
    }
}
