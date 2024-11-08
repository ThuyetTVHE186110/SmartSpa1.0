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
        <title>Staff Spa Dashboard</title>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="stylesheet" href="./styles.css">
    </head>
    <body>
        <div class="dashboard-container">
            <jsp:include page="sideBar.jsp" />

            <div class="main-content">
                <jsp:include page="topbar.jsp" />
                <main>
                    <div class="section-header">
                        <div class="header-content">
                            <h2>Therapists Management</h2>
                            <div class="therapist-filter">
                                <button class="filter-btn active">All Therapists</button>
                                <button class="filter-btn">Available</button>
                                <button class="filter-btn">On Duty</button>
                                <button class="filter-btn">On Break</button>
                            </div>
                        </div>
                        <button class="btn-primary" id="newTherapistBtn">
                            <i class="fas fa-plus"></i> Add New Therapist
                        </button>
                    </div>

                    <div class="therapists-grid">
                        <!-- Therapists List -->
                        <div class="therapists-list">
                            <c:forEach var="therapist" items="${therapists}">
                                <!-- Therapist Card -->
                                <div class="therapist-card ${therapist.status != null ? therapist.status.toLowerCase() : 'available'}">
                                    <div class="therapist-header">
                                        <c:choose>
                                            <c:when test="${therapist.image != null && !therapist.image.isEmpty()}">
                                                <img src="newUI/assets/img/${therapist.image}" alt="${therapist.name}" class="therapist-avatar">
                                            </c:when>
                                            <c:otherwise>
                                                <img src="newUI/assets/img/default-avatar.jpg" alt="${therapist.name}" class="therapist-avatar">
                                            </c:otherwise>
                                        </c:choose>
                                        <div class="status-badge">${therapist.status != null ? therapist.status : 'Available'}</div>
                                    </div>

                                    <div class="therapist-info">
                                        <h3>${therapist.name}</h3>
                                        <p class="therapist-title">Massage Therapist</p>

                                        <!-- Display Appointments -->
                                        <div class="appointments">
                                            <h4>Upcoming Appointments:</h4>
                                            <c:forEach var="appointment" items="${therapist.appointments}">
                                                <div class="appointment-details">
                                                    <p><b>Time:</b> ${appointment.start} - ${appointment.end}</p>
                                                    <p><b>Status:</b> ${appointment.status}</p>

                                                    <!-- Services Associated with Each Appointment -->
                                                    <div class="services-info">
                                                        <c:forEach var="service" items="${appointment.services}">
                                                            <div class="service-detail">
                                                                <p><b>Service:</b> ${service.name}</p>
                                                                <p><b>Price:</b> $${service.price}</p>
                                                                <p><b>Duration:</b> ${service.duration} mins</p>
                                                            </div>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>

                                    <!-- Action Buttons -->
                                    <div class="therapist-actions">
                                        <button class="btn-icon" title="View Schedule"><i class="fas fa-calendar"></i></button>
                                        <button class="btn-icon" title="Edit Profile"><i class="fas fa-edit"></i></button>
                                        <button class="btn-icon" title="Set Break"><i class="fas fa-coffee"></i></button>
                                    </div>
                                </div>
                            </c:forEach>


                            <!-- Busy Therapist Card -->
                            <div class="therapist-card busy">
                                <div class="therapist-header">
                                    <img src="therapist2.jpg" alt="John Smith" class="therapist-avatar">
                                    <div class="status-badge">In Session</div>
                                </div>
                                <div class="therapist-info">
                                    <h3>John Smith</h3>
                                    <p class="therapist-title">Massage Therapist</p>
                                    <div class="specialties">
                                        <span class="specialty-tag">Sports Massage</span>
                                        <span class="specialty-tag">Thai Massage</span>
                                    </div>
                                    <div class="schedule-info">
                                        <div class="schedule-item">
                                            <i class="fas fa-clock"></i>
                                            <span>Current session ends: 1:00 PM</span>
                                        </div>
                                        <div class="schedule-item">
                                            <i class="fas fa-calendar-check"></i>
                                            <span>Today's appointments: 8</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="therapist-actions">
                                    <button class="btn-icon" title="View Schedule"><i class="fas fa-calendar"></i></button>
                                    <button class="btn-icon" title="Edit Profile"><i class="fas fa-edit"></i></button>
                                    <button class="btn-icon" title="Set Break"><i class="fas fa-coffee"></i></button>
                                </div>
                            </div>

                            <!-- On Break Therapist Card -->
                            <div class="therapist-card break">
                                <div class="therapist-header">
                                    <img src="therapist3.jpg" alt="Lisa Anderson" class="therapist-avatar">
                                    <div class="status-badge">On Break</div>
                                </div>
                                <div class="therapist-info">
                                    <h3>Lisa Anderson</h3>
                                    <p class="therapist-title">Esthetician</p>
                                    <div class="specialties">
                                        <span class="specialty-tag">Facial Treatment</span>
                                        <span class="specialty-tag">Aromatherapy</span>
                                    </div>
                                    <div class="schedule-info">
                                        <div class="schedule-item">
                                            <i class="fas fa-clock"></i>
                                            <span>Returns at: 2:00 PM</span>
                                        </div>
                                        <div class="schedule-item">
                                            <i class="fas fa-calendar-check"></i>
                                            <span>Today's appointments: 5</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="therapist-actions">
                                    <button class="btn-icon" title="View Schedule"><i class="fas fa-calendar"></i></button>
                                    <button class="btn-icon" title="Edit Profile"><i class="fas fa-edit"></i></button>
                                    <button class="btn-icon" title="End Break"><i class="fas fa-check"></i></button>
                                </div>
                            </div>
                        </div>

                        <!-- Summary Column -->
                        <div class="summary-column">
                            <!-- Statistics Card -->
                            <div class="summary-card">
                                <h3>Staff Overview</h3>
                                <div class="summary-stats">
                                    <div class="stat">
                                        <span class="label">Total Staff</span>
                                        <span class="value">12</span>
                                    </div>
                                    <div class="stat">
                                        <span class="label">Available</span>
                                        <span class="value">5</span>
                                    </div>
                                    <div class="stat">
                                        <span class="label">On Duty</span>
                                        <span class="value">4</span>
                                    </div>
                                    <div class="stat">
                                        <span class="label">On Break</span>
                                        <span class="value">3</span>
                                    </div>
                                </div>
                            </div>

                            <!-- Schedule Overview Card -->
                            <div class="summary-card">
                                <h3>Today's Schedule</h3>
                                <div class="schedule-timeline">
                                    <div class="timeline-item">
                                        <span class="time">9:00 AM</span>
                                        <div class="timeline-content">
                                            <p>Morning Shift Starts</p>
                                            <span>8 therapists on duty</span>
                                        </div>
                                    </div>
                                    <div class="timeline-item">
                                        <span class="time">1:00 PM</span>
                                        <div class="timeline-content">
                                            <p>Shift Change</p>
                                            <span>4 therapists rotating</span>
                                        </div>
                                    </div>
                                    <div class="timeline-item">
                                        <span class="time">5:00 PM</span>
                                        <div class="timeline-content">
                                            <p>Evening Shift Starts</p>
                                            <span>6 therapists on duty</span>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Quick Actions -->
                            <div class="summary-card">
                                <h3>Quick Actions</h3>
                                <div class="quick-actions-grid">
                                    <button class="action-btn">
                                        <i class="fas fa-user-plus"></i>
                                        Add Therapist
                                    </button>
                                    <button class="action-btn">
                                        <i class="fas fa-calendar-alt"></i>
                                        Schedule Shift
                                    </button>
                                    <button class="action-btn">
                                        <i class="fas fa-clipboard-list"></i>
                                        View Reports
                                    </button>
                                    <button class="action-btn">
                                        <i class="fas fa-cog"></i>
                                        Settings
                                    </button>
                                </div>
                            </div>
                            <!-- Pagination -->
                            <div class="pagination">
                                <c:if test="${currentPage > 1}">
                                    <a href="therapist?page=${currentPage - 1}" class="page-link">Previous</a>
                                </c:if>

                                <c:forEach begin="1" end="${totalPages}" var="i">
                                    <a href="therapist?page=${i}" class="page-link ${i == currentPage ? 'active' : ''}">${i}</a>
                                </c:forEach>

                                <c:if test="${currentPage < totalPages}">
                                    <a href="therapist?page=${currentPage + 1}" class="page-link">Next</a>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </main>
            </div>
        </div>
    </body>
</html>