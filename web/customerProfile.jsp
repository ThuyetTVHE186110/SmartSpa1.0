<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Account" %> 
<%@ page import="model.Person" %>  <!-- Import Person class -->
<%
    // Kiểm tra xem người dùng đã đăng nhập hay chưa
    Account account = (Account) session.getAttribute("account");
    if (account == null) {
        // Nếu chưa đăng nhập, chuyển hướng tới trang lỗi hoặc login
        response.sendRedirect("error");
        return;
    }

    // Lấy thông tin cá nhân từ đối tượng account
    Person person = account.getPersonInfo();

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
                            <!-- Hiển thị ảnh của người dùng -->
                            <img src="<%= (person != null && person.getImage() != null && !person.getImage().isEmpty()) 
                                ? "newUI/assets/img/" + person.getImage() 
                                : "newUI/assets/img/default-avartar.jpg" %>" 
                                 alt="Profile Picture">
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
                    <button class="profile-nav-btn" data-tab="settings">Account Information</button>
                    <button class="profile-nav-btn" data-tab="preferences">Change Password</button>
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
                    <!-- Preferences Tab -->
                    <div class="profile-tab" id="preferences">
                        <h2>Change Your Password</h2>
                        <form class="preferences-form" action="customerProfile" method="post" onsubmit="return validatePassword()">
                            <!-- Password Update Section -->
                            <h3>Change Password</h3>


                            <!-- Password -->
                            <div class="form-group">
                                <label>Current Password</label>
                                <div style="display: flex; align-items: center;">
                                    <input type="password" id="password_field" name="password" class="form-control" required oninput="validatePassword()">
                                    <button type="button" class="show_password" onclick="togglePassword('password_field')" style="background:none; border:none; cursor:pointer; margin-left: 5px;">
                                        <svg fill="none" viewBox="0 0 24 24" height="24" width="24" xmlns="http://www.w3.org/2000/svg" class="icon">
                                        <path stroke-linecap="round" stroke-width="2" stroke="#141B34"
                                              d="M12 4.5C6.5 4.5 2 12 2 12s4.5 7.5 10 7.5 10-7.5 10-7.5-4.5-7.5-10-7.5zM12 17.5c-2.76 0-5-2.24-5-5s2.24-5 5-5 5 2.24 5 5-2.24 5-5 5zM12 9c-1.39 0-2.5 1.11-2.5 2.5S10.61 14 12 14s2.5-1.11 2.5-2.5S13.39 9 12 9z">
                                        </path>
                                        </svg>
                                    </button>
                                </div>
                            </div>

                            <!-- New Password -->
                            <div class="form-group">
                                <label>New Password</label>
                                <div style="display: flex; align-items: center;">
                                    <input type="password" name="newPassword" id="newPassword" required minlength="8" maxlength="20" required oninput="validatePassword()">
                                    <button type="button" class="show_password" onclick="togglePassword('newPassword')" style="background:none; border:none; cursor:pointer; margin-left: 5px;">
                                        <svg fill="none" viewBox="0 0 24 24" height="24" width="24" xmlns="http://www.w3.org/2000/svg" class="icon">
                                        <path stroke-linecap="round" stroke-width="2" stroke="#141B34"
                                              d="M12 4.5C6.5 4.5 2 12 2 12s4.5 7.5 10 7.5 10-7.5 10-7.5-4.5-7.5-10-7.5zM12 17.5c-2.76 0-5-2.24-5-5s2.24-5 5-5 5 2.24 5 5-2.24 5-5 5zM12 9c-1.39 0-2.5 1.11-2.5 2.5S10.61 14 12 14s2.5-1.11 2.5-2.5S13.39 9 12 9z">
                                        </path>
                                        </svg>
                                    </button>
                                </div>
                            </div>
                            <span id="password_error" style="color: red; display: none;"></span>

                            <!-- Confirm New Password -->
                            <div class="form-group">
                                <label>Confirm New Password</label>
                                <div style="display: flex; align-items: center;">
                                    <input type="password" name="confirmNewPassword" id="confirmNewPassword" required minlength="8" maxlength="20" required oninput="validatePassword()">
                                    <button type="button" class="show_password" onclick="togglePassword('confirmNewPassword')" style="background:none; border:none; cursor:pointer; margin-left: 5px;">
                                        <svg fill="none" viewBox="0 0 24 24" height="24" width="24" xmlns="http://www.w3.org/2000/svg" class="icon">
                                        <path stroke-linecap="round" stroke-width="2" stroke="#141B34"
                                              d="M12 4.5C6.5 4.5 2 12 2 12s4.5 7.5 10 7.5 10-7.5 10-7.5-4.5-7.5-10-7.5zM12 17.5c-2.76 0-5-2.24-5-5s2.24-5 5-5 5 2.24 5 5-2.24 5-5 5zM12 9c-1.39 0-2.5 1.11-2.5 2.5S10.61 14 12 14s2.5-1.11 2.5-2.5S13.39 9 12 9z">
                                        </path>
                                        </svg>
                                    </button>
                                </div>
                            </div>
                            <span id="confirm_password_error" style="color: red; display: none;"></span>

                            <button type="submit" class="save-preferences-btn">Save Changes</button>
                        </form>
                    </div>




                    <!-- Settings Tab -->
                    <div class="profile-tab" id="settings">
                        <h2>Account Settings</h2>
                        <!-- Form cập nhật thông tin -->
                        <form class="settings-form" action="customerProfile" method="post" onsubmit="return validateForm()">
                            <!-- Full Name -->
                            <div class="form-group">
                                <label>Full Name</label>
                                <input type="text" name="fullName" value="<%= person.getName() %>" required>
                            </div>

                            <!-- Date of Birth -->
                            <div class="form-group">
                                <label>Date of Birth</label>
                                <input type="date" name="dateOfBirth" value="<%= person.getDateOfBirth() != null ? person.getDateOfBirth().toString() : "" %>" required>
                            </div>

                            <!-- Gender -->
                            <div class="form-group">
                                <label>Gender</label>
                                <select name="gender" required>
                                    <option value="M" <%= (person.getGender() == 'M') ? "selected" : "" %>>Male</option>
                                    <option value="F" <%= (person.getGender() == 'F') ? "selected" : "" %>>Female</option>
                                    <option value="O" <%= (person.getGender() == 'O') ? "selected" : "" %>>Other</option>
                                </select>
                            </div>

                            <!-- Phone -->
                            <div class="form-group">
                                <label>Phone</label>
                                <input type="tel" name="phone" value="<%= person.getPhone() %>" required>
                            </div>
                            <!-- Address -->
                            <div class="form-group">
                                <label>Address</label>
                                <input type="text" name="address" value="<%= person.getAddress() %>" required>
                            </div>
                            <!-- Email -->
                            <div class="form-group">
                                <label>Email</label>
                                <input type="email" name="email" id="email_field" value="<%= person.getEmail() %>" required oninput="validateEmail(this)">
                            </div>
                            <div id="email_error" style="color: red; display: none;"></div> <!-- Hiển thị lỗi email -->           
                            <!-- Submit Button -->
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
            // Validate password and confirmation fields in change password form
            function validatePassword() {
                const passwordField = document.getElementById('newPassword');
                const confirmPasswordField = document.getElementById('confirmNewPassword');
                const passwordError = document.getElementById('password_error');
                const confirmPasswordError = document.getElementById('confirm_password_error');

                const password = passwordField.value;
                const confirmPassword = confirmPasswordField.value;
                const passwordCriteria = /^(?=.*[0-9])(?=.*[!@#$%^&*])[A-Z].{4,19}$/;

                let isValid = true;
                [passwordError, confirmPasswordError].forEach(error => error.style.display = 'none');

                // Check password complexity
                if (!passwordCriteria.test(password)) {
                    passwordError.innerText = "Password must start with an uppercase letter, be at least 5 characters long, contain at least one digit, and one special character.";
                    passwordError.style.display = 'block';
                    isValid = false;
                }

                // Check if passwords match
                if (password !== confirmPassword) {
                    confirmPasswordError.innerText = "Passwords do not match.";
                    confirmPasswordError.style.display = 'block';
                    isValid = false;
                }

                return isValid;
            }

            // Toggle the visibility of password fields
            function togglePassword(fieldId) {
                const passwordField = document.getElementById(fieldId);
                const iconPath = event.currentTarget.querySelector('svg path');

                const isPasswordHidden = passwordField.type === 'password';
                passwordField.type = isPasswordHidden ? 'text' : 'password';

                // Set icon based on visibility
                iconPath.setAttribute(
                        'd',
                        isPasswordHidden
                        ? 'M12 4.5C6.5 4.5 2 12 2 12s4.5 7.5 10 7.5 10-7.5 10-7.5-4.5-7.5-10-7.5zM12 17.5c-2.76 0-5-2.24-5-5s2.24-5 5-5 5 2.24 5 5-2.24 5-5 5zM12 9c1.39 0 2.5 1.11-2.5 2.5S10.61 14 12 14s2.5-1.11 2.5-2.5S13.39 9 12 9z'
                        : 'M12 4.5C6.5 4.5 2 12 2 12s4.5 7.5 10 7.5 10-7.5 10-7.5-4.5-7.5-10-7.5zM12 17.5c-2.76 0-5-2.24-5-5s2.24-5 5-5 5 2.24 5 5-2.24 5-5 5zM12 9c1.39 0-2.5 1.11-2.5 2.5S10.61 14 12 14s2.5-1.11-2.5 2.5S13.39 9 12 9z'
                        );
            }

            // Validate password matching in simple form submission
            function validateForm() {
                const [newPassword, confirmNewPassword] = [
                    document.querySelector('input[name="newPassword"]').value,
                    document.querySelector('input[name="confirmNewPassword"]').value
                ];

                if (newPassword !== confirmNewPassword) {
                    alert("New passwords do not match.");
                    return false;
                }
                return true;
            }
        </script>


        <% 
            String successMessage = (String) session.getAttribute("successMessage");
            String errorMessage = (String) request.getAttribute("errorMessage");

            if (successMessage != null) {
        %>
        <div class="alert alert-success"><%= successMessage %></div>
        <%
                session.removeAttribute("successMessage");
            }

            if (errorMessage != null) {
        %>
        <div class="alert alert-danger"><%= errorMessage %></div>
        <%
            }
        %>
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
            }
            );


        </script>
    </body>

</html>