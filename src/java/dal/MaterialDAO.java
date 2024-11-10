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
import model.Material;
import model.Supplier;

public class MaterialDAO extends DBContext {

    static final Logger logger = Logger.getLogger(MaterialDAO.class.getName());

    public MaterialDAO() {
    }

    public List<Material> getAllMaterials() {
        List<Material> materials = new ArrayList<>();
        String sql = "SELECT * FROM Material";

        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Material material = new Material();
                material.setId(rs.getInt("ID"));
                material.setName(rs.getString("Name"));
                material.setPrice(rs.getInt("Price"));
                material.setImage(rs.getString("Image"));
                material.setDescription(rs.getString("Description"));
                material.setStatus(rs.getString("Status"));
                materials.add(material);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materials;
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

    public Material getMaterialByID(int materialID) {
        Material material = null;
        try (Connection connection = DBContext.getConnection()) {
            String sql = """
                     SELECT p.*, s.ID as SupplierID, s.Name as SupplierName, s.Address as SupplierAddress
                     FROM Material p
                     INNER JOIN Supplier s ON p.SupplierID = s.ID                      
                     WHERE p.ID = ?""";

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, materialID);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        material = new Material();
                        material.setId(rs.getInt("ID"));
                        material.setName(rs.getString("Name"));
                        material.setPrice(rs.getInt("Price"));
                        material.setImage(rs.getString("Image"));
                        material.setDescription(rs.getString("Description"));

                        // Lưu thông tin nhà cung cấp
                        Supplier supplier = new Supplier();
                        supplier.setId(rs.getInt("SupplierID")); // Lưu ID của nhà cung cấp
                        supplier.setName(rs.getString("SupplierName"));
                        supplier.setAddress(rs.getString("SupplierAddress"));
                        material.setSupplierInfo(supplier);

                        // Nếu bạn có status trong bảng Material
                        material.setStatus(rs.getString("Status")); // Lưu status nếu có
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return material;
    }

    public int count(String txtSearch) {
        String searchMaterial = "SELECT COUNT(*) FROM Material p "
                + "WHERE p.Name LIKE ?";
        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(searchMaterial)) {
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

    public List<Material> search(String txtSearch, int index) {
        List<Material> materialList = new ArrayList<>();
        String sql = "SELECT * FROM ("
                + "SELECT ROW_NUMBER() OVER (ORDER BY p.ID ASC) AS r, p.* "
                + "FROM Material p "
                + "WHERE p.Name LIKE ?) AS x "
                + "WHERE r BETWEEN ? AND ?";
        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + txtSearch + "%");
            pstmt.setInt(2, (index - 1) * 6 + 1);  // Start of pagination
            pstmt.setInt(3, index * 6);  // End of pagination

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Material material = new Material();
                material.setId(rs.getInt("ID"));
                material.setName(rs.getString("Name"));
                material.setPrice(rs.getInt("Price"));
                material.setImage(rs.getString("Image"));
                material.setDescription(rs.getString("Description"));
                material.setStatus(rs.getString("Status"));
                materialList.add(material);
            }
            return materialList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteMaterial(int materialID) throws SQLException {
        String deleteMaterial = "DELETE FROM Material WHERE ID = ?";
        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(deleteMaterial)) {

            pstmt.setInt(1, materialID);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMaterial(String name, String description, int price,
            String image, int supplierId, String status) {
        String sql = "INSERT INTO Material (Name, Description, Price, Image, SupplierID, Status) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        System.out.println("SQL: " + sql);

        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setInt(3, price);
            pstmt.setString(4, image);
            pstmt.setInt(5, supplierId);
            pstmt.setString(6, status);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Material added successfully.");
            } else {
                System.out.println("No material was added.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Có thể ném ngoại lệ hoặc xử lý tiếp tuỳ theo nhu cầu
        }
        // Không cần finally để đóng pstmt và connection vì chúng đã được quản lý bởi try-with-resources
    }

    public void updateMaterial(int id, String name, String description, int price,
            String image, int supplierId, String status) throws SQLException {
//        String sql = "UPDATE Material SET Name = ?, Description = ?, Price = ?, Image = ?, SupplierID = ?, Status = ? WHERE ID = ?";
//        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
//            pstmt.setString(1, name);
//            pstmt.setString(2, description);
//            pstmt.setInt(3, price);
//            pstmt.setString(4, image);
//            pstmt.setInt(5, supplierId);
//            pstmt.setString(6, status);
//            pstmt.setInt(7, id); // Đặt materialId ở vị trí cuối cùng
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    
String sql;
        boolean updateImage = image != null && !image.isEmpty();

        if (updateImage) {
            sql = "UPDATE Product SET Name = ?, Price = ?, SupplierID = ?"
                    + "Image = ?, Description = ?, Status = ?"
                    + "WHERE ID = ?";
        } else {
            sql = "UPDATE Product SET Name = ?, Price = ?,SupplierID = ?"
                    + "Description = ?, Status = ?"
                    + "WHERE ID = ?";
        }

        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {

            // Set parameters for fields that are always updated
            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setInt(3, price);
        

            if (updateImage) {
                pstmt.setString(3, image);
                pstmt.setInt(4, supplierId);
                pstmt.setString(5, status);
               
            } else {
                 pstmt.setInt(3, supplierId);
                pstmt.setString(4, status);
            }

            pstmt.executeUpdate();
        } catch (SQLException e) {
                    throw new SQLException("Failed to add product: " + e.getMessage());  // Ném ngoại lệ nếu có lỗi

        }
    }


    public boolean isMaterialNameExists(String name) {
        String sql = "SELECT COUNT(*) FROM Material WHERE Name = ?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;  // Nếu có ít nhất 1 sản phẩm trùng tên, trả về true
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isMaterialNameExistsForEdit(int materialId, String name) {
        String sql = "SELECT COUNT(*) FROM Material WHERE Name = ? AND ID != ?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, materialId);  // Kiểm tra trùng tên nhưng loại trừ sản phẩm hiện tại
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        MaterialDAO materialDAO = new MaterialDAO();
        logger.log(Level.INFO, "First material: {0}", materialDAO.getAllMaterials().get(0));
        logger.log(Level.INFO, "Material with ID 1: {0}", materialDAO.getMaterialByID(1));
        int count = materialDAO.count("a");
        System.out.println(count);
        //materialDAO.addMaterial("Sản phẩm A", "Mô tả sản phẩm A", 100000, 10, "image_url.jpg", 1, 1, 2, "Chi nhánh 1");

    }
}
