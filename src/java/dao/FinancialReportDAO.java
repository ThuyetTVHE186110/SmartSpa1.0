package dao;

import dal.DBContext;
import java.sql.*;
import java.util.*;
import model.FinancialReport;
import java.time.LocalDate;

public class FinancialReportDAO extends DBContext {
    
    public Map<String, Double> getRevenueByService(String startDate, String endDate) {
        Map<String, Double> revenueByService = new LinkedHashMap<>();
        String sql = "SELECT s.Name as serviceName, " +
                    "COALESCE(SUM(ob.TotalAmount), 0) as revenue " +
                    "FROM Services s " +
                    "LEFT JOIN Order_Service os ON s.ID = os.ServiceID " +
                    "LEFT JOIN Orders_Bill ob ON os.OrderID = ob.ID " +
                    "WHERE (ob.OrderDate BETWEEN ? AND ?) OR ob.OrderDate IS NULL " +
                    "GROUP BY s.Name " +
                    "ORDER BY revenue DESC";
                    
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, startDate != null ? startDate : LocalDate.now().minusMonths(1).toString());
            ps.setString(2, endDate != null ? endDate : LocalDate.now().toString());
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String serviceName = rs.getString("serviceName");
                double revenue = rs.getDouble("revenue");
                if (serviceName != null) {
                    revenueByService.put(serviceName, revenue);
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
        
        for (int i = 1; i <= 12; i++) {
            monthlyRevenue.put(getMonthName(i), 0.0);
        }
        
        String sql = "SELECT MONTH(OrderDate) as month, " +
                    "COALESCE(SUM(TotalAmount), 0) as revenue " +
                    "FROM Orders_Bill " +
                    "WHERE YEAR(OrderDate) = ? " +
                    "GROUP BY MONTH(OrderDate) " +
                    "ORDER BY month";
                    
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, year);
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
    
    private String getMonthName(int month) {
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", 
                          "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        return months[month - 1];
    }
    
    public Map<String, Object> getFinancialSummary(String startDate, String endDate) {
        Map<String, Object> summary = new HashMap<>();
        String sql = "SELECT " +
                    "COALESCE(SUM(TotalAmount), 0) as totalRevenue, " +
                    "COUNT(*) as totalTransactions, " +
                    "COALESCE(AVG(TotalAmount), 0) as averageTransaction " +
                    "FROM Orders_Bill " +
                    "WHERE OrderDate BETWEEN ? AND ?";
                    
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, startDate != null ? startDate : LocalDate.now().minusMonths(1).toString());
            ps.setString(2, endDate != null ? endDate : LocalDate.now().toString());
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                summary.put("totalRevenue", rs.getDouble("totalRevenue"));
                summary.put("totalTransactions", rs.getInt("totalTransactions"));
                summary.put("averageTransaction", rs.getDouble("averageTransaction"));
            } else {
                summary.put("totalRevenue", 0.0);
                summary.put("totalTransactions", 0);
                summary.put("averageTransaction", 0.0);
            }
        } catch (SQLException e) {
            System.err.println("Error getting financial summary: " + e.getMessage());
            summary.put("totalRevenue", 0.0);
            summary.put("totalTransactions", 0);
            summary.put("averageTransaction", 0.0);
        }
        return summary;
    }
    
    public List<Map<String, Object>> getTopCustomers(String startDate, String endDate, int limit) {
        List<Map<String, Object>> topCustomers = new ArrayList<>();
        String sql = "SELECT TOP " + limit + " " +
                    "p.ID as PersonID, " +
                    "p.Name as customerName, " +
                    "COUNT(ob.ID) as totalVisits, " +
                    "COALESCE(SUM(ob.TotalAmount), 0) as totalSpent " +
                    "FROM Person p " +
                    "LEFT JOIN Orders_Bill ob ON p.ID = ob.PersonID " +
                    "WHERE (ob.OrderDate BETWEEN ? AND ?) OR ob.OrderDate IS NULL " +
                    "GROUP BY p.ID, p.Name " +
                    "ORDER BY totalSpent DESC";
                    
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, startDate != null ? startDate : LocalDate.now().minusMonths(1).toString());
            ps.setString(2, endDate != null ? endDate : LocalDate.now().toString());
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> customer = new HashMap<>();
                customer.put("customerId", rs.getString("PersonID"));
                customer.put("customerName", rs.getString("customerName"));
                customer.put("totalVisits", rs.getInt("totalVisits"));
                customer.put("totalSpent", rs.getDouble("totalSpent"));
                topCustomers.add(customer);
            }
            
            if (topCustomers.isEmpty()) {
                Map<String, Object> defaultCustomer = new HashMap<>();
                defaultCustomer.put("customerId", "N/A");
                defaultCustomer.put("customerName", "No customers yet");
                defaultCustomer.put("totalVisits", 0);
                defaultCustomer.put("totalSpent", 0.0);
                topCustomers.add(defaultCustomer);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting top customers: " + e.getMessage());
        }
        return topCustomers;
    }
} 