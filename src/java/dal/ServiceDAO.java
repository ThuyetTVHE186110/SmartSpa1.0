/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.Service;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Discount;
/**
 *
 * @author ThuyetTVHE186110
 */
public class ServiceDAO extends DBContext{
    private static final String INSERT_SERVICE_SQL = "INSERT INTO services (name, price, duration, description, image) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_SERVICE_BY_ID = "SELECT * FROM services WHERE id = ?";
    private static final String SELECT_ALL_SERVICES = "SELECT * FROM services";
    private static final String UPDATE_SERVICE_SQL = "UPDATE services SET name = ?, price = ?, duration = ?, description = ?, image = ? WHERE id = ?";
    private static final String DELETE_SERVICE_SQL = "DELETE FROM services WHERE id = ?";

    public ServiceDAO() {
    }

    
    // Insert a new service into the database
    public void addService(Service service) throws SQLException {
    try (Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SERVICE_SQL)) {
            preparedStatement.setString(1, service.getName());
            preparedStatement.setInt(2, service.getPrice());
            preparedStatement.setInt(3, service.getDuration());
            preparedStatement.setString(4, service.getDescription());
            preparedStatement.setString(5, service.getImage()); // Set image link
            preparedStatement.executeUpdate();
        }
    }


    // Select a service by its ID
    public Service selectService(int id) throws SQLException {
        Service service = null;
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SERVICE_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                int duration = rs.getInt("duration");
                String description = rs.getString("description");
                String image=rs.getString("image");
                service = new Service(id, name, price,  duration, description,image);
            }
        }
        return service;
    }

    // Select all services
    public List<Service> selectAllServices() throws SQLException {
        List<Service> services = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SERVICES)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                int duration = rs.getInt("duration");
                String description = rs.getString("description");
                String image= rs.getString("image");
                services.add(new Service(id, name, price, duration, description,image));
            }
        }
        return services;
    }

    // Update a service
    public boolean updateService(Service service) throws SQLException {
    try (Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SERVICE_SQL)) {
            preparedStatement.setString(1, service.getName());
            preparedStatement.setInt(2, service.getPrice());
            preparedStatement.setInt(3, service.getDuration());
            preparedStatement.setString(4, service.getDescription());
            preparedStatement.setString(5, service.getImage()); // Update image link
            preparedStatement.setInt(6, service.getId());
            return preparedStatement.executeUpdate() > 0;
        }
    }


    // Delete a service by its ID
    public boolean deleteService(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SERVICE_SQL)) {
            preparedStatement.setInt(1, id);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public List<Service> searchServices(String searchQuery, int offset, int limit) throws SQLException {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT * FROM (SELECT *, ROW_NUMBER() OVER (ORDER BY id) AS RowNum FROM services WHERE name LIKE ? OR description LIKE ?) AS SubQuery WHERE RowNum > ? AND RowNum <= ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + searchQuery + "%");
            preparedStatement.setString(2, "%" + searchQuery + "%");
            preparedStatement.setInt(3, offset);
            preparedStatement.setInt(4, offset + limit);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                services.add(extractServiceFromResultSet(rs));
            }
        }
        return services;
    }

    public int getTotalSearchResults(String searchQuery) throws SQLException {
        String sql = "SELECT COUNT(*) FROM services WHERE name LIKE ? OR description LIKE ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + searchQuery + "%");
            preparedStatement.setString(2, "%" + searchQuery + "%");
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public List<Service> selectAllServices(int offset, int limit) throws SQLException {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT * FROM (SELECT *, ROW_NUMBER() OVER (ORDER BY id) AS RowNum FROM services) AS SubQuery WHERE RowNum > ? AND RowNum <= ?";
        System.out.println("Executing SQL: " + sql + " with offset: " + offset + " and limit: " + limit);
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, offset + limit);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                services.add(extractServiceFromResultSet(rs));
            }
        }
        System.out.println("Retrieved " + services.size() + " services");
        return services;
    }

    public int getTotalServices() throws SQLException {
        String sql = "SELECT COUNT(*) FROM services";
        System.out.println("Executing SQL: " + sql);
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Total services count: " + count);
                return count;
            }
        }
        System.out.println("No services found");
        return 0;
    }

    private Service extractServiceFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        int price = rs.getInt("price");
        int duration = rs.getInt("duration");
        String description = rs.getString("description");
        String image = rs.getString("image");
        return new Service(id, name, price, duration, description, image);
    }
}