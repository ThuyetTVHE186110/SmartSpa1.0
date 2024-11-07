<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="model.Account" %>
<%@ page import="model.Person" %> <!-- Import Person class -->
<%@ page import="dal.PersonDAO" %>
<%@ page import="java.util.List" %>
<%
    // Constants for pagination
    final int CLIENTS_PER_PAGE = 8;
    int currentPage = 1;

    // Retrieve page parameter from the request if available
    String pageParam = request.getParameter("page");
    if (pageParam != null) {
        currentPage = Integer.parseInt(pageParam);
    }

    int offset = (currentPage - 1) * CLIENTS_PER_PAGE;

    // Initialize DAO and fetch the list of clients with pagination
    PersonDAO personDAO = new PersonDAO();
    List<Person> clients = personDAO.getAllCustomers(offset, CLIENTS_PER_PAGE);
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
                            <h2>Client Management</h2>
                            <div class="client-filter">
                                <form action="clientManagement" method="get" class="d-flex justify-content-between align-items-center">
                                    <button type="submit" name="filter" value="all"
                                            class="filter-btn btn btn-outline-primary ${param.filter == null || param.filter == 'all' ? 'active' : ''}">
                                        All Clients
                                    </button>

                                    <button type="submit" name="filter" value="regular"
                                            class="filter-btn btn btn-outline-primary ${param.filter == 'regular' ? 'active' : ''}">
                                        Regular Clients
                                    </button>

                                    <button type="submit" name="filter" value="vip"
                                            class="filter-btn btn btn-outline-primary ${param.filter == 'vip' ? 'active' : ''}">
                                        VIP Clients
                                    </button>

                                    <button type="submit" name="filter" value="new"
                                            class="filter-btn btn btn-outline-primary ${param.filter == 'new' ? 'active' : ''}">
                                        New Clients
                                    </button>
                                </form>


                            </div>

                        </div>
                        <button class="btn-primary" id="newClientBtn">
                            <i class="fas fa-plus"></i> Add New Client
                        </button>
                    </div>

                    <div class="clients-grid">
                        <!-- Clients List -->
                        <div class="clients-list">
                            <c:forEach var="client" items="${customers}">
                                <c:choose>
                                    <c:when test="${client.image != null && !client.image.isEmpty()}">
                                        <c:set var="imageUrl" value="newUI/assets/img/${client.image}" />
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="imageUrl" value="newUI/assets/img/default-avatar.jpg" />
                                    </c:otherwise>
                                </c:choose>

                                <div class="client-card ${client.tier != null ? client.tier.toLowerCase() : 'regular'}">
                                    <div class="client-header">
                                        <img src="${imageUrl}" 
                                             alt="${client.name}" class="client-avatar">
                                        <div class="client-status ${client.tier != null ? client.tier.toLowerCase() : 'regular'}">
                                            ${client.tier != null ? client.tier : 'Regular'}
                                        </div>
                                    </div>
                                    <div class="client-info">
                                        <h3>${client.name}</h3>
                                        <div class="contact-details">
                                            <p><i class="fas fa-envelope"></i> ${client.email}</p>
                                            <p><i class="fas fa-phone"></i> ${client.phone}</p>
                                        </div>
                                        <div class="preferences">
                                            <h4>Preferences</h4>
                                            <div class="preference-tags">
                                                <!-- Display preferences if available -->
                                                <span class="pref-tag">Swedish Massage</span>
                                                <span class="pref-tag">Medium Pressure</span>
                                                <span class="pref-tag">Female Therapist</span>
                                            </div>
                                        </div>
                                        <div class="visit-history">
                                            <p><i class="fas fa-history"></i> Last Visit: 2 weeks ago</p>
                                            <p><i class="fas fa-calendar-check"></i> Total Points: ${client.points}</p>
                                        </div>
                                    </div>
                                    <div class="client-actions">
                                        <button class="btn-icon" title="View Profile"><i class="fas fa-user"></i></button>
                                        <button class="btn-icon" title="Book Appointment"><i class="fas fa-calendar-plus"></i></button>
                                        <button class="btn-icon" title="Edit"><i class="fas fa-edit"></i></button>
                                    </div>
                                </div>
                            </c:forEach>

                        </div>




                        <!-- Summary Column -->
                        <div class="summary-column">
                            <!-- Client Statistics -->
                            <div class="summary-card">
                                <h3>Client Overview</h3>
                                <div class="summary-stats">
                                    <div class="stat">
                                        <span class="label">Total Clients</span>
                                        <span class="value">248</span>
                                    </div>
                                    <div class="stat">
                                        <span class="label">VIP Members</span>
                                        <span class="value">45</span>
                                    </div>
                                    <div class="stat">
                                        <span class="label">New This Month</span>
                                        <span class="value">12</span>
                                    </div>
                                    <div class="stat">
                                        <span class="label">Active</span>
                                        <span class="value">180</span>
                                    </div>
                                </div>
                            </div>

                            <!-- Recent Activity -->
                            <div class="summary-card">
                                <h3>Recent Activity</h3>
                                <div class="activity-list">
                                    <div class="activity-item">
                                        <div class="activity-icon"><i class="fas fa-user-plus"></i></div>
                                        <div class="activity-details">
                                            <h4>New Client Registration</h4>
                                            <p>Emily Davis joined as a new client</p>
                                            <span class="activity-time">2 hours ago</span>
                                        </div>
                                    </div>
                                    <div class="activity-item">
                                        <div class="activity-icon"><i class="fas fa-star"></i></div>
                                        <div class="activity-details">
                                            <h4>VIP Status Achieved</h4>
                                            <p>Michael Brown upgraded to VIP</p>
                                            <span class="activity-time">1 day ago</span>
                                        </div>
                                    </div>
                                    <div class="activity-item">
                                        <div class="activity-icon"><i class="fas fa-comment"></i></div>
                                        <div class="activity-details">
                                            <h4>Feedback Received</h4>
                                            <p>Sarah Johnson left a 5-star review</p>
                                            <span class="activity-time">2 days ago</span>
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
                                        Add Client
                                    </button>
                                    <button class="action-btn">
                                        <i class="fas fa-envelope"></i>
                                        Send Email
                                    </button>
                                    <button class="action-btn">
                                        <i class="fas fa-tag"></i>
                                        Promotions
                                    </button>
                                    <button class="action-btn">
                                        <i class="fas fa-file-export"></i>
                                        Export List
                                    </button>
                                </div>
                            </div>
                            <div class="pagination">
                                <c:if test="${currentPage > 1}">
                                    <a href="clientManagement?filter=${selectedTier}&page=${currentPage - 1}" class="page-link">Previous</a>
                                </c:if>

                                <c:forEach begin="1" end="${totalPages}" var="i">
                                    <a href="clientManagement?filter=${selectedTier}&page=${i}" class="page-link ${i == currentPage ? 'active' : ''}">${i}</a>
                                </c:forEach>

                                <c:if test="${currentPage < totalPages}">
                                    <a href="clientManagement?filter=${selectedTier}&page=${currentPage + 1}" class="page-link">Next</a>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </main>
            </div>
        </div>
    </body><!-- comment -->
</html>