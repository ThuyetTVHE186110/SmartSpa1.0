<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <!--<link rel="stylesheet" href="./styles.css">-->
        <link rel="stylesheet" href="/SmartSpa1.0/Frontend_Staff/styles.css">
    </head>
    <body>
        <div class="dashboard-container">
            <jsp:include page="sideBar.jsp" />

            <div class="main-content">
                <jsp:include page="topbar.jsp" />
                <main>
                    <div class="section-header">
                        <div class="header-content">
                            <h2>Feedback Management</h2>
                            <div class="feedback-filter">
                                <select class="filter-btn" id="serviceFilter">
                                        <option value="">All Services</option>
                                        <c:forEach items="${service}" var="service">
                                        <option value="${service.id}">${service.name}</option>
                                        </c:forEach>
                                    </select>
                            </div>
                        </div>
                        <button class="btn-primary" id="exportFeedbackBtn">
                            <i class="fas fa-download"></i> Export Report
                        </button>
                    </div>

                    <div class="feedback-grid">
                        <!-- Feedback List -->
                        <div class="feedback-list">
                            <!-- Recent Feedback -->
                            <div class="feedback-card positive">
                                <div class="feedback-header">
                                    <div class="client-info">
                                        <img src="client1.jpg" alt="Sarah Johnson" class="client-avatar">
                                        <div class="client-details">
                                            <h4>Sarah Johnson</h4>
                                            <span class="service-info">Swedish Massage with Emma Wilson</span>
                                        </div>
                                    </div>
                                    <div class="feedback-meta">
                                        <div class="rating">
                                            <i class="fas fa-star"></i>
                                            <i class="fas fa-star"></i>
                                            <i class="fas fa-star"></i>
                                            <i class="fas fa-star"></i>
                                            <i class="fas fa-star"></i>
                                            <span>5.0</span>
                                        </div>
                                        <span class="feedback-date">2 hours ago</span>
                                    </div>
                                </div>
                                <div class="feedback-content">
                                    <p>Amazing experience! Emma was fantastic and very professional. The atmosphere was perfect and I left
                                        feeling completely relaxed. Will definitely be coming back!</p>
                                </div>
                                <div class="feedback-response">
                                    <div class="response-status responded">
                                        <i class="fas fa-check-circle"></i> Responded
                                    </div>
                                    <p class="response-text">Thank you for your wonderful feedback, Sarah! We're delighted to hear about
                                        your experience with Emma. Looking forward to your next visit!</p>
                                </div>
                                <div class="feedback-actions">
                                    <button class="btn-icon" title="Edit Response"><i class="fas fa-edit"></i></button>
                                    <button class="btn-icon" title="Flag Important"><i class="fas fa-flag"></i></button>
                                    <button class="btn-icon" title="Archive"><i class="fas fa-archive"></i></button>
                                </div>
                            </div>

                            <!-- Needs Attention Feedback -->
                            <div class="feedback-card negative">
                                <div class="feedback-header">
                                    <div class="client-info">
                                        <img src="client2.jpg" alt="Michael Brown" class="client-avatar">
                                        <div class="client-details">
                                            <h4>Michael Brown</h4>
                                            <span class="service-info">Deep Tissue Massage with John Smith</span>
                                        </div>
                                    </div>
                                    <div class="feedback-meta">
                                        <div class="rating">
                                            <i class="fas fa-star"></i>
                                            <i class="fas fa-star"></i>
                                            <i class="fas fa-star"></i>
                                            <i class="far fa-star"></i>
                                            <i class="far fa-star"></i>
                                            <span>3.0</span>
                                        </div>
                                        <span class="feedback-date">1 day ago</span>
                                    </div>
                                </div>
                                <div class="feedback-content">
                                    <p>The massage was okay, but the room temperature was too cold and the music was a bit too loud. Would
                                        appreciate better attention to these details.</p>
                                </div>
                                <div class="feedback-response">
                                    <div class="response-status pending">
                                        <i class="fas fa-clock"></i> Awaiting Response
                                    </div>
                                    <div class="response-form">
                                        <textarea placeholder="Type your response here..."></textarea>
                                        <button class="btn-primary">Send Response</button>
                                    </div>
                                </div>
                                <div class="feedback-actions">
                                    <button class="btn-icon urgent" title="Respond Now"><i class="fas fa-reply"></i></button>
                                    <button class="btn-icon" title="Escalate"><i class="fas fa-exclamation-triangle"></i></button>
                                    <button class="btn-icon" title="Create Task"><i class="fas fa-tasks"></i></button>
                                </div>
                            </div>
                        </div>

                        <!-- Summary Column -->
                        <div class="summary-column">
                            <!-- Feedback Overview -->
                            <div class="summary-card">
                                <h3>Feedback Overview</h3>
                                <div class="summary-stats">
                                    <div class="stat">
                                        <span class="label">Total Reviews</span>
                                        <span class="value">248</span>
                                    </div>
                                    <div class="stat">
                                        <span class="label">Average Rating</span>
                                        <span class="value">4.8</span>
                                    </div>
                                    <div class="stat">
                                        <span class="label">Response Rate</span>
                                        <span class="value">95%</span>
                                    </div>
                                    <div class="stat">
                                        <span class="label">Pending</span>
                                        <span class="value">3</span>
                                    </div>
                                </div>
                            </div>

                            <!-- Rating Distribution -->
                            <div class="summary-card">
                                <h3>Rating Distribution</h3>
                                <div class="rating-distribution">
                                    <div class="rating-bar">
                                        <span class="rating-label">5 Stars</span>
                                        <div class="progress-bar">
                                            <div class="progress" style="width: 75%"></div>
                                        </div>
                                        <span class="rating-count">180</span>
                                    </div>
                                    <div class="rating-bar">
                                        <span class="rating-label">4 Stars</span>
                                        <div class="progress-bar">
                                            <div class="progress" style="width: 15%"></div>
                                        </div>
                                        <span class="rating-count">45</span>
                                    </div>
                                    <div class="rating-bar">
                                        <span class="rating-label">3 Stars</span>
                                        <div class="progress-bar">
                                            <div class="progress" style="width: 5%"></div>
                                        </div>
                                        <span class="rating-count">15</span>
                                    </div>
                                    <div class="rating-bar">
                                        <span class="rating-label">2 Stars</span>
                                        <div class="progress-bar">
                                            <div class="progress" style="width: 3%"></div>
                                        </div>
                                        <span class="rating-count">5</span>
                                    </div>
                                    <div class="rating-bar">
                                        <span class="rating-label">1 Star</span>
                                        <div class="progress-bar">
                                            <div class="progress" style="width: 2%"></div>
                                        </div>
                                        <span class="rating-count">3</span>
                                    </div>
                                </div>
                            </div>

                            <!-- Common Feedback Topics -->
                            <div class="summary-card">
                                <h3>Common Topics</h3>
                                <div class="topic-list">
                                    <div class="topic-item positive">
                                        <span class="topic-name">Staff Professionalism</span>
                                        <span class="topic-count">+45</span>
                                    </div>
                                    <div class="topic-item positive">
                                        <span class="topic-name">Massage Quality</span>
                                        <span class="topic-count">+38</span>
                                    </div>
                                    <div class="topic-item negative">
                                        <span class="topic-name">Room Temperature</span>
                                        <span class="topic-count">-8</span>
                                    </div>
                                    <div class="topic-item positive">
                                        <span class="topic-name">Cleanliness</span>
                                        <span class="topic-count">+42</span>
                                    </div>
                                </div>
                            </div>

                            <!-- Quick Actions -->
                            <div class="summary-card">
                                <h3>Quick Actions</h3>
                                <div class="quick-actions-grid">
                                    <button class="action-btn">
                                        <i class="fas fa-reply-all"></i>
                                        Bulk Response
                                    </button>
                                    <button class="action-btn">
                                        <i class="fas fa-chart-bar"></i>
                                        Analytics
                                    </button>
                                    <button class="action-btn">
                                        <i class="fas fa-envelope"></i>
                                        Send Survey
                                    </button>
                                    <button class="action-btn">
                                        <i class="fas fa-cog"></i>
                                        Settings
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>
            </div>
        </div>
    </body>
</html>