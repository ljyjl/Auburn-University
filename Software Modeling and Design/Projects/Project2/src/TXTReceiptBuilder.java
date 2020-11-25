public class TXTReceiptBuilder implements IReceiptBuilder {

    StringBuilder sb = new StringBuilder();

    @Override
    public void appendHeader(String header) {
        sb.append(header).append("\n");
    }

    @Override
    public void appendCustomer(CustomerModel customer) {
        sb.append("Customer ID: ").append(customer.mCustomerID).append("\n");
        sb.append("Customer Name: ").append(customer.mName).append("\n");
    }

    @Override
    public void appendProduct(ProductModel product) {
        sb.append("Product ID: ").append(product.mProductID).append("\n");
        sb.append("Product Name: ").append(product.mName).append("\n");
    }

    @Override
    public void appendPurchase(PurchaseModel purchase) {
        sb.append("Purchase ID: ").append(purchase.mProductID).append("\n");
        sb.append("Purchase Date: ").append(purchase.mDate).append("\n");
        sb.append("Purchase Cost: ").append(purchase.mCost).append("\n");
        sb.append("Purchase Tax: ").append(purchase.mTax).append("\n");
        sb.append("Purchase Total: ").append(purchase.mTotal).append("\n");
    }

    @Override
    public void appendFooter(String footer) {
        sb.append(footer).append("\n");
    }

    public String toString() {
        return sb.toString();
    }
}
