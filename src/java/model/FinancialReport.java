package model;

import java.util.Date;
import java.util.Map;
import java.util.List;

public class FinancialReport {
    private Date startDate;
    private Date endDate;
    private double totalRevenue;
    private int totalTransactions;
    private double averageTransaction;
    private Map<String, Double> revenueByService;
    private Map<String, Double> monthlyRevenue;
    private List<Map<String, Object>> topCustomers;
    
    // Getters and Setters
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    
    public double getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(double totalRevenue) { this.totalRevenue = totalRevenue; }
    
    public int getTotalTransactions() { return totalTransactions; }
    public void setTotalTransactions(int totalTransactions) { this.totalTransactions = totalTransactions; }
    
    public double getAverageTransaction() { return averageTransaction; }
    public void setAverageTransaction(double averageTransaction) { this.averageTransaction = averageTransaction; }
    
    public Map<String, Double> getRevenueByService() { return revenueByService; }
    public void setRevenueByService(Map<String, Double> revenueByService) { this.revenueByService = revenueByService; }
    
    public Map<String, Double> getMonthlyRevenue() { return monthlyRevenue; }
    public void setMonthlyRevenue(Map<String, Double> monthlyRevenue) { this.monthlyRevenue = monthlyRevenue; }
    
    public List<Map<String, Object>> getTopCustomers() { return topCustomers; }
    public void setTopCustomers(List<Map<String, Object>> topCustomers) { this.topCustomers = topCustomers; }
} 