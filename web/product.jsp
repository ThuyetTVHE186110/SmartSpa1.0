<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Account" %> 
<%@ page import="model.Person" %>  <!-- Import Person class -->
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Products - Blushed Beauty Bar</title>
        <link rel="stylesheet" href="newUI/assets/css/styles.css">
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
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
                        <div class="product-card" data-category="${p.category}" data-aos="fade-up">
                            <div class="product-image">
                                <img class="img-fluid" src="img/${p.image}" alt="">
                                <div class="product-overlay">
                                    <a href="producdetail?id=${p.id}" class="quick-view-btn">View Details</a>
                                </div>
                            </div>
                            <div class="product-info">
                                <h3>${p.name}</h3>
                                <p class="price">${p.price}$</p>
                                <button class="add-to-cart" onclick="addToCart(this)">Add to Cart</button>
                            </div>
                        </div>

                        <!--                        <div class="product-card" data-category="lash-care" data-aos="fade-up">
                                                    <div class="product-image">
                                                        <img src="./assets/img/products/lash-brush.jpg" alt="Lash Brush">
                                                        <div class="product-overlay">
                                                            <button class="quick-view-btn">Quick View</button>
                                                        </div>
                                                    </div>
                                                    <div class="product-info">
                                                        <h3>Professional Lash Brush</h3>
                                                        <p class="price">$12.99</p>
                                                        <button class="add-to-cart">Add to Cart</button>
                                                    </div>
                                                </div>
                        
                                                 Skincare Products 
                                                <div class="product-card" data-category="skincare" data-aos="fade-up">
                                                    <div class="product-image">
                                                        <img src="./assets/img/products/facial-serum.jpg" alt="Facial Serum">
                                                        <div class="product-overlay">
                                                            <button class="quick-view-btn">Quick View</button>
                                                        </div>
                                                    </div>
                                                    <div class="product-info">
                                                        <h3>Hydrating Facial Serum</h3>
                                                        <p class="price">$45.99</p>
                                                        <button class="add-to-cart">Add to Cart</button>
                                                    </div>
                                                </div>
                        
                                                 Tools & Accessories 
                                                <div class="product-card" data-category="tools" data-aos="fade-up">
                                                    <div class="product-image">
                                                        <img src="./assets/img/products/lash-mirror.jpg" alt="Lash Mirror">
                                                        <div class="product-overlay">
                                                            <button class="quick-view-btn">Quick View</button>
                                                        </div>
                                                    </div>
                                                    <div class="product-info">
                                                        <h3>LED Lash Mirror</h3>
                                                        <p class="price">$34.99</p>
                                                        <button class="add-to-cart">Add to Cart</button>
                                                    </div>
                                                </div>-->
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
                <!-- Cart items will be dynamically added here -->
            </div>
            <div class="cart-footer">
                <div class="cart-total">
                    <span>Total:</span>
                    <span class="total-amount">$0.00</span>
                </div>
                <a href="checkout.html" class="checkout-btn">Proceed to Checkout</a>
            </div>
        </div>

        <!-- Cart Toggle Button -->
        <button class="cart-toggle">
            <i class="fas fa-shopping-cart"></i>
            <span class="cart-count">0</span>
        </button>

        <!-- Footer Section -->
        <footer>
            <!-- [Previous footer code remains the same] -->
        </footer>

        <script>
            document.addEventListener('DOMContentLoaded', function () {
                // Chọn tất cả các nút danh mục và thẻ sản phẩm
                const categoryButtons = document.querySelectorAll('.category-btn');
                const productCards = document.querySelectorAll('.product-card');

                // Lặp qua từng nút danh mục
                categoryButtons.forEach(button => {
                    // Thêm sự kiện click cho từng nút
                    button.addEventListener('click', () => {
                        // Lấy danh mục của nút được nhấn
                        const category = button.getAttribute('data-category');

                        // Xóa lớp 'active' khỏi tất cả các nút và chỉ thêm vào nút được nhấn
                        categoryButtons.forEach(btn => btn.classList.remove('active'));
                        button.classList.add('active');

                        // Hiển thị hoặc ẩn các thẻ sản phẩm dựa trên danh mục đã chọn
                        productCards.forEach(card => {
                            // Lấy danh mục của thẻ sản phẩm
                            const productCategory = card.getAttribute('data-category');

                            // Hiển thị thẻ nếu nó khớp với danh mục đã chọn hoặc nếu chọn 'all'
                            if (category === 'all' || productCategory === category) {
                                card.style.display = 'block'; // Hiển thị thẻ
                            } else {
                                card.style.display = 'none'; // Ẩn thẻ
                            }
                        });
                    });
                });
            });

            AOS.init();

            // Hamburger menu functionality
            const hamburger = document.querySelector('.hamburger');
            const navLinks = document.querySelector('.nav-links');

            hamburger.addEventListener('click', () => {
                navLinks.classList.toggle('active');
                hamburger.classList.toggle('active');
            });

            // Category filter functionality
            const categoryBtns = document.querySelectorAll('.category-btn');
            const productCards = document.querySelectorAll('.product-card');

            categoryBtns.forEach(btn => {
                btn.addEventListener('click', () => {
                    // Remove active class from all buttons
                    categoryBtns.forEach(b => b.classList.remove('active'));
                    // Add active class to clicked button
                    btn.classList.add('active');

                    const category = btn.dataset.category;

                    productCards.forEach(card => {
                        if (category === 'all' || card.dataset.category === category) {
                            card.style.display = 'block';
                        } else {
                            card.style.display = 'none';
                        }
                    });
                });
            });

            // Quick View functionality
            const quickViewBtns = document.querySelectorAll('.quick-view-btn');
            const quickViewModal = document.querySelector('.quick-view-modal');
            const closeModalBtn = document.querySelector('.close-modal');

            quickViewBtns.forEach(btn => {
                btn.addEventListener('click', () => {
                    const productCard = btn.closest('.product-card');
                    const productImage = productCard.querySelector('img').src;
                    const productTitle = productCard.querySelector('h3').textContent;
                    const productPrice = productCard.querySelector('.price').textContent;

                    // Update modal content
                    quickViewModal.querySelector('img').src = productImage;
                    quickViewModal.querySelector('.product-title').textContent = productTitle;
                    quickViewModal.querySelector('.product-price').textContent = productPrice;

                    // Show modal
                    quickViewModal.classList.add('active');
                    document.body.style.overflow = 'hidden';
                });
            });

            closeModalBtn.addEventListener('click', () => {
                quickViewModal.classList.remove('active');
                document.body.style.overflow = '';
            });

            // Cart functionality
            const cartToggle = document.querySelector('.cart-toggle');
            const cartSidebar = document.querySelector('.cart-sidebar');
            const closeCart = document.querySelector('.close-cart');
            let cartCount = 0;

            cartToggle.addEventListener('click', () => {
                cartSidebar.classList.add('active');
            });

            closeCart.addEventListener('click', () => {
                cartSidebar.classList.remove('active');
            });

            // Add to cart functionality
            const addToCartBtns = document.querySelectorAll('.add-to-cart');

            addToCartBtns.forEach(btn => {
                btn.addEventListener('click', () => {
                    cartCount++;
                    document.querySelector('.cart-count').textContent = cartCount;
                    // Add animation to cart icon
                    cartToggle.classList.add('bounce');
                    setTimeout(() => cartToggle.classList.remove('bounce'), 300);
                });
            });

            function addToCart(button) {
                const productCard = button.closest('.product-card');
                const productName = productCard.querySelector('h3').textContent;
                const productPrice = productCard.querySelector('.price').textContent;
                const productImage = productCard.querySelector('img').src;

                // Add item to cart
                const cartItem = document.createElement('div');
                cartItem.className = 'cart-item';
                cartItem.innerHTML = `
                    <img src="${productImage}" alt="${productName}">
                    <div class="item-details">
                        <h4>${productName}</h4>
                        <p class="item-price">${productPrice}</p>
                        <div class="item-quantity">
                            <span>Qty: 1</span>
                        </div>
                    </div>
                `;

                document.querySelector('.cart-items').appendChild(cartItem);

                // Update cart count
                cartCount++;
                document.querySelector('.cart-count').textContent = cartCount;

                // Show cart sidebar
                document.querySelector('.cart-sidebar').classList.add('active');

                // Add animation to cart icon
                cartToggle.classList.add('bounce');
                setTimeout(() => cartToggle.classList.remove('bounce'), 300);

                // Update total
                updateCartTotal();
            }

            function updateCartTotal() {
                let total = 0;
                document.querySelectorAll('.cart-item .item-price').forEach(price => {
                    total += parseFloat(price.textContent.replace('$', ''));
                });
                document.querySelector('.total-amount').textContent = `$${total.toFixed(2)}`;
            }
        </script>
    </body>

</html>