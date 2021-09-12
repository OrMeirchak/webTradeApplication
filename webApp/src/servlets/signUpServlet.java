package servlets;

import constants.Constants;
import exception.UserNameAlreadyExistException;
import exception.UserTypeDidntExistException;
import utils.ServletUtils;
import utils.SessionUtils;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "signUp", urlPatterns = {"/pages/signUp/signUp"})
//@WebServlet(name = "login", urlPatterns = {"/signUp"})
public class signUpServlet extends HttpServlet {

    private final String MAIN_URL = "../main/main.html";
    private final String SIGN_UP_URL = "../signUp/signUp.html";
    private final String SIGN_UP_USER_EXIST_ERROR_URL = "/pages/loginError/login_attempt_after_error.jsp";  // must start with '/' since will be used in request dispatcher...
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Engine.Engine engine = ServletUtils.getEngine(getServletContext());

            String usernameFromParameter = request.getParameter(Constants.USERNAME);
            String usertypeFromParameter = request.getParameter(Constants.USERTYPE);

            if (usernameFromParameter == null || usernameFromParameter.isEmpty()) {
                response.getOutputStream().println(SIGN_UP_URL);
            } else {

                usernameFromParameter = usernameFromParameter.trim();

                try {
                        engine.addUser(usernameFromParameter,usertypeFromParameter);
                        request.getSession(true).setAttribute(Constants.USERNAME, usernameFromParameter);
                        request.getSession(true).setAttribute(Constants.USERTYPE, usertypeFromParameter);
                       // response.getOutputStream().println(MAIN_URL);
                    response.sendRedirect(MAIN_URL);
                    } catch (UserNameAlreadyExistException e) {
                        String errorMessage = "Username " + usernameFromParameter + " already exists. Please enter a different username.";
                        request.setAttribute(Constants.USER_NAME_ERROR, errorMessage);
                        getServletContext().getRequestDispatcher(SIGN_UP_USER_EXIST_ERROR_URL).forward(request, response);
                    } catch (UserTypeDidntExistException e) {
                    e.printStackTrace();
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
