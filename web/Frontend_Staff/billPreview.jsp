<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ page import="model.Account" %>
        <%@page contentType="text/html" pageEncoding="UTF-8" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Bill Preview</title>
                <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/Frontend_Staff/billing.css">
                <style>
                    body {
                        background: #f1f5f9;
                        padding: 2rem;
                        font-family: Arial, sans-serif;
                    }

                    .preview-container {
                        max-width: 800px;
                        margin: 0 auto;
                        background: white;
                        border-radius: 8px;
                        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                        overflow: hidden;
                    }

                    .preview-actions {
                        padding: 1rem;
                        background: #f8fafc;
                        border-bottom: 1px solid #e2e8f0;
                        display: flex;
                        justify-content: flex-end;
                        gap: 1rem;
                    }

                    .preview-content {
                        padding: 2rem;
                    }

                    .btn-primary,
                    .btn-secondary {
                        padding: 0.5rem 1rem;
                        border-radius: 4px;
                        cursor: pointer;
                        display: inline-flex;
                        align-items: center;
                        gap: 0.5rem;
                        font-size: 0.9rem;
                    }

                    .btn-primary {
                        background: #3b82f6;
                        color: white;
                        border: none;
                    }

                    .btn-secondary {
                        background: #f1f5f9;
                        color: #64748b;
                        border: 1px solid #e2e8f0;
                    }
                </style>
            </head>

            <body>
                <div class="preview-container">
                    <div class="preview-actions">
                        <button class="btn-secondary" onclick="window.close()">
                            <i class="fas fa-times"></i> Close
                        </button>
                        <button class="btn-primary" onclick="printBill()">
                            <i class="fas fa-print"></i> Print
                        </button>
                    </div>
                    <div class="preview-content">
                        <div id="printTemplate" class="print-template" style="display: block;">
                            <!-- Bill content will be inserted here by JavaScript -->
                        </div>
                    </div>
                </div>

                <script>
                    // Function to get URL parameters
                    function getQueryParam(param) {
                        const urlParams = new URLSearchParams(window.location.search);
                        return urlParams.get(param);
                    }

                    // Function to print the bill
                    function printBill() {
                        window.print();
                    }

                    // Load the bill content when the page loads
                    window.onload = function () {
                        const billContent = localStorage.getItem('billPreviewContent');
                        if (billContent) {
                            document.getElementById('printTemplate').innerHTML = billContent;
                            // Clear the storage after loading
                            localStorage.removeItem('billPreviewContent');
                        }
                    };
                </script>
            </body>

            </html>