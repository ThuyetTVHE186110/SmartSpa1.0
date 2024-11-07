<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@page contentType="text/html" pageEncoding="UTF-8" %>
        <%@ page import="model.Account" %>
            <%@ page import="model.Person" %> <!-- Import Person class -->
                <!DOCTYPE html>
                <html lang="en">

                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>About Us - Blushed Beauty Bar</title>
                    <link rel="stylesheet" href="newUI/assets/css/styles.css">
                    <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
                    <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
                    <link rel="stylesheet"
                        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
                </head>

                <body>
                    <jsp:include page="NavBarJSP/NavBarJSP.jsp" />


                    <!-- About Hero Section -->
                    <section class="about-hero">
                        <div class="hero-content">
                            <h1>Our Story</h1>
                            <p>Beauty with a personal touch</p>
                        </div>
                    </section>

                    <!-- Main About Section -->
                    <section class="about-main">
                        <div class="about-container">
                            <div class="about-intro" data-aos="fade-up">
                                <h2>Welcome to Smart Beauty Bar</h2>
                                <p>Founded in 2024, Smart Beauty Bar has grown from a small lash studio into Hanoi's
                                    premier beauty
                                    destination. Our passion for beauty and commitment to excellence drives everything
                                    we do.</p>
                            </div>

                            <div class="about-grid">
                                <div class="about-image" data-aos="fade-right">
                                    <img src="newUI/assets/img/about_mission.png" alt="Smart Beauty Bar Interior">
                                </div>
                                <div class="about-text" data-aos="fade-left">
                                    <h3>Our Mission</h3>
                                    <p>At Smart Beauty Bar, we believe that everyone deserves to feel beautiful and
                                        confident. Our
                                        mission is to provide exceptional beauty services in a welcoming, relaxing
                                        environment where
                                        clients can truly unwind and transform.</p>
                                </div>
                            </div>
                        </div>
                    </section>

                    <!-- Values Section -->
                    <section class="values-section">
                        <div class="values-container">
                            <h2 data-aos="fade-up">Our Core Values</h2>
                            <div class="values-grid">
                                <div class="value-item" data-aos="fade-up">
                                    <i class="fas fa-heart"></i>
                                    <h3>Passion</h3>
                                    <p>We love what we do and it shows in every service we provide.</p>
                                </div>
                                <div class="value-item" data-aos="fade-up" data-aos-delay="100">
                                    <i class="fas fa-star"></i>
                                    <h3>Excellence</h3>
                                    <p>We maintain the highest standards in both service and safety.</p>
                                </div>
                                <div class="value-item" data-aos="fade-up" data-aos-delay="200">
                                    <i class="fas fa-users"></i>
                                    <h3>Community</h3>
                                    <p>We create meaningful connections with our clients and community.</p>
                                </div>
                                <div class="value-item" data-aos="fade-up" data-aos-delay="300">
                                    <i class="fas fa-gem"></i>
                                    <h3>Quality</h3>
                                    <p>We use only premium products and cutting-edge techniques.</p>
                                </div>
                            </div>
                        </div>
                    </section>

                    <!-- Team Section -->
                    <section class="team-section">
                        <div class="team-container">
                            <h2 data-aos="fade-up">Meet Our Team</h2>
                            <div class="team-grid">
                                <div class="team-member" data-aos="fade-up">
                                    <img src="newUI/assets/img/team-member-1.jpg" alt="Team Member">
                                    <h3>Tran Van Thuyet</h3>
                                    <p class="position">Founder & Master Lash Artist</p>
                                    <p class="bio">With over 8 years of experience, Thuyet leads our team with passion
                                        and expertise.</p>
                                </div>
                                <div class="team-member" data-aos="fade-up" data-aos-delay="100">
                                    <img src="./assets/img/team-member-2.jpg" alt="Team Member">
                                    <h3>Nguyen Duc Chien</h3>
                                    <p class="position">Senior Esthetician</p>
                                    <p class="bio">Chien specializes in creating customized skincare experiences for our
                                        clients.</p>
                                </div>
                                <div class="team-member" data-aos="fade-up" data-aos-delay="200">
                                    <img src="./assets/img/team-member-3.jpg" alt="Team Member">
                                    <h3>Bui Duc Chuong</h3>
                                    <p class="position">Microblading Artist</p>
                                    <p class="bio">Chuong's attention to detail creates perfect, natural-looking brows.
                                    </p>
                                </div>
                                <div class="team-member" data-aos="fade-up" data-aos-delay="300">
                                    <img src="./assets/img/team-member-4.jpg" alt="Team Member">
                                    <h3>Nguyen Huu Thang</h3>
                                    <p class="position">Advanced Lash Artist</p>
                                    <p class="bio">Thang's artistic eye creates stunning, customized lash looks for
                                        every client.</p>
                                </div>
                                <div class="team-member" data-aos="fade-up" data-aos-delay="400">
                                    <img src="./assets/img/team-member-5.jpg" alt="Team Member">
                                    <h3>Le Thai Duong</h3>
                                    <p class="position">Skincare Specialist</p>
                                    <p class="bio">Duong's expertise in advanced skincare treatments helps clients
                                        achieve their best
                                        skin.</p>
                                </div>
                            </div>
                        </div>
                    </section>

                    <!-- Location Section -->
                    <section class="location-section">
                        <div class="location-container">
                            <div class="location-info" data-aos="fade-right">
                                <h2>Visit Us</h2>
                                <p><i class="fas fa-map-marker-alt"></i> FPT University Hanoi, Ha Noi, Viet Nam</p>
                                <p><i class="fas fa-phone"></i> +84 904 123 456</p>
                                <div class="hours">
                                    <h3>Hours</h3>
                                    <p>Monday - Friday: 9am - 7pm</p>
                                    <p>Saturday: 9am - 5pm</p>
                                    <p>Sunday: Closed</p>
                                </div>
                                <button class="book-now-btn"><a href="booking.jsp">Book an Appointment</a></button>
                            </div>
                            <div class="location-map" data-aos="fade-left">
                                <!-- Replace with your actual Google Maps embed code -->

                                <iframe
                                    src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3724.5063419425237!2d105.5227142752561!3d21.01241668063284!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3135abc60e7d3f19%3A0x2be9d7d0b5abcbf4!2sFPT%20University!5e0!3m2!1sen!2s!4v1730994830317!5m2!1sen!2s"
                                    width="100%" height="450" style="border:0;" allowfullscreen=""
                                    loading="lazy"></iframe>
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