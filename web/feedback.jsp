<%-- 
    Document   : feedback
    Created on : Nov 3, 2024, 10:24:51 PM
    Author     : Asus
--%>

<%@page import="model.Service"%>
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
                <h2 style="color: black">Leave Your Feedback</h2>
                <c:choose>
                    <c:when test="${not empty sessionScope.account}">
                        <form id="feedback-form" class="feedback-form" action="feedback" method="post">
                            <div class="form-row">
                                <div class="form-group">
                                    <label for="service">Service Received</label>
                                    <select id="service" name="service" required>
                                        <option value="">Select a service</option>
                                        <c:forEach items="${service}" var="service">
                                            <option value="${service.id}">${service.name}</option>
                                        </c:forEach>
                                    </select>
                                    
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="content">Your Feedback</label>
                                <textarea id="content" name="content" rows="5" required></textarea>
                            </div>                   
                            <button type="submit" class="submit-btn">Submit Feedback</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <p>You need to login to add feedback.</p>
                        <a href="login">Login</a>
                    </c:otherwise>
                </c:choose>
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
                <button id="load-more" class="load-more-btn" onclick="showFeedbackContainer()">Load More Reviews</button>
            </div>
        </section>

        <!-- Footer Section -->
        <footer>
            <!-- [Previous footer code remains the same] -->
            <!-- Container chứa tất cả feedback trong footer -->
            <div id="allFeedbackContainer" class="feedback-container" style="display: none;">
                <h2 style="color: brown">All Feedback</h2>
                <div class="feedback-list">
                    <hr>
                    <c:forEach items="${feedback}" var="feedback">
                        <div class="feedback-item">
                            <span class="client-name">-Customer Name: ${feedback.customer.name}</span><br>
                            <p>Feedback: "${feedback.content}"</p>
                            <span class="service-name">Service: ${feedback.service.name}</span>
                        </div>
                        <hr>
                    </c:forEach>
                </div>
            </div>

            <!-- CSS cho container và phân trang -->
            <style>
                .feedback-container {
                    padding: 20px;
                    background-color: #f8f9fa;
                    border-top: 1px solid #ddd;
                    max-width: 80%;
                    margin: auto;
                }
                .feedback-item {
                    margin-bottom: 15px;
                }
                
            </style>
        </footer>

        <script>

            // Hiển thị container feedback
            function showFeedbackContainer() {
                document.getElementById("allFeedbackContainer").style.display = "block";
            }

            //sessionn
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
                    slideInterval = setInterval(nextSlide, 3000);
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
