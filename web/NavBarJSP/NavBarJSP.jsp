<%@ page import="model.Person" %>  <!-- Import Person class -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Account" %> <!-- Add this line to import the Account class -->
        <!-- Navbar Section -->
        <header>
            <nav class="navbar">
                <div class="logo">
                    <img src="newUI/assets/img/logo.png" alt="Blushed Beauty Bar Logo">
                </div>
                <div class="hamburger">
                    <span></span>
                    <span></span>
                    <span></span>
                </div>
                <ul class="nav-links">
                    <li><a href="index" class="active">Home</a></li>
                    <li><a href="services">Services</a></li>          
                    <li><a href="product.jsp">Product</a></li>
                    <li><a href="contact.jsp">Contact</a></li>
                    <li><a href="about.jsp">About</a></li>
                    <li><a href="appointment">Appointments</a></li>
                    <li> <%
                            // Check if the user is logged in
                            Account loggedInAccount = (Account) session.getAttribute("account");
                             if (loggedInAccount != null) {
                                // Get the Person object from the account
                                Person loggedInPerson = loggedInAccount.getPersonInfo();
                                String personName = (loggedInPerson != null) ? loggedInPerson.getName() : "User";
                        %>
                        <span>Welcome, <%= personName %>!</span>
                        <a href="LogoutServlet" class="nav-item nav-link">Logout</a>
                        <% } else { %>
                        <a href="login" class="profile-link"><i class="fas fa-user"></i></a><% } %></li>
                    <li><a href="booking" class="book-now-btn">Book Now</a></li>
                </ul>
            </nav>
        </header>

