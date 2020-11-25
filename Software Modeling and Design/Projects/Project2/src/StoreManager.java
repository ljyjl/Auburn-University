import javax.swing.*;

public class StoreManager {
    public static final String DBMS_SQ_LITE = "Network";
    public static final String DB_FILE = "/Users/jiyeonlee/Desktop/Auburn_University/FALL2019/COMP3700/Activities/store.db";

    IDataAdapter adapter = null;
    private static StoreManager instance = null;

    public static StoreManager getInstance() {
        if (instance == null) {
            instance = new StoreManager("Network", "localhost:1000");
        }
        return instance;
    }

    private StoreManager(String dbms, String dbfile) {
        if (dbms.equals("Oracle"))
            adapter = new OracleDataAdapter();
        else
        if (dbms.equals("SQLite"))
            adapter = new SQLiteDataAdapter();
        else
        if (dbms.equals("Network"))
            adapter = new NetworkDataAdapter();

        adapter.connect(dbfile);
        ProductModel product = adapter.loadProduct(3);
        CustomerModel customer = adapter.loadCustomer(3);
        PurchaseModel purchase = adapter.loadPurchase(3);

        System.out.println("Loaded product: " + product);
        System.out.println("Loaded customer: " + customer);
        System.out.println("Loaded purchase: " + purchase);

    }

    public IDataAdapter getDataAdapter() {
        return adapter;
    }

    public void setDataAdapter(IDataAdapter a) {
        adapter = a;
    }


    public void run() {
        MainUI ui = new MainUI();
        ui.view.setVisible(true);
    }

    public static void main(String[] args) {
        System.out.println("Hello class!");
//        StoreManager.getInstance().init();
        StoreManager.getInstance().run();
    }

}
