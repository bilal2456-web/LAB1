/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.enhancedbillingsystem;

/**
 *
 * @author CW
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public abstract class EnhancedBillingSystem extends JFrame implements ActionListener {
    private JTextField itemField, priceField, quantityField, discountField, totalField, gstField, deliveryDateField;
    private JButton calculateButton, clearButton, addItemButton;
    private JTextArea itemListArea;
    private final double GST_RATE = 0.52; // GST rate as a constant
    private List<String> itemList;
    private List<Double> priceList;

    public EnhancedBillingSystem() {
        setTitle("Enhanced Billing System");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        itemList = new ArrayList<>();
        priceList = new ArrayList<>();

        JLabel itemLabel = new JLabel("Item:");
        itemLabel.setBounds(30, 30, 80, 20);
        add(itemLabel);

        itemField = new JTextField();
        itemField.setBounds(110, 30, 150, 20);
        add(itemField);

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBounds(30, 60, 80, 20);
        add(priceLabel);

        priceField = new JTextField();
        priceField.setBounds(110, 60, 150, 20);
        add(priceField);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setBounds(30, 90, 80, 20);
        add(quantityLabel);

        quantityField = new JTextField();
        quantityField.setBounds(110, 90, 150, 20);
        add(quantityField);

        addItemButton = new JButton("Add Item");
        addItemButton.setBounds(30, 120, 100, 30);
        add(addItemButton);
        addItemButton.addActionListener(this);

        JLabel discountLabel = new JLabel("Discount (%):");
        discountLabel.setBounds(30, 160, 100, 20);
        add(discountLabel);

        discountField = new JTextField();
        discountField.setBounds(130, 160, 70, 20);
        add(discountField);

        calculateButton = new JButton("Calculate Total");
        calculateButton.setBounds(30, 190, 150, 30);
        add(calculateButton);
        calculateButton.addActionListener(this);

        clearButton = new JButton("Clear");
        clearButton.setBounds(190, 190, 80, 30);
        add(clearButton);
        clearButton.addActionListener(this);

        JLabel itemListLabel = new JLabel("Items List:");
        itemListLabel.setBounds(300, 30, 80, 20);
        add(itemListLabel);

        itemListArea = new JTextArea();
        itemListArea.setBounds(300, 60, 250, 150);
        itemListArea.setEditable(false);
        add(itemListArea);

        JLabel totalLabel = new JLabel("Total:");
        totalLabel.setBounds(300, 230, 80, 20);
        add(totalLabel);

        totalField = new JTextField();
        totalField.setBounds(370, 230, 100, 20);
        totalField.setEditable(false);
        add(totalField);

        JLabel gstLabel = new JLabel("GST (18%): ");

        gstLabel.setBounds(300, 260, 80, 20);
        add(gstLabel);

        gstField = new JTextField();
        gstField.setBounds(370, 260, 100, 20);
        gstField.setEditable(false);
        add(gstField);

        JLabel deliveryDateLabel = new JLabel("Delivery Date (yyyy-MM-dd):");
        deliveryDateLabel.setBounds(30, 230, 180, 20);
        add(deliveryDateLabel);

        deliveryDateField = new JTextField();
        deliveryDateField.setBounds(220, 230, 120, 20);
        add(deliveryDateField);

        setVisible(true);
        
        // Set a custom background color
        //getContentPane().setBackground(new Color(255, 255, 200)); // Example: light yellow background
        
        // Set background using an image (replace "background.jpg" with your image file)
         ImageIcon backgroundImage = new ImageIcon(getClass().getResource("CW\\Pictures\\New folder\\billing system.jpg\""));
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        add(backgroundLabel);

        setLayout(null); // Make sure to set layout to null for absolute positioning
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addItemButton) {
            addItemToList();
        } else if (e.getSource() == calculateButton) {
            calculateTotal();
        } else if (e.getSource() == clearButton) {
            clearFields();
        }
    }

   private void addItemToList() {
    String item = itemField.getText();
    double price = Double.parseDouble(priceField.getText());
    int quantity = 1; // default quantity is 1

    try {
        quantity = Integer.parseInt(quantityField.getText());
    } catch (NumberFormatException ignored) {
    }

    double totalItemPrice = price * quantity;

    itemList.add(item);
    priceList.add(totalItemPrice);

    itemListArea.append(item + " - Rs" + price + " x " + quantity + " = Rs" + totalItemPrice + "\n");

    itemField.setText("");
    priceField.setText("");
    quantityField.setText("");
}


    private void calculateTotal() {
    double total = 0;
    for (Double price : priceList) {
        total += price;
    }

    double discount = 0;
    try {
        discount = Double.parseDouble(discountField.getText());
    } catch (NumberFormatException ignored) {
    }

    // Calculate discounted total
    double discountedTotal = total - (total * (discount / 100));

    double gstAmount = discountedTotal * GST_RATE;
    totalField.setText(String.valueOf(discountedTotal + gstAmount)); // Display total after GST

    DecimalFormat df = new DecimalFormat("#.##");
    gstField.setText(df.format(gstAmount));

    String deliveryDateString = deliveryDateField.getText();
    LocalDate deliveryDate = null;

    try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        deliveryDate = LocalDate.parse(deliveryDateString, formatter);
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Please enter a valid delivery date in yyyy-MM-dd format.");
    }

    if (deliveryDate != null) {
        JOptionPane.showMessageDialog(this, "Total: Rs" + discountedTotal + "\nGST: Rs" + gstAmount +
                "\nDelivery Date: " + deliveryDate);
    }


    }

    private void clearFields() {
        itemField.setText("");
        priceField.setText("");
        quantityField.setText("");
        discountField.setText("");
        totalField.setText("");
        gstField.setText("");
        deliveryDateField.setText("");
        itemListArea.setText("");



        itemList.clear();
        priceList.clear();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EnhancedBillingSystem() {});

    }
}




