package sample;

public class Product {
    String productName;
    String productAmount;
    String productAmountType;
    String productDescription;
    String productPicPath;
    double productPriceOld;
    double productPriceNew;

    public Product(String productName, String productAmount,  String productDescription, String productPicPath, double productPriceOld, double productPriceNew) {
        this.productName = productName;
        this.productAmount = productAmount;
        //this.productAmountType = productAmountType;
        this.productDescription = productDescription;
        this.productPicPath = productPicPath;
        this.productPriceOld = productPriceOld;
        this.productPriceNew = productPriceNew;
    }

    @Override
    public String toString() {
        return productAmount + " " +productName+ " " + " old: " + productPriceOld + " € new: " + productPriceNew + " €.";
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(String productAmount) {
        this.productAmount = productAmount;
    }

    public String getProductAmountType() {
        return productAmountType;
    }

    public void setProductAmountType(String productAmountType) {
        this.productAmountType = productAmountType;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductPicPath() {
        return productPicPath;
    }

    public void setProductPicPath(String productPicPath) {
        this.productPicPath = productPicPath;
    }

    public double getProductPriceOld() {        return productPriceOld;    }

    public void setProductPriceOld(double productPriceOld) {
        this.productPriceOld = productPriceOld;
    }

    public double getProductPriceNew() {
        return productPriceNew;
    }

    public void setProductPriceNew(double productPriceNew) {
        this.productPriceNew = productPriceNew;
    }
}

