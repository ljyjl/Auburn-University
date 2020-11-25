import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUI {

    public UserModel user = null;

    public JFrame view;

    public JButton btnSetupConfig = new JButton("Setup configuration");
    public JButton btnAddUser = new JButton("Add a new user");
    public JButton btnPurchase = new JButton("Add a new purchase");
    public JButton btnManageUser = new JButton("Manage a new user");
    public JButton btnDeleteUser = new JButton("Delete an existing user");

    public AdminUI(UserModel user) {

        this.user = user;

        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setTitle("Store Management System - Customer View");
        view.setSize(1000, 600);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("Store Management System");

        title.setFont (title.getFont ().deriveFont (24.0f));
        view.getContentPane().add(title);

        JPanel panelUser = new JPanel(new FlowLayout());
        panelUser.add(new JLabel("Fullname: " + user.mFullname));
        panelUser.add(new JLabel("CustomerID: " + user.mCustomerID));

        view.getContentPane().add(panelUser);

        JPanel panelButtons = new JPanel(new FlowLayout());
        //panelButtons.add(btnSetupConfig);
        panelButtons.add(btnAddUser);
        panelButtons.add(btnManageUser);
        panelButtons.add(btnPurchase);
        panelButtons.add(btnDeleteUser);

        view.getContentPane().add(panelButtons);

        /*btnSetupConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AddConfigUI ui = new AddConfigUI();
                ui.run();
            }
        });*/

        btnAddUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AddUserUI ui = new AddUserUI();
                ui.run();
            }
        });

        btnPurchase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AddPurchaseUI ui = new AddPurchaseUI();
                ui.run();
            }
        });


        btnManageUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ManageUserUI ui = new ManageUserUI();
                ui.run();
            }
        });

        btnDeleteUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ManageUserUI ui = new ManageUserUI();
                ui.run();
            }
        } );

    }
}