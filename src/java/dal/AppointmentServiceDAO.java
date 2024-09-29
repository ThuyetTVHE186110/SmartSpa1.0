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
import model.Appointment;
import model.Service;

/**
 *
 * @author ADMIN
 */
public class AppointmentServiceDAO extends DBContext {

    public void addAppointmentService(int appointmentid, Service service) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        try (Connection connection = getConnection()) {
            String query = "INSERT INTO Appointment_Service (appointmentID, serviceID) VALUES (?, ?)";
            stm = connection.prepareStatement(query);
            stm.setInt(1, appointmentid);
            stm.setInt(2, service.getId());
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
    
    public static void main(String[] args) {
        AppointmentServiceDAO aO = new AppointmentServiceDAO();
        Appointment appointment = new Appointment();
        appointment.setId(2);
        Service service = new Service();
        service.setId(1);
//        aO.addAppointmentService(appointment, service);
    }
}
