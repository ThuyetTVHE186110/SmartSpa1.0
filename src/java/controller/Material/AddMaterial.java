/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.Material;

import dal.MaterialDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import model.Supplier;

/**
 *
 * @author hotdo
 */
@MultipartConfig
@WebServlet(name="AddMaterial", urlPatterns={"/addmaterial"})
public class AddMaterial extends HttpServlet {
   
  @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       MaterialDAO materialDAO = new MaterialDAO();
       List<Supplier> suppliers = materialDAO.getAllSuppliers();
        request.setAttribute("suppliers", suppliers);
        request.getRequestDispatcher("/materialmanagement").forward(request, response);
}


    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String name = request.getParameter("name");
    String description = request.getParameter("description");
    String priceParam = request.getParameter("price");
    String supplierIdParam = request.getParameter("supplierId");

    // Validate price and supplier ID
    if (priceParam == null || priceParam.isEmpty()) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Price is missing");
        return;
    }
    if (supplierIdParam == null || supplierIdParam.isEmpty()) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Supplier ID is missing");
        return;
    }

    int price = Integer.parseInt(priceParam);
    int supplierId = Integer.parseInt(supplierIdParam);
    String status = request.getParameter("status");

    // Handle file upload
    Part imagePart = request.getPart("file");
    if (imagePart == null || imagePart.getSize() == 0) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Image part is missing or empty");
        return;
    }

    // Define upload path
    String uploadPath = getServletContext().getRealPath("img");
    File uploadDir = new File(uploadPath);
    if (!uploadDir.exists()) {
        uploadDir.mkdirs(); // Create the directory if it doesn't exist
    }

    // Save the image to the specified path
    String fileName = imagePart.getSubmittedFileName();
    Path filePath = Paths.get(uploadPath, fileName);
    Files.copy(imagePart.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
    
    // Store the relative path for the image in the database
    String image = "img/" + fileName;

    // Add the new material
    MaterialDAO materialDAO = new MaterialDAO();
    materialDAO.addMaterial(name, description, price, image, supplierId, status);

    // Redirect to the material management page
    response.sendRedirect("materialmanagement");
}
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

