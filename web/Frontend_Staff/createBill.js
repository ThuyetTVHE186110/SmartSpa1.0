let selectedServices = [];
let qrCode = null;

document.addEventListener('DOMContentLoaded', function () {
    // Initialize QR Code
    qrCode = new QRCode(document.getElementById("qrcode"), {
        width: 128,
        height: 128
    });

    // Category Filter
    document.querySelectorAll('.category-btn').forEach(button => {
        button.addEventListener('click', function () {
            document.querySelectorAll('.category-btn').forEach(btn => btn.classList.remove('active'));
            this.classList.add('active');
            filterServices(this.dataset.category);
        });
    });

    // Search Services
    document.getElementById('serviceSearch').addEventListener('input', function () {
        searchServices(this.value);
    });

    // Print Bill
    document.getElementById('printBill').addEventListener('click', async function () {
        await createBill();
    });

    // Clear Bill
    document.getElementById('clearBill').addEventListener('click', clearBill);
});

function addService(service) {
    selectedServices.push(service);
    updateBillDisplay();
    updateQRCode();
}

function removeService(index) {
    selectedServices.splice(index, 1);
    updateBillDisplay();
    updateQRCode();
}

function updateBillDisplay() {
    const servicesList = document.getElementById('selectedServices');
    servicesList.innerHTML = '';

    selectedServices.forEach((service, index) => {
        const item = document.createElement('div');
        item.className = 'bill-item';
        item.innerHTML = `
            <div>
                <h4>${service.name}</h4>
                <span>$${service.price.toFixed(2)}</span>
            </div>
            <button onclick="removeService(${index})" class="remove-btn">
                <i class="fas fa-times"></i>
            </button>
        `;
        servicesList.appendChild(item);
    });

    updateTotals();
}

function updateTotals() {
    const subtotal = selectedServices.reduce((sum, service) => sum + service.price, 0);
    const tax = subtotal * 0.1; // 10% tax
    const total = subtotal + tax;

    document.getElementById('subtotal').textContent = `$${subtotal.toFixed(2)}`;
    document.getElementById('tax').textContent = `$${tax.toFixed(2)}`;
    document.getElementById('total').textContent = `$${total.toFixed(2)}`;
}

function updateQRCode() {
    const billData = {
        customer: document.getElementById('customerName').value,
        phone: document.getElementById('customerPhone').value,
        services: selectedServices,
        total: parseFloat(document.getElementById('total').textContent.substring(1)),
        timestamp: new Date().toISOString()
    };

    // Convert billData to string before making QR code
    const qrString = JSON.stringify(billData);
    if (qrString) {
        qrCode.makeCode(qrString);
    }
}

async function createBill() {
    // Validate form
    const customerName = document.getElementById('customerName').value;
    const customerPhone = document.getElementById('customerPhone').value;

    if (!customerName || !customerPhone) {
        alert('Please fill in customer information');
        return;
    }

    if (selectedServices.length === 0) {
        alert('Please select at least one service');
        return;
    }

    const billData = {
        customerName,
        customerPhone,
        services: selectedServices,
        subtotal: parseFloat(document.getElementById('subtotal').textContent.substring(1)),
        tax: parseFloat(document.getElementById('tax').textContent.substring(1)),
        total: parseFloat(document.getElementById('total').textContent.substring(1))
    };

    try {
        // Use window.location.origin to get the base URL
        const response = await fetch(`${window.location.origin}/SmartSpa1.0/staff/bill`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(billData)
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.error || 'Failed to create bill');
        }

        const bill = await response.json();

        // Update QR code with bill information
        const qrData = {
            billCode: bill.billCode,
            customerName: bill.customerName,
            total: bill.total,
            date: new Date().toISOString()
        };
        updateQRCode(JSON.stringify(qrData));

        // Print bill
        await printBill(bill);

        // Show success message
        alert('Bill created successfully!');

        // Clear form
        clearBill();

    } catch (error) {
        console.error('Error creating bill:', error);
        alert(error.message);
    }
}

