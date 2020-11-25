import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CashierUI {
    public JFrame view;

    public JButton btnAddCustomer= new JButton("Add New Customer");
    public JButton btnManageCustomer = new JButton("Manage Existing Customer");
    public JButton btnAddPurchase = new JButton("Add New Purchase");

    public CashierUI() {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setTitle("Store Management System - Cashier View");
        view.setSize(400, 300);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("Store Management System");

        title.setFont (title.getFont ().deriveFont (24.0f));
        view.getContentPane().add(title);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnAddCustomer);
        panelButtons.add(btnManageCustomer);
        panelButtons.add(btnAddPurchase);

        view.getContentPane().add(panelButtons);


        btnAddCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AddCustomerUI ac = new AddCustomerUI();
                ac.run();
            }
        });

        btnManageCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ManageCustomerUI mc = new ManageCustomerUI();
                mc.run();
            }
        });

        btnAddPurchase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AddPurchaseUI ap = new AddPurchaseUI();
                ap.run();
            }
        });
    }
}