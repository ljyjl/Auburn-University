import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Calendar;
import java.util.Scanner;

public class ManagePurchaseUI {
    public JFrame view;

    public JButton btnLoad = new JButton("Load Purchase");
    public JButton btnSave = new JButton("Save Purchase");

    public JTextField txtPurchaseID = new JTextField(10);
    public JTextField txtCustomerID = new JTextField(10);
    public JTextField txtProductID = new JTextField(10);
    public JTextField txtQuantity = new JTextField(10);

    public JLabel labPrice = new JLabel();
    public JLabel labDate = new JLabel();

    public JLabel labCustomerName = new JLabel();
    public JLabel labProductName = new JLabel();

    public JLabel labCost = new JLabel();
    public JLabel labTax = new JLabel();
    public JLabel labTotalCost = new JLabel();

    ProductModel product;
    PurchaseModel purchase;
    CustomerModel customer;
    TXTReceiptBuilder receipt = new TXTReceiptBuilder();


    public ManagePurchaseUI() {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Update Purchase Information");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnLoad);
        panelButtons.add(btnSave);
        view.getContentPane().add(panelButtons);

        JPanel line = new JPanel(new FlowLayout());
        line.add(new JLabel("Customer Name: "));
        line.add(labCustomerName);
        line.add(new JLabel("Product Name: "));
        line.add(labProductName);
        view.getContentPane().add(line);

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("PurchaseID "));
        line1.add(txtPurchaseID);
        view.getContentPane().add(line1);

        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("CustomerID "));
        line2.add(txtCustomerID);
        view.getContentPane().add(line2);

        JPanel line3 = new JPanel(new FlowLayout());
        line3.add(new JLabel("ProductID "));
        line3.add(txtProductID);
        view.getContentPane().add(line3);

        JPanel line4 = new JPanel(new FlowLayout());
        line4.add(new JLabel("Quantity "));
        line4.add(txtQuantity);
        view.getContentPane().add(line4);

        JPanel line5 = new JPanel(new FlowLayout());
        line5 = new JPanel(new FlowLayout());
        line5.add(new JLabel("Cost: $"));
        line5.add(labCost);
        line5.add(new JLabel("Tax: $"));
        line5.add(labTax);
        line5.add(new JLabel("Total Cost: $"));
        line5.add(labTotalCost);
        view.getContentPane().add(line5);

        JPanel line6 = new JPanel(new FlowLayout());
        line6 = new JPanel(new FlowLayout());
        line6.add(new JLabel("Product Price: $"));
        line6.add(labPrice);
        line6.add(new JLabel("Date of Purchase: "));
        line6.add(labDate);
        view.getContentPane().add(line6);

        btnLoad.addActionListener(new ManagePurchaseUI.LoadButtonListerner());
        btnSave.addActionListener(new ManagePurchaseUI.SaveButtonListener());
    }

    public void run() {
        purchase = new PurchaseModel();
        purchase.mDate = Calendar.getInstance().getTime().toString();
        labDate.setText(purchase.mDate);

        view.setVisible(true);
    }

    class LoadButtonListerner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            PurchaseModel purchase = new PurchaseModel();
            Gson gson = new Gson();
            String id = txtPurchaseID.getText();

            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "PurchaseID cannot be null!");
                return;
            }

            try {
                purchase.mPurchaseID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "PurchaseID is invalid!");
                return;
            }

            // do client/server

            try {
                Socket link = new Socket("localhost", 1000);
                Scanner input = new Scanner(link.getInputStream());
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                MessageModel msg = new MessageModel();
                msg.code = MessageModel.GET_PURCHASE;
                msg.data = Integer.toString(purchase.mPurchaseID);
                output.println(gson.toJson(msg)); // send to Server

                msg = gson.fromJson(input.nextLine(), MessageModel.class);

                if (msg.code == MessageModel.OPERATION_FAILED) {
                    JOptionPane.showMessageDialog(null, "Purchase NOT exists!");
                }
                else {
                    purchase = gson.fromJson(msg.data, PurchaseModel.class);
                    txtCustomerID.setText(Integer.toString(purchase.mCustomerID));
                    txtProductID.setText(Integer.toString(purchase.mProductID));
                    txtQuantity.setText(Double.toString(purchase.mQuantity));

                    labPrice.setText(Double.toString(purchase.mPrice));
                    labDate.setText(purchase.mDate);

                    product = StoreManager.getInstance().getDataAdapter().loadProduct(purchase.mProductID);
                    customer = StoreManager.getInstance().getDataAdapter().loadCustomer(purchase.mCustomerID);
                    labCustomerName.setText(product.mName);
                    labProductName.setText(customer.mName);

                    labCost.setText(Double.toString(purchase.mCost));
                    labTax.setText(Double.toString(purchase.mTax));
                    labTotalCost.setText(Double.toString(purchase.mTotal));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            PurchaseModel purchase = new PurchaseModel();
            Gson gson = new Gson();

            String id = txtPurchaseID.getText();
            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "PurchaseID cannot be null!");
                return;
            }

            try {
                purchase.mPurchaseID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "PurchaseID is invalid!");
                return;
            }

            String idC = txtCustomerID.getText();
            if (idC.length() == 0) {
                JOptionPane.showMessageDialog(null, "CustomerID cannot be null!");
                return;
            }

            try {
                purchase.mCustomerID = Integer.parseInt(idC);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "CustomerID is invalid!");
                return;
            }

            String idP = txtProductID.getText();
            if (idP.length() == 0) {
                JOptionPane.showMessageDialog(null, "ProductID cannot be null!");
                return;
            }

            try {
                purchase.mProductID = Integer.parseInt(idP);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "ProductID is invalid!");
                return;
            }

            String quant = txtQuantity.getText();
            try {
                purchase.mQuantity = Double.parseDouble(quant);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Quantity is invalid!");
                return;
            }

            String price = labPrice.getText();
            try {
                purchase.mPrice = Double.parseDouble(price);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Price is invalid!");
                return;
            }

            String cost = labCost.getText();
            try {
                purchase.mCost = Double.parseDouble(cost);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Cost is invalid!");
                return;
            }

            String tax = labTax.getText();
            try {
                purchase.mCost = Double.parseDouble(tax);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Tax is invalid!");
                return;
            }

            String total = labTotalCost.getText();
            try {
                purchase.mTotal = Double.parseDouble(total);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Total Cost is invalid!");
                return;
            }

            String date = labDate.getText();
            try {
                purchase.mDate = date;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Date Cost is invalid!");
                return;
            }

            // all product infor is ready! Send to Server!

            try {
                Socket link = new Socket("localhost", 1000);
                Scanner input = new Scanner(link.getInputStream());
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                MessageModel msg = new MessageModel();
                msg.code = MessageModel.PUT_PURCHASE;
                msg.data = gson.toJson(purchase);
                output.println(gson.toJson(msg)); // send to Server

                msg = gson.fromJson(input.nextLine(), MessageModel.class); // receive from Server

                if (msg.code == MessageModel.OPERATION_FAILED) {
                    JOptionPane.showMessageDialog(null, "Purchase is NOT saved successfully!");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Purchase is SAVED successfully!");
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
