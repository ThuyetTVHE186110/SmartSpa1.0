package dal;

import java.sql.*;
import java.util.*;
import model.Payment;
import model.Service;

public class DashboardDAO extends DBContext {
    
    // Get total revenue for a specific period
    public double getTotalRevenue(String period) throws SQLException {
        String sql = "SELECT SUM(amount) as total FROM payments WHERE status = 'COMPLETED' ";
        
        switch(period.toLowerCase()) {
            case "today":
                sql += "AND CAST(created_at AS DATE) = CAST(GETDATE() AS DATE)";
                break;
            case "month":
                sql += "AND MONTH(created_at) = MONTH(GETDATE()) AND YEAR(created_at) = YEAR(GETDATE())";
                break;
            case "year":
                sql += "AND YEAR(created_at) = YEAR(GETDATE())";
                break;
        }
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total");
            }
        }
        return 0.0;
    }
    
    // Get total number of customers
    public int getTotalCustomers() throws SQLException {
        String sql = "SELECT COUNT(DISTINCT PersonID) as total FROM payments";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }
    
    // Get total transactions for a period
    public int getTotalTransactions(String period) throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM payments WHERE 1=1 ";
        
        switch(period.toLowerCase()) {
            case "today":
                sql += "AND CAST(created_at AS DATE) = CAST(GETDATE() AS DATE)";
                break;
            case "month":
                sql += "AND MONTH(created_at) = MONTH(GETDATE()) AND YEAR(created_at) = YEAR(GETDATE())";
                break;
            case "year":
                sql += "AND YEAR(created_at) = YEAR(GETDATE())";
                break;
        }
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }
    
    // Get recent payments
    public List<Payment> getRecentPayments(int limit) throws SQLException {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT TOP (?) * FROM payments ORDER BY created_at DESC";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Payment payment = new Payment();
                payment.setTransactionId(rs.getString("transaction_id"));
                payment.setOrderCode(rs.getString("order_code"));
                payment.setAmount(rs.getDouble("amount"));
                payment.setCurrency(rs.getString("currency"));
                payment.setStatus(rs.getString("status"));
                payment.setDescription(rs.getString("description"));
                payment.setPersonId(rs.getInt("PersonID"));
                payment.setCreatedAt(rs.getTimestamp("created_at"));
                payments.add(payment);
            }
        }
        return payments;
    }
    
    // Get revenue statistics by period
    public Map<String, Double> getRevenueStats(String period, int limit) throws SQLException {
        Map<String, Double> stats = new LinkedHashMap<>();
        String sql = "";
        
        switch(period.toLowerCase()) {
            case "daily":
                sql = "SELECT TOP (?) CAST(created_at AS DATE) as date, SUM(amount) as total " +
                      "FROM payments WHERE status = 'COMPLETED' " +
                      "GROUP BY CAST(created_at AS DATE) " +
                      "ORDER BY date DESC";
                break;
            case "monthly":
                sql = "SELECT TOP (?) DATEFROMPARTS(YEAR(created_at), MONTH(created_at), 1) as date, " +
                      "SUM(amount) as total " +
                      "FROM payments WHERE status = 'COMPLETED' " +
                      "GROUP BY DATEFROMPARTS(YEAR(created_at), MONTH(created_at), 1) " +
                      "ORDER BY date DESC";
                break;
        }
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                stats.put(rs.getDate("date").toString(), rs.getDouble("total"));
            }
        }
        return stats;
    }
    
    // Get payment status distribution
    public Map<String, Integer> getPaymentStatusDistribution() throws SQLException {
        Map<String, Integer> distribution = new HashMap<>();
        String sql = "SELECT status, COUNT(*) as count FROM payments GROUP BY status";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                distribution.put(rs.getString("status"), rs.getInt("count"));
            }
        }
        return distribution;
    }
} 