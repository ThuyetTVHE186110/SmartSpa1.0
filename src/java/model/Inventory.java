/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.util.Date;
/**
 *
 * @author hotdo
 */
public class Inventory {
     private int id;
    private int productId;
    private int materialId;
    private int currentStock;
    private int minStock;
    private int maxStock;
    private int monthlyUsageEstimate;
    private Date lastRestocked;
    private Date nextRestockDue;

    public Inventory() {
    }

    public Inventory(int id, int productId, int materialId, int currentStock, int minStock, int maxStock, int monthlyUsageEstimate, Date lastRestocked, Date nextRestockDue) {
        this.id = id;
        this.productId = productId;
        this.materialId = materialId;
        this.currentStock = currentStock;
        this.minStock = minStock;
        this.maxStock = maxStock;
        this.monthlyUsageEstimate = monthlyUsageEstimate;
        this.lastRestocked = lastRestocked;
        this.nextRestockDue = nextRestockDue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }

    public int getMinStock() {
        return minStock;
    }

    public void setMinStock(int minStock) {
        this.minStock = minStock;
    }

    public int getMaxStock() {
        return maxStock;
    }

    public void setMaxStock(int maxStock) {
        this.maxStock = maxStock;
    }

    public int getMonthlyUsageEstimate() {
        return monthlyUsageEstimate;
    }

    public void setMonthlyUsageEstimate(int monthlyUsageEstimate) {
        this.monthlyUsageEstimate = monthlyUsageEstimate;
    }

    public Date getLastRestocked() {
        return lastRestocked;
    }

    public void setLastRestocked(Date lastRestocked) {
        this.lastRestocked = lastRestocked;
    }

    public Date getNextRestockDue() {
        return nextRestockDue;
    }

    public void setNextRestockDue(Date nextRestockDue) {
        this.nextRestockDue = nextRestockDue;
    }
    
}
