<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="model.Account" %>
<%@ page import="model.Person" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Products - Blushed Beauty Bar</title>
        <link rel="stylesheet" href="newUI/assets/css/styles.css">
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
    </head>

    <body>
        <jsp:include page="NavBarJSP/NavBarJSP.jsp" />

        <!-- Products Hero Section -->
        <section class="products-hero">
            <div class="hero-content">
                <h1>Shop Our Products</h1>
                <p>Professional-grade beauty products for home care</p>
            </div>
        </section>
        <!-- Product Categories Section -->
        <section class="product-categories">
            <div style="display: flex; padding-bottom: 20px">
                <form action="searchproduct?index=1" method="post" style="display: flex; align-items: center; margin-left: auto">
                    <input class="searchBox" type="text" name="txtSearch" required 
                           style="padding: 7px; border: 1px solid #ccc; border-right: none; width: 200px; border-radius: 10px 0 0 10px;" 
                           value="${param.txtSearch}"> <!-- Giữ lại giá trị đã nhập -->
                    <input class="searchButton" type="submit" name="btnGo" value="Search" 
                           style="background-color: #B38886; color: white; border: 1px solid #F9A392; border-left: none; padding: 7px; cursor: pointer; border-radius: 0 10px 10px 0;">
                </form>
            </div>
            <div class="container">
                <div class="category-filters">
                    <button class="category-btn active" data-category="all">All Products</button>
                    <button class="category-btn" data-category="Lash-Care">Lash Care</button>
                    <button class="category-btn" data-category="Skincare">Skincare</button>
                    <button class="category-btn" data-category="Tools">Tools & Accessories</button>
                </div>



                <!-- Products Grid -->
                <div class="products-grid">
                    <c:forEach items="${product}" var="p">
                        <!-- Lash Care Products -->
                        <div class="product-card" data-category="${p.category}"
                             data-product-id="${p.id}" data-aos="fade-up">
                            <div class="product-image">
                                <img class="img-fluid" src="img/${p.image}" alt="${p.name}">
                                <div class="product-overlay">
                                    <a href="producdetail?id=${p.id}" class="quick-view-btn">View
                                        Details</a>
                                </div>
                            </div>
                            <div class="product-info">
                                <h3>${p.name}</h3>
                                <p class="price">${p.price}</p>
                                <form action="cart" method="POST" style="display: inline;">
                                    <input type="hidden" name="action" value="addToCart">
                                    <input type="hidden" name="productId" value="${p.id}">
                                    <button type="submit" class="add-to-cart">Add to Cart</button>
                                </form>
                            </div>
                        </div>
                    </c:forEach>
                </div>

            </div>
        </section>

        <!-- Quick View Modal -->
        <div class="quick-view-modal">
            <div class="modal-content">
                <button class="close-modal"><i class="fas fa-times"></i></button>
                <div class="product-details">
                    <div class="product-image">
                        <img src="" alt="Product Image">
                    </div>
                    <div class="product-info">
                        <h2 class="product-title"></h2>
                        <p class="product-price"></p>
                        <p class="product-description">
                            Detailed product description will appear here.
                        </p>
                        <div class="quantity-selector">
                            <button class="quantity-btn minus">-</button>
                            <input type="number" value="1" min="1" class="quantity-input">
                            <button class="quantity-btn plus">+</button>
                        </div>
                        <button class="add-to-cart-btn">Add to Cart</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Shopping Cart Sidebar -->
        <div class="cart-sidebar">
            <div class="cart-header">
                <h3>Shopping Cart</h3>
                <button class="close-cart"><i class="fas fa-times"></i></button>
            </div>
            <div class="cart-items">
                <c:forEach items="${cartItems}" var="item">
                    <div class="cart-item" data-id="${item.id}" data-product-id="${item.productId}">
                        <img src="img/${item.image}" alt="${item.productName}">
                        <div class="item-details">
                            <h4>${item.productName}</h4>
                            <p class="item-price">$${item.price}</p>
                            <div class="item-quantity">
                                <form action="cart" method="POST" style="display: inline;">
                                    <input type="hidden" name="action" value="updateQuantity">
                                    <input type="hidden" name="cartItemId" value="${item.id}">
                                    <input type="hidden" name="quantity"
                                           value="${item.productQuantity - 1}">
                                    <button type="submit">-</button>
                                </form>
                                <span>Qty: ${item.productQuantity}</span>
                                <form action="cart" method="POST" style="display: inline;">
                                    <input type="hidden" name="action" value="updateQuantity">
                                    <input type="hidden" name="cartItemId" value="${item.id}">
                                    <input type="hidden" name="quantity"
                                           value="${item.productQuantity + 1}">
                                    <button type="submit">+</button>
                                </form>
                            </div>
                            <form action="cart" method="POST" style="display: inline;">
                                <input type="hidden" name="action" value="removeItem">
                                <input type="hidden" name="cartItemId" value="${item.id}">
                                <button type="submit" class="remove-btn">Remove</button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="cart-footer">
                <div class="cart-total">
                    <span>Total:</span>
                    <span class="total-amount">
                        $${cartTotal} (${cartTotal * 24000} VND)
                    </span>
                </div>
                <form id="checkoutForm" action="order" method="POST">
                    <c:forEach items="${cartItems}" var="item">
                        <input type="hidden" name="productId" value="${item.productId}">
                        <input type="hidden" name="quantity" value="${item.productQuantity}">
                    </c:forEach>
                    <input type="hidden" name="totalAmount" value="${fn:replace(cartTotal, '$', '')}">
                    <button type="submit" class="checkout-btn">Proceed to Checkout</button>
                </form>
            </div>
        </div>

        <!-- Add this success/error message container -->
        <div id="messageContainer" style="display: none;" class="message-container">
            <p id="messageText"></p>
        </div>

        <!-- Cart Toggle Button -->
        <button class="cart-toggle">
            <i class="fas fa-shopping-cart"></i>
            <span class="cart-count">${cartCount}</span>
        </button>

        <!-- Footer Section -->
        <footer>
            <!-- [Previous footer code remains the same] -->
        </footer>

        <!-- Keep only the essential JavaScript for UI functionality -->
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                // Category filter functionality
                const categoryButtons = document.querySelectorAll('.category-btn');
                const productCards = document.querySelectorAll('.product-card');

                categoryButtons.forEach(button => {
                    button.addEventListener('click', () => {
                        const category = button.getAttribute('data-category');

                        categoryButtons.forEach(btn => btn.classList.remove('active'));
                        button.classList.add('active');

                        productCards.forEach(card => {
                            const productCategory = card.getAttribute('data-category');
                            if (category === 'all' || productCategory === category) {
                                card.style.display = 'block';
                            } else {
                                card.style.display = 'none';
                            }
                        });
                    });
                });

                // Initialize AOS (Animate on Scroll)
                AOS.init();

                // Hamburger menu functionality
                const hamburger = document.querySelector('.hamburger');
                const navLinks = document.querySelector('.nav-links');

                hamburger.addEventListener('click', () => {
                    navLinks.classList.toggle('active');
                    hamburger.classList.toggle('active');
                });

                // Cart sidebar toggle
                const cartToggle = document.querySelector('.cart-toggle');
                const cartSidebar = document.querySelector('.cart-sidebar');
                const closeCart = document.querySelector('.close-cart');

                cartToggle.addEventListener('click', () => {
                    cartSidebar.classList.add('active');
                });

                closeCart.addEventListener('click', () => {
                    cartSidebar.classList.remove('active');
                });

                // Add this JavaScript to handle URL parameters and show messages
                document.addEventListener('DOMContentLoaded', function () {
                    // Check for URL parameters
                    const urlParams = new URLSearchParams(window.location.search);
                    const status = urlParams.get('status');
                    const message = urlParams.get('message');

                    if (status && message) {
                        const messageContainer = document.getElementById('messageContainer');
                        const messageText = document.getElementById('messageText');

                        messageContainer.style.display = 'block';
                        messageText.textContent = decodeURIComponent(message);
                        messageContainer.className = `message-container ${status}`;

                        // Hide message after 5 seconds
                        setTimeout(() => {
                            messageContainer.style.display = 'none';
                        }, 5000);

                        // Clean URL
                        window.history.replaceState({}, document.title, window.location.pathname);
                    }
                });
            });
        </script>
    </body>

</html>