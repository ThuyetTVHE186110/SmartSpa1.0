<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ page import="model.Account" %>
        <%@page contentType="text/html" pageEncoding="UTF-8" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Create Bill - Spa Management</title>
                <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/Frontend_Staff/styles.css">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/Frontend_Staff/createBill.css">
            </head>

            <body>
                <div class="dashboard-container">
                    <jsp:include page="sideBar.jsp" />

                    <div class="main-content">
                        <jsp:include page="topbar.jsp" />

                        <main class="billing-container">
                            <div class="billing-header">
                                <h1>Create New Bill</h1>
                                <div class="bill-actions">
                                    <button class="btn-secondary" id="clearBill">
                                        <i class="fas fa-redo"></i> Clear
                                    </button>
                                    <button class="btn-primary" id="printBill">
                                        <i class="fas fa-print"></i> Print Bill
                                    </button>
                                </div>
                            </div>

                            <div class="billing-content">
                                <!-- Left Side - Service Selection -->
                                <div class="service-selection">
                                    <div class="search-services">
                                        <input type="text" placeholder="Search services..." id="serviceSearch">
                                        <div class="service-categories">
                                            <button class="category-btn active" data-category="all">All</button>
                                            <c:forEach items="${categories}" var="category">
                                                <button class="category-btn"
                                                    data-category="${category}">${category}</button>
                                            </c:forEach>
                                        </div>
                                    </div>

                                    <div class="services-grid">
                                        <c:forEach items="${services}" var="service">
                                            <div class="service-card" data-id="${service.id}"
                                                data-price="${service.price}" data-category="${service.category}">
                                                <img src="${not empty service.image ? service.image : 'assets/default-service.jpg'}"
                                                    alt="${service.name}">
                                                <div class="service-info">
                                                    <h3>${service.name}</h3>
                                                    <p class="price">$${service.price}</p>
                                                    <p class="duration"><i class="fas fa-clock"></i> ${service.duration}
                                                        mins</p>
                                                </div>
                                                <button class="add-service-btn" onclick="addService({
                                                    id: ${service.id},
                                                    name: '${service.name}',
                                                    price: ${service.price}
                                                })">
                                                    <i class="fas fa-plus"></i> Add
                                                </button>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>

                                <!-- Right Side - Bill Preview -->
                                <div class="bill-preview">
                                    <div class="customer-info">
                                        <h2>Customer Information</h2>
                                        <div class="form-group">
                                            <input type="text" id="customerName" placeholder="Customer Name">
                                            <input type="tel" id="customerPhone" placeholder="Phone Number">
                                        </div>
                                    </div>

                                    <div class="bill-items">
                                        <h2>Selected Services</h2>
                                        <div class="items-list" id="selectedServices">
                                            <!-- Selected services will be added here dynamically -->
                                        </div>

                                        <div class="bill-summary">
                                            <div class="subtotal">
                                                <span>Subtotal</span>
                                                <span id="subtotal">$0.00</span>
                                            </div>
                                            <div class="tax">
                                                <span>Tax (10%)</span>
                                                <span id="tax">$0.00</span>
                                            </div>
                                            <div class="total">
                                                <span>Total</span>
                                                <span id="total">$0.00</span>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="qr-code">
                                        <!-- QR code will be generated here -->
                                        <div id="qrcode"></div>
                                    </div>
                                </div>
                            </div>
                        </main>
                    </div>
                </div>

                <!-- Print Template -->
                <div id="printTemplate" class="print-template">
                    <div class="bill-header">
                        <img src="${pageContext.request.contextPath}/Frontend_Staff/assets/images/logo.png"
                            alt="Spa Logo" class="print-logo">
                        <h1>Spa Services Bill</h1>
                        <div class="print-meta">
                            <p class="company-info">Smart Beauty Spa</p>
                            <p>Phone: (123) 456-7890</p>
                            <p>Email: contact@smartbeautyspa.com</p>
                            <p>Address: 123 Spa Street, Beauty City</p>
                        </div>
                    </div>
                    <div class="bill-content">
                        <!-- Bill content will be cloned here for printing -->
                    </div>
                    <div class="bill-footer">
                        <div class="qr-print"></div>
                        <p>Thank you for choosing our services!</p>
                        <p class="terms">Terms & Conditions apply</p>
                    </div>
                </div>

                <!-- Print Preview Modal -->
                <div id="printPreviewModal" class="modal">
                    <div class="modal-content print-preview-content">
                        <div class="modal-header">
                            <h2>Print Preview</h2>
                            <div class="preview-actions">
                                <button class="btn-primary" onclick="confirmAndPrint()">
                                    <i class="fas fa-print"></i> Print
                                </button>
                                <button class="btn-secondary" onclick="closePrintPreview()">
                                    <i class="fas fa-times"></i> Close
                                </button>
                            </div>
                        </div>
                        <div class="modal-body">
                            <div id="printPreviewContent">
                                <!-- Print content will be loaded here -->
                            </div>
                        </div>
                    </div>
                </div>

                <script src="https://cdnjs.cloudflare.com/ajax/libs/qrcodejs/1.0.0/qrcode.min.js"></script>
                <script src="${pageContext.request.contextPath}/Frontend_Staff/createBill.js"></script>
            </body>

            </html>