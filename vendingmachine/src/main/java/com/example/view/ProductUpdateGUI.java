/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.view;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.example.controller.VendingMachine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Johnnie5Dubv
 */
public class ProductUpdateGUI extends javax.swing.JFrame {

    private static Logger logger = LogManager.getLogger(ProductUpdateGUI.class);

    /**
     * Creates new form ProductUpdateGUI
     */
    public ProductUpdateGUI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        textRadioButton = new javax.swing.JRadioButton();
        fileRadioButton = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jsonTextArea = new javax.swing.JTextArea();
        fileTextField = new javax.swing.JTextField();
        updateButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        buttonGroup1.add(textRadioButton);
        textRadioButton.setText("Text Json");

        buttonGroup1.add(fileRadioButton);
        fileRadioButton.setText("File Json");

        jsonTextArea.setColumns(20);
        jsonTextArea.setRows(20);
        jsonTextArea.setWrapStyleWord(true);
        jScrollPane1.setViewportView(jsonTextArea);

        fileTextField.setText("Enter Path to new input.json file");

        updateButton.setText("Submit");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("You may enter Json here or set a new file path below");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
                .createSequentialGroup().addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup().addComponent(textRadioButton).addGap(65, 65, 65)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 267,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup().addComponent(fileRadioButton)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(fileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 391,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(updateButton))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 580,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25)));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(textRadioButton).addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(fileRadioButton)
                                .addComponent(fileTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(updateButton))
                        .addGap(25, 25, 25)));

        pack();
    }// </editor-fold>

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        if (textRadioButton.isSelected()) {
            try {
                writeTextToFile();
            } catch (IOException ioe) {
                // TODO: handle exception
                logger.fatal(ioe);
            }

        } else if (fileRadioButton.isSelected()) {
            try {
                writeFileToFile();
            } catch (Exception ioe) {
                // TODO: handle exception
                logger.fatal(ioe);
            }

        }
    }

    public void writeTextToFile() throws IOException {
        BufferedWriter output = null;
        try {
            File file = new File("vendingmachine\\src\\main\\resources\\input.json");
            FileWriter fileWriter = new FileWriter(file, false);
            output = new BufferedWriter(fileWriter);
            output.write(jsonTextArea.getText());
        } catch (IOException ioe) {
            // TODO: handle exception
            logger.fatal(ioe);
        } finally {
            if (output != null) {
                output.close();
            }
        }
        restartVendingMachine();

    }

    public void restartVendingMachine() {
        String path = "vendingmachine\\src\\main\\resources\\input.json";
        VendingMachine vendingMachine = new VendingMachine(path);
        VendingMachineGUI gui = new VendingMachineGUI();
        gui.setVendingMachine(vendingMachine);
        gui.run();
        this.dispose();
    }

    public void writeFileToFile() throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            File source = new File(fileTextField.getText());
            File destination = new File("vendingmachine\\src\\main\\resources\\input.json");
            inputStream = new FileInputStream(source);
            outputStream = new FileOutputStream(destination);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

        } catch (IOException ioe) {
            logger.fatal(ioe);
            jLabel1.setText("Error reading old file or writing new file!");
        } finally {
            inputStream.close();
            outputStream.close();
        }
        restartVendingMachine();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ProductUpdateGUI.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProductUpdateGUI.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProductUpdateGUI.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProductUpdateGUI.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        }
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // new ProductUpdateGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton updateButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JRadioButton textRadioButton;
    private javax.swing.JRadioButton fileRadioButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jsonTextArea;
    private javax.swing.JTextField fileTextField;
    // End of variables declaration
}