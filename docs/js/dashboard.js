// ---------- TOGGLE ABOUT SECTION ----------
function toggleAbout() {
    const content = document.getElementById('about-content');
    const icon = document.getElementById('toggle-icon');
    
    if (content.style.display === 'none') {
        content.style.display = 'block';
        content.style.animation = 'fadeInUp 0.4s ease-out';
        icon.style.transform = 'rotate(0deg)';
    } else {
        content.style.display = 'none';
        icon.style.transform = 'rotate(-90deg)';
    }
}

// STEP 1: Run Simulation
async function runSimulation() {
    const button = event.target;
    const originalText = button.innerHTML;
    
    // Add loading state
    button.disabled = true;
    button.innerHTML = `
        <svg class="loading" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <circle cx="12" cy="12" r="10"/>
            <path d="M12 6v6l4 2"/>
        </svg>
        Running Simulation...
    `;
    
    try {
        const response = await fetch('/simulation/run', {
            method: 'POST'
        });

        if (response.ok) {
            // Success animation
            document.getElementById("simulation-section").style.animation = "fadeOut 0.5s ease-out";
            
            setTimeout(() => {
                document.getElementById("simulation-section").style.display = "none";
                document.getElementById("menu-section").style.display = "block";
                document.getElementById("menu-section").style.animation = "fadeInUp 0.6s ease-out";
                
                document.getElementById("data-section").innerHTML = `
                    <div style="text-align: center; padding: 2rem;">
                        <div style="width: 80px; height: 80px; background: linear-gradient(135deg, #10b981, #059669); border-radius: 50%; display: flex; align-items: center; justify-content: center; margin: 0 auto 1.5rem; box-shadow: 0 0 30px rgba(16, 185, 129, 0.4);">
                            <svg viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="3" style="width: 40px; height: 40px;">
                                <polyline points="20 6 9 17 4 12"/>
                            </svg>
                        </div>
                        <h3 style="color: #10b981; margin-bottom: 0.5rem;">Simulation Completed Successfully</h3>
                        <p style="color: #94a3b8;">Select a data category to explore the generated results</p>
                    </div>
                `;
            }, 500);
        } else {
            throw new Error('Simulation failed');
        }
    } catch (error) {
        button.innerHTML = originalText;
        button.disabled = false;
        
        document.getElementById("data-section").innerHTML = `
            <div style="text-align: center; padding: 2rem;">
                <div style="width: 80px; height: 80px; background: linear-gradient(135deg, #ef4444, #dc2626); border-radius: 50%; display: flex; align-items: center; justify-content: center; margin: 0 auto 1.5rem;">
                    <svg viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="3" style="width: 40px; height: 40px;">
                        <line x1="18" y1="6" x2="6" y2="18"/>
                        <line x1="6" y1="6" x2="18" y2="18"/>
                    </svg>
                </div>
                <h3 style="color: #ef4444; margin-bottom: 0.5rem;">Simulation Failed</h3>
                <p style="color: #94a3b8;">Error connecting to server. Please try again.</p>
            </div>
        `;
        document.getElementById("data-section").style.display = "block";
    }
}

// ---------- LOAD ALL COMPANIES ----------
async function loadCompanies() {
    showLoadingState();
    
    try {
        const response = await fetch('/api/companies');
        const data = await response.json();

        let html = `
            <h3>
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" style="width: 24px; height: 24px; display: inline-block; vertical-align: middle;">
                    <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"/>
                    <polyline points="9 22 9 12 15 12 15 22"/>
                </svg>
                All Companies (${data.length})
            </h3>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Country</th>
                        <th>Incorporation Date</th>
                        <th>Employees</th>
                        <th>Revenue</th>
                        <th>Industry</th>
                        <th>Shell?</th>
                        <th>Risk Score</th>
                        <th>Created At</th>
                    </tr>
                </thead>
                <tbody>
        `;

        data.forEach(company => {
            const rowClass = company.isShellTruth ? "shell-row" : "";
            const shellBadge = company.isShellTruth 
                ? '<span style="background: #ef4444; color: white; padding: 0.25rem 0.5rem; border-radius: 0.25rem; font-size: 0.875rem;">Yes</span>'
                : '<span style="background: #10b981; color: white; padding: 0.25rem 0.5rem; border-radius: 0.25rem; font-size: 0.875rem;">No</span>';
            
            const riskColor = company.riskScore > 70 ? '#ef4444' : company.riskScore > 40 ? '#f59e0b' : '#10b981';

            html += `
                <tr class="${rowClass}">
                    <td>${company.id}</td>
                    <td><strong>${company.name}</strong></td>
                    <td>${company.country}</td>
                    <td>${company.incorporationDate || 'N/A'}</td>
                    <td>${company.employeeCount}</td>
                    <td>$${company.declaredRevenue.toLocaleString()}</td>
                    <td>${company.industry}</td>
                    <td>${shellBadge}</td>
                    <td><span style="color: ${riskColor}; font-weight: 600;">${company.riskScore}</span></td>
                    <td>${company.createdAt || 'N/A'}</td>
                </tr>
            `;
        });

        html += `
                </tbody>
            </table>
        `;

        document.getElementById("data-section").innerHTML = html;
        document.getElementById("data-section").style.display = "block";
    } catch (error) {
        showErrorState('Failed to load companies');
    }
}

