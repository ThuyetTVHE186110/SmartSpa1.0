function filterBills() {
    const dateRange = document.getElementById('dateRange').value;
    const status = document.getElementById('status').value;
    fetchBills({ dateRange, status });
}

function searchBills(query) {
    fetchBills({ search: query });
}

function fetchBills(filters) {
    const params = [];
    for (const key in filters) {
        if (filters[key]) {
            params.push(`${key}=${encodeURIComponent(filters[key])}`);
        }
    }
    const queryString = params.length > 0 ? `?${params.join('&')}` : '';

    fetch(`${window.location.origin}/SmartSpa1.0/staff/bills${queryString}`)
        .then(response => response.json())
        .then(data => updateBillsTable(data))
        .catch(error => {
            console.error('Error fetching bills:', error);
            alert('Error loading bills. Please try again.');
        });
}

function updateBillsTable(bills) {
    const tbody = document.getElementById('billsTableBody');
    tbody.innerHTML = bills.map(bill => `
        <tr>
            <td>${bill.billCode}</td>
            <td>${new Date(bill.createdAt).toLocaleString()}</td>
            <td>
                <div>
                    <strong>${bill.customerName}</strong><br>
                    <small>${bill.customerPhone}</small>
                </div>
            </td>
            <td>
                <div class="services-list">
                    ${bill.billDetails.map(detail => detail.serviceName).join(', ')}
                </div>
            </td>
            <td>$${bill.total.toFixed(2)}</td>
            <td>
                <span class="status-badge ${bill.status.toLowerCase()}">
                    ${bill.status}
                </span>
            </td>
            <td>
                <div class="action-buttons">
                    <button class="btn-icon" onclick="viewBill('${bill.billCode}')" title="View Bill">
                        <i class="fas fa-eye"></i>
                    </button>
                    <button class="btn-icon" onclick="printBill('${bill.billCode}')" title="Print Bill">
                        <i class="fas fa-print"></i>
                    </button>
                    <button class="btn-icon" onclick="downloadBill('${bill.billCode}')" title="Download PDF">
                        <i class="fas fa-download"></i>
                    </button>
                </div>
            </td>
        </tr>
    `).join('');
}

function viewBill(billCode) {
    window.location.href = `${window.location.origin}/SmartSpa1.0/staff/bill/${billCode}`;
}

function printBill(billCode) {
    // Implement print functionality
}

function downloadBill(billCode) {
    // Implement download functionality
}

function exportBills() {
    // Implement export functionality
}

// Load bills when page loads
document.addEventListener('DOMContentLoaded', () => {
    filterBills();
}); 