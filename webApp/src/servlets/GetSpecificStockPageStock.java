package servlets;

import constants.Constants;
import exception.UserNameAlreadyExistException;
import exception.UserTypeDidntExistException;
import utils.ServletUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GetSpecificStockPageServlet", urlPatterns = {"/pages/main/getSpecificStockPage"})
public class GetSpecificStockPageStock extends HttpServlet {

    private final String SPECIFIC_STOCK_URL = "../specificStock/stock.jsp"; // must start with '/' since will be used in request dispatcher...
    private final String MAIN_URL = "/pages/main/main.html"; // must start with '/' since will be used in request dispatcher...
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

            String stockSymbolFromParameter = request.getParameter(Constants.STOCKSYMBOL);

            if((stockSymbolFromParameter!=null)&&(engine.isSymbolExist(stockSymbolFromParameter))){
                request.getSession(true).setAttribute(Constants.STOCK_SYMBOL_FOR_TRADE, stockSymbolFromParameter);
                response.sendRedirect(SPECIFIC_STOCK_URL);
            }
            else {
                response.sendRedirect(MAIN_URL);
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
