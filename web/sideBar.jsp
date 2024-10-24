<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <aside id="sidebar" class="sidebar">

            <ul class="sidebar-nav" id="sidebar-nav">

                <li class="nav-item">
                    <a class="nav-link " href="dashboard">
                        <i class="bi bi-grid"></i>
                        <span>Dashboard</span>
                    </a>
                </li><!-- End Dashboard Nav -->

                <!-- Admin Section -->
                <li class="nav-item">
                    <a class="nav-link collapsed" data-bs-target="#admin-nav" data-bs-toggle="collapse" href="#">
                        <i class="bi bi-person-gear"></i><span>Admin</span><i class="bi bi-chevron-down ms-auto"></i>
                    </a>
                    <ul id="admin-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
                        <li>
                            <a href="accountManagement">
                                <i class="bi bi-circle"></i><span>Account Management</span>
                            </a>
                        </li>
                    </ul>
                </li><!-- End Admin Nav -->

                <!-- Manager Section -->
                <li class="nav-item">
                    <a class="nav-link collapsed" data-bs-target="#manager-nav" data-bs-toggle="collapse" href="#">
                        <i class="bi bi-briefcase"></i><span>Manager</span><i class="bi bi-chevron-down ms-auto"></i>
                    </a>
                    <ul id="manager-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
                        <li>
                            <a href="service-management.jsp">
                                <i class="bi bi-circle"></i><span>Service Management</span>
                            </a>
                        </li>
                        <li>
                            <a href="productlist">
                                <i class="bi bi-circle"></i><span>Product Management</span>
                            </a>
                        </li>
                        <li>
                            <a href="employee-management" class="active">
                                <i class="bi bi-circle"></i><span>Staff Management</span>
                            </a>
                        </li>
                    </ul>
                </li><!-- End Manager Nav -->

                <!-- Staff Section -->
                <li class="nav-item">
                    <a class="nav-link collapsed" data-bs-target="#staff-nav" data-bs-toggle="collapse" href="#">
                        <i class="bi bi-people"></i><span>Staff</span><i class="bi bi-chevron-down ms-auto"></i>
                    </a>
                    <ul id="staff-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
                        <li>
                            <a href="appointmentManagement.jsp">
                                <i class="bi bi-circle"></i><span>Appointment Management</span>
                            </a>
                        </li>
                        <li>
                            <a href="schedulemanagement.jsp">
                                <i class="bi bi-circle"></i><span>Schedule Management</span>
                            </a>
                        </li>
                        <li>
                            <a href="feedback-management" class="active">
                                <i class="bi bi-circle"></i><span>Feedback Management</span>
                            </a>
                        </li>
                    </ul>
                </li><!-- End Staff Nav -->

            </ul>

        </aside><!-- End Sidebar--><h1>Hello World!</h1>
    </body>
</html>
