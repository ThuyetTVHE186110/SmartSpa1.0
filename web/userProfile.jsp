<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.Account" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Person" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta content="width=device-width, initial-scale=1.0" name="viewport">

        <title>Users / Profile - NiceAdmin Bootstrap Template</title>
        <meta content="" name="description">
        <meta content="" name="keywords">

        <!-- Favicons -->
        <link href="assets/img/favicon.png" rel="icon">
        <link href="assets/img/apple-touch-icon.png" rel="apple-touch-icon">

        <!-- Google Fonts -->
        <link href="https://fonts.gstatic.com" rel="preconnect">
        <link
            href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i"
            rel="stylesheet">

        <!-- Vendor CSS Files -->
        <link href="assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
        <link href="assets/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
        <link href="assets/vendor/quill/quill.snow.css" rel="stylesheet">
        <link href="assets/vendor/quill/quill.bubble.css" rel="stylesheet">
        <link href="assets/vendor/remixicon/remixicon.css" rel="stylesheet">
        <link href="assets/vendor/simple-datatables/style.css" rel="stylesheet">

        <!-- Template Main CSS File -->
        <link href="assets/css/style.css" rel="stylesheet">


    </head>

    <body>
        <% 
                    Account account = (Account) session.getAttribute("account"); 
                    String displayName = (account != null) ? account.getPersonInfo().getName() : "Guest"; 
        %>
        <jsp:include page="headerHTML.jsp" />

        <!-- ======= Sidebar ======= -->
        <jsp:include page="sideBar.jsp" />

        <main id="main" class="main">

            <div class="pagetitle">
                <h1>Profile</h1>
                <nav>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="dashboard">Home</a></li>
                        <li class="breadcrumb-item">Users</li>
                        <li class="breadcrumb-item active">Profile</li>
                    </ol>
                </nav>
            </div><!-- End Page Title -->

            <section class="section profile">
                <div class="row">
                    <div class="col-xl-4">

                        <div class="card">
                            <div class="card-body profile-card pt-4 d-flex flex-column align-items-center">

                                <img src="img/adminChien.jpg" alt="Profile" class="rounded-circle">
                                <h2><%= (displayName != null) ? displayName : "Guest" %></h2>
                                <h3>Web Designer</h3>
                                <div class="social-links mt-2">
                                    <a href="#" class="twitter"><i class="bi bi-twitter"></i></a>
                                    <a href="#" class="facebook"><i class="bi bi-facebook"></i></a>
                                    <a href="#" class="instagram"><i class="bi bi-instagram"></i></a>
                                    <a href="#" class="linkedin"><i class="bi bi-linkedin"></i></a>
                                </div>
                            </div>
                        </div>

                    </div>

                    <div class="col-xl-8">

                        <div class="card">
                            <div class="card-body pt-3">
                                <!-- Bordered Tabs -->
                                <ul class="nav nav-tabs nav-tabs-bordered">

                                    <li class="nav-item">
                                        <button class="nav-link active" data-bs-toggle="tab"
                                                data-bs-target="#profile-overview">Overview</button>
                                    </li>

                                    <li class="nav-item">
                                        <button class="nav-link" data-bs-toggle="tab" data-bs-target="#profile-edit">Edit Profile</button>
                                    </li>

                                    <li class="nav-item">
                                        <button class="nav-link" data-bs-toggle="tab" data-bs-target="#profile-settings">Settings</button>
                                    </li>

                                    <li class="nav-item">
                                        <button class="nav-link" data-bs-toggle="tab" data-bs-target="#profile-change-password">Change
                                            Password</button>
                                    </li>

                                </ul>


                                <%
    Person person = (Person) session.getAttribute("person");
    String fullName = (person != null) ? person.getName() : "Guest";
    String phone = (person != null) ? person.getPhone() : "N/A";
    String email = (person != null) ? person.getEmail() : "N/A";
    String address = (person != null) ? person.getAddress() : "N/A";
    char gender = (person != null) ? person.getGender() : 'N';  // Giả sử 'N' là not available
    // Lấy ngày sinh và định dạng nó thành chuỗi (nếu person không null)
    java.sql.Date dob = (person != null) ? person.getDateOfBirth() : null;
    String dateOfBirth = (dob != null) ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(dob) : "N/A";
                                %>
                                <div class="tab-content pt-2">

                                    <div class="tab-pane fade show active profile-overview" id="profile-overview">
                                        <h5 class="card-title">About</h5>
                                        <p class="small fst-italic">Tôi yêu Việt Nam</p>

                                        <h5 class="card-title">Profile Details</h5>

                                        <div class="row">
                                            <div class="col-lg-3 col-md-4 label ">Full Name</div>
                                            <div class="col-lg-9 col-md-8"><%= fullName %></div>
                                        </div>

                                        <div class="row">
                                            <div class="col-lg-3 col-md-4 label ">Date of Birth</div>
                                            <div class="col-lg-9 col-md-8"><%= dateOfBirth %></div>
                                        </div>

                                        <div class="row">
                                            <div class="col-lg-3 col-md-4 label">Gender</div>
                                            <div class="col-lg-9 col-md-8"><%= (gender == 'M') ? "Male" : (gender == 'F') ? "Female" : "Other" %></div>
                                        </div>

                                        <div class="row">
                                            <div class="col-lg-3 col-md-4 label">Phone</div>
                                            <div class="col-lg-9 col-md-8"><%= phone %></div>
                                        </div>

                                        <div class="row">
                                            <div class="col-lg-3 col-md-4 label">Email</div>
                                            <div class="col-lg-9 col-md-8"><%= email %></div>
                                        </div>

                                        <div class="row">
                                            <div class="col-lg-3 col-md-4 label">Address</div>
                                            <div class="col-lg-9 col-md-8"><%= address %></div>
                                        </div>

                                    </div>

                                    <div class="tab-pane fade profile-edit pt-3" id="profile-edit">

                                        <!-- Profile Edit Form -->
                                        <form>
                                            <div class="row mb-3">
                                                <label for="profileImage" class="col-md-4 col-lg-3 col-form-label">Profile Image</label>
                                                <div class="col-md-8 col-lg-9">
                                                    <img src="img/profile-img.jpg" alt="Profile">
                                                    <div class="pt-2">
                                                        <a href="#" class="btn btn-primary btn-sm" title="Upload new profile image"><i
                                                                class="bi bi-upload"></i></a>
                                                        <a href="#" class="btn btn-danger btn-sm" title="Remove my profile image"><i
                                                                class="bi bi-trash"></i></a>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="row mb-3">
                                                <label for="fullName" class="col-md-4 col-lg-3 col-form-label">Full Name</label>
                                                <div class="col-md-8 col-lg-9">
                                                    <input name="fullName" type="text" class="form-control" id="fullName" value="<%= fullName %>">
                                                </div>
                                            </div>

                                            <div class="row mb-3">
                                                <label for="about" class="col-md-4 col-lg-3 col-form-label">About</label>
                                                <div class="col-md-8 col-lg-9">
                                                    <textarea name="about" class="form-control" id="about"
                                                              style="height: 100px">Sunt est soluta temporibus accusantium neque nam maiores cumque temporibus. Tempora libero non est unde veniam est qui dolor. Ut sunt iure rerum quae quisquam autem eveniet perspiciatis odit. Fuga sequi sed ea saepe at unde.</textarea>
                                                </div>
                                            </div>

                                            <div class="row mb-3">
                                                <label for="dateOfBirth" class="col-md-4 col-lg-3 col-form-label">Date of Birth</label>
                                                <div class="col-md-8 col-lg-9">
                                                    <input name="dateOfBirth" type="text" class="form-control" id="dateOfBirth"
                                                           value="<%= dateOfBirth %>">
                                                </div>
                                            </div>                                         

                                            <div class="row mb-3">
                                                <label for="Address" class="col-md-4 col-lg-3 col-form-label">Address</label>
                                                <div class="col-md-8 col-lg-9">
                                                    <input name="address" type="text" class="form-control" id="Address"
                                                           value="<%= address %>">
                                                </div>
                                            </div>

                                            <div class="row mb-3">
                                                <label for="Phone" class="col-md-4 col-lg-3 col-form-label">Phone</label>
                                                <div class="col-md-8 col-lg-9">
                                                    <input name="phone" type="text" class="form-control" id="Phone" value="<%= phone %>">
                                                </div>
                                            </div>

                                            <div class="row mb-3">
                                                <label for="Email" class="col-md-4 col-lg-3 col-form-label">Email</label>
                                                <div class="col-md-8 col-lg-9">
                                                    <input name="email" type="email" class="form-control" id="Email" value="<%= email %>">
                                                </div>
                                            </div>



                                            <div class="row mb-3">
                                                <label for="Facebook" class="col-md-4 col-lg-3 col-form-label">Facebook Profile</label>
                                                <div class="col-md-8 col-lg-9">
                                                    <input name="facebook" type="text" class="form-control" id="Facebook"
                                                           value="https://facebook.com/#">
                                                </div>
                                            </div>

                                            <div class="row mb-3">
                                                <label for="Instagram" class="col-md-4 col-lg-3 col-form-label">Instagram Profile</label>
                                                <div class="col-md-8 col-lg-9">
                                                    <input name="instagram" type="text" class="form-control" id="Instagram"
                                                           value="https://instagram.com/#">
                                                </div>
                                            </div>

                                            <div class="row mb-3">
                                                <label for="Linkedin" class="col-md-4 col-lg-3 col-form-label">Linkedin Profile</label>
                                                <div class="col-md-8 col-lg-9">
                                                    <input name="linkedin" type="text" class="form-control" id="Linkedin"
                                                           value="https://linkedin.com/#">
                                                </div>
                                            </div>

                                            <div class="text-center">
                                                <button type="submit" class="btn btn-primary">Save Changes</button>
                                            </div>
                                        </form><!-- End Profile Edit Form -->

                                    </div>

                                    <div class="tab-pane fade pt-3" id="profile-settings">

                                        <!-- Settings Form -->
                                        <form>

                                            <div class="row mb-3">
                                                <label for="fullName" class="col-md-4 col-lg-3 col-form-label">Email Notifications</label>
                                                <div class="col-md-8 col-lg-9">
                                                    <div class="form-check">
                                                        <input class="form-check-input" type="checkbox" id="changesMade" checked>
                                                        <label class="form-check-label" for="changesMade">
                                                            Changes made to your account
                                                        </label>
                                                    </div>
                                                    <div class="form-check">
                                                        <input class="form-check-input" type="checkbox" id="newProducts" checked>
                                                        <label class="form-check-label" for="newProducts">
                                                            Information on new products and services
                                                        </label>
                                                    </div>
                                                    <div class="form-check">
                                                        <input class="form-check-input" type="checkbox" id="proOffers">
                                                        <label class="form-check-label" for="proOffers">
                                                            Marketing and promo offers
                                                        </label>
                                                    </div>
                                                    <div class="form-check">
                                                        <input class="form-check-input" type="checkbox" id="securityNotify" checked disabled>
                                                        <label class="form-check-label" for="securityNotify">
                                                            Security alerts
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="text-center">
                                                <button type="submit" class="btn btn-primary">Save Changes</button>
                                            </div>
                                        </form><!-- End settings Form -->

                                    </div>

                                    <div class="tab-pane fade pt-3" id="profile-change-password">
                                        <!-- Change Password Form -->
                                        <form>

                                            <div class="row mb-3">
                                                <label for="currentPassword" class="col-md-4 col-lg-3 col-form-label">Current Password</label>
                                                <div class="col-md-8 col-lg-9">
                                                    <input name="password" type="password" class="form-control" id="currentPassword">
                                                </div>
                                            </div>

                                            <div class="row mb-3">
                                                <label for="newPassword" class="col-md-4 col-lg-3 col-form-label">New Password</label>
                                                <div class="col-md-8 col-lg-9">
                                                    <input name="newpassword" type="password" class="form-control" id="newPassword">
                                                </div>
                                            </div>

                                            <div class="row mb-3">
                                                <label for="renewPassword" class="col-md-4 col-lg-3 col-form-label">Re-enter New Password</label>
                                                <div class="col-md-8 col-lg-9">
                                                    <input name="renewpassword" type="password" class="form-control" id="renewPassword">
                                                </div>
                                            </div>

                                            <div class="text-center">
                                                <button type="submit" class="btn btn-primary">Change Password</button>
                                            </div>
                                        </form><!-- End Change Password Form -->

                                    </div>

                                </div><!-- End Bordered Tabs -->

                            </div>
                        </div>

                    </div>
                </div>
            </section>

        </main><!-- End #main -->

        <!-- ======= Footer ======= -->
        <footer id="footer" class="footer">
            <div class="copyright">
                &copy; Copyright <strong><span>NiceAdmin</span></strong>. All Rights Reserved
            </div>
            <div class="credits">
                <!-- All the links in the footer should remain intact. -->
                <!-- You can delete the links only if you purchased the pro version. -->
                <!-- Licensing information: https://bootstrapmade.com/license/ -->
                <!-- Purchase the pro version with working PHP/AJAX contact form: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/ -->
                Designed by <a href="https://bootstrapmade.com/">BootstrapMade</a>
            </div>
        </footer><!-- End Footer -->

        <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i
                class="bi bi-arrow-up-short"></i></a>

        <!-- Vendor JS Files -->
        <script src="assets/vendor/apexcharts/apexcharts.min.js"></script>
        <script src="assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="assets/vendor/chart.js/chart.umd.js"></script>
        <script src="assets/vendor/echarts/echarts.min.js"></script>
        <script src="assets/vendor/quill/quill.js"></script>
        <script src="assets/vendor/simple-datatables/simple-datatables.js"></script>
        <script src="assets/vendor/tinymce/tinymce.min.js"></script>
        <script src="assets/vendor/php-email-form/validate.js"></script>

        <!-- Template Main JS File -->
        <script src="assets/js/main.js"></script>

    </body>

</html>