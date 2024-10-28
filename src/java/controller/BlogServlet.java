/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.BlogDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Blog;
import java.sql.SQLException;

/**
 *
 * @author PC
 */
@WebServlet(name = "BlogServlet", urlPatterns = {"/blog"})
public class BlogServlet extends HttpServlet {

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        BlogDAO blogDAO = new BlogDAO();
//        List<Blog> blogs;
//        List<Blog> recentBlogs;
//        int page = 1;
//        int blogsPerPage = 4;
//
//        // Get the page number from the request, default to 1 if not provided
//        String pageParam = request.getParameter("page");
//        if (pageParam != null && !pageParam.isEmpty()) {
//            page = Integer.parseInt(pageParam);
//        }
//
//        try {
//            int totalBlogs = blogDAO.getBlogCount(); // Method to get total number of blogs
//            int totalPages = (int) Math.ceil((double) totalBlogs / blogsPerPage);
//            // Fetch blogs for the current page
//            blogs = blogDAO.getBlogsForPage(page, blogsPerPage);
//            request.setAttribute("blogs", blogs);
//            blogs = blogDAO.getAllBlogs();
//            recentBlogs = blogDAO.getRecentBlogs();
//            request.setAttribute("blogs", blogs);
//            request.setAttribute("recentBlogs", recentBlogs);
//            
//            
//            request.setAttribute("currentPage", page);
//            request.setAttribute("totalPages", totalPages);
//            
//            
//            request.getRequestDispatcher("blog.jsp").forward(request, response);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            // Set an error message if an exception occurs
//            request.setAttribute("error", "Unable to retrieve blog posts.");
//            // Forward to error page or show an error message on the same page
//            request.getRequestDispatcher("blog.jsp").forward(request, response);
//        }
//    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BlogDAO blogDAO = new BlogDAO();
        List<Blog> blogs;
        List<Blog> recentBlogs;
        String query = request.getParameter("query");
        int page = 1;
        int blogsPerPage = 4;

        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            page = Integer.parseInt(pageParam);
        }

        try {
            if (query != null && !query.trim().isEmpty()) {
                // Perform search if a query is provided
                blogs = blogDAO.searchBlogs(query);
            } else {
                // Otherwise, paginate the blogs as usual
                int totalBlogs = blogDAO.getBlogCount();
                int totalPages = (int) Math.ceil((double) totalBlogs / blogsPerPage);
                blogs = blogDAO.getBlogsForPage(page, blogsPerPage);

                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);
            }

            recentBlogs = blogDAO.getRecentBlogs();
            request.setAttribute("blogs", blogs);
            request.setAttribute("recentBlogs", recentBlogs);

            request.getRequestDispatcher("blog.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Unable to retrieve blog posts.");
            request.getRequestDispatcher("blog.jsp").forward(request, response);
        }
    }

}
