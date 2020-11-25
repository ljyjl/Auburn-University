
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ManagerUI {
    public JFrame view;

    public JButton btnAddProduct = new JButton("Add Products");
    public JButton btnManageProduct = new JButton("Manage Products");
    public JButton btnManageCustomer = new JButton("Manage Customers");
    public JButton btnSummaryPurchase = new JButton("View Purchase History");

    private List<CustomerModel> cL = new ArrayList<CustomerModel>();

    public ManagerUI() {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setTitle("Store Management System - Manager View");
        view.setSize(1000, 600);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("Store Management System");

        title.setFont (title.getFont ().deriveFont (24.0f));
        view.getContentPane().add(title);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnAddProduct);
        panelButtons.add(btnManageProduct);
        panelButtons.add(btnManageCustomer);
        panelButtons.add(btnSummaryPurchase);

        view.getContentPane().add(panelButtons);

        btnAddProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AddProductUI ui = new AddProductUI();
                ui.run();
            }
        });

        btnManageProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ManageProductUI ui = new ManageProductUI();
                ui.run();
            }
        });

        btnManageCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ManageCustomerUI ui = new ManageCustomerUI();
                ui.run();
            }
        });

        btnSummaryPurchase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                for (int i = 1; i <= 4; i++)
                    cL.add(StoreManager.getInstance().getDataAdapter().loadCustomer(i));

                SummaryPurchaseUI ui = new SummaryPurchaseUI(cL);
                ui.run();
            }
        });


    }
}
