/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import static dal.DBContext.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.AppointmentService;

/**
 * AppointmentService Data Access Object
 *
 * @author ADMIN
 */
public class AppointmentServiceDAO extends DBContext {

    public void addAppointmentService(int appointmentid, int serviceID, int staffID) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        try (Connection connection = getConnection()) {
            String query = "INSERT INTO Appointment_Service (appointmentID, serviceID, staffID) VALUES (?, ?, ?)";
            stm = connection.prepareStatement(query);
            stm.setInt(1, appointmentid);
            stm.setInt(2, serviceID);
            stm.setInt(3, staffID);
            // Thực thi lệnh thêm
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while retrieving persons: " + e.getMessage());
        } finally {
            // Close the ResultSet and PreparedStatement if they are not null
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    public List<AppointmentService> getAllAppointmentServices() {
        List<AppointmentService> services = new ArrayList<>();
        PersonDAO personDAO = new PersonDAO();
        ServiceDAO serviceDAO = new ServiceDAO();
        PreparedStatement stm = null;
        ResultSet rs = null;
        try (Connection connection = getConnection()) {
            String query = "SELECT * FROM Appointment_Service";
            stm = connection.prepareStatement(query);
            rs = stm.executeQuery();

            while (rs.next()) {
                AppointmentService appointmentService = new AppointmentService();
                appointmentService.setId(rs.getInt("ID"));
                appointmentService.setAppointmentID(rs.getInt("AppointmentID"));
                appointmentService.setService(serviceDAO.selectService(rs.getInt("ServiceID")));
                appointmentService.setStaff(personDAO.getPersonByID(rs.getInt("StaffID")));
                services.add(appointmentService);
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving all appointment services: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
        return services;
    }
    

    public List<AppointmentService> getServiceByID(int appointmentID) {
        List<AppointmentService> services = new ArrayList<>();
        PersonDAO personDAO = new PersonDAO();
        ServiceDAO serviceDAO = new ServiceDAO();
        PreparedStatement stm = null;
        ResultSet rs = null;

        try (Connection connection = getConnection()) {
            String query = "SELECT * FROM Appointment_Service Where AppointmentID = ?";
            stm = connection.prepareStatement(query);
            stm.setInt(1, appointmentID);
            rs = stm.executeQuery();

            while (rs.next()) {
                AppointmentService appointmentService = new AppointmentService();
                appointmentService.setId(rs.getInt("ID"));
                appointmentService.setAppointmentID(rs.getInt("AppointmentID"));
                appointmentService.setService(serviceDAO.selectService(rs.getInt("ServiceID")));
                appointmentService.setStaff(personDAO.getPersonByID(rs.getInt("StaffID")));
                services.add(appointmentService);
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving all appointment services: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
        return services;
    }

    public void deleteAppointmentServiceByAppointmentID(int appointmentID) {
        PreparedStatement stm = null;

        try (Connection connection = getConnection()) {
            String query = "DELETE FROM Appointment_Service WHERE AppointmentID = ?";
            stm = connection.prepareStatement(query);
            stm.setInt(1, appointmentID);

            int rowsAffected = stm.executeUpdate();
            System.out.println("Deleted " + rowsAffected + " appointment service(s) with AppointmentID = " + appointmentID);
        } catch (SQLException e) {
            System.out.println("Error while deleting appointment services: " + e.getMessage());
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        AppointmentServiceDAO aO = new AppointmentServiceDAO();
        aO.addAppointmentService(540, 2, 8);
    }
}
