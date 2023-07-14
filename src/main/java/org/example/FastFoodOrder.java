package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.example.Connection.executeQuery;

public class FastFoodOrder extends JFrame {
    private JPanel rootPanel;
    private JPanel foodMenuPanel;
    private JLabel titleLabel;
    private JLabel imageLabel;
    private JPanel tablePanel;
    private JButton deleteButton;
    private JComboBox tableComboBox;
    private JButton orderButton;
    private JLabel tableLabel;
    private JTable orderTable;
    private JButton beefCheeseBurgerBtn;
    private JButton shrimpCheeseBurgerButton;
    private JButton shrimpBallCheeseBurgerButton;
    private JButton colaChickenBallButton;
    private JButton chickenCheeseBurgerButton;
    private JButton friedChickenButton;
    private JButton orangeJuiceButton;
    private JButton liptonButton;
    private JButton cokeButton;
    private JButton pepsiButton;
    private JButton a7UpButton;
    private JButton coffeeButton;
    private JButton fishCheeseBurgerButton;
    private JButton tenderRiceChickenButton;
    private JButton friedPotatoButton;
    static FastFoodOrder order = new FastFoodOrder();
    DefaultTableModel tableModel;

    public FastFoodOrder() {
        refreshTable();

        MouseAdapter listener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

            }
        };
        beefCheeseBurgerBtn.addMouseListener(listener);
        shrimpCheeseBurgerButton.addMouseListener(listener);
        shrimpBallCheeseBurgerButton.addMouseListener(listener);
        colaChickenBallButton.addMouseListener(listener);
        chickenCheeseBurgerButton.addMouseListener(listener);
        friedChickenButton.addMouseListener(listener);
        pepsiButton.addMouseListener(listener);
        a7UpButton.addMouseListener(listener);
        coffeeButton.addMouseListener(listener);
        fishCheeseBurgerButton.addMouseListener(listener);
        tenderRiceChickenButton.addMouseListener(listener);
        friedPotatoButton.addMouseListener(listener);
        cokeButton.addMouseListener(listener);
        liptonButton.addMouseListener(listener);
        orangeJuiceButton.addMouseListener(listener);
        orderTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    super.mouseClicked(e);
                    getTableNumber();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        orderButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                insertItem();
            }
        });
    }

    public static void main(String[] args) {
        createFrame();
    }

    private static void createFrame() {
        order.setContentPane(order.rootPanel);
        order.pack();
        order.setLocationRelativeTo(null);
        order.setVisible(true);
        order.foodMenuPanel.setBorder(BorderFactory.createTitledBorder("Food Menu"));
        order.setDefaultCloseOperation(order.EXIT_ON_CLOSE);

    }

    private void refreshTable() {
        try {
            tableModel = new DefaultTableModel();
            String getBillsSQL = "SELECT * FROM Bill";
            ResultSet resultSet = executeQuery(getBillsSQL);
            String no, orderItem, quantity;
            String[] columns = {"#", "Order Item", "Quantity"};
            tableModel.setColumnIdentifiers(columns);
            orderTable.setModel(tableModel);
            while (resultSet.next()) {
                no = resultSet.getString("tableNo");
                orderItem = resultSet.getString("orderItem");
                quantity = resultSet.getString("quantity");
                tableModel.addRow(new Object[]{no, orderItem, quantity});
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<JButton> btnGroup() {
        List<JButton> jButtons = new ArrayList<>();
        jButtons.add(beefCheeseBurgerBtn);
        jButtons.add(shrimpCheeseBurgerButton);
        jButtons.add(shrimpBallCheeseBurgerButton);
        jButtons.add(colaChickenBallButton);
        jButtons.add(chickenCheeseBurgerButton);
        jButtons.add(friedChickenButton);
        jButtons.add(pepsiButton);
        jButtons.add(a7UpButton);
        jButtons.add(coffeeButton);
        jButtons.add(fishCheeseBurgerButton);
        jButtons.add(tenderRiceChickenButton);
        jButtons.add(cokeButton);
        jButtons.add(liptonButton);
        jButtons.add(orangeJuiceButton);
        return jButtons;
    }

    private void getTableNumber() throws SQLException {
        tableComboBox.removeAllItems();
        var getRow = orderTable.getSelectedRow();
        String getColumnsNo = "SELECT tableNo FROM Bill WHERE tableNo = " + tableModel.getValueAt(getRow, 0);
        ResultSet resultSet = executeQuery(getColumnsNo);
        while (resultSet.next()) {
            String tableNo = resultSet.getString("tableNo");
            tableComboBox.addItem(tableNo);
        }
    }

    private void insertItem() {
        try {
            var tableNo = tableComboBox.getSelectedItem();
            var quantity = orderTable.getValueAt(orderTable.getSelectedRow(), 2);
            for (int i = 0; i < btnGroup().size(); i++) {
                if (btnGroup().get(i).isSelected()) {
                    var orderItem = btnGroup().get(i).getText();
                    String addData = "INSERT Bill VALUES("+ tableNo + ", " + orderItem + ", " + quantity + ")";
                    ResultSet resultSet = executeQuery(addData);
                }
            }
        } catch (SQLException e) {
        throw new RuntimeException(e);
        }
    }
}
