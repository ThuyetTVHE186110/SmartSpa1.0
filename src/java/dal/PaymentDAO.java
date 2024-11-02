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

    // Helper method to map ResultSet to Payment object
    private Payment mapPayment(ResultSet rs) throws SQLException {
        Payment payment = new Payment();
        payment.setId(rs.getInt("id"));
        payment.setOrderCode(rs.getString("order_code"));
        payment.setAmount(rs.getFloat("amount"));
        payment.setCurrency(rs.getString("currency"));
        payment.setStatus(rs.getString("status"));
        payment.setDescription(rs.getString("description"));
        payment.setPersonId(rs.getInt("PersonID"));
        return payment;
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
}
