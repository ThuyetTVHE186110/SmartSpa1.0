<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Account" %> 
<%@ page import="model.Person" %>  <!-- Import Person class -->
<%
    // Session đã được cung cấp sẵn trong JSP, không cần khai báo lại.
    // Kiểm tra xem session có tồn tại và người dùng đã đăng nhập hay chưa
    if (session == null || session.getAttribute("account") == null) {
        // Nếu chưa đăng nhập, chuyển hướng tới trang lỗi hoặc login
        response.sendRedirect("error");
        return;
    }

    // Lấy đối tượng account từ session
    Account account = (Account) session.getAttribute("account");
    Person person = account != null ? account.getPersonInfo() : null;

    // Kiểm tra quyền hạn (chỉ cho phép customer role)
    if (account.getRole() != 4) {
        response.sendRedirect("error");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>My Profile - Blushed Beauty Bar</title>
        <link rel="stylesheet" href="newUI//assets/css/styles.css">
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
    </head>
    <body>
        <jsp:include page="NavBarJSP/NavBarJSP.jsp" />

        <!-- Profile Section -->
        <section class="profile-section">
            <div class="profile-container">
                <!-- Profile Header -->
                <div class="profile-header">
                    <div class="profile-info">
                        <div class="profile-avatar">
                            <img src="./assets/img/default-avatar.jpg" alt="Profile Picture">
                            <button class="edit-avatar"><i class="fas fa-camera"></i></button>
                        </div>
                        <div class="profile-details">
                            <h1><c:out value="${account.personInfo.name}"/></h1> <!-- Display customer name -->
                            <p>Member since January 2023</p>
                        </div>
                    </div>
                </div>

                <!-- Profile Navigation -->
                <div class="profile-nav">
                    <button class="profile-nav-btn active" data-tab="appointments">Appointments</button>
                    <button class="profile-nav-btn" data-tab="history">History</button>
                    <button class="profile-nav-btn" data-tab="preferences">Preferences</button>
                    <button class="profile-nav-btn" data-tab="settings">Settings</button>
                </div>

                <!-- Profile Content -->
                <div class="profile-content">
                    <!-- Upcoming Appointments Tab -->
                    <div class="profile-tab active" id="appointments">
                        <h2>Upcoming Appointments</h2>
                        <div class="appointments-grid">
                            <div class="appointment-card">
                                <div class="appointment-date">
                                    <span class="month">MAR</span>
                                    <span class="day">15</span>
                                </div>
                                <div class="appointment-details">
                                    <h3>Classic Lash Extensions</h3>
                                    <p><i class="far fa-clock"></i> 2:00 PM - 4:30 PM</p>
                                    <p><i class="fas fa-user"></i> with Jessica Chen</p>
                                </div>
                                <div class="appointment-actions">
                                    <button class="reschedule-btn">Reschedule</button>
                                    <button class="cancel-btn">Cancel</button>
                                </div>
                            </div>
                            <!-- Add more appointment cards as needed -->
                        </div>
                    </div>

                    <!-- Service History Tab -->
                    <div class="profile-tab" id="history">
                        <h2>Service History</h2>
                        <div class="history-list">
                            <div class="history-item">
                                <div class="history-date">Feb 15, 2024</div>
                                <div class="history-details">
                                    <h3>Hybrid Lash Fill</h3>
                                    <p>Service by Emily Davis</p>
                                    <p class="price">$95</p>
                                </div>
                                <button class="book-again-btn">Book Again</button>
                            </div>
                            <!-- Add more history items as needed -->
                        </div>
                    </div>

                    <!-- Preferences Tab -->
                    <div class="profile-tab" id="preferences">
                        <h2>Service Preferences</h2>
                        <form class="preferences-form">
                            <div class="form-group">
                                <label>Preferred Lash Style</label>
                                <select>
                                    <option>Natural</option>
                                    <option>Dramatic</option>
                                    <option>Cat Eye</option>
                                    <option>Doll Eye</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Preferred Artist</label>
                                <select>
                                    <option>No Preference</option>
                                    <option>Jessica Chen</option>
                                    <option>Sarah Johnson</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Appointment Reminders</label>
                                <div class="checkbox-group">
                                    <label>
                                        <input type="checkbox" checked> Email
                                    </label>
                                    <label>
                                        <input type="checkbox" checked> SMS
                                    </label>
                                </div>
                            </div>
                            <button type="submit" class="save-preferences-btn">Save Preferences</button>
                        </form>
                    </div>

                    <!-- Settings Tab -->
                    <div class="profile-tab" id="settings">
                        <h2>Account Settings</h2>
                        <form class="settings-form" action="updateProfile" method="post">
                            <div class="form-group">
                                <label>Full Name</label>
                                <input type="text" name="fullName" value="<%= (account != null && account.getPersonInfo() != null) ? account.getPersonInfo().getName() : "" %>" required>
                            </div>

                            <div class="form-group">
                                <label>Email</label>
                                <input type="email" name="email" value="<%= (account != null && account.getPersonInfo() != null) ? account.getPersonInfo().getEmail() : "" %>" required>
                            </div>

                            <div class="form-group">
                                <label>Phone</label>
                                <input type="tel" name="phone" value="<%= (account != null && account.getPersonInfo() != null) ? account.getPersonInfo().getPhone() : "" %>" required>
                            </div>

                            <div class="form-group">
                                <label>Password</label>
                                <input type="password" name="password" value="<%= (account != null) ? account.getPassword() : "" %>">
                            </div>

                            <button type="submit" class="save-settings-btn">Save Changes</button>
                        </form>


                    </div>
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

            // Profile tab navigation
            const profileNavBtns = document.querySelectorAll('.profile-nav-btn');
            const profileTabs = document.querySelectorAll('.profile-tab');

            profileNavBtns.forEach(btn => {
                btn.addEventListener('click', () => {
                    // Remove active class from all buttons and tabs
                    profileNavBtns.forEach(b => b.classList.remove('active'));
                    profileTabs.forEach(tab => tab.classList.remove('active'));

                    // Add active class to clicked button and corresponding tab
                    btn.classList.add('active');
                    document.getElementById(btn.dataset.tab).classList.add('active');
                });
            });

            // Form submission handling
            document.querySelectorAll('form').forEach(form => {
                form.addEventListener('submit', (e) => {
                    e.preventDefault();
                    alert('Changes saved successfully!');
                });
            });
        </script>
    </body>

</html>