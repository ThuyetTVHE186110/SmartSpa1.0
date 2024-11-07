/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author PC
 */
public class Product {

    private int id;
    private String name;
    private int price;
    private int quantity;
    private Discount discountInfo;
    private Supplier supplierInfo;
    private String category;
    private String branchName;
    private String image;
    private String description;
    private String status;
    private String ingredient;
    private String howToUse;
    private String benefit;
    private int rewardPoints;

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public Product() {
    }

    public Product(int id, String name, int price, int quantity, Discount discountInfo, Supplier supplierInfo, String category, String branchName, String image, String description, String status, String ingredient, String howToUse, String benefit, int rewardPoints) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.discountInfo = discountInfo;
        this.supplierInfo = supplierInfo;
        this.category = category;
        this.branchName = branchName;
        this.image = image;
        this.description = description;
        this.status = status;
        this.ingredient = ingredient;
        this.howToUse = howToUse;
        this.benefit = benefit;
        this.rewardPoints = rewardPoints;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Discount getDiscountInfo() {
        return discountInfo;
    }

    public void setDiscountInfo(Discount discountInfo) {
        this.discountInfo = discountInfo;
    }

    public Supplier getSupplierInfo() {
        return supplierInfo;
    }

    public void setSupplierInfo(Supplier supplierInfo) {
        this.supplierInfo = supplierInfo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getHowToUse() {
        return howToUse;
    }

    public void setHowToUse(String howToUse) {
        this.howToUse = howToUse;
    }

    public String getBenefit() {
        return benefit;
    }

    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

 

}
