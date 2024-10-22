/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.PersonDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Person;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author PC
 */
public class UserProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Giả sử bạn có personId từ request
        int personId = Integer.parseInt(request.getParameter("personId"));
        
        PersonDAO personDAO = new PersonDAO();
        Person person = personDAO.getPersonById(personId);  // Lấy một person từ database dựa vào ID
        
        // Đặt đối tượng Person vào session
        HttpSession session = request.getSession();
        session.setAttribute("person", person);  // Đặt thông tin Person vào session
        
        // Chuyển tiếp tới JSP
        request.getRequestDispatcher("userProfile.jsp").forward(request, response);
    }
}
