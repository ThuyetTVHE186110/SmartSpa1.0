package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Payment; // Assuming you have a Payment model class

public class PaymentDAO extends DBContext {

    // Method to save payment information
    public void savePayment(Payment payment) {
        String sql = "INSERT INTO payments (transaction_id, order_code, amount, currency, status, description, PersonID) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, payment.getTransactionId());
            stmt.setString(2, payment.getOrderCode());
            stmt.setFloat(3, (float) payment.getAmount());
            stmt.setString(4, payment.getCurrency());
            stmt.setString(5, payment.getStatus());
            stmt.setString(6, payment.getDescription());
            stmt.setInt(7, payment.getPersonId());
            
            stmt.executeUpdate();
            System.out.println("Payment saved successfully for order: " + payment.getOrderCode());
        } catch (SQLException e) {
            System.err.println("Error saving payment: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error saving payment: " + e.getMessage(), e);
        }
    }

    // Method to get all payments
    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payments ORDER BY created_at DESC";
        try (Connection conn = DBContext.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                payments.add(mapPayment(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving payments: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error retrieving payments: " + e.getMessage(), e);
        }
        return payments;
    }

    // Get payment by transaction ID
    public Payment getPaymentByTransactionId(String transactionId) {
        String sql = "SELECT * FROM payments WHERE transaction_id = ?";
        try (Connection conn = DBContext.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, transactionId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapPayment(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving payment: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error retrieving payment: " + e.getMessage(), e);
        }
        return null;
    }

    // Update payment status
    public boolean updatePaymentStatus(String transactionId, String status) {
        String sql = "UPDATE payments SET status = ? WHERE transaction_id = ?";
        try (Connection conn = DBContext.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setString(2, transactionId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Payment status updated successfully: " + transactionId + " -> " + status);
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error updating payment status: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error updating payment status: " + e.getMessage(), e);
        }
    }

    // Delete payment
    public boolean deletePayment(String transactionId) {
        String sql = "DELETE FROM payments WHERE transaction_id = ?";
        try (Connection conn = DBContext.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, transactionId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting payment: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error deleting payment: " + e.getMessage(), e);
        }
    }

    // Add method to get payments by person ID
    public List<Payment> getPaymentsByPersonId(int personId) {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payments WHERE PersonID = ? ORDER BY created_at DESC";
        
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, personId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                payments.add(mapPayment(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting payments: " + e.getMessage());
            throw new RuntimeException("Error retrieving payments", e);
        }
        return payments;
    }

    

    // Test the database connection
    public static void main(String[] args) {
        PaymentDAO dao = new PaymentDAO();
        
        // Test saving a payment
        Payment testPayment = new Payment();
        testPayment.setTransactionId("TEST-" + System.currentTimeMillis());
        testPayment.setOrderCode("ORDER-123");
        testPayment.setAmount(1000);
        testPayment.setCurrency("VND");
        testPayment.setStatus("PENDING");
        testPayment.setDescription("Test payment");
        
        try {
            dao.savePayment(testPayment);
            System.out.println("Test payment saved successfully");
            
            // Retrieve and verify
            Payment saved = dao.getPaymentByTransactionId(testPayment.getTransactionId());
            if (saved != null) {
                System.out.println("Retrieved payment: " + saved.getTransactionId());
            }
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Add these new methods to your existing PaymentDAO class

    public boolean createPayment(Payment payment) {
        String sql = "INSERT INTO payments (transaction_id, order_code, amount, currency, status, description, PersonID, created_at) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, payment.getTransactionId());
            stmt.setString(2, payment.getOrderCode());
            stmt.setDouble(3, payment.getAmount());
            stmt.setString(4, payment.getCurrency());
            stmt.setString(5, payment.getStatus());
            stmt.setString(6, payment.getDescription());
            stmt.setInt(7, payment.getPersonId());
            stmt.setTimestamp(8, new java.sql.Timestamp(payment.getCreatedAt().getTime()));
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error creating payment: " + e.getMessage());
            throw new RuntimeException("Error creating payment", e);
        }
    }

    public boolean updatePayment(Payment payment) {
        String sql = "UPDATE payments SET status = ?, description = ? WHERE transaction_id = ?";
        try (Connection conn = DBContext.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, payment.getStatus());
            stmt.setString(2, payment.getDescription());
            stmt.setString(3, payment.getTransactionId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating payment: " + e.getMessage());
            throw new RuntimeException("Error updating payment", e);
        }
    }

    public List<Payment> searchPayments(String searchTerm) {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payments WHERE transaction_id LIKE ? OR order_code LIKE ? OR description LIKE ?";
        
        try (Connection conn = DBContext.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String searchPattern = "%" + searchTerm + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    payments.add(mapPayment(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching payments: " + e.getMessage());
            throw new RuntimeException("Error searching payments", e);
        }
        return payments;
    }

    public List<Payment> getPaymentsByStatus(String status) {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payments WHERE status = ? ORDER BY created_at DESC";
        
        try (Connection conn = DBContext.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    payments.add(mapPayment(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting payments by status: " + e.getMessage());
            throw new RuntimeException("Error getting payments by status", e);
        }
        return payments;
    }

    public List<Payment> getPaymentsByPeriod(String period) {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payments WHERE created_at >= ? ORDER BY created_at DESC";
        
        try (Connection conn = DBContext.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            java.sql.Timestamp timestamp = null;
            switch (period) {
                case "today":
                    timestamp = java.sql.Timestamp.valueOf(java.time.LocalDate.now().atStartOfDay());
                    break;
                case "week":
                    timestamp = java.sql.Timestamp.valueOf(java.time.LocalDate.now().minusWeeks(1).atStartOfDay());
                    break;
                case "month":
                    timestamp = java.sql.Timestamp.valueOf(java.time.LocalDate.now().minusMonths(1).atStartOfDay());
                    break;
                default:
                    return getAllPayments();
            }
            
            stmt.setTimestamp(1, timestamp);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    payments.add(mapPayment(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting payments by period: " + e.getMessage());
            throw new RuntimeException("Error getting payments by period", e);
        }
        return payments;
    }

    // Update the mapPayment method to include all fields
    private Payment mapPayment(ResultSet rs) throws SQLException {
        Payment payment = new Payment();
        payment.setId(rs.getInt("id"));
        payment.setTransactionId(rs.getString("transaction_id"));
        payment.setOrderCode(rs.getString("order_code"));
        payment.setAmount(rs.getDouble("amount"));
        payment.setCurrency(rs.getString("currency"));
        payment.setStatus(rs.getString("status"));
        payment.setDescription(rs.getString("description"));
        payment.setPersonId(rs.getInt("PersonID"));
        payment.setCreatedAt(rs.getTimestamp("created_at"));
        return payment;
    }
    // Add these methods to your PaymentDAO class

    public List<Payment> getPaymentsWithPagination(int page, int pageSize, String status, String period, String search) {
        List<Payment> payments = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT * FROM (SELECT *, ROW_NUMBER() OVER (ORDER BY created_at DESC) AS RowNum FROM payments WHERE 1=1");
        List<Object> params = new ArrayList<>();
        
        // Add filters
        if (status != null && !status.isEmpty()) {
            sql.append(" AND status = ?");
            params.add(status);
        }
        
        if (period != null && !period.isEmpty()) {
            sql.append(" AND created_at >= ?");
            java.sql.Timestamp timestamp = getTimestampForPeriod(period);
            if (timestamp != null) {
                params.add(timestamp);
            }
        }
        
        if (search != null && !search.trim().isEmpty()) {
            sql.append(" AND (transaction_id LIKE ? OR order_code LIKE ? OR description LIKE ?)");
            String searchPattern = "%" + search.trim() + "%";
            params.add(searchPattern);
            params.add(searchPattern);
            params.add(searchPattern);
        }
        
        // Close the subquery and add pagination
        sql.append(") AS PaymentWithRowNum WHERE RowNum BETWEEN ? AND ?");
        int start = (page - 1) * pageSize + 1;
        int end = page * pageSize;
        params.add(start);
        params.add(end);
        
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            
            // Set parameters
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    payments.add(mapPayment(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting payments with pagination: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error getting payments with pagination", e);
        }
        return payments;
    }

    public int getTotalPayments(String status, String period, String search) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) AS total FROM payments WHERE 1=1");
        List<Object> params = new ArrayList<>();
        
        if (status != null && !status.isEmpty()) {
            sql.append(" AND status = ?");
            params.add(status);
        }
        
        if (period != null && !period.isEmpty()) {
            sql.append(" AND created_at >= ?");
            java.sql.Timestamp timestamp = getTimestampForPeriod(period);
            if (timestamp != null) {
                params.add(timestamp);
            }
        }
        
        if (search != null && !search.trim().isEmpty()) {
            sql.append(" AND (transaction_id LIKE ? OR order_code LIKE ? OR description LIKE ?)");
            String searchPattern = "%" + search.trim() + "%";
            params.add(searchPattern);
            params.add(searchPattern);
            params.add(searchPattern);
        }
        
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting total payments: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error getting total payments", e);
        }
        return 0;
    }

    private java.sql.Timestamp getTimestampForPeriod(String period) {
        java.time.LocalDateTime dateTime = null;
        switch (period) {
            case "today":
                dateTime = java.time.LocalDate.now().atStartOfDay();
                break;
            case "week":
                dateTime = java.time.LocalDate.now().minusWeeks(1).atStartOfDay();
                break;
            case "month":
                dateTime = java.time.LocalDate.now().minusMonths(1).atStartOfDay();
                break;
            default:
                return null;
        }
        return java.sql.Timestamp.valueOf(dateTime);
    }
}
