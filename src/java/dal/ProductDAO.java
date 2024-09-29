package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Product;
import model.Discount;
import model.Supplier;

public class ProductDAO extends DBContext{

    public ProductDAO() {
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String sql = """
                         SELECT *from Product""";
            PreparedStatement pstmt = connection.prepareStatement(sql); 
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
            String sql = "SELECT p.*, d.DiscountPercent, s.Name as SupplierName, s.Address as SupplierAddress, c.Name as CategoryName\n"
                    + "FROM Product p\n"
                    + "LEFT JOIN Discount d ON p.DiscountID = d.DiscountID\n"
                    + "INNER JOIN Supplier s ON p.SupplierID = s.ID\n"
                    + "INNER JOIN Category c ON p.CategoryID = c.ID\n"
                    + "WHERE p.ID = ?";

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
    public static void main(String args){
        ProductDAO o =new ProductDAO();
        System.out.println(o.getAllProducts().get(0));
        System.out.println(o.getProductByID(1));
    }
}
