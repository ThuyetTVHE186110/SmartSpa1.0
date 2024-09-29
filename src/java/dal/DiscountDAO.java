/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Discount;
/**
 *
 * @author Asus
 */
public class DiscountDAO extends DBContext{
    private static final String INSERT_DISCOUNT_SQL = "INSERT INTO discounts (name, code, discountStart, discountEnd, discountPercent) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_DISCOUNT_BY_ID = "SELECT * FROM discounts WHERE discountID = ?";
    private static final String SELECT_ALL_DISCOUNTS = "SELECT * FROM discounts";
    private static final String UPDATE_DISCOUNT_SQL = "UPDATE discounts SET name = ?, code = ?, discountStart = ?, discountEnd = ?, discountPercent = ? WHERE discountID = ?";
    private static final String DELETE_DISCOUNT_SQL = "DELETE FROM discounts WHERE discountID = ?";

    // Insert a new discount into the database
    public void addDiscount(Discount discount) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DISCOUNT_SQL)) {
            preparedStatement.setString(1, discount.getName());
            preparedStatement.setString(2, discount.getCode());
            preparedStatement.setDate(3, new java.sql.Date(discount.getDiscountStart().getTime()));
            preparedStatement.setDate(4, new java.sql.Date(discount.getDiscountEnd().getTime()));
            preparedStatement.setInt(5, discount.getDiscountPercent());
            preparedStatement.executeUpdate();
        }
    }

    // Select a discount by ID
    public Discount selectDiscount(int discountID) throws SQLException {
        Discount discount = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_DISCOUNT_BY_ID)) {
            preparedStatement.setInt(1, discountID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String code = rs.getString("code");
                Date discountStart = rs.getDate("discountStart");
                Date discountEnd = rs.getDate("discountEnd");
                int discountPercent = rs.getInt("discountPercent");
                discount = new Discount(discountID, name, code, discountStart, discountEnd, discountPercent);
            }
        }
        return discount;
    }

    // Select all discounts
    public List<Discount> selectAllDiscounts() throws SQLException {
        List<Discount> discounts = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_DISCOUNTS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int discountID = rs.getInt("discountID");
                String name = rs.getString("name");
                String code = rs.getString("code");
                Date discountStart = rs.getDate("discountStart");
                Date discountEnd = rs.getDate("discountEnd");
                int discountPercent = rs.getInt("discountPercent");
                discounts.add(new Discount(discountID, name, code, discountStart, discountEnd, discountPercent));
            }
        }
        return discounts;
    }

    // Update an existing discount
    public boolean updateDiscount(Discount discount) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DISCOUNT_SQL)) {
            preparedStatement.setString(1, discount.getName());
            preparedStatement.setString(2, discount.getCode());
            preparedStatement.setDate(3, new java.sql.Date(discount.getDiscountStart().getTime()));
            preparedStatement.setDate(4, new java.sql.Date(discount.getDiscountEnd().getTime()));
            preparedStatement.setInt(5, discount.getDiscountPercent());
            preparedStatement.setInt(6, discount.getDiscountID());
            rowUpdated = preparedStatement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    // Delete a discount by ID
    public boolean deleteDiscount(int discountID) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_DISCOUNT_SQL)) {
            preparedStatement.setInt(1, discountID);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        }
        return rowDeleted;
    }
}
