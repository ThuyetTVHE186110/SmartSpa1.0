<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Lash Cleanser - Blushed Beauty Bar</title>
        <link rel="stylesheet" href="newUI/assets/css/styles.css">
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
    </head>

    <body>
        <!-- Navbar Section -->
        <jsp:include page="NavBarJSP/NavBarJSP.jsp" />

        <!-- Product Detail Section -->
        <section class="product-detail-section">
            <div class="product-detail-container">
                <!-- Product Gallery -->
                <div class="product-gallery" data-aos="fade-right">
                    <div class="main-image">
                        <img class="img-fluid" src="${product.image}" alt="${product.name}">
                    </div>
                    <!--                    <div class="thumbnail-images">
                                            <img src="./assets/img/products/lash-cleanser-1.jpg" alt="Lash Cleanser View 1"
                                                 onclick="changeImage(this.src)">
                                            <img src="./assets/img/products/lash-cleanser-2.jpg" alt="Lash Cleanser View 2"
                                                 onclick="changeImage(this.src)">
                                            <img src="./assets/img/products/lash-cleanser-3.jpg" alt="Lash Cleanser View 3"
                                                 onclick="changeImage(this.src)">
                                        </div>-->
                </div>

                <!-- Product Information -->
                <div class="product-info" data-aos="fade-left">
                    <div class="product-header">
                        <h1>${product.name}</h1>
                        <div class="product-rating">
                            <div class="stars">
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star-half-alt"></i>
                            </div>
                            <span>(4.5/5 based on 28 reviews)</span>
                        </div>
                    </div>


                    <span class="current-price">$${product.price}</span>



                    <div class="product-description">
                        <p>${product.description}</p>

                        <h3>Key Benefits:</h3>
                        <ul>
                            <c:forEach var="benefit" items="${fn:split(product.benefit, ',')}">
                                <li>${benefit}</li>
                                </c:forEach>
                        </ul>
                    </div>

                    <div class="product-actions">
                        <div class="quantity-selector">
                            <button class="quantity-btn minus" onclick="updateQuantity(-1)">-</button>
                            <input type="number" value="1" min="1" id="quantity">
                            <button class="quantity-btn plus" onclick="updateQuantity(1)">+</button>
                        </div>
                        <button class="add-to-cart-btn">
                            <i class="fas fa-shopping-cart"></i>
                            Add to Cart
                        </button>
                        <button class="wishlist-btn">
                            <i class="far fa-heart"></i>
                        </button>
                    </div>

                    <div class="product-meta">
                        <div class="meta-item">
                            <i class="fas fa-box"></i>
                            <span>Free shipping on orders over $50</span>
                        </div>
                        <div class="meta-item">
                            <i class="fas fa-undo"></i>
                            <span>30-day return policy</span>
                        </div>
                        <div class="meta-item">
                            <i class="fas fa-shield-alt"></i>
                            <span>Secure checkout</span>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Product Details Tabs -->
            <div class="product-tabs">
                <div class="tabs-header">
                    <button class="tab-btn active" data-tab="description">Description</button>
                    <button class="tab-btn" data-tab="ingredients">Ingredients</button>
                    <button class="tab-btn" data-tab="how-to-use">How to Use</button>
                    <!--                    <button class="tab-btn" data-tab="reviews">Reviews</button>-->
                </div>

                <div class="tab-content">
                    <div class="tab-pane active" id="description">
                        <h3>Product Description</h3>
                        <p>${product.description}</p>

                    </div>

                    <div class="tab-pane" id="ingredients">
                        <h3>Ingredients</h3>
                        <p><strong>Free from:</strong> ${product.ingredient}</p>
                    </div>

                    <div class="tab-pane" id="how-to-use">
                        <h3>How to Use</h3>
                        <ol>
                            <c:forEach var="howToUse" items="${fn:split(product.howToUse, ',')}">
                                <li>${howToUse}</li>
                                </c:forEach>
                        </ol>
                    </div>

                    <div class="tab-pane" id="reviews">
                        <h3>Customer Reviews</h3>
                        <div class="reviews-container">
                            <!-- Review items will be dynamically added here -->
                        </div>
                    </div>
                </div>
            </div>

            <!-- Related Products -->
            <div class="related-products">
                <h2>You May Also Like</h2>
                <div class="related-products-grid">
                    <!-- Related products will be dynamically added here -->
                </div>
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

            // Product image gallery
            function changeImage(src) {
                document.getElementById('mainImage').src = src;
            }

            // Quantity selector
            function updateQuantity(change) {
                const quantityInput = document.getElementById('quantity');
                let newValue = parseInt(quantityInput.value) + change;
                if (newValue < 1)
                    newValue = 1;
                quantityInput.value = newValue;
            }

            // Tabs functionality
            const tabBtns = document.querySelectorAll('.tab-btn');
            const tabPanes = document.querySelectorAll('.tab-pane');

            tabBtns.forEach(btn => {
                btn.addEventListener('click', () => {
                    // Remove active class from all buttons and panes
                    tabBtns.forEach(b => b.classList.remove('active'));
                    tabPanes.forEach(p => p.classList.remove('active'));

                    // Add active class to clicked button and corresponding pane
                    btn.classList.add('active');
                    document.getElementById(btn.dataset.tab).classList.add('active');
                });
            });

            // Wishlist button
            const wishlistBtn = document.querySelector('.wishlist-btn');
            wishlistBtn.addEventListener('click', () => {
                const icon = wishlistBtn.querySelector('i');
                icon.classList.toggle('far');
                icon.classList.toggle('fas');
                icon.classList.toggle('text-danger');
            });

            // Add to cart functionality
            document.querySelector('.add-to-cart-btn').addEventListener('click', () => {
                const quantity = document.getElementById('quantity').value;
                // Add your cart logic here
                alert(`Added ${quantity} item(s) to cart`);
            });
        </script>
    </body>

</html>