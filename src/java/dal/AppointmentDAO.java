/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import static dal.DBContext.getConnection;
import java.util.logging.Logger;
import java.sql.Timestamp;
import java.sql.Time;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import model.Appointment;

/**
 * Appointment Data Access Objects
 *
 * @author ADMIN
 */
public class AppointmentDAO extends DBContext {

    private static final String SELECT_ALL_APPOINTMENTS = "SELECT * FROM Appointment";
    private static final String INSERT_APPOINTMENT = "INSERT INTO Appointment (appointmentTime, appointmentDate, CreatedDate, status, note, personID) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String DELETE_APPOINTMENT = "DELETE FROM Appointment WHERE ID = ?";
    private static final String UPDATE_APPOINTMENT = "UPDATE Appointment SET AppointmentTime = ?, AppointmentDate = ?, CreatedDate = ?, Status = ?, Note = ?, PersonID = ? WHERE ID = ?";

    /**
     * Get all appointment
     *
     * @return List of appointment
     */
    public List<Appointment> getAll() {
        List<Appointment> appointments = new ArrayList<>();
        Logger logger = Logger.getLogger(getClass().getName());

        // Try-with-resources ensures resources are closed automatically
        try (Connection connection = getConnection(); PreparedStatement stm = connection.prepareStatement(SELECT_ALL_APPOINTMENTS); ResultSet rs = stm.executeQuery()) {

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
            logger.info(e.getMessage());
        }
        return appointments;
    }

    /**
     * Add new appointment
     *
     * @param appointment
     * @throws java.sql.SQLException
     */
    public void addAppointment(Appointment appointment) throws SQLException {
        Logger logger = Logger.getLogger(getClass().getName());
        PreparedStatement stm = null;
        try (Connection connection = getConnection()) {
            stm = connection.prepareStatement(INSERT_APPOINTMENT);
            stm.setTime(1, Time.valueOf(appointment.getAppointmentTime())); // Thời gian cuộc hẹn
            stm.setDate(2, java.sql.Date.valueOf(appointment.getAppointmentDate())); // Ngày cuộc hẹn
            stm.setTimestamp(3, Timestamp.valueOf(appointment.getCreatedDate()));   // Ngày tạo cuộc hẹn
            stm.setString(4, appointment.getStatus());                              // Trạng thái cuộc hẹn
            stm.setString(5, appointment.getNote());                                // Ghi chú
            stm.setInt(6, appointment.getPerson().getId());                         // ID của người tham gia

            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Appointment successfully added.");
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error adding appointment: {0}", e.getMessage());
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    public void deleteAppointment(int appointmentId) throws SQLException {
        PreparedStatement stm = null;
        Logger logger = Logger.getLogger(getClass().getName());

        try (Connection connection = getConnection()) {
            stm = connection.prepareStatement(DELETE_APPOINTMENT);
            stm.setInt(1, appointmentId); // Set appointment ID to delete

            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Appointment successfully deleted.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting appointment: {0}", e.getMessage());
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    public void updateAppointment(Appointment appointment) throws SQLException {
        PreparedStatement stm = null;
        Logger logger = Logger.getLogger(getClass().getName());

        try (Connection connection = getConnection()) {
            stm = connection.prepareStatement(UPDATE_APPOINTMENT);
            stm.setTime(1, Time.valueOf(appointment.getAppointmentTime()));
            stm.setDate(2, java.sql.Date.valueOf(appointment.getAppointmentDate()));
            stm.setTimestamp(3, Timestamp.valueOf(appointment.getCreatedDate()));
            stm.setString(4, appointment.getStatus());
            stm.setString(5, appointment.getNote());
            stm.setInt(6, appointment.getPerson().getId());
            stm.setInt(7, appointment.getId());

            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Appointment successfully updated.");
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating appointment: {0}", e.getMessage());
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    /**
     * Get max appointmentID
     *
     * @return max of column of ID
     */
    public int getMaxAppointmentID() {
        PreparedStatement stm = null;
        ResultSet rs = null;
        Logger logger = Logger.getLogger(getClass().getName());
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
            logger.info(e.getMessage());
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AppointmentDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AppointmentDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
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
