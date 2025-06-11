/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import model.AdminModel;
import model.BerlanggananDAO;
import model.BerlanggananModel;
import view.EditSubscriptionForm;

/**
 *
 * @author ASUS
 */
public class BerlanggananController {
    private BerlanggananDAO subsDAO;
    private BerlanggananModel subsModel;
    private AdminModel currentAdmin;
    private EditSubscriptionForm editSubsView;
    public BerlanggananController(AdminModel admModel){
        this.currentAdmin = admModel;
        subsDAO = new BerlanggananDAO();
        //update expired subscription
        
    }
    
    public ArrayList loadBerlanggananList() {
       return subsDAO.getAllSubscriptions();
    }
    public boolean addSubs(String ktp,String nama,int berapaLama){
        return subsDAO.addSubscription(ktp,nama,berapaLama);     
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
    public boolean updateSubscription(BerlanggananModel berlangganan, EditSubscriptionForm editFormView) {       
        if(currentAdmin == null){
            editFormView.showMessage("login dulu");
            return false;
        }else if (subsDAO.updateSubscription(berlangganan)) {
            editFormView.showMessage("Status berlangganan berhasil diperbarui!");
            return true;
        } else {
            editFormView.showMessage("Gagal memperbarui status berlangganan.");
            return false;
        }
    }
    public BerlanggananModel getSubsByKTP(String ktp){
        if(currentAdmin == null){
            editSubsView.showMessage("login dulu");
            return null;
        }else if (ktp != null) {
            return subsDAO.getSubscriptionByKTP(ktp);
        } else {
            return null;
        }
    }
    public boolean deletBerlangganaByKtp(String ktp){
        return subsDAO.deleteSubscription(ktp);
    }
    public void setAdminModel(AdminModel model){
        this.currentAdmin = model;
    }
    public void updateExpiredToday(){
        subsDAO.expireSubscriptionsToday();
    }
}