// ---------- LOAD SHELL COMPANIES ----------
async function loadShellCompanies() {
    showLoadingState();
    
    try {
        const response = await fetch('/api/companies');
        const data = await response.json();
        const shellCompanies = data.filter(company => company.isShellTruth);

        let html = `
            <h3 style="color: #ef4444;">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" style="width: 24px; height: 24px; display: inline-block; vertical-align: middle;">
                    <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"/>
                    <path d="M12 8v4"/>
                    <path d="M12 16h.01"/>
                </svg>
                Shell Companies (${shellCompanies.length})
            </h3>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Country</th>
                        <th>Incorporation Date</th>
                        <th>Employees</th>
                        <th>Revenue</th>
                        <th>Industry</th>
                        <th>Risk Score</th>
                        <th>Created At</th>
                    </tr>
                </thead>
                <tbody>
        `;

        shellCompanies.forEach(company => {
            const riskColor = company.riskScore > 70 ? '#ef4444' : company.riskScore > 40 ? '#f59e0b' : '#10b981';

            html += `
                <tr class="shell-row">
                    <td>${company.id}</td>
                    <td><strong>${company.name}</strong></td>
                    <td>${company.country}</td>
                    <td>${company.incorporationDate || 'N/A'}</td>
                    <td>${company.employeeCount}</td>
                    <td>$${company.declaredRevenue.toLocaleString()}</td>
                    <td>${company.industry}</td>
                    <td><span style="color: ${riskColor}; font-weight: 600;">${company.riskScore}</span></td>
                    <td>${company.createdAt || 'N/A'}</td>
                </tr>
            `;
        });

        html += `
                </tbody>
            </table>
        `;

        document.getElementById("data-section").innerHTML = html;
        document.getElementById("data-section").style.display = "block";
    } catch (error) {
        showErrorState('Failed to load shell companies');
    }
}

// ---------- LOAD TRANSACTIONS ----------
async function loadTransactions() {
    showLoadingState();
    
    try {
        const response = await fetch('/api/transaction/all');
        const data = await response.json();

        let html = `
            <h3>
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" style="width: 24px; height: 24px; display: inline-block; vertical-align: middle;">
                    <line x1="12" y1="1" x2="12" y2="23"/>
                    <path d="M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"/>
                </svg>
                Transactions (${data.length})
            </h3>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Company ID</th>
                        <th>Amount</th>
                        <th>Type</th>
                    </tr>
                </thead>
                <tbody>
        `;

        data.forEach(tx => {
            const amountColor = tx.amount > 100000 ? '#10b981' : '#60a5fa';
            html += `
                <tr>
                    <td>${tx.id}</td>
                    <td>${tx.companyId}</td>
                    <td><span style="color: ${amountColor}; font-weight: 600;">$${tx.amount.toLocaleString()}</span></td>
                    <td>${tx.transactionType}</td>
                </tr>
            `;
        });

        html += `
                </tbody>
            </table>
        `;

        document.getElementById("data-section").innerHTML = html;
        document.getElementById("data-section").style.display = "block";
    } catch (error) {
        showErrorState('Failed to load transactions');
    }
}

// ---------- LOAD ADDRESSES ----------
async function loadAddresses() {
    showLoadingState();
    
    try {
        const response = await fetch('/api/address/all');
        const data = await response.json();

        let html = `
            <h3>
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" style="width: 24px; height: 24px; display: inline-block; vertical-align: middle;">
                    <path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z"/>
                    <circle cx="12" cy="10" r="3"/>
                </svg>
                Addresses (${data.length})
            </h3>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Street</th>
                        <th>City</th>
                        <th>Country</th>
                        <th>Postal Code</th>
                    </tr>
                </thead>
                <tbody>
        `;

        data.forEach(addr => {
            html += `
                <tr>
                    <td>${addr.id}</td>
                    <td>${addr.street}</td>
                    <td>${addr.city}</td>
                    <td><strong>${addr.country}</strong></td>
                    <td>${addr.postalCode}</td>
                </tr>
            `;
        });

        html += `
                </tbody>
            </table>
        `;

        document.getElementById("data-section").innerHTML = html;
        document.getElementById("data-section").style.display = "block";
    } catch (error) {
        showErrorState('Failed to load addresses');
    }
}

