<%-- 
    Document   : select-service
    Created on : Oct 10, 2024, 10:10:35 AM
    Author     : ADMIN
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Select Your Services</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f8f8f8;
                margin: 0;
                padding: 0;
            }
            .container {
                width: 80%;
                margin: 0 auto;
                padding: 20px;
            }
            .header {
                text-align: center;
                margin-bottom: 20px;
            }
            .header h1 {
                font-size: 24px;
                color: #333;
            }
            .error-message {
                color: red;
            }
            .nav {
                display: flex;
                justify-content: center;
                margin-bottom: 20px;
            }
            .nav a {
                margin: 0 10px;
                text-decoration: none;
                color: #333;
                font-size: 16px;
                padding-bottom: 5px;
            }
            .nav a.active {
                border-bottom: 2px solid #b08c5a;
                color: #b08c5a;
            }
            .services {
                display: flex;
                justify-content: space-between;
            }
            .services .left, .services .right {
                width: 48%;
            }
            .services .left .service, .services .right .appointment {
                background-color: #fff;
                border: 1px solid #ddd;
                border-radius: 5px;
                padding: 15px;
                margin-bottom: 15px;
            }
            .services .left .service h2 {
                font-size: 18px;
                color: #333;
                margin: 0 0 10px;
            }
            .services .left .service .price {
                font-size: 16px;
                color: #333;
                margin-bottom: 10px;
            }
            .add-btn, .continue-btn {
                background-color: #b08c5a;
                color: #fff;
                border: none;
                padding: 10px 20px;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s;
            }
            .add-btn.disabled {
                background-color: #e0d6c9;
                cursor: not-allowed;
            }
            .remove {
                color: #999;
                cursor: pointer;
            }
            .selected-service {
                display: flex;
                justify-content: space-between;
                align-items: center;
                background-color: #f8f8f8;
                border: 1px solid #ddd;
                border-radius: 5px;
                padding: 10px;
                margin-bottom: 10px;
            }
            .notification {
                position: fixed;
                top: 20px;
                right: 20px;
                background-color: #f44336; /* Màu đỏ cho thông báo lỗi */
                color: white;
                padding: 15px;
                border-radius: 5px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                font-size: 16px;
                z-index: 1000;
                transition: opacity 0.3s ease-in-out;
            }

            /* Ẩn thông báo khi không sử dụng */
            .notification.hidden {
                opacity: 0;
                pointer-events: none;
            }

            /* Hiển thị thông báo */
            .notification.show {
                opacity: 1;
                pointer-events: auto;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="header">
                <h1>SELECT YOUR SERVICES</h1>
                <%
                String errorMessage = (String) request.getAttribute("errorMessage");
                if (errorMessage != null) {
                %>
                <div class="error-message">
                    <%= errorMessage %>
                </div>
                <%
                }
                %>
            </div>
            <div class="nav">
                <a href="#" class="active">Massages</a>
                <a href="#">Skin Care</a>
                <a href="#">Advanced Beauty</a>
                <a href="#">Body Services</a>
                <a href="#">Facials</a>
                <a href="#">Wellness or Integrative</a>
            </div>

            <div class="services">
                <div class="left">
                    <c:forEach items="${requestScope.serviceList}" var="service">
                        <div class="service" data-service-id="${service.id}">
                            <h2>${service.name} - ${service.duration} min</h2>
                            <div class="price">${service.duration} mins &middot; $${service.price}.00</div>
                            <p>${service.description}</p>
                            <button type="button" class="add-btn" onclick="addService(this, '${service.id}')">Add</button>
                        </div>
                    </c:forEach>
                </div>
                <div class="right">
                    <div class="appointment">
                        <h2>YOUR APPOINTMENT</h2>
                        <div id="appointment-list"></div>
                        <form action="select-staff" method="post">
                            <input type="hidden" name="selectedServices" id="selected-services-input" />
                            <button type="submit" class="continue-btn" onclick="handleContinueClick(event)">Continue</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div id="notification" class="notification hidden"></div>
        <script>
            let selectedServices = [];
            function showNotification(message, duration = 3000) {
                const notification = document.getElementById('notification');
                notification.textContent = message;
                notification.classList.remove('hidden');
                notification.classList.add('show');

                // Tự động ẩn thông báo sau một khoảng thời gian
                setTimeout(() => {
                    notification.classList.remove('show');
                    notification.classList.add('hidden');
                }, duration);
            }
            function addService(button, serviceId) {
                // Lấy thông tin dịch vụ từ HTML
                const serviceDiv = button.closest('.service');
                const serviceName = serviceDiv.querySelector('h2').textContent;
                const servicePrice = serviceDiv.querySelector('.price').textContent;

                // Kiểm tra nếu dịch vụ đã được thêm vào trước đó
                if (selectedServices.includes(serviceId)) {
                    showNotification('This service has been added.', 3000);
                    return;
                }

                // Thêm dịch vụ vào danh sách
                selectedServices.push(serviceId);

                // Tạo một phần tử mới để hiển thị dịch vụ trong "Your Appointment"
                const appointmentList = document.getElementById('appointment-list');
                const newServiceDiv = document.createElement('div');
                newServiceDiv.classList.add('selected-service');
                newServiceDiv.setAttribute('data-service-id', serviceId);
                newServiceDiv.innerHTML = `
                    <span>` + serviceName + `</span>
                    <span>` + servicePrice + `</span>
                    <span class="remove" onclick="removeService('` + serviceId + `')">✖</span>
                `;

                // Thêm vào danh sách "Your Appointment"
                appointmentList.appendChild(newServiceDiv);

                // Cập nhật giá trị ẩn để gửi khi submit form
                updateSelectedServicesInput();
            }

            function removeService(serviceId) {
                // Xóa dịch vụ khỏi mảng selectedServices
                // Xóa phần tử hiển thị trong "Your Appointment"
                const appointmentList = document.getElementById('appointment-list');
                const serviceDiv = appointmentList.querySelector(`[data-service-id="` + serviceId + `"]`);
                if (serviceDiv) {
                    appointmentList.removeChild(serviceDiv);
                    selectedServices = selectedServices.filter(id => id !== serviceId);
                }

                // Cập nhật giá trị ẩn để gửi khi submit form
                updateSelectedServicesInput();
            }

            function updateSelectedServicesInput() {
                // Cập nhật input ẩn với các ID dịch vụ đã chọn
                document.getElementById('selected-services-input').value = selectedServices.join(',');
                console.log("Selected Service:", selectedServices);
            }

            function handleContinueClick(event) {
                // Nếu chưa chọn dịch vụ nào, không cho tiếp tục
                if (selectedServices.length === 0) {
                    event.preventDefault();
                    showNotification('Please select at least one service.', 3000);
                }
            }
        </script>
    </body>
</html>
