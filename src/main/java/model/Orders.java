package model;

public class Orders {

    private int id;
    private int customerId;
    private int productId;
    private int productAmount;


    public Orders(int id, int customerId, int productId, int productAmount) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.productAmount = productAmount;
    }

    public Orders(int customerId, int productId, int productAmount) {
        this.customerId = customerId;
        this.productId = productId;
        this.productAmount = productAmount;
    }

    public Orders() {
        this.customerId = 0;
        this.productId = 0;
        this.productAmount = 1;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(int id) {
        this.customerId = id;
    }

    public int getProductId() {
        return this.productId;
    }

    public void setProductId(int id) {
        this.productId = id;
    }

    public int getProductAmount() {
        return this.productAmount;
    }

    public void setProductAmount(int amount) {
        this.productAmount = amount;
    }


}
