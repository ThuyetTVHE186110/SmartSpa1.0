<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Account" %> 
<%@ page import="model.Person" %>  <!-- Import Person class -->
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Lash Services - Blushed Beauty Bar</title>
        <link rel="stylesheet" href="newUI/assets/css/styles.css">
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
    </head>

    <body>
        <jsp:include page="NavBarJSP/NavBarJSP.jsp" />

        <!-- Services Hero Section -->
        <section class="services-hero">
            <div class="hero-content">
                <h1>Lash Services</h1>
                <p>Beautiful lashes are just a blink away</p>
            </div>
        </section>

        <!-- Services List Section -->
        <section class="services-list">
            <h2>Extensions</h2>
            <div class="service-grid">
                <div class="service-item" data-aos="fade-up">
                    <img src="newUI/assets/img/classic-lash.jpg" alt="Classic Lash Extensions">
                    <div class="service-info">
                        <h3>Classic Lash Extensions</h3>
                        <p>Full: Starting at $150 | Fill: $65 - $105 and up</p>
                        <div class="service-actions">
                            <a href="service-detail.html" class="view-details">View Details</a>
                            <a href="#" class="book-now">Book Now</a>
                        </div>
                    </div>
                </div>
                <div class="service-item" data-aos="fade-up" data-aos-delay="200">
                    <img src="../assets/img/hybrid-lash.jpg" alt="Hybrid Lash Extensions">
                    <div class="service-info">
                        <h3>Hybrid Lash Extensions</h3>
                        <p>Full: Starting at $170 | Fill: $80 - $130 and up</p>
                        <div class="service-actions">
                            <a href="service-detail.html" class="view-details">View Details</a>
                            <a href="#" class="book-now">Book Now</a>
                        </div>
                    </div>
                </div>
                <div class="service-item" data-aos="fade-up" data-aos-delay="400">
                    <img src="./assets/img/volume-lash.jpg" alt="Volume Lash Extensions">
                    <div class="service-info">
                        <h3>Volume Lash Extensions</h3>
                        <p>Full: $185 | Fill: $85 - $140</p>
                        <div class="service-actions">
                            <a href="service-detail.html" class="view-details">View Details</a>
                            <a href="#" class="book-now">Book Now</a>
                        </div>
                    </div>
                </div>
            </div>

            <h2>Lifts & Tints</h2>
            <div class="service-grid">
                <div class="service-item" data-aos="fade-up">
                    <img src="./assets/img/lash-lift.jpg" alt="Lash Lift">
                    <div class="service-info">
                        <h3>Lash Lift</h3>
                        <p>Starting at $85</p>
                        <div class="service-actions">
                            <a href="#" class="view-details">View Details</a>
                            <a href="#" class="book-now">Book Now</a>
                        </div>
                    </div>
                </div>
                <div class="service-item" data-aos="fade-up" data-aos-delay="200">
                    <img src="./assets/img/lash-lift-tint.jpg" alt="Lash Lift & Tint">
                    <div class="service-info">
                        <h3>Lash Lift & Tint</h3>
                        <p>Starting at $95</p>
                        <div class="service-actions">
                            <a href="#" class="view-details">View Details</a>
                            <a href="#" class="book-now">Book Now</a>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <!-- Client Testimonial Section -->
        <section class="testimonial">
            <div class="testimonial-content">
                <h2>Client Love</h2>
                <div class="testimonial-slider-container">
                    <div class="star-decoration top-left"></div>
                    <div class="star-decoration top-right"></div>
                    <button class="slider-control prev" aria-label="Previous testimonial">&lt;</button>
                    <div class="testimonial-slider">
                        <div class="testimonial-slide active">
                            <p>"I can't say enough wonderful things about Blushed! All of the staff have been so friendly,
                                professional, and welcoming!"</p>
                            <span class="client-name">- Linh</span>
                        </div>
                        <div class="testimonial-slide">
                            <p>"The lash extensions I got here are amazing! They look so natural and last for weeks. I'm
                                definitely coming back!"</p>
                            <span class="client-name">- Sarah</span>
                        </div>
                        <div class="testimonial-slide">
                            <p>"Blushed Beauty Bar is my go-to place for all things beauty. Their facials are absolutely
                                divine!"</p>
                            <span class="client-name">- Emma</span>
                        </div>
                        <div class="testimonial-slide">
                            <p>"I had my eyebrows microbladed here and I'm in love with the results. The attention to detail
                                is incredible!"</p>
                            <span class="client-name">- Olivia</span>
                        </div>
                        <div class="testimonial-slide">
                            <p>"The staff at Blushed Beauty Bar are true professionals. They always make me feel comfortable
                                and beautiful."</p>
                            <span class="client-name">- Sophia</span>
                        </div>
                    </div>
                    <button class="slider-control next" aria-label="Next testimonial">&gt;</button>
                    <div class="star-decoration bottom-left"></div>
                    <div class="star-decoration bottom-right"></div>
                </div>
                <div class="slider-dots"></div>
            </div>
        </section>

        <!-- Call to Action Section -->
        <section class="cta-section">
            <img src="newUI/assets/img/Slogan.png" alt="Slogan">
            <a href="#" class="cta-button">Book Now</a>
        </section>

        <!-- Footer Section -->
        <footer>


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
                    <p><strong>Blushed Beauty Bar</strong></p>
                    <p><i class="fas fa-map-marker-alt"></i> 5177 44th Street S, Fargo, ND</p>
                    <p><i class="fas fa-phone"></i> 218.790.3444</p>
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
                <p>&copy; 2023 Blushed Beauty Bar. All rights reserved.</p>
                <ul class="footer-legal">
                    <li><a href="#">Privacy Policy</a></li>
                    <li><a href="#">Terms of Service</a></li>
                </ul>
            </div>
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

            // Testimonial Slider
            const slides = document.querySelectorAll('.testimonial-slide');
            const dotsContainer = document.querySelector('.slider-dots');
            const prevButton = document.querySelector('.slider-control.prev');
            const nextButton = document.querySelector('.slider-control.next');
            let currentSlide = 0;
            let slideInterval;

            // Create dots
            slides.forEach((_, index) => {
                const dot = document.createElement('span');
                dot.classList.add('dot');
                dot.addEventListener('click', () => goToSlide(index));
                dotsContainer.appendChild(dot);
            });

            const dots = document.querySelectorAll('.dot');

            function goToSlide(n) {
                slides[currentSlide].classList.remove('active');
                dots[currentSlide].classList.remove('active');
                currentSlide = (n + slides.length) % slides.length;
                slides[currentSlide].classList.add('active');
                dots[currentSlide].classList.add('active');
            }

            function nextSlide() {
                goToSlide(currentSlide + 1);
            }

            function prevSlide() {
                goToSlide(currentSlide - 1);
            }

            // Initialize slider
            goToSlide(0);

            // Event listeners for controls
            prevButton.addEventListener('click', () => {
                prevSlide();
                resetInterval();
            });

            nextButton.addEventListener('click', () => {
                nextSlide();
                resetInterval();
            });

            // Auto-advance slides
            function startSlideShow() {
                slideInterval = setInterval(nextSlide, 5000);
            }

            function resetInterval() {
                clearInterval(slideInterval);
                startSlideShow();
            }

            startSlideShow();

            // Pause auto-advance on hover
            const sliderContainer = document.querySelector('.testimonial-slider-container');
            sliderContainer.addEventListener('mouseenter', () => clearInterval(slideInterval));
            sliderContainer.addEventListener('mouseleave', startSlideShow);
        </script>
    </body>

</html>