<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
                                        <option value="${service.name}">${service.name}</option>
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
                            <c:forEach items="${feedbackList}" var="c">
                                <div class="feedback-card" data-service-id="${c.service.id}" data-service-name="${c.service.name}">
                                    <c:if test="${c.starRating > 3}">
                                        <div class="positive">
                                            <div class="feedback-header">
                                                <div class="client-info">
                                                    <img src="client1.jpg" alt="Sarah Johnson" class="client-avatar">
                                                    <div class="client-details">
                                                        <h4>${c.customer.name}</h4>
                                                        <span class="service-info">${c.service.name}</span>
                                                    </div>
                                                </div>
                                                <div class="feedback-meta">
                                                    <div class="rating">
                                                        <c:forEach var="i" begin="1" end="${c.starRating}">
                                                            <i class="fas fa-star"></i>
                                                        </c:forEach>
                                                        <c:forEach var="i" begin="${c.starRating + 1}" end="5">
                                                            <i class="far fa-star"></i>
                                                        </c:forEach>
                                                        <span>${c.starRating}.0</span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="feedback-content">
                                                <p>${c.content}</p>
                                            </div>
                                            <div class="feedback-response">
                                                <div class="response-status responded">
                                                    <i class="fas fa-check-circle"></i> Responded
                                                </div>
                                                <p class="response-text">Thank you for your wonderful feedback, ${c.customer.name}! We're delighted to hear about your experience with ${c.service.name}. Looking forward to your next visit!</p>
                                            </div>
                                            <div class="feedback-actions">
                                                <button class="btn-icon" title="Edit Response"><i class="fas fa-edit"></i></button>
                                                <button class="btn-icon" title="Flag Important"><i class="fas fa-flag"></i></button>
                                                <button class="btn-icon" title="Archive"><i class="fas fa-archive"></i></button>
                                            </div>
                                        </div>
                                    </c:if>

                                    <c:if test="${c.starRating <= 3}">
                                        <div class="negative">
                                            <div class="feedback-header">
                                                <div class="client-info">
                                                    <img src="client2.jpg" alt="Michael Brown" class="client-avatar">
                                                    <div class="client-details">
                                                        <h4>${c.customer.name}</h4>
                                                        <span class="service-info">${c.service.name}</span>
                                                    </div>
                                                </div>
                                                <div class="feedback-meta">
                                                    <div class="rating">
                                                        <c:forEach var="i" begin="1" end="${c.starRating}">
                                                            <i class="fas fa-star"></i>
                                                        </c:forEach>
                                                        <c:forEach var="i" begin="${c.starRating + 1}" end="5">
                                                            <i class="far fa-star"></i>
                                                        </c:forEach>
                                                        <span>${c.starRating}.0</span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="feedback-content">
                                                <p>${c.content}</p>
                                            </div>
                                            <div class="feedback-response">
                                                <c:if test="${empty c.responseFeedback}">
                                                    <div class="response-status pending">
                                                        <i class="fas fa-clock"></i> Awaiting Response
                                                    </div>
                                                    <div class="response-form">
                                                        <form action="feedback-management" method="post">
                                                            <textarea name="responseFeedback" placeholder="Type your response here..."></textarea>
                                                            <input type="hidden" name="feedbackId" value="${c.id}" />
                                                            <button class="btn-primary" type="submit" name="action" value="send">Send Response</button>
                                                        </form>
                                                    </div>
                                                </c:if>

                                                <c:if test="${not empty c.responseFeedback}">
                                                    <div class="response-status updated">
                                                        <i class="fas fa-check-circle"></i> Response Provided
                                                    </div>
                                                    <div class="response-form">
                                                        <form action="feedback-management" method="post">
                                                            <textarea id="responseFeedback" name="responseFeedback" placeholder="Update your response...">${c.responseFeedback}</textarea>
                                                            <input type="hidden" name="feedbackId" value="${c.id}" />
                                                            <button class="btn-primary" type="submit" name="action" value="update" onclick="enableEdit()">Update Response</button>
                                                        </form>
                                                    </div>
                                                </c:if>
                                            </div>
                                            <div class="feedback-actions">
                                                <button class="btn-icon urgent" title="Respond Now"><i class="fas fa-reply"></i></button>
                                                <button class="btn-icon" title="Escalate"><i class="fas fa-exclamation-triangle"></i></button>
                                                <button class="btn-icon" title="Create Task"><i class="fas fa-tasks"></i></button>
                                            </div>
                                        </div>
                                    </c:if>
                                </div>
                            </c:forEach>


                            <!-- Needs Attention Feedback -->

                        </div>

                        <!-- Summary Column -->
                        <div class="summary-column">
                            <!-- Feedback Overview -->
                            <div class="summary-card">
                                <h3>Feedback Overview</h3>
                                <div class="summary-stats">
                                    <div class="stat">
                                        <span class="label">Total Reviews</span>
                                        <span class="value" id="total-reviews">0</span>
                                    </div>
                                    <div class="stat">
                                        <span class="label">Average Rating</span>
                                        <span class="value" id="average-rating">0.0</span>
                                    </div>
                                    <div class="stat">
                                        <span class="label">Response Rate</span>
                                        <span class="value" id="response-rate">0%</span>
                                    </div>
                                    <div class="stat">
                                        <span class="label">Pending</span>
                                        <span class="value" id="pending-feedback">0</span>
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
                                            <div class="progress" id="progress-5" style="width: 0%"></div>
                                        </div>
                                        <span class="rating-count" id="count-5">0</span>
                                    </div>
                                    <div class="rating-bar">
                                        <span class="rating-label">4 Stars</span>
                                        <div class="progress-bar">
                                            <div class="progress" id="progress-4" style="width: 0%"></div>
                                        </div>
                                        <span class="rating-count" id="count-4">0</span>
                                    </div>
                                    <div class="rating-bar">
                                        <span class="rating-label">3 Stars</span>
                                        <div class="progress-bar">
                                            <div class="progress" id="progress-3" style="width: 0%"></div>
                                        </div>
                                        <span class="rating-count" id="count-3">0</span>
                                    </div>
                                    <div class="rating-bar">
                                        <span class="rating-label">2 Stars</span>
                                        <div class="progress-bar">
                                            <div class="progress" id="progress-2" style="width: 0%"></div>
                                        </div>
                                        <span class="rating-count" id="count-2">0</span>
                                    </div>
                                    <div class="rating-bar">
                                        <span class="rating-label">1 Star</span>
                                        <div class="progress-bar">
                                            <div class="progress" id="progress-1" style="width: 0%"></div>
                                        </div>
                                        <span class="rating-count" id="count-1">0</span>
                                    </div>
                                </div>
                            </div>



                            <!-- Common Feedback Topics -->
                            <div class="summary-card">
                                <h3>Common Service</h3>
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
<script>
    // RATING DISTRIBUTE
    document.addEventListener("DOMContentLoaded", function () {
        // Kiểm tra dữ liệu feedbackList và cập nhật Rating Distribution
        const feedbackList = [
            {starRating: 5, content: "Great service!"},
            {starRating: 4, content: "Very good!"},
            {starRating: 3, content: "It was okay."},
            {starRating: 2, content: "Not satisfied."},
            {starRating: 1, content: "Terrible experience."},
                    // Thêm các mục feedback khác nếu cần
        ];

        function updateRatingDistribution(feedbackList) {
            const ratingCounts = {5: 0, 4: 0, 3: 0, 2: 0, 1: 0};

            // Đếm số lượng đánh giá cho mỗi mức sao
            feedbackList.forEach(feedback => {
                if (feedback.starRating >= 1 && feedback.starRating <= 5) {
                    ratingCounts[feedback.starRating]++;
                }
            });

            // Tổng số đánh giá
            const totalReviews = feedbackList.length;

            // Cập nhật tỷ lệ và số lượng cho từng mức đánh giá
            for (let rating = 5; rating >= 1; rating--) {
                const count = ratingCounts[rating];
                const percentage = totalReviews > 0 ? ((count / totalReviews) * 100).toFixed(0) + "%" : "0%";

                // Cập nhật thanh tiến trình và số lượng
                document.getElementById(`progress-${rating}`).style.width = percentage;
                document.getElementById(`count-${rating}`).textContent = count;
            }
        }

        // Gọi hàm cập nhật
        updateRatingDistribution(feedbackList);
    });


