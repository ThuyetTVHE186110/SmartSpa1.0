/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.List;
import model.Referral;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class ReferralDAO extends DBContext {

    private final Logger logger = Logger.getLogger(getClass().getName());

    // Thêm một bản ghi Referral mới vào bảng
    public boolean insertReferral(Referral referral) {
        String sql = "INSERT INTO Referral (code, value, staffID, bonus) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBContext.getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, referral.getCode());
            pstmt.setInt(2, referral.getValue());
            pstmt.setInt(3, referral.getStaffID());
            pstmt.setInt(4, referral.getBonus());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        referral.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            logger.log(Level.INFO, "Error inserting referral: {0}", e.getMessage());
        }
        return false;
    }

    // Lấy một Referral theo id
    public Referral getReferralById(int id) {
        String sql = "SELECT * FROM Referral WHERE id = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Referral referral = new Referral();
                    referral.setId(rs.getInt("id"));
                    referral.setCode(rs.getString("code"));
                    referral.setValue(rs.getInt("value"));
                    referral.setStaffID(rs.getInt("staffID"));
                    referral.setBonus(rs.getInt("Bonus"));
                    return referral;
                }
            }

        } catch (SQLException e) {
            logger.log(Level.INFO, "Error retrieving referral by ID: {0}", e.getMessage());
        }
        return null;
    }

    // Lấy một Referral theo Code
    public Referral getReferralByCode(String code) {
        String sql = "SELECT * FROM Referral WHERE code = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, code);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Referral referral = new Referral();
                    referral.setId(rs.getInt("id"));
                    referral.setCode(rs.getString("code"));
                    referral.setValue(rs.getInt("value"));
                    referral.setStaffID(rs.getInt("staffID"));
                    referral.setBonus(rs.getInt("Bonus"));
                    return referral;
                }
            }

        } catch (SQLException e) {
            logger.log(Level.INFO, "Error retrieving referral by ID: {0}", e.getMessage());
        }
        return null;
    }

    // Lấy một Referral theo Code
    public Referral getReferralByStaff(int staffID) {
        String sql = "SELECT * FROM Referral WHERE staffID = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, staffID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Referral referral = new Referral();
                    referral.setId(rs.getInt("id"));
                    referral.setCode(rs.getString("code"));
                    referral.setValue(rs.getInt("value"));
                    referral.setStaffID(rs.getInt("staffID"));
                    referral.setBonus(rs.getInt("Bonus"));
                    return referral;
                }
            }

        } catch (SQLException e) {
            logger.log(Level.INFO, "Error retrieving referral by ID: {0}", e.getMessage());
        }
        return null;
    }

    // Lấy tất cả Referral từ bảng
    public List<Referral> getAllReferrals() {
        String sql = "SELECT * FROM Referral";
        List<Referral> referrals = new ArrayList<>();

        // Try-with-resources đảm bảo các resource sẽ được đóng tự động
        try (Connection connection = DBContext.getConnection(); PreparedStatement stm = connection.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                Referral referral = new Referral();
                referral.setId(rs.getInt("id"));
                referral.setCode(rs.getString("code"));
                referral.setValue(rs.getInt("value"));
                referral.setStaffID(rs.getInt("staffID"));
                referral.setBonus(rs.getInt("Bonus"));
                referrals.add(referral);
            }

        } catch (SQLException e) {
            logger.log(Level.INFO, "Error accessing database: {0}", e.getMessage());
        }
        return referrals;
    }

    // Cập nhật một Referral
    public boolean updateReferral(Referral referral) {
        String sql = "UPDATE Referral SET code = ?, value = ?, staffID = ?, bonus = ? WHERE id = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, referral.getCode());
            pstmt.setInt(2, referral.getValue());
            pstmt.setInt(3, referral.getStaffID());
            pstmt.setInt(5, referral.getId());
            pstmt.setInt(4, referral.getBonus());
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.log(Level.INFO, "Error updating referral: {0}", e.getMessage());
        }
        return false;
    }

    // Xóa một Referral theo id
    public boolean deleteReferral(int id) {
        String sql = "DELETE FROM Referral WHERE id = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.log(Level.INFO, "Error deleting referral: {0}", e.getMessage());
        }
        return false;
    }
    public static void main(String[] args) {
        ReferralDAO referralDAO = new ReferralDAO();
        System.out.println(referralDAO.deleteReferral(1));
        Referral referral = new Referral();
        referral.setCode("CHAO2024");
        referral.setStaffID(10);
        referral.setValue(10);
        referral.setBonus(10);
        if(referralDAO.insertReferral(referral)){
            System.out.println("Thành công");
        } else {
            System.out.println("thất bại");
        }
        
    }
}
