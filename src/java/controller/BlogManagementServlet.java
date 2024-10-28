/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.BlogDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Account;
import model.Blog;
import java.sql.SQLException;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author PC
 */
public class BlogManagementServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BlogDAO blogDAO = new BlogDAO();
        List<Blog> blogs;
        try {
            blogs = blogDAO.getAllBlogs();
            request.setAttribute("blogs", blogs);
            request.getRequestDispatcher("blogManagement.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Unable to retrieve blog posts.");
            request.getRequestDispatcher("blogManagement.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Ensure that the user is logged in and is a staff member
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Account account = (Account) session.getAttribute("account");

        // Check if user is staff (RoleID == 3)
        if (account.getRole() != 3) {
            response.sendRedirect("unauthorized.jsp");
            return;
        }

        BlogDAO blogDAO = new BlogDAO();
        String action = request.getParameter("action");

        try {
            if ("create".equals(action)) {
                String title = request.getParameter("title");
                String content = request.getParameter("content");
                String image = request.getParameter("image");

                Blog newBlog = new Blog(0, title, content, account.getPersonInfo().getId(), null, image, account.getPersonInfo().getName());
                blogDAO.createBlog(newBlog);

            } else if ("update".equals(action)) {
                int blogId = Integer.parseInt(request.getParameter("id"));
                String title = request.getParameter("title");
                String content = request.getParameter("content");
                String image = request.getParameter("image");

                Blog updatedBlog = new Blog(blogId, title, content, account.getPersonInfo().getId(), null, image, account.getPersonInfo().getName());
                blogDAO.updateBlog(updatedBlog);

            } else if ("delete".equals(action)) {
                int blogId = Integer.parseInt(request.getParameter("id"));
                blogDAO.deleteBlog(blogId);
            }

            response.sendRedirect("blogManagement"); // Redirect back to the blog management page
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while processing your request.");
            request.getRequestDispatcher("blogManagement.jsp").forward(request, response);
        }
    }
}
