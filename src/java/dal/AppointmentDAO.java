/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import com.sun.mail.imap.IMAPSSLStore;
import static dal.DBContext.getConnection;
import java.util.logging.Logger;
import java.sql.Timestamp;
import java.sql.Time;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import model.Appointment;
import model.AppointmentService;
import model.Person;
import model.Service;
import model.TimeSlot;

/**
 * Appointment Data Access Objects
 *
 * @author ADMIN
 */
public class AppointmentDAO extends DBContext {

    private static final String SELECT_ALL_APPOINTMENTS = "SELECT * FROM Appointment ORDER BY Start DESC";
    private static final String INSERT_APPOINTMENT = "INSERT INTO Appointment (Start, [End], CreatedDate, Status, Note, CustomerID) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String DELETE_APPOINTMENT = "DELETE FROM Appointment WHERE ID = ?";
    private static final String UPDATE_APPOINTMENT = "UPDATE Appointment SET Start = ?, [End] = ?, CreatedDate = ?, Status = ?, Note = ?, CustomerID = ? WHERE ID = ?";
    private static final String BUSY_TIME = """
                                            SELECT Cast(a.Start as Time) as Start, Cast(a.[End] as Time) as [End]
                                            FROM Appointment a
                                            JOIN Appointment_Service asvc ON a.ID = asvc.AppointmentID
                                            WHERE asvc.StaffID = ? AND a.Status <> 'Completed' AND Cast(a.Start as Date) = ? AND Cast(a.[End] as Date) = ?""";

    private final PersonDAO personDAO = new PersonDAO();
    private final AppointmentServiceDAO serviceDAO = new AppointmentServiceDAO();

