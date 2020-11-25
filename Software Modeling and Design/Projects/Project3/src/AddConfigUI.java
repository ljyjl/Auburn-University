import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddConfigUI {

    public JFrame view;

    public JButton btnAdd = new JButton("Select");
    public JButton btnCancel = new JButton("Cancel");

    public JTextField txtCustomerID = new JTextField(20);


    public AddConfigUI()   {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Choose Configuration");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        String[] labels = {"Configuration "};

        //First line
        JPanel line1 = new JPanel(new FlowLayout());  //Panel
        line1.add(new JLabel("Configuration "));    //Label
        line1.add(txtCustomerID);                     //TextField
        view.getContentPane().add(line1);             //JFrame.getContentPane().add(JPanel object);

        JPanel panelButtons = new JPanel(new FlowLayout());  //Panel
        panelButtons.add(btnAdd);                            //JPanel object.add(JButton object)
        panelButtons.add(btnCancel);                         //JPanel object.add(JButton object)
        view.getContentPane().add(panelButtons);             //JFrame.getContentPane().add(JPanel object)

        btnAdd.addActionListener(new AddConfigUI.AddButtonListener());

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
            /*CustomerModel customer = new CustomerModel();

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
            }*/
        }
    }

}
