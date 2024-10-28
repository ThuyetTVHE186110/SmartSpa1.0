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
    String sql = "SELECT p.*, c.Name AS CategoryName\n" +
"                 FROM Product p \n" +
"                 JOIN Category c ON p.CategoryID = c.ID";

    try (Connection connection = getConnection(); 
         PreparedStatement pstmt = connection.prepareStatement(sql); 
         ResultSet rs = pstmt.executeQuery()) {

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
            products.add(product);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return products;
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
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;

    }

    public int count(String txtSearch) {
        String searchProduct = "select count(*) from Product where Name like ?";
        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(searchProduct)) {

            pstmt.setString(1, "%" + txtSearch + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
        }
        return 0;
    }

    public List<Product> search(String txtSearch, int index) {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT * FROM "
                + "(SELECT ROW_NUMBER() OVER (ORDER BY id ASC) AS r, * "
                + "FROM Product WHERE Name LIKE ?) AS x "
                + "WHERE r BETWEEN ? AND ?";
        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Đặt giá trị cho các tham số trước khi thực thi truy vấn
            pstmt.setString(1, "%" + txtSearch + "%");
            pstmt.setInt(2, (index - 1) * 3 + 1);  // Bắt đầu phân trang
            pstmt.setInt(3, index * 3);  // Kết thúc phân trang

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

    public void addProduct(String name, String description, int price, int quantity, String image, int categoryId, int supplierId, int discountId, String branchName) {
        String sql = "INSERT INTO Product (Name,"
                + " Description, "
                + "Price, "
                + "Quantity, "
                + " Image, "
                + "CategoryID,"
                + " SupplierID, "
                + "DiscountID,"
                + "BranchName) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
        System.out.println("SQL: " + sql);

        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setInt(3, price);
            pstmt.setInt(4, quantity);
            pstmt.setString(5, image);
            pstmt.setInt(6, categoryId);
            pstmt.setInt(7, supplierId);
            pstmt.setInt(8, discountId);

            pstmt.setString(9, branchName);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Không cần finally để đóng pstmt và connection vì chúng đã được quản lý bởi try-with-resources
    }

    public void updateProduct(int productId, String name, String description, int price, int quantity, String image, int categoryId, int supplierId, int discountId, String branchName,String status) {
        String sql = "UPDATE Product\n"
                + "SET Name = ?,\n"
                + "    Price = ?,\n"
                + "    Quantity = ?,\n"
                + "    DiscountID = ?,\n"
                + "    SupplierID = ?,\n"
                + "    CategoryID = ?,\n"
                + "    BranchName = ?,\n"
                + "    Image = ?,\n"
                + "    Description = ?,\n"
                + "    Status = ?\n"
                + "WHERE ID = ?";
        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setInt(2, price);
            pstmt.setInt(3, quantity);
            pstmt.setInt(4, discountId);
            pstmt.setInt(5, supplierId);
            pstmt.setInt(6, categoryId);
            pstmt.setString(7, branchName);
            pstmt.setString(8, image);
            pstmt.setString(9, description);  // Set description at the 9th position
             pstmt.setString(10, status);
            pstmt.setInt(11, productId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Không cần finally để đóng pstmt và connection vì chúng đã được quản lý bởi try-with-resources

    }

    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO();
        logger.log(Level.INFO, "First product: {0}", productDAO.getAllProducts().get(0));
        logger.log(Level.INFO, "Product with ID 1: {0}", productDAO.getProductByID(1));
        int count = productDAO.count("a");
        System.out.println(count);
        //productDAO.addProduct("Sản phẩm A", "Mô tả sản phẩm A", 100000, 10, "image_url.jpg", 1, 1, 2, "Chi nhánh 1");

    }
}
