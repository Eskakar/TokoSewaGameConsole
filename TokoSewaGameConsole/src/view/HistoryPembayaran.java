/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.AdminController;
import model.PembayaranDAO;
import model.PembayaranModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 *
 * @author Anzio
 */
public class HistoryPembayaran extends javax.swing.JFrame {
    private AdminController adminControl;
    private DefaultTableModel tableModel;
    
    /**
     * Creates new form HistoryPembayaran
     */
    public HistoryPembayaran() {
        initComponents();
        setupTable();    
    }

    /**
     * Setup table columns and model
     */
    private void setupTable() {
        String[] columnNames = {
            "ID", "Admin ID", "KTP", "Nama Pelanggan", 
            "Console ID", "Tanggal Pembayaran", "Lama Peminjaman (Hari)", 
            "Total Harga", "Status", "Tanggal Expired"
        };
        
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        
        jTable1.setModel(tableModel);
        
        // Set column widths for better display
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(80);  // Admin ID
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(100); // KTP
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(150); // Nama Pelanggan
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(80);  // Console ID
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(120); // Tanggal Pembayaran
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(100); // Lama Peminjaman
        jTable1.getColumnModel().getColumn(7).setPreferredWidth(100); // Total Harga
        jTable1.getColumnModel().getColumn(8).setPreferredWidth(120); // Status
        jTable1.getColumnModel().getColumn(9).setPreferredWidth(120); // Tanggal Expired
    }
    
    /**
     * Load data from database to table
     */
    public void loadDataToTable() {
        try {
            // Clear existing data
            tableModel.setRowCount(0);
            
            // Get data from database
            ArrayList<PembayaranModel> pembayaranList = adminControl.loadDataPembayaran();
            
            // Add data to table
            for (PembayaranModel pembayaran : pembayaranList) {
                Object[] row = {
                    pembayaran.getId(),
                    pembayaran.getFk_admin(),
                    pembayaran.getKTP(),
                    pembayaran.getNama_pelanggan(),
                    pembayaran.getFk_console(),
                    pembayaran.getTanggal_pembayaran(),
                    pembayaran.getLama_peminjaman(),
                    "Rp " + String.format("%,d", pembayaran.getTotal_harga()),
                    pembayaran.getStatus_console(),
                    pembayaran.getTanggal_expired()
                };
                tableModel.addRow(row);
            }
            
            if (pembayaranList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tidak ada data pembayaran yang ditemukan.", 
                                            "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saat memuat data: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    /**
     * Get selected pembayaran ID from table
     */
    private int getSelectedPembayaranId() {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin diproses terlebih dahulu!", 
                                        "Peringatan", JOptionPane.WARNING_MESSAGE);
            return -1;
        }
        return (Integer) tableModel.getValueAt(selectedRow, 0); // ID is in column 0
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(600, 500));

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));
        jPanel1.setMaximumSize(new java.awt.Dimension(900, 500));
        jPanel1.setMinimumSize(new java.awt.Dimension(900, 500));
        jPanel1.setPreferredSize(new java.awt.Dimension(900, 500));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, 750, 340));

        jLabel2.setFont(new java.awt.Font("Arial Black", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("History Persewaan");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, -1, -1));

        jButton1.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jButton1.setText("Edit");
        jButton1.setMaximumSize(new java.awt.Dimension(123, 28));
        jButton1.setMinimumSize(new java.awt.Dimension(123, 28));
        jButton1.setOpaque(true);
        jButton1.setPreferredSize(new java.awt.Dimension(123, 28));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 440, -1, -1));

        jButton2.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jButton2.setText("Kembali ke Menu");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 440, -1, -1));

        jButton3.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jButton3.setText("Hapus");
        jButton3.setMaximumSize(new java.awt.Dimension(123, 28));
        jButton3.setMinimumSize(new java.awt.Dimension(123, 28));
        jButton3.setPreferredSize(new java.awt.Dimension(123, 28));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 440, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
     // Edit pembayaran - mark as returned
        int selectedId = getSelectedPembayaranId();
        if (selectedId != -1) {
            //pengecekan apakah sudah dikembalikan
            if(adminControl.checkConsleStatus(selectedId)){
                showMessage("Sudah Dikembalikan");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Apakah Anda yakin ingin menandai console sebagai sudah dikembalikan?", 
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
                    boolean success = adminControl.updatePembayaran(selectedId, currentDate,"Dikembalikan");
                    
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Data berhasil diperbarui!", 
                            "Sukses", JOptionPane.INFORMATION_MESSAGE);
                        loadDataToTable(); // Refresh table
                    } else {
                        JOptionPane.showMessageDialog(this, "Gagal memperbarui data!", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        SubMenuA sm = adminControl.getSubMenuAView();
        sm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
    //hapus history pembayaran 
    
    int selectedId = getSelectedPembayaranId();
        if (selectedId != -1) {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Apakah Anda yakin ingin menghapus data pembayaran ini?", 
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    boolean success = adminControl.deleteHistoryPembayaran(selectedId);
                    
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Data berhasil dihapus!", 
                            "Sukses", JOptionPane.INFORMATION_MESSAGE);
                        loadDataToTable(); // Refresh table
                    } else {
                        JOptionPane.showMessageDialog(this, "Gagal menghapus data!", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HistoryPembayaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HistoryPembayaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HistoryPembayaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HistoryPembayaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HistoryPembayaran().setVisible(true);
            }
        });
    }
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    public void setController(AdminController controller) {
        this.adminControl = controller;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
