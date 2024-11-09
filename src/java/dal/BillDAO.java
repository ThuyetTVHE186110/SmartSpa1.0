package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Bill;
import model.BillDetail;

public class BillDAO extends DBContext {
    
    public Bill createBill(Bill bill) {
        String sql = "INSERT INTO bills (bill_code, staff_id, customer_name, customer_phone, " +
                    "subtotal, tax, total, status, created_at) " +
                    "OUTPUT INSERTED.id " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, bill.getBillCode());
            stmt.setInt(2, bill.getStaffId());
            stmt.setString(3, bill.getCustomerName());
            stmt.setString(4, bill.getCustomerPhone());
            stmt.setDouble(5, bill.getSubtotal());
            stmt.setDouble(6, bill.getTax());
            stmt.setDouble(7, bill.getTotal());
            stmt.setString(8, "PENDING");
            stmt.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                bill.setId(rs.getInt(1));
                // Insert bill details
                insertBillDetails(bill);
                return bill;
            }
        } catch (SQLException e) {
            System.err.println("Error creating bill: " + e.getMessage());
            throw new RuntimeException("Error creating bill", e);
        }
        return null;
    }
    
    private void insertBillDetails(Bill bill) throws SQLException {
        String sql = "INSERT INTO bill_details (bill_id, service_id, service_name, price) VALUES (?, ?, ?, ?)";
                    
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            for (BillDetail detail : bill.getBillDetails()) {
                stmt.setInt(1, bill.getId());
                stmt.setInt(2, detail.getServiceId());
                stmt.setString(3, detail.getServiceName());
                stmt.setDouble(4, detail.getPrice());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }
    
    public Bill getBillByCode(String billCode) {
        String sql = "SELECT * FROM bills WHERE bill_code = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, billCode);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Bill bill = mapBill(rs);
                bill.setBillDetails(getBillDetails(bill.getId()));
                return bill;
            }
        } catch (SQLException e) {
            System.err.println("Error getting bill: " + e.getMessage());
            throw new RuntimeException("Error getting bill", e);
        }
        return null;
    }
    
    private List<BillDetail> getBillDetails(int billId) throws SQLException {
        List<BillDetail> details = new ArrayList<>();
        String sql = "SELECT * FROM bill_details WHERE bill_id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, billId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                details.add(mapBillDetail(rs));
            }
        }
        return details;
    }
    
    public boolean updateBillStatus(String billCode, String status) {
        String sql = "UPDATE bills SET status = ? WHERE bill_code = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            stmt.setString(2, billCode);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating bill status: " + e.getMessage());
            throw new RuntimeException("Error updating bill status", e);
        }
    }
    
    public List<Bill> getBillsByStaffId(int staffId) {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT b.id as bill_id, b.bill_code, b.staff_id, b.customer_name, " +
                     "b.customer_phone, b.subtotal, b.tax, b.total, b.status, b.created_at, " +
                     "bd.id as detail_id, bd.service_id, bd.service_name, bd.price " +
                     "FROM bills b " +
                     "LEFT JOIN bill_details bd ON b.id = bd.bill_id " +
                     "WHERE b.staff_id = ? " +
                     "ORDER BY b.created_at DESC";
        
        try (Connection conn = getConnection()) {
            Map<Integer, Bill> billMap = new HashMap<>();
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, staffId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                int billId = rs.getInt("bill_id");
                Bill bill = billMap.get(billId);
                
                if (bill == null) {
                    bill = new Bill();
                    bill.setId(billId);
                    bill.setBillCode(rs.getString("bill_code"));
                    bill.setStaffId(rs.getInt("staff_id"));
                    bill.setCustomerName(rs.getString("customer_name"));
                    bill.setCustomerPhone(rs.getString("customer_phone"));
                    bill.setSubtotal(rs.getDouble("subtotal"));
                    bill.setTax(rs.getDouble("tax"));
                    bill.setTotal(rs.getDouble("total"));
                    bill.setStatus(rs.getString("status"));
                    bill.setCreatedAt(rs.getTimestamp("created_at"));
                    bill.setBillDetails(new ArrayList<>());
                    billMap.put(billId, bill);
                    bills.add(bill);
                }
                
                // Add bill details if they exist
                if (rs.getInt("detail_id") != 0) {
                    BillDetail detail = new BillDetail();
                    detail.setId(rs.getInt("detail_id"));
                    detail.setBillId(billId);
                    detail.setServiceId(rs.getInt("service_id"));
                    detail.setServiceName(rs.getString("service_name"));
                    detail.setPrice(rs.getDouble("price"));
                    bill.getBillDetails().add(detail);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting bills: " + e.getMessage());
            throw new RuntimeException("Error getting bills", e);
        }
        return bills;
    }
    
    public List<Bill> getBillsByDateRange(Date startDate, Date endDate) {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM bills WHERE created_at BETWEEN ? AND ? ORDER BY created_at DESC";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setTimestamp(1, new Timestamp(startDate.getTime()));
            stmt.setTimestamp(2, new Timestamp(endDate.getTime()));
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Bill bill = mapBill(rs);
                bill.setBillDetails(getBillDetails(bill.getId()));
                bills.add(bill);
            }
        } catch (SQLException e) {
            System.err.println("Error getting bills by date range: " + e.getMessage());
            throw new RuntimeException("Error getting bills by date range", e);
        }
        return bills;
    }
    
    private Bill mapBill(ResultSet rs) throws SQLException {
        Bill bill = new Bill();
        bill.setId(rs.getInt("id"));
        bill.setBillCode(rs.getString("bill_code"));
        bill.setStaffId(rs.getInt("staff_id"));
        bill.setCustomerName(rs.getString("customer_name"));
        bill.setCustomerPhone(rs.getString("customer_phone"));
        bill.setSubtotal(rs.getDouble("subtotal"));
        bill.setTax(rs.getDouble("tax"));
        bill.setTotal(rs.getDouble("total"));
        bill.setStatus(rs.getString("status"));
        bill.setCreatedAt(rs.getTimestamp("created_at"));
        return bill;
    }
    
    private BillDetail mapBillDetail(ResultSet rs) throws SQLException {
        BillDetail detail = new BillDetail();
        detail.setId(rs.getInt("id"));
        detail.setBillId(rs.getInt("bill_id"));
        detail.setServiceId(rs.getInt("service_id"));
        detail.setServiceName(rs.getString("service_name"));
        detail.setPrice(rs.getDouble("price"));
        return detail;
    }
} 