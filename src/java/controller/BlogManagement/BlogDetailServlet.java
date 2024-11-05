/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.BlogManagement;

import dal.BlogDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Blog;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author PC
 */
public class BlogDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BlogDAO blogDAO = new BlogDAO();

        // Get the blog ID from the URL parameter
        String blogIdParam = request.getParameter("id");
        if (blogIdParam == null || blogIdParam.isEmpty()) {
            response.sendRedirect("blog"); // Redirect to main blog page if no ID is specified
            return;
        }

        try {
            int blogId = Integer.parseInt(blogIdParam);

            // Fetch the blog details by ID
            Blog blog = blogDAO.getBlogById(blogId);

            if (blog != null) {
                // Increment the view count
                blogDAO.incrementViews(blogId);
                blog.setViews(blog.getViews() + 1); // Update the view count in the blog object
                List<Blog> relatedPosts = blogDAO.getRelatedPosts(blogId, blog.getCategory());

                // Set blog as a request attribute
                request.setAttribute("blog", blog);

                request.setAttribute("relatedPosts", relatedPosts);
                request.getRequestDispatcher("blogDetail.jsp").forward(request, response);
            } else {
                // If blog not found, redirect to an error page or show an error message
                response.sendRedirect("error");
            }

        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error"); // Redirect to an error page in case of exception
        }
    }
}
