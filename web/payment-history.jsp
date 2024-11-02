<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>Payment History</title>
            <link rel="stylesheet" href="newUI/assets/css/styles.css">
        </head>

        <body>
            <jsp:include page="NavBarJSP/NavBarJSP.jsp" />

            <div class="payment-history-container">
                <h2>Payment History</h2>
                <div class="payment-list">
                    <c:forEach items="${payments}" var="payment">
                        <div class="payment-item">
                            <div class="payment-header">
                                <span class="order-code">Order #${payment.orderCode}</span>
                                <span class="status ${payment.status.toLowerCase()}">${payment.status}</span>
                            </div>
                            <div class="payment-details">
                                <p>Amount: ${payment.amount} ${payment.currency}</p>
                                <p>Transaction ID: ${payment.transactionId}</p>
                                <p>Description: ${payment.description}</p>
                                <p>Date:
                                    <fmt:formatDate value="${payment.createdAt}" pattern="dd/MM/yyyy HH:mm:ss" />
                                </p>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </body>

        </html>