<%-- 
    Document   : testimonial
    Created on : Sep 29, 2024, 9:55:22 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
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
                <a href="." class="navbar-brand ml-lg-3">
                    <h1 class="m-0 text-primary"><span class="text-dark">SPA</span> Center</h1>
                </a>
                <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse justify-content-between px-lg-3" id="navbarCollapse">
                    <div class="navbar-nav m-auto py-0">
                        <a href="." class="nav-item nav-link">Home</a>
                        <a href="about.jsp" class="nav-item nav-link">About</a>
                        <a href="product" class="nav-item nav-link">Product</a>
                        <a href="services" class="nav-item nav-link">Services</a>
                        <a href="price.jsp" class="nav-item nav-link">Pricing</a>
                        <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle active" data-toggle="dropdown">Pages</a>
                            <div class="dropdown-menu rounded-0 m-0">
                                <a href="appointment.jsp" class="dropdown-item">Appointment</a>
                                <a href="opening.jsp" class="dropdown-item">Open Hours</a>
                                <a href="team.jsp" class="dropdown-item">Our Team</a>
                                <a href="feedback" class="dropdown-item active">Testimonial</a>
                            </div>
                        </div>
                        <a href="contact.jsp" class="nav-item nav-link">Contact</a>
                    </div>
                    <a href="login.jsp" class="nav-item nav-link">Login</a> 
                    <a href="appointment" class="btn btn-primary d-none d-lg-block">Book Now</a>
                </div>
            </nav>
        </div>
        <!-- Navbar End -->


        <!-- Header Start -->
        <div class="jumbotron jumbotron-fluid bg-jumbotron" style="margin-bottom: 90px;">
            <div class="container text-center py-5">
                <h3 class="text-white display-3 mb-4">Testimonial</h3>
                <div class="d-inline-flex align-items-center text-white">
                    <p class="m-0"><a class="text-white" href="">Home</a></p>
                    <i class="far fa-circle px-3"></i>
                    <p class="m-0">Testimonial</p>
                </div>
            </div>
        </div>
        <!-- Header End -->


        <!-- Testimonial Start -->

        <div class="container-fluid py-5">
            <div class="container py-5">
                <div class="row align-items-center">
                    <div class="col-lg-6 pb-5 pb-lg-0">
                        <img class="img-fluid w-100" src="img/testimonial.jpg" alt="">
                    </div>
                    <div class="col-lg-6">
                        <h6 class="d-inline-block text-primary text-uppercase bg-light py-1 px-2">Testimonial</h6>
                        <h1 class="mb-4">What Our Clients Say!</h1>


                        <div class="owl-carousel testimonial-carousel"> 
                            <c:forEach items="${feedback}" var="feedback">
                                <div class="position-relative">
                                    <i class="fa fa-3x fa-quote-right text-primary position-absolute" style="top: -6px; right: 0;"></i>
                                    <div class="d-flex align-items-center mb-3">
                                        <img class="img-fluid rounded-circle" src="img/testimonial-3.jpg" style="width: 60px; height: 60px;" alt="">
                                        <div class="ml-3">
                                            <h6 class="text-uppercase">${feedback.customer.name}</h6>
                                            <span>${feedback.service.name}</span>
                                        </div>
                                    </div>
                                    <p class="m-0">${feedback.content}</p>
                                    <div class="mt-3">
                                        <button class="btn btn-sm btn-info" onclick="editFeedback(${feedback.id}, '${feedback.content}')">Edit</button>
                                        <button class="btn btn-sm btn-danger" onclick="confirmDelete(${feedback.id})">Delete</button>
                                        <!--<button class="btn btn-sm btn-secondary" onclick="showAddForm(${feedback.customer.id})">Add</button>-->
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Testimonial End -->
        <!--Edit Feedback Modal-->  
        <div id="editFeedbackModal" style="display:none; text-align: center;">
            <h2>Edit Feedback</h2>

            <input type="text" id="editContent" placeholder="Edit feedback content">
            <input type="hidden" id="editId" value="">
            <button onclick="submitEdit()">Submit</button>
            <button onclick="closeEditModal()">Close</button>
        </div>

        <!-- Add Feedback Modal -->
        <div id="addFeedbackModal" style="display:none; text-align: center;">
            <h2>Add Feedback</h2>
            <form id="addFeedbackForm" action="feedback" method="post">
                <input type="hidden" id="addCustomerId" name="customerId"> <!-- Hidden field for customer ID -->
                <input type="hidden" id="addServiceId" name="serviceId"> <!-- Hidden field for service ID -->
                <textarea id="addContent" name="content" placeholder="Enter feedback content" required></textarea>
                <input type="hidden" name="action" value="add"> <!-- Action to specify add -->
                <button type="submit">Add</button>
                <button onclick="closeAddModal()">Close</button>
            </form>
        </div>

        <!-- Footer Start -->
        <div class="footer container-fluid position-relative bg-dark py-5" style="margin-top: 90px;">
            <div class="container pt-5">
                <div class="row">
                    <div class="col-lg-6 pr-lg-5 mb-5">
                        <a href="." class="navbar-brand">
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

                    </div>
                </div>
            </div>
        </div>
        <!-- Footer End -->

        <script>
            function confirmDelete(id) {
                if (confirm("Are you sure you want to delete this feedback?")) {
                    // AJAX call to delete feedback
                    $.post('feedback', {action: 'delete', id: id}, function () {
                        location.reload(); // Reload the page to reflect changes
                    });
                }
            }

            function editFeedback(id, content) {
                document.getElementById('editContent').value = content;
                document.getElementById('editId').value = id;
                document.getElementById('editFeedbackModal').style.display = 'block';
            }

            function submitEdit() {
                const id = document.getElementById('editId').value;
                const content = document.getElementById('editContent').value;

                $.post('feedback', {action: 'edit', id: id, content: content}, function () {
                    location.reload(); // Reload the page to see changes
                });
            }
            function closeEditModal() {
                document.getElementById('editFeedbackModal').style.display = 'none';
            }

            function showAddForm(customerId, serviceId) {
                document.getElementById('addCustomerId').value = customerId; // Set the customer ID
                document.getElementById('addServiceId').value = serviceId; // Set the service ID
                document.getElementById('addFeedbackModal').style.display = 'block'; // Show the modal
            }

            function submitAdd(event) {
                event.preventDefault(); // Prevent the default form submission

                const customerId = document.getElementById('addCustomerId').value;
                const serviceId = document.getElementById('addServiceId').value;
                const content = document.getElementById('addContent').value;

                $.post('feedback', {action: 'add', customerId: customerId, serviceId: serviceId, content: content})
                        .done(function () {
                            alert("Feedback added successfully!"); // Optionally show a success message
                            location.reload(); // Reload the page to see changes
                        })
                        .fail(function () {
                            alert("An error occurred while adding feedback. Please try again.");
                        });
            }

            function closeAdđModal() {
                document.getElementById('addFeedbackModal').style.display = 'none';
            }

            // Attach the submitAdd function to the form submission
            document.getElementById('addFeedbackForm').addEventListener('submit', submitAdd);

        </script>

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
</html>
