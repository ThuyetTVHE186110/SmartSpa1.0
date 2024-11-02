package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dal.PaymentDAO;
import model.Payment;
import model.Person;

@WebServlet("/payment-history")
public class PaymentHistoryServlet extends HttpServlet {
    private final PaymentDAO paymentDAO = new PaymentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Person person = (Person) session.getAttribute("person");
        
        if (person == null) {
            response.sendRedirect("login");
            return;
        }

        List<Payment> payments = paymentDAO.getPaymentsByPersonId(person.getId());
        request.setAttribute("payments", payments);
        request.getRequestDispatcher("payment-history.jsp").forward(request, response);
    }
} 