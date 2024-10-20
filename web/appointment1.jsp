<%-- 
    Document   : booking
    Created on : Oct 5, 2024, 8:41:13 PM
    Author     : ADMIN
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Account" %> <!-- Add this line to import the Account class -->

<!DOCTYPE html>
<html>
    <head>
        <title></title>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet"/>
        <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&display=swap" rel="stylesheet"/>
        <link href="css/service-menu.css" rel="stylesheet">
    </head>
    <body>
    <head>
        <meta charset="utf-8">
        <title>SPA Center - Beauty & Spa HTML Template</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <meta content="Free HTML Templates" name="keywords">
        <meta content="Free HTML Templates" name="description">

        <!-- Favicon -->
        <link href="img/favicon.ico" rel="icon">

        <!-- Google Web Fonts -->
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">

        <!-- Font Awesome -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">

        <!-- Libraries Stylesheet -->
        <link href="lib/animate/animate.min.css" rel="stylesheet">
        <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
        <link href="lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet" />

        <!-- Customized Bootstrap Stylesheet -->
        <link href="css/style.css" rel="stylesheet">
    </head>

    <body>
        <!-- Topbar Start -->
        <div class="container-fluid bg-light d-none d-lg-block">
            <div class="row py-2 px-lg-5">
                <div class="col-lg-6 text-left mb-2 mb-lg-0">
                    <div class="d-inline-flex align-items-center">
                        <small><i class="fa fa-phone-alt mr-2"></i>+012 345 6789</small>
                        <small class="px-3">|</small>
                        <small><i class="fa fa-envelope mr-2"></i>info@example.com</small>
                    </div>
                </div>
                <div class="col-lg-6 text-right">
                    <div class="d-inline-flex align-items-center">
                        <a class="text-primary px-2" href="">
                            <i class="fab fa-facebook-f"></i>
                        </a>
                        <a class="text-primary px-2" href="">
                            <i class="fab fa-twitter"></i>
                        </a>
                        <a class="text-primary px-2" href="">
                            <i class="fab fa-linkedin-in"></i>
                        </a>
                        <a class="text-primary px-2" href="">
                            <i class="fab fa-instagram"></i>
                        </a>
                        <a class="text-primary pl-2" href="">
                            <i class="fab fa-youtube"></i>
                        </a>
                    </div>
                </div>
            </div>
        </div>
        <!-- Topbar End -->


        <!-- Include the Navbar -->
        <jsp:include page="NavBarJSP/NavBarJSP.jsp" />

        <!-- Header Start -->
        <div class="jumbotron jumbotron-fluid bg-jumbotron" style="margin-bottom: 90px;">
            <div class="container text-center py-5">
                <h3 class="text-white display-3 mb-4">Appointment</h3>
                <div class="d-inline-flex align-items-center text-white">
                    <p class="m-0"><a class="text-white" href="">Home</a></p>
                    <i class="far fa-circle px-3"></i>
                    <p class="m-0">Appointment</p>
                </div>
            </div>
        </div>
        <!-- Header End -->

        <div class="container">
            <div class="services-nav">
                <a class="active nav-link" data-target="packages">Packages</a>
                <a class="nav-link" data-target="massages">Massages</a>
                <a class="nav-link" data-target="skin-care">Skin Care</a>
                <!--                <a class="nav-link" data-target="packages">Advanced Beauty</a>
                                <a class="nav-link" data-target="packages">Body Services</a>
                                <a class="nav-link" data-target="packages">Facials</a>
                                <a class="nav-link" data-target="packages">Wellness or Integrative</a>-->
            </div>
            <!--<h2>SERVICES</h2>-->
            <h3 id="packages">PACKAGES</h3>
            <div class="packages">
                <c:forEach items="${requestScope.serviceList}" var="service">
                    <div class="package">
                        <h3>${service.name}</h3>
                        <p class="price" >${service.duration} min · $${service.price}.00</p>
                        <p>${service.description}</p>
                        <a class="book-btn" href="pick-time?service=${service.id}">Book</a>
                    </div>
                </c:forEach>
            </div>
            <!--            <h3 id="massages">MASSAGES</h3>
                        <div class="massages">
                            <div class="massage">
                                <h3>1</h3>
                                <p class="price">1 hr · $120.00</p>
                                <p>A classic Swedish massage to relax and rejuvenate your body.</p>
                                <a class="book-btn" href="#">Book</a>
                            </div>
                            <div class="massage">
                                <h3>2</h3>
                                <p class="price">1 hr · $140.00</p>
                                <p>A deep tissue massage to relieve muscle tension and stress.</p>
                                <a class="book-btn" href="#">Book</a>
                            </div>
                            <div class="massage">
                                <h3>2</h3>
                                <p class="price">1 hr · $140.00</p>
                                <p>A deep tissue massage to relieve muscle tension and stress.</p>
                                <a class="book-btn" href="#">Book</a>
                            </div>
                            <div class="massage">
                                <h3>2</h3>
                                <p class="price">1 hr · $140.00</p>
                                <p>A deep tissue massage to relieve muscle tension and stress.</p>
                                <a class="book-btn" href="#">Book</a>
                            </div>
                            <div class="massage">
                                <h3>2</h3>
                                <p class="price">1 hr · $140.00</p>
                                <p>A deep tissue massage to relieve muscle tension and stress.</p>
                                <a class="book-btn" href="#">Book</a>
                            </div>
                            <div class="massage">
                                <h3>2</h3>
                                <p class="price">1 hr · $140.00</p>
                                <p>A deep tissue massage to relieve muscle tension and stress.</p>
                                <a class="book-btn" href="#">Book</a>
                            </div>
                            <div class="massage">
                                <h3>2</h3>
                                <p class="price">1 hr · $140.00</p>
                                <p>A deep tissue massage to relieve muscle tension and stress.</p>
                                <a class="book-btn" href="#">Book</a>
                            </div>
                            <div class="massage">
                                <h3>2</h3>
                                <p class="price">1 hr · $140.00</p>
                                <p>A deep tissue massage to relieve muscle tension and stress.</p>
                                <a class="book-btn" href="#">Book</a>
                            </div>
                            <div class="massage">
                                <h3>2</h3>
                                <p class="price">1 hr · $140.00</p>
                                <p>A deep tissue massage to relieve muscle tension and stress.</p>
                                <a class="book-btn" href="#">Book</a>
                            </div>
                        </div>
                        <h3 id="skin-care">SKIN CARE</h3>
                        <div class="skin-care">
                            <div class="skin-care-item">
                                <h3>Facial Treatment</h3>
                                <p class="price">1 hr · $100.00</p>
                                <p>A rejuvenating facial treatment to refresh your skin.</p>
                                <a class="book-btn" href="#">Book</a>
                            </div>
                            <div class="skin-care-item">
                                <h3>Anti-Aging Treatment</h3>
                                <p class="price">1 hr · $150.00</p>
                                <p>An anti-aging treatment to reduce wrinkles and fine lines.</p>
                                <a class="book-btn" href="#">Book</a>
                            </div>
                        </div>-->
        </div>
        <script>
            // Get all the navigation links
            const navLinks = document.querySelectorAll('.services-nav a');
            // Loop through each link and add a click event listener
            navLinks.forEach(link => {
                link.addEventListener('click', function () {
                    // Remove 'active' class from all links
                    navLinks.forEach(link => link.classList.remove('active'));

                    // Add 'active' class to the clicked link
                    this.classList.add('active');
                    const targetId = this.getAttribute('data-target');
                    const targetElement = document.getElementById(targetId);

                    // Scroll to the target element smoothly
                    targetElement.scrollIntoView({
                        behavior: 'smooth'
                    });
                });
            });
        </script>
    </body>
</html>