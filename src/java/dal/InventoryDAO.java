/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import static dal.DBContext.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import model.Category;
import model.Inventory;
import model.Material;
import model.Product;

/**
 *
 * @author hotdo
 */
public class InventoryDAO extends DBContext {

    static final Logger logger = Logger.getLogger(InventoryDAO.class.getName());

    public InventoryDAO() {

    }

    public List<Inventory> getAllInventory() {
        List<Inventory> inventoryList = new ArrayList<>();
        String sql = "SELECT * FROM Inventory";

        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Inventory inventory = new Inventory();
                inventory.setId(rs.getInt("ID"));
                inventory.setProductId(rs.getInt("ProductID"));
                inventory.setMaterialId(rs.getInt("MaterialID"));
                inventory.setCurrentStock(rs.getInt("CurrentStock"));
                inventory.setMinStock(rs.getInt("MinStock"));
                inventory.setMaxStock(rs.getInt("MaxStock"));
                inventory.setMonthlyUsageEstimate(rs.getInt("MonthlyUsageEstimate"));
                inventory.setLastRestocked(rs.getTimestamp("LastRestocked"));
                inventory.setNextRestockDue(rs.getTimestamp("NextRestockDue"));
                inventoryList.add(inventory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventoryList;
    }

    public List<Category> getProductCategories() {
        List<Category> productCategories = new ArrayList<>();
        String sql = "SELECT * FROM Category";  // Truy vấn này có thể thay đổi nếu có phân loại danh mục cho sản phẩm

        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("ID"));
                category.setName(rs.getString("Name"));
                productCategories.add(category); // Thêm danh mục vào danh sách
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productCategories;
    }
     public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.*, c.Name AS CategoryName\n"
                + "                 FROM Product p \n"
                + "                 JOIN Category c ON p.CategoryID = c.ID";

        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("ID"));
                product.setName(rs.getString("Name"));
                product.setPrice(rs.getInt("Price"));
                product.setImage(rs.getString("Image"));
                product.setQuantity(rs.getInt("Quantity"));
                product.setBranchName(rs.getString("BranchName"));
                product.setDescription(rs.getString("Description"));
                product.setCategory(rs.getString("CategoryName"));
                product.setStatus(rs.getString("Status"));
                product.setIngredient(rs.getString("Ingredient"));
                product.setHowToUse(rs.getString("HowToUse"));
                product.setBenefit(rs.getString("Benefit"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    public List<Material> getMaterialCategories() {
        List<Material> materialCategories = new ArrayList<>();
        String sql = "SELECT * FROM Material";

        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Material material = new Material();
                material.setId(rs.getInt("ID"));
                material.setName(rs.getString("Name"));
                material.setPrice(rs.getInt("Price"));
                material.setImage(rs.getString("Image"));
                material.setDescription(rs.getString("Description"));
                material.setStatus(rs.getString("Status"));
                materialCategories.add(material);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materialCategories;
    }

    public boolean addInventory(Inventory inventory) {
        String sql = "INSERT INTO Inventory (ProductID, MaterialID, CurrentStock, MinStock, MaxStock, MonthlyUsageEstimate, LastRestocked, NextRestockDue) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, inventory.getProductId());
            pstmt.setInt(2, inventory.getMaterialId());
            pstmt.setInt(3, inventory.getCurrentStock());
            pstmt.setInt(4, inventory.getMinStock());
            pstmt.setInt(5, inventory.getMaxStock());
            pstmt.setInt(6, inventory.getMonthlyUsageEstimate());
            pstmt.setTimestamp(7, (Timestamp) inventory.getLastRestocked());
            pstmt.setTimestamp(8, (Timestamp) inventory.getNextRestockDue());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        // Không cần finally để đóng pstmt và connection vì chúng đã được quản lý bởi try-with-resources
    }

    public boolean updateInventory(Inventory inventory) {
        String sql = "UPDATE Inventory SET CurrentStock = ?, MinStock = ?, MaxStock = ?, MonthlyUsageEstimate = ?, LastRestocked = ?, NextRestockDue = ? "
                + "WHERE ID = ?";

        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            pstmt.setInt(1, inventory.getCurrentStock());
            pstmt.setInt(2, inventory.getMinStock());
            pstmt.setInt(3, inventory.getMaxStock());
            pstmt.setInt(4, inventory.getMonthlyUsageEstimate());
            pstmt.setTimestamp(5, (Timestamp) inventory.getLastRestocked());
            pstmt.setTimestamp(6, (Timestamp) inventory.getNextRestockDue());
            pstmt.setInt(7, inventory.getId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
