/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.view;

import java.awt.event.ActionEvent;
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
 * GUI for updating the Json file which holds configuration and products to be
 * displayed in the Vending Machine app. Some of the boilerplate Swing code was
 * generated using the designer in the Apache Netbeans IDE.
 * 
 * @author Johnnie Meredith
 * @version 1.0
 */
public class ProductUpdateGUI extends javax.swing.JFrame {

    private static Logger logger = LogManager.getLogger(ProductUpdateGUI.class);
    private static final String PATH = "vendingmachine\\src\\main\\resources\\input.json";

    /**
     * Creates new form ProductUpdateGUI
     */
    public ProductUpdateGUI() {
        initComponents();
    }

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

    /**
     * Reads the TextArea and writes it to the location of the Json file or copies a
     * new Json file to use into the resources directory. WARNING: There is no
     * verification of valid Json.
     * 
     * @param evt Unused: here for overriding purposes.
     */
    private void updateButtonActionPerformed(ActionEvent evt) {
        if (textRadioButton.isSelected()) {
            try {
                writeTextToFile();
            } catch (IOException ioe) {
                logger.fatal(ioe);
            }

        } else if (fileRadioButton.isSelected()) {
            try {
                writeFileToFile();
            } catch (Exception ioe) {
                logger.fatal(ioe);
            }

        }
    }

    /**
     * Writes the text from the GUI text area to the json file used for config and
     * product population.
     * 
     * @throws IOException If the file cannot be opened or written to.
     */
    public void writeTextToFile() throws IOException {
        BufferedWriter output = null;
        try {
            File file = new File(PATH);
            FileWriter fileWriter = new FileWriter(file, false);
            output = new BufferedWriter(fileWriter);
            output.write(jsonTextArea.getText());
        } catch (IOException ioe) {
            logger.fatal(ioe);
        } finally {
            if (output != null) {
                output.close();
            }
        }
        logger.debug("New TEXT written to input.json");
        restartVendingMachine();

    }

    /**
     * Restarts the controller and view so that changes can populate.
     */
    public void restartVendingMachine() {
        logger.debug("RESTARTING VENDING MACHINE");
        VendingMachine vendingMachine = new VendingMachine(PATH);
        VendingMachineGUI gui = new VendingMachineGUI();
        gui.setVendingMachine(vendingMachine);
        gui.run();
        this.dispose();
    }

    /**
     * Writes the file at the given path over the original input.json.
     * 
     * @throws IOException
     */
    public void writeFileToFile() throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            File source = new File(fileTextField.getText());
            inputStream = new FileInputStream(source);
            outputStream = new FileOutputStream(PATH);
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
        logger.debug("New File written to input.json");
        restartVendingMachine();
    }

    // Variables declaration
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
