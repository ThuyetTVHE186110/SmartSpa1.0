<%-- 
    Document   : product
    Created on : Sep 29, 2024, 6:49:34 PM
    Author     : Dell Alienware
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
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
        <style>
            body {
                font-family: Arial, sans-serif;

            }
            .product-container {
                display: flex;
                flex-wrap: wrap;
                justify-content: center;
            }
            .service-item {
                border: 1px solid #ccc;
                border-radius: 5px;
                margin: 50px 10px; /* Tăng khoảng cách giữa các sản phẩm */
                width: calc(33.33% - 20px); /* 3 sản phẩm mỗi dòng */
                box-sizing: border-box;
            }
            .service-item img {
                max-width: 100%;
                height: auto;
            }

            @media (max-width: 800px) {
                .service-item {
                    width: calc(50% - 20px); /* 2 sản phẩm mỗi dòng trên màn hình nhỏ */
                }
            }
            @media (max-width: 500px) {
                .service-item {
                    width: 100%; /* 1 sản phẩm mỗi dòng trên màn hình rất nhỏ */
                }
            }
            .page-number {
                display: inline-block; /* Để có thể sử dụng margin và padding */
                border: 1px solid #ccc; /* Viền cho ô vuông */
                border-radius: 4px; /* Bo góc nhẹ */
                padding: 10px 15px; /* Khoảng cách bên trong */
                text-decoration: none; /* Bỏ gạch chân */
                color: #333; /* Màu chữ */
                transition: background-color 0.3s; /* Hiệu ứng chuyển màu khi hover */
            }

            .page-number:hover {
                background-color: #f0f0f0; /* Màu nền khi hover */
            }
        </style>
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


        <!-- Navbar Start -->
        <div class="container-fluid p-0">
            <nav class="navbar navbar-expand-lg bg-white navbar-light py-3 py-lg-0 px-lg-5">
                <a href="index.html" class="navbar-brand ml-lg-3">
                    <h1 class="m-0 text-primary"><span class="text-dark">SPA</span> Center</h1>
                </a>
                <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse justify-content-between px-lg-3" id="navbarCollapse">
                    <div class="navbar-nav m-auto py-0">
                        <a href="index" class="nav-item nav-link">Home</a>
                        <a href="About.jsp" class="nav-item nav-link">About</a>
                        <a href="product" class="nav-item nav-link active">Product</a>
                        <a href="services" class="nav-item nav-link">Services</a>
                        <a href="price.html" class="nav-item nav-link">Pricing</a>
                        <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown">Pages</a>
                            <div class="dropdown-menu rounded-0 m-0">
                                <a href="appointment" class="dropdown-item">Appointment</a>
                                <a href="opening.html" class="dropdown-item">Open Hours</a>
                                <a href="team.html" class="dropdown-item">Our Team</a>
                                <a href="feedback" class="dropdown-item">Testimonial</a>
                            </div>
                        </div>
                        <a href="contact.html" class="nav-item nav-link">Contact</a>
                    </div>
                    <a href="appointment" class="btn btn-primary d-none d-lg-block">Book Now</a>
                </div>
            </nav>
        </div>
        <!-- Navbar End -->


        <!-- Header Start -->
        <div class="jumbotron jumbotron-fluid bg-jumbotron" style="margin-bottom: 20px;">
            <div class="container text-center py-5">
                <h3 class="text-white display-3 mb-4">Spa & Beauty Products</h3>
                <div class="d-inline-flex align-items-center text-white">
                    <p class="m-0"><a class="text-white" href="">Home</a></p>
                    <i class="far fa-circle px-3"></i>
                    <p class="m-0">Products</p>
                </div>
            </div>
        </div>

        <div style="display: flex; align-items: center; justify-content: center; width: 100%;">
            <div style="flex: 1; text-align: center; padding-left: 350px">
                <span id="searchResult">All Products Found</span>
            </div>
            <form action="searchproduct?index=1" method="post" style="display: flex; align-items: center; margin-left: auto; padding-right: 10px">
                <input class="searchBox" type="text" name="txtSearch" required 
                       style="padding: 5px; border: 1px solid #ccc; border-right: none; width: 250px;" 
                       value="${param.txtSearch}"> <!-- Giữ lại giá trị đã nhập -->
                <input class="searchButton" type="submit" name="btnGo" value="Search" 
                       style="background-color: #F9A392; color: white; border: 1px solid #F9A392; border-left: none; padding: 5px; cursor: pointer;">
            </form>
        </div>

        <div class="product-container">
            <c:forEach items="${listSearch}" var="p">
                <div class="service-item position-relative">
                    <img class="img-fluid" src="${pageContext.request.contextPath}/img/${p.image}" alt="">
                    <div class="service-text text-center">
                        <h4 class="text-white font-weight-medium px-3">${p.name}</h4>
                        <p class="text-white px-3 mb-3">${p.description}</p>
                        <h4 class="text-white font-weight-medium px-3">Price: ${p.price}$</h4>
                        <div class="w-100 bg-white text-center p-4">
                            <a class="btn btn-primary" href="producdetail?id=${p.id}">View Detail</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <div class="paging text-center" style="margin-top: 80px;"> 
            <c:forEach begin="1" end="${endPage}" var="i">
                <a href="searchproduct?txtSearch=${param.txtSearch}&index=${i}" class="page-number">${i}</a>
            </c:forEach>
        </div>

        <!-- Footer Start -->
        <div class="footer container-fluid position-relative bg-dark py-5" style="margin-top: 90px;">
            <div class="container pt-5">
                <div class="row">
                    <div class="col-lg-6 pr-lg-5 mb-5">
                        <a href="index.html" class="navbar-brand">
                            <h1 class="mb-3 text-white"><span class="text-primary">SPA</span> Center</h1>
                        </a>
                        <p>Aliquyam sed elitr elitr erat sed diam ipsum eirmod eos lorem nonumy. Tempor sea ipsum diam  sed clita dolore eos dolores magna erat dolore sed stet justo et dolor.</p>
                        <p><i class="fa fa-map-marker-alt mr-2"></i>123 Street, New York, USA</p>
                        <p><i class="fa fa-phone-alt mr-2"></i>+012 345 67890</p>
                        <p><i class="fa fa-envelope mr-2"></i>info@example.com</p>
                        <div class="d-flex justify-content-start mt-4">
                            <a class="btn btn-lg btn-primary btn-lg-square mr-2" href="#"><i class="fab fa-twitter"></i></a>
                            <a class="btn btn-lg btn-primary btn-lg-square mr-2" href="#"><i class="fab fa-facebook-f"></i></a>
                            <a class="btn btn-lg btn-primary btn-lg-square mr-2" href="#"><i class="fab fa-linkedin-in"></i></a>
                            <a class="btn btn-lg btn-primary btn-lg-square" href="#"><i class="fab fa-instagram"></i></a>
                        </div>
                    </div>
                    <div class="col-lg-6 pl-lg-5">
                        <div class="row">
                            <div class="col-sm-6 mb-5">
                                <h5 class="text-white text-uppercase mb-4">Quick Links</h5>
                                <div class="d-flex flex-column justify-content-start">
                                    <a class="text-white-50 mb-2" href="#"><i class="fa fa-angle-right mr-2"></i>Home</a>
                                    <a class="text-white-50 mb-2" href="#"><i class="fa fa-angle-right mr-2"></i>About Us</a>
                                    <a class="text-white-50 mb-2" href="#"><i class="fa fa-angle-right mr-2"></i>Our Services</a>
                                    <a class="text-white-50 mb-2" href="#"><i class="fa fa-angle-right mr-2"></i>Pricing Plan</a>
                                    <a class="text-white-50" href="#"><i class="fa fa-angle-right mr-2"></i>Contact Us</a>
                                </div>
                            </div>
                            <div class="col-sm-6 mb-5">
                                <h5 class="text-white text-uppercase mb-4">Our Services</h5>
                                <div class="d-flex flex-column justify-content-start">
                                    <a class="text-white-50 mb-2" href="#"><i class="fa fa-angle-right mr-2"></i>Body Massage</a>
                                    <a class="text-white-50 mb-2" href="#"><i class="fa fa-angle-right mr-2"></i>Stone Therapy</a>
                                    <a class="text-white-50 mb-2" href="#"><i class="fa fa-angle-right mr-2"></i>Facial Therapy</a>
                                    <a class="text-white-50 mb-2" href="#"><i class="fa fa-angle-right mr-2"></i>Skin Care</a>
                                    <a class="text-white-50" href="#"><i class="fa fa-angle-right mr-2"></i>Nail Care</a>
                                </div>
                            </div>
                            <div class="col-sm-12 mb-5">
                                <h5 class="text-white text-uppercase mb-4">Newsletter</h5>
                                <div class="w-100">
                                    <div class="input-group">
                                        <input type="text" class="form-control border-light" style="padding: 30px;" placeholder="Your Email Address">
                                        <div class="input-group-append">
                                            <button class="btn btn-primary px-4">Sign Up</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="container-fluid bg-dark text-light border-top py-4" style="border-color: rgba(256, 256, 256, .15) !important;">
            <div class="container">
                <div class="row">
                    <div class="col-md-6 text-center text-md-left mb-3 mb-md-0">
                        <p class="m-0 text-white">&copy; <a href="#">Your Site Name</a>. All Rights Reserved.</p>
                    </div>
                    <div class="col-md-6 text-center text-md-right">
                        <p class="m-0 text-white">Designed by <a href="https://htmlcodex.com">HTML Codex</a></p>
                    </div>
                </div>
            </div>
        </div>
        <!-- Footer End -->


        <!-- Back to Top -->
        <a href="#" class="btn btn-lg btn-primary back-to-top"><i class="fa fa-angle-double-up"></i></a>


        <!-- JavaScript Libraries -->
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
        <script src="lib/easing/easing.min.js"></script>
        <script src="lib/waypoints/waypoints.min.js"></script>
        <script src="lib/counterup/counterup.min.js"></script>
        <script src="lib/owlcarousel/owl.carousel.min.js"></script>
        <script src="lib/tempusdominus/js/moment.min.js"></script>
        <script src="lib/tempusdominus/js/moment-timezone.min.js"></script>
        <script src="lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js"></script>

        <!-- Contact Javascript File -->
        <script src="mail/jqBootstrapValidation.min.js"></script>
        <script src="mail/contact.js"></script>

        <!-- Template Javascript -->
        <script src="js/main.js"></script>
    </body>
</body>
</html>