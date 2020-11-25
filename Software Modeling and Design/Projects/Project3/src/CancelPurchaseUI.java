import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CancelPurchaseUI {

    public JFrame view;

    public JButton btnAdd = new JButton("Cancel Purchase");
    public JButton btnCancel = new JButton("Cancel");

    public JTextField txtCustomerID = new JTextField(20);


    public CancelPurchaseUI()   {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Cancel Purchase");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        String[] labels = {"PurchaseID "};

        //First line
        JPanel line1 = new JPanel(new FlowLayout());  //Panel
        line1.add(new JLabel("PurchaseID "));    //Label
        line1.add(txtCustomerID);                     //TextField
        view.getContentPane().add(line1);             //JFrame.getContentPane().add(JPanel object);

        JPanel panelButtons = new JPanel(new FlowLayout());  //Panel
        panelButtons.add(btnAdd);                            //JPanel object.add(JButton object)
        panelButtons.add(btnCancel);                         //JPanel object.add(JButton object)
        view.getContentPane().add(panelButtons);             //JFrame.getContentPane().add(JPanel object)

        btnAdd.addActionListener(new CancelPurchaseUI.AddButtonListener());

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
        }
    }

}
