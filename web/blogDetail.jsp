<%@ page import="model.Blog" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>The Ultimate Guide to Lash Extensions - Blushed Beauty Bar</title>
        <link rel="stylesheet" href="newUI/assets/css/styles.css">
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
    </head>

    <body>
        <!-- Navbar Section -->
        <jsp:include page="NavBarJSP/NavBarJSP.jsp" />

        <!-- Blog Post Header -->
        <section class="blog-post-header">
            <div class="container">
                <div class="post-meta">
                    <span class="category">${blog.category}</span>
                    <span class="date"><fmt:formatDate value="${blog.datePosted}" pattern="MMMM d, yyyy" /></span>
                    <span class="author">By ${blog.authorName}</span>
                </div>
                <h1>${blog.title}</h1>
                <div class="post-stats">
                    <span><i class="far fa-clock"></i> Estimated read time here</span>
                    <span><i class="far fa-eye"></i> ${blog.views} views</span>
                    <span><i class="far fa-comment"></i> ${blog.commentsCount} comments</span>
                </div>
            </div>
        </section>

        <!-- Blog Post Content -->
        <section class="blog-post-content">
            <div class="container">
                <div class="post-main">
                    <!-- Featured Image -->
                    <!-- Featured Image -->
                    <div class="post-image">
                        <img src="${pageContext.request.contextPath}/newUI/assets/img/${blog.image}" alt="${blog.title}">
                    </div>

                    <!-- Description -->
                    <article>
                        <p>${blog.description}</p>
                    </article>

                    <!-- Article Content -->
                    <article class="post-body" data-aos="fade-up">
                        <!-- Main Content -->
                        <article>
                            <p>${blog.content}</p>
                        </article>



                        <!-- Call to Action -->
                        <div class="post-cta" data-aos="fade-up">
                            <h3>Ready to Transform Your Lashes?</h3>
                            <p>Book your lash extension appointment today and experience the magic!</p>
                            <a href="booking" class="cta-button">Book Now</a>
                        </div>
                    </article>

                    <!-- Author Bio -->
                    <div class="author-bio">
                        <img src="${pageContext.request.contextPath}/newUI/assets/img/${blog.authorImage}" alt="${blog.authorName}">
                        <div class="author-info">
                            <h3>${blog.authorName}</h3>
                            <p>${blog.authorBio}</p>
                        </div>
                    </div>

                    <!-- Comments Section -->
                    <div class="comments-section" data-aos="fade-up">
                        <h2>Comments (8)</h2>
                        <div class="comment-form">
                            <h3>Leave a Comment</h3>
                            <form id="comment-form">
                                <div class="form-group">
                                    <label for="comment-name">Name</label>
                                    <input type="text" id="comment-name" required>
                                </div>
                                <div class="form-group">
                                    <label for="comment-email">Email</label>
                                    <input type="email" id="comment-email" required>
                                </div>
                                <div class="form-group">
                                    <label for="comment-text">Comment</label>
                                    <textarea id="comment-text" rows="4" required></textarea>
                                </div>
                                <button type="submit" class="submit-btn">Post Comment</button>
                            </form>
                        </div>

                        <div class="comments-list">
                            <!-- Sample comments -->
                            <div class="comment">
                                <img src="./assets/img/avatars/user1.jpg" alt="User Avatar">
                                <div class="comment-content">
                                    <div class="comment-header">
                                        <h4>Emily White</h4>
                                        <span>2 days ago</span>
                                    </div>
                                    <p>This guide is so helpful! I've been considering getting lash extensions and now I
                                        feel much
                                        more confident about the process.</p>
                                    <button class="reply-btn">Reply</button>
                                </div>
                            </div>
                            <!-- More comments... -->
                        </div>
                    </div>
                </div>

                <!-- Related Posts -->
                <div class="related-posts" data-aos="fade-up">
                    <h2>Related Posts</h2>
                    <div class="related-posts-grid">
                        <c:forEach var="relatedPost" items="${relatedPosts}">
                            <article class="related-post">
                                <img src="${pageContext.request.contextPath}/newUI/assets/img/${relatedPost.image}" alt="${relatedPost.title}">
                                <h3>${relatedPost.title}</h3>
                                <span class="post-author">By ${relatedPost.authorName}</span>
                                <a href="blogDetails?id=${relatedPost.id}" class="read-more">Read More <i class="fas fa-arrow-right"></i></a>
                            </article>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </section>

        <!-- Footer Section -->
        <footer>
            <!-- [Previous footer code remains the same] -->
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

            // Comment form submission
            document.getElementById('comment-form').addEventListener('submit', async (e) => {
                e.preventDefault();

                const commentData = {
                    name: document.getElementById('comment-name').value,
                    email: document.getElementById('comment-email').value,
                    comment: document.getElementById('comment-text').value
                };

                try {
                    // Submit comment to backend
                    await submitComment(commentData);
                    alert('Comment submitted successfully!');
                    e.target.reset();
                } catch (error) {
                    console.error('Error submitting comment:', error);
                    alert('Error submitting comment. Please try again.');
                }
            });

            // Image gallery lightbox
            const galleryImages = document.querySelectorAll('.image-gallery img');
            galleryImages.forEach(img => {
                img.addEventListener('click', () => {
                    // Implement lightbox functionality
                });
            });
        </script>
    </body>

</html> 