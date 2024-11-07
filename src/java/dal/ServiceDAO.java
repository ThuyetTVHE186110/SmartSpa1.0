/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.Service;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ThuyetTVHE186110
 */
public class ServiceDAO extends DBContext {

    private static final String INSERT_SERVICE_SQL = "INSERT INTO services (name, price, duration, description, image, category, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_SERVICE_BY_ID = "SELECT * FROM services WHERE id = ?";
    private static final String SELECT_ALL_SERVICES = "SELECT * FROM services";
    private static final String UPDATE_SERVICE_SQL = "UPDATE services SET name = ?, price = ?, duration = ?, description = ?, image = ?, category = ?, status = ? WHERE id = ?";
    private static final String DELETE_SERVICE_SQL = "DELETE FROM services WHERE id = ?";

    public ServiceDAO() {
    }

    // Insert a new service into the database
    public void addService(Service service) throws SQLException {
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SERVICE_SQL)) {
            preparedStatement.setString(1, service.getName());
            preparedStatement.setInt(2, service.getPrice());
            preparedStatement.setInt(3, service.getDuration());
            preparedStatement.setString(4, service.getDescription());
            preparedStatement.setString(5, service.getImage());
            preparedStatement.setString(6, service.getCategory());
            preparedStatement.setString(7, service.getStatus() != null ? service.getStatus() : "ACTIVE");
            preparedStatement.executeUpdate();
        }
    }

    // Select a service by its ID
    public Service selectService(int id) throws SQLException {
        Service service = null;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SERVICE_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                service = extractServiceFromResultSet(rs);
            }
        }
        return service;
    }

    // Select all services
    public List<Service> selectAllServices() throws SQLException {
        return selectAllServices(0, Integer.MAX_VALUE);
    }

    public List<Service> selectAllServices(int offset, int limit) throws SQLException {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT * FROM (SELECT *, ROW_NUMBER() OVER (ORDER BY id) as RowNum FROM services) AS tmp "
                + "WHERE RowNum BETWEEN ? AND ?";

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, offset + 1);
            preparedStatement.setInt(2, offset + limit);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                services.add(extractServiceFromResultSet(rs));
            }
        }
        return services;
    }

    public static void main(String args[]) {
        ServiceDAO serviceDAO = new ServiceDAO();
        List<Service> services = null;
        try {
            services = serviceDAO.selectAllServices();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(Service service: services){
            System.out.println(services);
        }
        
        }
    

    // Get services by category using SQL Server syntax
    public List<Service> getServicesByCategory(String category) throws SQLException {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT * FROM services WHERE category = ? AND status = 'ACTIVE'";

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, category);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                services.add(extractServiceFromResultSet(rs));
            }
        }
        return services;
    }

    // Update a service
    public boolean updateService(Service service) throws SQLException {
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SERVICE_SQL)) {
            preparedStatement.setString(1, service.getName());
            preparedStatement.setInt(2, service.getPrice());
            preparedStatement.setInt(3, service.getDuration());
            preparedStatement.setString(4, service.getDescription());
            preparedStatement.setString(5, service.getImage());
            preparedStatement.setString(6, service.getCategory());
            preparedStatement.setString(7, service.getStatus());
            preparedStatement.setInt(8, service.getId());
            return preparedStatement.executeUpdate() > 0;
        }
    }

    // Delete a service
    public boolean deleteService(int id) throws SQLException {
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SERVICE_SQL)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    // Search services with pagination using SQL Server syntax
    public List<Service> searchServices(String searchQuery, int offset, int limit) throws SQLException {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT * FROM (SELECT *, ROW_NUMBER() OVER (ORDER BY id) as RowNum FROM services "
                + "WHERE name LIKE ? OR description LIKE ?) AS tmp "
                + "WHERE RowNum BETWEEN ? AND ?";

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + searchQuery + "%");
            preparedStatement.setString(2, "%" + searchQuery + "%");
            preparedStatement.setInt(3, offset + 1);
            preparedStatement.setInt(4, offset + limit);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                services.add(extractServiceFromResultSet(rs));
            }
        }
        return services;
    }

    // Get total count for search results
    public int getTotalSearchResults(String searchQuery) throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM services WHERE name LIKE ? OR description LIKE ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + searchQuery + "%");
            preparedStatement.setString(2, "%" + searchQuery + "%");
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }

    // Helper method to extract service from ResultSet
    private Service extractServiceFromResultSet(ResultSet rs) throws SQLException {
        Service service = new Service();
        service.setId(rs.getInt("id"));
        service.setName(rs.getString("name"));
        service.setPrice(rs.getInt("price"));
        service.setDuration(rs.getInt("duration"));
        service.setDescription(rs.getString("description"));
        service.setImage(rs.getString("image"));
        service.setCategory(rs.getString("category"));
        service.setStatus(rs.getString("status"));
        return service;
    }

    // Add method to update service status
    public boolean updateServiceStatus(int serviceId, String status) throws SQLException {
        String sql = "UPDATE services SET status = ? WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, serviceId);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    // Add method to get services by status
    public List<Service> getServicesByStatus(String status) throws SQLException {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT * FROM services WHERE status = ?";

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, status);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                services.add(extractServiceFromResultSet(rs));
            }
        }
        return services;
    }

    public List<Service> getServicesByStatusPaginated(String status, int offset, int limit) throws SQLException {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT * FROM (SELECT *, ROW_NUMBER() OVER (ORDER BY id) as RowNum FROM services "
                + "WHERE status = ?) AS tmp WHERE RowNum BETWEEN ? AND ?";

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, offset + 1);
            preparedStatement.setInt(3, offset + limit);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                services.add(extractServiceFromResultSet(rs));
            }
        }
        return services;
    }

    public int getTotalServicesByStatus(String status) throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM services WHERE status = ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, status);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }

    public List<Service> getServicesWithPagination(int offset, int limit) throws SQLException {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT * FROM (SELECT *, ROW_NUMBER() OVER (ORDER BY id) as RowNum FROM services) AS tmp "
                + "WHERE RowNum BETWEEN ? AND ?";

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, offset + 1);
            preparedStatement.setInt(2, offset + limit);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                services.add(extractServiceFromResultSet(rs));
            }
        }
        return services;
    }

    public int getTotalServices() throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM services";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }

}
