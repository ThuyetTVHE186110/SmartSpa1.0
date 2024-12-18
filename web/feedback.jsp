<%-- 
    Document   : feedback
    Created on : Nov 3, 2024, 10:24:51 PM
    Author     : Asus
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Client Feedback - Blushed Beauty Bar</title>
        <link rel="stylesheet" href="newUI/assets/css/styles.css">
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
    </head>

    <body>
        <!-- Navbar Section -->
        <jsp:include page="NavBarJSP/NavBarJSP.jsp" />

        <!-- Feedback Hero Section -->
        <section class="feedback-hero">
            <div class="hero-content">
                <h1>Client Feedback</h1>
                <p>Share your experience with us</p>
            </div>
        </section>

        <!-- Submit Feedback Section -->
        <section class="submit-feedback">
            <div class="container">
                <h2>Leave Your Feedback</h2>
                <form id="feedback-form" class="feedback-form">
                    <div class="form-row">
                        <div class="form-group">
                            <label for="name">Your Name</label>
                            <input type="text" id="name" required>
                        </div>
                        <div class="form-group">
                            <label for="service">Service Received</label>
                            <select id="service" required>
                                <option value="">Select a service</option>
                                <option value="body-massage">Body Massage</option>
                                <option value="stone-therapy">Stone Therapy</option>
                                <option value="facial-therapy">Facial Therapy</option>
                                <option value="skin-care">Skin Care</option>
                                <option value="stream-">Stream Bath</option>
                                <option value="classic-lash">Classic Lash Extensions</option>
                                <option value="hybrid-lash">Hybrid Lash Extensions</option>
                                <option value="volume-lash">Volume Lash Extensions</option>
                                <option value="lash-lift">Lash Lift</option>
                                <option value="lash-tint">Lash Lift & Tint</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label>Rate Your Experience</label>
                        <div class="rating">
                            <input type="radio" id="star5" name="rating" value="5" required>
                            <label for="star5"><i class="fas fa-star"></i></label>
                            <input type="radio" id="star4" name="rating" value="4">
                            <label for="star4"><i class="fas fa-star"></i></label>
                            <input type="radio" id="star3" name="rating" value="3">
                            <label for="star3"><i class="fas fa-star"></i></label>
                            <input type="radio" id="star2" name="rating" value="2">
                            <label for="star2"><i class="fas fa-star"></i></label>
                            <input type="radio" id="star1" name="rating" value="1">
                            <label for="star1"><i class="fas fa-star"></i></label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="feedback">Your Feedback</label>
                        <textarea id="feedback" rows="5" required></textarea>
                    </div>
                    <div class="form-group">
                        <label for="photo">Add Photos (optional)</label>
                        <input type="file" id="photo" accept="image/*" multiple>
                    </div>
                    <button type="submit" class="submit-btn">Submit Feedback</button>
                </form>
            </div>
        </section>

        <!-- Client Testimonials Section -->
        <section class="testimonial">
            <div class="testimonial-content">
                <h2>Feedback</h2>
                <div class="testimonial-slider-container">
                    <div class="star-decoration top-left"></div>
                    <div class="star-decoration top-right"></div>
                    <button class="slider-control prev" aria-label="Previous testimonial">&lt;</button>
                    
                    <div class="testimonial-slider">
                        <c:forEach items="${feedback}" var="feedback">
                            <div class="testimonial-slide ">
                                <p>"${feedback.content}"</p>
                                <span class="client-name">- ${feedback.customer.name}</span> <br> 
                                <span class="client-name">Service Name: ${feedback.service.name}</span>
                            </div>
                        </c:forEach>

                    </div>
                    <button class="slider-control next" aria-label="Next testimonial">&gt;</button>
                    <div class="star-decoration bottom-left"></div>
                    <div class="star-decoration bottom-right"></div>
                </div>
                <div class="slider-dots"></div>
            </div>
        </section>



        <!-- Footer Section -->
        <footer>
            <!-- [Previous footer code remains the same] -->
        </footer>

        <script>
            document.addEventListener('DOMContentLoaded', () => {
                AOS.init();
                // Hamburger menu functionality
                const hamburger = document.querySelector('.hamburger');
                const navLinks = document.querySelector('.nav-links');
                hamburger.addEventListener('click', () => {
                    navLinks.classList.toggle('active');
                    hamburger.classList.toggle('active');
                });
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
                // Create and activate dots for the slider
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

                prevButton.addEventListener('click', prevSlide);
                nextButton.addEventListener('click', nextSlide);
                // Auto-advance slides
                function startSlideShow() {
                    slideInterval = setInterval(nextSlide, 1000);
                }

                // Pause auto-advance on hover
                const sliderContainer = document.querySelector('.testimonial-slider-container');
                sliderContainer.addEventListener('mouseenter', () => clearInterval(slideInterval));
                sliderContainer.addEventListener('mouseleave', startSlideShow);

                // Initialize the slideshow and start auto-advance
                goToSlide(0);
                startSlideShow();

                function displayTestimonials(testimonials) {
                    const grid = document.querySelector('.testimonial-grid');
                    grid.innerHTML = ''; // Clear previous testimonials
                    testimonials.forEach(testimonial => {
                        grid.appendChild(createTestimonialCard(testimonial));
                    });
                }

            });


        </script>
    </body>

</html>
