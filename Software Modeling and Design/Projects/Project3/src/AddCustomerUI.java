import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCustomerUI {

    public JFrame view;

    public JButton btnAdd = new JButton("Add");
    public JButton btnCancel = new JButton("Cancel");

    public JTextField txtCustomerID = new JTextField(20);
    public JTextField txtName = new JTextField(20);
    public JTextField txtPhone = new JTextField(20);
    public JTextField txtAddress = new JTextField(20);


    public AddCustomerUI()   {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Add User");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        String[] labels = {"Customer ID ", "Name ", "Phone ", "Address "};

        //First line
        JPanel line1 = new JPanel(new FlowLayout());  //Panel
        line1.add(new JLabel("Customer ID "));    //Label
        line1.add(txtCustomerID);                     //TextField
        view.getContentPane().add(line1);             //JFrame.getContentPane().add(JPanel object);

        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("Name "));
        line2.add(txtName);
        view.getContentPane().add(line2);

        JPanel line3 = new JPanel(new FlowLayout());
        line3.add(new JLabel("Phone "));
        line3.add(txtPhone);
        view.getContentPane().add(line3);

        JPanel line4 = new JPanel(new FlowLayout());
        line4.add(new JLabel("Address "));
        line4.add(txtAddress);
        view.getContentPane().add(line4);

        JPanel panelButtons = new JPanel(new FlowLayout());  //Panel
        panelButtons.add(btnAdd);                            //JPanel object.add(JButton object)
        panelButtons.add(btnCancel);                         //JPanel object.add(JButton object)
        view.getContentPane().add(panelButtons);             //JFrame.getContentPane().add(JPanel object)

        btnAdd.addActionListener(new AddCustomerUI.AddButtonListener());

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                view.dispose();
            }
        });

    }

    public void run() {
        view.setVisible(true);
    }

    class AddButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            CustomerModel customer = new CustomerModel();

            String id = txtCustomerID.getText();

            //Check if the id is not empty
            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "CustomerID cannot be null!");
                return;
            }

            //Check if the customer id is a valid input
            try {
                customer.mCustomerID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "CustomerID is invalid!");
                return;
            }

            String name = txtName.getText();
            if (name.length() == 0) {
                JOptionPane.showMessageDialog(null, "Customer name cannot be empty!");
                return;
            }

            customer.mName = name;

            String phone = txtPhone.getText();
            if (phone.length() == 0) {
                JOptionPane.showMessageDialog(null, "Customer phone cannot be empty!");
                return;
            }

            customer.mPhone = phone;

            String address = txtAddress.getText();
            if (address.length() == 0) {
                JOptionPane.showMessageDialog(null, "Customer address cannot be empty!");
                return;
            }

            customer.mAddress = address;

            int res = StoreManager.getInstance().getDataAdapter().saveCustomer(customer);
            if (res == SQLiteDataAdapter.CUSTOMER_SAVE_FAILED)
                JOptionPane.showMessageDialog(null, "Customer NOT added successfully! Duplicate customer ID!");
            else
                JOptionPane.showMessageDialog(null, "Customer added successfully!" + customer);
        }
    }

}
