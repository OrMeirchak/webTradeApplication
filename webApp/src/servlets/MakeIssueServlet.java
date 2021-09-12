package servlets;

import constants.Constants;
import exception.*;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//in case of symbol already exist -- > the servlet return 409 error and MAKE_ISSUE_ERROR="symbolalreadyexisterror"
//in case of companyName already exist -- > the servlet return 409 error and MAKE_ISSUE_ERROR="companynamealreadyexisterror"
//in case of not all fields are full -- > the servlet return 409 error and MAKE_ISSUE_ERROR="parametererror"

@WebServlet(name = "makeIssueServlet", urlPatterns = {"/makeIssue"})
public class MakeIssueServlet extends HttpServlet {

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
        response.setContentType("text/plain;charset=UTF-8");
        Engine.Engine engine = ServletUtils.getEngine(getServletContext());

        String userNameFromSession= SessionUtils.getUsername(request);
            String symbolFromParameter = request.getParameter(Constants.SYMBOL);
            String companyNameFromParameter = request.getParameter(Constants.COMPANYNAME);
            String stockAmountFromParameter = request.getParameter(Constants.STOCKAMOUNT);
            String companyValueFromParameter = request.getParameter(Constants.COMPANYVALUE);


            if (symbolFromParameter == null || symbolFromParameter.isEmpty()
            || (companyNameFromParameter == null || companyNameFromParameter.isEmpty())
            || (stockAmountFromParameter == null || stockAmountFromParameter.isEmpty())
            || (companyValueFromParameter == null || companyValueFromParameter.isEmpty()))
            {
                response.getOutputStream().println(Constants.PARAMETERS_ERROR);
            } else {
                try {
                    engine.makeIssue(userNameFromSession,companyNameFromParameter,symbolFromParameter,Integer.valueOf(stockAmountFromParameter),Integer.valueOf(companyValueFromParameter));
                    response.getOutputStream().println("success");
                } catch (UserDidntExistException | NonTraderUserException e) {
                    e.printStackTrace();
                } catch (SymbolAlreadyExistException e) {
                    response.getOutputStream().println(Constants.SYMBOL_ALREADY_EXIST_ERROR);
                } catch (CompanyNameAlreadyExistException e) {
                    response.getOutputStream().println(Constants.COMPANY_NAME_ALREADY_EXIST_ERROR);

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