</script>

<script>
    // OVERVIEW FEEDBACK
    // 
    // Dữ liệu mẫu, thay thế bằng dữ liệu thực tế từ server hoặc backend
    const feedbackList = [
        {starRating: 5, responseFeedback: "Thank you!", service: {id: 1, name: "Massage"}, customer: {name: "Alice"}},
        {starRating: 4, responseFeedback: null, service: {id: 2, name: "Facial"}, customer: {name: "Bob"}},
        {starRating: 3, responseFeedback: "Sorry for the inconvenience.", service: {id: 3, name: "Haircut"}, customer: {name: "Charlie"}},
                // Thêm các mục feedback khác nếu cần
    ];

// Hàm tính toán các giá trị và cập nhật Summary Column
    function updateFeedbackSummary(feedbackList) {
        const totalReviews = feedbackList.length;
        const averageRating = (feedbackList.reduce((sum, feedback) => sum + feedback.starRating, 0) / totalReviews).toFixed(1);
        const respondedFeedback = feedbackList.filter(feedback => feedback.responseFeedback).length;
        const responseRate = ((respondedFeedback / totalReviews) * 100).toFixed(0) + "%";
        const pendingFeedback = totalReviews - respondedFeedback;

        // Cập nhật các phần tử HTML trong Summary Column
        document.getElementById('total-reviews').textContent = totalReviews;
        document.getElementById('average-rating').textContent = averageRating;
        document.getElementById('response-rate').textContent = responseRate;
        document.getElementById('pending-feedback').textContent = pendingFeedback;
    }

// Gọi hàm khi tải trang hoặc khi dữ liệu feedbackList thay đổi
    updateFeedbackSummary(feedbackList);

</script>

<script>
    // Function to enable editing of the textarea when the update button is clicked
    function enableEdit() {
        document.getElementById('responseFeedback').removeAttribute('readonly');
    }
</script>
<script>
    // Function to filter feedback cards based on selected service
    document.getElementById('serviceFilter').addEventListener('change', function () {
        var selectedServiceName = this.value; // Get selected service name
        var feedbackCards = document.querySelectorAll('.feedback-card'); // Get all feedback cards

        feedbackCards.forEach(function (card) {
            var serviceName = card.getAttribute('data-service-name'); // Get service name from the card
            // Show card if it matches the selected service or if "All Services" is selected
            if (selectedServiceName === "" || serviceName === selectedServiceName) {
                card.style.display = "block"; // Show matching feedback card
            } else {
                card.style.display = "none"; // Hide non-matching feedback card
            }
        });
    });
</script>