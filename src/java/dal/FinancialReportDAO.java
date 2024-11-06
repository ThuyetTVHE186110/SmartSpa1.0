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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Asus
 */
public class FinancialReportDAO {
    private static final String CURRENCY = "VND";
    private static final String COMPLETED_STATUS = "PAID";
    
    public Map<String, Double> getRevenueByService(String startDate, String endDate) {
        Map<String, Double> revenueByService = new LinkedHashMap<>();
        String sql = "SELECT description, SUM(amount) as revenue " +
                    "FROM payments " +
                    "WHERE status = ? " +
                    "AND created_at BETWEEN ? AND ? " +
                    "GROUP BY description " +
                    "ORDER BY revenue DESC";
                    
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, COMPLETED_STATUS);
            ps.setString(2, startDate != null ? startDate : LocalDate.now().minusMonths(1).toString());
            ps.setString(3, endDate != null ? endDate : LocalDate.now().toString());
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String description = rs.getString("description");
                double revenue = rs.getDouble("revenue");
                if (description != null) {
                    revenueByService.put(description, revenue);
                }
            }
            
            if (revenueByService.isEmpty()) {
                revenueByService.put("No data available", 0.0);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting revenue by service: " + e.getMessage());
            revenueByService.put("Error loading data", 0.0);
        }
        return revenueByService;
    }
    
    public Map<String, Double> getMonthlyRevenue(int year) {
        Map<String, Double> monthlyRevenue = new LinkedHashMap<>();
        
        // Initialize all months with 0
        for (int i = 1; i <= 12; i++) {
            monthlyRevenue.put(getMonthName(i), 0.0);
        }
        
        String sql = "SELECT MONTH(created_at) as month, " +
                    "SUM(amount) as revenue " +
                    "FROM payments " +
                    "WHERE YEAR(created_at) = ? " +
                    "AND status = ? " +
                    "GROUP BY MONTH(created_at) " +
                    "ORDER BY month";
                    
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, year);
            ps.setString(2, COMPLETED_STATUS);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int month = rs.getInt("month");
                double revenue = rs.getDouble("revenue");
                monthlyRevenue.put(getMonthName(month), revenue);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting monthly revenue: " + e.getMessage());
        }
        return monthlyRevenue;
    }
    
    public Map<String, Object> getFinancialSummary(String startDate, String endDate) {
        Map<String, Object> summary = new HashMap<>();
        String sql = "SELECT " +
                    "COALESCE(SUM(amount), 0) as totalRevenue, " +
                    "COUNT(*) as totalTransactions, " +
                    "COALESCE(AVG(amount), 0) as averageTransaction " +
                    "FROM payments " +
                    "WHERE status = ? " +
                    "AND created_at BETWEEN ? AND ?";
                    
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, COMPLETED_STATUS);
            ps.setString(2, startDate != null ? startDate : LocalDate.now().minusMonths(1).toString());
            ps.setString(3, endDate != null ? endDate : LocalDate.now().toString());
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                summary.put("totalRevenue", rs.getDouble("totalRevenue"));
                summary.put("totalTransactions", rs.getInt("totalTransactions"));
                summary.put("averageTransaction", rs.getDouble("averageTransaction"));
                summary.put("currency", CURRENCY);
            } else {
                summary.put("totalRevenue", 0.0);
                summary.put("totalTransactions", 0);
                summary.put("averageTransaction", 0.0);
                summary.put("currency", CURRENCY);
            }
        } catch (SQLException e) {
            System.err.println("Error getting financial summary: " + e.getMessage());
            summary.put("totalRevenue", 0.0);
            summary.put("totalTransactions", 0);
            summary.put("averageTransaction", 0.0);
            summary.put("currency", CURRENCY);
        }
        return summary;
    }
    
    public List<Map<String, Object>> getTopCustomers(String startDate, String endDate, int limit) {
        List<Map<String, Object>> topCustomers = new ArrayList<>();
        String sql = "SELECT TOP " + limit + " " +
                    "p.ID as PersonID, " +
                    "p.Name as customerName, " +
                    "COUNT(*) as totalVisits, " +
                    "COALESCE(SUM(py.amount), 0) as totalSpent " +
                    "FROM payments py " +
                    "JOIN Person p ON py.PersonID = p.ID " +
                    "WHERE py.status = ? " +
                    "AND py.created_at BETWEEN ? AND ? " +
                    "AND py.PersonID IS NOT NULL " +
                    "GROUP BY p.ID, p.Name " +
                    "ORDER BY totalSpent DESC";
                    
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, COMPLETED_STATUS);
            ps.setString(2, startDate != null ? startDate : LocalDate.now().minusMonths(1).toString());
            ps.setString(3, endDate != null ? endDate : LocalDate.now().toString());
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> customer = new HashMap<>();
                customer.put("customerId", rs.getString("PersonID"));
                customer.put("customerName", rs.getString("customerName"));
                customer.put("totalVisits", rs.getInt("totalVisits"));
                customer.put("totalSpent", rs.getDouble("totalSpent"));
                customer.put("currency", CURRENCY);
                topCustomers.add(customer);
            }
            
            if (topCustomers.isEmpty()) {
                Map<String, Object> defaultCustomer = new HashMap<>();
                defaultCustomer.put("customerId", "N/A");
                defaultCustomer.put("customerName", "No customers yet");
                defaultCustomer.put("totalVisits", 0);
                defaultCustomer.put("totalSpent", 0.0);
                defaultCustomer.put("currency", CURRENCY);
                topCustomers.add(defaultCustomer);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting top customers: " + e.getMessage());
        }
        return topCustomers;
    }
    
    private String getMonthName(int month) {
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", 
                          "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        return months[month - 1];
    }
}
