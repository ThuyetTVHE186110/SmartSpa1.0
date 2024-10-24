<%-- 
    Document   : pick-time
    Created on : Oct 6, 2024, 10:39:05 PM
    Author     : ADMIN
--%>

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
        <!-- Libraries Stylesheet -->
        <link href="lib/animate/animate.min.css" rel="stylesheet">
        <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
        <link href="lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet" />
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet"/>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f9f9f9;
                margin: 0;
                padding: 0;
            }
            .container {
                width: 80%;
                margin: 0 auto;
                padding: 20px;
            }
            .back {
                color: #a67c52;
                font-size: 18px;
                cursor: pointer;
            }
            .back i {
                margin-right: 5px;
            }
            .header {
                text-align: center;
                margin: 20px 0;
                font-size: 24px;
                color: #333;
            }
            .appointment {
                display: flex;
                align-items: center;
                margin-bottom: 20px;
            }
            .appointment img {
                width: 50px;
                height: 50px;
                border-radius: 50%;
                margin-right: 15px;
            }
            .appointment-details {
                background-color: #fff;
                padding: 15px;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                display: flex;
                align-items: center;
                justify-content: space-between;
                width: 100%;
            }
            .appointment-info {
                display: flex;
                align-items: center;
            }
            .appointment-info div {
                margin-left: 10px;
            }
            .appointment-info h3 {
                margin: 0;
                font-size: 16px;
                color: #333;
            }
            .appointment-info p {
                margin: 5px 0;
                color: #666;
            }
            .appointment-info a {
                color: #a67c52;
                text-decoration: none;
            }
        </style>
        <link href="css/style.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <div class="back">
                <i class="fas fa-arrow-left">
                </i>
                Back
            </div>
            <div class="header">
                YOUR APPOINTMENT
            </div>
            <div class="appointment">
                <div class="appointment-details">
                    <div class="appointment-info">
                        <img height="50" src="" alt="Logo Service" width="50"/>
                        <div>

                            <h3>
                                ${service.name}
                            </h3>
                            <p>
                                with ... staff
                            </p>
                            <a href="#">
                                change
                            </a>
                        </div>
                    </div>
                    <div>
                        ${service.duration} min
                    </div>
                </div>
            </div>
            <!-- Appointment Start -->
            <div class="container-fluid py-5">
                <div class="container py-5">
                    <div class="row justify-content-center bg-appointment mx-0">
                        <div class="col-lg-6 py-5">
                            <div class="p-5 my-5" style="background: rgba(33, 30, 28, 0.7);">
                                <form action="pick-time" method="post">
                                    <input type="hidden" value="${service.id}" name="serviceID"> 
                                    <div class="form-row">
                                        <div class="col-sm-12">
                                            <div class="form-group">
                                                <input type="text" name="name" class="form-control bg-transparent p-4" placeholder="Your Name" required="required" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-row">
                                        <div class="col-sm-12">
                                            <div class="form-group">
                                                <input type="text" name="phone" class="form-control bg-transparent p-4" placeholder="Your Phone" required="required" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-row">
                                        <div class="col-sm-12">
                                            <div class="form-group">
                                                <input type="email" name="email" class="form-control bg-transparent p-4" placeholder="Your Email" required="required" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-row">
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <div class="date" id="date" data-target-input="nearest">
                                                    <input type="text" name="appointmentDate" class="form-control bg-transparent p-4 datetimepicker-input" placeholder="Select Date" data-target="#date" data-toggle="datetimepicker" required="required"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <div class="time" id="time" data-target-input="nearest">
                                                    <input type="text" name="appointmentTime" class="form-control bg-transparent p-4 datetimepicker-input" placeholder="Select Time" data-target="#time" data-toggle="datetimepicker" required="required" accept=""/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-row">
                                        <div class="col-sm-12">
                                            <button class="btn btn-primary btn-block" type="submit" style="height: 47px;">Make Appointment</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Appointment End -->
        </div>
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