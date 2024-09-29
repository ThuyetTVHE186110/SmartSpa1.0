/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import static dal.DBContext.getConnection;
import java.sql.Timestamp;
import java.sql.Time;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Appointment;

/**
 *
 * @author ADMIN
 */
public class AppointmentDAO extends DBContext {

    public List<Appointment> getAll() {
        List<Appointment> appointments = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;

        try (Connection connection = getConnection()) {
            String strSelect = "SELECT * FROM Appointment";
            stm = connection.prepareStatement(strSelect);
            rs = stm.executeQuery();

            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setId(rs.getInt("ID"));
                appointment.setAppointmentTime(rs.getTime("AppointmentTime").toLocalTime());
                appointment.setAppointmentDate(rs.getDate("AppointmentDate").toLocalDate());
                appointment.setCreatedDate(rs.getTimestamp("CreatedDate").toLocalDateTime());
                appointment.setStatus(rs.getString("Status"));
                appointment.setNote(rs.getString("Note"));
                PersonDAO personDAO = new PersonDAO();
                appointment.setPerson(personDAO.getPersonByID(rs.getInt("PersonID")));
                appointments.add(appointment);
            }
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
        return appointments;
    }

    public void addAppointment(Appointment appointment) {
        PreparedStatement stm = null;
        ResultSet rs = null;

        try (Connection connection = getConnection()) {
            String query = "INSERT INTO Appointment (appointmentTime, appointmentDate, CreatedDate, status, note, personID) VALUES (?, ?, ?, ?, ?, ?)";
            stm = connection.prepareStatement(query);

            // Thiết lập các giá trị cho PreparedStatement
            stm.setTime(1, Time.valueOf(appointment.getAppointmentTime())); // Thời gian cuộc hẹn
            stm.setDate(2, java.sql.Date.valueOf(appointment.getAppointmentDate())); // Ngày cuộc hẹn
            stm.setTimestamp(3, Timestamp.valueOf(appointment.getCreatedDate()));   // Ngày tạo cuộc hẹn
            stm.setString(4, appointment.getStatus());                                // Trạng thái cuộc hẹn
            stm.setString(5, appointment.getNote());                                  // Ghi chú
            stm.setInt(6, appointment.getPerson().getId());                           // ID người thực hiện cuộc hẹn

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

    public int getMaxAppointmentID() {
        PreparedStatement stm = null;
        ResultSet rs = null;
        int maxID = 0;
        try (Connection connection = getConnection()) {
            String query = "SELECT MAX(ID) AS max_id FROM Appointment";
            stm = connection.prepareStatement(query);
            rs = stm.executeQuery();

            if (rs.next()) {
                maxID = rs.getInt("max_id");
                return maxID;
            }
        } catch (SQLException e) {
            System.out.println("Error while getting max appointment ID: " + e.getMessage());
        } finally {
            // Đóng ResultSet và PreparedStatement nếu không null
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
        return maxID; // Trả về 0 nếu có lỗi hoặc không tìm thấy ID nào
    }

    public static void main(String[] args) {
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        int max = appointmentDAO.getMaxAppointmentID();
        System.out.println(max);
    }
}
