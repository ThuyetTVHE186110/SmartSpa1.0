/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

/**
 *
 * @author PC
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Blog;

public class BlogDAO {

    public List<Blog> getAllBlogs() throws SQLException {
        List<Blog> blogs = new ArrayList<>();
        String sql = """
                 SELECT b.ID, 
                            b.Title, 
                            b.Content, 
                            b.StaffID, 
                            b.DatePosted, 
                            p.Name AS AuthorName,
                            b.Image  -- Make sure Image column is included
                     FROM Blog b
                     JOIN Person p ON b.StaffID = p.ID
                     JOIN Account a ON a.PersonID = p.ID
                     WHERE a.RoleID = 3
                 """;

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Blog blog = new Blog(
                        rs.getInt("ID"),
                        rs.getString("Title"),
                        rs.getString("Content"),
                        rs.getInt("StaffID"),
                        rs.getDate("DatePosted"),
                        rs.getString("AuthorName"),
                        rs.getString("Image")// Get the author name from the query
                );
                blogs.add(blog);
            }
        }
        return blogs;
    }

    public List<Blog> getRecentBlogs() throws SQLException {
        List<Blog> recentBlogs = new ArrayList<>();
        String sql = """
        SELECT TOP 3 b.ID, 
                     b.Title, 
                     b.Content, 
                     b.StaffID, 
                     b.DatePosted, 
                     p.Name AS AuthorName, 
                     b.Image
        FROM Blog b
        JOIN Person p ON b.StaffID = p.ID
        JOIN Account a ON a.PersonID = p.ID
        WHERE a.RoleID = 3
        ORDER BY b.DatePosted DESC
    """;

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Blog blog = new Blog(
                        rs.getInt("ID"),
                        rs.getString("Title"),
                        rs.getString("Content"),
                        rs.getInt("StaffID"),
                        rs.getDate("DatePosted"),
                        rs.getString("AuthorName"),
                        rs.getString("Image")
                );
                recentBlogs.add(blog);
            }
        }
        return recentBlogs;
    }

    public int getBlogCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Blog";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public List<Blog> getBlogsForPage(int page, int blogsPerPage) throws SQLException {
        List<Blog> blogs = new ArrayList<>();
        String sql = """
                 SELECT b.ID, 
                                             b.Title, 
                                             b.Content, 
                                             b.StaffID, 
                                             b.DatePosted, 
                                             p.Name AS AuthorName,
                                             b.Image  -- Make sure Image column is included
                                      FROM Blog b
                                      JOIN Person p ON b.StaffID = p.ID
                                      JOIN Account a ON a.PersonID = p.ID
                 ORDER BY DatePosted DESC
                 OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
                 """;

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            int offset = (page - 1) * blogsPerPage;
            stmt.setInt(1, offset);
            stmt.setInt(2, blogsPerPage);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Blog blog = new Blog(
                            rs.getInt("ID"),
                            rs.getString("Title"),
                            rs.getString("Content"),
                            rs.getInt("StaffID"),
                            rs.getDate("DatePosted"),
                            rs.getString("AuthorName"),
                            rs.getString("Image") // Ensure Blog has an Image field in the constructor
                    );
                    blogs.add(blog);
                }
            }
        }

        return blogs;
    }

}
