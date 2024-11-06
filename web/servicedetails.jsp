<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@page contentType="text/html" pageEncoding="UTF-8" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>${service.name} - Blushed Beauty Bar</title>
            <link rel="stylesheet" href="newUI/assets/css/styles.css">
            <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
            <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
        </head>

        <body>
            <!-- Navbar Section -->
            <jsp:include page="NavBarJSP/NavBarJSP.jsp" />

            <!-- Service Detail Hero -->
            <section class="service-detail-hero">
                <div class="hero-content">
                    <h1>${service.name}</h1>
                    <p>Enhance your natural beauty with our signature services</p>
                </div>
            </section>

            <!-- Service Detail Content -->
            <section class="service-detail-content">
                <div class="service-overview">
                    <div class="service-image" data-aos="fade-right">
                        <img src="${service.image}" alt="${service.name}">
                    </div>
                    <div class="service-info" data-aos="fade-left">
                        <h2>Service Overview</h2>
                        <p>${service.description}</p>
                        <div class="price-info">
                            <div class="price-item">
                                <h3>Price</h3>
                                <p class="price">$${service.price}</p>
                                <p class="duration"><i class="far fa-clock"></i> ${service.duration} minutes</p>
                            </div>
                        </div>
                        <button class="book-service-btn"
                            onclick="window.location.href='booking?service=${service.id}'">Book Now</button>
                    </div>
                </div>

                <div class="service-details">
                    <div class="detail-section" data-aos="fade-up">
                        <h3>What to Expect</h3>
                        <ul>
                            <li>Consultation to determine your desired look</li>
                            <li>Thorough preparation and cleaning</li>
                            <li>Professional service by experienced staff</li>
                            <li>Aftercare instructions and tips</li>
                        </ul>
                    </div>

                    <div class="detail-section" data-aos="fade-up">
                        <h3>Preparation Tips</h3>
                        <ul>
                            <li>Come with clean, makeup-free skin</li>
                            <li>Arrive 5-10 minutes before your appointment</li>
                            <li>Wear comfortable clothing</li>
                            <li>Inform us of any allergies or sensitivities</li>
                        </ul>
                    </div>

                    <div class="detail-section" data-aos="fade-up">
                        <h3>Aftercare</h3>
                        <ul>
                            <li>Follow provided aftercare instructions</li>
                            <li>Avoid certain activities for 24-48 hours</li>
                            <li>Use recommended products only</li>
                            <li>Schedule follow-up appointments as needed</li>
                        </ul>
                    </div>
                </div>
            </section>

            <!-- FAQ Section -->
            <section class="service-faq">
                <h2>Frequently Asked Questions</h2>
                <div class="faq-grid">
                    <div class="faq-item" data-aos="fade-up">
                        <h3>How long does the service last?</h3>
                        <p>The service duration is ${service.duration} minutes.</p>
                    </div>
                    <div class="faq-item" data-aos="fade-up">
                        <h3>Is this service safe?</h3>
                        <p>Yes! Our certified technicians use premium, safe materials and follow strict hygiene
                            protocols.</p>
                    </div>
                    <div class="faq-item" data-aos="fade-up">
                        <h3>How should I prepare?</h3>
                        <p>Come with clean skin and avoid any harsh treatments 24 hours before your appointment.</p>
                    </div>
                    <div class="faq-item" data-aos="fade-up">
                        <h3>What if I need to reschedule?</h3>
                        <p>Please contact us at least 24 hours in advance to reschedule your appointment.</p>
                    </div>
                </div>
            </section>

            <script>
                AOS.init();
            </script>
        </body>

        </html>