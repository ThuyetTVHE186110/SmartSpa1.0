/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.DBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.Random;
import java.sql.Statement;

/**
 *
 * @author PC
 */
public class SignupServlet extends HttpServlet {

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
            out.println("<title>Servlet SignupServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SignupServlet at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
        String action = request.getParameter("signup");

        if ("signup".equals(action)) {
            handleRegistration(request, response);
        } else if ("Reset".equals(action)) {
            handleReset(request, response);
        } else if ("verifyOtp".equals(action)) {
            handleOtpVerification(request, response);
        }
    }

//    private void handleRegistration(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String username = request.getParameter("txtEmail");
//        String phone = request.getParameter("txtPhone");
//        String name = request.getParameter("txtName");
//        String password = request.getParameter("txtPassword");
//        String confirm = request.getParameter("txtConfirmPassword");
//        int roleID = 4;
//
//        // Check if passwords match
//        if (password == null || confirm == null || !password.equals(confirm)) {
//            request.setAttribute("error", "Password does not match the confirm password.");
//            request.setAttribute("txtName", request.getParameter("txtName"));
//            request.setAttribute("txtPhone", request.getParameter("txtPhone"));
//            request.setAttribute("txtEmail", request.getParameter("txtEmail"));
//            request.getRequestDispatcher("signup.jsp").forward(request, response);
//            return;
//        }
//
//        // SQL query to check if the email or phone already exists
//        String checkEmailSql = "SELECT Person.Phone, Account.Username "
//                + "FROM dbo.Person "
//                + "INNER JOIN dbo.Account ON Person.ID = Account.PersonID "
//                + "WHERE Account.Username = ? OR Person.Phone = ?";
//
//        // Check if the email already exists only after passwords match
////        String checkEmailSql = "SELECT * FROM Account WHERE Username = ?";
//        try (Connection conn = DBContext.getConnection(); PreparedStatement checkStmt = conn.prepareStatement(checkEmailSql)) {
//
////            // Check if the email already exists
////            checkStmt.setString(1, username);
////            try (ResultSet rs = checkStmt.executeQuery()) {
////                if (rs.next()) {
////                    request.setAttribute("error", "This email is already registered. Please log in or reset your password.");
////                    request.getRequestDispatcher("login.jsp").forward(request, response);
////                    return;
////                }
////            }
//            // Set parameters for the email and phone
//            checkStmt.setString(1, username);
//            checkStmt.setString(2, phone);
//            try (ResultSet rs = checkStmt.executeQuery()) {
//                if (rs.next()) {
//                    if (rs.getString("Username").equals(username)) {
//                        request.setAttribute("error", "This email is already registered. Please log in or reset your password.");
//                    } else if (rs.getString("Phone").equals(phone)) {
//                        request.setAttribute("error", "This phone number is already registered.");
//                    }
//                    // Giữ lại thông tin nhập vào
//                    request.setAttribute("txtName", name);
//                    request.setAttribute("txtPhone", phone);
//                    request.setAttribute("txtEmail", username);
//                    request.getRequestDispatcher("signup.jsp").forward(request, response);
//                    return;
//                }
//            }
//
////            // If email doesn't exist, proceed with OTP generation
////            String otp = generateOtp();
////            if (sendOtpEmail(username, otp)) {
////                // Store the OTP and other info in session
////                request.getSession().setAttribute("otp", otp);
////                request.getSession().setAttribute("email", username);
////                request.getSession().setAttribute("password", password);
////                request.getSession().setAttribute("roleID", roleID);
////
////                // Redirect to OTP verification page
////                response.sendRedirect("OtpConfirmRegistration.jsp");
////            } else {
////                request.setAttribute("error", "Failed to send OTP. Please try again.");
////                request.getRequestDispatcher("signup.jsp").forward(request, response);
////            }
////
////        } catch (SQLException e) {
////            e.printStackTrace();
////            request.setAttribute("message", "Database error: " + e.getMessage());
////            request.getRequestDispatcher("signup.jsp").forward(request, response);
////        }
////    }
//// If email and phone don't exist, proceed with OTP generation
//            String otp = generateOtp();
//            if (sendOtpEmail(username, otp)) {
//                // Store the OTP and other info in session
//                request.getSession().setAttribute("otp", otp);
//                request.getSession().setAttribute("email", username);
//                request.getSession().setAttribute("phone", phone);
//                request.getSession().setAttribute("name", name);
//                request.getSession().setAttribute("password", password);
//                request.getSession().setAttribute("roleID", roleID);
//
//                // Redirect to OTP verification page
//                response.sendRedirect("OtpConfirmRegistration.jsp");
//            } else {
//                request.setAttribute("error", "Failed to send OTP. Please try again.");
//                request.setAttribute("txtName", name);
//                request.setAttribute("txtPhone", phone);
//                request.setAttribute("txtEmail", username);
//                request.getRequestDispatcher("signup.jsp").forward(request, response);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            request.setAttribute("message", "Database error: " + e.getMessage());
//            request.setAttribute("txtName", name);
//            request.setAttribute("txtPhone", phone);
//            request.setAttribute("txtEmail", username);
//            request.getRequestDispatcher("signup.jsp").forward(request, response);
//        }
//    }
    private void handleRegistration(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("txtEmail");
        String password = request.getParameter("txtPassword");
        String confirm = request.getParameter("txtConfirmPassword");
        String name = request.getParameter("txtName");
        String phone = request.getParameter("txtPhone");
        int roleID = 4;

        // Check if passwords match
        if (password == null || confirm == null || !password.equals(confirm)) {
            request.setAttribute("error", "Password does not match the confirm password.");
            request.setAttribute("txtName", name);
            request.setAttribute("txtPhone", phone);
            request.setAttribute("txtEmail", email);
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }

        String checkEmailPhoneSql = "SELECT Person.Phone, Account.Username FROM Person LEFT JOIN Account ON Person.ID = Account.PersonID WHERE Person.Phone = ? OR Account.Username = ?";
        String insertPersonSql = "INSERT INTO Person (Name, Phone) VALUES (?, ?)";
        String insertAccountSql = "INSERT INTO Account (Username, Password, RoleID, PersonID) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBContext.getConnection(); PreparedStatement checkStmt = conn.prepareStatement(checkEmailPhoneSql); PreparedStatement insertPersonStmt = conn.prepareStatement(insertPersonSql, Statement.RETURN_GENERATED_KEYS); PreparedStatement insertAccountStmt = conn.prepareStatement(insertAccountSql)) {

            // Check if the email or phone already exists
            checkStmt.setString(1, phone);
            checkStmt.setString(2, email);
            boolean phoneExists = false;
            boolean emailExists = false;

            try (ResultSet rs = checkStmt.executeQuery()) {
                while (rs.next()) {
                    String dbPhone = rs.getString("Phone");
                    String dbEmail = rs.getString("Username");

                    if (dbPhone != null && dbPhone.equals(phone)) {
                        phoneExists = true;
                    }

                    if (dbEmail != null && dbEmail.equals(email)) {
                        emailExists = true;
                    }
                }

                if (phoneExists) {
                    request.setAttribute("error", "This phone number is already registered. Please log in or reset your password.");
                    request.getRequestDispatcher("signup.jsp").forward(request, response);
                    return;
                }

                if (emailExists) {
                    request.setAttribute("error", "This email is already registered. Please log in or reset your password.");
                    request.getRequestDispatcher("signup.jsp").forward(request, response);
                    return;
                }
            }

            // Insert into the Person table
            insertPersonStmt.setString(1, name);
            insertPersonStmt.setString(2, phone);
            insertPersonStmt.executeUpdate();

            // Get the generated PersonID
            ResultSet generatedKeys = insertPersonStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int personID = generatedKeys.getInt(1);

                // Insert into the Account table
                insertAccountStmt.setString(1, email);
                insertAccountStmt.setString(2, password);
                insertAccountStmt.setInt(3, roleID);
                insertAccountStmt.setInt(4, personID);
                insertAccountStmt.executeUpdate();

                // Proceed with OTP generation and redirection
                String otp = generateOtp();
                if (sendOtpEmail(email, otp)) {
                    request.getSession().setAttribute("otp", otp);
                    request.getSession().setAttribute("email", email);
                    request.getSession().setAttribute("password", password);
                    request.getSession().setAttribute("roleID", roleID);
                    request.getSession().setAttribute("name", name);
                    request.getSession().setAttribute("phone", phone);

                    response.sendRedirect("OtpConfirmRegistration.jsp");
                } else {
                    request.setAttribute("error", "Failed to send OTP. Please try again.");
                    request.getRequestDispatcher("signup.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("error", "Failed to register. Please try again.");
                request.getRequestDispatcher("signup.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Database error: " + e.getMessage());
            request.getRequestDispatcher("signup.jsp").forward(request, response);
        }
    }

// Helper method to retain input values in the form
    private void retainInput(HttpServletRequest request, String name, String phone, String email) {
        request.setAttribute("txtName", name);
        request.setAttribute("txtPhone", phone);
        request.setAttribute("txtEmail", email);
    }

//    private void handleOtpVerification(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String enteredOtp = request.getParameter("otp");
//        String sessionOtp = (String) request.getSession().getAttribute("otp");
//
//        if (enteredOtp.equals(sessionOtp)) {
//            // Retrieve stored user info
//            String email = (String) request.getSession().getAttribute("email");
//            String password = (String) request.getSession().getAttribute("password");
//            int roleID = (int) request.getSession().getAttribute("roleID");
//
//            // Insert user into database
//            String insertSql = "INSERT INTO Account (Username, Password, RoleID) VALUES (?, ?, ?)";
//            try (Connection conn = DBContext.getConnection(); PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
//                pstmt.setString(1, email);
//                pstmt.setString(2, password);
//                pstmt.setInt(3, roleID);
//                pstmt.executeUpdate();
//
//                // Clear session attributes
//                request.getSession().removeAttribute("otp");
//                request.getSession().removeAttribute("email");
//                request.getSession().removeAttribute("password");
//                request.getSession().removeAttribute("roleID");
//
//                // Redirect to login page
//                request.getSession().setAttribute("message", "Registration successful! Please log in.");
//                response.sendRedirect("login.jsp");
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//                request.setAttribute("message", "Database error: " + e.getMessage());
//                request.getRequestDispatcher("OtpConfirmRegistration.jsp").forward(request, response);
//            }
//        } else {
//            request.setAttribute("error", "Invalid OTP. Please try again.");
//            request.getRequestDispatcher("OtpConfirmRegistration.jsp").forward(request, response);
//        }
//    }
    private void handleOtpVerification(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String enteredOtp = request.getParameter("otp");
        String sessionOtp = (String) request.getSession().getAttribute("otp");

        if (enteredOtp.equals(sessionOtp)) {
            // OTP is valid, proceed with user registration completion
            // Clear session attributes related to OTP after successful verification
            request.getSession().removeAttribute("otp");

            // Redirect user to a different process that handles finalizing the registration
            request.getSession().setAttribute("message", "OTP verified successfully! Now you can complete your registration.");
            response.sendRedirect("CompleteRegistration.jsp"); // A new page that completes the registration

        } else {
            // Invalid OTP, return to the OTP input page with an error message
            request.setAttribute("error", "Invalid OTP. Please try again.");
            request.getRequestDispatcher("OtpConfirmRegistration.jsp").forward(request, response);
        }
    }

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generate a 6-digit OTP
        return String.valueOf(otp);
    }

    private boolean sendOtpEmail(String recipient, String otp) {
        String host = "smtp.gmail.com";
        String from = "no@reply.smartbeauty.com";
        String subject = "Your OTP Code";
        String messageContent = "Your OTP is: " + otp;

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("dat33112@gmail.com", "cdct hlco ymoj wklg"); // Use a secure method for this
            }
        });

        try {
            Message mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(messageContent);
            Transport.send(mimeMessage);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void handleReset(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("signup.jsp");
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
