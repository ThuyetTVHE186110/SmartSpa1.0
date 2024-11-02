package model;

public class CartItem {
    private int id;
    private int personId;
    private int productId;
    private String productName;
    private int price;
    private int productQuantity;
    private String image;
    
    public CartItem() {
    }
    
    // Constructor for product cart item
    public CartItem(int personId, int productId, int productQuantity, String productName, int price, String image) {
        this.personId = personId;
        this.productId = productId;
        this.productQuantity = productQuantity;
        this.productName = productName;
        this.price = price;
        this.image = image;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
} 