    public Appointment getAppointmentByID(int id) {
        Appointment appointment = null;
        Logger logger = Logger.getLogger(getClass().getName());
        String sql = "SELECT * FROM Appointment WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement stm = connection.prepareStatement(sql)) {
            // Thiết lập tham số cho câu truy vấn SQL
            stm.setInt(1, id);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    appointment = new Appointment();
                    appointment.setId(rs.getInt("ID"));
                    appointment.setStart(rs.getTimestamp("Start").toLocalDateTime());
                    appointment.setEnd(rs.getTimestamp("End").toLocalDateTime());
                    appointment.setCreatedDate(rs.getTimestamp("CreatedDate").toLocalDateTime());
                    appointment.setStatus(rs.getString("Status"));
                    appointment.setNote(rs.getString("Note"));
                    appointment.setCustomer(personDAO.getPersonByID(rs.getInt("CustomerID")));
                    List<AppointmentService> list = serviceDAO.getServiceByID(appointment.getId());
                    appointment.setServices(list);
                }
            }

        } catch (SQLException e) {
            logger.info(e.getMessage());
        }

        return appointment;
    }

    public List<Appointment> getAllByCustomer(int customerID) {
        List<Appointment> appointments = new ArrayList<>();
        Logger logger = Logger.getLogger(getClass().getName());

        String SELECT_APPOINTMENTS = "SELECT * FROM Appointment a WHERE a.CustomerID = ?";

        try (Connection connection = getConnection(); PreparedStatement stm = connection.prepareStatement(SELECT_APPOINTMENTS)) {

// Thiết lập tham số cho câu truy vấn SQL
            stm.setInt(1, customerID);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setId(rs.getInt("ID"));
                    appointment.setStart(rs.getTimestamp("Start").toLocalDateTime());
                    appointment.setEnd(rs.getTimestamp("End").toLocalDateTime());
                    appointment.setCreatedDate(rs.getTimestamp("CreatedDate").toLocalDateTime());
                    appointment.setStatus(rs.getString("Status"));
                    appointment.setNote(rs.getString("Note"));
                    appointment.setCustomer(personDAO.getPersonByID(rs.getInt("CustomerID")));
                    List<AppointmentService> list = serviceDAO.getServiceByID(appointment.getId());
                    appointment.setServices(list);
                    appointments.add(appointment);
                }
            }

        } catch (SQLException e) {
            logger.info(e.getMessage());
        }

        return appointments;
    }

    public List<Appointment> getHistoryCustomer(int customerID) {
        List<Appointment> appointments = new ArrayList<>();
        Logger logger = Logger.getLogger(getClass().getName());

        String SELECT_APPOINTMENTS = "SELECT * FROM Appointment a WHERE a.Start < CAST(GETDATE() AS DATE) and a.CustomerID = ?";

        try (Connection connection = getConnection(); PreparedStatement stm = connection.prepareStatement(SELECT_APPOINTMENTS)) {

// Thiết lập tham số cho câu truy vấn SQL
            stm.setInt(1, customerID);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setId(rs.getInt("ID"));
                    appointment.setStart(rs.getTimestamp("Start").toLocalDateTime());
                    appointment.setEnd(rs.getTimestamp("End").toLocalDateTime());
                    appointment.setCreatedDate(rs.getTimestamp("CreatedDate").toLocalDateTime());
                    appointment.setStatus(rs.getString("Status"));
                    appointment.setNote(rs.getString("Note"));
                    appointment.setCustomer(personDAO.getPersonByID(rs.getInt("CustomerID")));
                    List<AppointmentService> list = serviceDAO.getServiceByID(appointment.getId());
                    appointment.setServices(list);
                    appointments.add(appointment);
                }
            }

        } catch (SQLException e) {
            logger.info(e.getMessage());
        }

        return appointments;
    }

    public List<Appointment> getCommingCustomer(int customerID) {
        List<Appointment> appointments = new ArrayList<>();
        Logger logger = Logger.getLogger(getClass().getName());

        String SELECT_APPOINTMENTS = "SELECT * FROM Appointment a WHERE a.Start >= CAST(GETDATE() AS DATE) and a.CustomerID = ?";

        try (Connection connection = getConnection(); PreparedStatement stm = connection.prepareStatement(SELECT_APPOINTMENTS)) {

// Thiết lập tham số cho câu truy vấn SQL
            stm.setInt(1, customerID);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setId(rs.getInt("ID"));
                    appointment.setStart(rs.getTimestamp("Start").toLocalDateTime());
                    appointment.setEnd(rs.getTimestamp("End").toLocalDateTime());
                    appointment.setCreatedDate(rs.getTimestamp("CreatedDate").toLocalDateTime());
                    appointment.setStatus(rs.getString("Status"));
                    appointment.setNote(rs.getString("Note"));
                    appointment.setCustomer(personDAO.getPersonByID(rs.getInt("CustomerID")));
                    List<AppointmentService> list = serviceDAO.getServiceByID(appointment.getId());
                    appointment.setServices(list);
                    appointments.add(appointment);
                }
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }

        return appointments;
    }

    public List<TimeSlot> getBusyTimes(int staffID, LocalDate date) {
        List<TimeSlot> busyTimes = new ArrayList<>();
        Logger logger = Logger.getLogger(getClass().getName());

        try (Connection connection = getConnection(); PreparedStatement stm = connection.prepareStatement(BUSY_TIME)) {

            // Thiết lập tham số cho câu truy vấn SQL
            stm.setInt(1, staffID);
            stm.setDate(2, java.sql.Date.valueOf(date));
            stm.setDate(3, java.sql.Date.valueOf(date));

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    TimeSlot busyTime = new TimeSlot();
                    busyTime.setStart(rs.getTime("Start").toLocalTime());
                    busyTime.setEnd(rs.getTime("End").toLocalTime());
                    boolean exists = busyTimes.stream().anyMatch(existingTime
                            -> existingTime.getStart().equals(busyTime.getStart())
                            && existingTime.getEnd().equals(busyTime.getEnd()));
                    if (!exists) {
                        busyTimes.add(busyTime);
                    }
                }
            }

        } catch (SQLException e) {
            logger.info(e.getMessage());
        }

        return busyTimes;
    }

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
                appointment.setStart(rs.getTimestamp("Start").toLocalDateTime());
                appointment.setEnd(rs.getTimestamp("End").toLocalDateTime());
                appointment.setCreatedDate(rs.getTimestamp("CreatedDate").toLocalDateTime());
                appointment.setStatus(rs.getString("Status"));
                appointment.setNote(rs.getString("Note"));

                appointment.setCustomer(personDAO.getPersonByID(rs.getInt("CustomerID")));
                appointments.add(appointment);

                List<AppointmentService> list = serviceDAO.getServiceByID(appointment.getId());
                appointment.setServices(list);
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return appointments;
    }

    /**
     * Get list of appointments by date
     *
     * @param date Search Date
     * @return
     */
    public List<Appointment> getByDate(LocalDate date) {
        List<Appointment> appointments = new ArrayList<>();
        Logger logger = Logger.getLogger(getClass().getName());

        String SELECT_APPOINTMENTS_BY_DATE = "SELECT * FROM Appointment WHERE Cast(Start as Date) = ? ORDER BY Start DESC";

        try (Connection connection = getConnection(); PreparedStatement stm = connection.prepareStatement(SELECT_APPOINTMENTS_BY_DATE)) {

            // Thiết lập tham số cho câu truy vấn SQL
            stm.setDate(1, java.sql.Date.valueOf(date));

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setId(rs.getInt("ID"));
                    appointment.setStart(rs.getTimestamp("Start").toLocalDateTime());
                    appointment.setEnd(rs.getTimestamp("End").toLocalDateTime());
                    appointment.setCreatedDate(rs.getTimestamp("CreatedDate").toLocalDateTime());
                    appointment.setStatus(rs.getString("Status"));
                    appointment.setNote(rs.getString("Note"));

                    appointment.setCustomer(personDAO.getPersonByID(rs.getInt("CustomerID")));
                    appointments.add(appointment);

                    List<AppointmentService> list = serviceDAO.getServiceByID(appointment.getId());
                    appointment.setServices(list);
                }
            }

        } catch (SQLException e) {
            logger.info(e.getMessage());
        }

        return appointments;
    }

    public List<Appointment> getByCustomer(String name) {
        List<Appointment> appointments = new ArrayList<>();
        Logger logger = Logger.getLogger(getClass().getName());

        String SELECT_APPOINTMENTS_BY_DATE = """
                                             SELECT * FROM Appointment a join Person p on a.customerID = p.ID where p.Name like ?
                                             ORDER BY a.appointmentDate DESC, a.appointmentTime DESC""";

        try (Connection connection = getConnection(); PreparedStatement stm = connection.prepareStatement(SELECT_APPOINTMENTS_BY_DATE)) {

            // Thiết lập tham số cho câu truy vấn SQL
            stm.setString(1, "%" + name + "%");

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setId(rs.getInt("ID"));
                    appointment.setStart(rs.getTimestamp("Start").toLocalDateTime());
                    appointment.setEnd(rs.getTimestamp("End").toLocalDateTime());
                    appointment.setCreatedDate(rs.getTimestamp("CreatedDate").toLocalDateTime());
                    appointment.setStatus(rs.getString("Status"));
                    appointment.setNote(rs.getString("Note"));
                    appointment.setCustomer(personDAO.getPersonByID(rs.getInt("CustomerID")));
                    List<AppointmentService> list = serviceDAO.getServiceByID(appointment.getId());
                    appointment.setServices(list);
                    appointments.add(appointment);
                }
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
     * @return
     */
    public boolean addAppointment(Appointment appointment) {
        Logger logger = Logger.getLogger(getClass().getName());

        try (Connection connection = getConnection(); PreparedStatement stm = connection.prepareStatement(INSERT_APPOINTMENT)) {

            stm.setTimestamp(1, Timestamp.valueOf(appointment.getStart()));
            stm.setTimestamp(2, Timestamp.valueOf(appointment.getEnd()));
            stm.setTimestamp(3, Timestamp.valueOf(appointment.getCreatedDate()));
            stm.setString(4, appointment.getStatus());
            stm.setString(5, appointment.getNote());
            stm.setInt(6, appointment.getCustomer().getId());

            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Appointment successfully added.");
                return true;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error adding appointment: {0}", e.getMessage());
        }
        return false;
    }

    public boolean deleteAppointment(int appointmentId) {
        Logger logger = Logger.getLogger(getClass().getName());

        try (Connection connection = getConnection(); PreparedStatement stm = connection.prepareStatement(DELETE_APPOINTMENT)) {

            stm.setInt(1, appointmentId);

            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Appointment successfully deleted.");
                return true;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting appointment: {0}", e.getMessage());
        }
        return false;
    }

    public boolean updateAppointment(Appointment appointment) {
        Logger logger = Logger.getLogger(getClass().getName());

        try (Connection connection = getConnection(); PreparedStatement stm = connection.prepareStatement(UPDATE_APPOINTMENT)) {

            stm.setTimestamp(1, Timestamp.valueOf(appointment.getStart()));
            stm.setTimestamp(2, Timestamp.valueOf(appointment.getEnd()));
            stm.setTimestamp(3, Timestamp.valueOf(appointment.getCreatedDate()));
            stm.setString(4, appointment.getStatus());
            stm.setString(5, appointment.getNote());
            stm.setInt(6, appointment.getCustomer().getId());
            stm.setInt(7, appointment.getId());

            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Appointment successfully updated.");
                return true;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating appointment: {0}", e.getMessage());
        }
        return false;
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

    public void updateStatus(String status, int appointmentID) {
        PreparedStatement stm = null;
        Logger logger = Logger.getLogger(getClass().getName());

        try (Connection connection = getConnection()) {
            stm = connection.prepareStatement("UPDATE Appointment SET Status = ? Where ID = ?;");
            stm.setString(1, status);
            stm.setInt(2, appointmentID);
            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Appointment successfully updated.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating appointment: {0}", e.getMessage());
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AppointmentDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void main(String[] args) {
        AppointmentDAO aO = new AppointmentDAO();
        aO.updateStatus("Cancelled", 4);
    }
}
