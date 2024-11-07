/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.Service;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.PopularService;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author ThuyetTVHE186110
 */
public class ServiceDAO extends DBContext{
    private static final String INSERT_SERVICE_SQL = "INSERT INTO services (name, price, duration, description, image, category, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_SERVICE_BY_ID = "SELECT * FROM services WHERE id = ?";
    private static final String SELECT_ALL_SERVICES = "SELECT * FROM services";
    private static final String UPDATE_SERVICE_SQL = "UPDATE services SET name = ?, price = ?, duration = ?, description = ?, image = ?, category = ?, status = ? WHERE id = ?";
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
            preparedStatement.setString(5, service.getImage());
            preparedStatement.setString(6, service.getCategory());
            preparedStatement.setString(7, service.getStatus() != null ? service.getStatus() : "ACTIVE");
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
        String sql = "SELECT * FROM (SELECT *, ROW_NUMBER() OVER (ORDER BY id) as RowNum FROM services) AS tmp " +
                    "WHERE RowNum BETWEEN ? AND ?";
        
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, offset + 1);
            preparedStatement.setInt(2, offset + limit);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                services.add(extractServiceFromResultSet(rs));
            }
        }
        return services;
    }

    // Get services by category using SQL Server syntax
    public List<Service> getServicesByCategory(String category) throws SQLException {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT * FROM services WHERE category = ? AND status = 'ACTIVE'";
        
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
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
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SERVICE_SQL)) {
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
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SERVICE_SQL)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    // Search services with pagination using SQL Server syntax
    public List<Service> searchServices(String searchQuery, int offset, int limit) throws SQLException {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT * FROM (SELECT *, ROW_NUMBER() OVER (ORDER BY id) as RowNum FROM services " +
                    "WHERE name LIKE ? OR description LIKE ?) AS tmp " +
                    "WHERE RowNum BETWEEN ? AND ?";
        
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
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
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
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
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, serviceId);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    // Add method to get services by status
    public List<Service> getServicesByStatus(String status) throws SQLException {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT * FROM services WHERE status = ?";
        
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
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
        String sql = "SELECT * FROM (SELECT *, ROW_NUMBER() OVER (ORDER BY id) as RowNum FROM services " +
                    "WHERE status = ?) AS tmp WHERE RowNum BETWEEN ? AND ?";
        
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
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
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
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
        String sql = "SELECT * FROM (SELECT *, ROW_NUMBER() OVER (ORDER BY id) as RowNum FROM services) AS tmp " +
                    "WHERE RowNum BETWEEN ? AND ?";
        
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
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
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }

    public List<String> getAllCategories() throws SQLException {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT DISTINCT category FROM Services WHERE category IS NOT NULL ORDER BY category";
        
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            
            while (rs.next()) {
                categories.add(rs.getString("category"));
            }
        }
        return categories;
    }

    public List<PopularService> getPopularServices() throws SQLException {
        List<PopularService> popularServices = new ArrayList<>();
        String sql = """
            SELECT s.ID, s.Name,
                   COUNT(aps.ServicesID) as booking_count,
                   CAST(
                       (CASE 
                            WHEN (SELECT COUNT(*) FROM Appointment_Service 
                                  INNER JOIN Appointment a ON Appointment_Service.AppointmentID = a.ID
                                  WHERE MONTH(a.CreatedDate) = MONTH(GETDATE())) > 0 
                            THEN (COUNT(aps.ServicesID) * 100.0 / 
                                  (SELECT COUNT(*) FROM Appointment_Service 
                                   INNER JOIN Appointment a ON Appointment_Service.AppointmentID = a.ID
                                   WHERE MONTH(a.CreatedDate) = MONTH(GETDATE())))
                            ELSE 0
                        END) AS INT
                   ) as trend_percentage
            FROM Services s
            LEFT JOIN Appointment_Service aps ON s.ID = aps.ServicesID
            LEFT JOIN Appointment a ON aps.AppointmentID = a.ID
            WHERE a.CreatedDate IS NULL OR MONTH(a.CreatedDate) = MONTH(GETDATE())
            GROUP BY s.ID, s.Name
            ORDER BY booking_count DESC
            OFFSET 0 ROWS FETCH NEXT 5 ROWS ONLY
        """;
        
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            
            while (rs.next()) {
                PopularService service = new PopularService();
                service.setId(rs.getInt("ID"));
                service.setName(rs.getString("Name"));
                service.setBookingCount(rs.getInt("booking_count"));
                service.setTrend(rs.getInt("trend_percentage"));
                popularServices.add(service);
            }
        }
        return popularServices;
    }

    public Map<String, Object> getServicePerformanceMetrics(int serviceId) throws SQLException {
        Map<String, Object> metrics = new HashMap<>();
        String sql = """
            SELECT 
                s.ID, s.Name,
                COUNT(DISTINCT a.ID) as total_appointments,
                COUNT(DISTINCT a.CustomerID) as unique_customers,
                COUNT(DISTINCT aps.StaffID) as assigned_staff,
                SUM(ob.TotalAmount) as total_revenue,
                AVG(DATEDIFF(MINUTE, a.CreatedDate, a.Start)) as avg_booking_leadtime
            FROM Services s
            LEFT JOIN Appointment_Service aps ON s.ID = aps.ServicesID
            LEFT JOIN Appointment a ON aps.AppointmentID = a.ID
            LEFT JOIN Orders_Bill ob ON a.ID = ob.AppointmentID
            WHERE s.ID = ? AND a.CreatedDate >= DATEADD(MONTH, -3, GETDATE())
            GROUP BY s.ID, s.Name
        """;
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, serviceId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                metrics.put("totalAppointments", rs.getInt("total_appointments"));
                metrics.put("uniqueCustomers", rs.getInt("unique_customers"));
                metrics.put("assignedStaff", rs.getInt("assigned_staff"));
                metrics.put("totalRevenue", rs.getBigDecimal("total_revenue"));
                metrics.put("avgBookingLeadtime", rs.getInt("avg_booking_leadtime"));
            }
        }
        return metrics;
    }

    public List<Map<String, Object>> getStaffServicePerformance(int serviceId) throws SQLException {
        String sql = """
            WITH StaffMetrics AS (
                SELECT 
                    p.ID as StaffID,
                    p.Name as StaffName,
                    COUNT(DISTINCT aps.ID) as service_count,
                    COUNT(DISTINCT a.CustomerID) as unique_customers,
                    COUNT(DISTINCT CASE WHEN a.Status = 'Completed' THEN a.ID END) as completed_services,
                    COUNT(DISTINCT a.ID) as total_services
                FROM Person p
                JOIN Account acc ON p.ID = acc.PersonID
                LEFT JOIN Appointment_Service aps ON p.ID = aps.StaffID
                LEFT JOIN Appointment a ON aps.AppointmentID = a.ID
                WHERE acc.RoleID = 3 
                AND (aps.ServicesID = ? OR ? IS NULL)
                GROUP BY p.ID, p.Name
            )
            SELECT 
                StaffID,
                StaffName,
                service_count,
                unique_customers,
                CASE 
                    WHEN total_services > 0 
                    THEN (completed_services * 100.0 / total_services)
                    ELSE 0 
                END as completion_rate
            FROM StaffMetrics
            WHERE service_count > 0
            ORDER BY service_count DESC
        """;
        
        List<Map<String, Object>> staffPerformance = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, serviceId);
            ps.setInt(2, serviceId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> staff = new HashMap<>();
                staff.put("staffId", rs.getInt("StaffID"));
                staff.put("staffName", rs.getString("StaffName"));
                staff.put("serviceCount", rs.getInt("service_count"));
                staff.put("uniqueCustomers", rs.getInt("unique_customers"));
                staff.put("completionRate", Math.round(rs.getDouble("completion_rate")));
                staffPerformance.add(staff);
            }
        }
        return staffPerformance;
    }

    public Map<String, Object> getServiceResourceRequirements(int serviceId) throws SQLException {
        Map<String, Object> resources = new HashMap<>();
        String sql = """
            SELECT 
                s.ID as ServiceID,
                p.ID as ProductID,
                p.Name as ProductName,
                p.Quantity as AvailableQuantity,
                m.ID as MaterialID,
                m.Name as MaterialName,
                m.Status as MaterialStatus,
                sp.ID as ServiceProductID
            FROM Services s
            LEFT JOIN Service_Product sp ON s.ID = sp.ServiceID
            LEFT JOIN Product p ON sp.ProductID = p.ID
            LEFT JOIN Material m ON p.ID = m.ID
            WHERE s.ID = ?
        """;
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, serviceId);
            ResultSet rs = ps.executeQuery();
            
            List<Map<String, Object>> products = new ArrayList<>();
            List<Map<String, Object>> materials = new ArrayList<>();
            
            while (rs.next()) {
                // Product info
                if (rs.getInt("ProductID") > 0) {
                    Map<String, Object> product = new HashMap<>();
                    product.put("id", rs.getInt("ProductID"));
                    product.put("name", rs.getString("ProductName"));
                    product.put("available", rs.getInt("AvailableQuantity"));
                    // Default to 1 if no specific requirement is set
                    product.put("required", 1);
                    products.add(product);
                }
                
                // Material info
                if (rs.getInt("MaterialID") > 0) {
                    Map<String, Object> material = new HashMap<>();
                    material.put("id", rs.getInt("MaterialID"));
                    material.put("name", rs.getString("MaterialName"));
                    material.put("status", rs.getString("MaterialStatus"));
                    materials.add(material);
                }
            }
            
            resources.put("products", products);
            resources.put("materials", materials);
            
            // Add debug logging
            System.out.println("Resource Requirements for Service " + serviceId + ":");
            System.out.println("Products found: " + products.size());
            System.out.println("Materials found: " + materials.size());
        }
        
        return resources;
    }

    public List<Map<String, Object>> getServiceSteps(int serviceId) throws SQLException {
        String sql = """
            SELECT 
                st.ID,
                st.Name,
                st.Description,
                st.Duration,
                COUNT(DISTINCT aps.StaffID) as qualified_staff_count
            FROM Steps st
            LEFT JOIN Appointment_Service aps ON st.ServiceID = aps.ServicesID
            WHERE st.ServiceID = ?
            GROUP BY st.ID, st.Name, st.Description, st.Duration
            ORDER BY st.ID
        """;
        
        List<Map<String, Object>> steps = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, serviceId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> step = new HashMap<>();
                step.put("id", rs.getInt("ID"));
                step.put("name", rs.getString("Name"));
                step.put("description", rs.getString("Description"));
                step.put("duration", rs.getInt("Duration"));
                step.put("qualifiedStaffCount", rs.getInt("qualified_staff_count"));
                steps.add(step);
            }
        }
        return steps;
    }

    public Map<String, Object> getMonthlyStats() throws SQLException {
        Map<String, Object> monthlyStats = new HashMap<>();
        String sql = """
            WITH CurrentMonthData AS (
                SELECT 
                    SUM(ob.TotalAmount) as total_revenue,
                    COUNT(DISTINCT a.ID) as total_bookings
                FROM Services s
                LEFT JOIN Appointment_Service aps ON s.ID = aps.ServicesID
                LEFT JOIN Appointment a ON aps.AppointmentID = a.ID
                LEFT JOIN Orders_Bill ob ON a.ID = ob.AppointmentID
                WHERE MONTH(a.CreatedDate) = MONTH(GETDATE())
            ),
            PreviousMonthData AS (
                SELECT 
                    SUM(ob.TotalAmount) as prev_revenue,
                    COUNT(DISTINCT a.ID) as prev_bookings
                FROM Services s
                LEFT JOIN Appointment_Service aps ON s.ID = aps.ServicesID
                LEFT JOIN Appointment a ON aps.AppointmentID = a.ID
                LEFT JOIN Orders_Bill ob ON a.ID = ob.AppointmentID
                WHERE MONTH(a.CreatedDate) = MONTH(DATEADD(MONTH, -1, GETDATE()))
            ),
            MonthlyTrends AS (
                SELECT 
                    MONTH(a.CreatedDate) as month,
                    COUNT(DISTINCT a.ID) as bookings,
                    SUM(ob.TotalAmount) as revenue
                FROM Services s
                LEFT JOIN Appointment_Service aps ON s.ID = aps.ServicesID
                LEFT JOIN Appointment a ON aps.AppointmentID = a.ID
                LEFT JOIN Orders_Bill ob ON a.ID = ob.AppointmentID
                WHERE a.CreatedDate >= DATEADD(MONTH, -6, GETDATE())
                GROUP BY MONTH(a.CreatedDate)
            )
            SELECT 
                c.total_revenue,
                c.total_bookings,
                CASE 
                    WHEN p.prev_revenue > 0 
                    THEN ((c.total_revenue - p.prev_revenue) * 100.0 / p.prev_revenue)
                    ELSE 0 
                END as revenue_trend,
                CASE 
                    WHEN p.prev_bookings > 0 
                    THEN ((c.total_bookings - p.prev_bookings) * 100.0 / p.prev_bookings)
                    ELSE 0 
                END as booking_trend
            FROM CurrentMonthData c
            CROSS JOIN PreviousMonthData p
        """;
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                monthlyStats.put("totalRevenue", rs.getBigDecimal("total_revenue"));
                monthlyStats.put("totalBookings", rs.getInt("total_bookings"));
                monthlyStats.put("revenueTrend", rs.getDouble("revenue_trend"));
                monthlyStats.put("bookingTrend", rs.getDouble("booking_trend"));
                
                // Add chart data
                monthlyStats.put("chartData", getMonthlyChartData());
            } else {
                // Set default values if no data
                monthlyStats.put("totalRevenue", BigDecimal.ZERO);
                monthlyStats.put("totalBookings", 0);
                monthlyStats.put("revenueTrend", 0.0);
                monthlyStats.put("bookingTrend", 0.0);
                monthlyStats.put("chartData", new ArrayList<>());
            }
        }
        return monthlyStats;
    }

    private List<Map<String, Object>> getMonthlyChartData() throws SQLException {
        String sql = """
            WITH MonthlyStats AS (
                SELECT 
                    MONTH(a.CreatedDate) as month,
                    COUNT(DISTINCT a.ID) as bookings,
                    SUM(ISNULL(ob.TotalAmount, 0)) as revenue
                FROM Services s
                LEFT JOIN Appointment_Service aps ON s.ID = aps.ServicesID
                LEFT JOIN Appointment a ON aps.AppointmentID = a.ID
                LEFT JOIN Orders_Bill ob ON a.ID = ob.AppointmentID
                WHERE a.CreatedDate >= DATEADD(MONTH, -6, GETDATE())
                    AND a.CreatedDate <= GETDATE()
                GROUP BY MONTH(a.CreatedDate)
            )
            SELECT 
                month,
                bookings,
                revenue,
                LAG(bookings) OVER (ORDER BY month) as prev_bookings,
                LAG(revenue) OVER (ORDER BY month) as prev_revenue
            FROM MonthlyStats
            ORDER BY month
        """;
        
        List<Map<String, Object>> chartData = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            
            // Add debug logging
            System.out.println("Monthly Chart Data:");
            while (rs.next()) {
                Map<String, Object> monthData = new HashMap<>();
                int month = rs.getInt("month");
                int bookings = rs.getInt("bookings");
                BigDecimal revenue = rs.getBigDecimal("revenue");
                
                monthData.put("month", month);
                monthData.put("bookings", bookings);
                monthData.put("revenue", revenue);
                
                // Debug print
                System.out.printf("Month: %d, Bookings: %d, Revenue: $%.2f%n", 
                    month, bookings, revenue);
                
                chartData.add(monthData);
            }
        }
        return chartData;
    }

    public Map<String, Object> getServiceTrends(int serviceId) {
        Map<String, Object> trends = new HashMap<>();
        String sql = """
            WITH MonthlyData AS (
                SELECT 
                    DATEPART(MONTH, a.CreatedDate) as month,
                    COUNT(DISTINCT a.ID) as booking_count,
                    SUM(ISNULL(ob.TotalAmount, 0)) as revenue,
                    AVG(DATEDIFF(MINUTE, a.CreatedDate, a.Start)) as avg_booking_leadtime
                FROM Services s
                JOIN Appointment_Service aps ON s.ID = aps.ServicesID
                JOIN Appointment a ON aps.AppointmentID = a.ID
                LEFT JOIN Orders_Bill ob ON a.ID = ob.AppointmentID
                WHERE s.ID = ? AND a.CreatedDate >= DATEADD(MONTH, -6, GETDATE())
                GROUP BY DATEPART(MONTH, a.CreatedDate)
            )
            SELECT 
                month,
                booking_count,
                revenue,
                avg_booking_leadtime,
                LAG(booking_count) OVER (ORDER BY month) as prev_bookings,
                LAG(revenue) OVER (ORDER BY month) as prev_revenue
            FROM MonthlyData
            ORDER BY month
        """;
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, serviceId);
            ResultSet rs = ps.executeQuery();
            
            List<Map<String, Object>> monthlyData = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> month = new HashMap<>();
                month.put("month", rs.getInt("month"));
                month.put("bookings", rs.getInt("booking_count"));
                month.put("revenue", rs.getBigDecimal("revenue"));
                month.put("leadTime", rs.getInt("avg_booking_leadtime"));
                
                // Calculate trends
                int prevBookings = rs.getInt("prev_bookings");
                BigDecimal prevRevenue = rs.getBigDecimal("prev_revenue");
                
                if (prevBookings > 0) {
                    double bookingTrend = ((rs.getInt("booking_count") - prevBookings) * 100.0) / prevBookings;
                    month.put("bookingTrend", Math.round(bookingTrend));
                }
                
                if (prevRevenue != null && prevRevenue.compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal currentRevenue = rs.getBigDecimal("revenue");
                    if (currentRevenue != null) {
                        BigDecimal revenueTrend = currentRevenue
                            .subtract(prevRevenue)
                            .multiply(new BigDecimal(100))
                            .divide(prevRevenue, 2, RoundingMode.HALF_UP);
                        month.put("revenueTrend", revenueTrend);
                    }
                }
                
                monthlyData.add(month);
            }
            
            trends.put("monthlyData", monthlyData);
            
            // Calculate overall trends
            if (!monthlyData.isEmpty()) {
                Map<String, Object> firstMonth = monthlyData.get(0);
                Map<String, Object> lastMonth = monthlyData.get(monthlyData.size() - 1);
                
                double overallBookingTrend = calculateTrend(
                    (Integer)firstMonth.get("bookings"), 
                    (Integer)lastMonth.get("bookings")
                );
                
                BigDecimal firstRevenue = (BigDecimal) firstMonth.get("revenue");
                BigDecimal lastRevenue = (BigDecimal) lastMonth.get("revenue");
                double overallRevenueTrend = calculateTrend(
                    firstRevenue != null ? firstRevenue.doubleValue() : 0, 
                    lastRevenue != null ? lastRevenue.doubleValue() : 0
                );
                
                trends.put("overallBookingTrend", overallBookingTrend);
                trends.put("overallRevenueTrend", overallRevenueTrend);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting service trends: " + e.getMessage());
            e.printStackTrace();
        }
        
        return trends;
    }

    private double calculateTrend(double start, double end) {
        if (start == 0) return 0;
        return ((end - start) / start) * 100;
    }

    public static void main(String[] args) {
        try {
            ServiceDAO serviceDAO = new ServiceDAO();
            System.out.println("=== Testing Database Connection ===");
            System.out.println("JDBC Driver: " + com.microsoft.sqlserver.jdbc.SQLServerDriver.class.getName());
            Connection conn = serviceDAO.getConnection();
            System.out.println("Database connection successful!");

            // Test basic service operations
            System.out.println("\n=== Testing Basic Service Operations ===");
            List<Service> allServices = serviceDAO.selectAllServices();
            System.out.println("Total services found: " + allServices.size());
            allServices.forEach(service -> {
                System.out.printf("ID: %d, Name: %s, Price: $%d, Status: %s%n",
                    service.getId(), service.getName(), service.getPrice(), service.getStatus());
            });

            // Test categories
            System.out.println("\n=== Testing Categories ===");
            List<String> categories = serviceDAO.getAllCategories();
            System.out.println("Categories found: " + categories.size());
            categories.forEach(category -> System.out.println("Category: " + category));

            // Test monthly stats
            System.out.println("\n=== Testing Monthly Stats ===");
            Map<String, Object> monthlyStats = serviceDAO.getMonthlyStats();
            System.out.println("Total Revenue: " + monthlyStats.get("totalRevenue"));
            System.out.println("Total Bookings: " + monthlyStats.get("totalBookings"));
            System.out.println("Revenue Trend: " + monthlyStats.get("revenueTrend") + "%");
            System.out.println("Booking Trend: " + monthlyStats.get("bookingTrend") + "%");

            // Test chart data
            System.out.println("\n=== Testing Chart Data ===");
            List<?> chartData = (List<?>) monthlyStats.get("chartData");
            System.out.println("Chart data points: " + chartData.size());
            chartData.forEach(data -> System.out.println("Data point: " + data));

            // Test service performance for a specific service
            System.out.println("\n=== Testing Service Performance ===");
            int testServiceId = 1; // Use an existing service ID
            Service testService = serviceDAO.selectService(testServiceId);
            if (testService != null) {
                System.out.println("Testing performance for service: " + testService.getName());
                
                Map<String, Object> performance = serviceDAO.getServicePerformanceMetrics(testServiceId);
                System.out.println("Performance Metrics:");
                performance.forEach((key, value) -> System.out.println(key + ": " + value));

                List<Map<String, Object>> staffPerformance = serviceDAO.getStaffServicePerformance(testServiceId);
                System.out.println("\nStaff Performance:");
                staffPerformance.forEach(staff -> {
                    System.out.printf("Staff ID: %s, Name: %s, Service Count: %s, Completion Rate: %s%%%n",
                        staff.get("staffId"),
                        staff.get("staffName"),
                        staff.get("serviceCount"),
                        staff.get("completionRate"));
                });

                Map<String, Object> resources = serviceDAO.getServiceResourceRequirements(testServiceId);
                System.out.println("\nResource Requirements:");
                resources.forEach((key, value) -> System.out.println(key + ": " + value));

                List<Map<String, Object>> steps = serviceDAO.getServiceSteps(testServiceId);
                System.out.println("\nService Steps:");
                steps.forEach(step -> {
                    System.out.printf("Step: %s, Duration: %s minutes, Staff Required: %s%n",
                        step.get("name"),
                        step.get("duration"),
                        step.get("qualifiedStaffCount"));
                });
            }

            // Test service trends
            System.out.println("\n=== Testing Service Trends ===");
            Map<String, Object> trends = serviceDAO.getServiceTrends(testServiceId);
            System.out.println("Overall Booking Trend: " + trends.get("overallBookingTrend") + "%");
            System.out.println("Overall Revenue Trend: " + trends.get("overallRevenueTrend") + "%");
            List<Map<String, Object>> monthlyTrends = (List<Map<String, Object>>) trends.get("monthlyData");
            System.out.println("\nMonthly Trends:");
            monthlyTrends.forEach(month -> {
                System.out.printf("Month: %d, Bookings: %d, Revenue: $%.2f%n",
                    month.get("month"),
                    month.get("bookings"),
                    ((BigDecimal)month.get("revenue")).doubleValue());
            });

        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("General Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}