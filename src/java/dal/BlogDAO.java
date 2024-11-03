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
                       b.Image,
                       b.Description,
                       b.Views,
                       b.CommentsCount,
                       b.Category,
                       b.AuthorBio,
                       b.AuthorImage
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
                        rs.getString("Image"),
                        rs.getString("Description"),
                        rs.getInt("Views"),
                        rs.getInt("CommentsCount"),
                        rs.getString("Category"),
                        rs.getString("AuthorBio"),
                        rs.getString("AuthorImage")
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
                                             b.Image,
                                             b.Description,
                                             b.Views,
                                             b.CommentsCount,
                                             b.Category,
                                             b.AuthorBio,
                                             b.AuthorImage
                                FROM Blog b
                                JOIN Person p ON b.StaffID = p.ID
                                JOIN Account a ON a.PersonID = p.ID
                                WHERE a.RoleID in (1,2,3)
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
                        rs.getString("Image"),
                        rs.getString("Description"),
                        rs.getInt("Views"),
                        rs.getInt("CommentsCount"),
                        rs.getString("Category"),
                        rs.getString("AuthorBio"),
                        rs.getString("AuthorImage")
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
                       b.Image,
                       b.Description,
                       b.Views,
                       b.CommentsCount,
                       b.Category,
                       b.AuthorBio,
                       b.AuthorImage
                FROM Blog b
                JOIN Person p ON b.StaffID = p.ID
                JOIN Account a ON a.PersonID = p.ID
                ORDER BY b.DatePosted DESC
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
                            rs.getString("Image"),
                            rs.getString("Description"),
                            rs.getInt("Views"),
                            rs.getInt("CommentsCount"),
                            rs.getString("Category"),
                            rs.getString("AuthorBio"),
                            rs.getString("AuthorImage")
                    );
                    blogs.add(blog);
                }
            }
        }
        return blogs;
    }

    public List<Blog> searchBlogs(String query) throws SQLException {
        List<Blog> blogs = new ArrayList<>();
        String sql = """
                SELECT b.ID,
                       b.Title,
                       b.Content,
                       b.StaffID,
                       b.DatePosted,
                       p.Name AS AuthorName,
                       b.Image,
                       b.Description,
                       b.Views,
                       b.CommentsCount,
                       b.Category,
                       b.AuthorBio,
                       b.AuthorImage
                FROM Blog b
                JOIN Person p ON b.StaffID = p.ID
                JOIN Account a ON a.PersonID = p.ID
                WHERE b.Title LIKE ? OR b.Content LIKE ?
                ORDER BY b.DatePosted DESC
                """;

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            String searchQuery = "%" + query + "%";
            stmt.setString(1, searchQuery);
            stmt.setString(2, searchQuery);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Blog blog = new Blog(
                            rs.getInt("ID"),
                            rs.getString("Title"),
                            rs.getString("Content"),
                            rs.getInt("StaffID"),
                            rs.getDate("DatePosted"),
                            rs.getString("AuthorName"),
                            rs.getString("Image"),
                            rs.getString("Description"),
                            rs.getInt("Views"),
                            rs.getInt("CommentsCount"),
                            rs.getString("Category"),
                            rs.getString("AuthorBio"),
                            rs.getString("AuthorImage")
                    );
                    blogs.add(blog);
                }
            }
        }
        return blogs;
    }

    public void createBlog(Blog blog) throws SQLException {
        String sql = """
            INSERT INTO Blog (Title, Content, StaffID, DatePosted, Image, Description, Category, AuthorImage)
            VALUES (?, ?, ?, GETDATE(), ?, ?, ?, ?)
            """;

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, blog.getTitle());
            stmt.setString(2, blog.getContent());
            stmt.setInt(3, blog.getStaffID()); // StaffID is used to link with the author
            stmt.setString(4, blog.getImage());
            stmt.setString(5, blog.getDescription());
            stmt.setString(6, blog.getCategory());
            stmt.setString(7, blog.getAuthorImage()); // Set author image

            stmt.executeUpdate();
        }
    }

    public void updateBlog(Blog blog) throws SQLException {
        String sql = """
            UPDATE Blog
            SET Title = ?, 
                Content = ?, 
                Description = ?, 
                Category = ?, 
                Image = ?, 
                AuthorImage = ?, 
                DatePosted = ?, 
                Views = ?, 
                CommentsCount = ?
            WHERE ID = ?
            """;

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, blog.getTitle());
            stmt.setString(2, blog.getContent());
            stmt.setString(3, blog.getDescription());
            stmt.setString(4, blog.getCategory());
            stmt.setString(5, blog.getImage());
            stmt.setString(6, blog.getAuthorImage());

            // Convert java.util.Date to java.sql.Date
            stmt.setDate(7, new java.sql.Date(blog.getDatePosted().getTime()));

            stmt.setInt(8, blog.getViews());
            stmt.setInt(9, blog.getCommentsCount());
            stmt.setInt(10, blog.getId());

            stmt.executeUpdate();
        }
    }

    public void deleteBlog(int blogId) throws SQLException {
        String sql = "DELETE FROM Blog WHERE ID = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, blogId);
            stmt.executeUpdate();
        }
    }

    public Blog getBlogById(int blogId) throws SQLException {
        String sql = """
                SELECT b.ID,
                       b.Title,
                       b.Content,
                       b.StaffID,
                       b.DatePosted,
                       p.Name AS AuthorName,
                       b.Image,
                       b.Description,
                       b.Views,
                       b.CommentsCount,
                       b.Category,
                       b.AuthorBio,
                       b.AuthorImage
                FROM Blog b
                JOIN Person p ON b.StaffID = p.ID
                JOIN Account a ON a.PersonID = p.ID
                WHERE b.ID = ?
                """;

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, blogId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Blog(
                            rs.getInt("ID"),
                            rs.getString("Title"),
                            rs.getString("Content"),
                            rs.getInt("StaffID"),
                            rs.getDate("DatePosted"),
                            rs.getString("AuthorName"),
                            rs.getString("Image"),
                            rs.getString("Description"),
                            rs.getInt("Views"),
                            rs.getInt("CommentsCount"),
                            rs.getString("Category"),
                            rs.getString("AuthorBio"),
                            rs.getString("AuthorImage")
                    );
                }
            }
        }
        return null;
    }

    public void incrementViews(int blogId) throws SQLException {
        String sql = "UPDATE Blog SET Views = Views + 1 WHERE ID = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, blogId);
            stmt.executeUpdate();
        }
    }

    public List<Blog> getRelatedPosts(int blogId, String category) throws SQLException {
        List<Blog> relatedPosts = new ArrayList<>();
        String sql = """
        SELECT TOP 3 b.ID, b.Title, b.Image, b.DatePosted, p.Name AS AuthorName
        FROM Blog b
        JOIN Person p ON b.StaffID = p.ID
        WHERE b.Category = ? AND b.ID != ? 
        ORDER BY b.DatePosted DESC
    """;

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category);
            stmt.setInt(2, blogId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Blog blog = new Blog();
                    blog.setId(rs.getInt("ID"));
                    blog.setTitle(rs.getString("Title"));
                    blog.setImage(rs.getString("Image"));
                    blog.setDatePosted(rs.getDate("DatePosted"));
                    blog.setAuthorName(rs.getString("AuthorName"));  // Set the author name
                    relatedPosts.add(blog);
                }
            }
        }
        return relatedPosts;
    }

    public List<Blog> getBlogsByStaffID(int staffID) throws SQLException {
        List<Blog> blogs = new ArrayList<>();
        String sql = """
        SELECT b.ID,
                       b.Title,
                       b.Content,
                       b.DatePosted,
                       b.Image,
                       b.Views,
                       b.CommentsCount,
                       b.Category,
                       b.Description,
                       p.Name AS AuthorName,  -- Use the name from Person table as AuthorName
                       b.AuthorImage
                FROM Blog b
                JOIN Person p ON b.StaffID = p.ID
                WHERE b.StaffID = ?
                ORDER BY b.DatePosted DESC
    """;

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, staffID);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Blog blog = new Blog();
                    blog.setId(rs.getInt("ID"));
                    blog.setTitle(rs.getString("Title"));
                    blog.setContent(rs.getString("Content"));
                    blog.setDatePosted(rs.getDate("DatePosted"));
                    blog.setImage(rs.getString("Image"));
                    blog.setDescription(rs.getString("Description"));  // Retrieve description here
                    blog.setViews(rs.getInt("Views"));
                    blog.setCommentsCount(rs.getInt("CommentsCount"));
                    blog.setCategory(rs.getString("Category"));
                    blog.setAuthorName(rs.getString("AuthorName"));
                    blog.setAuthorImage(rs.getString("AuthorImage"));

                    blogs.add(blog);
                }
            }
        }
        return blogs;
    }

    public List<String> getAllCategories() throws SQLException {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT DISTINCT Category FROM Blog";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                categories.add(rs.getString("Category"));
            }
        }
        return categories;
    }

    public List<Blog> getBlogsByCategory(String category) throws SQLException {
        List<Blog> blogs = new ArrayList<>();
        String sql = """
        SELECT b.ID,
               b.Title,
               b.Content,
               b.StaffID,
               b.DatePosted,
               p.Name AS AuthorName,
               b.Image,
               b.Description,
               b.Views,
               b.CommentsCount,
               b.Category,
               b.AuthorBio,
               b.AuthorImage
        FROM Blog b
        JOIN Person p ON b.StaffID = p.ID
        JOIN Account a ON a.PersonID = p.ID
        WHERE b.Category = ?
        ORDER BY b.DatePosted DESC
    """;

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Blog blog = new Blog(
                            rs.getInt("ID"),
                            rs.getString("Title"),
                            rs.getString("Content"),
                            rs.getInt("StaffID"),
                            rs.getDate("DatePosted"),
                            rs.getString("AuthorName"),
                            rs.getString("Image"),
                            rs.getString("Description"),
                            rs.getInt("Views"),
                            rs.getInt("CommentsCount"),
                            rs.getString("Category"),
                            rs.getString("AuthorBio"),
                            rs.getString("AuthorImage")
                    );
                    blogs.add(blog);
                }
            }
        }
        return blogs;
    }

    public List<Blog> searchBlogs2(String keyword) throws SQLException {
        List<Blog> blogs = new ArrayList<>();
        String sql = """
            SELECT b.ID,
                   b.Title,
                   b.Content,
                   b.StaffID,
                   b.DatePosted,
                   p.Name AS AuthorName,
                   b.Image,
                   b.Description,
                   b.Views,
                   b.CommentsCount,
                   b.Category,
                   b.AuthorBio,
                   b.AuthorImage
            FROM Blog b
            JOIN Person p ON b.StaffID = p.ID
            JOIN Account a ON a.PersonID = p.ID
            WHERE p.Name LIKE ? OR b.Title LIKE ? OR b.Content LIKE ? OR b.Description LIKE ?
            ORDER BY b.DatePosted DESC
            """;

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            String searchPattern = "%" + keyword + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);
            stmt.setString(4, searchPattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Blog blog = new Blog(
                            rs.getInt("ID"),
                            rs.getString("Title"),
                            rs.getString("Content"),
                            rs.getInt("StaffID"),
                            rs.getDate("DatePosted"),
                            rs.getString("AuthorName"),
                            rs.getString("Image"),
                            rs.getString("Description"),
                            rs.getInt("Views"),
                            rs.getInt("CommentsCount"),
                            rs.getString("Category"),
                            rs.getString("AuthorBio"),
                            rs.getString("AuthorImage")
                    );
                    blogs.add(blog);
                }
            }
        }
        return blogs;
    }

}