// Update the printBill function
async function printBill(bill) {
    // Format current date
    const currentDate = new Date(bill.createdAt || new Date()).toLocaleString();

    // Create the bill content
    const billContent = `
        <div class="bill-header">
            <img src="${window.location.origin}/SmartSpa1.0/Frontend_Staff/assets/images/logo.png" alt="Spa Logo" class="print-logo">
            <h1>Spa Services Bill</h1>
            <div class="print-meta">
                <p class="company-info">Smart Beauty Spa</p>
                <p>Phone: (123) 456-7890</p>
                <p>Email: contact@smartbeautyspa.com</p>
                <p>Address: 123 Spa Street, Beauty City</p>
            </div>
        </div>
        <div class="bill-content">
            <div class="bill-details">
                <div class="bill-info">
                    <h2>Bill #${bill.billCode || 'N/A'}</h2>
                    <p>Date: ${currentDate}</p>
                </div>
                <div class="customer-info">
                    <h3>Customer Details</h3>
                    <p><strong>Name:</strong> ${bill.customerName}</p>
                    <p><strong>Phone:</strong> ${bill.customerPhone}</p>
                </div>
                <div class="services-list">
                    <h3>Services</h3>
                    <table class="services-table">
                        <thead>
                            <tr>
                                <th>Service</th>
                                <th style="text-align: right;">Price</th>
                            </tr>
                        </thead>
                        <tbody>
                            ${(bill.billDetails || selectedServices).map(detail => `
                                <tr>
                                    <td>${detail.serviceName || detail.name}</td>
                                    <td style="text-align: right;">$${(detail.price).toFixed(2)}</td>
                                </tr>
                            `).join('')}
                        </tbody>
                    </table>
                </div>
                <div class="bill-summary">
                    <div class="subtotal">
                        <span>Subtotal:</span>
                        <span>$${bill.subtotal.toFixed(2)}</span>
                    </div>
                    <div class="tax">
                        <span>Tax (10%):</span>
                        <span>$${bill.tax.toFixed(2)}</span>
                    </div>
                    <div class="total">
                        <span><strong>Total:</strong></span>
                        <span><strong>$${bill.total.toFixed(2)}</strong></span>
                    </div>
                </div>
            </div>
        </div>
        <div class="bill-footer">
            <div class="qr-print">
                ${document.getElementById('qrcode').innerHTML}
            </div>
            <p>Thank you for choosing our services!</p>
            <p class="terms">Terms & Conditions apply</p>
        </div>
    `;

    // Store the bill content in localStorage
    localStorage.setItem('billPreviewContent', billContent);

    // Open the preview in a new window
    const previewWindow = window.open(
        `${window.location.origin}/SmartSpa1.0/Frontend_Staff/billPreview.jsp`,
        '_blank',
        'width=800,height=800'
    );
}

function clearBill() {
    selectedServices = [];
    document.getElementById('customerName').value = '';
    document.getElementById('customerPhone').value = '';
    updateBillDisplay();
    updateQRCode();
}

function filterServices(category) {
    const services = document.querySelectorAll('.service-card');
    services.forEach(service => {
        if (category === 'all' || service.dataset.category === category) {
            service.style.display = 'block';
        } else {
            service.style.display = 'none';
        }
    });
}

function searchServices(query) {
    const services = document.querySelectorAll('.service-card');
    services.forEach(service => {
        const name = service.querySelector('h3').textContent.toLowerCase();
        if (name.includes(query.toLowerCase())) {
            service.style.display = 'block';
        } else {
            service.style.display = 'none';
        }
    });
}

// Update the confirmAndPrint function
function confirmAndPrint() {
    const printContent = document.getElementById('printPreviewContent').innerHTML;
    const printWindow = window.open('', '_blank');

    printWindow.document.write(`
        <!DOCTYPE html>
        <html>
            <head>
                <title>Print Bill</title>
                <link rel="stylesheet" href="${window.location.origin}/SmartSpa1.0/Frontend_Staff/billing.css">
                <style>
                    body { 
                        margin: 0; 
                        padding: 2rem;
                        font-family: Arial, sans-serif;
                    }
                    .print-template { 
                        display: block;
                        max-width: 800px;
                        margin: 0 auto;
                        padding: 20px;
                    }
                    .services-table { 
                        width: 100%; 
                        border-collapse: collapse; 
                        margin: 1rem 0; 
                    }
                    .services-table th, .services-table td { 
                        padding: 0.5rem; 
                        border-bottom: 1px solid #eee; 
                        text-align: left;
                    }
                    .services-table th { 
                        background: #f8f9fa; 
                        font-weight: bold;
                    }
                    .bill-summary {
                        margin-top: 2rem;
                        border-top: 2px solid #eee;
                        padding-top: 1rem;
                    }
                    .bill-summary > div {
                        display: flex;
                        justify-content: space-between;
                        margin-bottom: 0.5rem;
                    }
                    .total {
                        font-weight: bold;
                        font-size: 1.1em;
                    }
                    @media print {
                        body { margin: 0; padding: 0; }
                        .print-template { margin: 0; }
                        .no-print { display: none; }
                    }
                </style>
            </head>
            <body>
                ${printContent}
                <script>
                    window.onload = function() { 
                        setTimeout(function() {
                            window.print();
                            window.close();
                        }, 1000);
                    }
                </script>
            </body>
        </html>
    `);
}

// Add function to close print preview
function closePrintPreview() {
    document.getElementById('printPreviewModal').classList.remove('active');
}