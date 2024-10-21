/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.util.stream.Collectors;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.util.Collections;
import java.security.GeneralSecurityException;
import com.google.gson.JsonObject;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

/**
 *
 * @author PC
 */
public class GoogleSignInServlet extends HttpServlet {

    private static final String CLIENT_ID = "608049009163-67or5j0sd3q1g1bnf3qg6qdm2t821gaj.apps.googleusercontent.com"; // Replace with your client ID

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Read the ID token from the request
            String jsonRequest = request.getReader().lines().collect(Collectors.joining());
            JsonObject jsonObject = new Gson().fromJson(jsonRequest, JsonObject.class);
            String idTokenString = jsonObject.get("credential").getAsString();

            // Verify the token with Google
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                // Retrieve user info from the token payload
                String userId = payload.getSubject();
                String email = payload.getEmail();
                boolean emailVerified = payload.getEmailVerified();
                String name = (String) payload.get("name");

                // Handle user login or account creation here, depending on your logic
                request.getSession().setAttribute("user", email);

                // Respond with success
                JsonObject jsonResponse = new JsonObject();
                jsonResponse.addProperty("status", "success");
                jsonResponse.addProperty("email", email);

                response.setContentType("application/json");
                response.getWriter().write(new Gson().toJson(jsonResponse));
            } else {
                // Invalid token
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid ID token.");
            }
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("An error occurred during token verification.");
        }
    }
}
