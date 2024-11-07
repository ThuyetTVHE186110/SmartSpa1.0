/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author PC
 */
public class Cart {

    private int id;
    private Person personInfo;
    private int quantity;
    private List<CartItem> items;  // Danh sách các mục trong giỏ hàng

    public Cart() {
        items = new ArrayList<>(); // Khởi tạo danh sách các mục
    }

    public Cart(int id, Person personInfo) {
        this.id = id;
        this.personInfo = personInfo;
        this.items = new ArrayList<>();
    }
    // Phương thức thêm một CartItem vào giỏ hàng

    public void addItem(CartItem item) {
        items.add(item);
    }

    // Phương thức xóa một CartItem khỏi giỏ hàng
    public void removeItem(CartItem item) {
        items.remove(item);
    }

    // Getter và Setter cho danh sách CartItem
    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(Person personInfo) {
        this.personInfo = personInfo;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
