/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Booking;


import com.google.gson.Gson;
import dal.ReferralDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import model.Referral;

/**
 *
 * @author ADMIN
 */
public class GetReferral extends HttpServlet {

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
            out.println("<title>Servlet NewServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NewServlet at " + request.getContextPath() + "</h1>");
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
        String code = request.getParameter("code");

    // Kiểm tra nếu code không rỗng
    if (code != null && !code.trim().isEmpty()) {
        // Tạo đối tượng DAO để kiểm tra Referral
        ReferralDAO referralDAO = new ReferralDAO();
        Referral referral = referralDAO.getReferralByCode(code);
        // Thiết lập response để trả về JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            // Kiểm tra Referral tồn tại
            if (referral != null) {
                // Nếu Referral tồn tại, trả về thông tin
                Map<String, Integer> responseObject = new HashMap<>();
                responseObject.put("message", referral.getValue()); // Lấy giá trị từ đối tượng Referral
                
                // Chuyển đổi đối tượng responseObject thành JSON và gửi về client
                String jsonResponse = new Gson().toJson(responseObject);
                out.print(jsonResponse);
            } else {
                // Nếu không tồn tại, trả về lỗi
                Map<String, String> responseObject = new HashMap<>();
                responseObject.put("message", "Mã không hợp lệ");

                // Chuyển đổi đối tượng responseObject thành JSON và gửi về client
                String jsonResponse = new Gson().toJson(responseObject);
                out.print(jsonResponse);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace(); // Xử lý lỗi nếu cần
        }
    
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

}
