package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.CartItem;
import model.Product;
import model.Service;

public class CartDAO extends DBContext {

    public CartDAO() {
    }

    public List<CartItem> getCartItems(int personId) {
        List<CartItem> cartItems = new ArrayList<>();
        String sql = "SELECT c.ID, c.PersonID, c.ProductID, c.ProductQuantity, "
                + "p.Name AS ProductName, p.Price, p.Image "
                + "FROM Cart c "
                + "JOIN Product p ON c.ProductID = p.ID "
                + "WHERE c.PersonID = ? AND c.ProductID IS NOT NULL AND c.ProductQuantity > 0";

        try (Connection conn = getConnection(); PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, personId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                CartItem item = new CartItem();
                item.setId(rs.getInt("ID"));
                item.setPersonId(rs.getInt("PersonID"));
                item.setProductId(rs.getInt("ProductID"));
                item.setProductQuantity(rs.getInt("ProductQuantity"));
                item.setProductName(rs.getString("ProductName"));
                item.setPrice(rs.getInt("Price"));
                item.setImage(rs.getString("Image"));
                cartItems.add(item);
            }
        } catch (SQLException e) {
            System.err.println("Error getting cart items: " + e.getMessage());
            e.printStackTrace();
        }
        return cartItems;
    }

    public void addToCart(CartItem item) {
        // Check for existing item
        String checkSql = "SELECT ID, ProductQuantity FROM Cart WHERE PersonID = ? AND ProductID = ?";
        String updateSql = "UPDATE Cart SET ProductQuantity = ? WHERE ID = ?";
        String insertSql = "INSERT INTO Cart (PersonID, ProductID, ServiceID, ProductQuantity, ServiceQuantity) "
                + "VALUES (?, ?, NULL, ?, NULL)";

        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            // First check if item exists
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, item.getPersonId());
                checkStmt.setInt(2, item.getProductId());
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    // Update existing item
                    int existingId = rs.getInt("ID");
                    int currentQuantity = rs.getInt("ProductQuantity");
                    int newQuantity = currentQuantity + item.getProductQuantity();

                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, newQuantity);
                        updateStmt.setInt(2, existingId);
                        updateStmt.executeUpdate();
                    }
                } else {
                    // Insert new item
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                        insertStmt.setInt(1, item.getPersonId());
                        insertStmt.setInt(2, item.getProductId());
                        insertStmt.setInt(3, item.getProductQuantity());
                        insertStmt.executeUpdate();
                    }
                }
            }

            conn.commit();
            System.out.println("Successfully added/updated cart item");

        } catch (SQLException e) {
            System.err.println("Error in addToCart: " + e.getMessage());
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw new RuntimeException("Error adding item to cart", e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void updateQuantity(int cartItemId, int quantity, boolean isProduct) {
        String sql = "UPDATE Cart SET ProductQuantity = ? WHERE ID = ? AND ProductID IS NOT NULL";

        try (Connection conn = getConnection(); PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, quantity);
            st.setInt(2, cartItemId);
            st.executeUpdate();
            System.out.println("Successfully updated quantity for cart item: " + cartItemId);
        } catch (SQLException e) {
            System.err.println("Error updating quantity: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error updating cart item quantity", e);
        }
    }

    public void removeFromCart(int cartItemId) {
        String sql = "DELETE FROM Cart WHERE ID = ? AND ProductID IS NOT NULL";

        try (Connection conn = getConnection(); PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, cartItemId);
            st.executeUpdate();
            System.out.println("Successfully removed cart item: " + cartItemId);
        } catch (SQLException e) {
            System.err.println("Error removing cart item: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error removing item from cart", e);
        }
    }

    public void clearCart(int personId) {
        String sql = "DELETE FROM Cart WHERE PersonID = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, personId);
            ps.executeUpdate();
            System.out.println("Successfully cleared cart for person ID: " + personId);
        } catch (SQLException e) {
            System.out.println("Error clearing cart: " + e.getMessage());
            throw new RuntimeException("Error clearing cart", e);
        }
    }

    public static void main(String[] args) {
        CartDAO cartDAO = new CartDAO();

        try {
            // Cleanup before testing
            System.out.println("Cleaning up test data...");
            cartDAO.clearCart(1);

            // Test 1: Add new item
            System.out.println("\nTest 1: Adding new item to cart...");
            CartItem newItem = new CartItem();
            newItem.setPersonId(1);
            newItem.setProductId(1);
            newItem.setProductQuantity(1);
            cartDAO.addToCart(newItem);
//            
//            // Verify addition
//            List<CartItem> items = cartDAO.getCartItems(1);
//            System.out.println("Items in cart after addition: " + items.size());
//            if (!items.isEmpty()) {
//                CartItem addedItem = items.get(0);
//                System.out.println("Added item details:");
//                System.out.println("ID: " + addedItem.getId());
//                System.out.println("Product ID: " + addedItem.getProductId());
//                System.out.println("Quantity: " + addedItem.getProductQuantity());
//            }
//            
//            // Test 2: Update quantity
//            if (!items.isEmpty()) {
//                System.out.println("\nTest 2: Updating quantity...");
//                int itemId = items.get(0).getId();
//                cartDAO.updateQuantity(itemId, 3, true);
//                
//                // Verify update
//                items = cartDAO.getCartItems(1);
//                if (!items.isEmpty()) {
//                    System.out.println("New quantity: " + items.get(0).getProductQuantity());
//                    assert items.get(0).getProductQuantity() == 3 : "Quantity update failed";
//                }
//            }
//            
//            // Test 3: Add duplicate item
//            System.out.println("\nTest 3: Adding duplicate item...");
//            cartDAO.addToCart(newItem);
//            items = cartDAO.getCartItems(1);
//            if (!items.isEmpty()) {
//                System.out.println("Quantity after duplicate add: " + items.get(0).getProductQuantity());
//            }
//            
//            // Test 4: Remove item
//            if (!items.isEmpty()) {
//                System.out.println("\nTest 4: Removing item...");
//                cartDAO.removeFromCart(items.get(0).getId());
//                items = cartDAO.getCartItems(1);
//                System.out.println("Items remaining: " + items.size());
//            }
//            
//            // Test 5: Clear cart
//            System.out.println("\nTest 5: Testing clear cart...");
//            // Add a few items first
//            cartDAO.addToCart(newItem);
//            CartItem anotherItem = new CartItem();
//            anotherItem.setPersonId(1);
//            anotherItem.setProductId(2);
//            anotherItem.setProductQuantity(1);
//            cartDAO.addToCart(anotherItem);
//            
//            // Clear and verify
//            cartDAO.clearCart(1);
//            items = cartDAO.getCartItems(1);
//            System.out.println("Items after clear: " + items.size());
//            assert items.isEmpty() : "Cart clear failed";
//            
//            System.out.println("\nAll tests completed successfully!");
//            
        } catch (Exception e) {
            System.err.println("Test failed with error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<CartItem> getCartItemsByOrderCode(String orderCode) {
        List<CartItem> items = new ArrayList<>();
        String sql = "SELECT c.ID, c.ProductID, c.ServiceID, c.ProductQuantity, c.ServiceQuantity, "
                + "p.RewardPoints AS product_reward_points, s.RewardPoints AS service_reward_points, "
                + "p.Name AS product_name, p.Price AS product_price, p.Image AS product_image, "
                + "s.Name AS service_name, s.Price AS service_price, s.Image AS service_image "
                + "FROM Cart c "
                + "JOIN Orders o ON c.PersonID = o.PersonID "
                + "LEFT JOIN Product p ON c.ProductID = p.ID "
                + "LEFT JOIN Services s ON c.ServiceID = s.ID "
                + "WHERE o.order_code = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, orderCode);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CartItem item = new CartItem();
                    item.setId(rs.getInt("ID"));

                    // Kiểm tra và thiết lập sản phẩm nếu tồn tại ProductID
                    if (rs.getInt("ProductID") > 0) {
                        Product product = new Product();
                        product.setId(rs.getInt("ProductID"));
                        product.setRewardPoints(rs.getInt("product_reward_points"));
                        product.setName(rs.getString("product_name"));
                        product.setPrice(rs.getInt("product_price"));
                        product.setImage(rs.getString("product_image"));
                        item.setProduct(product);
                        item.setProductQuantity(rs.getInt("ProductQuantity"));
                    }

                    // Kiểm tra và thiết lập dịch vụ nếu tồn tại ServiceID
                    if (rs.getInt("ServiceID") > 0) {
                        Service service = new Service();
                        service.setId(rs.getInt("ServiceID"));
                        service.setRewardPoints(rs.getInt("service_reward_points"));
                        service.setName(rs.getString("service_name"));
                        service.setPrice(rs.getInt("service_price"));
                        service.setImage(rs.getString("service_image"));
                        item.setService(service);
                        item.setServiceQuantity(rs.getInt("ServiceQuantity"));
                    }

                    items.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving cart items by order code", e);
        }

        return items;
    }

}
