/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Booking;

import com.google.gson.Gson;
import dal.AppointmentDAO;
import dal.ServiceDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Service;
import model.TimeSlot;

/**
 *
 * @author ADMIN
 */
public class GetAvailableTimes extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet getAvailableTimes</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet getAvailableTimes at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<String> availableTimes = null;
            int staffId = Integer.parseInt(request.getParameter("staffId"));
            LocalDate date = LocalDate.parse(request.getParameter("date"));
            LocalDate today = LocalDate.now();
            // Lấy danh sách serviceID từ tham số yêu cầu
            String[] serviceIds = request.getParameter("service").split(",");

            AppointmentDAO appointmentDAO = new AppointmentDAO();
            ServiceDAO serviceDAO = new ServiceDAO();

            // Tính tổng thời gian cho tất cả các dịch vụ được chọn
            int totalDuration = 0;
            for (String serviceIdStr : serviceIds) {
                int serviceID = Integer.parseInt(serviceIdStr);
                Service service = serviceDAO.selectService(serviceID);
                totalDuration += service.getDuration(); // Cộng dồn thời gian của mỗi dịch vụ
            }

            // Lấy các khung giờ đã được đặt trước đó
            List<TimeSlot> busyTimes = appointmentDAO.getBusyTimes(staffId, date);

            // Định nghĩa giờ mở cửa và đóng cửa
            LocalTime openingTime = LocalTime.of(9, 0);
            LocalTime closingTime = LocalTime.of(18, 0);

            // Tính toán các khung giờ trống dựa trên tổng thời gian dịch vụ
            if (date.isEqual(today)) {
                openingTime = openingTime();
                availableTimes = calculateAvailableSlots(totalDuration, openingTime, closingTime, busyTimes);
            } else if(date.isAfter(today)) {
                availableTimes = calculateAvailableSlots(totalDuration, openingTime, closingTime, busyTimes);
            }

            // Gửi các khung giờ trống dưới dạng JSON
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(new Gson().toJson(availableTimes));
            out.flush();

        } catch (SQLException ex) {
            Logger.getLogger(GetAvailableTimes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private List<String> calculateAvailableSlots(int serviceDuration, LocalTime opening, LocalTime closing, List<TimeSlot> busyTimes) throws SQLException {
        List<String> availableSlots = new ArrayList<>();
        LocalTime time = opening;

        // Generate slots in intervals based on service duration, avoiding busy times
        while (time.isBefore(closing.minusMinutes(serviceDuration)) || time.equals(closing.minusMinutes(serviceDuration))) {
            boolean isFree = true;
            for (TimeSlot busy : busyTimes) {
                // Check if current time slot conflicts with any busy time slot
                if (!time.isBefore(busy.getStart()) && time.isBefore(busy.getEnd())) {
                    isFree = false;
                    break;
                }
            }
            if (isFree) {
                availableSlots.add(time.toString());  // Add start time of the available slot
            }
            time = time.plusMinutes(30);  // Move to the next 15-minute interval
        }

        return availableSlots;
    }

    private LocalTime openingTime() {
        LocalTime openingTime = LocalTime.of(10, 0);
        LocalTime closingTime = LocalTime.of(18, 0);
        LocalTime nowTime = LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute());
        List<LocalTime> timeList = timeList(openingTime, closingTime);
        for (LocalTime localTime : timeList) {
            if (nowTime.equals(localTime) || nowTime.isBefore(localTime)) {
                openingTime = localTime;
                return openingTime;
            }
        }
        return openingTime;
    }

    private List<LocalTime> timeList(LocalTime opening, LocalTime closing) {
        List<LocalTime> timeList = new ArrayList<>();
        LocalTime time = opening;
        while (time.isBefore(closing) || time.equals(closing)) {
            timeList.add(time);
            time = time.plusMinutes(30);
        }
        return timeList;
    }

}
