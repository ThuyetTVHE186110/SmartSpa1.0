package model;

public class CartItem {

    private int id;
    private int personId;
    private int productId;
    private String productName;
    private int price;
    private int productQuantity;
    private int serviceQuantity;
    private String image;
    private Product product;
    private Service service;

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
// Giả định có một lớp Product với thuộc tính RewardPoints

    public boolean isProduct() {
        return product != null;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {  // Thêm phương thức setProduct
        this.product = product;
    }

    public void setService(Service service) {  // Thêm phương thức setService
        this.service = service;
    }

    public int getServiceQuantity() {  // Thêm getter cho serviceQuantity
        return serviceQuantity;
    }

    public void setServiceQuantity(int serviceQuantity) {  // Thêm setter cho serviceQuantity
        this.serviceQuantity = serviceQuantity;
    }

    // Giả định có một lớp Service với thuộc tính RewardPoints
    public boolean isService() {
        return service != null;
    }

    public Service getService() {
        return service;
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
