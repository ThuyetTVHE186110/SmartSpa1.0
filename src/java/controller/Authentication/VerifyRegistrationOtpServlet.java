package controller.Authentication;

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
import org.mindrot.jbcrypt.BCrypt;

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

        // Check if roleID is present in the session
        Integer roleID = (Integer) session.getAttribute("roleID");
        if (roleID == null) {
            request.setAttribute("error", "Role ID is missing in the session.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        Integer attempts = (Integer) session.getAttribute("otpAttempts");
        if (attempts == null) {
            attempts = 0;
        }

        try (Connection conn = DBContext.getConnection()) {
            conn.setAutoCommit(false);

            if (inputOtp != null && inputOtp.equals(storedOtp)) {
                String insertPersonSql = "INSERT INTO Person (Name, Phone, Email) VALUES (?, ?, ?)";
                try (PreparedStatement insertPersonStmt = conn.prepareStatement(insertPersonSql, Statement.RETURN_GENERATED_KEYS)) {
                    insertPersonStmt.setString(1, name);
                    insertPersonStmt.setString(2, phone);
                    insertPersonStmt.setString(3, email);
                    insertPersonStmt.executeUpdate();

                    ResultSet generatedKeys = insertPersonStmt.getGeneratedKeys();
                    int personID = 0;
                    if (generatedKeys.next()) {
                        personID = generatedKeys.getInt(1);
                    }

                    String insertAccountSql = "INSERT INTO Account (Username, Password, RoleID, PersonID) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement insertAccountStmt = conn.prepareStatement(insertAccountSql)) {
                        insertAccountStmt.setString(1, email);
                        insertAccountStmt.setString(2, password);
                        insertAccountStmt.setInt(3, roleID);
                        insertAccountStmt.setInt(4, personID);
                        insertAccountStmt.executeUpdate();
                    }

                    conn.commit();

                    session.removeAttribute("otp");
                    session.removeAttribute("email");
                    session.removeAttribute("password");
                    session.removeAttribute("name");
                    session.removeAttribute("phone");
                    session.removeAttribute("otpAttempts");

                    session.setAttribute("message", "Registration successful! Please log in.");
                    response.sendRedirect("login.jsp");

                } catch (SQLException e) {
                    conn.rollback();
                    e.printStackTrace();
                    request.setAttribute("error", "Database error: Unable to complete registration.");
                    request.getRequestDispatcher("OtpConfirmRegistration.jsp").forward(request, response);
                }

            } else {
                attempts++;
                session.setAttribute("otpAttempts", attempts);

                if (attempts >= 3) {
                    String checkPersonSql = "SELECT ID FROM Person WHERE Email = ?";
                    try (PreparedStatement checkPersonStmt = conn.prepareStatement(checkPersonSql)) {
                        checkPersonStmt.setString(1, email);
                        ResultSet rs = checkPersonStmt.executeQuery();

                        if (rs.next()) {
                            int personID = rs.getInt("ID");

                            String deletePersonSql = "DELETE FROM Person WHERE ID = ?";
                            try (PreparedStatement deleteStmt = conn.prepareStatement(deletePersonSql)) {
                                deleteStmt.setInt(1, personID);
                                deleteStmt.executeUpdate();
                                conn.commit();
                            }
                        }

                        session.invalidate();
                        request.setAttribute("error", "You have exceeded the maximum OTP attempts. Registration failed.");
                        request.getRequestDispatcher("error.jsp").forward(request, response);

                    } catch (SQLException ex) {
                        conn.rollback();
                        ex.printStackTrace();
                        request.setAttribute("error", "Database error: Unable to delete person.");
                        request.getRequestDispatcher("OtpConfirmRegistration.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("error", "Invalid OTP. Please try again.");
                    request.getRequestDispatcher("OtpConfirmRegistration.jsp").forward(request, response);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("OtpConfirmRegistration.jsp").forward(request, response);
        }
    }

}
