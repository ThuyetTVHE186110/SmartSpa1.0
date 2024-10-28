<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Account" %> 
<%@ page import="model.Person" %>  <!-- Import Person class -->
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Book Appointment - Blushed Beauty Bar</title>
        <link rel="stylesheet" href="newUI/assets/css/styles.css">
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
    </head>

    <body>
        <jsp:include page="NavBarJSP/NavBarJSP.jsp" />

        <!-- Booking Hero Section -->
        <section class="booking-hero">
            <div class="hero-content">
                <h1>Book Your Appointment</h1>
                <p>Transform your look with our expert services</p>
            </div>
        </section>

        <!-- Booking Form Section -->
        <section class="booking-section">
            <div class="booking-container">
                <div class="booking-steps" data-aos="fade-up">
                    <div class="step active">
                        <span class="step-number">1</span>
                        <span class="step-text">Select Service</span>
                    </div>
                    <div class="step">
                        <span class="step-number">2</span>
                        <span class="step-text">Choose Date & Time</span>
                    </div>
                    <div class="step">
                        <span class="step-number">3</span>
                        <span class="step-text">Your Details</span>
                    </div>
                    <div class="step">
                        <span class="step-number">4</span>
                        <span class="step-text">Confirmation</span>
                    </div>
                </div>

                <form id="bookingForm" class="booking-form" data-aos="fade-up" action="booking">
                    <!-- Step 1: Service Selection -->
                    <div class="form-step active" id="step1">
                        <h2>Select Your Service</h2>
                        <div class="service-categories">
                            <%--<c:forEach items="${requestScope.categoryList}" var="category">--%>
                            <div class="service-category">
                                <h3>${category.name}</h3>
                                <div class="service-options">
                                    <c:forEach items="${requestScope.serviceList}" var="service">
                                        <%--<c:if test="${category.id == service.categoryID}">--%>
                                        <label class="service-option">
                                            <input type="radio" name="service" value="classic-full">
                                            <span class="option-content">
                                                <span class="service-name">${service.name}</span>
                                                <span class="service-price">$${service.price}</span>
                                                <span class="service-duration">${service.duration} mins</span>
                                            </span>
                                        </label>
                                        <%--</c:if>--%>
                                    </c:forEach>
                                </div>
                            </div>
                            <%--</c:forEach>--%>
                        </div>
                        <div class="form-navigation">
                            <button type="button" class="next-step">Continue to Date & Time</button>
                        </div>
                    </div>

                    <!-- Step 2: Date & Time Selection -->
                    <div class="form-step" id="step2">
                        <h2>Choose Date & Time</h2>
                        <div class="datetime-selection">
                            <div class="calendar-container">
                                <!-- Calendar will be inserted here via JavaScript -->
                            </div>
                            <div class="time-slots">
                                <h3>Available Times</h3>
                                <div class="time-options">
                                    <!-- Time slots will be populated via JavaScript -->
                                </div>
                            </div>
                        </div>
                        <div class="form-navigation">
                            <button type="button" class="prev-step">Back</button>
                            <button type="button" class="next-step">Continue to Details</button>
                        </div>
                    </div>

                    <!-- Step 3: Personal Details -->
                    <div class="form-step" id="step3">
                        <h2>Your Details</h2>
                        <div class="personal-details">
                            <div class="form-group">
                                <label for="name">Full Name</label>
                                <input type="text" id="name" name="name" required>
                            </div>
                            <div class="form-group">
                                <label for="email">Email</label>
                                <input type="email" id="email" name="email" required>
                            </div>
                            <div class="form-group">
                                <label for="phone">Phone</label>
                                <input type="tel" id="phone" name="phone" required>
                            </div>
                            <div class="form-group">
                                <label for="notes">Special Notes/Requests</label>
                                <textarea id="notes" name="notes" rows="4"></textarea>
                            </div>
                        </div>
                        <div class="form-navigation">
                            <button type="button" class="prev-step">Back</button>
                            <button type="button" class="next-step">Review Booking</button>
                        </div>
                    </div>

                    <!-- Step 4: Confirmation -->
                    <div class="form-step" id="step4">
                        <h2>Review Your Booking</h2>
                        <div class="booking-summary">
                            <div class="summary-item">
                                <h3>Selected Service</h3>
                                <p id="summary-service"></p>
                            </div>
                            <div class="summary-item">
                                <h3>Date & Time</h3>
                                <p id="summary-datetime"></p>
                            </div>
                            <div class="summary-item">
                                <h3>Your Details</h3>
                                <p id="summary-details"></p>
                            </div>
                        </div>
                        <div class="form-navigation">
                            <button type="button" class="prev-step">Back</button>
                            <button type="submit" class="confirm-booking">Confirm Booking</button>
                        </div>
                    </div>
                </form>
            </div>
        </section>

        <!-- Footer Section -->
        <footer>
            <!-- [Previous footer code remains the same] -->
        </footer>

        <script>
            AOS.init();

            // Hamburger menu functionality
            const hamburger = document.querySelector('.hamburger');
            const navLinks = document.querySelector('.nav-links');

            hamburger.addEventListener('click', () => {
                navLinks.classList.toggle('active');
                hamburger.classList.toggle('active');
            });

            // Form step navigation
            let currentStep = 1;
            const totalSteps = 4;

            function updateSteps() {
                document.querySelectorAll('.step').forEach((step, index) => {
                    if (index + 1 < currentStep) {
                        step.classList.add('completed');
                        step.classList.remove('active');
                    } else if (index + 1 === currentStep) {
                        step.classList.add('active');
                        step.classList.remove('completed');
                    } else {
                        step.classList.remove('active', 'completed');
                    }
                });

                document.querySelectorAll('.form-step').forEach((step, index) => {
                    if (index + 1 === currentStep) {
                        step.classList.add('active');
                    } else {
                        step.classList.remove('active');
                    }
                });
            }

            document.querySelectorAll('.next-step').forEach(button => {
                button.addEventListener('click', () => {
                    const currentFormStep = document.querySelector('.form-step.active');
                    const inputs = currentFormStep.querySelectorAll('input, textarea');
                    let valid = true;

                    // Check if each input is filled (only if required)
                    inputs.forEach(input => {
                        if (input.hasAttribute('required') && !input.value.trim()) {
                            input.classList.add('error'); // Add an error class (you can style it in CSS)
                            valid = false;
                        } else {
                            input.classList.remove('error'); // Remove error class if input is valid
                        }
                    });

                    if (valid) {
                        // Move to the next step only if all required inputs are filled
                        if (currentStep < totalSteps) {
                            currentStep++;
                            updateSteps();
                            if (currentStep === 4) {
                                updateSummary(); // Update summary when reaching the last step
                            }
                        }
                    } else {
                        alert('Please fill in all required fields before continuing.');
                    }
                });
            });

            document.querySelectorAll('.prev-step').forEach(button => {
                button.addEventListener('click', () => {
                    if (currentStep > 1) {
                        currentStep--;
                        updateSteps();
                    }
                });
            });

            function updateSummary() {
                // Update selected service
                const selectedService = document.querySelector('input[name="service"]:checked');
                const serviceName = selectedService ? selectedService.closest('.service-option').querySelector('.service-name').textContent : '';
                const servicePrice = selectedService ? selectedService.closest('.service-option').querySelector('.service-price').textContent : '';
                const serviceDuration = selectedService ? selectedService.closest('.service-option').querySelector('.service-duration').textContent : '';
                document.getElementById('summary-service').textContent = serviceName + " - " + servicePrice + " (" + serviceDuration + ")";

                // Update selected date & time
                const selectedDate = document.querySelector('.calendar-container .selected-date'); // Assuming you have a selected date element
                const selectedTime = document.querySelector('.time-options input[name="time"]:checked'); // Assuming time slots have name="time"
                const summaryDate = selectedDate ? selectedDate.textContent : 'No date selected';
                const summaryTime = selectedTime ? selectedTime.value : 'No time selected';
                document.getElementById('summary-datetime').textContent = summaryDate + " at " + summaryTime;

                // Update personal details
                const name = document.getElementById('name').value;
                const email = document.getElementById('email').value;
                const phone = document.getElementById('phone').value;
                const notes = document.getElementById('notes').value;
                document.getElementById('summary-details').innerHTML =
                        "Name: " + name + "<br>Email: " + email + "<br>Phone: " + phone + "<br>Notes: " + (notes ? notes : 'None');
            }

            // Form submission
            document.getElementById('bookingForm').addEventListener('submit', function (e) {
                e.preventDefault();
                // Add your booking submission logic here
                alert('Thank you for your booking! We will confirm your appointment shortly.');
                // Reset form and steps
                currentStep = 1;
                updateSteps();
                this.reset();
            });
        </script>
    </body>

</html>