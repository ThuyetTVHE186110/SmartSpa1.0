<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@page contentType="text/html" pageEncoding="UTF-8" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>Beauty Consultation Survey - Smart Beauty Spa</title>
            <link rel="stylesheet" href="newUI/assets/css/styles.css">
            <link rel="stylesheet" href="newUI/assets/css/survey-styles.css">
        </head>

        <body>
            <jsp:include page="NavBarJSP/NavBarJSP.jsp" />

            <div class="survey-container">
                <div class="survey-header">
                    <h2>Personalized Beauty Consultation</h2>
                    <p>Help us understand your beauty needs better</p>

                    <!-- Add progress bar -->
                    <div class="progress-bar">
                        <div class="progress-fill"></div>
                    </div>
                </div>

                <!-- Display any error messages -->
                <c:if test="${not empty errorMessage}">
                    <div class="error-message">
                        ${errorMessage}
                    </div>
                </c:if>

                <!-- Display recommendations if available -->
                <c:if test="${not empty recommendations}">
                    <div class="recommendations-section">
                        <h3>Your Personalized Recommendations</h3>
                        <ul class="recommendations-list">
                            <c:forEach items="${recommendations}" var="service">
                                <li>${service}</li>
                            </c:forEach>
                        </ul>
                    </div>
                </c:if>

                <form id="beautySurvey" method="POST" action="SaveSurvey">
                    <div class="survey-steps">
                        <!-- Skin Type Section -->
                        <div class="survey-section active">
                            <h3>What's your skin type?</h3>
                            <div class="option-group">
                                <div class="option-card" onclick="selectOption(this)">
                                    <h4>Normal</h4>
                                    <p>Well-balanced, not too oily or dry</p>
                                    <input type="radio" name="skinType" value="normal" required>
                                </div>
                                <div class="option-card" onclick="selectOption(this)">
                                    <h4>Dry</h4>
                                    <p>Feels tight and may have flaky patches</p>
                                    <input type="radio" name="skinType" value="dry">
                                </div>
                                <div class="option-card" onclick="selectOption(this)">
                                    <h4>Oily</h4>
                                    <p>Shiny and prone to breakouts</p>
                                    <input type="radio" name="skinType" value="oily">
                                </div>
                                <div class="option-card" onclick="selectOption(this)">
                                    <h4>Combination</h4>
                                    <p>Oily T-zone, normal/dry elsewhere</p>
                                    <input type="radio" name="skinType" value="combination">
                                </div>
                            </div>
                            <div class="section-nav">
                                <button type="button" class="next-btn" onclick="nextSection()">Next</button>
                            </div>
                        </div>

                        <!-- Skin Concerns Section -->
                        <div class="survey-section">
                            <h3>What are your skin concerns? (Select all that apply)</h3>
                            <div class="option-group">
                                <div class="option-card" onclick="toggleOption(this)">
                                    <h4>Acne</h4>
                                    <p>Breakouts and blemishes</p>
                                    <input type="checkbox" name="skinConcerns" value="acne">
                                </div>
                                <div class="option-card" onclick="toggleOption(this)">
                                    <h4>Anti-aging</h4>
                                    <p>Fine lines and wrinkles</p>
                                    <input type="checkbox" name="skinConcerns" value="aging">
                                </div>
                                <div class="option-card" onclick="toggleOption(this)">
                                    <h4>Pigmentation</h4>
                                    <p>Dark spots and uneven tone</p>
                                    <input type="checkbox" name="skinConcerns" value="pigmentation">
                                </div>
                                <div class="option-card" onclick="toggleOption(this)">
                                    <h4>Sensitivity</h4>
                                    <p>Redness and irritation</p>
                                    <input type="checkbox" name="skinConcerns" value="sensitivity">
                                </div>
                            </div>
                            <div class="section-nav">
                                <button type="button" class="prev-btn" onclick="prevSection()">Previous</button>
                                <button type="button" class="next-btn" onclick="nextSection()">Next</button>
                            </div>
                        </div>

                        <!-- Beauty Goals Section -->
                        <div class="survey-section">
                            <h3>Beauty Goals</h3>
                            <div class="option-group">
                                <div class="option-card" onclick="toggleOption(this)">
                                    <h4>Natural Enhancement</h4>
                                    <p>Enhance your natural beauty</p>
                                    <input type="checkbox" name="beautyGoals" value="natural">
                                </div>
                                <div class="option-card" onclick="toggleOption(this)">
                                    <h4>Anti-aging</h4>
                                    <p>Reduce signs of aging</p>
                                    <input type="checkbox" name="beautyGoals" value="anti-aging">
                                </div>
                                <div class="option-card" onclick="toggleOption(this)">
                                    <h4>Skin Health</h4>
                                    <p>Improve overall skin health</p>
                                    <input type="checkbox" name="beautyGoals" value="skin-health">
                                </div>
                                <div class="option-card" onclick="toggleOption(this)">
                                    <h4>Problem Solution</h4>
                                    <p>Address specific skin issues</p>
                                    <input type="checkbox" name="beautyGoals" value="problem-solution">
                                </div>
                            </div>
                            <div class="section-nav">
                                <button type="button" class="prev-btn" onclick="prevSection()">Previous</button>
                                <button type="button" class="next-btn" onclick="nextSection()">Next</button>
                            </div>
                        </div>

                        <!-- Personal Information Section -->
                        <div class="survey-section">
                            <h3>Additional Information</h3>
                            <div class="form-group">
                                <label for="age">Age</label>
                                <input type="number" id="age" name="age" min="1" max="120" class="form-control"
                                    tabindex="0" aria-label="Age" placeholder="Enter your age">
                            </div>

                            <div class="form-group">
                                <label for="lifestyle">Lifestyle</label>
                                <select id="lifestyle" name="lifestyle" class="form-control" tabindex="0"
                                    aria-label="Select your lifestyle">
                                    <option value="">Select your lifestyle</option>
                                    <option value="active">Active/Sports</option>
                                    <option value="office">Office Worker</option>
                                    <option value="student">Student</option>
                                    <option value="homemaker">Homemaker</option>
                                    <option value="outdoor">Outdoor Worker</option>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="allergies">Any Allergies or Sensitivities?</label>
                                <textarea id="allergies" name="allergies" class="form-control" tabindex="0"
                                    aria-label="Allergies and sensitivities"
                                    placeholder="Please list any allergies or sensitivities..."></textarea>
                            </div>

                            <div class="section-nav">
                                <button type="button" class="prev-btn" onclick="prevSection()">Previous</button>
                                <button type="button" class="next-btn" onclick="nextSection()">Next</button>
                            </div>
                        </div>

                        <!-- Budget Range Section -->
                        <div class="survey-section">
                            <h3>What's your preferred budget range for treatments?</h3>
                            <div class="option-group">
                                <div class="option-card" onclick="selectOption(this)">
                                    <h4>Budget-Friendly</h4>
                                    <p>$40-60$ per treatment</p>
                                    <input type="radio" name="budgetRange" value="budget" required>
                                </div>
                                <div class="option-card" onclick="selectOption(this)">
                                    <h4>Moderate</h4>
                                    <p>$60-$150 per treatment</p>
                                    <input type="radio" name="budgetRange" value="moderate">
                                </div>
                                <div class="option-card" onclick="selectOption(this)">
                                    <h4>Premium</h4>
                                    <p>$150+ per treatment</p>
                                    <input type="radio" name="budgetRange" value="premium">
                                </div>
                            </div>
                            <div class="section-nav">
                                <button type="button" class="prev-btn" onclick="prevSection()">Previous</button>
                                <button type="submit" class="submit-survey">Get My Recommendations</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>

            <script>
                let currentSection = 0;
                const sections = document.querySelectorAll('.survey-section');
                const progressFill = document.querySelector('.progress-fill');

                // Hide all sections except first one
                sections.forEach((section, index) => {
                    if (index !== 0) {
                        section.style.display = 'none';
                    }
                });

                function updateProgress() {
                    const progress = ((currentSection + 1) / sections.length) * 100;
                    progressFill.style.width = `${progress}%`;
                }

                function showSection(index) {
                    sections.forEach((section, i) => {
                        section.style.display = i === index ? 'block' : 'none';
                        if (i === index) {
                            section.style.animation = 'fadeInUp 0.5s ease forwards';
                        }
                    });
                    updateProgress();
                }

                function nextSection() {
                    if (validateSection(currentSection) && currentSection < sections.length - 1) {
                        currentSection++;
                        showSection(currentSection);
                    }
                }

                function prevSection() {
                    if (currentSection > 0) {
                        currentSection--;
                        showSection(currentSection);
                    }
                }

                function selectOption(element) {
                    const group = element.parentElement;
                    group.querySelectorAll('.option-card').forEach(card => {
                        card.classList.remove('selected');
                    });
                    element.classList.add('selected');
                    const input = element.querySelector('input');
                    input.checked = true;
                }

                function toggleOption(element) {
                    element.classList.toggle('selected');
                    const input = element.querySelector('input');
                    input.checked = !input.checked;
                }

                function validateSection(index) {
                    const section = sections[index];

                    // For radio sections (skin type and budget)
                    if (section.querySelector('input[type="radio"]')) {
                        const checkedRadio = section.querySelector('input[type="radio"]:checked');
                        if (!checkedRadio) {
                            showError('Please select one option');
                            return false;
                        }
                    }

                    // For checkbox sections (skin concerns and beauty goals)
                    if (section.querySelector('input[type="checkbox"]')) {
                        const checkedBoxes = section.querySelectorAll('input[type="checkbox"]:checked');
                        if (checkedBoxes.length === 0) {
                            showError('Please select at least one option');
                            return false;
                        }
                    }

                    // For the personal information section
                    if (section.querySelector('.form-control')) {
                        const ageInput = section.querySelector('input[type="number"]');
                        if (ageInput && ageInput.value) {
                            const age = parseInt(ageInput.value);
                            if (age < 1 || age > 120) {
                                showError('Please enter a valid age between 1 and 120');
                                return false;
                            }
                        }
                    }

                    return true;
                }

                function showError(message) {
                    const errorDiv = document.createElement('div');
                    errorDiv.className = 'error-message';
                    errorDiv.textContent = message;

                    const section = sections[currentSection];
                    section.insertBefore(errorDiv, section.firstChild);

                    setTimeout(() => {
                        errorDiv.remove();
                    }, 3000);
                }

                // Initialize progress bar
                updateProgress();
            </script>
        </body>

        </html>