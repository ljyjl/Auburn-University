import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.ArrayList;

public class SummaryPurchaseUI {

    public JFrame view;
    public JTable purchaseTable;

    public List<CustomerModel> users = null;
    private List<PurchaseListModel> purchaseList = new ArrayList<PurchaseListModel>();

    public SummaryPurchaseUI(List<CustomerModel> uL) {
        this.users = uL;

        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("View Purchase History - Manager View");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("All purchases history ");

        title.setFont (title.getFont().deriveFont (24.0f));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        view.getContentPane().add(title);

        PurchaseListModel list = null;
        for (CustomerModel custo: uL)
            purchaseList.add(StoreManager.getInstance().getDataAdapter().loadPurchaseHistory(custo.mCustomerID));

        DefaultTableModel tableData = new DefaultTableModel();

        tableData.addColumn("PurchaseID");
        tableData.addColumn("ProductID");
        tableData.addColumn("Product Name");
        tableData.addColumn("Total");

        for (PurchaseListModel pur: purchaseList) {
            for (PurchaseModel purchase : pur.purchases) {
                Object[] row = new Object[tableData.getColumnCount()];
                row[0] = purchase.mPurchaseID;
                row[1] = purchase.mProductID;
                ProductModel product = StoreManager.getInstance().getDataAdapter().loadProduct(purchase.mProductID);
                row[2] = product.mName;
                row[3] = purchase.mTotal;
                tableData.addRow(row);
            }
        }

        purchaseTable = new JTable(tableData);

        JScrollPane scrollableList = new JScrollPane(purchaseTable);

        view.getContentPane().add(scrollableList);
    }

    public void run() {
        view.setVisible(true);
    }
}