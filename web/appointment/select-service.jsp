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
                            <div class="price">1 hr &middot; $${service.price}.00</div>
                            <p>${service.description}</p>
                            <button class="add-btn">Add</button>
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
        </div>F
        <script>
            window.onload = function () {
                let selectedServices = [];

                document.querySelectorAll('.add-btn').forEach(button => {
                    button.addEventListener('click', function () {
                        const serviceElement = this.closest('.service');
                        const serviceId = serviceElement.getAttribute('data-service-id');
                        const serviceName = serviceElement.querySelector('h2').innerText;
                        const appointmentList = document.getElementById('appointment-list');

                        if (!selectedServices.includes(serviceId)) {
                            const selectedService = document.createElement('div');
                            selectedService.className = 'selected-service';
                            selectedService.setAttribute('data-service-id', serviceId);
                            selectedService.innerHTML =
                                    `<span>${serviceName}</span><span class="remove" onclick="removeService(`+ serviceId +`)">&times;</span>`;

                            appointmentList.appendChild(selectedService);
                            selectedServices.push(serviceId); // Add service ID to array

                            this.classList.add('disabled');
                            this.innerText = 'Added';
                            this.disabled = true; // Disable button after adding
                        }
                    });
                });

                // Remove service function
                window.removeService = function (serviceId) {
                    const serviceToRemove = document.querySelector(`.selected-service[data-service-id="`+ serviceId +`"]`);
                    if (serviceToRemove) {
                        serviceToRemove.remove(); // Remove selected service from the list
                        selectedServices = selectedServices.filter(id => id !== serviceId); // Remove from the array

                        // Re-enable the add button for the removed service
                        const button = document.querySelector(`.service[data-service-id="`+ serviceId +`"] .add-btn`);
                        if (button) {
                            button.classList.remove('disabled'); // Enable the button
                            button.innerText = 'Add'; // Reset button text
                            button.disabled = false; // Allow re-adding
                        }
                    }
                };

                // Handle continue button click
                window.handleContinueClick = function (event) {
                    event.preventDefault(); // Prevent default form submission
                    console.log('Selected services:', selectedServices);
                    document.getElementById('selected-services-input').value = selectedServices.join(','); // Join IDs as a comma-separated string
                    event.target.closest('form').submit(); // Submit the form programmatically
                };
            };
        </script>
    </body>
</html>
