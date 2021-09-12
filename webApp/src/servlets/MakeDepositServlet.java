package servlets;

import constants.Constants;
import exception.NonTraderUserException;
import exception.UserDidntExistException;
import exception.UserNameAlreadyExistException;
import exception.UserTypeDidntExistException;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "makeDeposit", urlPatterns = {"/pages/main/makeDeposit"})
//@WebServlet(name = "login", urlPatterns = {"/signUp"})
public class MakeDepositServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Engine.Engine engine = ServletUtils.getEngine(getServletContext());

        String userNameFromSession = SessionUtils.getUsername(request);
        String amountToDepositFromParameter = request.getParameter(Constants.AMOUNT_TO_DEPOSIT);

        if (userNameFromSession == null || userNameFromSession.isEmpty()) {
        } else {

            userNameFromSession = userNameFromSession.trim();

            try {
                engine.deposit(userNameFromSession, Integer.valueOf(amountToDepositFromParameter));
                response.getOutputStream().println("Deposit made successfully");
            } catch (UserDidntExistException | NonTraderUserException e) {
                response.getOutputStream().println(e.getMessage());
            }
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
