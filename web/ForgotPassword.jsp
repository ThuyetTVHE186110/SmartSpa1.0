<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login Form</title>
        <link rel="stylesheet" href="./css/login.css">
    </head>

    <body>
        <div class="backgroundImg"></div>
        <form class="form form-container"action="PasswordForgotServlet" method="POST">
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
                       id="email_field" maxlength="50" required oninput="this.value = this.value.trim()">
            </div>
            

            <button class="button-submit" type="submit" name ="forgot">Confirm</button>
            <!-- Sign Up Option -->
            <p class="p">Login to another account? <span class="span"><a href="login.jsp" style="text-decoration: none; color: inherit;">Login</a></span></p>


        </p>

        <!-- Separator -->
        <div class="separator">
            <hr class="line">
            <span>Or</span>
            <hr class="line">
        </div>
        <!-- Google and Facebook Login Options -->
        <div class="flex-row">
            <button class="btn google">
                <svg version="1.1" width="20" id="Layer_1" xmlns="http://www.w3.org/2000/svg"
                     xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px" viewBox="0 0 512 512"
                     style="enable-background:new 0 0 512 512;" xml:space="preserve">
                <path style="fill:#FBBB00;" d="M113.47,309.408L95.648,375.94l-65.139,1.378C11.042,341.211,0,299.9,0,256
                      c0-42.451,10.324-82.483,28.624-117.732h0.014l57.992,10.632l25.404,57.644c-5.317,15.501-8.215,32.141-8.215,49.456
                      C103.821,274.792,107.225,292.797,113.47,309.408z"></path>
                <path style="fill:#518EF8;" d="M507.527,208.176C510.467,223.662,512,239.655,512,256c0,18.328-1.927,36.206-5.598,53.451
                      c-12.462,58.683-45.025,109.925-90.134,146.187l-0.014-0.014l-73.044-3.727l-10.338-64.535
                      c29.932-17.554,53.324-45.025,65.646-77.911h-136.89V208.176h138.887L507.527,208.176L507.527,208.176z"></path>
                <path style="fill:#28B446;" d="M416.253,455.624l0.014,0.014C372.396,490.901,316.666,512,256,512
                      c-97.491,0-182.252-54.491-225.491-134.681l82.961-67.91c21.619,57.698,77.278,98.771,142.53,98.771
                      c28.047,0,54.323-7.582,76.87-20.818L416.253,455.624z"></path>
                <path style="fill:#F14336;" d="M419.404,58.936l-82.933,67.896c-23.335-14.586-50.919-23.012-80.471-23.012
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
    
</body>

</html>