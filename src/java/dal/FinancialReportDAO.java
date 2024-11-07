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
    private static final String COMPLETED_STATUS = "COMPLETED";
    
    public Map<String, Double> getRevenueByService(String startDate, String endDate) {
        Map<String, Double> revenueByService = new LinkedHashMap<>();
        String sql = "SELECT description, SUM(amount) as revenue " +
                    "FROM payments " +
                    "WHERE status = ? " +
                    "AND CONVERT(date, created_at) BETWEEN CONVERT(date, ?) AND CONVERT(date, ?) " +
                    "GROUP BY description " +
                    "ORDER BY revenue DESC";
                    
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            System.out.println("Executing query with dates: " + startDate + " to " + endDate);
            ps.setString(1, COMPLETED_STATUS);
            ps.setString(2, startDate);
            ps.setString(3, endDate);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String description = rs.getString("description");
                double revenue = rs.getDouble("revenue");
                System.out.println("Found revenue: " + description + " = " + revenue);
                if (description != null) {
                    revenueByService.put(description, revenue);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error in getRevenueByService: " + e.getMessage());
            e.printStackTrace();
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
            
            System.out.println("Executing monthly revenue query for year: " + year);
            ps.setInt(1, year);
            ps.setString(2, COMPLETED_STATUS);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int month = rs.getInt("month");
                double revenue = rs.getDouble("revenue");
                System.out.println("Found monthly revenue: Month " + month + " = " + revenue);
                monthlyRevenue.put(getMonthName(month), revenue);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting monthly revenue: " + e.getMessage());
            e.printStackTrace();
        }
        return monthlyRevenue;
    }
    
    public Map<String, Object> getFinancialSummary(String startDate, String endDate) {
        Map<String, Object> summary = new HashMap<>();
        String sql = "SELECT " +
                    "COUNT(*) as totalTransactions, " +
                    "COALESCE(SUM(amount), 0) as totalRevenue, " +
                    "COALESCE(AVG(amount), 0) as averageTransaction " +
                    "FROM payments " +
                    "WHERE status = ? " +
                    "AND CONVERT(date, created_at) BETWEEN CONVERT(date, ?) AND CONVERT(date, ?)";
                    
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            System.out.println("Executing summary query with dates: " + startDate + " to " + endDate);
            ps.setString(1, COMPLETED_STATUS);
            ps.setString(2, startDate);
            ps.setString(3, endDate);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                summary.put("totalRevenue", rs.getDouble("totalRevenue"));
                summary.put("totalTransactions", rs.getInt("totalTransactions"));
                summary.put("averageTransaction", rs.getDouble("averageTransaction"));
                summary.put("currency", CURRENCY);
                System.out.println("Found summary: " + summary);
            }
        } catch (SQLException e) {
            System.err.println("Error getting financial summary: " + e.getMessage());
            e.printStackTrace();
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
                    "AND CONVERT(date, py.created_at) BETWEEN CONVERT(date, ?) AND CONVERT(date, ?) " +
                    "GROUP BY p.ID, p.Name " +
                    "ORDER BY totalSpent DESC";
                    
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            System.out.println("Executing top customers query with dates: " + startDate + " to " + endDate);
            ps.setString(1, COMPLETED_STATUS);
            ps.setString(2, startDate);
            ps.setString(3, endDate);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> customer = new HashMap<>();
                customer.put("customerId", rs.getString("PersonID"));
                customer.put("customerName", rs.getString("customerName"));
                customer.put("totalVisits", rs.getInt("totalVisits"));
                customer.put("totalSpent", rs.getDouble("totalSpent"));
                customer.put("currency", CURRENCY);
                System.out.println("Found customer: " + customer);
                topCustomers.add(customer);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting top customers: " + e.getMessage());
            e.printStackTrace();
        }
        return topCustomers;
    }
    
    private String getMonthName(int month) {
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", 
                          "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        return months[month - 1];
    }
    
    public static void main(String[] args) {
        FinancialReportDAO dao = new FinancialReportDAO();
        
        // Set test dates
        String startDate = LocalDate.now().minusMonths(1).toString();
        String endDate = LocalDate.now().toString();
        int currentYear = LocalDate.now().getYear();
        
        System.out.println("Testing FinancialReportDAO...");
        System.out.println("Date range: " + startDate + " to " + endDate);
        System.out.println("Year: " + currentYear);
        System.out.println("----------------------------------------");
        
        // Test getRevenueByService
        System.out.println("\nTesting getRevenueByService:");
        Map<String, Double> revenueByService = dao.getRevenueByService(startDate, endDate);
        System.out.println("Revenue by service: " + revenueByService);
        
        // Test getMonthlyRevenue
        System.out.println("\nTesting getMonthlyRevenue:");
        Map<String, Double> monthlyRevenue = dao.getMonthlyRevenue(currentYear);
        System.out.println("Monthly revenue: " + monthlyRevenue);
        
        // Test getFinancialSummary
        System.out.println("\nTesting getFinancialSummary:");
        Map<String, Object> summary = dao.getFinancialSummary(startDate, endDate);
        System.out.println("Total Revenue: " + summary.get("totalRevenue") + " " + summary.get("currency"));
        System.out.println("Total Transactions: " + summary.get("totalTransactions"));
        System.out.println("Average Transaction: " + summary.get("averageTransaction") + " " + summary.get("currency"));
        
        // Test getTopCustomers
        System.out.println("\nTesting getTopCustomers:");
        List<Map<String, Object>> topCustomers = dao.getTopCustomers(startDate, endDate, 10);
        for (Map<String, Object> customer : topCustomers) {
            System.out.println("Customer: " + customer.get("customerName"));
            System.out.println("Total Visits: " + customer.get("totalVisits"));
            System.out.println("Total Spent: " + customer.get("totalSpent") + " " + customer.get("currency"));
            System.out.println("----------------------------------------");
        }
        
        // Test SQL queries directly
        System.out.println("\nTesting direct SQL queries:");
        try (Connection conn = getConnection()) {
            // Test payments table
            String sql = "SELECT COUNT(*) as count, status FROM payments GROUP BY status";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            System.out.println("\nPayments by status:");
            while (rs.next()) {
                System.out.println(rs.getString("status") + ": " + rs.getInt("count"));
            }
            
            // Test recent payments
            sql = "SELECT TOP 5 * FROM payments ORDER BY created_at DESC";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            System.out.println("\nMost recent payments:");
            while (rs.next()) {
                System.out.println("Transaction ID: " + rs.getString("transaction_id"));
                System.out.println("Amount: " + rs.getDouble("amount") + " " + rs.getString("currency"));
                System.out.println("Status: " + rs.getString("status"));
                System.out.println("Created at: " + rs.getTimestamp("created_at"));
                System.out.println("----------------------------------------");
            }
            
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
