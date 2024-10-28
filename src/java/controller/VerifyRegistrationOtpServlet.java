package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import dal.DBContext;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 *
 * @author PC
 */
public class VerifyRegistrationOtpServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String inputOtp = request.getParameter("otp");
        HttpSession session = request.getSession();
        String storedOtp = (String) session.getAttribute("otp");
        String email = (String) session.getAttribute("email");
        String password = (String) session.getAttribute("password");
        String name = (String) session.getAttribute("name");
        String phone = (String) session.getAttribute("phone");
        int roleID = (int) session.getAttribute("roleID");

        // Lấy số lần nhập OTP sai
        Integer attempts = (Integer) session.getAttribute("otpAttempts");
        if (attempts == null) {
            attempts = 0;
        }

        Connection conn = null;

        try {
            conn = DBContext.getConnection();
            conn.setAutoCommit(false); // Bắt đầu transaction

            if (inputOtp != null && inputOtp.equals(storedOtp)) {
                // OTP đúng, tiến hành thêm dữ liệu vào bảng Person và Account

                // Thêm vào bảng Person
                String insertPersonSql = "INSERT INTO Person (Name, Phone, Email) VALUES (?, ?, ?)";
                try (PreparedStatement insertPersonStmt = conn.prepareStatement(insertPersonSql, Statement.RETURN_GENERATED_KEYS)) {
                    insertPersonStmt.setString(1, name);
                    insertPersonStmt.setString(2, phone);
                    insertPersonStmt.setString(3, email);
                    insertPersonStmt.executeUpdate();

                    ResultSet generatedKeys = insertPersonStmt.getGeneratedKeys();
                    int personID = 0;
                    if (generatedKeys.next()) {
                        personID = generatedKeys.getInt(1); // Lấy ID tự động của Person
                    }

                    // Thêm vào bảng Account
                    String insertAccountSql = "INSERT INTO Account (Username, Password, RoleID, PersonID) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement insertAccountStmt = conn.prepareStatement(insertAccountSql)) {
                        insertAccountStmt.setString(1, email);
                        insertAccountStmt.setString(2, password);
                        insertAccountStmt.setInt(3, roleID);
                        insertAccountStmt.setInt(4, personID);
                        insertAccountStmt.executeUpdate();
                    }

                    conn.commit(); // Commit transaction nếu thành công

                    // Xóa session và chuyển hướng
                    session.removeAttribute("otp");
                    session.removeAttribute("email");
                    session.removeAttribute("password");
                    session.removeAttribute("name");
                    session.removeAttribute("phone");
                    session.removeAttribute("otpAttempts");

                    session.setAttribute("message", "Registration successful! Please log in.");
                    response.sendRedirect("login.jsp");

                } catch (SQLException e) {
                    conn.rollback(); // Rollback nếu có lỗi
                    e.printStackTrace();
                    request.setAttribute("error", "Database error: Unable to complete registration.");
                    request.getRequestDispatcher("OtpConfirmRegistration.jsp").forward(request, response);
                }

            } else {
                // Nếu OTP không đúng
                attempts++;
                session.setAttribute("otpAttempts", attempts);

                if (attempts >= 3) {
                    // Nếu nhập sai OTP quá 3 lần, rollback và xóa Person nếu đã thêm
                    String checkPersonSql = "SELECT ID FROM Person WHERE Email = ?";
                    try (PreparedStatement checkPersonStmt = conn.prepareStatement(checkPersonSql)) {
                        checkPersonStmt.setString(1, email);
                        ResultSet rs = checkPersonStmt.executeQuery();

                        if (rs.next()) {
                            int personID = rs.getInt("ID");

                            // Xóa Person đã thêm
                            String deletePersonSql = "DELETE FROM Person WHERE ID = ?";
                            try (PreparedStatement deleteStmt = conn.prepareStatement(deletePersonSql)) {
                                deleteStmt.setInt(1, personID);
                                deleteStmt.executeUpdate();
                                conn.commit();
                            }
                        }

                        // Xóa session và chuyển hướng đến trang lỗi
                        session.invalidate(); // Clear toàn bộ session
                        request.setAttribute("error", "You have exceeded the maximum OTP attempts. Registration failed.");
                        request.getRequestDispatcher("error.jsp").forward(request, response);

                    } catch (SQLException ex) {
                        conn.rollback();
                        ex.printStackTrace();
                        request.setAttribute("error", "Database error: Unable to delete person.");
                        request.getRequestDispatcher("OtpConfirmRegistration.jsp").forward(request, response);
                    }
                } else {
                    // Nếu chưa quá 3 lần, cho phép thử lại
                    request.setAttribute("error", "Invalid OTP. Please try again.");
                    request.getRequestDispatcher("OtpConfirmRegistration.jsp").forward(request, response);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("OtpConfirmRegistration.jsp").forward(request, response);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

}
