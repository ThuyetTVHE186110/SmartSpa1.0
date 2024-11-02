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
    private static final String UPDATE_APPOINTMENT = "UPDATE Appointment SET AppointmentTime = ?, AppointmentDate = ?, CreatedDate = ?, Status = ?, Note = ?, CustomerID = ? WHERE ID = ?";
    private static final String BUSY_TIME = """
                                            SELECT Cast(a.Start as Time) as Start, Cast(a.[End] as Time) as [End]
                                            FROM Appointment a
                                            JOIN Appointment_Service asvc ON a.ID = asvc.AppointmentID
                                            WHERE asvc.StaffID = ? AND a.Status <> 'Completed' AND Cast(a.Start as Date) = ? AND Cast(a.[End] as Date) = ?""";
    private final PersonDAO personDAO = new PersonDAO();
    private final AppointmentServiceDAO serviceDAO = new AppointmentServiceDAO();

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

    public List<TimeSlot> getFreeTimeSlots(LocalTime opening, LocalTime closing, List<TimeSlot> busyTimes) {
        List<TimeSlot> freeSlots = new ArrayList<>();
        // Sort busy times by start time
        busyTimes.sort((t1, t2) -> t1.getStart().compareTo(t2.getStart()));

        // Initial slot before the first busy time
        if (opening.isBefore(busyTimes.get(0).getStart())) {
            freeSlots.add(new TimeSlot(opening, busyTimes.get(0).getStart()));
        }

        // Gaps between busy times
        for (int i = 0; i < busyTimes.size() - 1; i++) {
            LocalTime endCurrent = busyTimes.get(i).getEnd();
            LocalTime startNext = busyTimes.get(i + 1).getStart();
            if (endCurrent.isBefore(startNext)) {
                freeSlots.add(new TimeSlot(endCurrent, startNext));
            }
        }

        // Slot after the last busy time
        if (busyTimes.get(busyTimes.size() - 1).getEnd().isBefore(closing)) {
            freeSlots.add(new TimeSlot(busyTimes.get(busyTimes.size() - 1).getEnd(), closing));
        }

        return freeSlots;
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

        String SELECT_APPOINTMENTS_BY_DATE = "SELECT * FROM Appointment WHERE appointmentDate = ? ORDER BY appointmentDate DESC, appointmentTime DESC";

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
     * @throws java.sql.SQLException
     */
    public void addAppointment(Appointment appointment) throws SQLException {
        Logger logger = Logger.getLogger(getClass().getName());
        PreparedStatement stm = null;
        try (Connection connection = getConnection()) {
            stm = connection.prepareStatement(INSERT_APPOINTMENT);
            stm.setTimestamp(1, Timestamp.valueOf(appointment.getStart()));
            stm.setTimestamp(2, Timestamp.valueOf(appointment.getEnd()));
            stm.setTimestamp(3, Timestamp.valueOf(appointment.getCreatedDate()));   // Ngày tạo cuộc hẹn
            stm.setString(4, appointment.getStatus());                              // Trạng thái cuộc hẹn
            stm.setString(5, appointment.getNote());                                // Ghi chú
            stm.setInt(6, appointment.getCustomer().getId());                         // ID của người tham gia

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

//    public void updateAppointment(Appointment appointment) throws SQLException {
//        PreparedStatement stm = null;
//        Logger logger = Logger.getLogger(getClass().getName());
//
//        try (Connection connection = getConnection()) {
//            stm = connection.prepareStatement(UPDATE_APPOINTMENT);
//            stm.setTime(1, Time.valueOf(appointment.getAppointmentTime()));
//            stm.setDate(2, java.sql.Date.valueOf(appointment.getAppointmentDate()));
//            stm.setTimestamp(3, Timestamp.valueOf(appointment.getCreatedDate()));
//            stm.setString(4, appointment.getStatus());
//            stm.setString(5, appointment.getNote());
//            stm.setInt(6, appointment.getCustomer().getId());
//            stm.setInt(7, appointment.getId());
//
//            int rowsAffected = stm.executeUpdate();
//            if (rowsAffected > 0) {
//                logger.info("Appointment successfully updated.");
//            }
//
//        } catch (SQLException e) {
//            logger.log(Level.SEVERE, "Error updating appointment: {0}", e.getMessage());
//        } finally {
//            if (stm != null) {
//                stm.close();
//            }
//        }
//    }

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
        try {
            AppointmentDAO appointmentDAO = new AppointmentDAO();
            
            Person customer = new Person();
            customer.setId(39);
            Appointment appointment = new Appointment();
            LocalDate date = LocalDate.of(2024, 11, 1);
            LocalTime time = LocalTime.now();
            LocalDateTime start = LocalDateTime.of(date, time);
            appointment.setStart(start);
            appointment.setEnd(start.plusMinutes(60));
            appointment.setCreatedDate(LocalDateTime.now());
            appointment.setStatus("Scheduled");
            appointment.setNote("hello1");
            appointment.setCustomer(customer);
            appointmentDAO.addAppointment(appointment);
            Service service = new Service();
            service.setId(1);
            AppointmentServiceDAO appointmentServiceDAO = new AppointmentServiceDAO();
            int appointmentID = appointmentDAO.getMaxAppointmentID();
            appointmentServiceDAO.addAppointmentService(appointmentID, service.getId(), 8);
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
