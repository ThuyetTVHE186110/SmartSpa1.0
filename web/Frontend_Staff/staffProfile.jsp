<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.Account" %> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // No need to declare session manually; it's already available in JSP
    // You can directly use session
    if (session == null || session.getAttribute("account") == null) {
        // Redirect to login page if session is not found or account is not in session
        response.sendRedirect("../adminLogin");
    } else {
        // Get the account object from session
        Account account = (Account) session.getAttribute("account");

        if (account.getRole() == 3) {
            // Allow access to the page (do nothing and let the JSP render)
        } else {
            // Set an error message and redirect to an error page
            request.setAttribute("errorMessage", "You do not have the required permissions to access the dashboard.");
            request.getRequestDispatcher("error").forward(request, response);
        }
    }
%>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Staff Profile</title>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap"
              rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Frontend_Staff/styles.css">
    </head>

    <body>
        <div class="dashboard-container">
            <jsp:include page="sideBar.jsp" />

            <div class="main-content">
                <header class="top-bar">
                    <div class="search-bar">
                        <i class="fas fa-search" aria-hidden="true"></i>
                        <input type="text" placeholder="Search..." aria-label="Search">
                    </div>
                    <div class="user-menu">
                        <div class="notifications" aria-label="Notifications" tabindex="0">
                            <i class="fas fa-bell" aria-hidden="true"></i>
                            <span class="badge">3</span>
                        </div>
                        <div class="user-profile" aria-label="User Profile" tabindex="0">
                            <img src="user-avatar.jpg" alt="User Avatar">
                            <span>John Doe</span>
                            <i class="fas fa-chevron-down" aria-hidden="true"></i>
                        </div>
                    </div>
                </header>

                <main>
                    <div class="breadcrumb">
                        <i class="fas fa-user" aria-hidden="true"></i>
                        <span>Profile</span>
                    </div>

                    <section class="profile-section">
                        <div class="profile-card">
                            <div class="profile-header">
                                <img src="user-avatar.jpg" alt="User Avatar" class="profile-avatar">
                                <h2>John Doe</h2>
                                <p>Senior Therapist</p>
                            </div>
                            <div class="profile-completion">
                                <div class="progress-bar">
                                    <div class="progress" style="width: 75%;"></div>
                                </div>
                                <span>Profile 75% Complete</span>
                            </div>
                            <div class="profile-details">
                                <h3>Contact Information</h3>
                                <p><i class="fas fa-envelope" aria-hidden="true"></i> john.doe@example.com</p>
                                <p><i class="fas fa-phone" aria-hidden="true"></i> +1 234 567 890</p>
                                <p><i class="fas fa-map-marker-alt" aria-hidden="true"></i> 123 Main St, Cityville
                                </p>
                            </div>
                            <div class="profile-actions">
                                <button class="btn-primary" onclick="openEditModal()" aria-label="Edit Profile">
                                    <i class="fas fa-edit" aria-hidden="true"></i> Edit Profile
                                </button>
                                <div class="settings-dropdown">
                                    <button class="btn-secondary dropdown-toggle" aria-label="Settings">
                                        <i class="fas fa-cog" aria-hidden="true"></i> Settings
                                    </button>
                                    <div class="dropdown-content">
                                        <a href="#">Change Password</a>
                                        <a href="#">Notifications</a>
                                        <a href="#">Privacy Settings</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                </main>
            </div>
        </div>

        <!-- Edit Profile Modal -->
        <div id="editProfileModal" class="modal" role="dialog" aria-labelledby="editProfileTitle"
             aria-hidden="true">
            <div class="modal-content">
                <div class="modal-header">
                    <h2 id="editProfileTitle">Edit Profile</h2>
                    <button class="close-modal" onclick="closeEditModal()" aria-label="Close">&times;</button>
                </div>
                <div class="modal-body">
                    <form>
                        <div class="form-group">
                            <label for="name">Name</label>
                            <input type="text" id="name" value="John Doe" aria-required="true">
                        </div>
                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="email" id="email" value="john.doe@example.com" aria-required="true">
                        </div>
                        <div class="form-group">
                            <label for="phone">Phone</label>
                            <input type="tel" id="phone" value="+1 234 567 890" aria-required="true">
                        </div>
                        <div class="form-group">
                            <label for="address">Address</label>
                            <input type="text" id="address" value="123 Main St, Cityville" aria-required="true">
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button class="btn-primary" onclick="saveProfile()">Save Changes</button>
                    <button class="btn-secondary" onclick="closeEditModal()">Cancel</button>
                </div>
            </div>
        </div>

        <script>
            function openEditModal() {
                document.getElementById('editProfileModal').classList.add('active');
            }

            function closeEditModal() {
                document.getElementById('editProfileModal').classList.remove('active');
            }

            function saveProfile() {
                // Show loading indicator
                const saveButton = document.querySelector('.modal-footer .btn-primary');
                saveButton.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Saving...';
                saveButton.disabled = true;

                // Simulate save operation
                setTimeout(() => {
                    // Hide loading indicator
                    saveButton.innerHTML = 'Save Changes';
                    saveButton.disabled = false;
                    closeEditModal();
                }, 2000);
            }

            // Additional scripts for handling dropdowns and dynamic content
            document.querySelector('.dropdown-toggle').addEventListener('click', function () {
                document.querySelector('.dropdown-content').classList.toggle('show');
            });

            // Close the dropdown if the user clicks outside of it
            window.onclick = function (event) {
                if (!event.target.matches('.dropdown-toggle')) {
                    var dropdowns = document.getElementsByClassName("dropdown-content");
                    for (var i = 0; i < dropdowns.length; i++) {
                        var openDropdown = dropdowns[i];
                        if (openDropdown.classList.contains('show')) {
                            openDropdown.classList.remove('show');
                        }
                    }
                }
            }
        </script>
    </body>

</html>