<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@page contentType="text/html" pageEncoding="UTF-8" %>
        <%@ page import="model.Account" %>
            <%@ page import="model.Person" %> <!-- Import Person class -->

                <!DOCTYPE html>
                <!DOCTYPE html>
                <html lang="en">

                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Beauty Studio & Spa</title>
                    <link rel="stylesheet" href="newUI/assets/css/styles.css">
                    <!-- Add AOS library for scroll animations -->
                    <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
                    <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
                    <!-- Add Font Awesome for icons -->
                    <link rel="stylesheet"
                        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
                </head>

                <body>
                    <% Cookie[] cookies=request.getCookies(); boolean hasVisited=false; if (cookies !=null) { for
                        (Cookie cookie : cookies) { if (cookie.getName().equals("hasVisited")) { hasVisited=true; break;
                        } } } %>

                        <jsp:include page="NavBarJSP/NavBarJSP.jsp" />

                        <!-- Survey Modal -->
                        <c:if test="${!hasVisited}">
                            <div id="surveyModal" class="survey-modal">
                                <div class="survey-content" data-aos="fade-up">
                                    <h2>Welcome to Smart Beauty Spa! 🌟</h2>
                                    <p>Help us customize your beauty journey by answering a few questions.</p>
                                    <div class="survey-actions">
                                        <button class="submit-survey"><a style="text-decoration: none; color: white;"
                                                href="survey.jsp">Take
                                                Survey</a></button>
                                        <button type="button" class="skip-survey" onclick="closeSurvey()">Skip for
                                            Now</button>
                                    </div>
                                </div>
                            </div>
                        </c:if>

                        <!-- Hero Section -->
                        <section class="hero">
                            <div class="hero-text" data-aos="fade-up" data-aos-duration="1000">
                                <h1>Full-Service Beauty Spa</h1>
                                <p>Discover a world of relaxation and beauty</p>
                                <button class="cta-button">Book Your Experience</button>
                            </div>
                        </section>

                        <!-- Services Section -->
                        <section class="services" id="services">
                            <h2 data-aos="fade-up">Our Most Popular Services</h2>
                            <h3 data-aos="fade-up" data-aos-delay="200">Treat Yourself</h3>
                            <div class="service-cards">
                                <div class="service-card" data-aos="fade-up" data-aos-delay="300">
                                    <img src="newUI/assets/img/slashEyes.png" alt="Lash Extensions Icon">
                                    <h4>Lash Extensions</h4>
                                    <p>Wake up beautiful with effortless, fluttery lashes.</p>
                                    <button>Explore Lashes</button>
                                </div>
                                <div class="service-card" data-aos="fade-up" data-aos-delay="400">
                                    <img src="newUI/assets/img/facial.png" alt="Facials Icon">
                                    <h4>Facials</h4>
                                    <p>Revitalize your skin with our luxurious facial treatments.</p>
                                    <button>Discover Facials</button>
                                </div>
                                <div class="service-card" data-aos="fade-up" data-aos-delay="500">
                                    <img src="newUI/assets/img/Microblading.png" alt="Microblading Icon">
                                    <h4>Microblading</h4>
                                    <p>Achieve perfect brows that frame your face beautifully.</p>
                                    <button>Learn More</button>
                                </div>
                            </div>
                        </section>

                        <!-- About Section -->
                        <section class="about" id="about">
                            <div class="about-image" data-aos="fade-right">
                                <img src="newUI/assets/img/main_img1.png" alt="Lash Extensions Image">
                            </div>
                            <div class="about-text" data-aos="fade-left">
                                <h2>Your new favorite beauty bar.</h2>
                                <p>Smart Beauty Bar is a full-service studio and spa serving the Hanoi area
                                    specializes in
                                    handmade eyelash extensions and microblading. We have a passion for beauty, makeup,
                                    and
                                    styling.</p>
                                <button class="cta-button">Discover Our Story</button>
                            </div>
                        </section>

                        <!-- Footer Section -->
                        <footer>
                            <div class="footer-content">
                                <img src="newUI/assets/img/Slogan.png" alt="Slogan">
                                <button class="cta-button">Book Now</button>
                            </div>

                            <div class="footer-links">
                                <div class="footer-column services-list">
                                    <h4>Our Services</h4>
                                    <ul>
                                        <li><a href="#">Lash Services</a></li>
                                        <li><a href="#">Facials & Peels</a></li>
                                        <li><a href="#">Microblading</a></li>
                                        <li><a href="#">SMP Services</a></li>
                                        <li><a href="#">Waxing & Threading</a></li>
                                        <li><a href="#">Makeup Application</a></li>
                                        <li><a href="#">Airbrush Tanning</a></li>
                                        <li><a href="#">Permanent Jewelry</a></li>
                                        <li><a href="#">Ear Piercing & Curating</a></li>
                                    </ul>
                                </div>

                                <div class="footer-column contact-info">
                                    <h4>Contact Us</h4>
                                    <p><strong>Smart Beauty Spa</strong></p>
                                    <p><i class="fas fa-map-marker-alt"></i> FPT University Hanoi, Ha Noi, Viet Nam</p>
                                    <p><i class="fas fa-phone"></i> +84 904 123 456</p>
                                    <div class="social-icons">
                                        <a href="#" aria-label="Facebook"><i class="fab fa-facebook-f"></i></a>
                                        <a href="#" aria-label="Instagram"><i class="fab fa-instagram"></i></a>
                                        <a href="#" aria-label="Twitter"><i class="fab fa-twitter"></i></a>
                                    </div>
                                </div>

                                <div class="footer-column email-signup">
                                    <h4>Stay Connected</h4>
                                    <p>Subscribe for exclusive offers and beauty tips!</p>
                                    <form class="newsletter-form">
                                        <input type="email" placeholder="Enter your email" required>
                                        <button type="submit" class="cta-button">Sign Up</button>
                                    </form>
                                </div>
                            </div>

                            <div class="footer-bottom">
                                <p>&copy; 2023 Smart Beauty Spa. All rights reserved.</p>
                                <ul class="footer-legal">
                                    <li><a href="#">Privacy Policy</a></li>
                                    <li><a href="#">Terms of Service</a></li>
                                </ul>
                            </div>
                        </footer>

                        <div id="recommendationsModal" class="recommendations-modal" style="display: none;">
                            <div class="recommendations-content">
                                <h3>Recommended for You</h3>
                                <ul id="recommendationsList"></ul>
                                <button onclick="closeRecommendations()">Close</button>
                            </div>
                        </div>

                        <script>
                            document.addEventListener('DOMContentLoaded', function () {
                                const surveyForm = document.getElementById('beautySurvey');
                                if (surveyForm) {
                                    surveyForm.addEventListener('submit', function (e) {
                                        e.preventDefault();
                                        submitSurvey(this);
                                    });
                                }
                            });

                            function submitSurvey(form) {
                                const formData = new FormData(form);

                                fetch('SaveSurvey', {
                                    method: 'POST',
                                    body: formData
                                })
                                    .then(response => response.json())
                                    .then(data => {
                                        if (data.success) {
                                            document.cookie = "hasVisited=true; max-age=" + (365 * 24 * 60 * 60);

                                            if (data.recommendations && data.recommendations.length > 0) {
                                                showRecommendations(data.recommendations);
                                            }

                                            closeSurvey();
                                        } else {
                                            alert('Error saving survey: ' + data.message);
                                        }
                                    })
                                    .catch(error => {
                                        console.error('Error:', error);
                                        alert('Error saving survey. Please try again.');
                                    });
                            }

                            function showRecommendations(recommendations) {
                                const modal = document.getElementById('recommendationsModal');
                                const list = document.getElementById('recommendationsList');
                                list.innerHTML = '';

                                recommendations.forEach(service => {
                                    const li = document.createElement('li');
                                    li.textContent = service;
                                    list.appendChild(li);
                                });

                                modal.style.display = 'flex';
                            }

                            function closeRecommendations() {
                                document.getElementById('recommendationsModal').style.display = 'none';
                            }

                            function closeSurvey() {
                                const surveyModal = document.getElementById('surveyModal');
                                if (surveyModal) {
                                    surveyModal.style.display = 'none';
                                    document.cookie = "hasVisited=true; max-age=" + (365 * 24 * 60 * 60);
                                }
                            }

                            AOS.init();
                        </script>
                </body>

                <script>
                    AOS.init();
                </script>

                </html>