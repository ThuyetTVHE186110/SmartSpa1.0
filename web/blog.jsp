<%@ page import="java.util.List" %>
<%@ page import="model.Blog" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Blog - Blushed Beauty Bar</title>
        <link rel="stylesheet" href="newUI/assets/css/styles.css">
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
    </head>

    <body>
        <!-- Navbar Section -->
        <jsp:include page="NavBarJSP/NavBarJSP.jsp" />

        <!-- Blog Hero Section -->
        <section class="blog-hero">
            <div class="hero-content">
                <h1>Beauty Blog</h1>
                <p>Tips, tricks, and beauty inspiration</p>
            </div>
        </section>

        <!-- Blog Content Section -->
        <section class="blog-content">
            <div class="blog-container">
                <!-- Featured Post -->
                <!-- Check if there are blogs to display -->
                <c:choose>
                    <c:when test="${not empty blogs}">
                        <!-- Loop through each blog in the list -->
                        <c:forEach var="blog" items="${blogs}" varStatus="status">
                            <!-- Display the first blog as featured, then the rest as regular posts -->
                            <c:if test="${status.first}">
                                <!-- Featured Post -->
                                <article class="featured-post" data-aos="fade-up">
                                    <div class="post-image">
                                        <img src="${pageContext.request.contextPath}/newUI/assets/img/${blog.image}" 
                                             alt="${blog.title}">
                                        <div class="post-category">Featured</div>
                                    </div>
                                    <div class="post-content">
                                        <div class="post-meta">
                                            <span class="post-date"><fmt:formatDate value="${blog.datePosted}" pattern="MMMM d, yyyy" /></span>
                                            <span class="post-author">By ${blog.authorName}</span>
                                        </div>
                                        <h2>${blog.title}</h2>
                                        <p>${fn:substring(blog.content, 0, 150)}...</p>
                                        <a href="blogDetails?id=${blog.id}" class="read-more">Read More <i class="fas fa-arrow-right"></i></a>
                                    </div>
                                </article>
                            </c:if>     
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <p>No blog posts found for your search.</p>
                    </c:otherwise>
                </c:choose>


                <!-- Blog Grid for Remaining Posts -->
                <div class="blog-grid">
                    <c:forEach var="blog" items="${blogs}">
                        <article class="blog-post" data-aos="fade-up">
                            <div class="post-image">
                                <img src="${pageContext.request.contextPath}/newUI/assets/img/${blog.image}" 
                                     alt="${blog.title}">
                                <div class="post-category">Category</div>
                            </div>
                            <div class="post-content">
                                <div class="post-meta">
                                    <span class="post-date">
                                        <fmt:formatDate value="${blog.datePosted}" pattern="MMMM d, yyyy" />
                                    </span>
                                    <span class="post-author">By ${blog.authorName}</span>
                                </div>
                                <h3>${blog.title}</h3>
                                <p>${fn:substring(blog.content, 0, 100)}...</p>
                                <a href="blogDetails?id=${blog.id}" class="read-more">Read More <i class="fas fa-arrow-right"></i></a>
                            </div>
                        </article>
                    </c:forEach>
                </div>

                <!-- Pagination -->
                <div class="pagination">
                    <c:if test="${currentPage > 1}">
                        <a href="?page=${currentPage - 1}">Previous</a>
                    </c:if>
                    <c:forEach var="i" begin="1" end="${totalPages}">
                        <a href="?page=${i}" class="${i == currentPage ? 'active' : ''}">${i}</a>
                    </c:forEach>
                    <c:if test="${currentPage < totalPages}">
                        <a href="?page=${currentPage + 1}">Next</a>
                    </c:if>
                </div>

            </div>

            <!-- Sidebar -->
            <aside class="blog-sidebar">
                <!-- Search Widget -->
                <div class="sidebar-widget search-widget">
                    <form class="search-form" action="blog" method="GET">
                        <input type="search" name="query" placeholder="Search blog...">
                        <button type="submit"><i class="fas fa-search"></i></button>
                    </form>
                </div>
                <c:choose>
                    <c:when test="${not empty blogs}">
                        <!-- Loop and display blogs as before -->
                        <c:forEach var="blog" items="${blogs}" varStatus="status">
                            <!-- Blog post display logic here -->
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <p>No blog posts found for your search.</p>
                    </c:otherwise>
                </c:choose>


<!--                 Categories Widget 
                <div class="sidebar-widget categories-widget">
                    <h3>Categories</h3>
                    <ul>
                        <li><a href="#">Skincare <span>(12)</span></a></li>
                        <li><a href="#">Makeup <span>(8)</span></a></li>
                        <li><a href="#">Lash Extensions <span>(15)</span></a></li>
                        <li><a href="#">Microblading <span>(6)</span></a></li>
                        <li><a href="#">Self Care <span>(10)</span></a></li>
                    </ul>
                </div>-->

                <!-- Recent Posts Widget -->
                <div class="sidebar-widget recent-posts-widget">
                    <h3>Recent Posts</h3>
                    <ul>
                        <c:forEach var="recentBlog" items="${recentBlogs}">
                            <li>
                                <img src="${pageContext.request.contextPath}/newUI/assets/img/${recentBlog.image}" alt="${recentBlog.title}">
                                <div class="post-info">
                                    <h4><a href="blogDetails?id=${recentBlog.id}">${recentBlog.title}</a></h4>
                                    <span><fmt:formatDate value="${recentBlog.datePosted}" pattern="MMMM d, yyyy" /></span>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>

            </aside>
        </section>

        <!-- Footer Section -->
        <footer>
            <!-- Your existing footer content -->
        </footer>

        <script>
            AOS.init();

            // Hamburger menu functionality
            const hamburger = document.querySelector('.hamburger');
            const navLinks = document.querySelector('.nav-links');

            hamburger.addEventListener('click', () => {
                navLinks.classList.toggle('active');
                hamburger.classList.toggle('active');
            });

            // Close menu when a link is clicked
            document.querySelectorAll('.nav-links a').forEach(link => {
                link.addEventListener('click', () => {
                    navLinks.classList.remove('active');
                    hamburger.classList.remove('active');
                });
            });
        </script>
    </body>

</html>