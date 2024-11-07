<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login Form</title>
        <link rel="stylesheet" href="./css/login.css">
        <link rel="stylesheet" href="./css/validate.css">

    </head>

    <body>
        <div class="backgroundImg"></div>
        <form class="form form-container"action="forgotPassword" method="POST">
            <div class="logo_container"></div>
            <div class="title_container">
                <p class="title">Reset your Password</p>
                <span class="subtitle">Confirm your account information to reset the password.</span>
            </div>
            <br>
            <!-- Username Field -->
            <div class="input_container">
                <label class="input_label" for="email_field">Email</label>
                <svg fill="none" viewBox="0 0 24 24" height="24" width="24" xmlns="http://www.w3.org/2000/svg" class="icon">
                <path stroke-linejoin="round" stroke-linecap="round" stroke-width="1.5" stroke="#141B34"
                      d="M7 8.5L9.94202 10.2394C11.6572 11.2535 12.3428 11.2535 14.058 10.2394L17 8.5"></path>
                <path stroke-linejoin="round" stroke-width="1.5" stroke="#141B34"
                      d="M2.01577 13.4756C2.08114 16.5412 2.11383 18.0739 3.24496 19.2094C4.37608 20.3448 5.95033 20.3843 9.09883 20.4634C11.0393 20.5122 12.9607 20.5122 14.9012 20.4634C18.0497 20.3843 19.6239 20.3448 20.7551 19.2094C21.8862 18.0739 21.9189 16.5412 21.9842 13.4756C22.0053 12.4899 22.0053 11.5101 21.9842 10.5244C21.9189 7.45886 21.8862 5.92609 20.7551 4.79066C19.6239 3.65523 18.0497 3.61568 14.9012 3.53657C12.9607 3.48781 11.0393 3.48781 9.09882 3.53656C5.95033 3.61566 4.37608 3.65521 3.24495 4.79065C2.11382 5.92608 2.08114 7.45885 2.01576 10.5244C1.99474 11.5101 1.99475 12.4899 2.01577 13.4756Z">
                </path>
                </svg>
                <input placeholder="name@mail.com" title="Inpit title" name="email" type="text" class="input_field"
                       id="email_field" maxlength="50" value="<%= request.getAttribute("txtEmail") != null ? request.getAttribute("txtEmail") : "" %>" required oninput="validateEmail(this)">
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


            <button class="button-submit" type="submit" name ="forgot">Confirm</button>
            <!-- Sign Up Option -->
            <p class="p">Login to another account? <span class="span"><a href="login" style="text-decoration: none; color: inherit;">Login</a></span></p>


        </p>
    </form>

    <div class="error-container"></div> <!-- Error notification container -->

    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>

    <script>
        document.addEventListener("DOMContentLoaded", function () {
            var errorContainer = document.querySelector('.error-container');
        <% if (errorMessage != null) { %>
            errorContainer.innerText = "<%= errorMessage %>";
            errorContainer.style.display = 'block'; // Hi?n th? khung thông báo
            errorContainer.classList.add('show');  // Thêm class ?? hi?n th? CSS

            // ?n thông báo sau 3 giây
            setTimeout(function () {
                errorContainer.classList.remove('show');
                errorContainer.style.display = 'none';
            }, 3000);
        <% } %>
        });
    </script>

</body>

</html>