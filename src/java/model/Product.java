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
    private Integer price;
    private Integer quantity;
    private Discount discountInfo;
    private Supplier supplierInfo;
    private String category;
    private String branchName;
    private String description;

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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
