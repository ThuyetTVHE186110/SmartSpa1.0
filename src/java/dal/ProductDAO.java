package dal;

import static dal.DBContext.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Product;
import model.Discount;
import model.Supplier;

public class ProductDAO extends DBContext {

    static final Logger logger = Logger.getLogger(ProductDAO.class.getName());

    public ProductDAO() {
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.*, c.Name AS CategoryName\n"
                + "                 FROM Product p \n"
                + "                 JOIN Category c ON p.CategoryID = c.ID";

        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("ID"));
                product.setName(rs.getString("Name"));
                product.setPrice(rs.getInt("Price"));
                product.setImage(rs.getString("Image"));
                product.setQuantity(rs.getInt("Quantity"));
                product.setBranchName(rs.getString("BranchName"));
                product.setDescription(rs.getString("Description"));
                product.setCategory(rs.getString("CategoryName"));
                product.setStatus(rs.getString("Status"));
                product.setIngredient(rs.getString("Ingredient"));
                product.setHowToUse(rs.getString("HowToUse"));
                product.setBenefit(rs.getString("Benefit"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<Supplier> getAllSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM Supplier";

        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Supplier supplier = new Supplier();
                supplier.setId(rs.getInt("ID"));
                supplier.setName(rs.getString("Name"));
                suppliers.add(supplier); // Thêm nhà cung cấp vào danh sách
                System.out.println("Supplier ID: " + supplier.getId() + ", Name: " + supplier.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }

   
    public Product getProductByID(int productID) {
        Product product = null;
        try (Connection connection = DBContext.getConnection()) {
            String sql = """
                         SELECT p.*, d.DiscountPercent, s.Name as SupplierName, s.Address as SupplierAddress, c.Name as CategoryName
                         FROM Product p
                         LEFT JOIN Discount d ON p.DiscountID = d.DiscountID
                         INNER JOIN Supplier s ON p.SupplierID = s.ID
                         INNER JOIN Category c ON p.CategoryID = c.ID
                         WHERE p.ID = ?""";

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, productID);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        product = new Product();
                        product.setId(rs.getInt("ID"));
                        product.setName(rs.getString("Name"));
                        product.setPrice(rs.getInt("Price"));
                        product.setQuantity(rs.getInt("Quantity"));
                        product.setImage(rs.getString("Image"));

                        Discount discount = new Discount();
                        discount.setDiscountPercent(rs.getInt("DiscountPercent"));
                        product.setDiscountInfo(discount);

                        Supplier supplier = new Supplier();
                        supplier.setName(rs.getString("SupplierName"));
                        supplier.setAddress(rs.getString("SupplierAddress"));
                        product.setSupplierInfo(supplier);

                        product.setCategory(rs.getString("CategoryName"));
                        product.setBranchName(rs.getString("BranchName"));
                        product.setDescription(rs.getString("Description"));
                        product.setIngredient(rs.getString("Ingredient"));
                        product.setHowToUse(rs.getString("HowToUse"));
                        product.setBenefit(rs.getString("Benefit"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;

    }

    public int count(String txtSearch) {
        String searchProduct = "SELECT COUNT(*) FROM Product p "
                + "JOIN Category c ON p.CategoryID = c.ID "
                + "WHERE p.Name LIKE ? OR c.Name LIKE ?";
        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(searchProduct)) {
            pstmt.setString(1, "%" + txtSearch + "%");
            pstmt.setString(2, "%" + txtSearch + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Product> search(String txtSearch, int index) {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT * FROM ("
                + "SELECT ROW_NUMBER() OVER (ORDER BY p.ID ASC) AS r, p.*, c.Name AS CategoryName "
                + "FROM Product p "
                + "JOIN Category c ON p.CategoryID = c.ID "
                + "WHERE p.Name LIKE ? OR c.Name LIKE ?) AS x "
                + "WHERE r BETWEEN ? AND ?";
        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + txtSearch + "%");
            pstmt.setString(2, "%" + txtSearch + "%");
            pstmt.setInt(3, (index - 1) * 6 + 1);  // Bắt đầu phân trang
            pstmt.setInt(4, index * 6);  // Kết thúc phân trang

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("ID"));
                product.setName(rs.getString("Name"));
                product.setPrice(rs.getInt("Price"));
                product.setImage(rs.getString("Image"));
                product.setQuantity(rs.getInt("Quantity"));
                product.setBranchName(rs.getString("BranchName"));
                product.setDescription(rs.getString("Description"));
                product.setCategory(rs.getString("CategoryName"));
                product.setStatus(rs.getString("Status"));
                product.setIngredient(rs.getString("Ingredient"));
                product.setHowToUse(rs.getString("HowToUse"));
                product.setBenefit(rs.getString("Benefit"));
                productList.add(product);
            }
            return productList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteProduct(int productID) throws SQLException {
        String deleteProduct = "DELETE FROM Product WHERE ID = ?";
        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(deleteProduct)) {

            pstmt.setInt(1, productID);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addProduct(String name, String description, int price, int quantity,
            String image, int categoryId, int supplierId, String branchName, String status, String ingredient, String howToUse, String benefit) {
        String sql = "INSERT INTO Product (Name, Description, Price, Quantity, Image, CategoryID, SupplierID, BranchName, Status, Ingredient, HowToUse, Benefit) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        System.out.println("SQL: " + sql);

        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setInt(3, price);
            pstmt.setInt(4, quantity);
            pstmt.setString(5, image);
            pstmt.setInt(6, categoryId);
            pstmt.setInt(7, supplierId);
            pstmt.setString(8, branchName);
            pstmt.setString(9, status);
            pstmt.setString(10, ingredient);
            pstmt.setString(11, howToUse);
            pstmt.setString(12, benefit);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Product added successfully.");
            } else {
                System.out.println("No product was added.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Có thể ném ngoại lệ hoặc xử lý tiếp tuỳ theo nhu cầu
        }
        // Không cần finally để đóng pstmt và connection vì chúng đã được quản lý bởi try-with-resources
    }

    public void updateProduct(int productId, String name, String description, int price,
            int quantity, String image, int categoryId, int supplierId, String branchName, String status, String ingredient, String howToUse, String benefit) {
        String sql = "UPDATE Product "
                + "SET Name = ?, "
                + "    Price = ?, "
                + "    Quantity = ?, "
                + "    SupplierID = ?, "
                + "    CategoryID = ?, "
                + "    BranchName = ?, "
                + "    Image = ?, "
                + "    Description = ?, "
                + "    Status = ?, "
                + "    Ingredient = ?, "
                + "    HowToUse = ?, "
                + "    Benefit = ? "
                + "WHERE ID = ?";

        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, price);
            pstmt.setInt(3, quantity);
            pstmt.setInt(4, supplierId);
            pstmt.setInt(5, categoryId);
            pstmt.setString(6, branchName);
            pstmt.setString(7, image);
            pstmt.setString(8, description);
            pstmt.setString(9, status);
            pstmt.setString(10, ingredient);
            pstmt.setString(11, howToUse);
            pstmt.setString(12, benefit);
            pstmt.setInt(13, productId); // Đặt productId ở vị trí cuối cùng
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO();
//        logger.log(Level.INFO, "First product: {0}", productDAO.getAllProducts().get(0));
//        logger.log(Level.INFO, "Product with ID 1: {0}", productDAO.getProductByID(1));
//        int count = productDAO.count("a");
//        System.out.println(count);
        //productDAO.addProduct("Sản phẩm A", "Mô tả sản phẩm A", 100000, 10, "image_url.jpg", 1, 1, 2, "Chi nhánh 1");
        List<Supplier> suppliers = productDAO.getAllSuppliers();
        if (suppliers.isEmpty()) {
            System.out.println("No suppliers found!");
        }

    }
}
