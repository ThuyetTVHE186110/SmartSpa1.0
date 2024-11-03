<%-- 
    Document   : feedback
    Created on : Nov 3, 2024, 10:24:51 PM
    Author     : Asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Client Feedback - Blushed Beauty Bar</title>
    <link rel="stylesheet" href="newUI/assets/css/styles.css">
    <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
    <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
</head>

<body>
    <!-- Navbar Section -->
    <jsp:include page="NavBarJSP/NavBarJSP.jsp" />

    <!-- Feedback Hero Section -->
    <section class="feedback-hero">
        <div class="hero-content">
            <h1>Client Feedback</h1>
            <p>Share your experience with us</p>
        </div>
    </section>

    <!-- Submit Feedback Section -->
    <section class="submit-feedback">
        <div class="container">
            <h2>Leave Your Feedback</h2>
            <form id="feedback-form" class="feedback-form">
                <div class="form-row">
                    <div class="form-group">
                        <label for="name">Your Name</label>
                        <input type="text" id="name" required>
                    </div>
                    <div class="form-group">
                        <label for="service">Service Received</label>
                        <select id="service" required>
                            <option value="">Select a service</option>
                            <option value="classic-lash">Classic Lash Extensions</option>
                            <option value="hybrid-lash">Hybrid Lash Extensions</option>
                            <option value="volume-lash">Volume Lash Extensions</option>
                            <option value="lash-lift">Lash Lift</option>
                            <option value="lash-tint">Lash Lift & Tint</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label>Rate Your Experience</label>
                    <div class="rating">
                        <input type="radio" id="star5" name="rating" value="5" required>
                        <label for="star5"><i class="fas fa-star"></i></label>
                        <input type="radio" id="star4" name="rating" value="4">
                        <label for="star4"><i class="fas fa-star"></i></label>
                        <input type="radio" id="star3" name="rating" value="3">
                        <label for="star3"><i class="fas fa-star"></i></label>
                        <input type="radio" id="star2" name="rating" value="2">
                        <label for="star2"><i class="fas fa-star"></i></label>
                        <input type="radio" id="star1" name="rating" value="1">
                        <label for="star1"><i class="fas fa-star"></i></label>
                    </div>
                </div>
                <div class="form-group">
                    <label for="feedback">Your Feedback</label>
                    <textarea id="feedback" rows="5" required></textarea>
                </div>
                <div class="form-group">
                    <label for="photo">Add Photos (optional)</label>
                    <input type="file" id="photo" accept="image/*" multiple>
                </div>
                <button type="submit" class="submit-btn">Submit Feedback</button>
            </form>
        </div>
    </section>

    <!-- Client Testimonials Section -->
    <section class="testimonials">
        <div class="container">
            <h2>What Our Clients Say</h2>
            <div class="testimonial-filters">
                <button class="filter-btn active" data-filter="all">All</button>
                <button class="filter-btn" data-filter="classic-lash">Classic Lash</button>
                <button class="filter-btn" data-filter="hybrid-lash">Hybrid Lash</button>
                <button class="filter-btn" data-filter="volume-lash">Volume Lash</button>
                <button class="filter-btn" data-filter="lash-lift">Lash Lift</button>
            </div>
            <div class="testimonial-grid">
                <!-- Testimonial cards will be dynamically populated -->
            </div>
            <button id="load-more" class="load-more-btn">Load More Reviews</button>
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

        // Feedback form submission
        document.getElementById('feedback-form').addEventListener('submit', async (e) => {
            e.preventDefault();

            const formData = new FormData();
            formData.append('name', document.getElementById('name').value);
            formData.append('service', document.getElementById('service').value);
            formData.append('rating', document.querySelector('input[name="rating"]:checked').value);
            formData.append('feedback', document.getElementById('feedback').value);

            const photos = document.getElementById('photo').files;
            for (let i = 0; i < photos.length; i++) {
                formData.append('photos', photos[i]);
            }

            try {
                // Submit feedback to backend
                const response = await submitFeedback(formData);
                alert('Thank you for your feedback!');
                e.target.reset();
            } catch (error) {
                console.error('Error submitting feedback:', error);
                alert('Error submitting feedback. Please try again.');
            }
        });

        // Filter testimonials
        const filterButtons = document.querySelectorAll('.filter-btn');
        filterButtons.forEach(btn => {
            btn.addEventListener('click', () => {
                filterButtons.forEach(b => b.classList.remove('active'));
                btn.classList.add('active');
                filterTestimonials(btn.dataset.filter);
            });
        });

        // Load testimonials
        async function loadTestimonials(filter = 'all', page = 1) {
            try {
                const testimonials = await fetchTestimonials(filter, page);
                displayTestimonials(testimonials);
            } catch (error) {
                console.error('Error loading testimonials:', error);
            }
        }

        // Display testimonials
        function displayTestimonials(testimonials) {
            const grid = document.querySelector('.testimonial-grid');
            testimonials.forEach(testimonial => {
                const card = createTestimonialCard(testimonial);
                grid.appendChild(card);
            });
        }

        // Create testimonial card
        function createTestimonialCard(testimonial) {
            const card = document.createElement('div');
            card.className = 'testimonial-card';
            card.innerHTML = `
                <div class="testimonial-header">
                    <div class="client-info">
                        <h3>${testimonial.name}</h3>
                        <span class="service-type">${testimonial.service}</span>
                    </div>
                    <div class="rating">
                        ${createStarRating(testimonial.rating)}
                    </div>
                </div>
                <p class="testimonial-text">${testimonial.feedback}</p>
                ${testimonial.photos ? createPhotoGallery(testimonial.photos) : ''}
                <div class="testimonial-footer">
                    <span class="date">${formatDate(testimonial.date)}</span>
                </div>
            `;
            return card;
        }

        // Helper functions
        function createStarRating(rating) {
            return Array(5).fill('').map((_, i) =>
                `<i class="fas fa-star${i < rating ? ' filled' : ''}"></i>`
            ).join('');
        }

        function createPhotoGallery(photos) {
            return `
                <div class="photo-gallery">
                    $ {photos.map(photo => `
                        <img src="${photo}" alt="Client feedback photo" loading="lazy">
                    `).join('')}
                </div>
            `;
        }

        function formatDate(date) {
            return new Date(date).toLocaleDateString('en-US', {
                year: 'numeric',
                month: 'long',
                day: 'numeric'
            });
        }

        // Load initial testimonials
        loadTestimonials();
    </script>
</body>

</html>
