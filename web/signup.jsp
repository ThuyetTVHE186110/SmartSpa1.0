<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Signup Form</title>
        <link rel="stylesheet" href="./css/login.css">
        <link rel="stylesheet" href="./css/validate.css">
    </head>

    <body>
        <div class="backgroundImg"></div>
        <form class="form form-container" action="signup" method="POST">
            <div class="logo_container"></div>
            <div class="title_container">
                <p class="title">Create Your Account</p>
                <span class="subtitle">Join us and enjoy all the features we offer.</span>
            </div>
            <br>
            <!-- Name Field -->
            <div class="input_container">
                <label class="input_label" for="name_field">Full Name</label>
                <svg fill="none" viewBox="0 0 24 24" height="24" width="24" xmlns="http://www.w3.org/2000/svg" class="icon">
                <path stroke-linecap="round" stroke-width="1.5" stroke="#141B34"
                      d="M12 12C14.2091 12 16 10.2091 16 8C16 5.79086 14.2091 4 12 4C9.79086 4 8 5.79086 8 8C8 10.2091 9.79086 12 12 12Z"></path>
                <path stroke-linecap="round" stroke-width="1.5" stroke="#141B34"
                      d="M4 20C4 16.6863 7.68629 14 12 14C16.3137 14 20 16.6863 20 20"></path>
                </svg>
                <input 
                    placeholder="Your Full Name" 
                    title="Input your full name" 
                    name="txtName" 
                    type="text" 
                    class="input_field" 
                    id="name_field" 
                    maxlength="50" 
                    required 
                    value="<%= request.getAttribute("txtName") != null ? request.getAttribute("txtName") : "" %>" 
                    oninput="this.value = this.value.trim()">
            </div>

            <!-- Phone Field -->
            <div class="input_container">
                <label class="input_label" for="phone_field">Phone Number</label>
                <svg fill="none" viewBox="0 0 24 24" height="24" width="24" xmlns="http://www.w3.org/2000/svg" class="icon">
                <path stroke-linecap="round" stroke-width="2" stroke="#141B34" 
                      d="M7 2h10a2 2 0 012 2v16a2 2 0 01-2 2H7a2 2 0 01-2-2V4a2 2 0 012-2z">
                </path>
                <path stroke-linecap="round" stroke-width="2" stroke="#141B34" 
                      d="M12 18h0"></path>
                <path stroke-linecap="round" stroke-width="2" stroke="#141B34" 
                      d="M9 2h6a1 1 0 011 1v0a1 1 0 01-1 1H9a1 1 0 01-1-1v0a1 1 0 011-1z">
                </path>
                </svg>


                <input 
                    placeholder="Your Phone Number" 
                    title="Input your phone number" 
                    name="txtPhone" 
                    type="tel" 
                    class="input_field" 
                    id="phone_field" 
                    maxlength="10" 
                    pattern="^0[0-9]{9}$" 
                    required 
                    value="<%= request.getAttribute("txtPhone") != null ? request.getAttribute("txtPhone") : "" %>" 
                    oninput="this.value = this.value.replace(/[^0-9]/g, ''); validatePhone(this)">
            </div>
            <span class="error-message" id="phone_error" style="display: none;">Please enter a valid phone number containing 10 digits.</span>


            <script>
                function validatePhone(input) {
                    const phoneError = document.getElementById('phone_error');
                    const phonePattern = /^0\d{9}$/; // Pattern for phone number starting with 0 and followed by 9 digits

                    if (!phonePattern.test(input.value)) {
                        input.classList.add('invalid'); // Add invalid class for styling
                        phoneError.style.display = 'block'; // Show error message
                    } else {
                        input.classList.remove('invalid'); // Remove invalid class if valid
                        phoneError.style.display = 'none'; // Hide error message
                    }
                }
            </script>


            <!-- Username (Email) Field -->
            <div class="input_container">
                <label class="input_label" for="email_field">Email</label>

                <svg fill="none" viewBox="0 0 24 24" height="24" width="24" xmlns="http://www.w3.org/2000/svg" class="icon">
                <path stroke-linejoin="round" stroke-linecap="round" stroke-width="1.5" stroke="#141B34"
                      d="M7 8.5L9.94202 10.2394C11.6572 11.2535 12.3428 11.2535 14.058 10.2394L17 8.5"></path>
                <path stroke-linejoin="round" stroke-width="1.5" stroke="#141B34"
                      d="M2.01577 13.4756C2.08114 16.5412 2.11383 18.0739 3.24496 19.2094C4.37608 20.3448 5.95033 20.3843 9.09883 20.4634C11.0393 20.5122 12.9607 20.5122 14.9012 20.4634C18.0497 20.3843 19.6239 20.3448 20.7551 19.2094C21.8862 18.0739 21.9189 16.5412 21.9842 13.4756C22.0053 12.4899 22.0053 11.5101 21.9842 10.5244C21.9189 7.45886 21.8862 5.92609 20.7551 4.79066C19.6239 3.65523 18.0497 3.61568 14.9012 3.53657C12.9607 3.48781 11.0393 3.48781 9.09882 3.53656C5.95033 3.61566 4.37608 3.65521 3.24495 4.79065C2.11382 5.92608 2.08114 7.45885 2.01576 10.5244C1.99474 11.5101 1.99475 12.4899 2.01577 13.4756Z">
                </path>
                </svg>

                <input 
                    placeholder="name@mail.com" 
                    title="Input your email" 
                    name="txtEmail" 
                    type="email" 
                    class="input_field" 
                    id="email_field" 
                    maxlength="50" 
                    value="<%= request.getAttribute("txtEmail") != null ? request.getAttribute("txtEmail") : "" %>" 
                    required 
                    oninput="validateEmail(this)">


            </div>
            <span class="error-message" id="email_error" style="display: none;">Please enter a valid email address.</span>

            <script>
                function validateEmail(input) {
                    const emailError = document.getElementById('email_error');
                    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // Simple regex for email validation

                    if (!emailPattern.test(input.value)) {
                        input.classList.add('invalid'); // Add invalid class for styling
                        emailError.style.display = 'block'; // Show error message
                    } else {
                        input.classList.remove('invalid'); // Remove invalid class if valid
                        emailError.style.display = 'none'; // Hide error message
                    }
                }
            </script>


            <!-- Password Field -->
            <div class="input_container">
                <label class="input_label" for="password_field">Password</label>
                <svg fill="none" viewBox="0 0 24 24" height="24" width="24" xmlns="http://www.w3.org/2000/svg" class="icon">
                <path stroke-linecap="round" stroke-width="1.5" stroke="#141B34"
                      d="M18 11.0041C17.4166 9.91704 16.273 9.15775 14.9519 9.0993C13.477 9.03404 11.9788 9 10.329 9C8.67911 9 7.18091 9.03404 5.70604 9.0993C3.95328 9.17685 2.51295 10.4881 2.27882 12.1618C2.12602 13.2541 2 14.3734 2 15.5134C2 16.6534 2.12602 17.7727 2.27882 18.865C2.51295 20.5387 3.95328 21.8499 5.70604 21.9275C6.42013 21.9591 7.26041 21.9834 8 22">
                </path>
                <path stroke-linejoin="round" stroke-linecap="round" stroke-width="1.5" stroke="#141B34"
                      d="M6 9V6.5C6 4.01472 8.01472 2 10.5 2C12.9853 2 15 4.01472 15 6.5V9"></path>
                <path fill="#141B34"
                      d="M21.2046 15.1045L20.6242 15.6956V15.6956L21.2046 15.1045ZM21.4196 16.4767C21.7461 16.7972 22.2706 16.7924 22.5911 16.466C22.9116 16.1395 22.9068 15.615 22.5804 15.2945L21.4196 16.4767ZM18.0228 15.1045L17.4424 14.5134V14.5134L18.0228 15.1045ZM18.2379 18.0387C18.5643 18.3593 19.0888 18.3545 19.4094 18.028C19.7299 17.7016 19.7251 17.1771 19.3987 16.8565L18.2379 18.0387ZM14.2603 20.7619C13.7039 21.3082 12.7957 21.3082 12.2394 20.7619L11.0786 21.9441C12.2794 23.1232 14.2202 23.1232 15.4211 21.9441L14.2603 20.7619ZM12.2394 20.7619C11.6914 20.2239 11.6914 19.358 12.2394 18.82L11.0786 17.6378C9.86927 18.8252 9.86927 20.7567 11.0786 21.9441L12.2394 20.7619ZM12.2394 18.82C12.7957 18.2737 13.7039 18.2737 14.2603 18.82L15.4211 17.6378C14.2202 16.4587 12.2794 16.4587 11.0786 17.6378L12.2394 18.82ZM14.2603 18.82C14.8082 19.358 14.8082 20.2239 14.2603 20.7619L15.4211 21.9441C16.6304 20.7567 16.6304 18.8252 15.4211 17.6378L14.2603 18.82ZM20.6242 15.6956L21.4196 16.4767L22.5804 15.2945L21.785 14.5134L20.6242 15.6956ZM15.4211 18.82L17.8078 16.4767L16.647 15.2944L14.2603 17.6377L15.4211 18.82ZM17.8078 16.4767L18.6032 15.6956L17.4424 14.5134L16.647 15.2945L17.8078 16.4767ZM16.647 16.4767L18.2379 18.0387L19.3987 16.8565L17.8078 15.2945L16.647 16.4767ZM21.785 14.5134C21.4266 14.1616 21.0998 13.8383 20.7993 13.6131C20.4791 13.3732 20.096 13.1716 19.6137 13.1716V14.8284C19.6145 14.8284 19.619 14.8273 19.6395 14.8357C19.6663 14.8466 19.7183 14.8735 19.806 14.9391C19.9969 15.0822 20.2326 15.3112 20.6242 15.6956L21.785 14.5134ZM18.6032 15.6956C18.9948 15.3112 19.2305 15.0822 19.4214 14.9391C19.5091 14.8735 19.5611 14.8466 19.5879 14.8357C19.6084 14.8273 19.6129 14.8284 19.6137 14.8284V13.1716C19.1315 13.1716 18.7483 13.3732 18.4281 13.6131C18.1276 13.8383 17.8008 14.1616 17.4424 14.5134L18.6032 15.6956Z">
                </path>
                </svg>
                <input placeholder="Password" title="Input your password" name="txtPassword" type="password" class="input_field"
                       id="password_field" minlength="8" maxlength="20" required oninput="this.value = this.value.trim()" >
                <button type="button" class="show_password" onclick="togglePassword('password_field')">
                    <svg fill="none" viewBox="0 0 24 24" height="24" width="24" xmlns="http://www.w3.org/2000/svg" class="icon">
                    <path stroke-linecap="round" stroke-width="2" stroke="#141B34"
                          d="M12 4.5C6.5 4.5 2 12 2 12s4.5 7.5 10 7.5 10-7.5 10-7.5-4.5-7.5-10-7.5zM12 17.5c-2.76 0-5-2.24-5-5s2.24-5 5-5 5 2.24 5 5-2.24 5-5 5zM12 9c-1.39 0-2.5 1.11-2.5 2.5S10.61 14 12 14s2.5-1.11 2.5-2.5S13.39 9 12 9z">
                    </path>
                    </svg>
                </button>
            </div>
            <span id="password_error" style="color: red; display: none;"></span>

            <!-- Confirm Password Field -->
            <div class="input_container">
                <label class="input_label" for="confirm_password_field">Confirm Password</label>
                <!-- SVG Icon -->
                <svg fill="none" viewBox="0 0 24 24" height="24" width="24" xmlns="http://www.w3.org/2000/svg" class="icon">
                <path stroke-linecap="round" stroke-width="1.5" stroke="#141B34"
                      d="M18 11.0041C17.4166 9.91704 16.273 9.15775 14.9519 9.0993C13.477 9.03404 11.9788 9 10.329 9C8.67911 9 7.18091 9.03404 5.70604 9.0993C3.95328 9.17685 2.51295 10.4881 2.27882 12.1618C2.12602 13.2541 2 14.3734 2 15.5134C2 16.6534 2.12602 17.7727 2.27882 18.865C2.51295 20.5387 3.95328 21.8499 5.70604 21.9275C6.42013 21.9591 7.26041 21.9834 8 22">
                </path>
                <path stroke-linejoin="round" stroke-linecap="round" stroke-width="1.5" stroke="#141B34"
                      d="M6 9V6.5C6 4.01472 8.01472 2 10.5 2C12.9853 2 15 4.01472 15 6.5V9"></path>
                <path fill="#141B34"
                      d="M21.2046 15.1045L20.6242 15.6956V15.6956L21.2046 15.1045ZM21.4196 16.4767C21.7461 16.7972 22.2706 16.7924 22.5911 16.466C22.9116 16.1395 22.9068 15.615 22.5804 15.2945L21.4196 16.4767ZM18.0228 15.1045L17.4424 14.5134V14.5134L18.0228 15.1045ZM18.2379 18.0387C18.5643 18.3593 19.0888 18.3545 19.4094 18.028C19.7299 17.7016 19.7251 17.1771 19.3987 16.8565L18.2379 18.0387ZM14.2603 20.7619C13.7039 21.3082 12.7957 21.3082 12.2394 20.7619L11.0786 21.9441C12.2794 23.1232 14.2202 23.1232 15.4211 21.9441L14.2603 20.7619ZM12.2394 20.7619C11.6914 20.2239 11.6914 19.358 12.2394 18.82L11.0786 17.6378C9.86927 18.8252 9.86927 20.7567 11.0786 21.9441L12.2394 20.7619ZM12.2394 18.82C12.7957 18.2737 13.7039 18.2737 14.2603 18.82L15.4211 17.6378C14.2202 16.4587 12.2794 16.4587 11.0786 17.6378L12.2394 18.82ZM14.2603 18.82C14.8082 19.358 14.8082 20.2239 14.2603 20.7619L15.4211 21.9441C16.6304 20.7567 16.6304 18.8252 15.4211 17.6378L14.2603 18.82ZM20.6242 15.6956L21.4196 16.4767L22.5804 15.2945L21.785 14.5134L20.6242 15.6956ZM15.4211 18.82L17.8078 16.4767L16.647 15.2944L14.2603 17.6377L15.4211 18.82ZM17.8078 16.4767L18.6032 15.6956L17.4424 14.5134L16.647 15.2945L17.8078 16.4767ZM16.647 16.4767L18.2379 18.0387L19.3987 16.8565L17.8078 15.2945L16.647 16.4767ZM21.785 14.5134C21.4266 14.1616 21.0998 13.8383 20.7993 13.6131C20.4791 13.3732 20.096 13.1716 19.6137 13.1716V14.8284C19.6145 14.8284 19.619 14.8273 19.6395 14.8357C19.6663 14.8466 19.7183 14.8735 19.806 14.9391C19.9969 15.0822 20.2326 15.3112 20.6242 15.6956L21.785 14.5134ZM18.6032 15.6956C18.9948 15.3112 19.2305 15.0822 19.4215 14.9391C19.5091 14.8735 19.5611 14.8466 19.5879 14.8357C19.6084 14.8273 19.6129 14.8284 19.6137 14.8284V13.1716C19.1314 13.1716 18.7483 13.3732 18.4281 13.6131C18.1276 13.8383 17.8008 14.1616 17.4424 14.5134L18.6032 15.6956Z">
                </path>
                </svg>
                <!-- Confirm Password Input -->
                <input placeholder="Confirm Password" title="Input title" name="txtConfirmPassword" type="password"
                       class="input_field" id="confirm_password_field" minlength="8" maxlength="20" required oninput="this.value = this.value.trim()">
                <button type="button" class="show_password" onclick="togglePassword('confirm_password_field')">
                    <svg fill="none" viewBox="0 0 24 24" height="24" width="24" xmlns="http://www.w3.org/2000/svg" class="icon">
                    <path stroke-linecap="round" stroke-width="2" stroke="#141B34"
                          d="M12 4.5C6.5 4.5 2 12 2 12s4.5 7.5 10 7.5 10-7.5 10-7.5-4.5-7.5-10-7.5zM12 17.5c-2.76 0-5-2.24-5-5s2.24-5 5-5 5 2.24 5 5-2.24 5-5 5zM12 9c-1.39 0-2.5 1.11-2.5 2.5S10.61 14 12 14s2.5-1.11 2.5-2.5S13.39 9 12 9z">
                    </path>
                    </svg>
                </button>
            </div>
            <span id="confirm_password_error" style="color: red; display: none;"></span>
            <!-- JavaScript Validation -->
            <script>
                function togglePassword(fieldId) {
                    const passwordField = document.getElementById(fieldId);
                    const button = event.target;

                    if (passwordField.type === 'password') {
                        passwordField.type = 'text';
                        button.querySelector('path').setAttribute('d', 'M12 4.5C6.5 4.5 2 12 2 12s4.5 7.5 10 7.5 10-7.5 10-7.5-4.5-7.5-10-7.5zM12 17.5c-2.76 0-5-2.24-5-5s2.24-5 5-5 5 2.24 5 5-2.24 5-5 5zM12 9c1.39 0 2.5 1.11 2.5 2.5S13.39 14 12 14s-2.5-1.11-2.5-2.5S10.61 9 12 9z');
                    } else {
                        passwordField.type = 'password';
                        button.querySelector('path').setAttribute('d', 'M12 4.5C6.5 4.5 2 12 2 12s4.5 7.5 10 7.5 10-7.5 10-7.5-4.5-7.5-10-7.5zM12 17.5c-2.76 0-5-2.24-5-5s2.24-5 5-5 5 2.24 5 5-2.24 5-5 5zM12 9c-1.39 0-2.5 1.11-2.5 2.5S10.61 14 12 14s2.5-1.11 2.5-2.5S13.39 9 12 9z');
                    }
                }

                function validateForm() {
                    const passwordField = document.getElementById('password_field');
                    const confirmPasswordField = document.getElementById('confirm_password_field');
                    const password = passwordField.value;

                    // Ki?m tra ??nh d?ng m?t kh?u
                    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d@$!%*?&]{8,}$/; // ít nh?t 8 ký t?, có ch? hoa, ch? th??ng và s?

                    if (!passwordRegex.test(password)) {
                        alert("Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one number.");
                        return false; // Ng?n ch?n g?i bi?u m?u
                    }

                    // Ki?m tra xem m?t kh?u và xác nh?n m?t kh?u có kh?p không
                    if (password !== confirmPasswordField.value) {
                        alert("Passwords do not match.");
                        return false; // Ng?n ch?n g?i bi?u m?u
                    }

                    return true; // G?i bi?u m?u
                }
            </script>
            <script>
                function validatePassword() {
                    const passwordField = document.getElementById('password_field');
                    const confirmPasswordField = document.getElementById('confirm_password_field');
                    const passwordError = document.getElementById('password_error');
                    const confirmPasswordError = document.getElementById('confirm_password_error');

                    const password = passwordField.value;
                    const confirmPassword = confirmPasswordField.value;

                    // Reset error messages
                    passwordError.style.display = 'none';
                    confirmPasswordError.style.display = 'none';

                    // Password validation
                    const passwordCriteria = /^(?=.*[0-9])(?=.*[!@#$%^&*])[A-Z].{4,19}$/; // First letter uppercase, at least 5 characters, one digit, one special character

                    if (!password.match(passwordCriteria)) {
                        passwordError.innerText = "Password must start with an uppercase letter, be at least 5 characters long, contain at least one digit and one special character.";
                        passwordError.style.display = 'block';
                    }

                    // Confirm password validation
                    if (password !== confirmPassword) {
                        confirmPasswordError.innerText = "Passwords do not match.";
                        confirmPasswordError.style.display = 'block';
                    }
                }

                // Add event listeners
                document.getElementById('password_field').addEventListener('input', validatePassword);
                document.getElementById('confirm_password_field').addEventListener('input', validatePassword);
            </script>


            <!-- Submit Button -->
            <button class="button-submit" type="submit" value="signup" name = "signup">Sign Up</button>

            <!-- Login Option -->
            <p class="p">Already have an account? <span class="span"><a href="login.jsp" style="text-decoration: none; color: inherit;">Log In</a></span></p>


            <!-- Separator -->
            <div class="separator">
                <hr class="line">
                <span>Or</span>
                <hr class="line">
            </div>
            <!-- Google and Facebook Signup Options -->
            <div class="flex-row">
                <button class="btn google">
                    <svg version="1.1" width="20" id="Layer_1" xmlns="http://www.w3.org/2000/svg"
                         xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px" viewBox="0 0 512 512"
                         style="enable-background:new 0 0 512 512;" xml:space="preserve">
                    <path style="fill:#FBBB00;"
                          d="M113.47,309.408L95.648,375.94l-65.139,1.378C11.042,341.211,0,299.9,0,256
                          c0-42.451,10.324-82.483,28.624-117.732h0.014l57.992,10.632l25.404,57.644c-5.317,15.501-8.215,32.141-8.215,49.456
                          C103.821,274.792,107.225,292.797,113.47,309.408z"></path>
                    <path style="fill:#518EF8;"
                          d="M507.527,208.176C510.467,223.662,512,239.655,512,256c0,18.328-1.927,36.206-5.598,53.451
                          c-12.462,58.683-45.025,109.925-90.134,146.187l-0.014-0.014l-73.044-3.727l-10.338-64.535
                          c29.932-17.554,53.324-45.025,65.646-77.911h-136.89V208.176h138.887L507.527,208.176L507.527,208.176z"></path>
                    <path style="fill:#28B446;"
                          d="M416.253,455.624l0.014,0.014C372.396,490.901,316.666,512,256,512
                          c-97.491,0-182.252-54.491-225.491-134.681l82.961-67.91c21.619,57.698,77.278,98.771,142.53,98.771
                          c28.047,0,54.323-7.582,76.87-20.818L416.253,455.624z"></path>
                    <path style="fill:#F14336;"
                          d="M419.404,58.936l-82.933,67.896c-23.335-14.586-50.919-23.012-80.471-23.012
                          c-66.729,0-123.429,42.957-143.965,102.724l-83.397-68.276h-0.014C71.23,56.123,157.06,0,256,0
                          C318.115,0,375.068,22.126,419.404,58.936z"></path>
                    </svg>
                    Google
                </button>
                <button class="btn facebook">
                    <svg viewBox="0 0 16 16" class="bi bi-facebook" fill="currentColor" height="16" width="16"
                         xmlns="http://www.w3.org/2000/svg">
                    <path
                        d="M16 8.049c0-4.446-3.582-8.05-8-8.05C3.58 0-.002 3.603-.002 8.05c0 4.017 2.926 7.347 6.75 7.951v-5.625h-2.03V8.05H6.75V6.275c0-2.017 1.195-3.131 3.022-3.131.876 0 1.791.157 1.791.157v1.98h-1.009c-.993 0-1.303.621-1.303 1.258v1.51h2.218l-.354 2.326H9.25V16c3.824-.604 6.75-3.934 6.75-7.951z">
                    </path>
                    </svg>
                    <span>Facebook</span>
                </button>
            </div>
        </form>
        <div class="error-container" style="display:none;"></div> <!-- Error notification container -->

        <% String errorMessage = (String) request.getAttribute("error"); %>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                var errorContainer = document.querySelector('.error-container');

            <% if (errorMessage != null) { %>
                errorContainer.innerText = "<%= errorMessage %>";
                errorContainer.style.display = 'block'; // Show the container
                errorContainer.classList.add('show'); // Add the 'show' class for CSS transition

                // Use setTimeout to remove the notification after 3 seconds
                setTimeout(function () {
                    errorContainer.classList.remove('show'); // Hide the notification
                    errorContainer.style.display = 'none'; // Optionally set display back to none
                }, 3000);
            <% } %>
            });
        </script>

    </body>

</html>
