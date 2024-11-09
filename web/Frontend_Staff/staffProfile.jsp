<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.Account" %>
<%@ page import="model.Person" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%
    // Check if session exists and if account is present
    if (session == null || session.getAttribute("account") == null) {
        // Redirect to login page if session or account is not found
        response.sendRedirect("adminLogin.jsp");
    } else {
        // Get the account object from session
        Account account = (Account) session.getAttribute("account");

        // Check the role of the account
        if (account.getRole() == 3) {
            // Allow access to the page (do nothing and let the JSP render)
        } else {
            // Set an error message and redirect to an error page
            request.setAttribute("errorMessage", "You do not have the required permissions to access the dashboard.");
            request.getRequestDispatcher("error").forward(request, response);
        }
    }
%>
<% Account account = (Account) session.getAttribute("account");
    String displayName = (account
            != null) ? account.getPersonInfo().getName() : "Guest"; %>
<%
    Person person = account.getPersonInfo(); // Get additional information from Person

    // Assign person details or default values if person is null
    String fullName = (person != null) ? person.getName() : "Guest";
    String phone = (person != null) ? person.getPhone() : "N/A";
    String email = (person != null) ? person.getEmail() : "N/A";
    String address = (person != null) ? person.getAddress() : "N/A";
    char gender = (person != null) ? person.getGender() : 'N';

    // Handle date of birth formatting
    java.sql.Date dob = (person != null) ? person.getDateOfBirth() : null;
    String dateOfBirth = (dob != null) ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(dob) : "N/A";

    // Get image path from person (if applicable)
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
        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Frontend_Staff/staffProfile.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Frontend_Staff/styles.css">
    </head>

    <body>
        <div class="dashboard-container">
            <jsp:include page="sideBar.jsp" />

            <div class="main-content">
                <jsp:include page="topbar.jsp" />

                <main>
                    <div class="breadcrumb">
                        <i class="fas fa-user"></i>
                        <span>Profile</span>
                    </div>

                    <section class="profile-section">
                        <div class="profile-card">
                            <div class="profile-header">
                                 <img src="<%= (person != null && person.getImage() != null && !person.getImage().isEmpty())
                                         ? " img/" + person.getImage() : "img/default-avartar.jpg"%>" alt="Profile
                                 Picture" class="rounded-circle" style="width: 100px; height:
                                 100px; object-fit: cover;">
                                <div class="profile-header-info">
                                    <h2><%= (displayName != null) ? displayName : "Guest"%></h2>
                                    <p>Senior Therapist</p>

                                </div>
                            </div>

                            <div class="profile-details">
                                <div class="detail-card">
                                    <h3><i class="fas fa-address-card"></i> Personal Information</h3>
                                    <p><i class="fas fa-envelope"></i> ${person.email}</p>
                                    <p><i class="fas fa-phone"></i> ${person.phone}</p>
                                    <p><i class="fas fa-map-marker-alt"></i> ${person.address}</p>
                                    <p><i class="fas fa-user"></i> Full Name: ${person.name}</p>
                                    <p><i class="fas fa-venus-mars"></i> Gender: 
                                        <%= (gender == 'M') ? "Male" : (gender == 'F')
                                                ? "Female" : "Other"%>
                                    </p>
                                    <p><i class="fas fa-birthday-cake"></i> Date of Birth: ${person.dateOfBirth}</p>
                                </div>

                                <div class="detail-card">
                                    <h3><i class="fas fa-briefcase"></i> Professional Details</h3>
                                    <p><i class="fas fa-user-md"></i> Specialization: Physical Therapy</p>
                                    <p><i class="fas fa-clock"></i> Experience: 5+ years</p>
                                    <p><i class="fas fa-certificate"></i> Certifications: 3</p>
                                </div>
                            </div>

                            <div class="profile-actions">
                                <button class="btn-primary" onclick="openEditModal()">
                                    <i class="fas fa-edit"></i> Edit Profile
                                </button>
                            </div>
                        </div>
                    </section>
                </main>
            </div>
        </div>

        <!-- Edit Profile Modal -->
        <div id="editProfileModal" class="modal" role="dialog" aria-labelledby="editProfileTitle" aria-hidden="true">
            <div class="modal-content">
                <div class="modal-header">
                    <h2 id="editProfileTitle">Edit Profile</h2>
                    <button class="close-modal" onclick="closeEditModal()" aria-label="Close">&times;</button>
                </div>
                <div class="modal-body">
                    <form>
                        <!-- Full Name -->
                        <div class="form-group">
                            <label for="name">Full Name</label>
                            <input type="text" id="name" value="${person.name}" aria-required="true">
                        </div>

                        <!-- Email -->
                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="email" id="email" value="${person.email}" aria-required="true">
                        </div>

                        <!-- Phone -->
                        <div class="form-group">
                            <label for="phone">Phone</label>
                            <input type="tel" id="phone" value="${person.phone}" aria-required="true">
                        </div>

                        <!-- Address -->
                        <div class="form-group">
                            <label for="address">Address</label>
                            <input type="text" id="address" value="${person.address}" aria-required="true">
                        </div>

                        <!-- Gender -->
                        <div class="form-group">
                            <label for="gender">Gender</label>
                            <select id="gender" aria-required="true">
                                <option value="M" <c:if test="${person.gender == 'M'}">selected</c:if>>Male</option>
                                <option value="F" <c:if test="${person.gender == 'F'}">selected</c:if>>Female</option>
                                <option value="N" <c:if test="${person.gender == 'N'}">selected</c:if>>Not Specified</option>
                                </select>
                            </div>

                            <!-- Date of Birth -->
                            <div class="form-group">
                                <label for="dateOfBirth">Date of Birth</label>
                                <input type="date" id="dateOfBirth" value="${person.dateOfBirth}" aria-required="true">
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
            // Mở modal Edit Profile
            function openEditModal() {
                const modal = document.getElementById('editProfileModal');
                if (modal) {
                    modal.classList.add('active');
                    modal.style.display = "block";  // Hiển thị modal khi thêm class 'active'
                }
            }

            // Đóng modal Edit Profile
            function closeEditModal() {
                const modal = document.getElementById('editProfileModal');
                if (modal) {
                    modal.classList.remove('active');
                    modal.style.display = "none";  // Ẩn modal khi xóa class 'active'
                }
            }

            // Lưu thông tin hồ sơ
            function saveProfile() {
                const saveButton = document.querySelector('.modal-footer .btn-primary');

                if (saveButton) {
                    // Hiển thị biểu tượng loading
                    saveButton.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Saving...';
                    saveButton.disabled = true;

                    // Giả lập thao tác lưu (có thể thay bằng lệnh AJAX thực tế)
                    setTimeout(() => {
                        // Kết thúc loading và cập nhật lại nút
                        saveButton.innerHTML = 'Save Changes';
                        saveButton.disabled = false;
                        closeEditModal();
                    }, 2000);
                }
            }

            // Xử lý dropdown
            const dropdownToggle = document.querySelector('.dropdown-toggle');
            if (dropdownToggle) {
                dropdownToggle.addEventListener('click', function () {
                    const dropdownContent = document.querySelector('.dropdown-content');
                    if (dropdownContent) {
                        dropdownContent.classList.toggle('show');
                    }
                });
            }

            // Đóng dropdown nếu người dùng click ngoài vùng dropdown
            window.addEventListener('click', function (event) {
                if (!event.target.matches('.dropdown-toggle')) {
                    const dropdowns = document.getElementsByClassName("dropdown-content");
                    for (let i = 0; i < dropdowns.length; i++) {
                        const openDropdown = dropdowns[i];
                        if (openDropdown.classList.contains('show')) {
                            openDropdown.classList.remove('show');
                        }
                    }
                }
            });
        </script>

    </body>

</html>