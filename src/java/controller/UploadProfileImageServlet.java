/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.PersonDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import model.Person;
import java.sql.SQLException;

@MultipartConfig
public class UploadProfileImageServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Person person = (Person) session.getAttribute("person");

        if (person == null) {
            response.sendRedirect("adminLogin.jsp");
            return;
        }

        // Get uploaded file
        Part filePart = request.getPart("profileImage");
        String fileName = filePart.getSubmittedFileName();
        String uploadPath = getServletContext().getRealPath("/") + "img";

        // Ensure upload directory exists
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        // Save file to directory
        filePart.write(uploadPath + File.separator + fileName);

        // Update image in database and session
        try {
            PersonDAO personDAO = new PersonDAO();
            personDAO.updateImage(person.getId(), fileName);  // Update in the database
            person.setImage(fileName);  // Update in session object
            session.setAttribute("person", person);  // Update session with modified person
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("errorPage.jsp");  // Handle error if necessary
            return;
        }

        // Redirect back to profile page to view updated image
        response.sendRedirect("userProfile.jsp?tab=edit");
    }
}