// ---------- LOAD DIRECTORS ----------
async function loadDirectors() {
    showLoadingState();
    
    try {
        const response = await fetch('/api/director/all');
        const data = await response.json();

        let html = `
            <h3>
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" style="width: 24px; height: 24px; display: inline-block; vertical-align: middle;">
                    <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
                    <circle cx="12" cy="7" r="4"/>
                </svg>
                Directors (${data.length})
            </h3>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Nationality</th>
                    </tr>
                </thead>
                <tbody>
        `;

        data.forEach(director => {
            html += `
                <tr>
                    <td>${director.id || director.Id}</td>
                    <td><strong>${director.name}</strong></td>
                    <td>${director.nationality}</td>
                </tr>
            `;
        });

        html += `
                </tbody>
            </table>
        `;

        document.getElementById("data-section").innerHTML = html;
        document.getElementById("data-section").style.display = "block";
    } catch (error) {
        showErrorState('Failed to load directors');
    }
}

// ---------- COMPANY DETAIL VIEW ----------
async function viewCompanyDetails(companyId) {
    showLoadingState();
    
    try {
        const response = await fetch('/api/companies');
        const data = await response.json();

        const company = data.find(c => c.id === companyId || c.companyId === companyId);

        if (!company) {
            showErrorState('Company not found');
            return;
        }

        const shellBadge = company.isShellTruth || company.shellTruth
            ? '<span style="background: #ef4444; color: white; padding: 0.5rem 1rem; border-radius: 0.5rem; font-size: 1rem;">Shell Company</span>'
            : '<span style="background: #10b981; color: white; padding: 0.5rem 1rem; border-radius: 0.5rem; font-size: 1rem;">Legitimate</span>';

        const riskColor = company.riskScore > 70 ? '#ef4444' : company.riskScore > 40 ? '#f59e0b' : '#10b981';

        let html = `
            <h3>
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" style="width: 24px; height: 24px; display: inline-block; vertical-align: middle;">
                    <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"/>
                </svg>
                Company Details
            </h3>
            <div style="background: #334155; border-radius: 0.75rem; padding: 2rem; margin-top: 1.5rem;">
                <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 1.5rem;">
                    <div>
                        <p style="color: #94a3b8; margin-bottom: 0.5rem;">Company Name</p>
                        <p style="color: #f1f5f9; font-size: 1.25rem; font-weight: 600;">${company.name}</p>
                    </div>
                    <div>
                        <p style="color: #94a3b8; margin-bottom: 0.5rem;">Country</p>
                        <p style="color: #f1f5f9; font-size: 1.25rem; font-weight: 600;">${company.country}</p>
                    </div>
                    <div>
                        <p style="color: #94a3b8; margin-bottom: 0.5rem;">Employees</p>
                        <p style="color: #f1f5f9; font-size: 1.25rem; font-weight: 600;">${company.employeeCount}</p>
                    </div>
                    <div>
                        <p style="color: #94a3b8; margin-bottom: 0.5rem;">Risk Score</p>
                        <p style="color: ${riskColor}; font-size: 1.5rem; font-weight: 700;">${company.riskScore}</p>
                    </div>
                    <div>
                        <p style="color: #94a3b8; margin-bottom: 0.5rem;">Status</p>
                        <p>${shellBadge}</p>
                    </div>
                    <div>
                        <p style="color: #94a3b8; margin-bottom: 0.5rem;">Revenue</p>
                        <p style="color: #10b981; font-size: 1.25rem; font-weight: 600;">$${company.declaredRevenue.toLocaleString()}</p>
                    </div>
                </div>
            </div>
        `;

        document.getElementById("data-section").innerHTML = html;
        document.getElementById("data-section").style.display = "block";
    } catch (error) {
        showErrorState('Failed to load company details');
    }
}

// ---------- HELPER FUNCTIONS ----------
function showLoadingState() {
    document.getElementById("data-section").innerHTML = `
        <div style="text-align: center; padding: 3rem;">
            <div class="loading" style="width: 50px; height: 50px; border: 4px solid rgba(59, 130, 246, 0.3); border-top-color: #3b82f6; margin: 0 auto 1rem;"></div>
            <p style="color: #94a3b8; font-size: 1.1rem;">Loading data...</p>
        </div>
    `;
    document.getElementById("data-section").style.display = "block";
}

function showErrorState(message) {
    document.getElementById("data-section").innerHTML = `
        <div style="text-align: center; padding: 2rem;">
            <div style="width: 80px; height: 80px; background: linear-gradient(135deg, #ef4444, #dc2626); border-radius: 50%; display: flex; align-items: center; justify-content: center; margin: 0 auto 1.5rem;">
                <svg viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="3" style="width: 40px; height: 40px;">
                    <line x1="18" y1="6" x2="6" y2="18"/>
                    <line x1="6" y1="6" x2="18" y2="18"/>
                </svg>
            </div>
            <h3 style="color: #ef4444; margin-bottom: 0.5rem;">Error</h3>
            <p style="color: #94a3b8;">${message}</p>
        </div>
    `;
    document.getElementById("data-section").style.display = "block";
}

// Add fade out animation
const style = document.createElement('style');
style.textContent = `
    @keyframes fadeOut {
        from {
            opacity: 1;
            transform: translateY(0);
        }
        to {
            opacity: 0;
            transform: translateY(-20px);
        }
    }
`;
document.head.appendChild(style);
