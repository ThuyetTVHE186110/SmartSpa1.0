<%-- 
    Document   : NavBarJSP
    Created on : Oct 14, 2024, 11:59:30 AM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Account" %> <!-- Add this line to import the Account class -->

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <!-- navbar.jsp -->
        <div class="container-fluid p-0">
            <nav class="navbar navbar-expand-lg bg-white navbar-light py-3 py-lg-0 px-lg-5">
                <a href="index.jsp" class="navbar-brand ml-lg-3">
                    <h1 class="m-0 text-primary"><span class="text-dark">SPA</span> Center</h1>
                </a>
                <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse justify-content-between px-lg-3" id="navbarCollapse">
                    <div class="navbar-nav m-auto py-0">
                        <a href="index.jsp" class="nav-item nav-link">Home</a>
                        <a href="about.jsp" class="nav-item nav-link">About</a>
                        <a href="product" class="nav-item nav-link">Product</a>
                        <a href="services" class="nav-item nav-link">Services</a>
                        <a href="price.jsp" class="nav-item nav-link">Pricing</a>
                        <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown">Pages</a>
                            <div class="dropdown-menu rounded-0 m-0">
                                <a href="appointment.jsp" class="dropdown-item">Appointment</a>
                                <a href="opening.jsp" class="dropdown-item">Open Hours</a>
                                <a href="team.jsp" class="dropdown-item">Our Team</a>
                                <a href="feedback.jsp" class="dropdown-item">Testimonial</a>
                            </div>
                        </div>
                        <a href="contact.jsp" class="nav-item nav-link">Contact</a>
                    </div>
                    <%
                        // Check if the user is logged in
                        Account loggedInAccount = (Account) session.getAttribute("account");
                        if (loggedInAccount != null) {
                    %>
                    <span>Welcome, <%= loggedInAccount.getUsername() %>!</span>
                    <a href="LogoutServlet" class="nav-item nav-link">Logout</a>
                    <% } else { %>
                    <a href="login.jsp" class="nav-item nav-link">Login</a>
                    <% } %>
                    <a href="appointment.jsp" class="btn btn-primary d-none d-lg-block">Book Now</a>
                </div>
            </nav>
        </div>

    </body>
</html>
